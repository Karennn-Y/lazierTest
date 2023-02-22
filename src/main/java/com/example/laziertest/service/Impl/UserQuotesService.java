package com.example.laziertest.service.Impl;

import com.example.laziertest.dto.module.UserQuotesDto;
import com.example.laziertest.dto.module.UserQuotesInput;
import com.example.laziertest.exception.QuotesException.UserAlreadyExistException;
import com.example.laziertest.exception.QuotesException.UserNotFoundException;
import com.example.laziertest.persist.entity.module.UserQuotes;
import com.example.laziertest.persist.entity.user.LazierUser;
import com.example.laziertest.persist.repository.UserQuotesRepository;
import com.example.laziertest.service.user.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQuotesService {

    private final UserQuotesRepository userQuotesRepository;
    private final MemberService memberService;

    public void add(HttpServletRequest request, UserQuotesInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);
        // 중복 아이디 예외
        if (userQuotesRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 존재합니다.");
        }

        UserQuotes userQuotes = UserQuotes.builder().lazierUser(lazierUser)
            .content(parameter.getContent()).build();

        userQuotesRepository.save(userQuotes);
    }

    public UserQuotesDto get(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserQuotes userQuotes = userQuotesRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return UserQuotesDto.of(userQuotes);
    }

    public void update(HttpServletRequest request, UserQuotesInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserQuotes userQuotes = userQuotesRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotes.update(parameter.getContent());
    }

    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserQuotes userQuotes = userQuotesRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotesRepository.delete(userQuotes);
    }

}
