package com.example.jpa_shop.repository;

import com.example.jpa_shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {


    //JPA 에서 By뒤에있는 글자를 보고 쿼리문을 자동으로 생성해준다.
    List<Member> findByName(String name);
}
