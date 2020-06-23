package dimu.tech.res.spark.common;

/**
 * 标准的业务处理接口
 * @param <T>
 */
public interface Processor<T> {

    boolean process(T t) throws CaptureException;

    boolean stopOrPause();
}
