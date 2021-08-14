package il.ac.telhai.algorithm;

import java.awt.Container;
import java.lang.reflect.Constructor;
import java.util.Stack;

public class Runner<P extends Problem> {
	
	private Algorithm<P> instance;
	private Stack<State<Algorithm<P>>> stack = new Stack<>();
	private Container container;
	private Input<P> input;
	
	public Runner(Class<Algorithm<P>> algorithmClass, Input<P> input, Container container) throws Exception {
		this.container = container;
		this.input = input;
		Constructor<Algorithm<P>> ctor = algorithmClass.getConstructor();
		instance = ctor.newInstance();
		instance.reset(input);
		pushAndShow();
		instance.step();
	}

	private void pushAndShow() {
		State<Algorithm<P>> state = instance.getState();
		stack.push(state);
		state.show(container);
	}
	
	public void reset() {
		stack = new Stack<>();
		instance.reset(input);
		State<Algorithm<P>> state = instance.getState();
		assert(state.getDepth() > 0);
		state.show(container);
	}
	
	public void step(int depth) {
		while (instance.getState().getDepth() > depth) 
		       instance.step();
        pushAndShow();
	}

	public void back (int depth) {
		State<Algorithm<P>> state = null;
		if (stack.peek().getDepth() == depth) state = stack.pop(); // Peel at most one state of the same depth
		while (!stack.isEmpty() && stack.peek().getDepth() > depth) { // Peel deeper states
			state = stack.pop();
		}
		assert(! stack.isEmpty());
		state = stack.peek();
		instance.setState(state);
		state.show(container);
	}
	
	public void runToEnd() {
		step(0);
		instance.getOutput().show(container);
	}

}
