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
public class Senior {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seniorId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private Long prefer;

    @OneToMany(mappedBy = "senior")
    private List<Meeting> meetingList = new ArrayList<>();

    @OneToMany(mappedBy = "senior")
    private List<Enrollment> enrollmentList = new ArrayList<>();

    //빌더
    @Builder
    public Senior(String name, String phone, Long prefer) {
        this.name = name;
        this.phone = phone;
        this.prefer = prefer;
    }
}

