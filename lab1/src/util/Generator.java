package util;

import java.util.ArrayList;

public class Generator {
    public static ArrayList<Double> generateExpNumbers(int count, double lambda) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(randExp(lambda));
        }
        return numbers;
    }

    public static double randExp(double lambda)  {
        double rnd = Math.random();
        while (rnd == 0) {
            rnd = Math.random();
        }
        return - (1 / lambda) * Math.log(rnd);
    }

    public static ArrayList<Double> generateNormNumbers(int count, double a, double omega) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(randNorm(a, omega));
        }
        return numbers;
    }

    public static double randNorm(double a, double omega) {
        return omega * generateMU() + a;
    }

    private static double generateMU() {
        double res = 0;
        for (int i = 0; i < 12; i++) {
            res += Math.random() - 6;
        }
        return res;
    }
}
