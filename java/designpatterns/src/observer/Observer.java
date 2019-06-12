package observer;

/**
 * The observer design pattern is a common solution when an object needs to automatically notify a
 * list of other objects when some event happen, eg a state change.   
 * @author dwx
 *
 */
public interface Observer {
    
    /**
     * notify observer message come
     * @param sweet
     */
    void notify(String sweet);
    
}
