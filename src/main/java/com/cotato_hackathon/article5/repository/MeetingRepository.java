package com.cotato_hackathon.article5.repository;

import com.cotato_hackathon.article5.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
