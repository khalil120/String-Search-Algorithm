import java.util.Stack;

public class BM_State implements State{

	Stack<Integer> stack;
	public int index;
	public BM_State() {
		stack = new Stack<Integer>();
		index = 0 ;
	}


	@Override
	public int currentState() {
		/*if(!stack.empty()) {
			 return stack.peek();
		}
		else
			return -1;*/
		if(index > -1)
			return stack.get(index-1);
        return -1;
	}

	@Override
	public void nextState(int state) {
		stack.push(state);	
		index++;
	}

	@Override
	public int prevState() {
		if(!stack.empty()) {
			stack.pop(); //delete current head
			index--;
			return stack.peek();
		}
		else
			return -1;


	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}


	public Stack<Integer> getStack() {
		return stack;
	}


	public void setStack(Stack<Integer> stack) {
		this.stack = stack;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}




}
