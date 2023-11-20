package Opgave4UDP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DNSThread extends Thread {
    private DatagramSocket serverSocket;
    private HashMap<String, String> adresser;

    public DNSThread(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.adresser = DNSDB.getAdresser();
    }

    @Override
    public void run() {
        try {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData,
                        receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                System.out.println(sentence);
                String[] hostname = sentence.split("\u0000");
                System.out.println(hostname[0]);
                String toClient = adresser.get(hostname[0]);

                sendData = toClient.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData,
                        sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
