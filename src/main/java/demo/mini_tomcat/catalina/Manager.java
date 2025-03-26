package demo.mini_tomcat.catalina;

import demo.mini_tomcat.catalina.session.Session;

import java.io.IOException;

public interface Manager {
    void add(Session session);

    Session findSession(String id) throws IOException;

    void remove(Session session);
}
