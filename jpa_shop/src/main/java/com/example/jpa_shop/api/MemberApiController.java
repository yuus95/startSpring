package com.example.jpa_shop.api;


import com.example.jpa_shop.domain.Member;
import com.example.jpa_shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody 
@RestController //두개를 합친 어노테이션,responsebody --> 데이터를 JSON으로 바로 반환할 떄
@RequiredArgsConstructor
public class MemberApiController {
//템플릿 컨트롤러랑 API컨트롤러 구분하기 --> 공동처리는 패키지 단위로 많이한다.

    private final MemberService memberService;

    //Get V1
    // 회원목록만 필요한데 회원에 대한 주문목록도 들어간다. -> 직접 엔티티를 반환해서는 안된다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    //Get V2
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        
        
        //api 스펙은 DTD와 1대1이다
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }


//    @GetMapping("/api/yushin/members")
//    public yushin_result member(){
//        List<Member> members = memberService.findMembers();
//
//    }
//
//    static class yushin_result<T>{
//
//    }



    //V1
    //이렇게 사용하면 오류가 날 확률이 높다 XX
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //v2
    // 별도의 엔티티를 RequestBody와 매핑한다.
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return  new CreateMemberResponse(id);
    }


    //수정
    // 수정이랑 등록은 api스펙이 많이 다르다.
    // 별도의 레스펀스를 가져야한다. DTO은 안에서 만드는게 편하다
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        // 가급적이면 변경감지 사용하기
        // 업데이트문은 그상태로 끝내기
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(),findMember.getName());

    }


    /**
     * get 반환 클래스
     */
    @Data
    @AllArgsConstructor
    static  class Result<T> {
        private T data;

    }

    /**
     * get 멤버 DTD
     */
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }



    // 엔티티에는 Getter만쓰기
    // DTO는 어노테이션 많이사용
    @Data
    @AllArgsConstructor
    static  class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static  class UpdateMemberRequest{
        private String name;
    }




    @Data
    static class CreateMemberRequest{
        @NotEmpty //validation을 DTO에 할 수 있다.
        private String name;

    }


    @Data
    public class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }




}
