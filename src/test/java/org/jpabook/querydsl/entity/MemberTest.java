package org.jpabook.querydsl.entity;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        //when
        List<Member> selectMFromMemberM = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        //then
        for (Member member : selectMFromMemberM) {
            System.out.println("member = " + member);
            System.out.println("member.team = " + member.getTeam());
        }
    }

    @Test
    public void versionTest() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        //when
        List<Member> selectMFromMemberM = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        //then
        for (Member member : selectMFromMemberM) {
            System.out.println("member = " + member);
            System.out.println("member.team = " + member.getTeam());
            System.out.println("member.getVersion() = " + member.getVersion());
            member.setUsername("M"+member.getUsername());
        }
        em.flush();
        em.clear();

        List<Member> findMembers = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
        for (Member findMember : findMembers) {
            System.out.println("findMember = " + findMember);
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            System.out.println("findMember.getVersion() = " + findMember.getVersion());
        }


        assertThat(findMembers).extracting("version").containsExactly(1,1,1,1);
    }
}