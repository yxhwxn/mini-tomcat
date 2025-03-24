package demo.mini_tomcat.jwa.controller.exception;

public class NotFoundControllerException extends RuntimeException {
    public NotFoundControllerException(final String message) {
        super(message);
    }
}
