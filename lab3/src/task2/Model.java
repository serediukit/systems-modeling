package task2;

import java.util.ArrayList;

public class Model {
    private ArrayList<Element> list;
    private double tnext;
    private double tcurr;
    private int event;
    public Model(ArrayList<Element> elements) {
        list = new ArrayList<>(elements);
        tnext = 0.0;
        event = 0;
        tcurr = tnext;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            checkQueue();
            tnext = Double.MAX_VALUE;
            for (Element e : list) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    event = e.getId();
                }
            }
//            System.out.println("\nIt's time for event in "
//                    + list.get(event).getName()
//                    + ", time = " + tnext);
            for (Element e : list) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;
            for (Element e : list) {
                e.setTcurr(tcurr);
            }
            for (Element e: list) {
                if (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }
            printInfo();
        }
        printResult();
    }
    private void checkQueue() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof ConditionProcess) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(j) instanceof ConditionProcess) {
                        Process p1 = (Process) list.get(i);
                        Process p2 = (Process) list.get(j);
                        if (p1.getQueue() - p2.getQueue() >= 2) {
                            p1.setQueue(p1.getQueue() - 1);
                            p2.setQueue(p2.getQueue() + 1);
                        } else if (p2.getQueue() - p1.getQueue() >= 2) {
                            p1.setQueue(p1.getQueue() + 1);
                            p2.setQueue(p2.getQueue() - 1);
                        }
                        return;
                    }
                }
                break;
            }
        }
    }
    public void printInfo() {
        for (Element e: list) {
            e.printInfo();
        }
    }
    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            e.printResult();
            if (e instanceof Process p) {
                System.out.println("mean length of queue = "
                        + p.getMeanQueue() / tcurr
                        + "\nfailure probability = "
                        + p.getFailure() / (double) p.getQuantity()
                );
            }
            System.out.println();
        }
    }
}