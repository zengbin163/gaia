package test.datastructure.tree;

import org.junit.Test;
import test.datastructure.tree.pojo.BiTree;
import test.datastructure.tree.util.BiTreeUtils;

/**
 * Created by zengbin on 2018/3/25.
 */
public class BiTreeTest {

    @Test
    public void r1(){
        BiTree root = new BiTree(10);

        for(int i = 0; i < 20; i++){
            BiTree tmp = new BiTree(i);
//            BiTreeUtils.add(root, tmp);
        }

        System.out.println(root);

        System.out.println(BiTreeUtils.find(root, 17));
        System.out.println(BiTreeUtils.find(root, 21));
    }
}
