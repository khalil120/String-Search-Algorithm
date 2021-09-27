package il.ac.telhai.algorithm;

import java.awt.Container;

import il.ac.telhai.stringSearchMultiple.StringSearchMultiple;
import il.ac.telhai.stringSearchMultiple.StringSearchMultipleInput;

public interface Input<P extends Problem> {
    void show(Container c);
    String pattern();
    String input();
    int getxCord();   // TODO: MORDO - DECOUPLE GUI FROM LOGIC
    int getyCord();
    Container getcont();
    void setCont(Container c);
    StringSearchMultipleInput getSSMI();
    void isManual(int num);  
    void setRun(Runner<StringSearchMultiple> run);
}
