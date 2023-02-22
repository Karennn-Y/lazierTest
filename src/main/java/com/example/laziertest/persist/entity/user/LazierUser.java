package com.example.laziertest.persist.entity.user;

import com.example.laziertest.dto.user.MemberInfo;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class LazierUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100, unique = true)
    private String userEmail; //oauth

    private String userName; //oauth

    private String oauthId; //oauth

    private String password;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private String userStatus;

    private String socialType;

    private String dataStatus; //수정

    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;


    public void updateUserInfo(MemberInfo memberInfo) {
        if (!memberInfo.getSocialType().toLowerCase().trim().equals("google")) {
            this.userEmail = memberInfo.getUserEmail();
        }
        this.userName = memberInfo.getUserName();
        this.phoneNumber = memberInfo.getPhoneNumber();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
