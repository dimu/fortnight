package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * subject class to encapsulate
 * @author dwx
 *
 */
public class Feed implements Subject {
    
    private final List<Observer> container = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        container.add(o);

    }

    @Override
    public void notifyObservers(String tweet) {
        container.forEach(item -> item.notify(tweet));
    }
    
    public static void main(String[] args) {
        
        Feed feed  = new Feed();
        feed.registerObserver(new NYTims());
        feed.registerObserver(new AnotherObserver());
        
        feed.notifyObservers("The queen said her favourite fruit is banana!");
        
    }

}
