package template;

/**
 * template demo
 * @author dwx
 *
 */
public abstract class OnlineBanking {
    public void process() {
        String param = "aa";
        String result  = compile(param);
        System.out.println(result);
    }
    
    /**
     * template method
     * @param param consumer string
     * @return reduce string
     */
    protected abstract String compile(String param);
}


