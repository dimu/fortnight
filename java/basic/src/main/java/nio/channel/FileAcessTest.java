package nio.channel;


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * file channel 相关测试
 *
 * @author dwx
 * @Date 2020-08-06
 */
public class FileAcessTest {

    /**
     * file channel position is reflected from the underlying file descriptor.
     * @throws IOException
     */
    @Test
    public void sharedFileDescriptor() throws IOException {

        URL resource = FileAcessTest.class.getClassLoader().getResource("nio/buffertest.txt");
//        URL resource = FileAcessTest.class.getClassLoader().getResource("access/Cake.class");

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(resource.getFile()), "r");

        randomAccessFile.seek(1000);

        FileChannel fileChannel = randomAccessFile.getChannel();

        System.out.println(fileChannel.position());

        randomAccessFile.seek(500);

        System.out.println(fileChannel.position());

        fileChannel.position(100);

        System.out.println(randomAccessFile.getFilePointer());

    }

    /**
     * truncate chops off any data beyond the new size you specify
     */
    @Test
    public void truncateData() throws IOException {
        URL resource = FileAcessTest.class.getClassLoader().getResource("nio/buffertest.txt");

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(resource.getFile()), "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        System.out.println("file size:" + fileChannel.size());

        fileChannel.truncate(fileChannel.size() -1);

        fileChannel.close();

        randomAccessFile.close();
    }

    @Test
    public void forceData() throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\test.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        System.out.println(fileChannel.size());

//        fileChannel.force(true);

        System.out.println(fileChannel.size());
    }

    /**
     * file locks are intended for arbitrating file access at the process level.
     * Don't use for concurrent access between multiple Java threads.
     */
    @Test
    public void fileLockTest() {

    }

    /**
     * what will happen if you don't release the file lock
     * @throws IOException
     */
    @Test
    public void notReleaseFileLock() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\test.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock lock = fileChannel.lock();

        System.out.println(lock.isShared());

        while(true) {
            //loop forever, don't release the file lock
        }
    }
}
