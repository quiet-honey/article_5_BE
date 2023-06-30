package com.cotato_hackathon.article5.controller;

import com.cotato_hackathon.article5.dto.BoardDetailResponseDto;
import com.cotato_hackathon.article5.dto.SeniorParticipantResponseDto;
import com.cotato_hackathon.article5.dto.SeniorUpdateRequestDto;
import com.cotato_hackathon.article5.service.SeniorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SeniorController {

    private final SeniorService seniorService;

    @PutMapping("/mypage")//회원정보 수정
    public String update(@RequestBody SeniorUpdateRequestDto requestDto){
        return seniorService.update(requestDto);
    }

    @GetMapping("/mypage")//내가 만든 모임
    public List<BoardDetailResponseDto> findAllMeeting(){
        return seniorService.findAllMeeting();
    }

    @GetMapping("/mypage/memberlist") // 승인 대기자
    public List<SeniorParticipantResponseDto> findAllParticipant(Long meetingId){
        return seniorService.findAllParticipant(meetingId);
    }

    @DeleteMapping("signout")
    public String signout(){
        return seniorService.signout();
    }
}
