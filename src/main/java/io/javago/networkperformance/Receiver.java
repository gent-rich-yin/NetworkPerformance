package io.javago.networkperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Service
@Slf4j
public class Receiver {
    public Receiver() {

    }

    public void receive(int port) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ServerSocket(port).accept().getInputStream()))) {
            while(true) {
                log.info(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
