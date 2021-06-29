package com.example.jpa_shop.api;


import com.example.jpa_shop.domain.Address;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderItem;
import com.example.jpa_shop.domain.OrderStatus;
import com.example.jpa_shop.repository.OrderRepository;
import com.example.jpa_shop.repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;


    // 엔티티 자체를 반환하기때문에 사용하면 안된다.
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());


        // iter + TAP 치면 자동 작성
        //  프록시 객체를 강제로 초기화 하기 위해서 처리하는 과정
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    // order 자체를 반환하는것이 아니라 DTO를 통해서 반환한다. -- N+1 문제 해결xx
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;

    }

    // v2과 차이점은 레포에서 fetch join을 통해 객체그래프를 탐색할 수 있게 만든것만 차이난다.
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    //DTO를 사용하여 데이터를 보낼떄는 엔티티에 대한 의존도를 완전히 뗴어내야된다.
            @Data
            static class OrderDto{

                private Long orderId;
                private String name;
                private LocalDateTime orderDate;
                private OrderStatus orderStatus;
                private Address address;

                // DTO 안에 엔티티가 있으면 안된다. 매핑하는것도 안됨
        private List<OrderItemDto> orderItems; // DTO로 바꿔서 사용해야한다.




        public OrderDto(Order o){
            orderId= o.getId();
            name = o.getMember().getName();
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address=o.getDelivery().getAddress();

//            o.getOrderItems().stream().forEach(o-> o.getItem().getName());
//            orderItems=o.getOrderItems();
            orderItems = o.getOrderItems().stream()
                    .map( orderItem-> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());


        }
    }


    @Getter
    static class OrderItemDto{

        private String itemName; // 상품명
        private int orderPrice; // 주문가격
        private int count; // 주문 수량


        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getItem().getPrice();
            count = orderItem.getCount();

        }
    }
}
