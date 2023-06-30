package com.cotato_hackathon.article5.entity;

import com.cotato_hackathon.article5.entity.senior.Senior;
import com.fasterxml.jackson.annotation.JsonBackReference;
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


    @Column(nullable = false)
    private boolean approved;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "seniorId")
    private Senior senior;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "meetingId")
    private Meeting meeting;


    public boolean getApproved() {
        return approved;
    }
}
