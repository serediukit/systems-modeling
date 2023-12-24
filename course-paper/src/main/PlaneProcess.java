package main;

public class PlaneProcess extends Process {
    private double meanTimeLoading;
    private double meanTimeWaiting;
    private int waitsCount;

    public PlaneProcess(String name, String distribution, double delayMean, double delayDev, int maxQueue) {
        super(name, distribution, delayMean, delayDev, maxQueue);
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
        quantity++;
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
    public void doStatistic(double delta) {
        super.doStatistic(delta);
        meanTimeLoading += getState() * delta;
        meanTimeWaiting += (1 - getState()) * delta;
    }
}
