package Server;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
class Connection extends Thread {
    private String name = "Unknown";
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Connection(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        log.info("Connection has been created");
    }

    @Override
    public void run() {
        try {
            name = dataInputStream.readUTF();
            log.info("Connected {}", name);
            String line;
            while (true) {
                line = this.dataInputStream.readUTF();
                log.info("reading...");
                for (Connection connection : Server.connections) {
                    if (!name.equals(connection.name)) {
                        connection.dataOutputStream.writeUTF(LocalTime.now().format(dtf) + " " + name + ": " + line);
                        connection.dataOutputStream.flush();
                    }
                }
            }
        } catch (IOException e) {
            log.error("Exception: ", e);
        }
    }
}
