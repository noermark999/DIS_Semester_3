package Opgave4;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class DNSThread extends Thread{
    private Socket clientSocket;
    private HashMap<String, String> adresser;

    public DNSThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.adresser = DNSDB.getAdresser();
    }

    @Override
    public void run() {
        try {
            while (true) {

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                outToClient.writeBytes("Tast 1 for at tilføje adresse eller 2 for at finde ip" + "\n");
                String answer = inFromClient.readLine();
                if (answer.equals("1")) {
                    String key = inFromClient.readLine();
                    String value = inFromClient.readLine();
                    DNSDB.addAddress(key,value);
                } else {
                    outToClient.writeBytes(adresser + "\n");
                    String clientSentence = inFromClient.readLine();
                    System.out.println(clientSentence);
                    String toClient = adresser.get(clientSentence);
                    System.out.println(toClient);
                    outToClient.writeBytes(toClient + "\n");
                    clientSocket.close();
                }

            }
        }
        catch (IOException e) {
            e.getMessage();
        }
    }
}
