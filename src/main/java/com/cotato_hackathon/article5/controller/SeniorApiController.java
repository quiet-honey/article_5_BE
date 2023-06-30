package com.cotato_hackathon.article5.controller;

import com.cotato_hackathon.article5.dto.SeniorParticipantResponseDto;
import com.cotato_hackathon.article5.dto.SeniorUpdateRequestDto;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.service.SeniorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SeniorApiController {

    private final SeniorService seniorService;


    @PutMapping("/mypage")//회원정보 수정
    public String update(@RequestBody SeniorUpdateRequestDto requestDto){
        return seniorService.update(requestDto);
    }

    @GetMapping("/mypage")//내가 만든 모임
    public List<Meeting> findAllMeeting(Long seniorId){
        return seniorService.findAllMeeting(seniorId);
    }

    @GetMapping("/mypage/memberlist")//승인 대기자
    public SeniorParticipantResponseDto findAllParticipant(Long meetingId){
        return seniorService.findAllParticipant(meetingId);
    }


}
