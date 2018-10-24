package test.guava.precond;

import org.junit.Test;

import com.google.common.base.Preconditions;

public class GuavaPreconditionTest {

	@Test
	public void r1() {
		int count = -100;
		// 不满足就异常
		Preconditions.checkArgument(count > 0, "must be positive: %s", count);
	}

	@Test
	public void r2() {
		int count = -100;
		try {
			// 输出异常看看，原来是runtime异常
			Preconditions.checkArgument(count > 0, "must be positive: %s", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
