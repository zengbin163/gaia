package test.datastructure.tree.util;

import test.datastructure.tree.pojo.BiTree;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zengbin on 2018/3/25.
 */
public class BiTreeUtils {

    /**
     * 插入新的树节点。算法不对，因为这样不是二叉树，而是偏科很严重的。。。
     * 原因则是，没有调整功能！
     *
     * @param root
     * @param some
     */
    @Deprecated
    public static void add_v0(BiTree root, BiTree some){
        if(root.getNum() == some.getNum()){
            System.out.println("---重复数值[" + some.getNum() + "]，抛弃！");
            return;
        }

        if(root.getNum() > some.getNum()){
            if(root.getLeft() == null){
                root.setLeft(some);
            } else{
                BiTreeUtils.add_v0(root.getLeft(), some);
            }
        } else{
            if(root.getRight() == null){
                root.setRight(some);
            } else{
                BiTreeUtils.add_v0(root.getRight(), some);
            }
        }
    }

    /**
     * v0的改进版本 - 要保证顺序！那就得每次插入的时候都检索一番！
     * 逻辑：将a插入b时，判断ab大小，再与b的子节点c相比较。
     * 将abc中中值作为父节点！其他两个分别作为子节点！TODO 待完成
     *
     * @param root
     * @param some
     */
    public static void add_v1(BiTree root, BiTree some){
        if(root.getNum() == some.getNum()){
            System.out.println("---重复数值[" + some.getNum() + "]，抛弃！");
            return;
        }

        if(root.getNum() > some.getNum()){
            if(root.getLeft() == null){
                root.setLeft(some);
            } else{
                BiTreeUtils.add_v1(root.getLeft(), some);
            }
        } else{
            if(root.getRight() == null){
                root.setRight(some);
            } else{
                BiTreeUtils.add_v1(root.getRight(), some);
            }
        }
    }

    /**
     * 从二叉树中查找给定的数值。找到则返回，否则返回null。
     *
     * @param root
     * @param val
     * @return
     */
    public static BiTree find(BiTree root, int val){
        AtomicInteger count = new AtomicInteger();//尼玛 应该用引用
        BiTree result;
        if(root.getNum() == val){
            System.out.println("层级[" + count.get() + "]");
            return root;
        }
        if(root.getNum() > val){
            if(root.getLeft() == null){
                return null;
            }
            result = BiTreeUtils.doFind(root.getLeft(), val, count);
        } else{
            if(root.getRight() == null){
                return null;
            }
            result = BiTreeUtils.doFind(root.getRight(), val, count);
        }

        if(result != null){
            System.out.println("层级[" + count.get() + "]");
        }
        return result;
    }

    public static BiTree doFind(BiTree root, int val, AtomicInteger count){
        count.incrementAndGet();
        if(root.getNum() == val){
            return root;
        }
        if(root.getNum() > val){
            if(root.getLeft() == null){
                return null;
            }
            return BiTreeUtils.doFind(root.getLeft(), val, count);
        }

        if(root.getRight() == null){
            return null;
        }
        return BiTreeUtils.doFind(root.getRight(), val, count);
    }
}
