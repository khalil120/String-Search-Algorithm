package il.ac.telhai.projects.ssa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.util.Scanner;
import il.ac.telhai.algorithm.Algorithm;
import il.ac.telhai.algorithm.BM;
import il.ac.telhai.algorithm.KMP;
import il.ac.telhai.algorithm.Runner;
import il.ac.telhai.stringSearchMultiple.StringSearchMultiple;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;

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
	private static final int numOfSections = 4;
	private static final int AutomaticDepth = 0;
	private static final int radioBtnGroubSize = 2;
	private boolean isFirstTime = true;;
	public JFrame frame;
	public Runner<StringSearchMultiple> run;



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
		// TODO: MORDO- THe above code seems to be equivalent to
		// initAlgSection(0);
		// initRunTypeSection(1);
		// initInputSection(2);
		// initStartBtn(3);
	}

	public void initRadioButtons(JRadioButton[] buttons, String[] str, JPanel panel) {

		ButtonGroup btnGroup = new ButtonGroup();
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(str[i]);
			btnGroup.add(buttons[i]);
			panel.add(buttons[i]);
		}
		buttons[0].setSelected(true); //set first button to be selected as default

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
			show();
		}else {
			isFirstTime = false;
		}

		if (selectAlgRadioBtn[0].isSelected()) { //BM Algorithm selected	

			// showing the input & buttons in the problem
			StringSearchMultipleInput input;
			if(inputRadioBtn[0].isSelected()) {
				input = new StringSearchMultipleInput(readFromFile(),patField.getText());
			}else {
				input = new StringSearchMultipleInput(inputField.getText(),patField.getText());
			}
			frame.setSize(1850,900);
			Container container = frame.getContentPane();
			input.show(container);
			Class cls[] = new Class[] {BM.class};
			Class<Algorithm<StringSearchMultiple>> algorithmClass = cls[0] ;
			try {
				run = new Runner<StringSearchMultiple>(algorithmClass, input, container);
				if(runTypeRadioBtn[0].isSelected()) {
					//Automatic Search Method -> depth = 0
					input.isManual(AutomaticDepth);
					input.setRun(run);
				}else {
					input.isManual(AutomaticDepth+1);
					input.setRun(run);
				}
				input.getNxtBtn().addActionListener(input);
                input.getPrevBtn().addActionListener(input);
                input.getRstBtn().addActionListener(input);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}else {  // KMP Algorithm selected
			StringSearchMultipleInput input;
			if(inputRadioBtn[0].isSelected()) {
				input = new StringSearchMultipleInput(readFromFile(),patField.getText());
			}else {
				input = new StringSearchMultipleInput(inputField.getText(),patField.getText());
			}
			frame.setSize(1850,900);
			Container container = frame.getContentPane();
			input.show(container);
			Class cls[] = new Class[] {KMP.class};
			Class<Algorithm<StringSearchMultiple>> algorithmClass = cls[0] ;
			try {
				run = new Runner<StringSearchMultiple>(algorithmClass, input, container);
				if(runTypeRadioBtn[0].isSelected()) {
					//Automatic Search Method -> depth = 0
					input.isManual(AutomaticDepth);
					input.setRun(run);
				}else {
					input.isManual(AutomaticDepth+1);
					input.setRun(run);
				}
				input.getNxtBtn().addActionListener(input);
                input.getPrevBtn().addActionListener(input);
                input.getRstBtn().addActionListener(input);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private String readFromFile() {
		String data = ""; 
		try {
			// TODO: MORDO - Avoid hard-coded literals within the body of the code. 
			// TODO: MORDO - Avoid absolute filenames
		   File myObj = new File("C:\\Users\\eslam asli\\eclipse-workspace\\SSA1\\src\\input.txt");
		//	File myObj = new File("C:\\Users\\khalil\\eclipse-workspace\\SSA\\src\\input.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine())
				data += myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) { 

		MainGUI GUI = new MainGUI();
		GUI.show();
	}
}

