package com.cotato_hackathon.article5.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //고객 id = auto increment
    private Long enrollId;

    @ManyToOne
    private Senior seniorId;

    @ManyToOne
    private Meeting meetingId;

    @Column(nullable = false)
    private boolean isApproved;

    @Builder
    public Enrollment(Long enrollId, Senior seniorId, Meeting meetingId, boolean isApproved){
        this.enrollId = enrollId;
        this.seniorId = seniorId;
        this.meetingId = meetingId;
        this.isApproved = isApproved;
    }
}
