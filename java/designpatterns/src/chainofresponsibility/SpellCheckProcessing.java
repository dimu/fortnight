package chainofresponsibility;

/**
 * concrete chain of responsibility, used for spell checking
 * @author dwx
 *
 */
public class SpellCheckProcessing extends ProcessingObject<String> {

    @Override
    protected String handleWork(String input) {
        return input.replace("labda", "lambda");
    }

}
