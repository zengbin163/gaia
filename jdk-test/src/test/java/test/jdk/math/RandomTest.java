package test.jdk.math;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

/**
 * 方法一：
 * 举例：1 20% 2 30% 3 50%
 * <p>
 * 可以将20个1，30个2 , 50 个3 随机放入数组中，然后随机1-100，拿出对应数字下标下的数字。
 * <p>
 * 方法二：
 * <p>
 * 按照概率进行区间划分
 * <p>
 * 10个数字1 2 代表1，3 4 5代表3， 6 7 8 9 10代表数字5，从而产生一定概率的随机数字
 * <p>
 * <p>
 * Created by zengbin on 2018/5/24.
 */
public class RandomTest {

    //先来个最简单的
    @Test
    public void r1(){
        for(int i = 0; i < 10; i++){
            System.out.println(roll());
        }

    }


    //最简单的实现。。真你妹聪明啊
    public String roll(){
        double random = Math.random();
        if(random < 0.8){
            return "1万聪";
        } else if(random < 0.95){
            return "1.5万聪";
        } else if(random < 0.98){
            return "2万聪";
        } else if(random < 0.99){
            return "3万聪";
        } else if(random < 0.995){
            return "5万聪";
        } else{
            return "10万聪";
        }
    }


    public int roll2(){
        return list.get(new Random().nextInt(list.size()));
    }

    //-------------------------------------------------------------------
    static ArrayList<Integer> list = null;

    /**
     * @param probs   所有的概率 - 注意，k代表值，v代表概率，总和不能超过1
     * @param precise 小数点后最多多少位
     */
    private void initProb(Map<Integer, Double> probs, int precise){
        if(list == null){
            int t = 1;
            for(int i = 0; i < precise; i++){
                t = t * 10;
            }
            int tmp = t;
            list = new ArrayList<>(tmp);

            probs.forEach((k, v) -> {
                ArrayList<Integer> tmpList = new ArrayList((int) (v * tmp));
                Collections.fill(tmpList, k);
                list.addAll(tmpList);
            });
        }
    }
    //-------------------------------------------------------------------

}
