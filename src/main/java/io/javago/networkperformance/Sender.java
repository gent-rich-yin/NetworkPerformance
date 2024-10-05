package io.javago.networkperformance;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class Sender {
    private final List<String> messages;
    private final static int N_MESSAGES = 1_000_000;

    public Sender() {
        messages = generateFakeMessages();
    }

    public void send(String host, int port) {
        long stime = System.currentTimeMillis();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new Socket(host, port).getOutputStream()))) {
            for (String m : messages) {
//                writer.write(m + System.lineSeparator());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        long ftime = System.currentTimeMillis();
        log.info("Took: " + (ftime - stime) + "ms, " + (((double)N_MESSAGES) * 1000 / (ftime - stime)) + " messages per second");
    }

    private List<String> generateFakeMessages() {
        var shakespeare = new Faker().shakespeare();
        return IntStream.range(0, N_MESSAGES).mapToObj(i -> shakespeare.romeoAndJulietQuote()).toList();
    }
}
