package com.cotato_hackathon.article5.dto;

import com.cotato_hackathon.article5.entity.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String notice;
    private String centerName;
    private LocalDateTime meetingTime;
    private Long totalSenior;
    private Long currentSenior;

    public BoardResponseDto(Meeting meeting) {
        this.title = meeting.getTitle();
        this.notice = meeting.getNotice();
        this.centerName = meeting.getCenter().getCenterName();
        this.meetingTime = meeting.getMeetingTime();
        this.totalSenior = meeting.getTotalSenior();
        this.currentSenior = meeting.getCurrentSenior();
    }
}
