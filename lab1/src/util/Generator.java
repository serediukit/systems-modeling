package util;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static ArrayList<Double> generateNumbers(int count) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(Math.random());
        }
        return numbers;
    }
}
