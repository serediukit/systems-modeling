package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PriorityProcess extends Process {
    private final ArrayList<Integer> nextElementsPriority = new ArrayList<>();

    public PriorityProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
    }

    public void setNextElementsPriority(List<Integer> nextElementsPriority) {
        this.nextElementsPriority.addAll(nextElementsPriority);
    }

    @Override
    public Element getNextElement() {
        if (super.getNextElements().size() != nextElementsPriority.size())
            return super.getNextElements().get(0);
        ArrayList<Integer> sortedList = new ArrayList<>(nextElementsPriority);
        sortedList.sort(null);
        for (Integer priority : sortedList) {
            for (int i = 0; i < nextElementsPriority.size(); i++) {
                if (Objects.equals(priority, nextElementsPriority.get(i))) {
                    Process np = (Process) super.getNextElements().get(i);
                    if (np.isFree()) {
                        System.out.println("Choosing " + super.getNextElements().get(i).getName() + " with priority " + nextElementsPriority.get(i));
                        return super.getNextElements().get(i);
                    }
                }
            }
        }
        Process temp = (Process) super.getNextElements().get(0);
        int minQueue = temp.getQueue();
        for (int i = 1; i < super.getNextElements().size(); i++) {
            temp = (Process) super.getNextElements().get(i);
            if (temp.getQueue() < minQueue) {
                minQueue = temp.getQueue();
            }
        }
        for (Integer priority : sortedList) {
            for (int i = 0; i < nextElementsPriority.size(); i++) {
                if (Objects.equals(priority, nextElementsPriority.get(i))) {
                    Process np = (Process) super.getNextElements().get(i);
                    if (np.getQueue() == minQueue) {
                        System.out.println("Choosing " + super.getNextElements().get(i).getName() + " with priority " + nextElementsPriority.get(i));
                        return super.getNextElements().get(i);
                    }
                }
            }
        }
        System.out.println("Choosing " + super.getNextElements().get(0).getName() + " with priority " + nextElementsPriority.get(0));
        return super.getNextElements().get(0);
    }
}
