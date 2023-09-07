package Opgave4UDP;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class DNSServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(11080);
        DNSDB.addAddress("Jakob", "10.10.132.123");

            DNSThread dnsThread = new DNSThread(serverSocket);
            dnsThread.start();

    }
}

