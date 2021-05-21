package com.example.jpa_shop.controller;


import com.example.jpa_shop.domain.Item.Item;
import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.domain.Order;
import com.example.jpa_shop.repository.OrderSearch;
import com.example.jpa_shop.service.ItemService;
import com.example.jpa_shop.service.MemberService;
import com.example.jpa_shop.service.OrderSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderSerivce orderSerivce;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItem();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
     // 핵심 비즈니스가 있는 경우 식별자만 넘겨준다
        orderSerivce.order(memberId, itemId, count);
        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,Model model){
        //단순히 조회하는 기능이면 바로 Repository를 불러내도 상관 없다 . (단순 위임)
        List<Order> orders = orderSerivce.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderSerivce.cancelOrder(orderId);
        return "redirect:/orders";
    }
}


