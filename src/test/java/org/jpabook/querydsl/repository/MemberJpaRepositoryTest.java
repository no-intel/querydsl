package org.jpabook.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.jpabook.querydsl.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();

        List<Member> result1 = memberJpaRepository.findAll_Querydsl();

        List<Member> result2 = memberJpaRepository.findByUsername_Querydsl(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member);
        Assertions.assertThat(result1).containsExactly(member);
        Assertions.assertThat(result2).containsExactly(member);
    }
}