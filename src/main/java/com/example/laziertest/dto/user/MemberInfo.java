package com.example.laziertest.dto.user;


import com.example.laziertest.persist.entity.user.LazierUser;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {

	@Email
	@NotBlank
	String userEmail;

	@NotBlank
	String userName;

	@Size(min = 8)
	@NotBlank
	String password;

	@NotBlank
	@Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
	String phoneNumber;

	String socialType;

	public static MemberInfo of(LazierUser lazierUser) {
		return MemberInfo.builder()
			.userEmail(lazierUser.getUserEmail())
			.userName(lazierUser.getUsername())
			.phoneNumber(lazierUser.getPhoneNumber())
			.socialType(lazierUser.getSocialType().toLowerCase().trim()) //google이라면 email 비활성
			.build();
	}
}
