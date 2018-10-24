package commons.test.lang3;

import commons.test.entity.Gender;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * TODO Enum是所有enum的父类。
 * TODO 奇怪的是，所有enum都有一个values方法，返回所有的实例，但是，就是找不到这个方法在哪！
 * <p>
 * Created by zengbin on 2018/1/29.
 */
public class EnumUtilsTest {
    @Test
    public void enumTest(){
        Gender gender = Gender.MALE;
        Gender male = Enum.valueOf(Gender.class, "MALE"); //默认name与变量一致
        System.out.println(male);

        System.out.println(gender.name());
        System.out.println(Arrays.toString(gender.values())); //TODO 这个方法，你妹的

    }

    @Test
    public void validTest(){
        boolean valid = EnumUtils.isValidEnum(Gender.class, "MALE");
        System.out.println(valid);

        long no = EnumUtils.generateBitVector(Gender.class, Gender.MALE, Gender.MALE, Gender.MALE, Gender.MALE);
        System.out.println(no);
        System.out.println(Long.toString(no, 2));
    }

    @Test
    public void get(){
        //返回一个可修改的list，永远非null
        List<Gender> enumList = EnumUtils.getEnumList(Gender.class);
        System.out.println(enumList);

        //返回一个可修改的map，永远非null
        Map<String, Gender> enumMap = EnumUtils.getEnumMap(Gender.class);
        System.out.println(enumMap);
    }

    @Test
    public void r(){
//        EnumUtils
    }

}
