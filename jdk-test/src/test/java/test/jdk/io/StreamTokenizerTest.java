package test.jdk.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.nio.charset.Charset;

/**
 * Created by zengbin on 2018/5/8.
 */
public class StreamTokenizerTest {
    String str = "1\tfake record1\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t\t0\t0\t0\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record1\n" +
                         "2\tfake record2\t2018-04-20 15:26:59\t2018-04-20 15:27:30\t2018-04-21 15:27:03\t2018-04-23 17:41:18\t0\t1\t0\tselect * from fake_log;\t/tmp/log/\t2\t319\tfake record2\n" +
                         "3\tfake record3\t2018-04-20 15:26:59\t2018-04-20 15:27:30\t2018-04-21 15:27:03\t\t0\t1\t0\tselect * from fake_log;\t/tmp/log/\t2\t319\tfake record3\n" +
                         "4\tfake record4\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t\t0\t0\t0\tselect * from hehe;\t/tmp/log/\t1\t319\tfake record4\n" +
                         "5\tfake record5\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-17 17:41:26\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t1\t319\tfake record5\n" +
                         "6\tfake record6\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-26 17:41:29\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record6\n" +
                         "7\tfake record7\t2018-04-20 14:16:27\t2018-04-20 14:17:05\t2018-04-20 14:16:31\t2018-04-26 17:41:29\t0\t0\t1\tselect * from hehe;\t/tmp/log/\t2\t319\tfake record7";

    @Test
    public void r1(){
        byte[] bytes = str.getBytes(Charset.forName("utf-8"));

        StreamTokenizer streamTokenizer1 = new StreamTokenizer(new ByteArrayInputStream(bytes));//TODO 被遗弃了，建议使用字符流！
        StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes))));
//        streamTokenizer.commentChar('a');//就一个字符，不够吧
        streamTokenizer.eolIsSignificant(true);//是否将换行符当作token。
        streamTokenizer.lineno();//line no.
        streamTokenizer.lowerCaseMode(true);//自动小写
        try{
            streamTokenizer.nextToken();
        } catch(IOException e){
            e.printStackTrace();
        }
        streamTokenizer.ordinaryChar('.');//将某个字符就是作为这个字符，而非其他意义的
        streamTokenizer.ordinaryChars(33, 66);//将范围内的所有字符都作为本身
        streamTokenizer.parseNumbers();//指出需要解析数字（会将0-9，还有[.][-]都作为numeric来解析）。
        streamTokenizer.pushBack();//
        streamTokenizer.quoteChar('\'');//
        streamTokenizer.slashSlashComments(true);//是否开启C++风格注释
        streamTokenizer.slashStarComments(true);//是否开启C风格注释
        streamTokenizer.whitespaceChars(67, 99);//设置哪些是空白字符
        streamTokenizer.wordChars(100, 111); //设置word的组成元素

        streamTokenizer.resetSyntax();//TODO 重置后，所有的字符都是原本的意思（不存在 white space、 alphabetic、 numeric、 string quote、 and comment character的分类。）
    }
}
