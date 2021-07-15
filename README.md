# springboot-jpa-project
    springboot-jpa-project는 책으로 실습한 내용을 기반으로 커뮤니티 게시판 서비스를 구현한 것입니다.
    게시판 구현에 대한 다양한 경험을 쌓기 위해 프로젝트를 구현했습니다.
    
    앞서 한 프로젝트의 게시판 작성, 수정, 삭제 기능뿐만 아닌
    회원가입 관련 기능, 댓글 작성, 메세지 전송등의 기능을 추가했습니다.
    
 ### 프로그램 기능

    *게시물 작성/ 수정/ 삭제/ 검색/ 페이징
    *댓글 작성/ 수정/ 삭제
    *메세지 확인/ 전송 / 삭제
    *회원 정보 확인/ 수정/ 탈퇴
    *회원가입/ 아이디 찾기/ 비밀번호 찾기(임시 비밀번호 발급)

  
      
### 개발 환경
  * 사용 OS       : Window10
  * Data Base     : Maria DB
  * IDE           : IntelliJ IDEA ULtimate (자바스크립트 지원을 위해 꼭 사용)
  * java8
  * SpringBoot 2.4.2 
  * gradle
  * JPA
  
 ### Gradle 버전로 오류가 생길 시
  ![image](https://user-images.githubusercontent.com/75718761/125775722-29c30171-b243-4ec9-b6d2-bcd5b9bfd329.png)
        
      gradle 버전을 gradle-6.7.1로 사용했지만 추후 업데이트시 정상 작동을 안 할 경우
      gradlew wrapper --gradle-version 4.x.x와 같은 4버전으로 다운그레이드를 하여 사용
      
 ### DB 연결 단위 테스트 JUnit4
 ![image](https://user-images.githubusercontent.com/75718761/125779019-ff2ac2f0-45f7-40a1-87d0-02b1570e7b2e.png)
        
        DB가 연결이 되었는지 확인하기 위해 JUnit4를 이용하여 단위 테스트
        Junit5가 기본 설정이므로 gradle에 Junit4 버전을 추가 후 설정에서 Run tests using을 IntelliJ IDEA로 변경
        

##  메인 기능 화면

#### 1.글 등록 및 세부 화면
글 등록 화면 | 글 등록 후 세부 화면 | 비작성자 화면 
:-------------------------:|:-------------------------:|:-------------------------:
![글 등록](https://user-images.githubusercontent.com/75718761/125769287-5ec68438-4d27-4799-9182-0755edbc676f.JPG)|![세부 글](https://user-images.githubusercontent.com/75718761/125769275-27c1917d-5944-4017-9e25-144dae9c0b3f.JPG)|![세부 글 본인 글이 아닐 때](https://user-images.githubusercontent.com/75718761/125769272-0fa8c67b-0bf4-497b-a1ca-896f6143f302.JPG)
제목과 내용만 입력 가능|수정 버튼 클릭 후 수정 및 삭제 가능<br>조회수, 작성일자와 수정일자도 관람 가능|수정 버튼 노출되지 않음
#### 2.댓글 화면
댓글 화면 |  댓글 수정 제한
:-------------------------:|:-------------------------:
![댓글](https://user-images.githubusercontent.com/75718761/125770446-21b7d949-daf5-4857-9b84-a56f487ae936.JPG)|![댓글 수정 제한](https://user-images.githubusercontent.com/75718761/125770448-02d22f34-3d8d-4c2a-98c1-ddca1201efeb.JPG)
댓글 작성/ 수정/ 삭제 가능 화면 <br> 버튼은 모두 노출| 버튼은 모두 노출 되지만 본인이 아닐 시 경고문과 함께 불가함
#### 3.메세지 화면
나의 메세지함 | 메세지 전송  | 메세지 삭제
:-------------------------:|:-------------------------:|:-------------------------:
![마이페이지 -쪽지함](https://user-images.githubusercontent.com/75718761/125771682-362ad34b-c0b7-4b38-bbd9-c10cb04feb27.JPG)|![마이페이지 - 쪽지 전송 2](https://user-images.githubusercontent.com/75718761/125772368-1633f6c6-c0a0-4910-b884-75325544d2d5.JPG)|![마이페이지 -쪽지 삭제](https://user-images.githubusercontent.com/75718761/125771678-e17a3ede-2daa-4e7f-8955-c37aed7504e3.JPG)
온 메세지를 확인 가능<br>옆의 메뉴에서 메세지 수 확인 가능|회원 아이디를 알면 메세지 전송 가능|메세지 삭제 화면
