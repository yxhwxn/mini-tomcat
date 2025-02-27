package demo.mini_tomcat.coyote;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum HttpVersion {
    HTTP11("HTTP/1.1");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public static HttpVersion from(final String version) {
        return Arrays.stream(HttpVersion.values())
                .filter(it -> it.version.equals(version))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 HttpHeaderType이 없습니다. " + version));
    }

    public String getVersion() {
        return version;
    }
}
