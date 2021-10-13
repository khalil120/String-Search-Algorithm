package il.ac.telhai.stringSearchMultiple;
import java.awt.Color;
import java.awt.Container;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import il.ac.telhai.algorithm.Algorithm;
import il.ac.telhai.algorithm.Input;
import il.ac.telhai.algorithm.Output;
import il.ac.telhai.algorithm.Problem;

public class StringSearchMultipleOutput implements Output<StringSearchMultiple> {
	private List<Integer> locations = new LinkedList<>(); // this list for input coloring
	private final List<Integer> locationsPattern = new LinkedList<>(); // this list for pattern coloring
	private Container container;
	private StringSearchMultipleInput inputData;
	private boolean isGreen;
	private final int firstIndex = 0;

	public void addLocation(int index) {
		this.locations.add(index);
	}

	public void addLocationPattern(int index) {
		this.locationsPattern.add(index);
	}

	@Override
	public void show(Container c) {
		Color color ;
		if(!isGreen) color = Color.RED ;
		else color = Color.GREEN ;
		inputData.getPattArr()[locationsPattern.get(firstIndex)].setBackground(color);
		inputData.getInputArr()[locations.get(firstIndex)].setBackground(color);
		locations.remove(firstIndex);
		locationsPattern.remove(firstIndex);
		if(Algorithm.stack.size() == 1 && inputData.isPrev() )	{
			inputData.getPrevBtn().setEnabled(false);
		}

	}

	public void findNextKmp(int patt_ch, int inpt_ch,  LinkedList<Integer> Indexlist,Input<Problem> input,int nextDepth) {
		// finding the next step to enable the next button 
		inputData.setNextDepth(nextDepth);
		if(patt_ch==inpt_ch) {
			boolean index = false;
			if(inputData.getNextDepth() >= Indexlist.size()) inputData.setNextDepth(inputData.getNextDepth()-1);
			int indexToPaint2 = Indexlist.size() - inputData.getNextDepth();
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
				inputData.setNextDepth(inputData.getNextDepth()-1);
				indexToPaint2 = Indexlist.size() - inputData.getNextDepth();
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
	}


	public void enableNextButtonKMP() {
		if( ((inputData.getNextDepth() < inputData.pattern().length() && 
				inputData.getDepth()!= inputData.getPattern().length()) ||
				inputData.getIndexToStartFrom() ==  inputData.getText().length() || 
				inputData.getNextDepth() == inputData.getPattern().length()) 
				&&!inputData.isPrev() && inputData.getIsManual() == 0) {
			inputData.getNxtBtn().setEnabled(false);
		}

	}

	public void findMatching(int patt_ch, int inpt_ch,int indexToStartFrom,int patternLen ,
			boolean matchFound,Input<Problem> input,Stack<Integer> stack,int depth) {
		if(inpt_ch == patt_ch) {
			addOutput(indexToStartFrom,patternLen,true);
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
					addOutput(indexToStartFrom,patternLen,true);
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
			addOutput(indexToStartFrom,patternLen,false);
		}

	} 
	public void addOutput(int indexToStartFrom,int patternLen,boolean value) {
		addLocation(indexToStartFrom);
		addLocationPattern(patternLen);
		isGreen(value);
		show(container);
	}

	public void findMatchingBM(int patt_ch, int inpt_ch,int indexToStartFrom,int patternLen ,
			Input<Problem> input,Stack<Integer> stack,int depth) {
		if(inpt_ch == patt_ch) {
			if (!inputData.isPrev()) {
				if(stack.isEmpty()) stack.push(depth);
				else if(depth!=stack.peek())
					stack.push(depth);
			}
			addOutput(indexToStartFrom,patternLen,true);
			patternLen--;
			indexToStartFrom--;
			while(patternLen > -1) {
				patt_ch = (int)input.pattern().toUpperCase().charAt(patternLen);
				inpt_ch = (int)input.input().toUpperCase().charAt(indexToStartFrom);
				if(inpt_ch == patt_ch ) {
					addOutput(indexToStartFrom,patternLen,true);
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
			addOutput(indexToStartFrom,patternLen,false);
		}
		
	}

	public void findNextBM(int patt_ch, int inpt_ch, LinkedList<Integer> Indexlist, Input<Problem> input,
			int nextDepth,int patternLen2, int indexToStartFrom2) {
		boolean index;
		// finding the next step to enable the next button 
		if(patt_ch==inpt_ch) {
			index = false;
			if(nextDepth >= Indexlist.size()) nextDepth--;
			int indexToPaint2 = Indexlist.size() - nextDepth;
			if(Indexlist.size() == indexToPaint2)
				indexToPaint2--;
			patternLen2 = input.pattern().length() - 1;
			indexToStartFrom2 = Indexlist.get(indexToPaint2);
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

	}
	public void enableNextButtonBM(int nextDepth) {
		if( nextDepth < 0 )	inputData.getNxtBtn().setEnabled(false);
	}
	public List<Integer> getLocations() {
		return locations;
	}

	public void setLocations(List<Integer> locations) {
		this.locations = locations;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public void setInputData(StringSearchMultipleInput inputData) {
		this.inputData=inputData;
	}

	public void isGreen(boolean bool) {
		isGreen = bool;
	}
}
