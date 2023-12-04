import java.util.ArrayList;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
//        test();

        Create c = new Create("CREATOR", 1.0);
        Process p1 = new Process("PROCESSOR1", 1.0, 5);
        Process p2 = new Process("PROCESSOR2", 10.0, 15);
        Process p3 = new Process("PROCESSOR3", 5.0, 15);
        p1.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);
        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        c.setNextElement(p1);
        p1.setNextElement(p2);
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

    private static void test() {
        int testNumber = 0;
        int[][] testData = new int[][] {
                { 2, 1, 1, 1, 5, 5, 5 },
                { 2, 2, 1, 1, 5, 5, 5 },
                { 2, 2, 2, 1, 10, 5, 5 },
                { 2, 2, 2, 5, 10, 10, 5 },
                { 5, 2, 2, 2, 10, 10, 5 },
                { 5, 5, 2, 2, 10, 10, 5 },
                { 5, 5, 5, 5, 10, 10, 10 },
                { 5, 10, 10, 10, 10, 10, 10 },
                { 10, 10, 10, 10, 10, 10, 10 },
                { 10, 10, 15, 15, 10, 10, 10 }
        };

        for (int[] data : testData) {
            testNumber++;
            System.out.println("TEST NUMBER " + testNumber);
            Model model = getModel(data);
            model.simulate(1000.0);
            Element.clear();
        }
    }

    private static Model getModel(int[] data) {
        Create c = new Create("CREATOR", data[0]);
        Process p1 = new Process("PROCESSOR1", data[1], 1);
        Process p2 = new Process("PROCESSOR2", data[2], 1);
        Process p3 = new Process("PROCESSOR3", data[3], 1);
        p1.setMaxqueue(data[4]);
        p2.setMaxqueue(data[5]);
        p3.setMaxqueue(data[6]);
        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);
        ArrayList<Element> list = new ArrayList<>(asList(c, p1, p2, p3));
        return new Model(list);
    }
}