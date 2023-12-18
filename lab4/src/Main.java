import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
        int testType = 2;
        int testCount = 10;
        Map<Integer, Double> results = new HashMap<>();

        for (int n = 10; n <= 300; n += 10) {
            double sum = .0;
            for (int test = 0; test < testCount; test++) {
                Model m;
                if (testType == 1) {
                    m = getFirstTypeModel(n);
                } else {
                    m = getSecondTypeModel(n);
                }
                long startTime = System.currentTimeMillis();
                m.simulate(1000);
                long endTime = System.currentTimeMillis();
                sum += (endTime - startTime);
            }
            results.put(n, sum / testCount);
            System.out.println("N = " + n + " - done");
        }
        if (testType == 1)
            saveInFiles("First type results.txt", new TreeMap<>(results));
        else
            saveInFiles("Second type results.txt", new TreeMap<>(results));
    }

    private static Model getFirstTypeModel(int n) {
        Create c = new Create("CREATOR", "exp", 1);
        ArrayList<Element> elements = new ArrayList<>(List.of(c));
        for (int i = 0; i < n; i++) {
            Process p = new Process("PROCESS " + (i + 1), "exp", 1, 1, Integer.MAX_VALUE);
            elements.get(elements.size() - 1).setNextElement(p);
            elements.add(p);
        }
        return new Model(elements);
    }

    private static Model getSecondTypeModel(int n) {
        Create c = new Create("CREATOR", "exp", 1);
        ArrayList<Process> processElements = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Process p = new Process("PROCESS " + (i + 1), "exp", 1, 1, Integer.MAX_VALUE);
            for (int j = 1; j < i; j++) {
                processElements.get(j).setNextElements(List.of(p));
            }
            processElements.add(p);
        }
        c.setNextElement(processElements.get(1));
        ArrayList<Element> elements = new ArrayList<>();
        elements.add(c);
        elements.addAll(processElements);
        return new Model(elements);
    }

    private static void saveInFiles(String filepath, Map<Integer, Double> m) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (Integer k : m.keySet()) {
//                writer.write(k.toString() + " " + m.get(k) + "\n");
                writer.write(m.get(k) + "\n");
            }
            writer.close();
        } catch (IOException ignored) {}
    }
}