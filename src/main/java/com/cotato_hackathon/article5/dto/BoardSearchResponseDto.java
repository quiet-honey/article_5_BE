package com.cotato_hackathon.article5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardSearchResponseDto {
    private Long totalCount;
    private List<BoardResponseDto> boardList;

    public BoardSearchResponseDto(Long totalCount, List<BoardResponseDto> boardList) {
        this.totalCount = totalCount;
        this.boardList = boardList;
    }
}
