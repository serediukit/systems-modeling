package task2;

import java.util.ArrayList;
import java.util.List;

public class ChanceProcess extends Process {
    private final ArrayList<Double> nextElementsChances = new ArrayList<>();

    public ChanceProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
    }

    public void setNextElementsChances(List<Double> nextElementsChances) {
        this.nextElementsChances.addAll(nextElementsChances);
        double sum = this.nextElementsChances
                .stream()
                .mapToDouble(x -> x)
                .sum();
        if (sum < 1) {
            this.nextElementsChances.add(1 - sum);
        }
    }

    @Override
    public Element getNextElement() {
        if (super.getNextElements().size() != nextElementsChances.size())
            return super.getNextElements().get(0);
        double totalChance = 0;
        double chance = Math.random();
        for (int i = 0; i < nextElementsChances.size(); i++) {
            totalChance += nextElementsChances.get(i);
            if (chance <= totalChance) {
                System.out.println("Choosing " + super.getNextElements().get(i).getName() + " with chance " + nextElementsChances.get(i));
                return super.getNextElements().get(i);
            }
        }
        return super.getNextElements().get(0);
    }
}
