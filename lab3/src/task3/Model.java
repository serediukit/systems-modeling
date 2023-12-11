package task3;

import java.util.ArrayList;
import java.util.Arrays;

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
            tnext = Double.MAX_VALUE;
            for (Element e : list) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    event = e.getId();
                }
            }
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
//            printInfo();
        }
        printResult();
    }
    public void printInfo() {
        for (Element e: list) {
            e.printInfo();
        }
    }
    public void printResult() {
        System.out.println("\n-------------RESULTS-------------\n");
        System.out.println("Created:");
        System.out.println("patient type 1 = " + Result.patientQuantity[0]);
        System.out.println("patient type 2 = " + Result.patientQuantity[1]);
        System.out.println("patient type 3 = " + Result.patientQuantity[2]);
        System.out.println();
        System.out.println("Average time in hospital for each type of patient:");
        System.out.println("patient type 1 = " + Result.time[0] / Result.patientQuantity[0]);
        System.out.println("patient type 2 = " + Result.time[1] / Result.patientQuantity[1]);
        System.out.println("patient type 3 = " + Result.time[2] / Result.patientQuantity[2]);
        System.out.println();
        System.out.println("Average interval between coming to laboratory:");
        System.out.println("interval = " + Result.timeInLab / Result.patientInLab);

        System.out.println(Arrays.stream(Result.time).sum() / Arrays.stream(Result.patientQuantity).sum());

//        System.out.println("\n-------------RESULTS-------------");
//        for (Element e : list) {
//            e.printResult();
//            if (e instanceof TypeCreate c) {
//                ArrayList<Integer> q = c.getTypeQuantity();
//                System.out.println(
//                        "patient type 1 = " + q.get(0)
//                        + "\npatient type 2 = " + q.get(1)
//                        + "\npatient type 3 = " + q.get(2)
//                );
//            }
//            if (e instanceof Process p) {
//                System.out.println("mean length of queue = "
//                        + p.getMeanQueue() / tcurr
//                        + "\nfailure probability = "
//                        + p.getFailure() / (double) p.getQuantity()
//                );
//            }
//            System.out.println();
//        }
    }
}