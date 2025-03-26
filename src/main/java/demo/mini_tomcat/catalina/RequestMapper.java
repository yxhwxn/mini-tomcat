package demo.mini_tomcat.catalina;

import demo.mini_tomcat.coyote.http11.controller.Controller;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.jwa.controller.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public enum RequestMapper {

    HOME(Constants.HOME_URL_REGEX, new HomeController()),
    INDEX(Constants.INDEX_PAGE_REGEX, new IndexController()),
    LOGIN(Constants.LOGIN_REGEX, new LoginController()),
    REGISTER(Constants.REGISTER_REGEX, new RegisterController()),
    RESOURCE(Constants.RESOURCE_URL_REGEX, new ResourceController());

    private final Pattern urlRegex;
    private final Controller controller;

    RequestMapper(final Pattern urlRegex, final Controller controller) {
        this.urlRegex = urlRegex;
        this.controller = controller;
    }

    public static Controller getControllerFrom(final HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        return Arrays.stream(RequestMapper.values())
                .filter(it -> matchUrl(url, it))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 handler가 없습니다. " + url))
                .controller;
    }

    private static class Constants {
        private static final Pattern HOME_URL_REGEX = Pattern.compile("^/$");
        private static final Pattern RESOURCE_URL_REGEX = Pattern.compile("^(/[a-z|A-Z|가-힣|ㄱ-ㅎ|_|0-9|\\-]*)+(\\.[a-z]*)$");
        private static final Pattern LOGIN_REGEX = Pattern.compile("^(/login)(\\?([^#\\s]*))?");
        private static final Pattern REGISTER_REGEX = Pattern.compile("^(/register)(\\?([^#\\s]*))?");
        private static final Pattern INDEX_PAGE_REGEX = Pattern.compile("^(/index\\.html)");
    }

    private static boolean matchUrl(final String url, final RequestMapper requestMapper) {
        return requestMapper.urlRegex.matcher(url).find();
    }
}
