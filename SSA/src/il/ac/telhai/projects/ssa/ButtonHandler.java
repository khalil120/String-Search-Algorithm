package il.ac.telhai.projects.ssa;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;


public class ButtonHandler implements ActionListener{
	private BM bm;
	private JButton[] inputarr;
	private JButton[] patarray;
	private JButton next;
	private int cnt = 0;
	private int stackSize = 0;
	private JButton prev;
	private JButton reset;
	private Stack<node> found;

	//TODO: change here BM to Algorithm
	// TODO: MORDO - Finish all TODOs
	public ButtonHandler(BM bm2, JButton[] arr, JButton[] array, JButton next,JButton prev,JButton reset) {
		super();
		this.bm = bm2;
		this.inputarr = arr;
		this.patarray = array;
		this.next = next;
		this.prev = prev;
		this.reset = reset;
		found = new Stack<node>();
	}



	@Override
	public void actionPerformed(ActionEvent ae) {
		stackSize = bm.getStackSize();

		if(bm.isSearchtype()) {
			if (ae.getSource() == next) 
				NEXTautomatic_search();
			if (ae.getSource() == prev)
				PREVautomatic_search();
		}else {
			if (ae.getSource() == next) 
				NextManual_Search();
			if (ae.getSource() == prev)
				PREVmanual_search();
		}

		if (ae.getSource() == reset) {
			int i;
			getNextBtn().setEnabled(true);
			prev.setEnabled(true);
			setCnt(0);
			for(i = 0 ; i < patarray.length ;i++)
		     	patarray[i].setBackground(Color.WHITE);

			for(i = 0 ; i < inputarr.length ;i++)
				inputarr[i].setBackground(Color.WHITE);
		} 

	}
	public void NEXTautomatic_search() {

		resetBoard();
		prev.setEnabled(true);

		int patLen = bm.getPattern().length();
		int state, i;
		int iter = cnt;
		boolean cntFlag = false;

		while(iter < bm.getStackSize()) {
			state = bm.getStack().get(iter).getState();
			if(bm.isMatch(state)){
				//pattern match...
				for(i = 0; i < patLen; i++) {
					inputarr[state - i].setBackground(Color.GREEN);
					patarray[i].setBackground(Color.GREEN);
				}
				cnt = (iter + 1);
				//save the data where pattern found
				found.push(new node(cnt,state));
				//System.out.println("cnt = " + cnt + " iter = " + iter);
				break;
			}else {
				//no match...
				iter++;
			}
		}
		if(cnt == stackSize) {
			cnt--;
			cntFlag = true;
		}
		if(cnt+1 < bm.getInput().length()) {   
			State<Integer> stt = bm.getStack().get(cnt);
			int curr = stt.getState();
			cnt++;
			if( curr == bm.getStack().get(stackSize-1).getState())
				next.setEnabled(false);
			else
				next.setEnabled(true);
		}else {
			next.setEnabled(false);
		}

	}

	private void PREVautomatic_search() {

		//1. delete current apperiance from stack 
		if(!found.isEmpty()) {
			node tmp = found.pop();
			colorBoard(tmp.getState(), Color.WHITE);
			if(!next.isEnabled())
				next.setEnabled(true);
		}
		//2. set count to the new value
		if(!found.isEmpty()) {
			setCnt(found.peek().getItr());
			colorBoard(found.peek().getState(), Color.GREEN);
		}if (found.size() == 1)  {
			setCnt(bm.getPattern().length() - 1);
			prev.setEnabled(false);
		}
		//check next button in case Disabeled enable it
		if(!next.isEnabled()) {
			next.setEnabled(true);
		}

	}

	public void NextManual_Search() {  
		//manual search
		resetBoard();
		int j = bm.getPattern().length()-1;
		if( cnt < bm.getStack().size() ) {
			prev.setEnabled(true);
			State<Integer> st = bm.getStack().get(cnt);
			int cur = st.getState();
			if(cur < bm.getInput().length()) {
				char input = bm.getInput().charAt(cur);
				char patt = bm.getPattern().charAt(j);
				if(input == patt ) {
					patarray[j].setBackground(Color.GREEN);
					inputarr[cur].setBackground(Color.GREEN);
					j--;
					cur--;
					while(j > -1) {
						input = bm.getInput().charAt(cur);
						patt = bm.getPattern().charAt(j);
						if(input == patt ) {
							patarray[j].setBackground(Color.GREEN);
							inputarr[cur].setBackground(Color.GREEN);
						}else break;
						j--;
						cur--;
					}

				}else {
					patarray[j].setBackground(Color.RED);
					inputarr[cur].setBackground(Color.RED);
				}
			}
			State<Integer> stt = bm.getStack().get(cnt);
			int curr = stt.getState();
			cnt++;
			if( curr == bm.getStack().get(stackSize-1).getState())
				next.setEnabled(false);
			else
				next.setEnabled(true);
		}
	}

	private void PREVmanual_search() { 
		//TODO: check coloring 
		if( cnt> 0) {
			if(cnt == getStackSize()) {
				getNextBtn().setEnabled(true);
			}
			int j = bm.getPattern().length()-1;
			State<Integer> st = bm.getStack().get(cnt-2);
			int cur = st.getState();
			char input = bm.getInput().charAt(cur);
			char patt = bm.getPattern().charAt(j);
			if(input == patt ) {
				resetBoard();
				patarray[j].setBackground(Color.GREEN);
				inputarr[cur].setBackground(Color.GREEN);
				j--;
				cur--;
				while(j > -1) {
					input = bm.getInput().charAt(cur);
					patt = bm.getPattern().charAt(j);
					if(input == patt ) {
						patarray[j].setBackground(Color.GREEN);
						inputarr[cur].setBackground(Color.GREEN);
					}else break;
					j--;
					cur--;
				}
			}else {
				resetBoard();
				patarray[j].setBackground(Color.RED);
				inputarr[cur].setBackground(Color.RED);
			}

			setCnt(cnt-1);	
			if(getCnt() == 1)
				prev.setEnabled(false);
			else
				prev.setEnabled(true);
		}
	}

	public int getCnt() {
		return cnt;
	}

	public void decCnt() {
		this.cnt--;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getStackSize() {
		return stackSize;
	}

	public JButton getNextBtn() {
		return this.next;
	}

	public void colorBoard(int index, Color color) {
		int i;
		for(i = 0; i < bm.getPattern().length(); i++) {
			patarray[i].setBackground(color);
			inputarr[index - i].setBackground(color);
		}
	}

	public void resetBoard() {

		int i;
		for(i = 0 ; i < inputarr.length ;i++) {
			inputarr[i].setBackground(Color.WHITE);
			if(i < patarray.length)
				patarray[i].setBackground(Color.WHITE);
		}
	}

	private class node{
		private int itr;
		private int state;

		public node(int itr, int state) {
			this.itr = itr;
			this.state = state;
		}

		public int getItr() {
			return itr;
		}

		public int getState() {
			return state;
		}


	}
}
