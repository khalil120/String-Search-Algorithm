package il.ac.telhai.projects.ssa;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;


public class BM  extends Algorithm<Integer>{

	private String input, pattern;
	private int NO_OF_CHARS = 256;
	private int patLen; 
	private int txtLen;
	private  boolean searchtype;

	public BM(String input, String pattern) {

		this.setDataStructure(new Stack<State<Integer>>());
		this.input = input;
		this.pattern = pattern;
		patLen = pattern.length(); 
		txtLen = input.length();

	}

	public BM(String pattern) {

		this.setDataStructure(new Stack<State<Integer>>());
		this.pattern = pattern;
		this.input = readFromFile();
		patLen = pattern.length(); 
		txtLen = input.length();

	}

	private String readFromFile() {
		String data = ""; 
		try {
			File myObj = new File("C:\\Users\\home\\eclipse-workspace\\SSA\\src\\il\\input.txt");
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

	public int max (int a, int b) { return (a > b)? a: b; } 

	public void nextStep() {
		int s = this.currentState();
		search(s);
	}

	public void prevStep() {
		int s = this.prevState();
		search(s);
	}
	
	public boolean isMatch(int index) {
		int starting = index;
		int i = patLen - 1;
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

		int badchar[] = new int[NO_OF_CHARS]; 

		badchar = badCharTable(pat, patLen); 

		int s = patLen -1 ;
		this.updateNextState(s);

		int j;
		boolean bool = true;
		while(s < txtLen-1) {
			for(j = 0 ; j < patLen ; j++) {
				if(txt[s] == pat[j]) {
					s+= badchar[j]; 
					this.updateNextState(s);
					//System.out.println("s =  "+ s );
					bool = false;
					break;
				}else { 
					bool = true;
				}
			}
			if(bool) {
				s+=patLen;
				this.updateNextState(s);
				//System.out.println("s =  "+ s);
			}
		}
	}

	//manual search
	public void search(int s) {
		char [] pat = this.pattern.toCharArray();
		char [] txt = this.input.toCharArray();

		int badchar[] = new int[NO_OF_CHARS]; 

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
	public void updateNextState(Integer state) {
		this.getStack().push(new State<Integer>(state));
		this.incIndex();
	}

	@Override
	public Integer currentState() {
		return this.getStack().get(0).getState();
	}

	@Override
	public Integer prevState() {
		//TODO: check possible bug here
		if(!this.getStack().isEmpty()) {
			this.decIndex();
			return this.getStack().pop().getState();
		}
		return null;
	}




}

