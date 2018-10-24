package test.jdk.security;

import org.junit.Test;

/**
 * Created by 张少昆 on 2018/6/20.
 */
public class SecurityTest01 {

    @Test
    public void r1(){
        System.out.println(System.getProperty("java.home"));
    }

    //java.security.AccessControlException: access denied ("java.util.PropertyPermission" "java.home" "read")
    @Test
    public void r2(){
        System.out.println(System.getSecurityManager());//default is null
        //关键
        System.setSecurityManager(new SecurityManager());

        System.out.println(System.getProperty("java.home"));
    }

    @Test
    public void r3(){
        System.out.println(System.getSecurityManager());//default is null
        //关键
        System.setSecurityManager(new SecurityManager());
        //java.security.AccessControlException: access denied ("java.util.PropertyPermission" "java.security.policy" "write")
        System.setProperty("java.security.policy", "demo.policy");
        System.out.println(System.getSecurityManager());//

        System.out.println(System.getProperty("java.home"));
    }

}
