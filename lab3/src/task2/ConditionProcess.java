package task2;

import java.util.ArrayList;
import java.util.List;

public class ConditionProcess extends Process {
    private final ArrayList<Integer> nextElementsConditions = new ArrayList<>();


    public ConditionProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
    }

    public void setNextElementsConditions(List<Integer> nextElementsConditions) {
        this.nextElementsConditions.addAll(nextElementsConditions);
    }

    @Override
    public Element getNextElement() {
        return super.getNextElements().get(nextElementsConditions.indexOf(0));
    }
}
