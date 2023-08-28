package SocketEks2023;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomSocket = new ServerSocket(6789);

		while(true){
			Socket connectionSocket = welcomSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			BufferedReader sentenceFromServer = new BufferedReader(new InputStreamReader(System.in));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Fra Client: " + clientSentence);
			capitalizedSentence = sentenceFromServer.readLine() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}

	}

}
