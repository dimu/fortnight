package nio.buffer;

import net.mindview.util.CountingGenerator;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;

/**
 * buffer test
 * @author dwx
 * @date 2020-07-02
 */
public class BufferTest {

    /**
     * try to instance ByteBuffer Object through constructor
     */
    @Test
    public void bufferPut() {
        /**
         * all ByteBuffer children class are package-access, can't instance through constructor function.
         */
        //ByteBuffer bf = new HeapByteBuffer();

        ByteBuffer bf  = ByteBuffer.allocate(10);

        bf.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e').put((byte)'f');
//        bf.putInt(97);
        bf.putChar('中');
        System.out.println(bf.toString());

        System.out.println(new String(bf.array()));

        /**
         * java.nio.BufferOverflowException as long data has 8 byte plus origin 6 byte equals 14 byte, exceed the
         * capacity 10
         */
//        bf.putLong(10L);

        bf.flip();
        System.out.println(bf.get());
        bf.flip();
        System.out.println((char)bf.get());
    }

    @Test
    public void manipulateFileBuffer() {
//        File file = new File("nio\buffertest.txt");
//        new FileInputStream(file).
    }
}
