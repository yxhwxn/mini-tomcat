package demo.mini_tomcat.coyote.http11.controller;

import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;

public interface Controller {
    void service(final HttpRequest httpRequest, final HttpResponse httpResponse);
}
