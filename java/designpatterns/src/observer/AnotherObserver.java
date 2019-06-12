package observer;

public class AnotherObserver implements Observer {

    @Override
    public void notify(String sweet) {
        if (null != sweet && sweet.contains("queen")) {
            System.out.println("another new in London" + sweet);
        }

    }

}
