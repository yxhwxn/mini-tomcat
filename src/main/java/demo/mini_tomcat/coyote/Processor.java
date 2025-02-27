package demo.mini_tomcat.coyote;

import java.net.Socket;

public interface Processor {

    void process(Socket socket);
}
