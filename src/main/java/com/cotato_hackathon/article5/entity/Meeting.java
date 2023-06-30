package com.cotato_hackathon.article5.entity;

import com.cotato_hackathon.article5.entity.senior.Senior;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    @Id //PK값 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK가 자동으로 1씩 증가
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
    @JoinColumn(name = "seniorId")
    private Senior senior;
    @ManyToOne
    @JoinColumn(name = "centerId")
    private Center center;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Enrollment> enrollmentList = new ArrayList<>();

    @Builder
    public Meeting(String title, String notice, String place, LocalDateTime meetingTime, Long currentSenior, Long totalSenior) {
        this.title = title;
        this.notice = notice;
        this.place = place;
        this.meetingTime = meetingTime;
        this.currentSenior = currentSenior;
        this.totalSenior = totalSenior;
    }

}
