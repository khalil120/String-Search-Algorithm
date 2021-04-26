package il.ac.telhai.projects.ssa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class MainGUI implements ActionListener{

	private JPanel selectAlg;
	private static String[] Actions = {"CHOOSE ALGORITHM: " , "Run Type: ", "Input: ", "Start"};
	private static String[] radioBtnAlgorithimOptoins = {"A) BM " , "B) KMP "};
	private static String[] radioBtnRunTypeOptoins = {"A) Automatic", "B) Manual "};
	private static String[] radioBtnInputOptoins = {"Current File", "Manual Input"};
	private JRadioButton[] selectAlgRadioBtn;
	private JRadioButton[] runTypeRadioBtn;
	private JRadioButton[] inputRadioBtn;
	private JTextField inputField;
	private JTextField patField;
	private int firstIndex = 0;
	//private int secondIndex = 1;
	private int numOfSections = 4;
	public JFrame frame;

	public MainGUI(){ 
		selectAlg = new JPanel();
		selectAlgRadioBtn = new JRadioButton[2];
		runTypeRadioBtn = new JRadioButton[2];
		inputRadioBtn = new JRadioButton[2];
		init();

	}
	private void init() {

		for(int i = 0; i < numOfSections; i++) {
			switch (i) {
			case 0:
				initAlgSection(i);
				break;
			case 1:
				initRunTypeSection(i);
				break;
			case 2:
				initInputSection(i);
				break;
			case 3:
				initStartBtn(i);
				break;
			}
		}

	}

	public void initRadioButtons(JRadioButton[] buttons, String[] str, JPanel panel) {

		ButtonGroup btnGroup = new ButtonGroup();
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(str[i]);
			btnGroup.add(buttons[i]);
			panel.add(buttons[i]);
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}

	public void initAlgSection(int secIndex) {

		JLabel algText	=	new	JLabel(Actions[secIndex]);
		algText.setFont(new Font(algText.getFont().getName(), Font.PLAIN, 18));
		selectAlg.add(algText);  	
		initRadioButtons(selectAlgRadioBtn, radioBtnAlgorithimOptoins, selectAlg);
	}

	public void initRunTypeSection(int secIndex) {

		JPanel runType = new JPanel();
		JLabel typeText	=	new	JLabel(Actions[secIndex]);
		typeText.setFont(new Font(typeText.getFont().getName(), Font.PLAIN, 18));
		runType.add(typeText); 
		initRadioButtons(runTypeRadioBtn, radioBtnRunTypeOptoins, runType);
		selectAlg.add(runType);
	}

	public void initInputSection(int secIndex) {

		JPanel inputPanel = new JPanel();
		JLabel inputlbl	=	new	JLabel(Actions[secIndex]);
		inputlbl.setFont(new Font(inputlbl.getFont().getName(), Font.PLAIN, 18));
		inputPanel.add(inputlbl);
		inputField = new JTextField(30);
		JLabel patlbl	=	new	JLabel("Pattern: ");
		patlbl.setFont(new Font(patlbl.getFont().getName(), Font.PLAIN, 18));
		patField = new JTextField(30);
		initRadioButtons(inputRadioBtn, radioBtnInputOptoins, inputPanel);
		inputPanel.add(inputField);
		inputPanel.add(patlbl);
		inputPanel.add(patField);
		selectAlg.add(inputPanel);
	}

	public void initStartBtn(int secIndex) {

		JButton start = new JButton(Actions[secIndex]);
		start.addActionListener(this);
		start.setBackground(new Color(59, 89, 182));
		start.setForeground(Color.WHITE);
		start.setFocusPainted(false);
		start.setFont(new Font("Tahoma", Font.BOLD, 12));
		selectAlg.add(start);
	}

	public void show() {

		frame	=	new	JFrame();
		frame.setTitle("String Search Algorithms");
		Container	cp	=	frame.getContentPane();
		cp.setLayout(new BorderLayout());

		frame.add(selectAlg);    	
		frame.pack();
		frame.setSize(850,850);
		frame.setLayout(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DoBMButton run;
		BM bm ;
		if(selectAlgRadioBtn[firstIndex].isSelected()) {
			//to be changed - new featuers - BUG HERE will Fix Soon 
			String pat = patField.getName();
			if(inputRadioBtn[firstIndex].isSelected()) {
				//input from file
				bm = new BM("ABCDABD");
				String input = bm.getInput();
				String patt = bm.getPattern();
				run = new DoBMButton( input, patt);
				run.actionPerformed(null);
				run.getBm().setSearchtype(runTypeRadioBtn[firstIndex].isSelected());

			}else {
				String text = inputField.getText();
				String txt = patField.getText();
				bm = new BM(text,txt);
				run = new DoBMButton(text,txt);
				run.actionPerformed(null);
				run.getBm().setSearchtype(runTypeRadioBtn[firstIndex].isSelected());

			}

		}else {
			System.out.println("KMP");
		}

	}

	private class DoBMButton  implements ActionListener{
		private BM bm;

		public DoBMButton(String input,String patt) {
			this.bm = new BM(input, patt);
		}
		public void actionPerformed(ActionEvent ae) {
			// showing the input 
			String st = bm.getInput();
			JLabel inputLbl = new JLabel("Input: ");
			inputLbl.setFont(new Font(inputLbl.getFont().getName(), Font.PLAIN, 18));
			inputLbl.setBounds(400, 0, 150, 50);

			frame.add(inputLbl);
			JButton arr[] =  new JButton[st.length()];
			int x = 500 , y = 0 , width = 50  , height = 50;
			for(int i = 0 ; i < st.length() ; i++) {
				char c = st.charAt(i);
				String ss  = String.valueOf(c);  
				arr[i] = new JButton(ss);
				arr[i].setBounds(x,y,width,height);
				if(x < 1000 )
					x+=width;
				else {
					x = 500;
					y+= height+10;
				}
				arr[i].setBackground(Color.WHITE);
				arr[i].setForeground(Color.BLACK);
				frame.add(arr[i]);
			}
			// showing the Pattern
			JLabel patternLbl = new JLabel("PATTERN: ");
			patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 18));
			patternLbl.setBounds(400, y + 60, 150, 50);
			String str=  bm.getPattern();
			frame.add(patternLbl);
			JButton array[] =  new JButton[str.length()];
			x = 500 ; y = y + 60  ; width = 50  ; height = 50;
			for(int i = 0 ; i < str.length() ; i++) {
				char c = str.charAt(i);
				String s  = String.valueOf(c);  
				array[i] = new JButton(s);
				array[i].setBounds(x,y,width,height); 
				x+=width;
				array[i].setBackground(Color.WHITE);
				array[i].setForeground(Color.BLACK);
				frame.add(array[i]);
			}
			JButton b =	new	JButton("NEXT");;
			JButton b1=	new	JButton("PREV");
			JButton b2	=	new	JButton("RESET");

			b.setBounds(500,y+80,150,50);
			b1.setBounds(700,y+80,150,50);
			b2.setBounds(900,y+80,150,50);

			DoNEXTButton donenext	=	new	DoNEXTButton(bm,arr,array,b,b1);
			b.addActionListener(donenext);
			frame.add(b);

			DoPREVButton donePREV	=	new	DoPREVButton(bm,arr,array,donenext,b1);
			b1.addActionListener(donePREV);
			frame.add(b1);

			DoRESETButton doneRest	=	new	DoRESETButton(arr,array,donenext,b1);
			b2.addActionListener(doneRest);
			frame.add(b2);



			frame.setSize(1300,700);
			frame.setLayout(null); 
			frame.setVisible(true);
			printchartable(bm.getPattern(), frame);

			bm.search();

		}
		public BM getBm() {
			return bm;
		}
	
		public void printchartable(String str,JFrame f) {
			int i,j; 
            int y = 400;
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
			int x = 500  , width = 50  , height = 50;

			JLabel patternLbl = new JLabel("BAD CHAR TABLE: ");
			patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 25));
			patternLbl.setBounds(500, y, width*5, height);
			f.add(patternLbl);
            y =y +50;

			JTextField myOutpu = new JTextField("Letters");
			myOutpu.setBounds(500, y, width*2, height);
			f.add(myOutpu);
			x = x +150;
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

			x = 500 ;y = 500; // coordinates 
			JTextField myOutput = new JTextField("values");
			myOutput.setBounds(500, y, width*2, height);
			f.add(myOutput);
			x = x + 150;
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

			
		}
	}



	public static void main(String[] args) { 

		MainGUI GUI = new MainGUI();
		GUI.show();
	}
}

