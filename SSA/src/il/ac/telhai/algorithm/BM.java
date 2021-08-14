package il.ac.telhai.algorithm;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;


public class BM implements Algorithm<Problem>, State<Algorithm<Problem>> {

	private State<Algorithm<Problem>> state;
	private LinkedList<Integer> Indexlist = new LinkedList<Integer>();
	private Input<Problem> input;
	private Output<Problem> output;
	private int depth = 0;
	private int NO_OF_CHARS = 256;
	private Container container ;
	private StringSearchMultipleInput inputData ;
	private boolean bool = false;

	public BM() {
		state = this;
	}

	@Override
	public void reset(Input<Problem> input) {
		this.input=input;
		inputData = this.input.getSSMI();
		printchartable(input.pattern(),input.getcont(),input.getxCord(),input.getyCord());
		calcMaxDepth();
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
	public void step() {
		//System.out.println("Pattern to look for is: " + this.input.pattern());
		//	System.out.println("Input is:" + this.input.input());
		//System.out.println("Starting new Step with Depth = " + depth);
		int i;
		for(i = 0; i < this.inputData.getInputArr().length; i++) {
		     this.inputData.getInputArr()[i].setBackground(Color.WHITE);
		}
		if(!bool) {
			bool = true;
			return;
		}
		int indexToPaint = this.Indexlist.size() - this.depth;
		int patternLen = input.pattern().length() - 1;
		int inputLength = input.input().length();
		int indexToStartFrom = Indexlist.get(indexToPaint);
		int patt_ch, inpt_ch;
		if(indexToStartFrom < inputLength) {
			patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
			inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
			if(inpt_ch == patt_ch) {
				this.inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
				this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
				patternLen--;
				indexToStartFrom--;
				while(patternLen > -1) {
					patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
					inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
					if(inpt_ch == patt_ch ) {
						this.inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
						this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
					}else break;
					patternLen--;
					indexToStartFrom--;
				}
			}else if (inputData.getIsmanual() == 0){
				inputData.setdepth(inputData.getdepth()-1);
			}
			if (inputData.getIsmanual() == 1 && inpt_ch != patt_ch) {
				this.inputData.getPattArr()[patternLen].setBackground(Color.RED);
				this.inputData.getInputArr()[indexToStartFrom].setBackground(Color.RED);
			}
			this.depth --;
		}
		else {
			this.depth --;
		}
	}

	public void calcMaxDepth() {

		char [] pat = this.input.pattern().toUpperCase().toCharArray();
		char [] txt = this.input.input().toUpperCase().toCharArray();
		int patLen = input.pattern().length();
		int badchar[] = new int[NO_OF_CHARS]; 
		int txtLen = input.input().length();
		badchar = badCharTable(pat, patLen); 

		int s = patLen -1 ;

		/*	when the Data Structure is not empty this means this is not the first call for search method
		 *	then -> clear the Data Structure then store the new data 
		 */
		if(!this.isEmpty())
			this.clear();

		this.depth++;
		this.updateNextState(s);

		int j;
		boolean bool = true;
		while(s < txtLen-1) {
			for(j = 0 ; j < patLen ; j++) {
				if(txt[s] == pat[j]) {
					s+= badchar[j];
					this.depth++;
					this.updateNextState(s);
					bool = false;
					break;
				}else 
					bool = true;

			}
			if(bool) {
				s+=patLen;
				this.depth++;
				this.updateNextState(s);
			}
		}
		inputData.setdepth(depth);
		//System.out.println("number of indexs is: " + this.Indexlist.size());
		//for(int i = 0; i < this.Indexlist.size(); i++)
		//System.out.println(Indexlist.get(i));
	}

	private int[] badCharTable(char []str, int size) { 
		int i ,j;
		int c = 0;
		int vals[] = new int[size];

		for( i = 0 ; i < size ; i++) {
			c = max(1,size-i-1);
			vals[i] = c;
		}

		for( i = 0 ; i < size; i++) {
			for( j = i+1 ; j < size ;j++) {
				if(str[i] == str[j]) {
					vals[i] = vals[j];
					vals[j] = 0;
				}
			}
		}
		return vals;
	}

	public void colorBoard(int index, Color color) {
		int i;
		for(i = 0; i < this.inputData.getPattern().length(); i++) {
			this.inputData.getPattArr()[i].setBackground(color);
			this.inputData.getInputArr()[index - i + 1].setBackground(color);
		}
	}

	@Override
	public Output<Problem> getOutput() {
		this.output.show(container);
		return output;
	}


	public int max (int a, int b) { return (a > b)? a: b; } 
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
			c = this.max(1, str.length()-i-1);
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

	@Override
	public int getDepth() { 
		if (this.depth == inputData.getdepth()&& inputData.getIsmanual() == 0)
			return this.depth;
		else if (inputData.getIsmanual() == 1 && this.depth == inputData.getdepth())  return this.depth;
		else return 0;
	}

	@Override
	public void show(Container c) { //TODO: check this method
		//printchartable(input.pattern(),c,input.getxCord(),input.getyCord());	
	}


}
