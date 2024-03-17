package org.jpabook.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.jpabook.querydsl.dto.MemberSearchCondition;
import org.jpabook.querydsl.dto.MemberTeamDto;
import org.jpabook.querydsl.repository.MemberJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }
}
