package test.rpc.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 张少昆 on 2018/10/20.
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -3525616059756666062L;

    private int id;
    private String name;

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }
}
