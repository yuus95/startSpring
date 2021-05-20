package com.example.jpa_shop.controller;

import com.example.jpa_shop.domain.Address;
import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //컨트롤러 에서 넘어온 화면validation 이랑 도메인의 validation이 다를 수 있기떄문에
    // Member 클래스를 바로 쓰는게 아니라 from클래스를 만들어서 쓴다.  하나로 합치면 지저분해진다.
    //컨트롤러에서 뷰로 넘어갈떄 모델에 데이터를 넣어서 넘긴다
    @GetMapping("/members/new")
    public String createForm(Model model){
        //화면에서는 이 객체를 접근할 수 있다 .
        model.addAttribute("memberForm",new MemberForm());
        return "member/createMemberForm";
    }

    //@vaild를 넣으면 MemberForm에 있는 유효성 검사를 가져올 수 있음
    // PostMapping :Post 요청을 받을 수 있음음
   @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        // 스프링이랑 타임리프가 인텔그리션이 잘되있다  -> 에러를 화면까지끌고감
       // 에러가 나면 들어온 폼데이터는 그대로 돌려보낸다/

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

       Address address  = new Address(form.getCity(), form.getStreet(), form.getZipcode());

       Member member = new Member();
       member.setName(form.getName());
       member.setAddress(address);
       memberService.join(member);

       return "redirect:/";
   }




    @GetMapping("/members")
    public String List(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "member/memberList";
    }

}

