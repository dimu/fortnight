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
        Pattern pattern = Pattern.compile("^(?:/abc/)(.*)/code/(.*)/1234$");
        Matcher matcher = pattern.matcher("/abc/123/code/456/1234");
        if (matcher.matches()) {
            System.out.println("input string match the regex !");
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        } else {
            System.out.println("no match!");
        }
    }

    @Test
    public void getMatcherValue1() {
        Pattern pattern = Pattern.compile("^day\\((.*)\\)$");
        Matcher matcher = pattern.matcher("day(-12)");
        if (matcher.matches()) {
            System.out.println("input string match the regex !");
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        } else {
            System.out.println("no match!");
        }
    }

    @Test
    public void opposite() {
        System.out.println(-Integer.valueOf(-1).intValue());
        System.out.println(-Integer.valueOf(2).intValue());
    }

    @Test
    public void groupMatcherTest() {
        String source = "192.168.200.110 - - [30/Jun/2020:16:45:16 +0800] \"GET /favicon.ico HTTP/1.1\" 404 548 \"-\" \"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36\"";
        String[] arr = source.split(" ");

//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

        Pattern pattern = Pattern.compile("(.*) (.*) (.*) (\\[.*\\]) (\".*\")");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            System.out.println(matcher.group(5));
        } else {
            System.out.println("no found!");
        }
    }
}
