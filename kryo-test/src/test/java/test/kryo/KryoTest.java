package test.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Before;
import org.junit.Test;
import test.pojo.Person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by zengbin on 2018/4/29.
 */
public class KryoTest {
    Kryo kryo;
    Output output;
    Input input;

    @Before
    public void init() throws FileNotFoundException{
        kryo = new Kryo();
        output = new Output(new FileOutputStream("file.dat"));
        input = new Input(new FileInputStream("file.dat"));
    }

    //示意了单个对象的读写，注意读写的方法，是带有class的！
    @Test
    public void givenObject_firstSerialize_thenReadCorrectly(){
        Object someObject = "Some string";

        kryo.writeClassAndObject(output, someObject);//注意，写入class和对象！
        output.close();

        Object theObject = kryo.readClassAndObject(input);//注意，读出class和对象！
        input.close();

        assertEquals(theObject, "Some string");
    }

    //示意了多个对象的读写！注意读写的方法，不带class，所以需要手动指定class！
    @Test
    public void givenObjects_firstSerialize_thenReadCorrectly(){
        String someString = "Multiple Objects";
        Date someDate = new Date(915170400000L);

        kryo.writeObject(output, someString);//这里只写入对象！
        kryo.writeObject(output, someDate);
        output.close();

        String readString = kryo.readObject(input, String.class);//读出的时候就要指定class！
        Date readDate = kryo.readObject(input, Date.class);
        input.close();

        assertEquals(readString, "Multiple Objects");
        assertEquals(readDate.getTime(), 915170400000L);
    }

    @Test
    public void r1(){
//        kryo.addDefaultSerializer(class, serializer);
        System.out.println("getAsmEnabled: " + kryo.getAsmEnabled());
        System.out.println("getClassLoader: " + kryo.getClassLoader());
        System.out.println("getContext: " + kryo.getContext());
        System.out.println("getClassResolver: " + kryo.getClassResolver());
        System.out.println("getDepth: " + kryo.getDepth());
        System.out.println("getGenericsScope: " + kryo.getGenericsScope());
        System.out.println("getGraphContext: " + kryo.getGraphContext());
        System.out.println(kryo.isRegistrationRequired());

//        kryo.
    }

    @Test
    public void r2(){//TODO 每次都是Serializer的新实例？？？
        Serializer serializer = kryo.getSerializer(Date.class);
        Serializer defaultSerializer = kryo.getDefaultSerializer(Date.class);

        System.out.println(serializer);
        System.out.println(defaultSerializer);

        System.out.println(serializer == defaultSerializer);
    }

    @Test
    public void r3(){ //判断final类，以及创建对象
        System.out.println(kryo.isFinal(Date.class)); //fasle
        System.out.println(kryo.isFinal(String.class)); //true

        System.out.println(kryo.newInstance(Date.class)); //now
        System.out.println("[" + kryo.newInstance(String.class) + "]");

        kryo.setAutoReset(true);//默认就是true
//        kryo.reset(); //会被自动调用
    }

    @Test
    public void testCopy(){ //深拷贝，完全不同！
        String name = new String("john");
        Date date = new Date();

        Person person = new Person();
        person.name = name;
        person.birthday = date;

        Person copy = kryo.copy(person);

        System.out.println(person == copy);//false
        System.out.println(person.name == copy.name);//true nimei
        System.out.println(person.birthday == copy.birthday);//false
    }

    @Test
    public void testShallowCopy(){ //浅拷贝，内部引用一样！
        String name = new String("john");
        Date date = new Date();

        Person person = new Person();
        person.name = name;
        person.birthday = date;

        Person copy = kryo.copyShallow(person);

        System.out.println(person == copy);
        System.out.println(person.name == copy.name);
        System.out.println(person.birthday == copy.birthday);//true!
    }

    //writeClass  可以在输出中看到内容！
    @Test
    public void r4(){
        //Registration: Describes the Serializer and class ID to use for a class.
        Registration registration = kryo.writeClass(output, Person.class);//奇怪，写哪去了？
        Registration registration2 = kryo.writeClass(output, Person.class);
        output.close();//
        System.out.println(registration == registration2);//同一个！

        System.out.println(registration.getSerializer()); //FieldSerializer!!!
        System.out.println(registration.getType());

        // 简言之，就是写了什么就读什么！
        Registration registration1 = kryo.readClass(input);
        input.close();//
        System.out.println(registration1.getType());
        System.out.println(registration1.getInstantiator());
        System.out.println(registration1.getSerializer());
        System.out.println(registration1.getId());

    }

    //事实证明，writeClassAndObject就是writeClass & writeObject！
    @Test
    public void r5(){
        Person person = new Person();
        person.name = "我的名字";
        person.birthday = new Date();

        kryo.writeClassAndObject(output, person);
        output.close();//

        //readClass
        Registration registration = kryo.readClass(input);
        System.out.println(registration.getType());
        System.out.println(registration.getSerializer());
        System.out.println(registration.getId());
        System.out.println(registration.getInstantiator());

        //readObject
        Person obj = kryo.readObject(input, Person.class);
        System.out.println(obj);

        //上面两段，作用同下！
//        Person obj = (Person) kryo.readClassAndObject(input);
//        System.out.println(obj);
    }

}
