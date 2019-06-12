package socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.0.22", 50070);
//		socket.getInputStream().r;
		System.out.println(socket.getChannel().isOpen());
		
	}
}
