package math;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * range random research
 * @author dwx
 */
public class RandomInt {

    private static Random rand = new Random();

    public static void main(String[] args) {
        RandomInt randomInt = new RandomInt();
        for (int i = 0; i < 100; i++) {
            System.out.println(randomInt.next(7,10));
        }

        System.out.println("thread local random!");

        for (int i = 0; i < 100; i++) {
            System.out.println(randomInt.rangeRandom(7,10));
        }
    }

    /**
     * before jdk1.7 random range method
     * @param min start value
     * @param max end value
     * @return 返回的随机数
     */
    public int next(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * since jdk1.7 standard random range method
     * @param min start value
     * @param max end value
     * @return 返回的随机数
     */
    public int rangeRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }
}
