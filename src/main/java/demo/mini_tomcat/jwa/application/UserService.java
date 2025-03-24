package demo.mini_tomcat.jwa.application;

import demo.mini_tomcat.jwa.db.InMemoryUserRepository;
import demo.mini_tomcat.jwa.dto.UserLoginRequest;
import demo.mini_tomcat.jwa.dto.UserRegisterRequest;
import demo.mini_tomcat.jwa.exception.DuplicateUserException;
import demo.mini_tomcat.jwa.model.User;

public class UserService {

    private UserService() {
    }

    private static class UserServiceGenerator {
        private static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserServiceGenerator.INSTANCE;
    }

    public void save(final UserRegisterRequest userRegisterRequest) {
        validateExistUser(userRegisterRequest.getAccount());
        final User user = new User(userRegisterRequest.getAccount(), userRegisterRequest.getPassword(), userRegisterRequest.getEmail());
        InMemoryUserRepository.save(user);
    }

    private void validateExistUser(final String account) {
        if (InMemoryUserRepository.existByAccount(account)) {
            throw new DuplicateUserException();
        }
    }

    public void login(final UserLoginRequest userLoginRequest) {
        final User user = InMemoryUserRepository.getByAccount(userLoginRequest.getAccount());
        validateUserPassword(userLoginRequest.getPassword(), user);
    }

    private void validateUserPassword(final String password, final User user) {
        if (!user.checkPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 정확하지 않습니다. : " + password);
        }
    }
}
