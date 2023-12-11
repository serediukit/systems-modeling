package task3;

public class ChangingProcess extends Process {
    public ChangingProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
    }

    @Override
    public void inAct(int type) {
        boolean isFind = false;
        for (int i = 0; i < countOfProcesses; i++) {
            if (stateOfProcesses.get(i) == 0) {
                stateOfProcesses.set(i, 4);
                double tempDelay = super.getDelay();
                Result.time[type] += tempDelay;
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
        for (int i = 0; i < countOfProcesses; i++) {
            if (tnextOfProcesses.get(i) == super.getTcurr()) {
                stateOfProcesses.set(i, 0);
                tnextOfProcesses.set(i, Double.MAX_VALUE);
                setTnext();
                for (int j = 0; j < typeQueues.size(); j++) {
                    if (typeQueues.get(j) > 0) {
                        typeQueues.set(j, typeQueues.get(j) - 1);
                        stateOfProcesses.set(i, 4);
                        double tempDelay = getDelay();
                        Result.time[j] += tempDelay;
                        tnextOfProcesses.set(i, super.getTcurr() + tempDelay);
                        setTnext();
                        break;
                    }
                }
                break;
            }
        }

        if (nextElements.size() > 1) {
            getNextElements().get(0).inAct(3);
        } else if (super.getNextElement() != null) {
            super.getNextElement().inAct(3);
        }
    }
}
