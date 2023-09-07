package Opgave4UDP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Indtast hostname på server:");
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData,
                sendData.length, inetAddress, 11080);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String ipAddress = new String(receivePacket.getData());
        String[] ipAddressUB = ipAddress.split("\u0000");
        System.out.println(ipAddressUB[0]);

        System.out.println("Connecting to: " + ipAddressUB[0]);
        Socket newclientSocket = new Socket(ipAddressUB[0], 6789);

        Listener listener = new Listener(newclientSocket);
        listener.start();
        Writer writer = new Writer(newclientSocket);
        writer.start();

        //clientSocket.close();

    }

}
