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
	private JTextField inputField;
	private JTextField patField;
	private int firstIndex = 0;
	//private int secondIndex = 1;
	private int numOfSections = 4;

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
    	
    	JFrame frame	=	new	JFrame();
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
	
	
    public static void main(String[] args) { 
    	
    	MainGUI GUI = new MainGUI();
    	GUI.show();
    }
}

