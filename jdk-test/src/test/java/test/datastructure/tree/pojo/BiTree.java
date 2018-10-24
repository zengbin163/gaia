package test.datastructure.tree.pojo;

/**
 * 首先，tree都要有一个root！额，不必说了，默认对象就是root！
 * 其次，只考虑向下查找，不考虑向上查找。
 * <p>
 * Created by zengbin on 2018/3/25.
 */
public class BiTree {
    /*** left subtree*/
    private BiTree left;
    /*** right subtree*/
    private BiTree right;

    private int num;

    public BiTree(int num){
        this.num = num;
    }

    public BiTree getLeft(){
        return left;
    }

    public void setLeft(BiTree left){
        this.left = left;
    }

    public BiTree getRight(){
        return right;
    }

    public void setRight(BiTree right){
        this.right = right;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    @Override
    public String toString(){
        return "BiTree{" +
                       "left=" + left +
                       ", right=" + right +
                       ", num=" + num +
                       '}';
    }
}
