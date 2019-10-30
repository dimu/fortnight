package exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * try-with-resource test
 */
public class TryWithResourceTest {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader(
                "D:\\opt\\log\\stash\\admin\\admin.log"))) {
            while(br.read() != -1) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
