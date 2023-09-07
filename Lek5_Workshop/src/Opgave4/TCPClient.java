package Opgave4;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {

        Socket clientSocket = new Socket("localhost", 11080);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(inFromServer.readLine());
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String answer = inFromUser.readLine();
        outToServer.writeBytes(answer + "\n");
        System.out.println(inFromServer.readLine());
        System.out.println("Indtast hostname på server:");
        String sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        String ipAddress = inFromServer.readLine();
        System.out.println("Connecting to: " + ipAddress);
        Socket newclientSocket = new Socket(ipAddress, 6789);

        Listener listener = new Listener(newclientSocket);
        listener.start();
        Writer writer = new Writer(newclientSocket);
        writer.start();

        //clientSocket.close();

    }

}
