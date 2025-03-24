package demo.mini_tomcat.jwa.controller;

import demo.mini_tomcat.catalina.webutils.Parser;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;
import demo.mini_tomcat.jwa.application.UserService;
import demo.mini_tomcat.jwa.dto.UserRegisterRequest;

import java.util.Map;

import static demo.mini_tomcat.jwa.controller.ResourceUrls.INDEX_HTML;
import static demo.mini_tomcat.jwa.controller.ResourceUrls.REGISTER_HTML;

public class RegisterController extends ResourceController {

    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(final HttpRequest httpRequest,
                      final HttpResponse httpResponse) {
        setResource(REGISTER_HTML.getValue(), httpResponse);
    }

    @Override
    public void doPost(final HttpRequest httpRequest,
                       final HttpResponse httpResponse) {
        final String body = httpRequest.getBody();

        final Map<String, String> queryParams = Parser.parseQueryParams(body);
        validateRegisterParams(queryParams);

        final String account = queryParams.get("account");
        final String email = queryParams.get("email");
        final String password = queryParams.get("password");
        final UserRegisterRequest userRegisterRequest = new UserRegisterRequest(account, password, email);
        userService.save(userRegisterRequest);
        setRedirectHeader(httpResponse, INDEX_HTML);
    }

    private void validateRegisterParams(final Map<String, String> queryParams) {
        if (!queryParams.containsKey("account")
                || !queryParams.containsKey("password")
                || !queryParams.containsKey("email")) {
            throw new IllegalArgumentException("회원가입 정보가 입력되지 않았습니다.");
        }
    }
}
