package test.lombok;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 张少昆 on 2017/12/25.
 */
@Data //lombok注解
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;

    public static void main(String[] args){
        User user = new User();
        user.id = 77L;
        System.out.println(user.id);
        System.out.println(user.getId()); //it works!
        System.out.println(user); //我擦，这也行！看看class文件，很帅！
    }
}
