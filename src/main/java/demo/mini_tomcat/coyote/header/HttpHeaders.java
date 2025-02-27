package demo.mini_tomcat.coyote.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HttpHeaders {
    private Map<String, HttpHeader> headers = new LinkedHashMap<>();

    private static final String COLON_LETTER = ":";

    public HttpHeaders() {
    }

    private HttpHeaders(final Map<String, HttpHeader> headers) {
        this.headers = headers;
    }

    public static HttpHeaders from(final BufferedReader bufferedReader) throws IOException {
        return new HttpHeaders(readAllHeaders(bufferedReader));
    }

    public static HttpHeaders of(final HttpHeader... httpHeaders) {
        final Map<String, HttpHeader> headers = Arrays.stream(httpHeaders)
                .collect(Collectors.toMap(HttpHeader::getHttpHeaderType,
                        httpHeader -> httpHeader,
                        (key, value) -> value,
                        LinkedHashMap::new));
        return new HttpHeaders(headers);
    }

    private static Map<String, HttpHeader> readAllHeaders(final BufferedReader bufferedReader) throws IOException {
        final Map<String, HttpHeader> headers = new LinkedHashMap<>();

        while (true) {
            final String line = bufferedReader.readLine();
            if (line.equals("")) {
                break;
            }
            final List<String> header = parseHeader(line);
            final String headerType = removeBlank(header.get(0));
            final String headerValue = removeBlank(header.get(1));
            final String httpHeaderType = HttpHeaderType.of(headerType);
            headers.put(httpHeaderType, HttpHeader.of(httpHeaderType, headerValue));
        }

        return headers;
    }

    private static List<String> parseHeader(final String line) {
        final List<String> header = List.of(line.split(COLON_LETTER));
        validateHeader(header);
        return header;
    }

    private static void validateHeader(final List<String> header) {
        if (header.size() < 2) {
            throw new IllegalArgumentException("요청 정보가 잘못되었습니다.");
        }
    }

    public boolean contains(final String httpHeaderType) {
        return headers.containsKey(httpHeaderType);
    }

    public HttpHeader get(final String httpHeaderType) {
        return headers.get(httpHeaderType);
    }

    public Set<String> keySet() {
        return headers.keySet();
    }

    public void put(final String httpHeaderType, final HttpHeader httpHeader) {
        headers.put(httpHeaderType, httpHeader);
    }

    public void add(final HttpHeader httpHeader) {
        headers.put(httpHeader.getHttpHeaderType(), httpHeader);
    }
}
