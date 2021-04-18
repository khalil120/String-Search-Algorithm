package il.ac.telhai.projects.ssa;

import java.util.Stack;

public abstract class Algorithm<S> {
	
	private Stack<State<S>> stack;
	private int index;

	/**
	 * 
	 * @return return a copy of stack head
	 */
	public abstract S currentState();
	
	/**
	 * push the new state to the Data Structure
	 * @param state to be the index of the new state
	 */
	public abstract void updateNextState(S state);
	
	/**
	 * pop and remove the head of the stack state
	 * @return Head of the Stack
	 */
	public abstract S prevState();
	
	/**
	 * init the variables of the algorithm
	 * this method must be used on creating algorithm instance
	 * @param stack
	 */
	public void setDataStructure(Stack<State<S>> stack) {
		this.setStack(stack);
		this.setIndex(0);
	}
	
	public Stack<State<S>> getStack() {
		return stack;
	}


	public void setStack(Stack<State<S>> stack) {
		this.stack = stack;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * increment index value by 1
	 */
	public void incIndex() {
		this.index++;
	}
	/**
	 * decrement index by 1;
	 */
	public void decIndex() {
		this.index--;
	}
	
	public boolean isEmpty() {
		return this.stack.isEmpty();
	}
}
