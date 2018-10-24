package test.jdk.regex;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

/**
 * Created by 张少昆 on 2017/12/26.
 */
public class RegexTest {

    // Pattern pattern = Pattern.compile("^[13[0-9]|145|147|149|150|151|152|153|155|156|157|158|159|166|170|171|173|175|176|177|178|18[0-9]|198|199][0-9]{8}$");
    Pattern pattern = Pattern.compile("^((13\\d)|(145)|(147)|(149)|(150)|(151)|(152)|(153)|(155)|(156)|(157)|(158)|(159)|(166)|(170)|(171)|(173)|(175)|(176)|(177)|(178)|(18\\d)|(198)|(199))\\d{8}$");

    @Test
    public void colon(){
        String s = "k:v:value";
        String[] arr = s.split(":");
        System.out.println(String.join("--", arr));
    }

    @Test
    public void replaceAll(){
        String str = "abccccccc";
        String re = str.replaceAll("d", "_");
        System.out.println(re == str);
        System.out.println(str);
        System.out.println(re);
    }


    @Test
    public void r1(){
        // Matcher matcher = pattern.matcher("123");
        // System.out.println(matcher.matches());

        Set<String> set = new HashSet<>();
        set.add("01234567890");
        set.add("11234567890");
        set.add("201234567890");
        set.add("301234567890");
        set.add("401234567890");
        set.add("501234567890");
        set.add("601234567890");
        set.add("701234567890");
        set.add("801234567890");
        set.add("901234567890");
        set.forEach(e -> {
            Matcher matcher = pattern.matcher(e);
            System.out.println(e + ": " + matcher.matches());
        });

        LongStream stream = LongStream.range(0, 1000000000000L);
        // System.out.println(stream.filter(e -> {
        //     Matcher matcher = pattern.matcher(e + "");
        //     return matcher.matches();
        // }).count());

        // stream.filter(e -> {
        //     Matcher matcher = pattern.matcher(e + "");
        //     return matcher.matches();
        // }).findFirst().ifPresent(System.out::println);

        stream.filter(e -> {
            Matcher matcher = pattern.matcher(e + "");
            return matcher.matches();
        }).limit(10).forEach(System.out::println);
    }

    @Test
    public void r2(){
        Matcher matcher = pattern.matcher("13300000000");
        System.out.println(matcher.matches());
    }

    //TODO java正则的匹配是完全匹配，奇怪了，那^$有啥意义？起码在matches中没啥意义吧
    @Test
    public void r3(){
        System.out.println("abc".matches("\\w"));
        System.out.println("abc".matches("\\w+"));
        System.out.println("1abc5".matches("\\w+")); //TODO \w 等价于[a-zA-Z0-9_]

        System.out.println("1abc5".matches("[a-zA-Z]+"));
        System.out.println("1abc5".matches("^[a-zA-Z]+$"));

        System.out.println("abc123".matches("\\d$"));
        System.out.println("abc123".matches("$\\d"));
    }

    @Test
    public void r4(){
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if(m.find()){
            System.out.println("Found value: " + m.group(0)); //0是整体
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
            // System.out.println("Found value: " + m.group(4)); //越界
        } else{
            System.out.println("NO MATCH");
        }
    }

    @Test
    public void r5(){
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if(m.find()){
            int count = m.groupCount(); //Group zero denotes the entire pattern by convention. It is not included in this count.
            for(int i = 0; i <= count; i++){
                System.out.println(m.group(i));
            }
        } else{
            System.out.println("NO MATCH");
        }
    }

}
