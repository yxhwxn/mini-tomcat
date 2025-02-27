package demo.mini_tomcat.coyote.request;

import demo.mini_tomcat.coyote.HttpVersion;

import java.util.List;

public class HttpRequestStartLine {

    private static final int START_LINE_MIN_LENGTH = 3;
    private static final String BLANK_LETTER = " ";

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpVersion httpVersion;

    private HttpRequestStartLine(final HttpMethod httpMethod, final String path, final HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestStartLine from(final String startLine) {
        return parseStartLine(startLine);
    }

    private static HttpRequestStartLine parseStartLine(final String startLine) {
        final List<String> startLineInfos = parseStartLineInfos(startLine);
        final HttpMethod method = HttpMethod.from(startLineInfos.get(0));
        final String path = startLineInfos.get(1);
        final HttpVersion version = HttpVersion.from(startLineInfos.get(2));

        return new HttpRequestStartLine(method, path, version);
    }

    private static List<String> parseStartLineInfos(final String startLine) {
        final List<String> startLineInfos = List.of(startLine.split(BLANK_LETTER));
        validateStartLineLength(startLineInfos);
        return startLineInfos;
    }

    private static void validateStartLineLength(final List<String> startLineInfos) {
        if (startLineInfos.size() < START_LINE_MIN_LENGTH) {
            throw new IllegalArgumentException("요청 정보가 잘못되었습니다.");
        }
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }
}
