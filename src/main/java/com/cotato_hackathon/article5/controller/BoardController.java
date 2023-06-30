package com.cotato_hackathon.article5.controller;

import com.cotato_hackathon.article5.dto.BoardDetailResponseDto;
import com.cotato_hackathon.article5.dto.BoardResponseDto;
import com.cotato_hackathon.article5.dto.BoardSaveRequestDto;
import com.cotato_hackathon.article5.dto.BoardSearchResponseDto;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //모임 등록 api
    @PostMapping
    public ResponseEntity<BoardSaveRequestDto> createMeeting(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        //400 에러 -> 잘못된 요청
        Meeting meeting = boardService.createMeeting(boardSaveRequestDto);
        return new ResponseEntity("모임 등록이 완료되었습니다. (meetingId: " + meeting.getMeetingId() + ")", HttpStatus.OK);
    }

    //모임 검색 api(검색 후 모임 조회)
    @GetMapping
    public ResponseEntity<BoardSearchResponseDto> getMetting(
            @RequestParam(value = "dis") String dis,
            @RequestParam(value = "ngb") String ngb,
            @RequestParam(value = "sortBy") String sortBy) {

        List<Meeting> allMeetings = boardService.sortMeetingBySortBy(
                boardService.findAllMeetings(dis, ngb), sortBy);

        List<BoardResponseDto> boardList = allMeetings.stream().map(BoardResponseDto::new).collect(Collectors.toList());


        BoardSearchResponseDto pageResponseDto = new BoardSearchResponseDto((long) allMeetings.size(), boardList);
        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
    }

    //모임 상세페이지 접속 api
    @GetMapping("/{meetingId}")
    public ResponseEntity<BoardDetailResponseDto> getBoardDetail(
            @PathVariable Long meetingId) {
        return ResponseEntity.ok(
                new BoardDetailResponseDto(boardService.findMeetingById(meetingId))
        );
    }
}
