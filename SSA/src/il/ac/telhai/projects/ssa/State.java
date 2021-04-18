package il.ac.telhai.projects.ssa;

public class State<T> {
	
	private T state;
	
	public State(T state) {
		this.state = state;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}
	

}
