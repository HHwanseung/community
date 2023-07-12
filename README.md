# :pushpin: Community
  REST API Community Project
</br>

## 제작 기간 & 참여 인원
📋 2022년 11월 8일 ~ 현재까지 업데이트 중
- 개인 프로젝트

## 개발 필수요건

### 게시물
- 게시물을 등록/수정/삭제 가능
- 카테고리를 활용 가능

### 사용자별 게시물
- 댓글/대댓글 활용 가능
- 첨부파일을 등록/수정/삭제 가능
- 좋아요, 즐겨찾기 활용 가능

### 사용자
- 사용자 자신의 정보 관리가능

### 메세지
- 메세지 등록/수정/삭제 가능
- 받은 메세지, 보낸 메세지 관리 가능

### 신고
- 사용자, 게시물을 신고 가능

### 관리자
- 신고된 사용자, 게시물을 관리 가능
- 카테고리 관리 가능

## Tech Stacks

- ### Spring Boot Framework

- ### MySQL

- ### Swagger Open API 3.0

</br>

# Project Sturucture

```bash
community
├── advice
├── config
│   ├── auth
│   ├── constant
│   ├── jwt
│   ├── redis
│   └──  security
├── controller
├── dto
├── entity
├── exceptions
│   └── type
├── repository
├── response 
└── service
```

### Components

- advice
  - 인증/인가와 관련된 컴포넌트
  - `ex) jwt, google, github, ...etc`
- config
  - 전역으로 공통적으로 사용될 파일들로 구성
  - `ex) Dto, Decorator, enum, interface, utils`
- controller
  - database, orm 설정 등 추가적으로 특정 패키지의 설정이 들어갈 때 해당 디렉토리에서 모듈화하도록 구성
- dto
  - 커스텀하게 특정 예외나 에러를 처리하기 위한 클래스들이 위치할 디렉토리
  - `ex) HttpExceptionFilter`
- entity
  - AOP를 위한 특정 역할을 위한 커스텀한 Interceptor 클래스들이 위치할 디렉토리
  - `ex) Logging Interceptor, Transform Interceptor`
- exceptions
  - 주문 관련 도메인의 파일로 구성
- repository
  - 상품 관련 도메인의 파일로 구성
- response
  - 유저 관련 도메인의 파일로 구성
- service
  - 각종 유효성 검사를 위한 파일로 구성
  - `ex) 환경변수 및 데이터베이스 설정 Validation`


## ERD 설계
![ERD_real](https://user-images.githubusercontent.com/78191801/236974012-ada52da7-0c2a-4000-9c0d-34d663fe388b.png)

# API

## Auth

- 회원가입 `(POST /sign-up)`
- 로그인 `(POST /sign-in)`
- 토큰 재발급 `(POST /reissue)`

## Board

- 게시물 작성 `(POST /boards)`
- 전체 게시물 조회 `(GET /boards/all/{categoryid})`
- 단건 게시물 조회 `(GET /boards/{id})`
- 게시물 수정 `(PUT /boards/{id})`
- 게시물 삭제 `(DELETE /boards/{id})`
- 게시물 검색 `(GET /boards/search?page={})`
- 게시물 좋아요/취소 `(POST /boards/{id})`
- 게시물 즐겨찾기/취소 `(POST /boards/{id}/favorites)`
- 추천글 조회 `(GET /boards/best)`

## Member

- 전체 회원 조회 `(GET /members)`
- 개인 회원 조회 `(GET /members/{id})`
- 회원 정보 수정 `(PUT /members)`
- 전체 회원 삭제 `(DELETE /members)`

## Comment
- 댓글 작성 `(POST /comments)`
- 댓글 조회 `(GET /comments)`
- 댓글 삭제 `(DELETE /comments/{id})`

## Message
- 메세지 작성 `(POST /messages)`
- 받은 메세지 전부 확인 `(GET /messages/receiver)`
- 받은 메세지 단건 확인 `(GET /messages/receiver/{id})`
- 보낸 메세지 전부 확인 `(GET /messages/sender)`
- 보낸 메세지 단건 확인 `(GET /messages/sender/{id})`
- 받은 메세지 삭제 `(DELETE /messages/receiver/{id})`
- 보낸 메세지 삭제 `(DELETE /messages/sender/{id})`

## Category
- 카테고리 생성 `(POST /categoryies)`
- 카테고리 조회 `(GET /categoryies)`
- 카테고리 삭제 `(DELETE /categoryies/{id})`

## Report
- 유저 신고 `(POST /reports/users)`
- 게시물 신고 `(POST /reports/boards)`

## Admin
- 정지 유저 관리 `(GET /admin/manages/members)`
- 신고된 유저 정지 해제 `(POST /admin/manages/members/{id})`
- 게시물 관리 `(GET /admin/manages/boards)`
- 신고된 게시물 관리 `(POST /admin/manages/boards/{id})`

</br>

## 핵심 트러블 슈팅
### Authentication Refactoring
- 기존에는 Security를 Service에 의존해서 처리하는 방식으로 했지만 Service는 도메인에 대한 비지니스 로직이 들어가서 Security를 의존하는건 좋지 않다고 생각하였습니다. 

- 사전에 미리 인증로직을 수행하면 조금 더 빠르게 인증 실패 에러를 내려줄 수 있다 생각하게 되었습니다.
  Controller에서 인증에 필요한 정보를 가지고, 서비스 메소드 파라미터로 전달해줄 것입니다.

<details>
<summary><b>기존 코드</b></summary>
<div markdown="1">

~~~java
  
  /**
  * BoardController
  */
  @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createBoard(@Valid @ModelAttribute BoardCreateRequest req,
                           @RequestParam(value = "category", defaultValue = "1") int categoryId) {
        return Response.success(boardService.createBoard(req, categoryId));
    }
  
  /**
  * BoardService
  */
  @Transactional
    public BoardCreateResponse createBoard(BoardCreateRequest req, int categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member meber = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        List<Image> images = req.getImages().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Board board = boardRepository.save(new Board(req.getTitle(), req.getContent(), user, category, images));
~~~
  
  

</div>
</details>
  
- 또한 이대로 리팩토링을 하다보니 너무 중첩되는 코드가 왔다갔다 하는거 같아 
  인증 & 인가 로직을 따로 메서드로 분리하였습니다. 

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

~~~java
  
   /**
  * BoardController
  */
  @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createBoard(@Valid @ModelAttribute BoardCreateRequest req,
                           @RequestParam(value = "category", defaultValue = "1") int categoryId) {
        Member member = getPrincipal();
        return Response.success(boardService.createBoard(req, categoryId, member));
    }
    private Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
  
  
  /**
  * BoardService
  */
  @Override
    public BoardCreateResponse createBoard(BoardCreateRequest req, int categoryId, Member member) {
        List<Image> images = req.getImages().stream().
                map(i -> new Image(i.getOriginalFilename()))
                .collect(toList());
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Board board = boardRepository.save(new Board(req.getTitle(), req.getContent(), member, category, images));
        uploadImages(board.getImages(), req.getImages());
        return new BoardCreateResponse(board.getId(), board.getTitle(), board.getContent());
    }
  
~~~

</div>
</details>

</br>

</div>
</details>


