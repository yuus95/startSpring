package com.example.jpa_shop.repository;

import com.example.jpa_shop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
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
