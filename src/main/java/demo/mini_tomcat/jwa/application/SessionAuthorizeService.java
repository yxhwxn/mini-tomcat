package demo.mini_tomcat.jwa.application;

import demo.mini_tomcat.coyote.header.HttpHeader;
import demo.mini_tomcat.coyote.header.HttpHeaders;
import demo.mini_tomcat.coyote.request.HttpRequest;

import java.util.Optional;

import static demo.mini_tomcat.coyote.header.HttpHeaderType.COOKIE;

public class SessionAuthorizeService {

    private static final String JSESSIONID = "JSESSIONID";

    private SessionAuthorizeService() {
    }

    private static class AuthorizeServiceGenerator {
        private static final SessionAuthorizeService INSTANCE = new SessionAuthorizeService();
    }

    public static SessionAuthorizeService getInstance() {
        return AuthorizeServiceGenerator.INSTANCE;
    }

    public boolean isAuthorized(final HttpRequest httpRequest) {
        final HttpHeaders headers = httpRequest.getHeaders();
        if (!headers.contains(COOKIE.getValue())) {
            return false;
        }

        final HttpHeader httpHeader = headers.get(COOKIE.getValue());
        final Optional<String> jsessionid = httpHeader.getValues().stream()
                .filter(it -> it.contains(JSESSIONID))
                .findFirst();

        return jsessionid.isPresent();
    }
}
