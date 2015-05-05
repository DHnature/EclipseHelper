package dayton;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class VideoServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket;
			Scanner in = new Scanner(System.in);
			System.out.print("Port: ");
			int port = in.nextInt();
			System.out.println();
			in.close();
			serverSocket = new ServerSocket(port);
			System.out.println("Connected to port " + port);
			byte[] byteArray = null;
			int bytesRead = 0;
			String receivedFile = null;
			while(true) {
				System.out.println("Listening for Clients...");
				Socket socket = serverSocket.accept();
				System.out.println("Accepted Client");
				if(receivedFile == null) {
					receivedFile = "received" + System.currentTimeMillis();
					BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(receivedFile));
					byte[] buffer = new byte[1024];
					int count;
					InputStream is = socket.getInputStream();
					try {
						while((count = is.read(buffer)) >= 0) {
							os.write(buffer,0,count);
						}
					} catch (Exception ex) {}
					os.close();
					System.out.println("Received file.");
				} else {
					int count;
					byte[] buffer = new byte[1024];
					OutputStream os = socket.getOutputStream();
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(receivedFile));
					try {
						while((count = is.read(buffer)) >= 0) {
							os.write(buffer,0,count);
							os.flush();
						}
					} catch (Exception ex) {}
					is.close();
					System.out.println("Sent file.");
					receivedFile = null;
				}
				socket.close();
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}
}