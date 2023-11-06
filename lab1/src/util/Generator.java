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
}
