package il.ac.telhai.algorithm;

public interface Algorithm<P extends Problem> {
	// A concrete algorithm does not expose any other public methods besides this I/F
	void reset(Input<P> input);
    State<Algorithm<P>> getState();   // The final state of the algorithm (and only it) must have depth zero
    void setState(State<Algorithm<P>> state);
    void step();
    Output<P> getOutput();
}
