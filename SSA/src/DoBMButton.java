import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class DoBMButton implements ActionListener { 
	public  JFrame frame2 ;
	private BM bm;
	
	public DoBMButton() {
		this.frame2 = new JFrame();
		this.bm = new BM("ABCDABD");
	}
	public void actionPerformed(ActionEvent ae) {
	     // showing the input 
		String st = bm.getInput();
		JButton button	=	new	JButton("input");
		button.setBounds(0,0,150,50);
		frame2.add(button);
		JButton arr[] =  new JButton[st.length()];
		int x = 100 , y = 0 , width = 50  , height = 50;
		for(int i = 0 ; i < st.length() ; i++) {
			char c = st.charAt(i);
			String ss  = String.valueOf(c);  
			arr[i] = new JButton(ss);
			arr[i].setBounds(x,y,width,height);
			if(x < 800 ) // 800?//////////
				x+=width;
			else {
				x = 100;
				y+= height+10;
			}
			arr[i].setBackground(Color.WHITE);
			arr[i].setForeground(Color.BLACK);
			frame2.add(arr[i]);
		}
		// showing the search string
		String str=  bm.getPattern();
		JButton button1	=	new	JButton("search string");
		button1.setBounds(0,y+60,150,50);
		frame2.add(button1);
		JButton array[] =  new JButton[str.length()];
		x = 200 ; y = y + 60  ; width = 50  ; height = 50;
		for(int i = 0 ; i < str.length() ; i++) {
			char c = str.charAt(i);
			String s  = String.valueOf(c);  
			array[i] = new JButton(s);
			array[i].setBounds(x,y,width,height); 
			x+=width;
			array[i].setBackground(Color.WHITE);
			array[i].setForeground(Color.BLACK);
			frame2.add(array[i]);
		}
		JButton b	=	new	JButton("NEXT");
		b.setBounds(0,y+60,150,50);
		DoNEXTButton donenext	=	new	DoNEXTButton(bm,arr,array);
    	b.addActionListener(donenext);
		frame2.add(b);
     
		JButton b1	=	new	JButton("PREV");
		b1.setBounds(200,y+60,150,50);
		frame2.add(b1);
		
		JButton b2	=	new	JButton("RESET");
		b2.setBounds(400,y+60,150,50);
		frame2.add(b2);
		 // should add bad match table to GUI???
		
		frame2.setTitle("BM");
		frame2.setSize(1000,700);
		frame2.setLayout(null); 
		frame2.setVisible(true);
		//bm.search(); //automatic search
		bm.search(5); // manual 
	}
	public BM getBm() {
		return bm;
	}
	public void setBm(BM bm) {
		this.bm = bm;
	}

}
