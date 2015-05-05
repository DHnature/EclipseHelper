
package dayton;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class VideoReceiver {
	// called by instructor process in response to XMPP message from student saying new video at server
	public static void receiveVideo() throws IOException {
		String baseName = VideoSender.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		baseName = baseName.replace("%20", " ");
		String fileName = baseName + "/../screencaptures/screencap-vid.mp4";
		
		File screencaps = new File(baseName + "/../screencaptures");
		if(!screencaps.exists()) {
			screencaps.mkdir();
		}
		
		System.out.println("Connecting to Server...");
		Socket socket = new Socket("152.2.129.144",26503);
		System.out.println("Connected to Server.");

		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
		byte[] buffer = new byte[1024];
		int count;
		InputStream is = socket.getInputStream();
		while((count = is.read(buffer)) >= 0) {
			os.write(buffer,0,count);
		}
		os.close();
		socket.close();
		System.out.println("Received file.");
	}
}