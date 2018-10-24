package test.jdk.protect;

import org.junit.Test;

/**
 * Created by zengbin on 2017/10/28 0028.
 */
public class ProtectedTest {
	@Test
	public void r1(){
		new SamePkg().hi();
		SamePkg.hi();
	}
}

class SamePkg {


	protected static void hi(){
		System.out.println("hi from SamePkg!");
	}
}
