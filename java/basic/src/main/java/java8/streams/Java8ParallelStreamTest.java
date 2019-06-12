package java8.streams;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Java8ParallelStreamTest {

    /**
     * 多线程求和,由于特殊环境，速度最慢
     * 1. genertates boxed objects,which hava to be unboxed before they can be added
     * 2. iterate is difficult to divide into independent chunks to execute in parallel
     * @param n 求和
     * @return 返回求和值
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i+1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }
    
    /**
     * 改进的求和操作
     * 1. 避免开箱操作，采用LongStream来进行封装
     * 2. 避免采用iterator，而是采用rangeClosed方法，便于快速分块
     * @param n 参数
     * @return 求和值
     */
    public static long improveParallelSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }
    
    /**
     * 单线程求和
     * @param n 
     * @return
     */
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i+1)
                .limit(n)
                .reduce(0L, Long::sum);
    }
    
    /**
     * java8以前模式
     * @param n
     * @return
     */
    public static long preSum(long n) {
        long total = 0L;
        for(int i = 1; i <= n; i++) {
            total += i;
        }
        return total;
    }
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        System.out.println(parallelSum(100000000));
//        System.out.println(sequentialSum(100000000));
        System.out.println(preSum(1000000000));
//        System.out.println(improveParallelSum(10000000000));
        long end = System.currentTimeMillis();
        System.out.println("execute time:" + (end-start));
        
    }
}
