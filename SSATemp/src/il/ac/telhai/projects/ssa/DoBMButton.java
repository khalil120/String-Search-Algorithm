package il.ac.telhai.projects.ssa;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class DoBMButton  implements ActionListener{
	public  JFrame frame2 ;
	private BM bm;

	public DoBMButton(String input,String patt) {
		this.frame2 = new JFrame();
		this.bm = new BM(input, patt);
	}
	public void actionPerformed(ActionEvent ae) {
		// showing the input 
		String st = bm.getInput();
		JButton button	=	new	JButton("input");
		button.setBounds(0,0,150,50);
		frame2.add(button);
		JButton arr[] =  new JButton[st.length()];
		int x = 200 , y = 0 , width = 50  , height = 50;
		for(int i = 0 ; i < st.length() ; i++) {
			char c = st.charAt(i);
			String ss  = String.valueOf(c);  
			arr[i] = new JButton(ss);
			arr[i].setBounds(x,y,width,height);
			if(x < 800 )
				x+=width;
			else {
				x = 200;
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
		JButton b =	new	JButton("NEXT");;
		JButton b1=	new	JButton("PREV");
		JButton b2	=	new	JButton("RESET");

		b.setBounds(0,y+60,150,50);
		b1.setBounds(200,y+60,150,50);
		b2.setBounds(400,y+60,150,50);

		DoNEXTButton donenext	=	new	DoNEXTButton(bm,arr,array,b,b1);
		b.addActionListener(donenext);
		frame2.add(b);

		DoPREVButton donePREV	=	new	DoPREVButton(bm,arr,array,donenext,b1);
		b1.addActionListener(donePREV);
		frame2.add(b1);

		DoRESETButton doneRest	=	new	DoRESETButton(arr,array,donenext,b1);
		b2.addActionListener(doneRest);
		frame2.add(b2);



		frame2.setTitle("BM");
		frame2.setSize(1000,700);
		frame2.setLayout(null); 
		frame2.setVisible(true);
		printchartable(bm.getPattern(), frame2);

		bm.search();

	}
	public BM getBm() {
		return bm;
	}
	public void setBm(BM bm) {
		this.bm = bm;
	}
	public void printchartable(String str,JFrame f) {
		int i,j; 

		char[] chars = str.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
			sb.append(character);
		}

		JButton array[] =  new JButton[str.length()];
		int x = 200 , y = 300 , width = 50  , height = 50;
		for( i = 0 ; i < sb.length() ; i++) {
			char c = str.charAt(i);
			String s  = String.valueOf(c);  
			array[i] = new JButton(s);
			array[i].setBounds(x,y,width,height); 
			x+=width;
			array[i].setBackground(Color.WHITE);
			array[i].setForeground(Color.BLACK);
			f.add(array[i]);
		}

		JTextField myOutpu = new JTextField("Letters");
		myOutpu.setBounds(100, y, width*2, height);
		f.add(myOutpu);

		int c = 0;
		String s;
		int vals[] = new int[str.length()];

		for( i = 0 ; i < str.length() ; i++) {
			c =bm.max(1, str.length()-i-1);
			vals[i] = c;
		}

		for( i = 0 ; i < str.length() ; i++) {
			for( j = i+1 ; j < str.length() ;j++) {
				if(str.charAt(i) == str.charAt(j)) {
					vals[i] = vals[j];
					vals[j] = 0;
				}
			}
		}

		x = 200 ;y = 350; // coordinates 
		JButton arr[] =  new JButton[str.length()];
		for( i = 0 ; i < str.length() ; i++) {
			if(vals[i]!=0) {
				int k = vals[i];
				s  = String.valueOf(k);
				arr[i] = new JButton(s);
				arr[i].setBounds(x,y,width,height); 
				x+=width;
				arr[i].setBackground(Color.WHITE);
				arr[i].setForeground(Color.BLACK);
				f.add(arr[i]);
			}
		}

		JTextField myOutput = new JTextField("values");
		myOutput.setBounds(100, y, width*2, height);
		f.add(myOutput);
	}
}
