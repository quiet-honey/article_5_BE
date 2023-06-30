package com.cotato_hackathon.article5.entity.senior;

import com.cotato_hackathon.article5.entity.Enrollment;
import com.cotato_hackathon.article5.entity.Meeting;
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
    @Column(nullable = false)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // APPLE, KAKAO

    private String socialId; // 로그인한 소셜 타입의 식별자 값

    @OneToMany(mappedBy = "senior")
    private List<Meeting> meetingList = new ArrayList<>();

    @OneToMany(mappedBy = "senior")
    private List<Enrollment> enrollmentList = new ArrayList<>();
    private String refreshToken;

    //빌더
    @Builder
    public Senior(String name, String phone, Long prefer) {
        this.name = name;
        this.phone = phone;
        this.prefer = prefer;
    }

    // 유저 권한 설정 메소드
    public void authorizeMember() {
        this.role = Role.USER;
    }

    // RefreshToken 재발급 시 사용할 메소드
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void updateName(String nickname) { this.name = name; }
}

