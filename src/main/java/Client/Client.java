package Client;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 9108;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        try (Socket socket = new Socket(IP, PORT);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            log.info("Server.Connection successful");
            System.out.println(LocalTime.now().format(dtf) + " Enter your name!");
            Reader reader = new Reader(socket);
            reader.start();
            while (true) {
                dataOutputStream.writeUTF(bufferedReader.readLine());
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            log.error("Exception: ", e);
        }
    }
}
