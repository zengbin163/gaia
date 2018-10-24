package test.jvm;

import java.util.Date;

/**
 * Created by 张少昆 on 2018/3/23.
 */
public class CloneTest implements Cloneable {
    private Date date;

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public String toString(){
        return "CloneTest{" +
                       "date=" + date +
                       '}';
    }

    public static void main(String[] args) throws CloneNotSupportedException{
        CloneTest cloneTest = new CloneTest();
        cloneTest.setDate(new Date());
        System.out.println(cloneTest);

        CloneTest clone = (CloneTest) cloneTest.clone();
        System.out.println(clone);

        System.out.println(clone == cloneTest); //不一样，可以理解
        System.out.println(clone.date == cloneTest.date); //尼玛，为毛一样呢
    }
}
