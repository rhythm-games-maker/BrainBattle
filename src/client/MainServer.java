package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String [] args) {
		try {
			Socket clientSocket =new Socket("192.168.61.137",8888); 
			
			ReceiveThread re_thread = new ReceiveThread();
			re_thread.setSocket(clientSocket);
			
			SendThread se_thread = new SendThread();
			se_thread.setSocket(clientSocket);
			
			re_thread.start();
			se_thread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
