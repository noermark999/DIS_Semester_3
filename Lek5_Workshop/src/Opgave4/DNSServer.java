package Opgave4;

import jdk.net.Sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class DNSServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(11080);
        DNSDB.addAddress("Jakob", "10.10.132.123");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            DNSThread dnsThread = new DNSThread(clientSocket);
            dnsThread.start();
        }
    }
}

