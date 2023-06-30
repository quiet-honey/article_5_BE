package com.cotato_hackathon.article5.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    @Column(nullable = false)
    private String centerName;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String neighbor;

    private String detail;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    List<Meeting> meetingList = new ArrayList<>();
}