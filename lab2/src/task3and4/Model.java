package task3and4;

public class Model {
    private double[] t = new double[4];
    private double[] sumTime = new double[] { 0, 0, 0 };
    private int[] numProcess = new int[3];
    private int[] queue = new int[3];
    private double[] delayProcess = new double[3];
    private double tnext;
    private double tcurr;
    private double delayCreate;
    private int numCreate;
    private int failure;
    private int state;
    private int maxQueue;
    private int nextEvent;

    public Model(double delayCreate, double[] delayProcess) {
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
    public Model(double delayCreate, double[] delayProcess, int maxQueue) {
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
            } else if (t[2] <= tnext && t[2] <= Math.min(t[1], t[3])) {
                tnext = 2;
                nextEvent = 2;
            } else if (t[3] <= tnext && t[3] <= Math.min(t[1], t[2])) {
                tnext = 3;
                nextEvent = 3;
            }
            tcurr = tnext;

            switch (nextEvent) {
                case 0: eventCreate();
                    break;
                case 1:
                case 2:
                case 3:
                    eventProcess(nextEvent);
            }
            printInfo();
        }
        printStatistic();
    }

    public void printStatistic() {
        System.out.println("numCreate = " + numCreate
                + " failure = " + failure
        );
        for (int i = 0; i < 3; i++) {
            System.out.println("numProcess1 = " + numProcess[i]
                    + " averageTimeProcess1 = " + sumTime[i] / numProcess[i]
            );
        }
    }
    public void printInfo() {
        System.out.println("t = " + tcurr
                + " state = " + state
                + " queueProcess1 = " + queue[0]
                + " queueProcess2 = " + queue[1]
                + " queueProcess3 = " + queue[2]
        );
    }
    public void eventCreate() {
        t[0] = tcurr + getDelayOfCreate();
        numCreate++;
        if (state == 0) {
            state = 1;
            t[1] = tcurr + getDelayOfProcess(1);
        } else {
            if(queue[0] < maxQueue)
                queue[0]++;
            else
                failure++;
        }
    }
    public void eventProcess(int processNumber) {
        t[processNumber] = Double.MAX_VALUE;
        state = 0;
        if (queue[processNumber - 1] > 0) {
            queue[processNumber - 1]--;
            state = processNumber;
            double delayOfProcess = getDelayOfProcess(processNumber);
            t[processNumber] = tcurr + delayOfProcess;
            sumTime[processNumber - 1] += delayOfProcess;
        }
        numProcess[processNumber - 1]++;
    }

    private double getDelayOfCreate() {
        return FunRand.Exp(delayCreate);
    }
    private double getDelayOfProcess(int processNumber) {
        return FunRand.Exp(delayProcess[processNumber - 1]);
    }
}
