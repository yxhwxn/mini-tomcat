package demo.mini_tomcat.coyote;

public enum HttpStatus {
    OK(200, "OK"),
    REDIRECT(302, "Found");

    private final int code;
    private final String message;

    HttpStatus(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
