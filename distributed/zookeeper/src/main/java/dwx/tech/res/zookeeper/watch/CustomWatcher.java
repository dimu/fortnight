package dwx.tech.res.zookeeper.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * add research about zookeeper watcher.
 * we can implement {@link Watcher} to process watched event callback
 *
 * @author dwx
 */
public class CustomWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {

        System.out.println(watchedEvent.toString());

        switch (watchedEvent.getType()) {
            case NodeCreated:
                System.out.println("node create: " + watchedEvent.getPath());
                break;
            case NodeDataChanged:

                System.out.println("node data changed! ");
                break;
            default:
                System.out.println("don't capture any watcher event ");
        }
    }
}
