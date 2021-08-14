package il.ac.telhai.stringSearchMultiple;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;

import javax.swing.JButton;
import javax.swing.JLabel;

import il.ac.telhai.algorithm.Algorithm;
import il.ac.telhai.algorithm.BM;
import il.ac.telhai.algorithm.Input;
import il.ac.telhai.algorithm.Runner;
import il.ac.telhai.projects.ssa.ButtonHandler;
import il.ac.telhai.projects.ssa.MainGUI;

public class StringSearchMultipleInput implements Input<StringSearchMultiple>, ActionListener {
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
	private Runner run;
	private int depth;

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
	public Container getcont() {
		return c;
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
			run.step(0);
			this.depth--;
		}
		if (e.getSource() == prevBtn) {

		}

	}

	@Override
	public void setCont(Container c) {
		this.c = c;
	}

	@Override
	public void ismanual(int num) {
		this.ismanual = num;
	}

	public int getIsmanual() {
		return ismanual;
	}

	public Runner getRun() {
		return run;
	}

	public void setRun(Runner run) {
		this.run = run;
	}

	public void setdepth(int depth) {
		this.depth = depth;

	}

	public int getdepth() {
		return this.depth;
	}


}
