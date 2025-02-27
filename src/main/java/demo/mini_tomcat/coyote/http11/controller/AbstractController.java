package demo.mini_tomcat.coyote.http11.controller;

import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(final HttpRequest httpRequest,
                        final HttpResponse httpResponse) {
        if (httpRequest.isGetMethod()) {
            doGet(httpRequest, httpResponse);
            return;
        }

        doPost(httpRequest, httpResponse);
    }

    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new NotFoundControllerException("해당하는 메서드의 컨트롤러를 찾을 수 없습니다. " + httpRequest.getUrl());
    }

    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new NotFoundControllerException("해당하는 메서드의 컨트롤러를 찾을 수 없습니다. " + httpRequest.getUrl());
    }
}
