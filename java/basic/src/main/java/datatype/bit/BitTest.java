package datatype.bit;

import org.junit.Test;

/**
 * bit 相关操作研究
 */
public class BitTest {

    /**
     *
     */
    @Test
    public void bitDefinition() {

        char num = 0b110111;

        short negativeNum = (short)0b1111111111111010;

        short positiveNum = (short)(~negativeNum +1);

        System.out.println(num);

        System.out.println(negativeNum);

        System.out.println(positiveNum);

        int negativeInt = 0x80000000;

        int buma = ~negativeInt + 1;

        System.out.println(buma);
    }
}
