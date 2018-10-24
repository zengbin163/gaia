package test.datastructure.queue;

import java.util.Iterator;

/**
 * 什么是队列？
 * 火车站买票，需要排队，这个队就是队列。
 * 队列有头有尾，有长度限制，且先进先出。
 * <p>
 * 那么最简单的队列怎么实现？其实就两种，一种是队首出队，后面的依次前移；一种是将队首后移。
 * 两者都有问题：前者效率低，需要频繁操作内存；后者空间利用率低。
 * <p>
 * 那么怎么做才能既考虑效率 又考虑空间呢？
 * 这就是环形队列！
 * <p>
 * 本例就是练习环形队列，不考虑并发、泛型等情况！
 * <p>
 * Created by zengbin on 2018/3/6.
 */
public class CircleQueue implements Iterable<Integer> {
    /*** 容量，指队列底层数组的长度 */
    private int capacity;
    /*** 队列头 */
    private int head = 0;
    /*** 队列尾 */
    private int tail = 0;

    /*** 队列底层的数组 */
    private int[] arr;

    /*** 队列中有效元素的个数 */
    private int size = 0;

    /**
     * 构造方法
     *
     * @param capacity
     */
    public CircleQueue(int capacity){
        this.capacity = capacity;
        arr = new int[capacity];
    }

    /**
     * 压入队列（队尾）
     *
     * @param value
     * @return
     */
    public CircleQueue push(int value){
        if(size != capacity){
            arr[tail] = value;
            tail = (tail + 1) % capacity;
            ++size;
            return this;
        }
        throw new RuntimeException("队列已满！");
    }

    /**
     * 弹出（队首）
     *
     * @return
     */
    public int pop(){
        if(size == 0){
            throw new RuntimeException("队列为空");
        }
        int tmp = arr[head];
        arr[head] = -256;
        head = (head + 1) % capacity;
        --size;
        return tmp;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CircleQueue@");
        sb.append(this.hashCode());
        sb.append('[');
        for(int i = 0; i < size; i++){
            sb.append(arr[(head + i) % capacity]);
            sb.append(',');
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    @Override
    public Iterator<Integer> iterator(){
        CircleQueueIterator iterator = new CircleQueueIterator();
        iterator.setTarget(this);
        return iterator;
    }

    /**
     * 本类专用迭代器。
     */
    class CircleQueueIterator implements Iterator<Integer> {
        private CircleQueue obj;
        private int head;
        private int size;

        public void setTarget(CircleQueue obj){
            this.obj = obj;
            this.head = obj.head;
            this.size = obj.size;
        }

        @Override
        public boolean hasNext(){
            if(this.obj.size == 0 || this.size == 0){
                return false;
            }
            return true;
        }

        @Override
        public Integer next(){
            System.out.println(">>> head = " + head);
            this.head = (this.head) % this.obj.capacity;
            int value = this.obj.arr[head];
            this.head++;
            this.size--;
            return value;
        }
    }
}
