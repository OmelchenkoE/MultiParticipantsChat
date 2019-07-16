package Server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class Server {
    private static final int PORT = 9108;
    public static List<Connection> connections = new CopyOnWriteArrayList<Connection>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        log.info("Waiting for a connection");
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                log.info("Have a connection");
                Connection connection = new Connection(socket);
                connection.start();
                connections.add(connection);
                log.info("Connection.start");
            }
        } catch (Throwable t) {
            log.error("Exception: ", t);
        }
    }
}