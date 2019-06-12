package java8.lamdascope;

import org.junit.Test;

/**
 * 本类用于测试lamda表达式对变量作用域的限制，
 * 本示例运行多次会有不同结果
 * @author dwx
 *
 */
public class LamdaScopeTest {

    //instance variable stored on the heap
    public volatile int number = 15;
    
    public Runnable r = () -> System.out.println(Thread.currentThread().getName() + ":" + number);
    
    /**
     * multi thread share the same mutable variable
     * @throws InterruptedException 
     */
    @Test
    public void printNumber() throws InterruptedException {
        new Thread(r).start();
     //   Thread.currentThread().sleep(1000);
        System.out.println(Thread.currentThread().getName());
        number = 16;
        new Thread(r).start();
    }
    
    /**
     * local variable live on the stack。
     */
    @Test
    public void testLocalVariable() {
        int num1 = 123;
        Runnable r1 = () -> System.out.println(Thread.currentThread().getName() + ":" +  num1);
        new Thread(r1).start();
//        num1 =124;
        
        
    }
}
