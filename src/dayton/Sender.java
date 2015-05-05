package dayton;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("IP: ");
		String ip = scanner.next();
		System.out.println();
		System.out.print("Port: ");
		int port = scanner.nextInt();
		System.out.println();
		System.out.print("File name: ");
		File sendFile = new File(scanner.next());
		System.out.println();
		scanner.close();
		
		Socket socket = new Socket(ip,port);
		int count;
		byte[] buffer = new byte[1024];
		OutputStream os = socket.getOutputStream();
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(sendFile));
		while((count = is.read(buffer)) >= 0) {
			os.write(buffer,0,count);
			os.flush();
		}
		is.close();
		System.out.println("Sent file.");
	}
}