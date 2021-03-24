import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DoRESETButton implements ActionListener  {
	public BM bm;
	public  JFrame frame2 ;
	public JButton[] inputarr;
	public JButton[] patarray;
	
	public DoRESETButton(BM bm, JButton[] arr, JButton[] array) {
		super();
		this.bm = bm;
		this.inputarr = arr;
		this.patarray = array;
	}
	
	public void actionPerformed(ActionEvent ae) {
		int i;
		
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);
	}

}
