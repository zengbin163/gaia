package test.jdk.io;

import org.junit.Test;

import java.util.StringTokenizer;

/**
 * 看说明，这是一个历史遗留类，新版建议使用String#split！
 * <p>
 * Created by zengbin on 2018/5/8.
 */
public class StringTokenizerTest {
    String str = "1\tfake record1\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t\t0\t0\t0\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record1\n" +
                         "2\tfake record2\t2018-04-20 15:26:59\t2018-04-20 15:27:30\t2018-04-21 15:27:03\t2018-04-23 17:41:18\t0\t1\t0\tselect * from fake_log;\t/tmp/log/\t2\t319\tfake record2\n" +
                         "3\tfake record3\t2018-04-20 15:26:59\t2018-04-20 15:27:30\t2018-04-21 15:27:03\t\t0\t1\t0\tselect * from fake_log;\t/tmp/log/\t2\t319\tfake record3\n" +
                         "4\tfake record4\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t\t0\t0\t0\tselect * from hehe;\t/tmp/log/\t1\t319\tfake record4\n" +
                         "5\tfake record5\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-17 17:41:26\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t1\t319\tfake record5\n" +
                         "6\tfake record6\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-26 17:41:29\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record6\n" +
                         "7\tfake record7\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-26 17:41:29\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record7";

    @Test
    public void r1(){
        //注意，第二个参数可以是多个字符，每个字符都会被拆成一个分隔符
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\t", true);
        int i = stringTokenizer.countTokens();
        System.out.println(i);

//        System.out.println(stringTokenizer.nextToken("\t"));

        int count = 0;
        while(stringTokenizer.hasMoreTokens()){
            System.out.println(count++ + "\t: [" + stringTokenizer.nextToken() + "]");
        }
    }

    @Test
    public void r2(){
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\t", true);

        int count = 0;
        while(stringTokenizer.hasMoreElements()){
            System.out.println(count++ + "\t: [" + stringTokenizer.nextElement() + "]");
        }
    }
}
