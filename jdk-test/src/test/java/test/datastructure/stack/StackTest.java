package test.datastructure.stack;

import org.junit.Test;

import java.util.Iterator;

public class StackTest {

    @Test
    public void r1() {
        Stack stack = new Stack(10);
        for (int i = 0; i < 3; i++) {
            System.out.println(">>> " + stack.push(i));
        }

        System.out.println(stack);

        System.out.println("pop >> " + stack.pop());
        System.out.println(stack);
    }

    @Test
    public void r2() {
        Stack stack = new Stack(10);
        for (int i = 0; i < 3; i++) {
            System.out.println(">>> " + stack.push(i));
        }

        System.out.println(stack);

        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(">>> " + iterator.next());
        }
    }
}
