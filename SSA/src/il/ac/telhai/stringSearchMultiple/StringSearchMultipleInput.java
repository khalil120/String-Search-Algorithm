package il.ac.telhai.stringSearchMultiple;

import java.awt.Container;

import il.ac.telhai.algorithm.Input;

public class StringSearchMultipleInput implements Input<StringSearchMultiple> {
	String text;
	String pattern;
	
	public StringSearchMultipleInput(String text, String pattern) {
		super();
		this.text = text;
		this.pattern = pattern;
	}

	@Override
	public void show(Container c) {
		// TODO Auto-generated method stub
	}

	public String getText() {
		return text;
	}

	public String getPattern() {
		return pattern;
	}
}
