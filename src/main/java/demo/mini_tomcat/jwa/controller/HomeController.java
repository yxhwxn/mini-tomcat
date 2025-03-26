package demo.mini_tomcat.jwa.controller;

import demo.mini_tomcat.coyote.http11.controller.AbstractController;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;

import static demo.mini_tomcat.coyote.HttpStatus.OK;


public class HomeController extends AbstractController {
    private static final String DEFAULT_MESSAGE = "Hello world!";

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.setHttpStatus(OK);
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.setBody(DEFAULT_MESSAGE);
    }
}
