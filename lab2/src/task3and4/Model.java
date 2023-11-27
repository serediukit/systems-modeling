package task3and4;

public class Model {
    private double[] t = new double[4];
    private double[] sumQueueLength = new double[] { 0, 0, 0 };
    private int[] numProcess = new int[3];
    private int[] queue = new int[3];
    private double tnext;
    private double tcurr;
    private double delayCreate;
    private double delayProcess;
    private int numCreate;
    private int failure;
    private int state;
    private int maxQueue;
    private int nextEvent;

    public Model(double delayCreate, double delayProcess) {
        this.delayCreate = delayCreate;
        this.delayProcess = delayProcess;
        tnext = 0.0;
        tcurr = tnext;
        t[0] = tcurr;
        t[1] = Double.MAX_VALUE;
        t[2] = Double.MAX_VALUE;
        t[3] = Double.MAX_VALUE;
        maxQueue = 0;
    }
    public Model(double delayCreate, double delayProcess, int maxQueue) {
        this.delayCreate = delayCreate;
        this.delayProcess = delayProcess;
        tnext = 0.0;
        tcurr = tnext;
        t[0] = tcurr;
        t[1] = Double.MAX_VALUE;
        t[2] = Double.MAX_VALUE;
        t[3] = Double.MAX_VALUE;
        this.maxQueue = maxQueue;
    }

    public void simulate(double timeModeling) {
        while (tcurr < timeModeling) {

            tnext = t[0];
            nextEvent = 0;

            if (t[1] <= tnext && t[1] <= Math.min(t[2], t[3])) {
                tnext = t[1];
                nextEvent = 1;
            }
            tcurr = tnext;
            switch (nextEvent) {
                case 0: event0();
                    break;
                case 1: event1();

            }
            printInfo();
        }
        printStatistic();
    }

    public void printStatistic() {
        System.out.println("numCreate = " + numCreate
                + " numProcess = " + numProcess
                + " failure = " + failure
                + " averageQueueLength = " + sumQueueLength / numProcess
        );
    }
    public void printInfo() {
        System.out.println("t = " + tcurr
                + " state = " + state
                + " queue = " + queue
        );
    }
    public void event0() {
        t0 = tcurr + getDelayOfCreate();
        numCreate++;
        if (state == 0) {
            state = 1;
            t1 = tcurr + getDelayOfProcess();
        } else {
            if(queue < maxqueue)
                queue++;
            else
                failure++;
        }
    }
    public void event1() {
        t1 = Double.MAX_VALUE;
        state = 0;
        if (queue > 0) {
            queue--;
            state = 1;
            double delayOfProcess = getDelayOfProcess();
            t1 = tcurr + delayOfProcess;
            sumQueueLength += delayOfProcess;
        }
        numProcess++;
    }

    private double getDelayOfCreate() {
        return FunRand.Exp(delayCreate);
    }
    private double getDelayOfProcess() {
        return FunRand.Exp(delayProcess);
    }
}
