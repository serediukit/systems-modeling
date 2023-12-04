import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriorityProcess extends Process {
    private final ArrayList<Double> nextElementsPriority = new ArrayList<>();

    public PriorityProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
    }

    public void setNextElementsPriority(List<Double> nextElementsPriority) {
        this.nextElementsPriority.addAll(nextElementsPriority);
    }

    @Override
    public Element getNextElement() {
        if (super.getNextElements().size() != nextElementsPriority.size())
            return super.getNextElements().get(0);
        ArrayList<Double> sortedList = new ArrayList<>(nextElementsPriority);
        sortedList.sort(null);
        for (Element e : super.getNextElements()) {
            if (e.getState() == 0) {

            }
        }
        return super.getNextElements().get(0);
    }
}
