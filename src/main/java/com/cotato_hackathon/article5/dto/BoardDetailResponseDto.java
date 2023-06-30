package com.cotato_hackathon.article5.dto;


import com.cotato_hackathon.article5.entity.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailResponseDto {
    private Long meetingID; //모임 Id
    private String title;
    private String notice;
    private String place;
    private LocalDateTime meetingTime;
    private Long totalSenior;
    private Long currentSenior;
    private List<String> attendList;

    public BoardDetailResponseDto(Meeting meeting) {
        this.meetingID = meeting.getMeetingId();
        this.title = meeting.getTitle();
        this.notice = meeting.getNotice();
        this.place = meeting.getPlace();
        this.meetingTime = meeting.getMeetingTime();
        this.totalSenior = meeting.getTotalSenior();
        this.currentSenior = meeting.getCurrentSenior();
    }
}
