package il.ac.telhai.algorithm;

import java.util.LinkedList;
import java.util.Stack;

public interface Algorithm<P extends Problem> {
	// A concrete algorithm does not expose any other public methods besides this I/F
	void reset(Input<P> input);
    State<Algorithm<P>> getState();   // The final state of the algorithm (and only it) must have depth zero
    Stack<Integer> stack = new Stack<Integer>();
    LinkedList<Integer> Indexlist = new LinkedList<Integer>();
    void setState(State<Algorithm<P>> state);
    void step();
    Output<P> getOutput();
}
