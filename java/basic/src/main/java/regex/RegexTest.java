package regex;


import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * research about regex
 */
public class RegexTest {

    @Test
    public void getMatcherValue() {
        Pattern pattern = Pattern.compile("/abc//code/*/1234");
        Matcher matcher = pattern.matcher("/abc/123/code/456/1234");
        if (matcher.matches()) {
            System.out.println("input string match the regex !");
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }
    }
}
