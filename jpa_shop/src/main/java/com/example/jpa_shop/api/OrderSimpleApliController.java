package com.example.jpa_shop.api;


import com.example.jpa_shop.domain.Address;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderStatus;
import com.example.jpa_shop.repository.OrderRepository;
import com.example.jpa_shop.repository.OrderSearch;
import com.example.jpa_shop.repository.order.simplerquery.OrderSimpleQueryDto;
import com.example.jpa_shop.repository.order.simplerquery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * x To One(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController //주용도 - > Json형태로 객체 데이터를 반환
@RequiredArgsConstructor
public class OrderSimpleApliController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order: all){
            order.getMember().getName();  // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }

        return all;

    }

    
    
    /**
     * 반환값을 DTO로 바꿔서 보내기
     * LAZT로딩으로 인한 데이터베이스 쿼리가 너무많이 생성되는 단점을 갖고 있음
     *
     * ORDER -> SQL 1번 ->  결과 주문수 2개
     * N + 1 문제 발생
     *
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){

        //ORDER 2개
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());


        List<SimpleOrderDto> result = orders.stream() // 생성하기
                .map(o -> new SimpleOrderDto(o)) //가공하기 Mapping 스트림 내 요소들을 하나씩 특정 값으로 변환 해 준다.
                .collect(Collectors.toList()); //결과 만들기 Stream elements를 List로 변경한 메서드
        return result;

    }

    @GetMapping("/api/v3/simple-orders") // 재사용확률이 높다
    public List<SimpleOrderDto>ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto>ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();


    }


    @Data // DTO가 엔티티를 파라미터로 받는건 크게 문제가 되지 않는다.
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus(); //LAZY 초기화
        }
    }
}
