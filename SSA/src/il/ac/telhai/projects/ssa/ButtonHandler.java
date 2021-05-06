package il.ac.telhai.projects.ssa;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	public ButtonHandler(BM bm2, JButton[] arr, JButton[] array, JButton next,JButton prev,JButton reset) {
		super();
		this.bm = bm2;
		this.inputarr = arr;
		this.patarray = array;
		this.next = next;
		this.prev = prev;
		this.reset = reset;
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
		this.bm.nextStep();
		State<Integer> st = this.bm.getStack().get(this.bm.getIndex()-1);
		int i = st.getState();
		int j = this.bm.getPattern().length()-1;
		char input = this.bm.getInput().charAt(i-2);
		char patt = this.bm.getPattern().charAt(j);
		while(j > -1) {
			if( input == patt ) {
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[i-2].setBackground(Color.GREEN);
			}
			j--;
			i--;
		}
	}
	public void NEXTmanual_search() {
		//manual search
		int i;
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);


		int j = this.bm.getPattern().length()-1;
		if( cnt < this.bm.getStack().size() ) {

			if(cnt == 0 ) {
				this.prev.setEnabled(true);
			}

			State<Integer> st = this.bm.getStack().get(cnt);
			int cur = st.getState();
			//System.out.println(cur);
			char input = this.bm.getInput().charAt(cur);
			char patt = this.bm.getPattern().charAt(j);
			if(input == patt ) {
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[cur].setBackground(Color.GREEN);
				j--;
				cur-=3;
				while(j > -1) {
					if(input == patt ) {
						this.patarray[j].setBackground(Color.GREEN);
						this.inputarr[cur].setBackground(Color.GREEN);
					}
					j--;
					cur--;

				}

			}else {
				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur].setBackground(Color.RED);
			}
			cnt++;
			if(cnt >= stackSize)
				this.next.setEnabled(false);
			else
				this.next.setEnabled(true);
		}
	}

	private void PREVmanual_search() {

		if( this.getCnt()>1) {

			if(this.getCnt() == this.getStackSize()) {
				this.getNextBtn().setEnabled(true);
			}

			int j = this.bm.getPattern().length()-1;
			State<Integer> st = this.bm.getStack().get(this.getCnt()-2);
			int cur = st.getState();
			char input = this.bm.getInput().charAt(cur-2);
			char patt = this.bm.getPattern().charAt(j);

			if(input == patt ) {
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[cur-2].setBackground(Color.GREEN);
				j--;
				cur-=3;
				while(j > -1) {
					if(input == patt ) {
						this.patarray[j].setBackground(Color.GREEN);
						this.inputarr[cur].setBackground(Color.GREEN);
					}
					j--;
					cur--;

				}
			}else {
				int i;
				for(i = 0 ; i < this.patarray.length ;i++)
					this.patarray[i].setBackground(Color.WHITE);

				for(i = 0 ; i < this.inputarr.length ;i++)
					this.inputarr[i].setBackground(Color.WHITE);

				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur-2].setBackground(Color.RED);
			}

			this.setCnt(this.getCnt()-1);		
		}else {
			this.setCnt(0);

			if(this.getCnt() >= 0)
				this.prev.setEnabled(false);
			else
				this.prev.setEnabled(true);

			int i ;

			for(i = 0 ; i < this.patarray.length ;i++)
				this.patarray[i].setBackground(Color.WHITE);

			for(i = 0 ; i < this.inputarr.length ;i++)
				this.inputarr[i].setBackground(Color.WHITE);
		}
	}

	private void PREVautomatic_search() {
		int i;
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);

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
}
