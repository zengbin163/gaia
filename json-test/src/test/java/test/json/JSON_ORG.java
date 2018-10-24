package test.json;


import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import test.json.entity.Student;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 测试 org.json。
 * 注意，null值不输出。
 * <p>
 * 四种构造JSONObject对象的方式：直接put添加kv、使用map构造、使用javabean构造、使用json字符串构造！
 * 可惜的是，无法将json字符串转成java bean！
 * <p>
 * Created by 张少昆 on 2017/12/8.
 */
public class JSON_ORG {
    @Test
    public void usePut(){
        JSONObject json = new JSONObject();
        try{
            json.put("name", "王小二");
            json.put("age", 25.2);
            json.put("birthday", "1990-01-01");
            json.put("school", "蓝翔");
            json.put("major", new String[]{"理发", "挖掘机"}); //list,[]都可以
            json.put("has_girlfriend", false);
            json.put("car", (Object) null);//如何打印出来？
            json.put("house", (Boolean) null);//如何打印出来？
            json.put("comment", "这是一个注释");

            System.out.println(json.toString(4)); //缩进

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void useMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王小二");
        map.put("age", 25.2);
        map.put("birthday", "1990-01-01");
        map.put("school", "蓝翔");
        map.put("major", new String[]{"理发", "挖掘机"}); //list,[]都可以
        map.put("has_girlfriend", false);
        map.put("car", (Object) null);//如何打印出来？
        map.put("house", (Boolean) null);//如何打印出来？
        map.put("comment", "这是一个注释");

        JSONObject json = new JSONObject(map);
        System.out.println(json.toString(4)); //打印带缩进
    }

    @Test
    public void useBean(){
        Student student = new Student();
        student.setName("王小二");
        student.setAge(25.2);
        student.setBirthday("1990-01-01");
        student.setSchool("藍翔");
        student.setMajor(new String[]{"理发", "挖掘机"});
        student.setHas_girlfriend(false);
        student.setCar(null);
        student.setHouse(null);
        student.setComment("这是一个注释");

        JSONObject json = new JSONObject(student);
        System.out.println(json.toString(4));
    }

    @Test
    public void useString() throws URISyntaxException, IOException{
        URL resource = Student.class.getClassLoader().getResource("demo.json");
        // 使用path
        Path path = Paths.get(resource.toURI());
        Optional<String> content = Files.readAllLines(path).stream().reduce((a, b) -> a + System.lineSeparator() + b);
        if(content.isPresent()){
            JSONObject json = new JSONObject(content.get());
            // System.out.println(json.toString(4));
            if(json.has("name")){
                System.out.println(json.getString("name"));
            }
            if(!json.isNull("age")){
                System.out.println(json.getDouble("age"));
            }
            JSONArray major = json.getJSONArray("major");
            System.out.println(major.getString(0));
        }

    }

    @Test
    public void useString2() throws URISyntaxException, IOException{
        URL resource = Student.class.getClassLoader().getResource("demo.json");

        // 使用file
        File file = new File(resource.toURI());
        String lines = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(lines);
        System.out.println(jsonObject.toString(4));
    }

}