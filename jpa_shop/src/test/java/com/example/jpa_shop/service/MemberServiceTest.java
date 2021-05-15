package com.example.jpa_shop.service;

import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

//통합테스트
@SpringBootTest // 스프링부트 테스트에 필요한 거의 모든 의존성을 제공해준다
@Transactional //테스트에서는 다 롤백 시킨다
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;
//    @Test
//    void 회원가입() throws Exception
//    {
//        //given
//        Member member = new Member();
//        member.setName("1hoon");
//
//        //when
//        Long joinId = memberService.join(member);
//
//        //then
//        assertEquals(member, memberRepository.findOne(joinId));
//    }

    @Test
    public void 회원가입() throws Exception{
            //given
            Member member = new Member();
            member.setName("kim");

            //when
            Long savedId = memberService.join(member);



            //then
            // em.flush();
            //asserEquals(a,b) 객체 a와 b의 값이 같은지 확인
            assertEquals(member,memberRepository.findOne(savedId));
    }



//    @Test
//    void 중복_회원_예외() throws Exception
//    {
//        //given
//        String name = "1hoon";
//
//        Member memberA = new Member();
//        memberA.setName(name);
//
//        Member memberB = new Member();
//        memberB.setName(name);
//
//        //when
//        memberService.join(memberA);
//
//        //then
//        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(memberB));
//        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
//    }
//


    @Test
    public void 중복_회원_예외() throws Exception{
            //given
            Member member1 = new Member();
            member1.setName("kim");

            Member member2 = new Member();
            member2.setName("kim");

            //when
            memberService.join(member1);
         //   memberService.join(member2); //예외가 발생해야함

            //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }
}