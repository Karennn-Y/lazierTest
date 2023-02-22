package com.example.laziertest.persist.repository;

import com.example.laziertest.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<LazierUser, Long> {

    boolean existsByUserEmail(String userEmail);
    Optional<LazierUser> findByEmailAuthKey(String uuid);
    Optional<LazierUser> findByUserEmail(String useEmail);
    Optional<LazierUser> findByUserId(Long username);
    Optional<LazierUser> findByOauthId(String oauthId);
}

