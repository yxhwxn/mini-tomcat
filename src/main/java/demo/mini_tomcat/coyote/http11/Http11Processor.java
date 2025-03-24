package demo.mini_tomcat.coyote.http11;

import demo.mini_tomcat.catalina.RequestMapper;
import demo.mini_tomcat.coyote.Processor;
import demo.mini_tomcat.coyote.http11.controller.Controller;
import demo.mini_tomcat.coyote.request.HttpRequest;
import demo.mini_tomcat.coyote.response.HttpResponse;
import demo.mini_tomcat.jwa.exception.UncheckedServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
            process(connection);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {

            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            final HttpRequest httpRequest = HttpRequest.from(bufferedReader);
            final Controller controller = RequestMapper.getControllerFrom(httpRequest);
            final HttpResponse httpResponse = new HttpResponse();
            controller.service(httpRequest, httpResponse);
            final String response = httpResponse.generateResponse();

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
