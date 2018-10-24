package test.json;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import test.json.entity.Student;
import test.json.entity.StudentWithDate;
import test.json.entity.StudentWithList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * Created by zengbin on 2017/12/8.
 */
public class Gson_Test {
    private Student student;
    @Before
    public void init(){
        student = new Student();
        student.setName("王小二");
        student.setAge(25.2);
        student.setBirthday("1990-01-01");
        student.setSchool("藍翔");
        student.setMajor(new String[]{"理发", "挖掘机"});
        student.setHas_girlfriend(false);
        student.setCar(null);
        student.setHouse(null);
        student.setComment("这是一个注释");
    }
    @Test
    public void r1(){
        Gson gson = new Gson();
        System.out.println(gson.toJson(student));
    }

    @Test
    public void r2(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting(); //
        gsonBuilder.setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field field){
                if(field.getName().equals("name")){
                    return "NAME";
                }
                return field.getName();
            }
        });
        Gson gson = gsonBuilder.create();
        System.out.println(gson.toJson(student));
    }

    //反序列化！！！
    @Test
    public void r3() throws IOException{
        String abs = Student.class.getClassLoader().getResource("demo.json").getFile();
        String content = FileUtils.readFileToString(new File(abs), StandardCharsets.UTF_8);

        Gson gson = new Gson();
        Student student = gson.fromJson(content, Student.class);
        System.out.println(student);
    }

    //TODO GsonBuilder设置日期格式
    @Test
    public void date() throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-dd-MM").create();

        String abs = Student.class.getClassLoader().getResource("demo.json").getFile();
        String content = FileUtils.readFileToString(new File(abs), StandardCharsets.UTF_8);

        StudentWithDate student = gson.fromJson(content, StudentWithDate.class);//
        System.out.println(student.getBirthday().toString());
    }
    @Test
    public void list() throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-dd-MM").create();

        String abs = Student.class.getClassLoader().getResource("demo.json").getFile();
        String content = FileUtils.readFileToString(new File(abs), StandardCharsets.UTF_8);

        StudentWithList student = gson.fromJson(content, StudentWithList.class);//
        System.out.println(student.getMajor());
        System.out.println(student.getMajor().getClass());
    }



}
