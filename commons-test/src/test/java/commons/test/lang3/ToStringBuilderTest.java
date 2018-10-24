package commons.test.lang3;


import commons.test.entity.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;

/**
 * ToStringBuilder，为了避免toString()导致的大量碎片字符串出现的。
 * TODO 注意，还可以new ToStringBuilder(this).append(..)...build()!!!  可以用在toString()中！
 * <p>
 * <p>
 * Created by zengbin on 2017/12/11.
 */
public class ToStringBuilderTest {
    private User user;

    @Before
    public void init(){
        user = new User();
        user.setName("小明");
        user.setAge(17);
        user.setAddress("明珠大道");
        user.setSth("呵呵，不重要的东西");
    }

    @Test
    public void r1(){
        String str = ToStringBuilder.reflectionToString(this.user);
        System.out.println("格式[default]: " + str);

        str = ToStringBuilder.reflectionToString(user, ToStringStyle.JSON_STYLE);
        System.out.println("格式[JSON_STYLE]: " + str);

        str = ToStringBuilder.reflectionToString(user, ToStringStyle.JSON_STYLE, true);
        System.out.println("格式[JSON_STYLE,true]: " + str);

        str = ToStringBuilder.reflectionToString(user, ToStringStyle.DEFAULT_STYLE);
        System.out.println("格式[DEFAULT_STYLE]: " + str);
        str = ToStringBuilder.reflectionToString(user, ToStringStyle.SIMPLE_STYLE);
        System.out.println("格式[SIMPLE_STYLE]: " + str);
        str = ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE);
        System.out.println("格式[MULTI_LINE_STYLE]: " + str);
        str = ToStringBuilder.reflectionToString(user, ToStringStyle.SHORT_PREFIX_STYLE);
        System.out.println("格式[SHORT_PREFIX_STYLE]: " + str);
        str = ToStringBuilder.reflectionToString(user, ToStringStyle.NO_CLASS_NAME_STYLE);
        System.out.println("格式[NO_CLASS_NAME_STYLE]: " + str);
        str = ToStringBuilder.reflectionToString(user, ToStringStyle.NO_FIELD_NAMES_STYLE);
        System.out.println("格式[NO_FIELD_NAMES_STYLE]: " + str);

    }

}