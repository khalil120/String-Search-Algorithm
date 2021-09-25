package il.ac.telhai.projects.ssa;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class BM extends Algorithm<Integer>{

	private String input, pattern;
	private int NO_OF_CHARS = 256;  // TODO: MORDO CONST?
	private int patLen; 
	private int txtLen;
	private  boolean searchtype;  // TODO: MORDO camelCase 

	public BM(String input, String pattern) {

		this.setDataStructure(new Stack<State<Integer>>());
		this.input = input.toUpperCase();       // TODO: MORDO - This functionality is not specific to BM
		this.pattern = pattern.toUpperCase();
		patLen = pattern.length(); 
		txtLen = input.length();

	}

	public BM(String pattern) {

		this.setDataStructure(new Stack<State<Integer>>());
		this.pattern = pattern.toUpperCase();    // TODOO: MORDO - Refactor code common to various c'tors. use "this()"
		this.input = readFromFile().toUpperCase();
		patLen = pattern.length(); 
		txtLen = input.length();

	}

	private String readFromFile() {
		String data = ""; 
		try {
			File myObj = new File("C:\\Users\\home\\eclipse-workspace\\SSA\\src\\input.txt");
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

	private int[] badCharTable(char []str, int size) { 
		// TODO: MORDO: The functionality of this function is not clear. Document the function
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

	public int max (int a, int b) { return (a > b)? a: b; } // TODO: MORDO use the built-in max function

	public void nextStep() {
		int s = this.currentState();
		search(s);
	}

	public void prevStep() {
		int s = this.prevState();
		search(s);
	}
	
	public boolean isMatch(int index) {
		if (index > txtLen-1) return false;
		int starting = index;
		int i = patLen - 1;   // TODO: MORDO - Why not embedded in the following for statement
		for(; i >= 0; i--) {
			if(input.charAt(index) != pattern.charAt(i))
				return false;
			else {
				index--;
				if(starting == (index + patLen))
					return true;
			}
		}
		return false;
	}

	//automatic search
	public void search() {

		char [] pat = this.pattern.toCharArray();
		char [] txt = this.input.toCharArray();

		int badchar[] = new int[NO_OF_CHARS];   // TODO: MORDO - Overridedn by the next statement

		badchar = badCharTable(pat, patLen); 

		int s = patLen -1 ;
	
		/*	when the stack not empty this means this is not the first call for search method
		 *	then -> clear the stack then store the new data 
		*/
		if(!this.isEmpty())
			this.clear();
		
		this.updateNextState(s);

		int j;
		boolean bool = true;  // TODO: MOROD - More meaningful names. "bool" and "flag" are the worst names possible
		while(s < txtLen-1) {
			for(j = 0 ; j < patLen ; j++) {
				if(txt[s] == pat[j]) {
					s+= badchar[j]; 
					this.updateNextState(s);
					bool = false;
					break;
				}else { 
					bool = true;
				}
			}
			if(bool) {
				s+=patLen;
				this.updateNextState(s);
			}
		}
	}

	// TODO: MORDO - What is "manual"
	//manual search
	public void search(int s) {
		char [] pat = this.pattern.toCharArray();
		char [] txt = this.input.toCharArray();

		int badchar[] = new int[NO_OF_CHARS]; // TODO: MORDO - Overriden by next statement 

		badchar = badCharTable(pat, patLen); 
		this.updateNextState(s);
		while(s <= (txtLen - patLen)) {	
			int j = patLen-1; 
			while(j >= 0 && pat[j] == txt[s+j])
				j--;

			if (j < 0) { 
				s += (s+patLen < txtLen)? patLen-badchar[txt[s+patLen]] : 1;
			} else {
				s += max(1, j - badchar[txt[s+j]]); 
			}
			this.updateNextState(s);
		}
	}
	
	public void printchartable(String str,JFrame f, int xCord, int yCord) {  // TODO: MORDO: camelCase
		
		int i,j; 
		yCord += 80;     // TODO: MORDO: Algorithm should not have GIU concerns.
		char[] chars = str.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
			sb.append(character);
		}

		JButton array[] =  new JButton[str.length()]; // TODO: MORDO: Algorithm should not have GIU concerns.
		                                              // DECOUPLE ALGORITHM FROM GUI !!!!!
		int x = 500  , width = 50  , height = 50;  // TODO: MORDO: Algorithm should not have GIU concerns.

		JLabel patternLbl = new JLabel("BAD CHAR TABLE: ");
		patternLbl.setFont(new Font(patternLbl.getFont().getName(), Font.PLAIN, 25));
		patternLbl.setBounds(500,  yCord, width*5, height);
		f.add(patternLbl);
		yCord += 50;

		JTextField myOutpu = new JTextField("Letters");
		myOutpu.setBounds(500, yCord, width*2, height);
		f.add(myOutpu);
		x = x +150;
		for( i = 0 ; i < sb.length() ; i++) {
			char c = str.charAt(i);
			String s  = String.valueOf(c);  
			array[i] = new JButton(s);
			array[i].setBounds(x,yCord,width,height); 
			x+=width;
			array[i].setBackground(Color.WHITE);
			array[i].setForeground(Color.BLACK);
			f.add(array[i]);
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
		f.add(tmp);

		x = 500 ; yCord += 50; // coordinates 
		JTextField myOutput = new JTextField("values");
		myOutput.setBounds(500, yCord, width*2, height);
		f.add(myOutput);
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
				f.add(arr[i]);
			}
		}
		// if there is no match char
		int k = str.length();
		s  = String.valueOf(k);
		JButton tmpp = new JButton(s);
		tmpp.setBounds(x, yCord, width*3, height);
		tmpp.setBackground(Color.WHITE);
		f.add(tmpp);

	}


	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public boolean isSearchtype() {
		return searchtype;
	}
	public void setSearchtype(boolean searchtype) {
		this.searchtype = searchtype;
	}



	@Override
	public void updateNextState(Integer state) {   // TODO: MORDO - I don't see any reason to override
		this.getStack().push(new State<Integer>(state));
		this.incIndex();
	}

	@Override
	public Integer currentState() {  // TODO: MORDO - I don't see any reason to override
		return this.getStack().get(0).getState();
	}

	@Override   // TODO: MORDO - I don't see any reason to override
	public Integer prevState() {
		//TODO: check possible bug here
		if(!this.getStack().isEmpty()) {
			this.decIndex();
			return this.getStack().pop().getState();
		}
		return null;
	}




}

