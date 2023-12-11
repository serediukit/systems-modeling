package task3;

public class ChangingProcess extends Process {
    public ChangingProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
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
                        stateOfProcesses.set(i, j + 1);
                        tnextOfProcesses.set(i, super.getTcurr() + getDelay());
                        setTnext();
                        break;
                    }
                }
                break;
            }
        }

        if (nextElements.size() > 1)
            super.setNextElement(nextElements.get(0));

        if (super.getNextElement() != null)
            super.getNextElement().inAct(1);
    }
}
