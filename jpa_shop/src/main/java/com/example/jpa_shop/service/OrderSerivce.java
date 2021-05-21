package com.example.jpa_shop.service;


import com.example.jpa_shop.domain.Delivery;
import com.example.jpa_shop.domain.Item.Item;
import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderItem;
import com.example.jpa_shop.repository.ItemRepository;
import com.example.jpa_shop.repository.MemberRepository;
import com.example.jpa_shop.repository.OrderRepository;
import com.example.jpa_shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

//ctrl + shift + t  테스트 코드 작성
public class OrderSerivce {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;




    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, long itemId,int count){

         //엔티티 조회
         Member member = memberRepository.findOne(memberId);
         Item item = itemRepository.findOne(itemId);


         //배송정보보 생성 -->  cascade떄문에 따로 저장안해도 order를 저장하는순간 알아서 저장됨
         Delivery delivery = new Delivery();
         delivery.setAddress(member.getAddress());

         // 주문상품 생성(비즈니스 로직이 domain에 있음) - 도메인 모델 패턴
         OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

         //protected access 엔티티에서 protected 접근제한시킴
         // OrderItem orderItem1 = new OrderItem();

         // 주문 생성
         Order order=Order.createOrder(member,delivery,orderItem);

         // 주문 저장
         orderRepository.save(order);

         return order.getId();

     }

    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
//
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
