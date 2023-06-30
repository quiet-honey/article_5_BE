package com.cotato_hackathon.article5.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //고객 id = auto increment
    private Long enrollId;

    @ManyToOne
    @JoinColumn(name = "seniorId")
    private Senior senior;

    @ManyToOne
    @JoinColumn(name = "meetingId")
    private Meeting meeting;

    @Column(nullable = false)
    private boolean isApproved;

}
