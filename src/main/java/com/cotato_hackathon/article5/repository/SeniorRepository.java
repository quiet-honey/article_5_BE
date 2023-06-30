package com.cotato_hackathon.article5.repository;

import com.cotato_hackathon.article5.entity.senior.Senior;
import com.cotato_hackathon.article5.entity.senior.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
    Optional<Senior> findByEmail(String email);

    Optional<Senior> findByRefreshToken(String refreshToken);

    Optional<Senior> findBySocialTypeAndSocialId(SocialType socialType, String id);
}
