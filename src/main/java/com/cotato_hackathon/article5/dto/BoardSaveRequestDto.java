package com.cotato_hackathon.article5.dto;

import com.cotato_hackathon.article5.entity.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String notice;
    private String place;
    private LocalDateTime meetingTime;
    private Long totalSenior;

}
