import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DoPREVButton implements ActionListener  {
	public BM bm;
	public  JFrame frame2 ;
	public JButton[] inputarr;
	public JButton[] patarray;
	private DoNEXTButton next;

	public DoPREVButton(BM bm, JButton[] arr, JButton[] array,DoNEXTButton donenext) {
		super();
		this.bm = bm;
		this.inputarr = arr;
		this.patarray = array;
		this.next = donenext;
	}

	public void actionPerformed(ActionEvent ae) {
		if(bm.isSearchtype())
			automatic_search();
		else
			manual_search();
	}

	private void manual_search() {
		if( this.next.getCnt()>1) {
			int j = this.bm.getPattern().length()-1;
			int temp  = this.bm.getStateMachine().stack.get(this.next.getCnt()-1);
			int cur = this.bm.getStateMachine().stack.get(this.next.getCnt()-2);
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
				this.inputarr[temp-2].setBackground(Color.WHITE);
				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur-2].setBackground(Color.RED);
			}

			this.next.setCnt(this.next.getCnt()-1);		
		}else {
			this.next.setCnt(0);
			int i ;
			for(i = 0 ; i < this.patarray.length ;i++)
				this.patarray[i].setBackground(Color.WHITE);

			for(i = 0 ; i < this.inputarr.length ;i++)
				this.inputarr[i].setBackground(Color.WHITE);
		}
	}

	private void automatic_search() {
		int i;
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);

	}

}