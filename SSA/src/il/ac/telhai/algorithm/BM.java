package il.ac.telhai.algorithm;
import java.awt.Container;

import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleOutput;

public class BM implements Algorithm<Problem>, State<Algorithm<Problem>> {

	private State<Algorithm<Problem>> state;
	private Input<Problem> input;
	private Output<Problem> output;
	private int depth = 0;
	private Container container ;
	private StringSearchMultipleInput inputData ;
	private final StringSearchMultipleOutput outputData = new StringSearchMultipleOutput();
	private boolean bool = false;
	private int nextDepth = 0;
	private int indexToStartFrom;

	public BM() {
		state = this;
		this.clear();
	}

	@Override
	public void reset(Input<Problem> input) {
		this.input=input;
		inputData = this.input.getSSMI();
		if(inputData.isRst()) {
			inputData.resetBoard();
			inputData.getPrevBtn().setEnabled(false);
			clear();
		}
		calcMaxDepth();
	}

	@Override
	public void step() {

		int indexToPaint = Indexlist.size() - depth;
		int patternLen = input.pattern().length() - 1;
		int inputLength = input.input().length();
		int indexToStartFrom;
		int patternLen2 = 0, indexToStartFrom2 = 0;
		int patt_ch, inpt_ch;
		boolean matchFound = false;
		
		if(!bool) {
			bool = true;
			return;
		}
		inputData.resetBoard();
		outputData.setInputData(inputData);
		if(Indexlist.size() == indexToPaint)
			indexToPaint--;
		indexToStartFrom = Indexlist.get(indexToPaint);
		if(indexToStartFrom <= inputLength) {
			if(!stack.isEmpty() && stack.peek() == depth && !inputData.isPrev())
				inputData.setDepth(inputData.getDepth()-1);
			patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
			if(indexToStartFrom >=input.input().length())
				indexToStartFrom = input.input().length()-1;
			inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom); 
			outputData.findMatchingBM(patt_ch, inpt_ch, indexToStartFrom, patternLen, input, stack, depth,matchFound);
			if(!inputData.isPrev()) {
				depth --;
			}
			else depth++;
			nextDepth = depth;
			outputData.findNextBM(patt_ch, inpt_ch, Indexlist, input,nextDepth,patternLen2, indexToStartFrom2);
			nextDepth--;
			outputData.enableNextButtonBM(nextDepth);
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
		int[] badCharTable;
		int txtLen = input.input().length();
		badCharTable = badCharTable(pat, patLen);

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
					s+= badCharTable[j];
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

	@Override
	public Output<Problem> getOutput() {
		output.show(container);
		return output;
	}



	@Override
	public int getDepth() { 
		if((inputData.getDepth() == nextDepth || depth == nextDepth) && inputData.getIsManual() == 1)
	    	depth--;
		if (depth == inputData.getDepth() && inputData.getIsManual() == 0) {
			return depth;
		}if(depth == nextDepth && inputData.getIsManual() == 0 ) return depth--;
		else if (inputData.getIsManual() == 1 && depth == inputData.getDepth())  return depth;
		return 0;
	}

	@Override
	public void show(Container c) {

		int stackHead;

		if(inputData.isPrev() ) {
			if(depth != 0 && !stack.isEmpty()) 
				stack.pop();
			if(stack.isEmpty()) { 
			     inputData.resetBoard();
			}
			else{
				if(stack.size() == 1)	{
					inputData.setDepth(depth);
				}
				if(depth == 0 ) {
					depth++;
					inputData.setDepth(depth);
					stack.pop();
				}
				stackHead = stack.peek();
				while (stackHead >= depth) {
					step();
				}
				depth--;
			}
		}
		inputData.setNextDepth(nextDepth);
		inputData.setIndexToStartFrom(indexToStartFrom);
	}

	@Override
	public State<Algorithm<Problem>> getState() {
		return this.state;
	}
	public void setState(State<Algorithm<Problem>> state) {
		this.state = state;
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
		depth = 0;
	}

}
