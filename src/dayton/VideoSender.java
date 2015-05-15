
package dayton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class VideoSender {

	public static void sendVideo() throws IOException {
		String baseName = VideoSender.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		baseName = baseName.replace("%20", " ");
		String fileName = baseName + "/../screencaptures/screencap-vid.mp4";
		System.out.println("Connecting to Server...");
		Socket socket = new Socket("152.2.129.144",26503);
		System.out.println("Connected to Server.");
		int count;
		byte[] buffer = new byte[1024];
		OutputStream os = socket.getOutputStream();
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileName));
		while((count = is.read(buffer)) >= 0) {
			os.write(buffer,0,count);
			os.flush();
		}
		is.close();
		socket.close();
		System.out.println("Sent file.");
	}
}