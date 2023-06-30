package com.cotato_hackathon.article5.repository;

import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.entity.senior.Senior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllBySenior(Senior senior);
}
