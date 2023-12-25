package verification;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private double timeNext;
    private double timeCurrent;
    private final ArrayList<Element> elements;
    private final TestData testData;

    public Model(List<Element> elements, TestData testData) {
        this.elements = new ArrayList<>(elements);
        this.testData = testData;
        timeNext = 0;
        timeCurrent = 0;
    }

    public void simulate(double timeSimulation) {
        while (timeCurrent < timeSimulation) {
            timeNext = Double.MAX_VALUE;

            for (Element e : elements) {
                if (e.getTimeNext() < timeNext) {
                    timeNext = e.getTimeNext();
                }
            }

            for (Element e : elements) {
                e.doStatistic(timeNext - timeCurrent);
            }

            timeCurrent = timeNext;

            for (Element e : elements) {
                e.setTimeCurrent(timeCurrent);
            }

            for (Element e : elements) {
                if (e.getTimeNext() == timeCurrent) {
                    e.outAct();
                }
            }
        }
        printResults();
    }

    private void printInfo() {
        for (Element e : elements) {
            e.printInfo();
        }
    }

    private void printResults() {
        int contCount = 0;
        double contMeanQ = 0;
        double[] planeWait = new double[] {0, 0, 0, 0, 0};
        double[] planeLoad = new double[] {0, 0, 0, 0, 0};
        int pIndex = 0;
        for (Element e : elements) {
            if (e instanceof Create c) {
                contCount = c.getQuantity();
                contMeanQ = c.getMeanQueue() / timeCurrent;
            }
            if (e instanceof PlaneProcess p) {
                planeWait[pIndex] = p.getMeanTimeWaiting() / p.getWaitsCount();
                planeLoad[pIndex] = p.getMeanTimeLoading() / timeCurrent;
                pIndex++;
            }
        }
        System.out.printf("| %9.2f " +
                        "| %8d " +
                        "| %10.2f " +
                        "| %8d " +
                        "| %10.2f " +
                        "| %9.2f " +
                        "|| %9d " +
                        "| %9.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f " +
                        "| %13.3f |\n",
                testData.containerDelay,
                testData.capacityType1,
                testData.planeDelay1,
                testData.capacityType2,
                testData.planeDelay2,
                testData.conveyorDelay,
                contCount,
                contMeanQ,
                planeWait[0],
                planeWait[1],
                planeWait[2],
                planeWait[3],
                planeWait[4],
                planeLoad[0],
                planeLoad[1],
                planeLoad[2],
                planeLoad[3],
                planeLoad[4]
        );
        Result.meanLoadintFirstPlane += planeLoad[0];
    }

    private void printResult() {
        System.out.println();
        System.out.println("+--------------------------------------------+");
        System.out.println("|                   RESULT                   |");
        System.out.println("+--------------------------------------------+");
        System.out.println();
        System.out.println("+--------------------------------------------+");
        System.out.println("|            Average time waiting            |");
        System.out.println("+------------------------------+-------------+");
        for (Element e : elements) {
            if (e instanceof PlaneProcess p) {
                System.out.printf("| %-28s | %11.6f |\n", p.getId() + " " + p.getName(), p.getMeanTimeWaiting() / p.getWaitsCount());
            }
        }
        System.out.println("+------------------------------+-------------+");
        System.out.println();
        System.out.println("+--------------------------------------------+");
        System.out.println("|              Average loading               |");
        System.out.println("+------------------------------+-------------+");
        for (Element e : elements) {
            if (e instanceof PlaneProcess p) {
                System.out.printf("| %-28s | %10.4f%% |\n", p.getId() + " " + p.getName(), p.getMeanTimeLoading() * 100 / timeCurrent);
            }
        }
        System.out.println("+------------------------------+-------------+");
    }
}
