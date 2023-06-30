package com.cotato_hackathon.article5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardAttendRequestDto {
    private Long seniorId;
    private Long boardId;
}
