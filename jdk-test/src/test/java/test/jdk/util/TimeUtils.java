package test.jdk.util;

import java.time.LocalDateTime;

/**
 * Created by zengbin on 2017/9/7.
 */
public class TimeUtils {
	public static String time(){
		LocalDateTime now = LocalDateTime.now();
		return (now.getMinute() + ":" + now.getSecond() + " -- ");
	}
}
