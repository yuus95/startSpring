package com.example.jpa_shop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // hello url로 오면 밑에있는 함수 호출
    @GetMapping("hello")
    public String hello(Model model){
        // name:데이터인 곳에 hello을 넘긴다.
        model.addAttribute(  "data","hello!!");
        return "hello";
    }
}
