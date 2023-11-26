package util;

import java.io.*;
import java.util.ArrayList;

public class UniformTest {
    final static private int COUNT = 10000;
    final static private int COLUMNS = 40;
    final static private double ALPHA = .05;
    final static private ArrayList<Double> deletedColumns = new ArrayList<>();

    public UniformTest(String folder) {
        System.out.println("PERFORMING UNIFORM TESTING");
        int[] pow = new int[] {
                13,
                17,
                23,
                31
        };
        for (int ai = 0; ai < pow.length; ai++) {
            for (int cj = 0; cj < pow.length; cj++) {
                double a = Math.pow(2, pow[ai]);
                double c = Math.pow(5, pow[cj]);
                ArrayList<Double> uniformNumbers = Generator.generateUniformNumbers(COUNT, a, c);
                saveListToFile(folder + "/UniformNumbers_a=2^" + pow[ai] + "_c=5^" + pow[cj] + ".txt", uniformNumbers);

                double averageNum = uniformNumbers
                        .stream()
                        .mapToDouble(n -> n)
                        .average()
                        .orElse(Double.NaN);
                double dispersion = uniformNumbers
                        .stream()
                        .mapToDouble(n -> n * n)
                        .average()
                        .orElse(Double.NaN) - averageNum * averageNum;

                ArrayList<Double> columns = getColumns(0, 1);
                ArrayList<Integer> countInColumns = getCountInColumns(uniformNumbers, columns);

                for (int i = COLUMNS - 1; i >= 0; i--) {
                    if (deletedColumns.contains(columns.get(i))) {
                        columns.remove(i);
                    }
                }
                saveListToFile(folder + "/Columns_a=2^" + pow[ai] + "_c=5^" + pow[cj] + ".txt", columns);
                saveIntListToFile(folder + "/CountInColumns_a=2^" + pow[ai] + "_c=5^" + pow[cj] + ".txt", countInColumns);

                ArrayList<Double> uniformTheoretical = getTheoreticalUniform(columns);
                saveListToFile(folder + "/UniformTheoretical_a=2^" + pow[ai] + "_c=5^" + pow[cj] + ".txt", uniformTheoretical);

                double chiSquared = getChiSquared(uniformTheoretical, countInColumns);
                double chiSquaredCritical = ChiCritical.getChiSquaredCritical(1 - ALPHA, columns.size() - 2);

                System.out.println();
                System.out.println();

                System.out.println("A: 2^" + pow[ai]);
                System.out.println("C: 5^" + pow[cj]);
                System.out.println("Математичне сподівання: " + averageNum);
                System.out.println("Дисперсія: " + dispersion);
                System.out.println("Хі-квадрат: " + chiSquared);
                System.out.println("Хі-квадрат критичне: " + chiSquaredCritical);

                if (chiSquared <= chiSquaredCritical)
                    System.out.println("Розподіл відповідає - нульову гіпотезу не відхиляємо");
                else
                    System.out.println("Розподіл не відповідає - нульову гіпотезу відхиляємо");

                System.out.println();
                System.out.println();
            }
        }
    }

    private ArrayList<Double> getColumns(double minNum, double maxNum) {
        ArrayList<Double> columns = new ArrayList<>();
        double step = (maxNum - minNum) / COLUMNS;
        for (int i = 0; i < COLUMNS; i++) {
            columns.add(minNum + i * step);
        }
        return columns;
    }

    private ArrayList<Integer> getCountInColumns(ArrayList<Double> numbers, ArrayList<Double> columns) {
        ArrayList<Integer> count = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < COLUMNS - 1; i++) {
            while (c < 5 && i < COLUMNS - 1) {
                for (Double num : numbers) {
                    if (num >= columns.get(i) && num < columns.get(i + 1)) {
                        c++;
                    }
                }
                if (c < 5) {
                    i++;
                    if (i < COLUMNS - 1) {
                        deletedColumns.add(columns.get(i));
                    }
                }
            }
            if (c >= 5) {
                count.add(c);
            } else {
                count.set(count.size() - 1, count.get(count.size() - 1) + c);
            }
            c = 0;
        }
        for (Double num : numbers) {
            if (num >= columns.get(COLUMNS - 1)) {
                c++;
            }
        }
        if (c >= 5) {
            count.add(c);
        } else {
            count.set(count.size() - 1, count.get(count.size() - 1) + c);
            deletedColumns.add(columns.get(COLUMNS - 1));
        }
        return count;
    }

    private ArrayList<Double> getTheoreticalUniform(ArrayList<Double> columns) {
        ArrayList<Double> theoreticalUniform = new ArrayList<>();
        for (Double column : columns) {
            theoreticalUniform.add((double) 1 / COLUMNS);
        }
        return theoreticalUniform;
    }

    private double getChiSquared(ArrayList<Double> theoretical, ArrayList<Integer> statistic) {
        double chiSquared = 0;
        for (int i = 0; i < statistic.size(); i++) {
            chiSquared += Math.pow(theoretical.get(i) - (double) statistic.get(i) / COUNT, 2) / theoretical.get(i);
        }
        return chiSquared;
    }

    private void saveListToFile(String filepath, ArrayList<Double> numbers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (Double num : numbers) {
                writer.write(num.toString() + "\n");
            }
            writer.close();
        } catch (IOException ignored) {}
    }

    private void saveIntListToFile(String filepath, ArrayList<Integer> numbers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (Integer num : numbers) {
                writer.write(num.toString() + "\n");
            }
            writer.close();
        } catch (IOException ignored) {}
    }
}
