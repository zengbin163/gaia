package c13;

/**
 * Created by 张少昆 on 2018/4/18.
 */
public interface Component {
    void operation();
}

interface Leaf extends Component {

}

interface Composite extends Component {
    void add(Component c);

    void remove(Component c);

    Component getChild(int index);
}
