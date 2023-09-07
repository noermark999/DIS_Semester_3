package Opgave4UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener extends Thread {

    private Socket socket;

    public Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String tekst = input.readLine();
                if (tekst == null) {
                    input.close();
                }
                System.out.println(tekst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
