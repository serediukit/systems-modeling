package task3and4;

public class Process extends Element {
    private int queue;
    private int maxqueue;
    private int failure;
    private double meanQueue;
    public Process(String name, double delay) {
        super(name, delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
    }
    @Override
    public void inAct() {
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
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
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        if (queue > 0) {
            queue--;
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
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
}