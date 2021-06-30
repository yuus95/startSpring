package com.example.jpa_shop;


import com.example.jpa_shop.domain.*;
import com.example.jpa_shop.domain.Item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct //객체 초기화 부분, 객체가 생성된 후 별도의 초기화 작업을 위해 실행하는 메소드를 선언한다.
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }





    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        public void dbInit1(){
            Member member = getMember();
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1 Book");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 Book");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);


            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);


        }

        private Member getMember() {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울","1","1"));
            return member;
        }


        public void dbInit2(){
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("부산","2","2"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring1 Book");
            book1.setPrice(20000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("Spring2 Book");
            book2.setPrice(30000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 30000, 2);


            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);


        }
    }

}
