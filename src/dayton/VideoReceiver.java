
package dayton;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class VideoReceiver {
	
	public static void receiveVideo() throws IOException {
		System.out.println("Connecting to Server...");
		Socket socket = new Socket("127.0.0.1",12345);
		InputStream inputStream = socket.getInputStream();
		System.out.println("Connected to Server.");
		byte[] byteArray = new byte[10240];
		int bytesRead = inputStream.read(byteArray,0,byteArray.length);
		socket.close();
		
		//Scanner scanner = new Scanner(System.in);
		//System.out.print("File name: ");
		//String fileName = scanner.next();
		String fileName = "C:/Users/Dayton Ellwanger/Desktop/received.txt";
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(fileName));
		System.out.println();
		//scanner.close();
		
		outputStream.write(byteArray,0,bytesRead);
		outputStream.close();
		
		System.out.println("File received.");
	}
}