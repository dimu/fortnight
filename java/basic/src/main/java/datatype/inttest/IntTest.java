package datatype.inttest;

import org.junit.Test;

/**
 * integer相关研究
 *
 */
public class IntTest {

    @Test
    public void intoverflow() {
        System.out.println("integer max value: " + 0x7fffffff);
        System.out.println("integer max value: " + 0x80000000);
        System.out.println(0xffffffff);

        System.out.println("integer max value: " + 0x80000001);
    }
}
