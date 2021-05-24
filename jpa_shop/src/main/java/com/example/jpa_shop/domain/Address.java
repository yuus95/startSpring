package com.example.jpa_shop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;


//  객체를 표현할 떄 공통적인 부분을  분리해서 클래스로 저장한 뒤, 테이블에 매핑할 때 사용
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //기본생성자 대신 생성 상속 금지
    protected  Address(){
    }


    // 따로 생성자를만들어두면 기본생성자가 없어서 오류가 뜬다
    public Address(String city,String street,String zipcode){
        this.city=city;
        this.street = street;
        this.zipcode=zipcode;
    }
}
