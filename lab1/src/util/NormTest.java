package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NormTest {
    final static private int COUNT = 10000;
    final static private int COLUMNS = 40;
    final static private double ALPHA = .05;
    final static private ArrayList<Double> deletedColumns = new ArrayList<>();

    public NormTest(String folder) {
        System.out.println("PERFORMING NORMAL TESTING");
        for (int a = 0; a <= 6; a += 2) {
            for (int sigma = 1; sigma <= 5; sigma+=2) {
                ArrayList<Double> normNumbers = Generator.generateNormNumbers(COUNT, a, sigma);
                saveListToFile(folder + "/NormNumbers_a=" + a + "_sigma=" + sigma + ".txt", normNumbers);

                double averageNum = normNumbers
                        .stream()
                        .mapToDouble(n -> n)
                        .average()
                        .orElse(Double.NaN);
                double dispersion = normNumbers
                        .stream()
                        .mapToDouble(n -> n * n)
                        .average()
                        .orElse(Double.NaN) - averageNum * averageNum;

                double minNum = normNumbers
                        .stream()
                        .mapToDouble(n -> n)
                        .min()
                        .orElse(Double.NaN);
                double maxNum = normNumbers
                        .stream()
                        .mapToDouble(n -> n)
                        .max()
                        .orElse(Double.NaN);
                ArrayList<Double> columns = getColumns(minNum, maxNum);
                ArrayList<Integer> countInColumns = getCountInColumns(normNumbers, columns);

                for (int i = COLUMNS - 1; i >= 0; i--) {
                    if (deletedColumns.contains(columns.get(i))) {
                        columns.remove(i);
                    }
                }
                saveListToFile(folder + "/Columns_a=" + a + "_sigma=" + sigma + ".txt", columns);
                saveIntListToFile(folder + "/CountInColumns_a=" + a + "_sigma=" + sigma + ".txt", countInColumns);

                ArrayList<Double> normTheoretical = getTheoreticalNorm(columns, averageNum, Math.sqrt(dispersion));
                saveListToFile(folder + "/NormTheoretical_a=" + a + "_sigma=" + sigma + ".txt", normTheoretical);

                double chiSquared = getChiSquared(normTheoretical, countInColumns);
                double chiSquaredCritical = ChiCritical.getChiSquaredCritical(1 - ALPHA, columns.size() - 2);

                System.out.println();
                System.out.println();

                System.out.println("A: " + a);
                System.out.println("Омега: " + sigma);
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

    private ArrayList<Double> getTheoreticalNorm(ArrayList<Double> columns, double a, double sigma) {
        ArrayList<Double> theoreticalNorm = new ArrayList<>();
        for (Double column : columns) {
            theoreticalNorm.add(Math.exp(-((column - a) * (column - a)) / (2 * sigma * sigma)) / (sigma * Math.sqrt(2 * Math.PI)));
        }
        return theoreticalNorm;
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
