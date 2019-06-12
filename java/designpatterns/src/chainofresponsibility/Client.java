package chainofresponsibility;

/**
 * client use concrete chain 
 * @author dwx
 *
 */
public class Client {

    public static void main(String[] args) {
        HeaderTextProcessing p1 = new HeaderTextProcessing(); 
        SpellCheckProcessing p2 = new SpellCheckProcessing();
        
        p1.setSuccessor(p2);
        String r = p1.handle("Aren't labda intresting？");
        System.out.println(r);
    }

}
