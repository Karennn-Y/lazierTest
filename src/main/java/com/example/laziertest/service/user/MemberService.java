package com.example.laziertest.service.user;

import com.example.laziertest.component.MailComponents;
import com.example.laziertest.dto.user.FindPasswordRequestDto;
import com.example.laziertest.dto.user.MemberInfo;
import com.example.laziertest.dto.user.UpdatePasswordRequestDto;
import com.example.laziertest.exception.user.FailedFindPasswordException;
import com.example.laziertest.exception.user.NotFoundMemberException;
import com.example.laziertest.exception.user.NotMatchMemberException;
import com.example.laziertest.persist.entity.user.LazierUser;
import com.example.laziertest.persist.repository.MemberRepository;
import com.example.laziertest.type.MemberStatus;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final RedisService redisService;
	private final MailComponents mailComponents;

	public MemberInfo showUserInfo(HttpServletRequest request) {
		LazierUser lazierUser = searchMember(parseUserId(request));
		return MemberInfo.of(lazierUser);
	}

	public void updateUserInfo(HttpServletRequest request, MemberInfo memberInfo) {
		LazierUser lazierUser = searchMember(parseUserId(request));
		lazierUser.updateUserInfo(memberInfo);
	}

	public void updatePassword(HttpServletRequest request, UpdatePasswordRequestDto passwordDto) {
		LazierUser lazierUser = searchMember(parseUserId(request));

		if (!passwordEncoder.matches(passwordDto.getPassword(), lazierUser.getPassword())) {
			throw new NotMatchMemberException("비밀번호를 다시 입력하세요.");
		}
		lazierUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
	}

	//사용자 정보(이메일, 전화번호)가 일치하면 -> 이메일로 임시 비밀번호 전송
	public void findPassword(HttpServletRequest request, FindPasswordRequestDto passwordDto) {
		LazierUser lazierUser = searchMember(parseUserId(request));

		if (!lazierUser.getUserEmail().equals(passwordDto.getUserEmail())
			|| !lazierUser.getPhoneNumber().equals(passwordDto.getPhoneNumber())) {
			throw new NotFoundMemberException("사용자 정보가 없습니다");
		}

		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
		lazierUser.setPassword(passwordEncoder.encode(uuid));

		String email = lazierUser.getUserEmail();
		String title = "Lazier 새 비밀번호 발급";
		String contents = "임시 비밀번호 : " + uuid;

		boolean sendEmail = mailComponents.sendEmail(email, title, contents);
		if (!sendEmail) { throw new FailedFindPasswordException("메일 전송에 실패하였습니다."); }
	}

	@Transactional
	public void withdrawal(HttpServletRequest request) {
		LazierUser lazierUser = searchMember(parseUserId(request));
		redisService.delValues(request);
		//lazierUser.delete();
		//memberRepository.delete(lazierUser);
		lazierUser.setUserStatus(MemberStatus.STATUS_WITHDRAW.getUserStatus());
	}

	public Long parseUserId(HttpServletRequest request) {
		return Long.valueOf(request.getAttribute("userId").toString());
	}

	public LazierUser searchMember(Long userId) {
		LazierUser lazierUser = memberRepository.findByUserId(userId)
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다."));

		return lazierUser;
	}
}
