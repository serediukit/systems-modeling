import java.util.ArrayList;
import java.util.List;

public class Process extends Element {
    private int queue;
    private int maxqueue;
    private int failure;
    private double meanQueue;
    private final double countOfProcesses;
    private ArrayList<Integer> stateOfProcesses = new ArrayList<>();
    private ArrayList<Double> tnextOfProcesses = new ArrayList<>();
    private ArrayList<Element> nextElements = new ArrayList<>();
    private ArrayList<Double> nextElementsChances = new ArrayList<>();

    public Process(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay);
        this.countOfProcesses = countOfProcesses;
        while (countOfProcesses-- > 0) {
            stateOfProcesses.add(0);
            tnextOfProcesses.add(Double.MAX_VALUE);
        }
        setTnext();
        queue = 0;
        this.maxqueue = maxqueue;
        meanQueue = 0.0;
    }
    @Override
    public void inAct() {
        boolean isFind = false;
        for (int i = 0; i < countOfProcesses; i++) {
            if (stateOfProcesses.get(i) == 0) {
                stateOfProcesses.set(i, 1);
                tnextOfProcesses.set(i, super.getTcurr() + super.getDelay());
                setTnext();
                isFind = true;
                break;
            }
        }
        if (!isFind)  {
            if (queue < maxqueue) {
                queue++;
            } else {
                failure++;
            }
        }
    }
    @Override
    public void outAct() {
        super.outAct();
        for (int i = 0; i < countOfProcesses; i++) {
            if (tnextOfProcesses.get(i) == super.getTcurr()) {
                stateOfProcesses.set(i, 0);
                tnextOfProcesses.set(i, Double.MAX_VALUE);
                setTnext();
                if (queue > 0) {
                    queue--;
                    stateOfProcesses.set(i, 1);
                    tnextOfProcesses.set(i, super.getTcurr() + super.getDelay());
                    setTnext();
                }
                break;
            }
        }
        if (nextElements.size() > 1) {
            Element element = getRandomElement();
            System.out.println("Choosing " + element.getName());
            super.setNextElement(element);
        }

        if (super.getNextElement() != null)
            super.getNextElement().inAct();
    }

    public int getFailure() {
        return failure;
    }
    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }
    @Override
    public void doStatistics(double delta) {
        meanQueue += queue * delta;
    }
    public double getMeanQueue() {
        return meanQueue;
    }

    private void setTnext() {
        super.setTnext(tnextOfProcesses
                .stream()
                .mapToDouble(x -> x)
                .min()
                .orElse(Double.MAX_VALUE));
    }

    public void setNextElements(List<Element> nextElements) {
        this.nextElements.addAll(nextElements);
        if (super.getNextElement() == null)
            super.setNextElement(nextElements.get(0));
    }

    public void setNextElementsChances(List<Double> nextElementsChances) {
        this.nextElementsChances.addAll(nextElementsChances);
    }

    public Element getRandomElement() {
        if (nextElements.size() != nextElementsChances.size())
            return nextElements.get(0);
        double totalChance = 0;
        double chance = Math.random();
        for (int i = 0; i < nextElementsChances.size(); i++) {
            totalChance += nextElementsChances.get(i);
            if (chance <= totalChance)
                return nextElements.get(i);
        }
        return nextElements.get(0);
    }
}