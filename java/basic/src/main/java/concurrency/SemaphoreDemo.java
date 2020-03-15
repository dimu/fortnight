package concurrency;

//: concurrency/SemaphoreDemo.java
// Testing the PoolSemaphore class
import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

// A task to check a resource out of a poolSemaphore:
class CheckoutTask<T> implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private PoolSemaphore<T> poolSemaphore;
  public CheckoutTask(PoolSemaphore<T> poolSemaphore) {
    this.poolSemaphore = poolSemaphore;
  }
  public void run() {
    try {
      T item = poolSemaphore.checkOut();
      print(this + "checked out " + item);
      TimeUnit.SECONDS.sleep(1);
      print(this +"checking in " + item);
      poolSemaphore.checkIn(item);
    } catch(InterruptedException e) {
      // Acceptable way to terminate
    }
  }
  public String toString() {
    return "CheckoutTask " + id + " ";
  }
}

public class SemaphoreDemo {
  final static int SIZE = 25;
  public static void main(String[] args) throws Exception {
    final PoolSemaphore<Fat> poolSemaphore =
      new PoolSemaphore<Fat>(Fat.class, SIZE);
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < SIZE; i++)
      exec.execute(new CheckoutTask<Fat>(poolSemaphore));
    print("All CheckoutTasks created");
    List<Fat> list = new ArrayList<Fat>();
    for(int i = 0; i < SIZE; i++) {
      Fat f = poolSemaphore.checkOut();
      printnb(i + ": main() thread checked out ");
      f.operation();
      list.add(f);
    }
    Future<?> blocked = exec.submit(new Runnable() {
      public void run() {
        try {
          // Semaphore prevents additional checkout,
          // so call is blocked:
          poolSemaphore.checkOut();
        } catch(InterruptedException e) {
          print("checkOut() Interrupted");
        }
      }
    });
    TimeUnit.SECONDS.sleep(2);
    blocked.cancel(true); // Break out of blocked call
    print("Checking in objects in " + list);
    for(Fat f : list)
      poolSemaphore.checkIn(f);
    for(Fat f : list)
      poolSemaphore.checkIn(f); // Second checkIn ignored
    exec.shutdown();
  }
} /* (Execute to see output) *///:~
