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
	private JTextField depth;
	private int firstIndex = 0;
	private int numOfSections = 4;
	private final int radioBtnGroubSize = 2;
	private final int AutomaticDepth = 0;
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
		depth = new JTextField(30);
		JLabel depthlbl	=	new	JLabel("Depth: ");
		depthlbl.setFont(new Font(depthlbl.getFont().getName(), Font.PLAIN, 18));
		inputPanel.add(depthlbl);
		inputPanel.add(depth);

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
			this.show();
		}else {
			isFirstTime = false;
		}

		if (selectAlgRadioBtn[firstIndex].isSelected()) { //BM Algorithm selected	

			// showing the input & buttons in the problem
			StringSearchMultipleInput input;
			if(inputRadioBtn[firstIndex].isSelected()) {
				input = new StringSearchMultipleInput(this.readFromFile(),patField.getText());
			}else {
				input = new StringSearchMultipleInput(inputField.getText(),patField.getText());
			}
			frame.setSize(1850,900);
			Container container = frame.getContentPane();
			input.show(container);
			Class cls[] = new Class[] {BM.class};
			Class<Algorithm<StringSearchMultiple>> algorithmClass = cls[0] ;
			try {
				this.run = new Runner<StringSearchMultiple>(algorithmClass, input, container);
				if(runTypeRadioBtn[firstIndex].isSelected()) {
					//Automatic Search Method -> depth = 0
					input.ismanual(AutomaticDepth);
					input.setRun(run);
			       // this.run.step(this.AutomaticDepth); // run to end 
				}else {
					input.ismanual(AutomaticDepth+1);
					input.setRun(run);
					//this.run.step(this.AutomaticDepth+1);
				}
				input.getNxtBtn().addActionListener(input);
                input.getPrevBtn().addActionListener(input);
                input.getRstBtn().addActionListener(input);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}else {  // KMP Algorithm selected

		}
	}

	private String readFromFile() {
		String data = ""; 
		try {
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

