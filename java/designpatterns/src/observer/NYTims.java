package observer;

/**
 * one observer class 
 * @author dwx
 *
 */
public class NYTims implements Observer {

    /**
     * consumer notify message
     */
    @Override
    public void notify(String sweet) {
        if (null != sweet && sweet.contains("money")) {
            System.out.println("Breaking news in NY!" + sweet);
        }
    }

}
