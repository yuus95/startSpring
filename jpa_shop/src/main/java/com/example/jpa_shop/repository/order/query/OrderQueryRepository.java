package com.example.jpa_shop.repository.order.query;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Order 레포는 Order 엔티티 같은 핵심적인 것을 찾을 떄(핵심 비즈니스 로직)
// 쿼리 - API 나 화면과 관계 있을 떄

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {


    private final EntityManager em;

    //N+1 문제가 일어났다.
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders(); // 쿼리 1번 - > 2개

        result.forEach(o -> {
           List<OrderItemQueryDto> orderItems= findOrderItems(o.getOrderId()); //Query N 번
           o.setOrderItems(orderItems);
        });

        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.example.jpa_shop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice,oi.count )" +
                        " from OrderItem oi " +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        //값 찾기 편하게 orderItems 을 맵으로 변환 시킨것
        //key : orderId,  value = list
        Map<Long, List<OrderItemQueryDto>> orderItemMap  = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new com.example.jpa_shop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice,oi.count )" +
                        " from OrderItem oi " +
                        " join oi.item i" +
                        " where oi.order.id = :orderId",OrderItemQueryDto.class)
                .setParameter("orderId",orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                " select new com.example.jpa_shop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate,o.status,d.address) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }


}
