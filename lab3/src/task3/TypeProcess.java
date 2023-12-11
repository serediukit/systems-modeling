package task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeProcess extends Process {
    private final ArrayList<Double> delays = new ArrayList<>();
    private final ArrayList<Integer> typeQueue;
    public TypeProcess(String name, String distribution, double delay, int countOfProcesses, int maxqueue) {
        super(name, distribution, delay, countOfProcesses, maxqueue);
        typeQueue = new ArrayList<>(Collections.nCopies(4, 0));
    }

    @Override
    public void inAct(int type) {
        if (getName().equals("Analyze in laboratory")) {
            if (Result.lastTimeComingInLab != 0) {
                Result.timeInLab += getTcurr() - Result.lastTimeComingInLab;
            }
            Result.lastTimeComingInLab = getTcurr();
            Result.patientInLab++;
        }
        boolean isFind = false;
        for (int i = 0; i < countOfProcesses; i++) {
            if (stateOfProcesses.get(i) == 0) {
                stateOfProcesses.set(i, type + 1);
                double tempDelay = getDelayForType(type % 3);
                Result.time[type > 2 ? 1 : type] += tempDelay;
                tnextOfProcesses.set(i, super.getTcurr() + tempDelay);
                setTnext();
                isFind = true;
                break;
            }
        }
        if (!isFind)  {
            if (typeQueue.get(type) < maxqueue) {
                typeQueue.set(type, typeQueue.get(type) + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.quantity++;
        int nextType = 0;
        for (int i = 0; i < countOfProcesses; i++) {
            if (tnextOfProcesses.get(i) == super.getTcurr()) {
                nextType = stateOfProcesses.get(i) - 1;
                stateOfProcesses.set(i, 0);
                tnextOfProcesses.set(i, Double.MAX_VALUE);
                setTnext();
                for (int j = 0; j < typeQueue.size(); j++) {
                    if (typeQueue.get(j) > 0) {
                        typeQueue.set(j, typeQueue.get(j) - 1);
                        stateOfProcesses.set(i, j + 1);
                        double tempDelay = getDelayForType(j % 3);
                        Result.time[j > 2 ? 1 : j] += tempDelay;
                        tnextOfProcesses.set(i, super.getTcurr() + tempDelay);
                        setTnext();
                        break;
                    }
                }
                break;
            }
        }

        if (nextElements.size() > 1) {
            getNextElements().get(nextType % 3).inAct(nextType);
        } else if (super.getNextElement() != null) {
            super.getNextElement().inAct(nextType);
        }
    }

    public void setDelayForTypes(List<Double> delays) {
        this.delays.addAll(delays);
    }

    public void setNextElementsForTypes(List<Element> nextElements) {
        super.setNextElements(nextElements);
    }

    private double getDelayForType(int type) {
        return switch (distribution.toLowerCase()) {
            case "exp" -> FunRand.Exp(delays.get(type));
            case "norm" -> FunRand.Norm(delays.get(type), delayDev);
            case "unif" -> FunRand.Unif(delays.get(type), delayDev);
            default -> delays.get(type);
        };
    }

    @Override
    public void doStatistics(double delta) {
        for (int type = 0; type < typeQueue.size(); type++) {
            Result.time[type > 2 ? 1 : type] += typeQueue.get(type) * delta;
        }
    }
}
