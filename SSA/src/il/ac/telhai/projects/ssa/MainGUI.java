package il.ac.telhai.projects.ssa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JButton[] inputArr;
	private JButton[] pattArr;
	private JTextField inputField;
	private JTextField patField;
	private int firstIndex = 0;
	private int xCord = 0;
	private int yCord = 0;
	private final int radioBtnGroubSize = 2;
	private int numOfSections = 4;
	private boolean isFirstTime = true;;
	public JFrame frame;

	public MainGUI(){ 
		selectAlg = new JPanel();
		selectAlgRadioBtn = new JRadioButton[radioBtnGroubSize];
		runTypeRadioBtn = new JRadioButton[radioBtnGroubSize];
		inputRadioBtn = new JRadioButton[radioBtnGroubSize];
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
		buttons[firstIndex].setSelected(true); //set first button to be selected as default
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
	
    public void initInputDisplay(String input) {
    	
    	JLabel inputLbl = new JLabel("Input: ");
		inputLbl.setFont(new Font(inputLbl.getFont().getName(), Font.PLAIN, 18));
		inputLbl.setBounds(400, 0, 150, 50);
		frame.add(inputLbl);
		inputArr =  new JButton[input.length()];
		xCord = 500;  yCord = 0;  
		int width = 50  , height = 50;
		for(int i = 0 ; i < input.length() ; i++) { 
			inputArr[i] = new JButton(String.valueOf(input.charAt(i)));
			inputArr[i].setBounds(xCord,yCord,width,height);
			if(xCord < 1000 )
				xCord += width;
			else {
				xCord = 500;
				yCord += height+10;
			}
			inputArr[i].setBackground(Color.WHITE);
			inputArr[i].setForeground(Color.BLACK);
			frame.add(inputArr[i]);
		}
    }
    
    public void initPatternDisplay(String pattern) {
    	
    	xCord = 500; yCord += 60 ;
    	int width = 50  , height = 50;
		JLabel patternLbl = new JLabel("PATTERN: ");
		patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 18));
		patternLbl.setBounds(400, yCord, 150, 50);
		frame.add(patternLbl);
		pattArr =  new JButton[pattern.length()];
		for(int i = 0 ; i < pattern.length() ; i++) {

			pattArr[i] = new JButton(String.valueOf(pattern.charAt(i)));
			pattArr[i].setBounds(xCord,yCord,width,height); 
			xCord += width;
			pattArr[i].setBackground(Color.WHITE);
			pattArr[i].setForeground(Color.BLACK);
			frame.add(pattArr[i]);
		}
    }
    
    public void initButton(int len, BM alg) {
    	
    	yCord += 80;
		JButton nxtBtn = new JButton("NEXT");
		JButton prevBtn = new JButton("PREV");
		JButton rstBtn	= new JButton("RESET");

		nxtBtn.setBounds(500,yCord,150,50);
		prevBtn.setBounds(700,yCord,150,50);
		rstBtn.setBounds(900,yCord,150,50);
		
		ButtonHandler buttonsarr = new  ButtonHandler(alg, inputArr, pattArr, nxtBtn, prevBtn,rstBtn);
		nxtBtn.addActionListener(buttonsarr);
		prevBtn.addActionListener(buttonsarr);
		rstBtn.addActionListener(buttonsarr);
		
		frame.add(nxtBtn);
		frame.add(prevBtn);
		frame.add(rstBtn);
    }
	
	public void show() {
		
		if(frame == null) {
			frame = new JFrame();
			frame.setTitle("String Search Algorithms");
			Container cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
		}
		frame.add(selectAlg);
		frame.pack();
		frame.setSize(850,850);
		frame.setLayout(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!isFirstTime) {
			//this is not the first click on start button then reset the frame
			frame.getContentPane().removeAll();
			frame.repaint();
			this.show();
		}else {
			isFirstTime = false;
		}
		
		if (selectAlgRadioBtn[firstIndex].isSelected()) { //bm selected
			BM bm ;
			String input;
			String pat;
			pat = patField.getText();
			if(inputRadioBtn[firstIndex].isSelected()) {
				//input from file
				bm = new BM(pat);
				initInputDisplay(bm.getInput());
				initPatternDisplay(pat);
				initButton(bm.getInput().length(),bm);
				bm.search();
				bm.setSearchtype(runTypeRadioBtn[firstIndex].isSelected());
			}else {
				//input from user
				input = inputField.getText();
				initInputDisplay(input);
				initPatternDisplay(pat);
				bm = new BM(input,pat);
				initButton(pat.length(),bm);
				bm.search();
				bm.setSearchtype(runTypeRadioBtn[firstIndex].isSelected());
			}
			bm.printchartable(pat, frame, xCord, yCord);
			bm.search();
			bm.show(frame);
		}else {  // kmp selected

		}
	}
	
	public static void main(String[] args) { 

		MainGUI GUI = new MainGUI();
		GUI.show();
	}
}

