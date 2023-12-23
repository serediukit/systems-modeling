public class Process extends Element {
    protected int maxQueue;
    protected int queue;
    protected int failure;

    private double meanQueue;
    private double meanTimeLoading;
    private double meanTimeWaiting;
    private int waitsCount;

    public Process(String name, String distribution, double delayMean, double delayDev, int maxQueue) {
        super(name, distribution, delayMean, delayDev);
        this.maxQueue = maxQueue;
        setState(0);
        setTimeNext(Double.MAX_VALUE);
        queue = 0;
        failure = 0;
        meanQueue = 0;
        meanTimeLoading = 0;
        waitsCount = 1;
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
        waitsCount++;
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

    public int getFailure() {
        return failure;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    public double getMeanTimeLoading() {
        return meanTimeLoading;
    }

    public double getMeanTimeWaiting() {
        return meanTimeWaiting;
    }

    public int getWaitsCount() {
        return waitsCount;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure: " + failure);
    }

    @Override
    public void doStatistic(double delta) {
        meanQueue += queue * delta;
        meanTimeLoading += getState() * delta;
        meanTimeWaiting += (1 - getState()) * delta;
    }
}
