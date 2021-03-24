import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class DoNEXTButton implements ActionListener {
	public BM bm;
	public  JFrame frame2 ;
	public JButton[] inputarr;
	public JButton[] patarray;
	public int cnt =0 ;

	public DoNEXTButton(BM bm) {
		super();
		this.bm = bm;
	}

	public DoNEXTButton(BM bm2, JButton[] arr, JButton[] array) {
		super();
		this.bm = bm2;
		this.inputarr = arr;
		this.patarray = array;
	}

	public void actionPerformed(ActionEvent ae) {
		if(bm.isSearchtype())
			automatic_search();
		else
			manual_search();
	}


	public void automatic_search() {
		this.bm.nextStep();
		int i = this.bm.getStateMachine().stack.get(this.bm.getStateMachine().getIndex()-1), 
				j = this.bm.getPattern().length()-1;
		char input = this.bm.getInput().charAt(i-2);
		char patt = this.bm.getPattern().charAt(j);

		while(j > -1) {
			if(input == patt ) {
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[i-2].setBackground(Color.GREEN);
			}
			j--;
			i--;
		}
	}


	public void manual_search() {
		//manual search
		int i;
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);


		int j = this.bm.getPattern().length()-1;
		if( cnt < this.bm.getStateMachine().stack.size() ) {
			int cur = this.bm.getStateMachine().stack.get(cnt);
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
				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur-2].setBackground(Color.RED);
			}
			cnt++; 
		}else {
			cnt = 0;
		}
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}


