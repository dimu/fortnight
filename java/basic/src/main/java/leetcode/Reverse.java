package leetcode;


import org.junit.Test;

/**
 * 给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *  
 *
 * 示例 1：
 *
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 *
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 *
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 *
 * 输入：x = 0
 * 输出：0
 *  
 *
 * 提示：
 *
 * -231 <= x <= 231 - 1
 *
 */
public class Reverse {

    @Test
    public void testSignedInt() {
        int x = -12;
        int divide = x/10;
        int mode = x%10;
        System.out.println(divide);

        System.out.println(reverse(1534236469));
    }

    /**
     * 注意点：
     * 1.有符号数的操作，求模与求余都是带符号的
     * 2.整数溢出如何解决
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        //余数
        long result = 0;
        do {
            result = result * 10 + x%10;
            x = x / 10;
        } while (x != 0);

        return result < Integer.MIN_VALUE || result > Integer.MAX_VALUE ? 0 : (int)result;
    }
}
