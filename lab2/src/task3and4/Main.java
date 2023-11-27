package task3and4;

public class Main {
    public static void main(String[] args) {
        Model model = new Model(2, new double[] {1, 1, 2}, 5);
        model.simulate(1000);
    }
}