package demo.mini_tomcat.jwa;

import demo.mini_tomcat.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("tomcat server start.");
        final var tomcat = new Tomcat();
        tomcat.start();
    }

}
