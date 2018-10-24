package test.avro;

import example.avro.User;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * As shown in this example, Avro objects can be created either by invoking a constructor directly or by using a builder.
 * Unlike constructors, builders will automatically set any default values specified in the schema.
 * Additionally, builders validate the data as it set, whereas objects constructed directly will not cause an error until the object is serialized. However, using constructors directly generally offers better performance, as builders create a copy of the datastructure before it is written.
 * <p>
 * (using a builder requires setting all fields, even if they are null)
 * <p>
 * Created by 张少昆 on 2018/4/29.
 */
public class AvroTestWithCodeGeneration {
    File file = new File("users.avro");

    @Test
    public void serialize(){
        // 无参构造器。注意，user1没有设置颜色！不报错！缺点是，不会检查是否符合schema要求！
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);

        // 带参构造器。缺点是，不会检查是否符合schema要求！
        User user2 = new User("Ben", 7, "red");

        // builder - 浪费资源（有副本），但会检查是否满足要求！即便是可为null的值也必须设置！
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null) //TODO using a builder requires setting all fields, even if they are null
                .build();

        // 序列化到磁盘
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
        try{
            dataFileWriter.create(user1.getSchema(), file);

            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                dataFileWriter.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deserialize(){
        // 从磁盘反序列化
        DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
        try{
            DataFileReader<User> dataFileReader = new DataFileReader<>(file, userDatumReader);
            User user = null;
            while(dataFileReader.hasNext()){
                // 重用user对象。 This saves us from
                // allocating and garbage collecting many objects for files with many items.
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
