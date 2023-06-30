package com.cotato_hackathon.article5.service;

import com.cotato_hackathon.article5.dto.BoardResponseDto;
import com.cotato_hackathon.article5.dto.BoardSaveRequestDto;
import com.cotato_hackathon.article5.entity.Center;
import com.cotato_hackathon.article5.entity.Meeting;
import com.cotato_hackathon.article5.repository.CenterRepository;
import com.cotato_hackathon.article5.repository.MeetingRepository;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class BoardService {
    private final MeetingRepository meetingRepository;
    private final CenterRepository centerRepository;

    public BoardService(MeetingRepository meetingRepository, CenterRepository centerRepository) {
        this.meetingRepository = meetingRepository;
        this.centerRepository = centerRepository;
    }

    //모임 저장 로직
    @Transactional
    public Meeting createMeeting(BoardSaveRequestDto boardSaveRequestDto) {
        final Meeting newMeeting = new Meeting(boardSaveRequestDto.getTitle(), boardSaveRequestDto.getNotice(), boardSaveRequestDto.getPlace(),
                boardSaveRequestDto.getMeetingTime(), boardSaveRequestDto.getCurrentSenior(), boardSaveRequestDto.getTotalSenior());
        return meetingRepository.save(newMeeting);
    }

    // param 으로 아예 tags 나 keyword 를 포함하지 않을 수도 있기 때문에 ==null 로 비교
    // null 일 때 alert 메시지 띄우는걸로
    public List<Meeting> findAllMeetings(String dis, String ngb) {
        //모집 여부 상태 확인
        //changeAllClubStatus();

        //모든 모임 조회
        List<Meeting> meetings = meetingRepository.findAll();

        //모집중 만 필터링

        //tag 와 keyword 값 확인 -> 둘 다 없으면 sortBy만 적용해서 리턴
        List<Meeting> meetingSortedByDisNgb = new ArrayList<>();

        if (dis.isEmpty() && ngb.isEmpty())
            meetingSortedByDisNgb = meetings;
        else {
            for (Meeting meeting : meetings) {
                if (meeting.getCenter().getDistrict().contains(dis) && meeting.getCenter().getNeighbor().contains(ngb)) {
                    meetingSortedByDisNgb.add(meeting);
                }
            }
        }

        return meetingSortedByDisNgb;
    }

    //정렬 메서드
    public List<Meeting> sortMeetingBySortBy(List<Meeting> meetings, String sortBy) {
        if(sortBy.equals("createdAt")){
            return meetings.stream().sorted(Comparator.comparing(Meeting::getMeetingTime).reversed())
                    .collect(Collectors.toList());
        } else {
            return meetings.stream().sorted().collect(Collectors.toList());
        }
    }

    //상세페이지 조회 메서드
    public Meeting findMeetingById(Long meetingId){
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new NoSuchElementException());;
        return meeting;
    }

}
