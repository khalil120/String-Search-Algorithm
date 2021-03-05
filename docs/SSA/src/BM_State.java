import java.util.Stack;

public class BM_State implements State{
	
	Stack<Integer> stack;
	public BM_State() {

		stack = new Stack<>();
	}


	@Override
	public int currentState() {
		return stack.peek();
	}

	@Override
	public void nextState(int state) {
			stack.push(state);	
	}

	@Override
	public int prevState() {
		if(!stack.empty()) {
			 stack.pop(); //delete current head
			 return stack.peek();
		}
		else
			return -1;
		

	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	

	
}
