package template;

import java.util.function.Function;

/**
 * change template design pattern with java8 lambda expression, more
 * concise and easy to use
 * @author dwx
 *
 */
public class TemplateWithLambda {
    
    public void process(Function<String, String> consumer) {
        String param = "aa";
        String result  = consumer.apply(param);
        System.out.println(result);
    }
    
    public static void main(String[] args) {
        new TemplateWithLambda().process((item) -> "ICBC" + item );
    }
}
