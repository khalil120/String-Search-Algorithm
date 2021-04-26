import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class DoRESETButton implements ActionListener  {
	private JButton[] inputarr;
	private JButton[] patarray;
	private DoNEXTButton next;
	private JButton prev;
	
	public DoRESETButton(JButton[] arr, JButton[] array, DoNEXTButton next,JButton b) {
		super();
		this.inputarr = arr;
		this.patarray = array;
		this.next = next;
		this.prev = b;
	}
	
	public void actionPerformed(ActionEvent ae) {
		int i;
		this.next.getNextBtn().setEnabled(true);
		this.prev.setEnabled(true);
		this.next.setCnt(0);
		for(i = 0 ; i < this.patarray.length ;i++)
			this.patarray[i].setBackground(Color.WHITE);

		for(i = 0 ; i < this.inputarr.length ;i++)
			this.inputarr[i].setBackground(Color.WHITE);
	}

}
