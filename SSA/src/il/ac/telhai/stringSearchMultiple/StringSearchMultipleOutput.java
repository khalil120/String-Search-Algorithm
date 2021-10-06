package il.ac.telhai.stringSearchMultiple;
import java.awt.Color;
import java.awt.Container;
import java.util.LinkedList;
import java.util.List;
import il.ac.telhai.algorithm.Output;

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
