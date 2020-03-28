package connectionpool;

/**
 *
 * thread pool
 *
 * @param <Job> the job thread
 *
 * @author dwx
 */
public interface ThreadPool<Job extends Runnable>{
    /**
     * execute a job thread
     * @param job
     */
    void execute(Job job);

    /**
     * shutdown the current thread pool
     */
    void shutDown();

    /**
     * add the parameter number workers
     * @param num
     */
    void addWorkers(int num);

    /**
     * reduce the num workers
     * @param num
     */
    void removeWorker(int num);

    /**
     * return the waiting job number
     * @return the wating job number
     */
    int getJobSize();
}
