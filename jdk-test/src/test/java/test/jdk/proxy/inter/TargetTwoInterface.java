package test.jdk.proxy.inter;

import java.util.List;

public interface TargetTwoInterface {
    List<?> findSomething();

    List<?> findSomethingByField(Integer field);
}
