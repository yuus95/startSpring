package com.example.jpa_shop.service;

import com.example.jpa_shop.domain.Address;
import com.example.jpa_shop.domain.Item.Book;
import com.example.jpa_shop.domain.Item.Item;
import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.domain.OrderStatus;
import com.example.jpa_shop.exception.NotEnoughStockException;
import com.example.jpa_shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderSerivceTest {

    @Autowired  EntityManager em;
    @Autowired  OrderSerivce  orderSerivce;
    @Autowired  OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();


        Book book = createBook("시골 JPA", 10000, 10);

        em.persist(book);

        int orderCount  = 2;
        //when

        Long orderId = orderSerivce.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 Order");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000*orderCount,getOrder.getTotalPrice(),"주문 가격은 가격* 수량이다");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }


    @Test
    public void 주문취소() throws Exception{
        //given

        //when

        //then
    }


    //이런 예외 테스트가 중요하다
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 8;

        //when

        //then
        NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
            orderSerivce.order(member.getId(), book.getId(), orderCount);
        });
        assertEquals(ex.getMessage(), "need more Stock");
    }
}