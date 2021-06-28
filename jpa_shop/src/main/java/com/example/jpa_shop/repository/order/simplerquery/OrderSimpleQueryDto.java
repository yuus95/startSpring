package com.example.jpa_shop.repository.order.simplerquery;

import com.example.jpa_shop.domain.Address;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
            this.orderId =orderId;
            this.name =name;
            this.orderDate =orderDate;
            this.orderStatus =orderStatus;
            this.address =address;
        }
}
