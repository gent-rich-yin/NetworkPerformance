package io.javago.networkperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
public class Main {
    @Autowired
    private Sender sender;
    @Autowired
    private Receiver receiver;
    private static String role;
    private static String host;
    private static int port;

    public static void main(String[] args) {
        role = args[0];
        host = args[1];
        port = Integer.parseInt(args[2]);
        new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if(role.equalsIgnoreCase("receiver")) {
            receiver.receive(port);
        } else if (role.equalsIgnoreCase("sender")) {
            sender.send(host, port);
        } else {
            log.error("Invalid role: " + role);
        }
    }

}
