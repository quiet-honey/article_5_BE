package com.cotato_hackathon.article5.repository;

import com.cotato_hackathon.article5.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByCenterName(String centerName);
}
