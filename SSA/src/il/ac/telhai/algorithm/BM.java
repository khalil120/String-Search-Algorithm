package il.ac.telhai.algorithm;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;

import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;

public class BM implements Algorithm<Problem>, State<Algorithm<Problem>> {

	private State<Algorithm<Problem>> state;
	private Input<Problem> input;
	private Output<Problem> output;
	private int depth = 0;
	private Container container ;
	private StringSearchMultipleInput inputData ;
	private boolean bool = false;
	private int nextDepth = 0;

	public BM() {
		state = this;
	}

	@Override
	public void reset(Input<Problem> input) {
		this.input=input;
		inputData = this.input.getSSMI();
		if(inputData.isRst()) {
			for(JButton btn: inputData.getPattArr())
				btn.setBackground(Color.WHITE);

			for(JButton btn: inputData.getInputArr())
				btn.setBackground(Color.WHITE);
			
			inputData.getPrevBtn().setEnabled(false);
			clear();
			depth = 0;
		}
		calcMaxDepth();
	}

	@Override
	public State<Algorithm<Problem>> getState() {
		return this.state;
	}
	public void setState(State<Algorithm<Problem>> state) {
		this.state = state;
	}


	@Override
	public void step() {
		int i;
		for(i = 0; i < inputData.getInputArr().length; i++) {
			inputData.getInputArr()[i].setBackground(Color.WHITE);
		}
		for(i = 0; i < inputData.getPattArr().length; i++) {
			inputData.getPattArr()[i].setBackground(Color.WHITE);
		}
		if(!bool) {
			bool = true;
			return;
		}
		int indexToPaint = Indexlist.size() - depth;
		if(Indexlist.size() == indexToPaint)
			indexToPaint--;
		int patternLen = input.pattern().length() - 1;
		int inputLength = input.input().length();
		int indexToStartFrom = Indexlist.get(indexToPaint);
		int patt_ch, inpt_ch;
		if(indexToStartFrom <= inputLength) {
			if(!stack.isEmpty() && stack.peek() == depth && !inputData.isPrev())
				inputData.setDepth(inputData.getDepth()-1);
			patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
			if(indexToStartFrom >=input.input().length())
				indexToStartFrom = input.input().length()-1;
			inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom); 
			if(inpt_ch == patt_ch) {
				if (!inputData.isPrev()) {
					if(stack.isEmpty()) stack.push(depth);
					else if(depth!=stack.peek())
						stack.push(depth);
				}
				inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
				inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
				patternLen--;
				indexToStartFrom--;
				while(patternLen > -1) {
					patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
					inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
					if(inpt_ch == patt_ch ) {
						inputData.getPattArr()[patternLen].setBackground(Color.GREEN);
						inputData.getInputArr()[indexToStartFrom].setBackground(Color.GREEN);
					}else break;
					patternLen--;
					indexToStartFrom--;
				}
			}else if (inputData.getIsManual() == 0){
				if(!inputData.isPrev()) {
					inputData.setDepth(inputData.getDepth()-1);
				}
				else if (inputData.isPrev()) {
					inputData.setDepth(inputData.getDepth()+1);
				}
			}
			if (inputData.getIsManual() == 1 && inpt_ch != patt_ch) {
				if (!inputData.isPrev())
					stack.push(depth);
				inputData.getPattArr()[patternLen].setBackground(Color.RED);
				inputData.getInputArr()[indexToStartFrom].setBackground(Color.RED);
			}
			if(!inputData.isPrev()) {
				depth --;
			}
			else depth++;
			nextDepth = depth;
			// finding the next step to enable the next button 
			if(patt_ch==inpt_ch) {
				boolean index = false;
				if(nextDepth >= Indexlist.size()) nextDepth--;
				int indexToPaint2 = Indexlist.size() - nextDepth;
				if(Indexlist.size() == indexToPaint2)
					indexToPaint2--;
				int patternLen2 = input.pattern().length() - 1;
				int indexToStartFrom2 = Indexlist.get(indexToPaint2);
				patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen2);
				inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom2);
				while (patt_ch != inpt_ch) {
					nextDepth--;
					indexToPaint2 = Indexlist.size() - nextDepth;
					if(Indexlist.size() <= indexToPaint2  ) {
						inputData.getNxtBtn().setEnabled(false);
						index = true;
						break;
					}
					if(!index) {
						patternLen2 = input.pattern().length() - 1;
						indexToStartFrom2 = Indexlist.get(indexToPaint2);
						patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen2);
						if(input.input().length()<indexToStartFrom2)
							indexToStartFrom2  = input.input().length()-1;
						inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom2);
					}
				}
			}
			nextDepth--;
			if( nextDepth < 0 )	inputData.getNxtBtn().setEnabled(false);
		}
		else {
			if(!inputData.isPrev())
				depth --;
			else depth++;
		}
	}

	public void calcMaxDepth() {
		char [] pat = input.pattern().toUpperCase().toCharArray();
		char [] txt = input.input().toUpperCase().toCharArray();
		int patLen = input.pattern().length();
		int[] badchar;
		int txtLen = input.input().length();
		badchar = badCharTable(pat, patLen); 

		int s = patLen -1 ;

		/*	when the Data Structure is not empty this means this is not the first call for search method
		 *	then -> clear the Data Structure then store the new data 
		 */
		if(!isEmpty())
			clear();
		else depth = 0;
	    depth++;
		updateNextState(s);

		int j;
		boolean bool = true;
		while(s < txtLen-1) {
			for(j = 0 ; j < patLen ; j++) {
				if(txt[s] == pat[j]) {
					s+= badchar[j];
					depth++;
					updateNextState(s);
					bool = false;
					break;
				}else 
					bool = true;

			}
			if(bool) {
				s+=patLen;
				depth++;
				updateNextState(s);
			}
		}
		inputData.setDepth(depth);
	}

	/**
	 * badCharTable method for BM Algorithm used to calculate the value of the jump for mismatch on specific char from the string
	 * @param str equal to the pattern string
	 * @param size pattern length
	 * @return badCharTable with jumping values
	 */
	private int[] badCharTable(char []str, int size) { 
		int i ,j;
		int c = 0;
		int vals[] = new int[size];

		for( i = 0 ; i < size ; i++) {
			c = Math.max(1,size-i-1);
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
		for(i = 0; i < inputData.getPattern().length(); i++) {
			inputData.getPattArr()[i].setBackground(color);
			inputData.getInputArr()[index - i + 1].setBackground(color);
		}
	}

	@Override
	public Output<Problem> getOutput() {
		output.show(container);
		return output;
	}

	
	public void updateNextState(Integer index) {
		/*
		 * This list used to save the indexes that the Algorithm will start
		 * to search from
		 */
		Indexlist.add(index);
	}
	public boolean isEmpty() {
		return Indexlist.isEmpty();
	}

	public void clear() {
		Indexlist.clear();
		stack.clear();
	}

	@Override
	public int getDepth() { 
		if((inputData.getDepth() == nextDepth || depth == nextDepth) && inputData.getIsManual() == 1)
	    	depth--;
		if (depth == inputData.getDepth()&& inputData.getIsManual() == 0)
			return depth;
		else if (inputData.getIsManual() == 1 && depth == inputData.getDepth())  return depth;
		return 0;
	}

	@Override
	public void show(Container c) {
		if(inputData.isPrev() ) {
			if(depth != 0 && !stack.isEmpty()) 
				stack.pop();
			if(stack.isEmpty()) { 
				int i;
				for(i = 0; i < inputData.getInputArr().length; i++) {
					inputData.getInputArr()[i].setBackground(Color.WHITE);
				}
				for(i = 0; i < inputData.getPattArr().length; i++) {
					inputData.getPattArr()[i].setBackground(Color.WHITE);
				}
			}
			else{
				if(stack.size() == 1)	{
					inputData.getPrevBtn().setEnabled(false);
					inputData.setDepth(depth);
				}
				if(depth == 0 ) {
					depth++;
					inputData.setDepth(depth);
					stack.pop();
				}
				int tmp = stack.peek();
				while (tmp >= depth) {
					step();
				}
				depth--;
			}
		}
	}


}
