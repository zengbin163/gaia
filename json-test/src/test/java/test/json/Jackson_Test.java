package test.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.Before;
import org.junit.Test;
import test.json.entity.Student;
import test.json.entity.StudentWithDate;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * jackson自己注册了默认的日期格式！DateFormat：StdDateFormat.instance，而StdDateFormat.instance的DEFAULT_LOCALE = Locale.US。
 * <p>
 * <p>
 * Created by zengbin on 2017/12/11.
 */
public class Jackson_Test {
    private StudentWithDate student; //TODO 注意这里的实体类，跟测试中的实体类可能不同
    private ObjectMapper objectMapper;
    public static final String value = "{\n" +
                                               "  \"name\": \"王小二\",\n" +
                                               "  \"age\": 25.2,\n" +
                                               "  \"birthday\": \"1990-01-01\",\n" +
                                               "  \"school\": \"蓝翔\",\n" +
                                               "  \"major\": [\n" +
                                               "    \"理发\",\n" +
                                               "    \"挖掘机\"\n" +
                                               "  ],\n" +
                                               "  \"has_girlfriend\": false,\n" +
                                               "  \"car\": null,\n" +
                                               "  \"house\": null,\n" +
                                               "  \"comment\": \"这是一个注释\"\n" +
                                               "}";

    @Before
    public void init(){
        student = new StudentWithDate();
        student.setName("王小二");
        student.setAge(25.2);
        student.setBirthday(new Date());
        student.setSchool("藍翔");
        student.setMajor(new String[]{"理发", "挖掘机"});
        student.setHas_girlfriend(false);
        student.setCar(null);
        student.setHouse(null);
        student.setComment("这是一个注释");

        objectMapper = new ObjectMapper();
    }

    @Test
    public void writeValueAsString() throws JsonProcessingException{
        String str = objectMapper.writeValueAsString(student);
        System.out.println(str);//TODO 注意日期
    }

    @Test
    public void readToStudent() throws IOException, URISyntaxException{
        URL resource = Student.class.getClassLoader().getResource("demo.json");
//        Path path = Paths.get(resource.toURI());
//        List<String> strings = Files.readAllLines(path);
//        System.out.println(strings);
        Student stu = objectMapper.readValue(new File(resource.getFile()), Student.class); //奇怪，怎么换了电脑就找不到文件了？
        System.out.println(stu);
    }

    @Test
    public void readToStudent2() throws IOException, URISyntaxException{
        Student stu = objectMapper.readValue(value, Student.class); //
        System.out.println(stu);
    }

    @Test
    public void readToStudent3() throws IOException, URISyntaxException{
        StudentWithDate stu = objectMapper.readValue(value, StudentWithDate.class); // it works!就是说默认注册了日期格式，是us格式的！
        System.out.println(stu);
    }

    //TODO 任何json字符串都可以转成map！注意，不要使用Map.class，要使用具体的实现类。
    @Test
    public void readToMap() throws IOException{
        URL resource = Student.class.getClassLoader().getResource("demo.json");
        HashMap stu = objectMapper.readValue(new File(resource.getFile()), HashMap.class);
        System.out.println(stu);

        System.out.println(stu.keySet());
    }

    // 默认的日期格式
    @Test
    public void defaultDateFormat(){
        System.out.println(objectMapper.getDateFormat());
        System.out.println(objectMapper.getDateFormat().format(new Date())); //2017-12-18T02:50:01.660+0000
    }

    // 自定义日期格式，建议使用customDateFormat2的形式！
    @Test
    public void customDateFormat(){
        System.out.println(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        objectMapper.setDateFormat(new StdDateFormat(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")), Locale.CHINA));

        System.out.println(objectMapper.getDateFormat());
        System.out.println(objectMapper.getDateFormat().format(new Date())); //2017-12-18T10:49:43.449+0800
    }

    // 自定义日期格式2，推荐
    @Test
    public void customDateFormat2(){
        StdDateFormat sdf = StdDateFormat.instance;
        sdf = sdf.withTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"))).withLocale(Locale.CHINA);

        objectMapper.setDateFormat(sdf);

        System.out.println(objectMapper.getDateFormat());
        System.out.println(objectMapper.getDateFormat().format(new Date())); //2017-12-18T10:49:43.449+0800
    }

    // 自定义一些特性
    @Test
    public void customSettings() throws JsonProcessingException{
        //是否接受空数组作为null对象
        DeserializationFeature f1 = DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT;
        boolean b1 = true;
        objectMapper.configure(f1, b1);

        //是否支持缩进
        SerializationFeature f2 = SerializationFeature.INDENT_OUTPUT;
        boolean b2 = true;
        objectMapper.configure(f2, b2);

//        objectMapper.getFactory().createGenerator(outStream, JsonEncoding.UTF8);//TODO 可以指定输出到什么地方！

        System.out.println(objectMapper.writeValueAsString(student));
    }


    @Test
    public void node() throws IOException{
        String test = "{\"results\":[{\"objectID\":357,\"geoPoints\":[{\"x\":504604.59802246094,\"y\":305569.9150390625}]},{\"objectID\":358,\"geoPoints\":[{\"x\":504602.2680053711,\"y\":305554.43603515625}]}]}";
        JsonNode node = objectMapper.readTree(test);   //将Json串以树状结构读入内存
        JsonNode contents = node.get("results");//得到results这个节点下的信息
        // TODO 注意，下面这样只能用于array node！
        for(int i = 0; i < contents.size(); i++){//遍历results下的信息，size()函数可以得到节点所包含的的信息的个数，类似于数组的长度
            System.out.println(contents.get(i).get("objectID").asInt()); //读取节点下的某个子节点的值
            JsonNode geoNumber = contents.get(i).get("geoPoints");
            for(int j = 0; j < geoNumber.size(); j++){   //循环遍历子节点下的信息
                System.out.println(geoNumber.get(j).get("x").asDouble() + " " + geoNumber.get(j).get("y").asDouble());
            }
        }
    }

    //以树的形式，逐个访问，不能越过！
    @Test
    public void node2() throws IOException{
        JsonNode root = objectMapper.readTree(value);
        // find values by, for example, using a JsonPointer expression:
        String text = root.at("/name").asText();
        System.out.println(text);

        System.out.println(root.size()); //

        System.out.println(root.get(0)); //TODO null?????  因为只能用于array node

        //TODO 非array node，需要使用field来获取
        System.out.println(root.get("comment").asText());
        System.out.println(root.get("age").asText());
    }

    @Test
    public void node3() throws IOException{
        String value = "{\n" +
                               "  \"name\": \"王小二\",\n" +
                               "  \"age\": 25.2,\n" +
                               "  \"birthday\": \"1990-01-01\",\n" +
                               "  \"school\": \"蓝翔\",\n" +
                               "  \"major\": [\n" +
                               "    \"理发\",\n" +
                               "    \"挖掘机\"\n" +
                               "  ],\n" +
                               "  \"has_girlfriend\": false,\n" +
                               "  \"car\": null,\n" +
                               "  \"house\": null,\n" +
                               "  \"comment\": \"这是一个注释\",\n" +
                               "  \"address\": {\n" +
                               "    \"city\": \"北京\",\n" +
                               "    \"district\": \"朝阳\",\n" +
                               "    \"building\": \"乾坤大厦\"\n" +
                               "  }\n" +
                               "}";
        JsonNode jsonNode = objectMapper.readTree(value);
        // get(field)
        // System.out.println(jsonNode.get("address").asText());
        // TODO at(tree_path)!!! 通过树的路径查找内容！
        System.out.println(jsonNode.at("/address/building").asText());
    }
}
