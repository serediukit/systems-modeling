package main;

public class Create extends Element {
    private int queue = 0;
    private double meanQueue = 0;
    private Process nextElement;

    public Create(String name, String distribution, double delayMean, double delayDev) {
        super(name, distribution, delayMean, delayDev);
    }

    @Override
    public void outAct() {
        super.outAct();
        setTimeNext(getTimeCurrent() + getDelay());
        if (getNextElements() != null) {
            queue++;
            Element next = getNextElement();
            while (queue > 0 && next.getState() == 0) {
                next.inAct();
                queue--;
            }
        }
    }

    @Override
    public Element getNextElement() {
        if (nextElement == null) {
            nextElement = getNextElements().get(0);
            return nextElement;
        }
        if (nextElement.getQueue() == nextElement.getMaxQueue() || nextElement.getState() == 1) {
            for (Process p : getNextElements()) {
                if (p.getQueue() < p.getMaxQueue() && p.getState() == 0) {
                    nextElement = p;
                    return nextElement;
                }
            }
        }
        return nextElement;
    }

    @Override
    public void doStatistic(double delta) {
        meanQueue += queue * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }
}
