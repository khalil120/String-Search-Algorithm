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
		this.bm.nextStep();
		int i = this.bm.getStateMachine().currentState(), j = this.bm.getPattern().length()-1;
		char input = this.bm.getInput().charAt(i-2);
		char patt = this.bm.getPattern().charAt(j);
		while(j > -1) {
			if(input == patt ) {
				this.patarray[j].setBackground(Color.GREEN);
				this.inputarr[i-2].setBackground(Color.GREEN);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			j--;
			i--;
		}
	}

}
