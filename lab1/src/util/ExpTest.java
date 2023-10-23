package util;

import org.apache.commons.math3.analysis.function.Histogram;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExpTest {
    final static private int COUNT = 10000;
    final static private int COLUMNS = 40;
    final static private double ALPHA = .05;
    final static private ArrayList<Double> deletedColumns = new ArrayList<>();

    public ExpTest(String folder) {
        for (double lambda = .5; lambda <= 5.0; lambda += 1.5) {
            ArrayList<Double> expNumbers = Generator.generateExpNumbers(COUNT, lambda);
            saveListToFile(folder + "/ExpNumbers_lam=" + lambda + ".txt", expNumbers);

            double maxNum = expNumbers
                    .stream()
                    .mapToDouble(n -> n)
                    .max()
                    .orElse(Double.NaN);
            ArrayList<Double> columns = getColumns(maxNum);
            ArrayList<Integer> countInColumns = getCountInColumns(expNumbers, columns);

            for (int i = COLUMNS - 1; i >= 0; i--) {
                if (deletedColumns.contains(columns.get(i))) {
                    columns.remove(i);
                }
            }

            saveListToFile(folder + "/Columns_lam=" + lambda + ".txt", columns);
            saveIntListToFile(folder + "/CountInColumns_lam=" + lambda + ".txt", countInColumns);

            double averageNum = expNumbers
                    .stream()
                    .mapToDouble(n -> n)
                    .average()
                    .orElse(Double.NaN);
            double dispersion = expNumbers
                    .stream()
                    .mapToDouble(n -> n * n)
                    .average()
                    .orElse(Double.NaN) - averageNum * averageNum;

            ArrayList<Double> expTheoretical = getTheoreticalExp(columns, lambda);
            saveListToFile(folder + "/ExpTheoretical_lam=" + lambda + ".txt", expTheoretical);

            ArrayList<Double> centerOfColumns = getCenterColumns(columns);
            ArrayList<Double> theoreticalForCentredColumns = getTheoreticalExp(centerOfColumns, lambda);
            double chiSquared = getChiSquared(theoreticalForCentredColumns, countInColumns);
            double chiSquaredCritical = ChiCritical.getChiSquaredCritical(1 - ALPHA, columns.size() - 1);

            System.out.println();
            System.out.println();

            System.out.println("Лямбда: " + lambda);
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

    private ArrayList<Double> getColumns(double maxNum) {
        ArrayList<Double> columns = new ArrayList<>();
        double step = maxNum / COLUMNS;
        for (int i = 0; i < COLUMNS; i++) {
            columns.add(i * step);
        }
        return columns;
    }

    private ArrayList<Integer> getCountInColumns(ArrayList<Double> numbers, ArrayList<Double> columns) {
        ArrayList<Integer> count = new ArrayList<>();
        int c;
        for (int i = 0; i < COLUMNS - 1; i++) {
            c = 0;
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
        }
        c = 0;
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

    private ArrayList<Double> getTheoreticalExp(ArrayList<Double> columns, double lambda) {
        ArrayList<Double> theoreticalExp = new ArrayList<>();
        for (Double column : columns) {
            theoreticalExp.add(lambda * Math.exp(-lambda * column));
        }
        return theoreticalExp;
    }

    private ArrayList<Double> getCenterColumns(ArrayList<Double> columns) {
        ArrayList<Double> centerOfColumns =  new ArrayList<>();
        for (int i = 0; i < columns.size() - 1;  i++) {
            centerOfColumns.add((columns.get(i) + columns.get(i + 1)) / 2);
        }
        return centerOfColumns;
    }

    private double getChiSquared(ArrayList<Double> theoretical, ArrayList<Integer> statistic) {
        System.out.println(theoretical);
        System.out.println(statistic);
        double chiSquared = 0;
        for (int i = 0; i < statistic.size(); i++) {
            chiSquared += Math.pow((double) statistic.get(i) - theoretical.get(i), 2) / theoretical.get(i);
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

    private void printArrayList(ArrayList<Double> lst) {
        for (Double l : lst) {
            System.out.print(l + " ");
        }
        System.out.println();
    }
}
