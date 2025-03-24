package demo.mini_tomcat.jwa.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
        super("중복된 유저가 존재합니다.");
    }
}
