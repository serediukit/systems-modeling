public class Process extends Element {
    protected int maxQueue;
    protected int queue;
    protected int failure;

    private double meanQueue;

    public Process(String name, String distribution, double delayMean, double delayDev) {
        super(name, distribution, delayMean, delayDev);
        setState(0);
        setTimeNext(Double.MAX_VALUE);
        queue = 0;
        maxQueue = Integer.MAX_VALUE;
        failure = 0;
        meanQueue = 0;
    }

    @Override
    public void inAct() {
        if (getState() == 0 && (queue + 1) >= maxQueue) {
            queue = 0;
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
        if (queue == maxQueue) {
            queue = 0;
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

    public void setMaxQueue(int maxQueue) {
        this.maxQueue = maxQueue;
    }

    public int getQueue() {
        return queue;
    }

    public void increaseQueue() {
        queue++;
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
