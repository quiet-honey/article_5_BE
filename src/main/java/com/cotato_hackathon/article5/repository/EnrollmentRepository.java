package com.cotato_hackathon.article5.repository;

import com.cotato_hackathon.article5.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
