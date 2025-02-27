# mini-tomcat

> 톰캣 서버를 간단하게 직접 구현해보면서 was 서버와 http에 대한 이해를 높여보자

### 서버 기능요구사항

1. GET /login 요청에 로그인 페이지를 보여준다.
2. GET /register 요청에 회원가입 페이지를 보여준다.
3. POST /register , body를 포함한 요청에 회원가입을 시키고 login 페이지로 redirect 시킨다.
4. POST /login 요청에 로그인 처리를 한다.
    - 서버에서 세션을 생성해 로그인 정보를 저장한다.
    - 쿠키에 JSESSION 아이디를 담아서 로그인을 유지시킨다.
5. 로그인 처리가 된 사용자에게는 index.html 페이지를 보여준다.

