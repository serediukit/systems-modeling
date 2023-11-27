package task3and4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Create c = new Create("CREATOR", 2.0);
        Process p1 = new Process("PROCESSOR1", 1.0);
        Process p2 = new Process("PROCESSOR2", 1.0);
        Process p3 = new Process("PROCESSOR3", 1.0);
        p1.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);
        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);
        System.out.println("id_0 = " + c.getId()
                + " id_1 = " + p1.getId()
                + " id_2 = " + p2.getId()
                + " id_3 = " + p3.getId()
        );
        ArrayList<Element> list = new ArrayList<>(Arrays.asList(c, p1, p2, p3));
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}