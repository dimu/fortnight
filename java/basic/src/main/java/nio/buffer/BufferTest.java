package nio.buffer;

import net.mindview.util.CountingGenerator;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.util.Arrays;

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
        bf.putChar('H');
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

    /**
     * flip 非常有用，防止非本次输入的数据泄露，通过指定limit为position与重置position为0达到相应目的
     * 例如，当填满的buffer需要输出到channel时，可以使用flip()后再输出
     * flip() 等价于buffer.limit(buffer.position()).position(0);
     */
    @Test
    public void flipTest() {
        ByteBuffer  bf = ByteBuffer.allocate(1024);
        bf.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e');
        bf.flip();

        System.out.println((char)bf.get(4));
        /**
         * exceed the limit range 5,will print java.lang.IndexOutOfBoundsException
         */
        System.out.println((char)bf.get(5));
    }

    /**
     * rewind function don't set the limit = position
     */
    @Test
    public void rewindTest() {
        ByteBuffer  bf = ByteBuffer.allocate(1024);
        bf.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e');
        bf.rewind();

        System.out.println((char)bf.get(4));
        /**
         * don't cause IndexOutOfBoundException as rewind function don't reset limit attribute.
         */
        System.out.println("second" + (char)bf.get(5));
    }

    /**
     * buffer provider view ability
     */
    @Test
    public void bufferViewTest() {
        ByteBuffer  bf = ByteBuffer.allocateDirect(1024);
        bf.putChar('a').putChar('b').putChar('c').putChar('d').putChar('e').putChar('f');

        /**
         * buffer b1 view, char data view as byte data
         */
        ByteBuffer bf1 = bf.asReadOnlyBuffer();
        /**
         * bf1 has it's own position, limit, mask attributes
         */
        bf1.flip();
        int count = bf1.remaining();
        for(int i = 0; i < count; i++) {
            System.out.println(i + ":" +  bf1.get(i));
        }

        bf.flip();
        /**
         * the change of bf will be visible to bf1
         */
        bf.putChar('f').putChar('e').putChar('d').putChar('c').putChar('b').putChar('a');
        count = bf1.remaining();
        for(int i = 0; i < count; i++) {
            System.out.println(i + ":" +  bf1.get(i));
        }
    }

    @Test
    public void compactTest() {

        ByteBuffer  bf = ByteBuffer.allocate(6);
        bf.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e').put((byte)'f');
        bf.rewind();
        bf.get();
        bf.get();

        bf.compact();

        /**
         * output is 4, the dead element is 'e' and 'f'
         */
        System.out.println("position:" + bf.position());

        bf.flip();
        for (int i = 0; i < bf.remaining(); i++) {
            System.out.println(i + ":" + (char) bf.get(i));
        }

        System.out.println((char)bf.get());
        /**
         * output:cdefef
         */
        System.out.println(new String(bf.array()));

    }
}
