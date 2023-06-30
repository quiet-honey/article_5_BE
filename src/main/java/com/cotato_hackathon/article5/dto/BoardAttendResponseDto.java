package com.cotato_hackathon.article5.dto;

import com.cotato_hackathon.article5.entity.senior.Senior;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Member;

@Getter
@Setter
@NoArgsConstructor
public class BoardAttendResponseDto {
    private String name;
    private String phone;
    private Long prefer;
    private String email;

    public BoardAttendResponseDto(Senior senior) {
        this.name = senior.getName();
        this.phone = senior.getPhone();
        this.prefer = senior.getPrefer();
        this.email = senior.getEmail();
    }
}
