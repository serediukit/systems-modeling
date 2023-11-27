package task5;

import java.util.ArrayList;

public class Process extends Element {
    private int queue;
    private int maxqueue;
    private int failure;
    private double meanQueue;
    private final double countOfProcesses;
    private ArrayList<Integer> stateOfProcesses = new ArrayList<>();
    private ArrayList<Double> tnextOfProcesses = new ArrayList<>();
    public Process(String name, double delay, int countOfProcesses) {
        super(name, delay);
        this.countOfProcesses = countOfProcesses;
        while (countOfProcesses-- > 0) {
            stateOfProcesses.add(0);
            tnextOfProcesses.add(Double.MAX_VALUE);
        }
        setTnext();
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
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
}