package demo.mini_tomcat.jwa.controller;

import demo.mini_tomcat.catalina.session.SessionManager;
import demo.mini_tomcat.catalina.webutils.Parser;
import demo.mini_tomcat.coyote.header.HttpCookie;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;
import demo.mini_tomcat.jwa.application.SessionAuthorizeService;
import demo.mini_tomcat.jwa.application.UserService;
import demo.mini_tomcat.jwa.dto.UserLoginRequest;

import java.util.Map;
import java.util.NoSuchElementException;

import static demo.mini_tomcat.jwa.controller.ResourceUrls.*;

public class LoginController extends ResourceController {

    private final SessionAuthorizeService sessionAuthorizeService = SessionAuthorizeService.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (sessionAuthorizeService.isAuthorized(httpRequest)) {
            setRedirectHeader(httpResponse, INDEX_HTML);
            return;
        }
        setResource(LOGIN_HTML.getValue(), httpResponse);
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        final String body = httpRequest.getBody();
        login(body, httpResponse);
    }

    private void login(final String body, final HttpResponse httpResponse) {
        final Map<String, String> queryParams = Parser.parseQueryParams(body);
        try {
            final UserLoginRequest userLoginRequest = getUserLoginRequest(queryParams);
            userService.login(userLoginRequest);
            final HttpCookie cookie = SessionManager.createCookie();
            setRedirectHeader(httpResponse, INDEX_HTML);
            httpResponse.addHeader("Set-Cookie", cookie.toHeaderValue());
        } catch (IllegalArgumentException exception) {
            setRedirectHeader(httpResponse, UNAUTHORIZED_HTML);
        } catch (NoSuchElementException exception) {
            setRedirectHeader(httpResponse, LOGIN_HTML);
        }
    }

    private UserLoginRequest getUserLoginRequest(final Map<String, String> queryParams) {
        validateLoginParams(queryParams);
        return new UserLoginRequest(queryParams.get("account"),
                queryParams.get("password"));
    }

    private void validateLoginParams(final Map<String, String> queryParams) {
        if (!queryParams.containsKey("account") || !queryParams.containsKey("password")) {
            throw new IllegalArgumentException("account와 password 정보가 입력되지 않았습니다.");
        }
    }
}
