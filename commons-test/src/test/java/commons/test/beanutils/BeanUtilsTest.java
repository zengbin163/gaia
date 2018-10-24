package commons.test.beanutils;

import commons.test.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 张少昆 on 2017/12/11.
 */
public class BeanUtilsTest {
    private User user;

    @Before
    public void init(){
        user = new User();
        user.setName("小明");
        user.setAge(17);
        user.setAddress("明珠大道");
        user.setSth("呵呵，不重要的东西");
    }

    //克隆bean
    @Test
    public void cloneBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        User u = (User) BeanUtils.cloneBean(user);
        System.out.println(u);
        System.out.println(u.getName() == user.getName()); //引用的，还是引用
    }

    //改变属性值
    @Test
    public void copyProperty() throws InvocationTargetException, IllegalAccessException{
        BeanUtils.copyProperty(user, "name", "小王");
        System.out.println(user);
    }

    //和上面的一样啊
    @Test
    public void setProperty() throws InvocationTargetException, IllegalAccessException{
        BeanUtils.setProperty(user, "name", "小李");
        System.out.println(user);
    }

    // 使用map填充bean
    @Test
    public void populate() throws InvocationTargetException, IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王小二");
        map.put("age", 13);
        map.put("address", "黄金大道");
        map.put("sth", "shenmewanyier");
        User user = new User();
        BeanUtils.populate(user, map);
        System.out.println(user);
    }

    // 使用bean获取map
    @Test
    public void describe() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException{
        Map<String, String> describe = BeanUtils.describe(user);
        System.out.println(describe);
    }
}
