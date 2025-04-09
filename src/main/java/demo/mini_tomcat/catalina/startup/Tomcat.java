package demo.mini_tomcat.catalina.startup;

import demo.mini_tomcat.coyote.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Tomcat {

    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    public void start() {
        final Connector connector = new Connector(1, 10);
        connector.start();

        try {
            // make the application wait until we press any key.
            System.in.read();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("tomcat server stop.");
            connector.stop();
        }
    }
}
