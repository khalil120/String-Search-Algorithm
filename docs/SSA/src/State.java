
public interface State {
	
	
	/**
	 * 
	 * @return return a copy of stack head
	 */
	public int currentState();
	
	/**
	 * push the new state to the stack
	 * @param state to be the index of the new state
	 */
	public void nextState(int state);
	
	/**
	 * pop current head
	 * @return a copy of stack head
	 */
	public int prevState();

}
