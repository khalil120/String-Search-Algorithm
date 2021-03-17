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
		int i = this.bm.getStateMachine().getIndex() , j = this.bm.getPattern().length()-1;
		char input = this.bm.getInput().charAt(i);
		char patt = this.bm.getPattern().charAt(j);
		//System.out.println("1111111111111 " +patt +"   " + input);
		if(input == patt ) {
			this.patarray[j].setBackground(Color.GREEN);
			j--;
		}else {
			this.patarray[j].setBackground(Color.RED);
			j = this.bm.getPattern().length()-1;
		}
		try {
			//System.out.println("sleeping for 1 seconds");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
