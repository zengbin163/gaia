package test.jdk.nanana;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 哈 给人做个东西，结果。。。冏就是了
 *
 * Created by 张少昆 on 2017/12/11.
 */
public class SomeTest {
    private final double[] referFreq = {
            0.082, 0.015, 0.028, 0.043, 0.127,
            0.022, 0.02, 0.061, 0.07, 0.002,
            0.008, 0.04, 0.024, 0.067, 0.075,
            0.019, 0.001, 0.06, 0.063, 0.091,
            0.028, 0.01, 0.024, 0.002, 0.02,
            0.001
    };
    private final int[] counts = new int[26];
    private final String work = "d:/homework/encrypt";
    private final Map<Character, Integer> countMap = new HashMap<>(27, 1);
    private final Map<Character, Double> ratioMap = new HashMap<>(27, 1);
    private final Map<Character, Set<Character>> ref = new HashMap<>(27, 1);
    private final Map<Character, Set<Character>> ref2 = new HashMap<>(27, 1);
    private int totalNum = 0;
    private String encodedContent = null;
    private String decodedContent = null;

    @Before
    public void init(){
        for(char i = 'a'; i <= 'z'; i++){
            countMap.put(i, 0);
            ratioMap.put(i, 0.0d);
            ref.put(i, new HashSet<>());
            ref2.put(i, new HashSet<>());
        }
        System.out.println(countMap);
        System.out.println(ratioMap);
        System.out.println(ref);
        System.out.println(ref2);
    }

    @Test
    public void r1() throws IOException{
        //先统计
        File file = new File(work + "01.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null){
            line = line.toLowerCase();
            count(line);
            stringBuilder.append(line);
        }
        encodedContent = stringBuilder.toString();

        calcRatio();
        match();

        // System.out.println(ref.keySet().size());
        reverseRef();
        decode();
        System.out.println(decodedContent);
    }

    public void count(String content){
        char[] chars = content.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(countMap.containsKey(chars[i])){
                Integer value = countMap.get(chars[i]);
                countMap.replace(chars[i], ++value);
                ++totalNum;
            }
        }
    }

    public void calcRatio(){
        Set<Character> set = countMap.keySet();
        for(Character key : set){
            Integer count = countMap.get(key);
            ratioMap.replace(key, (count * 1.0d) / totalNum);
        }
        // System.out.println(totalNum);
        System.out.println(ratioMap);
    }

    public void match(){

        for(int i = 0; i < referFreq.length; i++){
            findNearest(i, referFreq[i]);
        }
        System.out.println(ref);
    }

    /**
     * 找出最接近的value，如果有两个，怎么办？而且，有异常数据怎么办？？？
     * <p>
     * 弄反了，应该反过来。
     *
     * @param index
     * @param value
     */
    public void findNearest(int index, double value){
        Set<Map.Entry<Character, Double>> entries = ratioMap.entrySet();
        double tmp = 1.0d;
        for(Map.Entry<Character, Double> entry : entries){
            Set<Character> set = ref.get((char) ('a' + index));
            if(Math.abs(Math.abs(entry.getValue() - value) - tmp) < 0.000000001){
                set.add(entry.getKey());
            } else if(Math.abs(entry.getValue() - value) < tmp){
                tmp = Math.abs(entry.getValue() - value);
                set.clear();
                set.add(entry.getKey());
            } /*else if(Math.abs(entry.getValue() - value) == tmp){
                set.add(entry.getKey());
            }*/
        }
    }

    public void reverseRef(){
        Set<Map.Entry<Character, Set<Character>>> entries = ref.entrySet();
        for(Map.Entry<Character, Set<Character>> entry : entries){
            Character key = entry.getKey();
            Set<Character> set = entry.getValue();
            for(Character character : set){
                Set<Character> set2 = ref2.get(character);
                set2.add(key);
            }
        }
        System.out.println(ref2);
    }


    public void decode(){
        decodedContent = encodedContent;
        for(char i = 'a'; i <= 'z'; i++){
            decodedContent = decodedContent.replace(i, ref2.get(i).iterator().next()); //Returns a string resulting from replacing all occurrences of oldChar in this string with newChar.
        }
    }

    public void replace(char[] arr, char oldChar, char newChar){

    }
}
