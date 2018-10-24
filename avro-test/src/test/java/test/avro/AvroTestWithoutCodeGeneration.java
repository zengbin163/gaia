package test.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 不生成代码，直接使用GenericRecord。
 * 一直到运行时才会检查是否符合schema！
 * <p>
 * <p>
 * Created by 张少昆 on 2018/4/29.
 */
public class AvroTestWithoutCodeGeneration {
    File avsc = new File("src/main/avro/user.avsc");
    File avro = new File("users.avro");

    Schema schema = null;

    @Before
    public void init(){
        try{
            schema = new Schema.Parser().parse(avsc);//TODO 不同之处0
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void serialize(){
        // 1. 创建对象

        // Leave favorite color null
        GenericRecord user1 = new GenericData.Record(schema); //TODO 不同之处1
        user1.put("name", "Alyssa");
        user1.put("favorite_number", 256);

        GenericRecord user2 = new GenericData.Record(schema);
        user2.put("name", "Ben");
        user2.put("favorite_number", 7);
        user2.put("favorite_color", "red");

        //2. 序列化
        // Serialize user1 and user2 to disk
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);//TODO 不同之处2
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        try{
            dataFileWriter.create(schema, avro);
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
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
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema); //TODO 不同之处
        DataFileReader<GenericRecord> dataFileReader;
        try{
            dataFileReader = new DataFileReader<>(avro, datumReader);
            GenericRecord user = null;
            while(dataFileReader.hasNext()){
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
