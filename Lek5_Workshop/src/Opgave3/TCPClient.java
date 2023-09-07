package Opgave3;

import java.io.IOException;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {

        Socket clientSocket = new Socket("10.10.132.248", 6789);

        Listener listener = new Listener(clientSocket);
        listener.start();
        Writer writer = new Writer(clientSocket);
        writer.start();

        //clientSocket.close();

    }

}
