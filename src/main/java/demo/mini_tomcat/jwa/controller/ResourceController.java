package demo.mini_tomcat.jwa.controller;

import demo.mini_tomcat.catalina.webutils.IOUtils;
import demo.mini_tomcat.catalina.webutils.Parser;
import demo.mini_tomcat.coyote.header.ContentType;
import demo.mini_tomcat.coyote.http11.controller.AbstractController;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;
import demo.mini_tomcat.jwa.exception.InternalException;

import java.io.IOException;
import java.net.URISyntaxException;

import static demo.mini_tomcat.coyote.HttpStatus.OK;
import static demo.mini_tomcat.coyote.HttpStatus.REDIRECT;
import static demo.mini_tomcat.coyote.header.HttpHeaderType.CONTENT_TYPE;
import static demo.mini_tomcat.coyote.header.HttpHeaderType.LOCATION;

public class ResourceController extends AbstractController {

    @Override
    public void doGet(final HttpRequest httpRequest,
                      final HttpResponse httpResponse) {
        setResource(httpRequest, httpResponse);
    }

    private void setResource(final HttpRequest httpRequest,
                             final HttpResponse httpResponse) {
        final String path = httpRequest.getStartLine().getPath();
        final String fileName = Parser.convertResourceFileName(path);
        setResourceByFileName(fileName, httpResponse);
    }

    protected void setResource(final String fileName,
                               final HttpResponse httpResponse) {
        setResourceByFileName(fileName, httpResponse);
    }

    private void setResourceByFileName(final String fileName,
                                       final HttpResponse httpResponse) {
        final String fileType = Parser.parseFileType(fileName);
        try {
            final String body = IOUtils.readResourceFile(fileName);
            httpResponse.setHttpStatus(OK);
            httpResponse.addHeader(CONTENT_TYPE.getValue(), ContentType.of(fileType) + ";charset=utf-8");
//            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setBody(body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new InternalException("서버 에러가 발생했습니다.");
        }
    }

    protected void setRedirectHeader(final HttpResponse httpResponse, final ResourceUrls resourceUrls) {
        httpResponse.setHttpStatus(REDIRECT);
        httpResponse.addHeader(LOCATION.getValue(), resourceUrls.getValue());
    }
}
