package util;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static ArrayList<Double> generateExpNumbers(int count, double lambda) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(randExp(lambda));
        }
        return numbers;
    }

    public static double randExp(double lambda)  {
        return - (1 / lambda) * Math.log(Math.random());
    }
}
