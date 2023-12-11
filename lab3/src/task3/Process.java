package task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Process extends Element {
    protected int queue;
    protected ArrayList<Integer> typeQueues;
    protected int maxqueue;
    protected int failure;
    private double meanQueue;
    protected final int countOfProcesses;
    protected final ArrayList<Integer> stateOfProcesses = new ArrayList<>();
    protected final ArrayList<Double> tnextOfProcesses = new ArrayList<>();
    protected final ArrayList<Element> nextElements = new ArrayList<>();


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
        typeQueues = new ArrayList<>(Collections.nCopies(4, 0));
    }
    @Override
    public void inAct(int type) {
        boolean isFind = false;
        for (int i = 0; i < countOfProcesses; i++) {
            if (stateOfProcesses.get(i) == 0) {
                stateOfProcesses.set(i, type + 1);
                double tempDelay = super.getDelay();
                Result.time[type > 2 ? 1 : type] += tempDelay;
                tnextOfProcesses.set(i, super.getTcurr() + tempDelay);
                setTnext();
                isFind = true;
                break;
            }
        }
        if (!isFind)  {
            if (typeQueues.get(type) < maxqueue) {
                typeQueues.set(type, typeQueues.get(type) + 1);
            } else {
                failure++;
            }
        }
    }
    @Override
    public void outAct() {
        super.outAct();
        int nextType = 0;
        for (int i = 0; i < countOfProcesses; i++) {
            if (tnextOfProcesses.get(i) == super.getTcurr()) {
                nextType = stateOfProcesses.get(i) - 1;
                stateOfProcesses.set(i, 0);
                tnextOfProcesses.set(i, Double.MAX_VALUE);
                setTnext();
                for (int j = 0; j < typeQueues.size(); j++) {
                    if (typeQueues.get(j) > 0) {
                        typeQueues.set(j, typeQueues.get(j) - 1);
                        stateOfProcesses.set(i, j + 1);
                        double tempDelay = getDelay();
                        Result.time[j > 2 ? 1 : j] += tempDelay;
                        tnextOfProcesses.set(i, super.getTcurr() + tempDelay);
                        setTnext();
                        break;
                    }
                }
                break;
            }
        }

        if (nextElements.size() > 1) {
            getNextElements().get(nextType % 3).inAct(nextType);
        } else if (super.getNextElement() != null) {
            super.getNextElement().inAct(nextType);
        }
    }

//    @Override
//    public Element getNextElement() {
//        return nextElements.get(0);
//    }

    public int getFailure() {
        return failure;
    }

    @Override
    public void printInfo() {
//        super.printInfo();
//        System.out.println("failure = " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        for (int type = 0; type < typeQueues.size(); type++) {
            Result.time[type > 2 ? 1 : type] += typeQueues.get(type) * delta;
        }
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    protected void setTnext() {
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

    public ArrayList<Element> getNextElements() {
        return nextElements;
    }
    public int getQueue() {
        return queue;
    }
    public int getMaxqueue() {
        return maxqueue;
    }

    public boolean isFree() {
        for (Integer state : stateOfProcesses) {
            if (state == 0)
                return true;
        }
        return false;
    }
}