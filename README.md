# :pushpin: Community
  REST API Community Project
</br>

## 제작 기간 & 참여 인원
📋 2022년 11월 8일 ~ 현재까지 업데이트 중
- 개인 프로젝트

## 사용 기술

  - Java 
  - Spring Boot
  - JPA
  - JPQL
  - MySQL
  - Spring Security
  - JWT
  - Junit5
  - Swagger
  - Docker, Docker-compose
  - Redis
  - ~AWS EC2~
</br>

## 주요기능
* Spring Security, JWT 로그인을 이용한 유저인증
* 게시물 CRUD, 게시물 좋아요/즐겨찾기 기능, 검색 기능, 첨부파일 CRUD
* 유저 CRUD 기능, 유저 전체조회/개인조회 기능
* 유저, 게시물에 신고기능
* Admin 계정은 지속적인 신고를 받은 유저, 게시물을 관리가능
* 댓글/대댓글 CRD 기능과 댓글 좋아요 기능
* 카테고리를 기능, 카테고리별 검색 기능
* 메세지 CRUD기능
* Redis를 이용한 포인트 기능
</br>

## ERD 설계
![ERD_real](https://user-images.githubusercontent.com/78191801/236974012-ada52da7-0c2a-4000-9c0d-34d663fe388b.png)


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

## 그 외 트러블 슈팅
<details>
<summary>npm run dev 실행 오류</summary>
<div markdown="1">

- Webpack-dev-server 버전을 3.0.0으로 다운그레이드로 해결
- `$ npm install —save-dev webpack-dev-server@3.0.0`

</div>
</details>


