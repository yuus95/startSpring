package com.example.jpa_shop.repository;

import com.example.jpa_shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//쿼리문 작성하는 곳 같음 mapper같은역할
@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {


    //@PersistenceContext -- >  다른방법으로 인잭션 하는방법
    private final EntityManager em;



    public void save(Member member){
        em.persist(member);
        //persist한다고 db에 insert문이 바로나가지않는다
        // commit이 될 때 insert문이 나감
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        List<Member> result =  em.createQuery("select m from Member m",Member.class).getResultList();
        return result;
    }

    //회원 이름으로 조회하는방법법
   public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class).setParameter("name",name).getResultList();
    }

}
