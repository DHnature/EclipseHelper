package dayton;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {
	
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.print("IP: ");
		String ip = scanner.next();
		System.out.println();
		System.out.print("Port: ");
		int port = scanner.nextInt();
		System.out.println();
		System.out.print("File name: ");
		String fileName = scanner.next();
		System.out.println();
		scanner.close();
		
		Socket socket = new Socket(ip,port);
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
		byte[] buffer = new byte[1024];
		int count;
		InputStream is = socket.getInputStream();
		while((count = is.read(buffer)) >= 0) {
			os.write(buffer,0,count);
		}
		os.close();
		System.out.println("Received file.");
	}
}