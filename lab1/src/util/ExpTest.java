package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ExpTest {
    final static private int count = 10000;
    final static private double alpha = .05;
    final static private int m = 50;
    public ExpTest() {
        for (double lambda = 0.5; lambda <= 0.5; lambda += 0.5) {
            ArrayList<Double> randomNumbers = Generator.generateNumbers(count);
            printArrayList(randomNumbers);

            ArrayList<Double> expNumbers = getExpNumber(randomNumbers, lambda);
            saveListToFile("results/ExpNumbers_lam=" + lambda + ".txt", expNumbers);

            double maxNum = expNumbers.stream().mapToDouble(n -> n).max().orElse(Double.NaN);
            System.out.print(maxNum);




        }
    }

    private ArrayList<Double> getExpNumber(ArrayList<Double> randomNumbers, double lambda) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double num = - (1 / lambda) * Math.log(randomNumbers.get(i));
            numbers.add(num);
        }
        return numbers;
    }

    private void saveListToFile(String filepath, ArrayList<Double> numbers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (Double num : numbers) {
                writer.write(num.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printArrayList(ArrayList<Double> lst) {
        for (Double l : lst) {
            System.out.print(l + " ");
        }
        System.out.println();
    }
}
