package Opgave2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomSocket = new ServerSocket(6789);
        boolean firstTime = true;

        while(true){
            Socket connectionSocket = welcomSocket.accept();
            if (firstTime) {
                System.out.println("Vil du snakke med: " + connectionSocket.getLocalAddress());
                BufferedReader klientConnect = new BufferedReader(new InputStreamReader(System.in));
                String acceptOrDeny = klientConnect.readLine();
                if (acceptOrDeny.equals("Accept")) {
                    firstTime = false;
                }
            }
            if(!firstTime) {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                BufferedReader serverSentence = new BufferedReader(new InputStreamReader(System.in));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Fra klient: " + clientSentence);
                capitalizedSentence = serverSentence.readLine() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            } else {
                welcomSocket.close();
            }
        }

    }

}
