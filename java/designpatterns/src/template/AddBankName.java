package template;

/**
 * template subclass
 * @author dwx
 *
 */
class AddBankName extends OnlineBanking {

    @Override
    protected String compile(String param) {
        return "ICBC" + param;
    }
    
    public static void main(String[] args) {
        new AddBankName().process();
    }
    
}