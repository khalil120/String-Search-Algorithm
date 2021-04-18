package il.ac.telhai.projects.ssa;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;



public class DoNEXTButton implements ActionListener {
	private BM bm;
	private JButton[] inputarr;
	private JButton[] patarray;
	private JButton next;
	private int cnt = 0;
	private int stackSize = 0;
    private JButton prev;

	public DoNEXTButton(BM bm) {
		super();
		this.bm = bm;
	}

	public DoNEXTButton(BM bm2, JButton[] arr, JButton[] array, JButton b,JButton prev) {
		super();
		this.bm = bm2;
		this.inputarr = arr;
		this.patarray = array;
		this.next = b;
		this.prev = prev;
	}

	public void actionPerformed(ActionEvent ae) {
		
		
		stackSize = bm.getStack().size();
		
		if(bm.isSearchtype())
			automatic_search();
		else
			manual_search();
	}


	public void automatic_search() {
		this.bm.nextStep();
		State<Integer> st = this.bm.getStack().get(this.bm.getIndex()-1);
		int i = st.getState();
		int j = this.bm.getPattern().length()-1;
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
		if( cnt < this.bm.getStack().size() ) {
			
			if(cnt == 0 ) {
				this.prev.setEnabled(true);
			}
			
			State<Integer> st = this.bm.getStack().get(cnt);
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
				this.patarray[j].setBackground(Color.RED);
				this.inputarr[cur-2].setBackground(Color.RED);
			}
			cnt++;
			if(cnt >= stackSize)
				this.next.setEnabled(false);
			else
				this.next.setEnabled(true);
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

}


