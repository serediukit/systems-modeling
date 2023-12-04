import java.util.ArrayList;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
        Create c = new Create("CREATOR", "exp", 1.0);
        PriorityProcess p1 = new PriorityProcess("PROCESSOR1", "exp", 1.0, 5, 5);
        ChanceProcess p2 = new ChanceProcess("PROCESSOR2", "exp", 10.0, 15, 5);
        Process p3 = new Process("PROCESSOR3", "exp", 5.0, 15, 5);
        c.setNextElement(p1);
        p1.setNextElements(asList(p2, p3));
        p2.setNextElements(asList(p3, p1));
        p2.setNextElementsChances(asList(.7, .3));
        System.out.println("id_0 = " + c.getId()
                + " id_1 = " + p1.getId()
                + " id_2 = " + p2.getId()
                + " id_3 = " + p3.getId()
        );
        ArrayList<Element> list = new ArrayList<>(asList(c, p1, p2, p3));
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}