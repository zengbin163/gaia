package test.spring.reload;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * TODO 用来测试Class的reload。
 * TODO 结果表明，只有实现了AbstractRefreshableApplicationContext的类才可以多次refresh()！--也就是重新加载类。
 * <p>
 * TODO 好在，除了web项目可以同时支持XML和注解形式，其他的都仅支持XML形式！
 * <p>
 * Created by zengbin on 2018/3/15.
 */
@Component
public class Reload {
    static{
        System.out.println("--------@" + LocalDateTime.now());
    }
}
