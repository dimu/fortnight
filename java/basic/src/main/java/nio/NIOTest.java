package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class NIOTest {

	@Test
	public void NioFileRead() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("d:\\README.md", "r");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);

			int bytesRead = fileChannel.read(buf);
			System.out.println(bytesRead);

			while (bytesRead != -1) {
				buf.flip();
				while (buf.hasRemaining()) {
					System.out.print((char) buf.get());
				}

				buf.compact();
				bytesRead = fileChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
