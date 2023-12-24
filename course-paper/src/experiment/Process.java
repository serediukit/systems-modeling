package experiment;

public class Process extends Element {
    private double meanQueue;

    protected int maxQueue;
    protected int queue;
    protected int failure;

    public Process(String name, String distribution, double delayMean, double delayDev, int maxQueue) {
        super(name, distribution, delayMean, delayDev);
        this.maxQueue = maxQueue;
        setState(0);
        setTimeNext(Double.MAX_VALUE);
        queue = 0;
        failure = 0;
        meanQueue = 0;
    }

    @Override
    public void inAct() {
        if (getState() == 0) {
            setState(1);
            setTimeNext(getTimeCurrent() + getDelay());
        } else if (queue < maxQueue) {
            queue++;
        } else {
            failure++;
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        setTimeNext(Double.MAX_VALUE);
        setState(0);
        if (queue > 0) {
            queue--;
            setState(1);
            setTimeNext(getTimeCurrent() + getDelay());
        }
        if (getNextElements() != null) {
            getNextElement().inAct();
        }
    }

    public int getMaxQueue() {
        return maxQueue;
    }

    public int getQueue() {
        return queue;
    }

    public int getFailure() {
        return failure;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure: " + failure);
    }

    @Override
    public void doStatistic(double delta) {
        meanQueue += queue * delta;
    }
}
