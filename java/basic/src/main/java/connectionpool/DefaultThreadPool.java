package connectionpool;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * the default thread pool realization
 * @param <Job>
 *
 * @author dwx
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static  final  int MAX_WORKERS_SIZE = 10;

    private static final int MIN_WORKERS_SIZE = 1;

    private static final int DEAFAULT_WORKER_SIZE = 5;

    private List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    private LinkedList<Job> jobs = new LinkedList<>();

    private int workerNum = DEAFAULT_WORKER_SIZE;

    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initialWorkers(DEAFAULT_WORKER_SIZE);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKERS_SIZE ? MAX_WORKERS_SIZE : num < MIN_WORKERS_SIZE ? MIN_WORKERS_SIZE : num;
        initialWorkers(workerNum);
    }

    private void initialWorkers(int workerNum) {
        for (int i = 0; i< workerNum; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread  = new Thread(worker, "Thread-pool-worker" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (null != job) {
            synchronized (jobs) {
                /**
                 * add the job to the waiting list, add notify worker to consume the waiting list
                 */
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutDown() {
        workers.forEach(item -> item.shutdown());
    }

    @Override
    public void addWorkers(int num) {

    }

    @Override
    public void removeWorker(int num) {

    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable {

        private  volatile  boolean running  = true;

        @Override
        public void run() {
            while (running) {
                synchronized (jobs)
            }
        }

        private void shutdown() {
            running = false;
        }
    }
}
