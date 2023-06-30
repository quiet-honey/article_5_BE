package com.cotato_hackathon.article5.dto;

import com.cotato_hackathon.article5.entity.Enrollment;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.entity.senior.Senior;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SeniorParticipantResponseDto {

    private Long enrollId;
    private Long seniorId;
    private Long meetingId;
    private boolean isApproved;

    @Builder
    public SeniorParticipantResponseDto(Enrollment enrollment){
        this.enrollId=enrollment.getEnrollId();
        this.isApproved = enrollment.getApproved();
        this.seniorId = enrollment.getSenior().getSeniorId();
        this.meetingId = enrollment.getMeeting().getMeetingId();

    }

}
