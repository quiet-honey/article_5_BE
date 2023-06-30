package com.cotato_hackathon.article5.service;
import com.cotato_hackathon.article5.dto.BoardDetailResponseDto;
import com.cotato_hackathon.article5.dto.SeniorParticipantResponseDto;
import com.cotato_hackathon.article5.entity.senior.Senior;

import com.cotato_hackathon.article5.dto.SeniorUpdateRequestDto;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.repository.EnrollmentRepository;
import com.cotato_hackathon.article5.repository.MeetingRepository;
import com.cotato_hackathon.article5.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeniorService {
    private final SeniorRepository seniorRepository;
    private final MeetingRepository meetingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ValidateService validateService;


    // 자신이 연 모든 모임 조회
    public List<BoardDetailResponseDto> findAllMeeting(){
        Senior senior = validateService.validateSenior();
        return meetingRepository.findAllBySenior(senior).stream()
                .map(BoardDetailResponseDto::new).collect(Collectors.toList());
    }

    public List<SeniorParticipantResponseDto> findAllParticipant(Long meetingId){
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(()-> new IllegalArgumentException("해당 모임이 없습니다. meetingId = "+meetingId));
        Senior senior = validateService.validateSenior();
        if(!meeting.getSenior().getSeniorId().equals(senior.getSeniorId())){
            throw new RuntimeException("자신이 연 모임이 아닙니다.");
        }
        return enrollmentRepository.findAllByMeeting(meeting).stream()
                .map(SeniorParticipantResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public String update(SeniorUpdateRequestDto requestDto){
        Senior senior = validateService.validateSenior();
        senior.updateName(requestDto.getName());
        senior.updatePhone(requestDto.getPhone());
        return "OK";
    }

    @Transactional
    public String signout(){
        Senior senior = validateService.validateSenior();
        seniorRepository.deleteById(senior.getSeniorId());
        return "회원 탈퇴 완료";
    }
}
