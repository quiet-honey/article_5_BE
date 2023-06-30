package com.cotato_hackathon.article5.service;

import com.cotato_hackathon.article5.config.SecurityUtil;
import com.cotato_hackathon.article5.entity.senior.Senior;
import com.cotato_hackathon.article5.repository.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidateService {
    private final SeniorRepository seniorRepository;

    // Access Token으로 회원 검증 및 불러오기
    public Senior validateSenior() {
        String email = SecurityUtil.getEmail();
        return validateMemberByEmail(email);
    }

    // Email로 회원 불러오기
    public Senior validateMemberByEmail(String email) {
        return seniorRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException());
    }

    // 회원 ID로 회원 불러오기
    public Senior validateMemberById(Long id){
        return seniorRepository.findById(id).orElseThrow(()->
                new RuntimeException());
    }

}