package com.cotato_hackathon.article5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SeniorUpdateRequestDto {

    private Long seniorId;
    private String name;
    private String phone;

    @Builder
    public SeniorUpdateRequestDto(Long seniorId,String name, String phone){
        this.seniorId = seniorId;
        this.name = name;
        this.phone = phone;
    }
}
