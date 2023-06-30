package com.cotato_hackathon.article5.service;
import com.cotato_hackathon.article5.dto.SeniorParticipantResponseDto;
import com.cotato_hackathon.article5.entity.Enrollment;
import com.cotato_hackathon.article5.entity.senior.Senior;

import com.cotato_hackathon.article5.dto.SeniorUpdateRequestDto;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.repository.EnrollmentRepository;
import com.cotato_hackathon.article5.repository.MeetingRepository;
import com.cotato_hackathon.article5.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeniorService {
    private final SeniorRepository seniorRepository;
    private final MeetingRepository meetingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ValidateService validateService;


    @Transactional
    public List<Meeting> findAllMeeting(Long seniorId){
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. seniorid = "+seniorId));

        return meetingRepository.findAllBySenior(senior);
    }

    @Transactional
    public SeniorParticipantResponseDto findAllParticipant(Long meetingId){
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(()-> new IllegalArgumentException("해당 모임이 없습니다. meetingId = "+meetingId));
        List<Enrollment> enrollment = enrollmentRepository.findAllByMeeting(meeting);
        return new SeniorParticipantResponseDto(enrollment);
    }

    @Transactional
    public String update(SeniorUpdateRequestDto requestDto){
        Senior senior = seniorRepository.findById(requestDto.getSeniorId())
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. seniorid = "+requestDto.getSeniorId()));

        //이름, 전화번호 수
        senior.update(requestDto.getName(), requestDto.getPhone());

        return "OK";
    }

    @Transactional
    public String signout(){
        Senior senior = validateService.validateSenior();
        seniorRepository.deleteById(senior.getSeniorId());
        return "회원 탈퇴 완료";
    }
}
