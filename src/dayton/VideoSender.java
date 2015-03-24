
package dayton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class VideoSender {

	public static void sendVideo() throws IOException {
		//Scanner scanner = new Scanner(System.in);
		//System.out.print("File name: ");
		//String fileName = scanner.next();
		String fileName = "C:/Users/Dayton Ellwanger/Desktop/test.txt";
		File sendFile = new File(fileName);
		System.out.println();
		//scanner.close();
		
		byte[] byteArray = new byte[(int) sendFile.length()];
		BufferedInputStream inputStream = new BufferedInputStream(
				new FileInputStream(sendFile));
		inputStream.read(byteArray,0,byteArray.length);
		inputStream.close();
		
		System.out.println("Connecting to Server...");
		Socket socket = new Socket("127.0.0.1",12345);
		OutputStream outputStream = socket.getOutputStream();
		System.out.println("Connected to Server.");
		outputStream.write(byteArray,0,byteArray.length);
		outputStream.flush();
		socket.close();
		
		System.out.println("Sent file.");
	}
}