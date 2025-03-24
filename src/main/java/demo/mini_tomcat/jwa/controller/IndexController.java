package demo.mini_tomcat.jwa.controller;

import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;
import demo.mini_tomcat.jwa.application.SessionAuthorizeService;

import static demo.mini_tomcat.jwa.controller.ResourceUrls.INDEX_HTML;
import static demo.mini_tomcat.jwa.controller.ResourceUrls.LOGIN_HTML;

public class IndexController extends ResourceController {

    private final SessionAuthorizeService sessionAuthorizeService = SessionAuthorizeService.getInstance();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (sessionAuthorizeService.isAuthorized(httpRequest)) {
            setResource(INDEX_HTML.getValue(), httpResponse);
            return;
        }
        setRedirectHeader(httpResponse, LOGIN_HTML);
    }
}
