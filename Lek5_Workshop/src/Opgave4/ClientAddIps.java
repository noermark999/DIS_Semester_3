package Opgave4;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientAddIps {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", 11080);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(inFromServer.readLine());
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');

        System.out.print("Hostname: ");
        String key = inFromUser.readLine();
        outToServer.writeBytes(key + "\n");
        System.out.print("IP: ");
        String value = inFromUser.readLine();
        outToServer.writeBytes(value + "\n");


    }
}
