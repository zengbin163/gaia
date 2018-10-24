package test.datastructure.stack;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 栈，后进先出 LIFO。
 * <p>
 * 本例，数组从索引0开始，每次压入一个，栈顶就+1。
 */
public class Stack implements Iterable<Integer> {
    /*** 最大容量 */
    private int capacity;
    /*** 栈顶 */
    private int head = 0;
    /*** 栈中元素个数 TODO 注意，和head是等值的 */
    private int size = 0;

    /*** 底层数组 */
    private int[] arr;

    /**
     * 构造
     *
     * @param capacity
     */
    public Stack(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
    }

    /**
     * 压入栈，成功则返回压入的值；栈满则异常！
     *
     * @param value
     * @return
     */
    public int push(int value) {
        if (capacity == size) {
            throw new RuntimeException("栈已满");
        }
        arr[head++] = value;
        size++;
        return value;
    }

    public int pop() {
        if (size == 0 || head == 0) {
            throw new RuntimeException("栈中无内容");
        }
        int tmp = arr[head - 1];
        head--;
        size--;
        return tmp;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "capacity=" + capacity +
                ", head=" + head +
                ", size=" + size +
                ", arr=" + Arrays.toString(arr) +
                '}';
    }

    @Override
    public Iterator<Integer> iterator() {
        return new StackIterator(this);
    }

    class StackIterator implements Iterator<Integer> {

        private final Stack stack;
        private int head;//

        public StackIterator(Stack stack) {
            this.stack = stack;
            this.head = stack.head;
        }

        @Override
        public boolean hasNext() {
            if (this.stack.size == 0 || head == 0) {
                return false;
            }
            return true;
        }

        @Override
        public Integer next() {
            return this.stack.arr[(head--) - 1];
        }
    }
}
