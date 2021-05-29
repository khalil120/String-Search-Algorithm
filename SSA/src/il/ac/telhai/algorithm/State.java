package il.ac.telhai.algorithm;

import java.awt.Container;

public interface State<A extends Algorithm<?>> {
    int getDepth();  // The final state has depth 0, other states have higher depths
    void show(Container c);
}
