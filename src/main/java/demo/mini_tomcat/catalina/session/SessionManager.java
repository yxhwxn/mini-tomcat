package demo.mini_tomcat.catalina.session;

import demo.mini_tomcat.coyote.header.HttpCookie;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public static void add(final Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public static HttpCookie createCookie() {
        return new HttpCookie("JSESSIONID", UUID.randomUUID().toString());
    }
}
