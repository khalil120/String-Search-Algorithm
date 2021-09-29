package il.ac.telhai.stringSearchMultiple;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import il.ac.telhai.algorithm.Input;
import il.ac.telhai.algorithm.Runner;


public class StringSearchMultipleInput implements Input<StringSearchMultiple>, ActionListener {
	private static boolean prev = false;
	String text;
	String pattern;
	Container c;
	int xCord = 0;
	int yCord = 0;
	JButton[] inputArr;
	JButton[] pattArr;
	JButton nxtBtn;
	JButton prevBtn;
	JButton rstBtn;
	boolean nxt = false;
	boolean prv = false;
	private int ismanual;
	private Runner<StringSearchMultiple> run;
	private int depth;
	private boolean rst = false;
	private int[] lps;

	public StringSearchMultipleInput(String text, String pattern) {
		super();
		this.text = text;
		this.pattern = pattern;
	}

	@Override
	public void show(Container c) {
		this.c = c;
		initInputDisplay(text);
		initPatternDisplay(pattern);
		initButton(pattern.length());
		printchartable(pattern,c,xCord,yCord);
		lps = new int[pattern.length()];
		lps = computeLPSArray(pattern, pattern.length());
		printlps(pattern,c,xCord,yCord,lps);
	}

	public void initInputDisplay(String input) {

		JLabel inputLbl = new JLabel("Input: ");
		inputLbl.setFont(new Font(inputLbl.getFont().getName(), Font.PLAIN, 18));
		inputLbl.setBounds(400, 0, 150, 50);
		c.add(inputLbl);
		inputArr = new JButton[input.length()];
		xCord = 500;
		yCord = 0;
		int width = 50, height = 50;
		for (int i = 0; i < input.length(); i++) {
			inputArr[i] = new JButton(String.valueOf(input.charAt(i)));
			inputArr[i].setBounds(xCord, yCord, width, height);
			if (xCord < 1000)
				xCord += width;
			else {
				xCord = 500;
				yCord += height + 10;
			}
			inputArr[i].setBackground(Color.WHITE);
			inputArr[i].setForeground(Color.BLACK);
			c.add(inputArr[i]);
		}
	}

	public void initPatternDisplay(String pattern) {

		xCord = 500;
		yCord += 60;
		int width = 50, height = 50;
		JLabel patternLbl = new JLabel("PATTERN: ");
		patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 18));
		patternLbl.setBounds(400, yCord, 150, 50);
		c.add(patternLbl);
		pattArr = new JButton[pattern.length()];
		for (int i = 0; i < pattern.length(); i++) {

			pattArr[i] = new JButton(String.valueOf(pattern.charAt(i)));
			pattArr[i].setBounds(xCord, yCord, width, height);
			xCord += width;
			pattArr[i].setBackground(Color.WHITE);
			pattArr[i].setForeground(Color.BLACK);
			c.add(pattArr[i]);
		}
	}

	public void initButton(int len) {

		yCord += 80;
		nxtBtn = new JButton("NEXT");
		prevBtn = new JButton("PREV");
		rstBtn = new JButton("RESET");

		nxtBtn.setBounds(500, yCord, 150, 50);
		prevBtn.setBounds(700, yCord, 150, 50);
		rstBtn.setBounds(900, yCord, 150, 50);

		c.add(nxtBtn);
		c.add(prevBtn);
		c.add(rstBtn);
	}
	public void printchartable(String str,Container cc, int xCord, int yCord) {

		int i,j; 
		yCord += 80;
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
		patternLbl.setBounds(500,  yCord, width*5, height);
		cc.add(patternLbl);
		yCord += 50;

		JTextField myOutpu = new JTextField("Letters");
		myOutpu.setBounds(500, yCord, width*2, height);
		cc.add(myOutpu);
		x = x +150;
		for( i = 0 ; i < sb.length() ; i++) {
			char c = str.charAt(i);
			String s  = String.valueOf(c);  
			array[i] = new JButton(s);
			array[i].setBounds(x,yCord,width,height); 
			x+=width;
			array[i].setBackground(Color.WHITE);
			array[i].setForeground(Color.BLACK);
			cc.add(array[i]);
		}

		int c = 0;
		String s;
		int vals[] = new int[str.length()];

		for( i = 0 ; i < str.length() ; i++) {
			c = Math.max(1, str.length()-i-1);
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
		// if there is no match char
		String st = "no match";
		JButton tmp = new JButton(st);
		tmp.setBounds(x, yCord, width*3, height);
		tmp.setBackground(Color.WHITE);
		cc.add(tmp);

		x = 500 ; yCord += 50; // coordinates 
		JTextField myOutput = new JTextField("values");
		myOutput.setBounds(500, yCord, width*2, height);
		cc.add(myOutput);
		x = x + 150;
		JButton arr[] =  new JButton[str.length()];
		for( i = 0 ; i < str.length() ; i++) {
			if(vals[i]!=0) {
				int k = vals[i];
				s  = String.valueOf(k);
				arr[i] = new JButton(s);
				arr[i].setBounds(x,yCord,width,height); 
				x+=width;
				arr[i].setBackground(Color.WHITE);
				arr[i].setForeground(Color.BLACK);
				cc.add(arr[i]);
			}
		}
		// if there is no match char
		int k = str.length();
		s  = String.valueOf(k);
		JButton tmpp = new JButton(s);
		tmpp.setBounds(x, yCord, width*3, height);
		tmpp.setBackground(Color.WHITE);
		cc.add(tmpp);

	}
	private int[]  computeLPSArray(String pat, int M)
	{
		// length of the previous longest prefix suffix
		int len = 0;
		int i = 1;
		lps[0] = 0; // lps[0] is always 0

		// the loop calculates lps[i] for i = 1 to M-1
		while (i < M) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			}
			else{
				if (len != 0) {
					len = lps[len - 1];
				}
				else{
					lps[i] = len;
					i++;
				}
			}
		}
		return lps;
	}
	public void printlps(String str,Container cc, int xCord, int yCord,int[] lps) {

		int i; 
		yCord += 80;
		char[] chars = str.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			charSet.add(c);
		}

		JButton array[] =  new JButton[str.length()];
		int x = 500  , width = 50  , height = 50;
		JLabel patternLbl = new JLabel("LPS: ");
		patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 25));
		patternLbl.setBounds(500,  yCord, width*5, height);
		cc.add(patternLbl);
		yCord += 50;

		JTextField myOutpu = new JTextField("Letters");
		myOutpu.setBounds(500, yCord, width*2, height);
		cc.add(myOutpu);
		x = x +150;
		for( i = 0 ; i < str.length() ; i++) {
			char c = str.charAt(i);
			String s  = String.valueOf(c);  
			array[i] = new JButton(s);
			array[i].setBounds(x,yCord,width,height); 
			x+=width;
			array[i].setBackground(Color.WHITE);
			array[i].setForeground(Color.BLACK);
			cc.add(array[i]);
		}

		String s;

		// if there is no match char
		String st = "no match";
		JButton tmp = new JButton(st);
		tmp.setBounds(x, yCord, width*3, height);
		tmp.setBackground(Color.WHITE);
		cc.add(tmp);

		x = 500 ; yCord += 50; // coordinates 
		JTextField myOutput = new JTextField("values");
		myOutput.setBounds(500, yCord, width*2, height);
		cc.add(myOutput);
		x = x + 150;
		JButton arr[] =  new JButton[str.length()];
		for( i = 0 ; i < str.length() ; i++) {
			int k = lps[i];
			s  = String.valueOf(k);
			arr[i] = new JButton(s);
			arr[i].setBounds(x,yCord,width,height); 
			x+=width;
			arr[i].setBackground(Color.WHITE);
			arr[i].setForeground(Color.BLACK);
			cc.add(arr[i]);
		}
		// if there is no match char
		int k = str.length();
		s  = String.valueOf(k);
		JButton tmpp = new JButton(s);
		tmpp.setBounds(x, yCord, width*3, height);
		tmpp.setBackground(Color.WHITE);
		cc.add(tmpp);

	}

	public String getText() {
		return text;
	}

	public String getPattern() {
		return pattern;
	}

	public JButton getNxtBtn() {
		return nxtBtn;
	}

	public void setNxtBtn(JButton nxtBtn) {
		this.nxtBtn = nxtBtn;
	}

	public JButton getPrevBtn() {
		return prevBtn;
	}

	public void setPrevBtn(JButton prevBtn) {
		this.prevBtn = prevBtn;
	}

	public JButton getRstBtn() {
		return rstBtn;
	}

	public void setRstBtn(JButton rstBtn) {
		this.rstBtn = rstBtn;
	}

	public int getxCord() {
		return xCord;
	}

	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public void setyCord(int yCord) {
		this.yCord = yCord;
	}

	@Override
	public String pattern() {
		return pattern;
	}

	@Override
	public String input() {
		return text;
	}


	public void setC(Container c) {
		this.c = c;
	}


	@Override
	public StringSearchMultipleInput getSSMI() {
		return this;
	}

	public JButton[] getInputArr() {
		return inputArr;
	}

	public void setInputArr(JButton[] inputArr) {
		this.inputArr = inputArr;
	}

	public JButton[] getPattArr() {
		return pattArr;
	}

	public void setPattArr(JButton[] pattArr) {
		this.pattArr = pattArr;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nxtBtn) {
			prevBtn.setEnabled(true);
			prev = false;
			rst = false;
			run.step(0);
			depth--;
		}
		else if (e.getSource() == prevBtn) {
			nxtBtn.setEnabled(true);
		    prev = true;
		    rst = false;
            run.back(depth);
            depth++;
		}
		else if(e.getSource() == rstBtn) {
			nxt = false;
			prv = false;
			rst = true;
			nxtBtn.setEnabled(true);
			prevBtn.setEnabled(true);
			run.reset();
		}

	}


	@Override
	public void isManual(int num) {
		this.ismanual = num;
	}

	public int getIsmanual() {
		return ismanual;
	}

	public Runner<StringSearchMultiple> getRun() {  
		return run;
	}

	public void setRun(Runner<StringSearchMultiple> run) {
		this.run = run;
	}

	public void setdepth(int depth) {
		this.depth = depth;

	}

	public int getdepth() {
		return this.depth;
	}

	public boolean isprev() {
		return prev;
	}
	
	public boolean isrst() {
		return rst ;
	}
}
