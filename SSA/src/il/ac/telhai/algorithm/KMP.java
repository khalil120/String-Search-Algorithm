package il.ac.telhai.algorithm;
import java.awt.Container;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleOutput;

public class KMP implements Algorithm<Problem>, State<Algorithm<Problem>> {

	private State<Algorithm<Problem>> state;
	private Input<Problem> input;
	private Output<Problem> output;
	private int depth = 0;
	private Container container ;
	private StringSearchMultipleInput inputData ;
	private final StringSearchMultipleOutput outputData = new StringSearchMultipleOutput();
	private boolean bool = false;
	private int nextDepth = 0;
	private int[] lps;

	public KMP() {
		state = this;
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
			if(depth != 0  & !stack.isEmpty() ) 
				stack.pop();
			if(stack.isEmpty()) { 
				inputData.resetBoard();
			}
			else{
				if(Algorithm.stack.size() == 1)	{
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

	@Override
	public void step() {
		if(!bool) {
			bool = true;
			return;
		}
		inputData.resetBoard();
		outputData.setInputData(inputData);
		boolean matchFound = false;
		int indexToPaint = Indexlist.size() -depth;
		int patternLen = 0;
		int inputLength = input.input().length();
		if(indexToPaint < 0)
			indexToPaint = 0;
		int indexToStartFrom = Indexlist.get(indexToPaint);
		int patt_ch, inpt_ch;
		if(indexToStartFrom <= inputLength) {
			if(!stack.isEmpty() && stack.peek() == depth && !inputData.isPrev())
				inputData.setDepth(inputData.getDepth()-1);
			patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
			if(input.input().length()<=indexToStartFrom ) {
				inputData.getNxtBtn().setEnabled(false);
				indexToStartFrom = input.input().length() -1;
			}
			inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
			if(inpt_ch == patt_ch) {
				outputData.addLocation(indexToStartFrom);
				outputData.addLocationPattern(patternLen);
				outputData.isGreen(true);
				outputData.show(container);
				patternLen++;
				indexToStartFrom++;
				while(patternLen < input.pattern().length()) {
					patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
					if(input.input().length()<=indexToStartFrom ) {
						inputData.getNxtBtn().setEnabled(false);
						indexToStartFrom = input.input().length() -1;
					}
					inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
					if(inpt_ch == patt_ch ) {
						outputData.addLocation(indexToStartFrom);
						outputData.addLocationPattern(patternLen);
						outputData.isGreen(true);
						outputData.show(container);
						matchFound = false;
					}else  {

						if (inputData.getIsManual() == 0){
							if(!inputData.isPrev()) {
								inputData.setDepth(inputData.getDepth()-1);
							}
							else if (inputData.isPrev()) {
								inputData.setDepth(inputData.getDepth()+1);
							}
						}
						matchFound = true;
						break;
					}
					patternLen++;
					indexToStartFrom++;
				}
				if (!inputData.isPrev() && !matchFound) {
					if(stack.isEmpty()) stack.push(depth);
					else if(depth!=stack.peek())
						stack.push(depth);
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
				outputData.addLocation(indexToStartFrom);
				outputData.addLocationPattern(patternLen);
				outputData.isGreen(false);
				outputData.show(container);
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
				int patternLen2 = 0;
				if(Indexlist.size()<=indexToPaint2 ) {
					inputData.getNxtBtn().setEnabled(false);
					indexToPaint2 = Indexlist.size() -1;
				}
				int indexToStartFrom2 = Indexlist.get(indexToPaint2);
				if(input.input().length()<=indexToStartFrom2 ) {
					inputData.getNxtBtn().setEnabled(false);
					indexToStartFrom2 = input.input().length() -1;
				}
				patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen2);
				inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom2);
				while (patt_ch!=inpt_ch) {
					nextDepth--;
					indexToPaint2 = Indexlist.size() - nextDepth;
					if(Indexlist.size() <= indexToPaint2 ) {
						inputData.getNxtBtn().setEnabled(false);
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
			nextDepth--;
			if( ((nextDepth < input.pattern().length() && depth!=inputData.getPattern().length()) || indexToStartFrom ==  inputData.getText().length()) 
					&&!inputData.isPrev() && inputData.getIsManual() == 0) {
				inputData.getNxtBtn().setEnabled(false);
			}
		}
		else {
			if(!inputData.isPrev())
				depth --;
			else depth++;
		}
	}

	@Override
	public void reset(Input<Problem> input) {
		this.input=input;
		inputData = input.getSSMI();
		if(inputData.isRst()) {
			inputData.resetBoard();
			inputData.getPrevBtn().setEnabled(false);
			clear();
			depth = 0;
		}
		KMPSearch(input.pattern(),inputData.getText());
	}


	@Override
	public State<Algorithm<Problem>> getState() {
		return state;
	}

	@Override
	public void setState(State<Algorithm<Problem>> state) {
		this.state = state;
	}

	@Override
	public Output<Problem> getOutput() {
		output.show(container);
		return output;
	}

	public void KMPSearch(String pat, String txt){
		int M = pat.length();
		int N = txt.length();
		lps = new int[M];
		int j = 0; // index for pat[]
		lps = computeLPSArray(pat, M);

		int i = 0; // index for txt[]
		updateNextState(0);
		depth++;
		while (i < N) {
			if (pat.toUpperCase().charAt(j) == txt.toUpperCase().charAt(i)) {
				j++;
				i++;
			}
			if (j == M) {
				updateNextState((i - j));
				if(i != 0)
					updateNextState(i);
				depth+=2;
				j = lps[j - 1];
			}

			// mismatch after j matches
			else if (i < N && pat.toUpperCase().charAt(j) != txt.toUpperCase().charAt(i)) {
				if (j != 0) {
					j = lps[j - 1];
				}
				i = i + 1;
				updateNextState(i);
				depth++;
			}
		}

		for(i= 0 ; i < Indexlist.size()-1;i++) {
			if(Indexlist.get(i) == Indexlist.get(i+1)){
				Indexlist.remove(i+1);
				depth--;
			}
		} 
		inputData.setDepth(depth);
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
	public void updateNextState(Integer index) {
		/*
		 * This list used to save the indexes that the Algorithm will start
		 * to search from
		 */
		Indexlist.add(index);
	}

	public void clear() {
		Indexlist.clear();
		stack.clear();
	}
}
