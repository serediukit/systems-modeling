package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExpTest {
    final static private int count = 10000;
    final static private double alpha = .05;
    final static private int m = 50;
    public ExpTest() {
        for (double lambda = 0.5; lambda <= 2.5; lambda += 0.5) {
            ArrayList<Double> randomNumbers = Generator.generateNumbers(count);

            ArrayList<Double> expNumbers = getExpNumber(randomNumbers, lambda);
            saveListToFile("results/ExpNumbers_lam=" + lambda + ".txt", expNumbers);

            double maxNum = expNumbers
                    .stream()
                    .mapToDouble(n -> n)
                    .max()
                    .getAsDouble();
            ArrayList<Double> columns = getColumns(0, maxNum);
            saveListToFile("results/Columns_lam=" + lambda + ".txt", columns);

            ArrayList<Double> expTheoretical = getTheoreticalExp(columns, lambda);
            saveListToFile("results/ExpTheoretical_lam=" + lambda + ".txt", expTheoretical);

            double averageNum = expNumbers
                    .stream()
                    .mapToDouble(n -> n)
                    .average()
                    .getAsDouble();
            double dispersion = expNumbers
                    .stream()
                    .mapToDouble(n -> n * n)
                    .average()
                    .getAsDouble() - averageNum * averageNum;
            System.out.println(averageNum);
            System.out.println(dispersion);

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

    private ArrayList<Double> getColumns(double start, double end) {
        ArrayList<Double> columns = new ArrayList<>();
        double step = (end - start) / m;
        for (int i = 0; i < m; i++) {
            columns.add(i * step);
        }
        return columns;
    }

    private ArrayList<Double> getTheoreticalExp(ArrayList<Double> columns, double lambda) {
        ArrayList<Double> theoreticalExp = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            theoreticalExp.add(1 - Math.exp(- lambda * columns.get(i)));
        }
        return theoreticalExp;
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
