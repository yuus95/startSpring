package com.example.jpa_shop.repository;


import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderSearch {
    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태(Order,Cancel)

}
