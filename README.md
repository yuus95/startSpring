# startSpring


---

ctrl  + alt + v :변수 객체 생성
orderService.order(member.getId) -- > 
명렁어 실행시  
반환형 변수명 = orderService.order(member.getId)  로 변경


Ctrl + alt + E :  최근에 봤던 클래스 목록 보기

Ctrl + Shift + alt + T : 리펙토링 

Ctrl 연속 2번 + 방향키:멀티커서


---

__JPA활용2__ <br/>

- 0617
  - 마이크로 서비스 아키텍처 : 애플리케이션을 상호 독립적인 최소 구성요소로 분할
  - 템플릿 컨트롤러랑 API컨트롤러 구분하기
      - 공통 처리는 패키지 단위로 많이 하기 때문에 구분해야한다.
  - API를 만들 때 항상 엔티티를 파라미터로 받지 말기 
    - 엔티티에 프레젠테이션 계층을 위한 로직이 추가 된다.<br/>
      화면 Validation이 엔티티에  들어가게 된다.<br/>
      엔티티가 변경되면 API 스펙이 벼한다/
  - 엔티티 외부 노출 XX
  - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
    - @RequestBody 에 DTO 삽입하기   
    - 엔티티와 API스펙을 명확히 분리할 수 있다.
    - 유지보수가 용이 하다.
  

```
 JSON표현방식
 {
    count:
    data:[{},{}] 이런식으로 표현 하기 
 } --> 스펙 확장이 용이하다
```
    
