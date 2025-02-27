package demo.mini_tomcat.coyote.request;

import demo.mini_tomcat.coyote.header.HttpHeader;
import demo.mini_tomcat.coyote.header.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;

import static demo.mini_tomcat.coyote.header.HttpHeaderType.CONTENT_LENGTH;

public class HttpRequest {

    private final HttpRequestStartLine startLine;
    private final HttpHeaders headers;
    private final String body;

    private HttpRequest(final HttpRequestStartLine startLine, final HttpHeaders headers, final String body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(final BufferedReader bufferedReader) throws IOException {
        final String startLine = bufferedReader.readLine();
        if (startLine == null) {
            throw new IllegalArgumentException("request가 비어있습니다.");
        }

        final HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.from(startLine);
        final HttpHeaders headers = HttpHeaders.from(bufferedReader);
        final String body = readBody(bufferedReader, headers);

        return new HttpRequest(httpRequestStartLine, headers, body);
    }

    private static String readBody(final BufferedReader bufferedReader,
                                   final HttpHeaders headers) throws IOException {

        if (!headers.contains(CONTENT_LENGTH.getValue())) {
            return "";
        }

        final int contentLength = convertIntFromContentLength(headers.get(CONTENT_LENGTH.getValue()));
        return readData(bufferedReader, contentLength);
    }

    private static int convertIntFromContentLength(final HttpHeader contentLength) {
        return Integer.parseInt(String.join("", contentLength.getValues()));
    }

    public HttpRequestStartLine getStartLine() {
        return startLine;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return startLine.getPath();
    }

    public boolean isGetMethod() {
        return startLine.getHttpMethod().isGet();
    }
}
