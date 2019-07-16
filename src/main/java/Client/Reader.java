package Client;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
class Reader extends Thread {
    private DataInputStream dataInputStream;

    Reader(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(dataInputStream.readUTF());
            } catch (IOException e) {
                log.error("Exception: ", e);
            }
        }
    }
}
