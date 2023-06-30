package com.cotato_hackathon.article5.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "meeting")
public class Meeting {
    @Id //PK값 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK가 자동으로 1씩 증가
    @Column(name = "meetingId", unique = true, nullable = false)
    private Long meetingId;
    @Column(nullable = false)
    private String title;
    @Column
    private String notice;
    @Column(nullable = false)
    private String place;
    @Column(nullable = false)
    private LocalDateTime meetingTime;
    @Column(nullable = false)
    private Long totalSenior;
    @Column(nullable = false)
    private Long currentSenior;
    @ManyToOne
    @JoinColumn(name = "SeniorID")
    private Senior senior;
    @ManyToOne
    @JoinColumn(name = "centerId")
    private Center center;
    @OneToMany(mappedBy = "enrollment")
    private List<Enrollment> enrollmentList = new ArrayList<>();

    //빌더
    @Builder
    public Meeting(String title, String notice, String place, LocalDateTime meetingTime, Long totalSenior, Long currentSenior, Senior senior, Center center) {
        this.title = title;
        this.notice = notice;
        this.place = place;
        this.meetingTime = meetingTime;
        this.totalSenior = totalSenior;
        this.currentSenior = currentSenior;
        this.senior = senior;
        this.center = center;
    }

}
