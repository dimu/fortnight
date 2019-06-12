package chainofresponsibility;

/**
 * concrete chain of responsibility, used for adding header text
 * @author dwx
 *
 */
public class HeaderTextProcessing extends ProcessingObject<String> {

    @Override
    protected String handleWork(String input) {
        return "Hello everyone, this book is for children only! " + input;
    }

}
