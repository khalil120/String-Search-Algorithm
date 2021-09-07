package il.ac.telhai.algorithm;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;

public class KMP implements Algorithm<Problem>, State<Algorithm<Problem>> {

	private static final int NO_OF_CHARS = 256;
	private State<Algorithm<Problem>> state;
	private LinkedList<Integer> Indexlist = new LinkedList<Integer>();
	private Stack<Integer> stack = new Stack<>(); //the stack to save the depth of all the matching strings
	private Input<Problem> input;
	private Output<Problem> output;
	private int depth = 0;
	private Container container ;
	private StringSearchMultipleInput inputData ;
	private boolean bool = false;
	private int nextdepth = 0;
	private int[] lps;

	public KMP() {
		state = this;
	}

	@Override
	public int getDepth() { 
		if((inputData.getdepth() == nextdepth || depth == nextdepth) && inputData.getIsmanual() == 1)
			this.depth--;
		if (this.depth == inputData.getdepth()&& inputData.getIsmanual() == 0)
			return this.depth;
		else if (inputData.getIsmanual() == 1 && this.depth == inputData.getdepth())  return this.depth;
		return 0;
	}

	@Override
	public void show(Container c) {
		if(inputData.isprev() ) {
			if(depth != 0  & !stack.isEmpty() ) 
				stack.pop();
			if(stack.isEmpty()) { 
				int i;
				for(i = 0; i < this.inputData.getInputArr().length; i++) {
					this.inputData.getInputArr()[i].setBackground(Color.WHITE);
				}
				for(i = 0; i < this.inputData.getPattArr().length; i++) {
					this.inputData.getPattArr()[i].setBackground(Color.WHITE);
				}
			}
			else{
				if(this.stack.size() == 1)	{
					this.inputData.getPrevBtn().setEnabled(false);
					inputData.setdepth(depth);
				}
				if(depth == 0 ) {
					depth++;
					inputData.setdepth(depth);
					stack.pop();
				}
				int tmp = stack.peek();
				while (tmp >= depth) {
					this.step();
				}
				this.depth--;
			}
		}
	}

	@Override
	public void step() {
		int i;
		for(i = 0; i < this.inputData.getInputArr().length; i++) {
			this.inputData.getInputArr()[i].setBackground(Color.WHITE);
		}
		for(i = 0; i < this.inputData.getPattArr().length; i++) {
			this.inputData.getPattArr()[i].setBackground(Color.WHITE);
		}
		if(!bool) {
			bool = true;
			return;
		}
		boolean tmp = false;
		int indexToPaint = this.Indexlist.size() - this.depth;
		int patternLen = 0;
		int inputLength = input.input().length();
		if(indexToPaint < 0)
			indexToPaint = 0;
		int indexToStartFrom = Indexlist.get(indexToPaint);
		int patt_ch, inpt_ch;
		if(indexToStartFrom <= inputLength) {
			if(!stack.isEmpty() && stack.peek() == depth && !inputData.isprev())
				inputData.setdepth(inputData.getdepth()-1);
			patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
			if(input.input().length()<=indexToStartFrom ) {
				this.inputData.getNxtBtn().setEnabled(false);
				indexToStartFrom = input.input().length() -1;
			}
			inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
			if(inpt_ch == patt_ch) {
				this.inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
				this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
				patternLen++;
				indexToStartFrom++;
				while(patternLen < input.pattern().length()) {
					patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
					inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
					if(inpt_ch == patt_ch ) {
						this.inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
						this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
						tmp = false;
					}else  {

						if (inputData.getIsmanual() == 0){
							if(!this.inputData.isprev()) {
								inputData.setdepth(inputData.getdepth()-1);
							}
							else if (this.inputData.isprev()) {
								inputData.setdepth(inputData.getdepth()+1);
							}
						}
						tmp = true;
						break;
					}
					patternLen++;
					indexToStartFrom++;
				}
				if (!inputData.isprev() && !tmp) {
					if(stack.isEmpty()) stack.push(depth);
					else if(depth!=stack.peek())
						stack.push(depth);
				}
			}else if (inputData.getIsmanual() == 0){
				if(!this.inputData.isprev()) {
					inputData.setdepth(inputData.getdepth()-1);
				}
				else if (this.inputData.isprev()) {
					inputData.setdepth(inputData.getdepth()+1);
				}
			}
			if (inputData.getIsmanual() == 1 && inpt_ch != patt_ch) {
				if (!inputData.isprev())
					stack.push(depth);
				this.inputData.getPattArr()[patternLen].setBackground(Color.RED);
				this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.RED);
			}
			if(!this.inputData.isprev()) {
				this.depth --;
			}
			else this.depth++;
			nextdepth = depth;
			// finding the next step to enable the next button 
			if(patt_ch==inpt_ch) {
				boolean index = false;
				if(nextdepth >= this.Indexlist.size()) nextdepth--;
				int indexToPaint2 = this.Indexlist.size() - this.nextdepth;
				int patternLen2 = 0;
				if(this.Indexlist.size()<=indexToPaint2 ) {
					this.inputData.getNxtBtn().setEnabled(false);
					indexToPaint2 = this.Indexlist.size() -1;
				}
				int indexToStartFrom2 = Indexlist.get(indexToPaint2);
				if(input.input().length()<=indexToStartFrom2 ) {
					this.inputData.getNxtBtn().setEnabled(false);
					indexToStartFrom2 = input.input().length() -1;
				}
				patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen2);
				inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom2);
				while (patt_ch!=inpt_ch) {
					nextdepth--;
					indexToPaint2 = this.Indexlist.size() - this.nextdepth;
					if(this.Indexlist.size() <= indexToPaint2 ) {
						this.inputData.getNxtBtn().setEnabled(false);
						index = true;
						break;
					}
					if(!index) {
						patternLen2 = 0;
						indexToStartFrom2 = Indexlist.get(indexToPaint2);
						patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen2);
						if(input.input().length()<=indexToStartFrom2)
							indexToStartFrom2  = input.input().length()-1;
						inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom2);
					}
				}
			}
			nextdepth--;
			if( nextdepth < input.pattern().length() && nextdepth!=0 && depth!=inputData.getPattern().length()) {
				this.inputData.getNxtBtn().setEnabled(false);
			}
		}
		else {
			if(!this.inputData.isprev())
				this.depth --;
			else this.depth++;
		}
	}

	@Override
	public void reset(Input<Problem> input) {
		this.input=input;
		inputData = this.input.getSSMI();
		if(inputData.isrst()) {
			int i;
			for(i = 0; i < this.inputData.getInputArr().length; i++) {
				this.inputData.getInputArr()[i].setBackground(Color.WHITE);
			}
			for(i = 0; i < this.inputData.getPattArr().length; i++) {
				this.inputData.getPattArr()[i].setBackground(Color.WHITE);
			}
			this.inputData.getPrevBtn().setEnabled(false);
			this.clear();
			depth = 0;
		}
		KMPSearch(input.pattern(),inputData.getText());
		printlps(input.pattern(),input.getcont(),input.getxCord(),input.getyCord());
	}


	@Override
	public State<Algorithm<Problem>> getState() {
		return this.state;
	}

	@Override
	public void setState(State<Algorithm<Problem>> state) {
		this.state = state;
	}

	@Override
	public Output<Problem> getOutput() {
		this.output.show(container);
		return output;
	}

	public void colorBoard(int index, Color color) {
		int i;
		for(i = 0; i < this.inputData.getPattern().length(); i++) {
			this.inputData.getPattArr()[i].setBackground(color);
			this.inputData.getInputArr()[index - i + 1].setBackground(color);
		}
	}

	public void KMPSearch(String pat, String txt){
		int M = pat.length();
		int N = txt.length();
		lps = new int[M];
		int j = 0; // index for pat[]
		lps = computeLPSArray(pat, M);

		int i = 0; // index for txt[]
		this.updateNextState(0);
		this.depth++;
		while (i < N) {
			if (pat.toUpperCase().charAt(j) == txt.toUpperCase().charAt(i)) {
				j++;
				i++;
			}
			if (j == M) {
				this.updateNextState((i - j));
				if(i != 0)
					this.updateNextState(i);
				this.depth+=2;
				j = lps[j - 1];
			}

			// mismatch after j matches
			else if (i < N && pat.toUpperCase().charAt(j) != txt.toUpperCase().charAt(i)) {
				if (j != 0) {
					j = lps[j - 1];
				}
				i = i + 1;
				this.updateNextState(i);
				this.depth++;
			}
		}

		for(i= 0 ; i < Indexlist.size()-1;i++) {
			if(Indexlist.get(i) == Indexlist.get(i+1)){
				Indexlist.remove(i+1);
				this.depth--;
			}
		} 
		inputData.setdepth(depth);
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
	public int max (int a, int b) { return (a > b)? a: b; } 
	public void printlps(String str,Container cc, int xCord, int yCord) {

		int i,j; 
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

		int c = 0;
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
	public void updateNextState(Integer index) {
		/*
		 * This list used to save the indexes that the Algorithm will start
		 * to search from
		 */
		this.Indexlist.add(index);
	}
	public boolean isEmpty() {
		return this.Indexlist.isEmpty();
	}

	public void clear() {
		this.Indexlist.clear();
	}
}
