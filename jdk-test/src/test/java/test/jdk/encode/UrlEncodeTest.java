package test.jdk.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

public class UrlEncodeTest {

	@Test
	public void r() throws UnsupportedEncodingException {
		String str = "{\"head\":[{\"sysCode\":\"1001\",\"appCode\":\"2000\"}],\"biz\":[{\"orderId\":\"1\",totalFee:10}]}";
		String en = URLEncoder.encode(str, "utf-8");
		System.out.println(en);
	}
}
