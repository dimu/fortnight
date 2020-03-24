package classloader;

public class StaticBlockTest {

    static {
        System.out.println("class step into static block");
    }

    public static void main(String[] args) {
        try {
            System.out.println(StaticBlockTest.class.toString());
            ClassLoader.getSystemClassLoader().loadClass("\\classloader\\StaticBlockTest.class");
            Class.forName(StaticBlockTest.class.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
