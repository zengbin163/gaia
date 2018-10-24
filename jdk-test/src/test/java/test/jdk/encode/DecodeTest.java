package test.jdk.encode;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by zengbin on 2017/12/12.
 */
public class DecodeTest {
    private String str = "/user/huge/xx/.Trash";
    private String enc = "%2Fuser%2Faccount_www%2F.Trash=";

    @Test
    public void r1() throws UnsupportedEncodingException{
        String encode = URLEncoder.encode(str, "utf-8");
        System.out.println(encode);
    }

    @Test
    public void r2() throws UnsupportedEncodingException{
        String decode = URLDecoder.decode(enc, "utf-8");
        System.out.println(decode);
    }

    @Test
    public void r3(){
        String x = Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(x);
        String y = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(y);
    }

    @Test
    public void r35(){
        System.out.println((int)'^'); //94
        System.out.println((byte)'^'); //94
        System.out.println((char)94);
    }

}
