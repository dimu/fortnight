package interfaces;
//: interfaces/RandomDoubles.java

import java.util.*;

public class RandomDoubles {
    private static Random rand = new Random(47);

    public static void main(String[] args) {
        RandomDoubles rd = new RandomDoubles();
        for (int i = 0; i < 7; i++)
            System.out.print(rd.next() + " ");
    }

    public double next() {
        return rand.nextDouble();
    }
}
