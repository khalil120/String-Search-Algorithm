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
				NEXTmanual_search();
			if (ae.getSource() == prev)
				PREVmanual_search();
		}

		if (ae.getSource() == reset) {
			int i;
			this.getNextBtn().setEnabled(true);
			this.prev.setEnabled(true);
			this.setCnt(0);
			for(i = 0 ; i < this.patarray.length ;i++)
				this.patarray[i].setBackground(Color.WHITE);

			for(i = 0 ; i < this.inputarr.length ;i++)
				this.inputarr[i].setBackground(Color.WHITE);
		} 

	}
	public void NEXTautomatic_search() {

		resetBoard();
		this.prev.setEnabled(true);

		int patLen = this.bm.getPattern().length();
		int state, i;
		int iter = cnt;
		boolean cntFlag = false;

		while(iter < bm.getStackSize()) {
			state = this.bm.getStack().get(iter).getState();
			if(this.bm.isMatch(state)){
				//pattern match...
				for(i = 0; i < patLen; i++) {
					this.inputarr[state - i].setBackground(Color.GREEN);
					this.patarray[i].setBackground(Color.GREEN);
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
		// disable next button at end of input.
		//System.out.println("cnt = " + cnt+ " iter = " + iter + " stackSize = " + stackSize);
		if(cnt == stackSize) {
			cnt--;
			cntFlag = true;
		}

		/*if(bm.getStack().get(cnt).getState() == bm.getStack().get(stackSize-1).getState() || cnt == 0 ) {
			this.next.setEnabled(false);
			if(cntFlag) {
				cnt++;
				cntFlag = false;
			}
		}*/
		if(cnt+1 < this.bm.getInput().length()) {
			State<Integer> stt = this.bm.getStack().get(cnt);
			int curr = stt.getState();
			cnt++;
			if( curr == this.bm.getStack().get(stackSize-1).getState())
				this.next.setEnabled(false);
			else
				this.next.setEnabled(true);
		}else {
			this.next.setEnabled(false);
		}

	}

	private void PREVautomatic_search() {

		//1. delete current apperiance from stack 
		if(!found.isEmpty()) {
			node tmp = found.pop();
			colorBoard(tmp.getState(), Color.WHITE);
			if(!this.next.isEnabled())
				this.next.setEnabled(true);
		}
		//2. set count to the new value
		if(!found.isEmpty()) {
			this.setCnt(found.peek().getItr());
			colorBoard(found.peek().getState(), Color.GREEN);
		}if (found.size() == 1)  {
			this.setCnt(bm.getPattern().length() - 1);
			this.prev.setEnabled(false);
		}
		//check next button in case Disabeled enable it
		if(!this.next.isEnabled()) {
			this.next.setEnabled(true);
		}

	}

	public void NEXTmanual_search() {
		//manual search
		resetBoard();

		int j = this.bm.getPattern().length()-1;
		if( cnt < this.bm.getStack().size() ) {


			this.prev.setEnabled(true);


			State<Integer> st = this.bm.getStack().get(cnt);
			int cur = st.getState();
			if(cur < this.bm.getInput().length()) {
				char input = this.bm.getInput().charAt(cur);
				char patt = this.bm.getPattern().charAt(j);
				if(input == patt ) {
					this.patarray[j].setBackground(Color.GREEN);
					this.inputarr[cur].setBackground(Color.GREEN);
					j--;
					cur--;
					while(j > -1) {
						input = this.bm.getInput().charAt(cur);
						patt = this.bm.getPattern().charAt(j);
						if(input == patt ) {
							this.patarray[j].setBackground(Color.GREEN);
							this.inputarr[cur].setBackground(Color.GREEN);
						}else break;
						j--;
						cur--;
					}

				}else {
					this.patarray[j].setBackground(Color.RED);
					this.inputarr[cur].setBackground(Color.RED);
				}
			}

			State<Integer> stt = this.bm.getStack().get(cnt);
			int curr = stt.getState();
			cnt++;
			if( curr == this.bm.getStack().get(stackSize-1).getState())
				this.next.setEnabled(false);
			else
				this.next.setEnabled(true);
		}
	}

	private void PREVmanual_search() {
		//TODO: check coloring 
		if( this.getCnt()> 0) {
			if(this.getCnt() == this.getStackSize()) {
				this.getNextBtn().setEnabled(true);
			}

			int j = this.bm.getPattern().length()-1;
			State<Integer> st = this.bm.getStack().get(cnt-2);
			int cur = st.getState();
			char input = this.bm.getInput().charAt(cur);
			char patt = this.bm.getPattern().charAt(j);

			if(input == patt ) {

				resetBoard();
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[cur].setBackground(Color.GREEN);
				j--;
				cur--;
				while(j > -1) {
					input = this.bm.getInput().charAt(cur);
					patt = this.bm.getPattern().charAt(j);
					if(input == patt ) {
						this.patarray[j].setBackground(Color.GREEN);
						this.inputarr[cur].setBackground(Color.GREEN);
					}else break;
					j--;
					cur--;
				}
			}else {
				resetBoard();
				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur].setBackground(Color.RED);
			}

			this.setCnt(this.getCnt()-1);	
			if(this.getCnt() == 1)
				this.prev.setEnabled(false);
			else
				this.prev.setEnabled(true);
		}/*else {
			this.setCnt(0);
			if(this.getCnt() == 0)
				this.prev.setEnabled(false);
			else
				this.prev.setEnabled(true);

			resetBoard();
		}*/
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
		for(i = 0; i < this.bm.getPattern().length(); i++) {
			this.patarray[i].setBackground(color);
			this.inputarr[index - i].setBackground(color);
		}
	}

	public void resetBoard() {

		int i;
		for(i = 0 ; i < this.inputarr.length ;i++) {
			this.inputarr[i].setBackground(Color.WHITE);
			if(i < this.patarray.length)
				this.patarray[i].setBackground(Color.WHITE);
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
