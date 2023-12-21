import java.util.ArrayList;
import java.util.List;

public class Model {
    private int event;
    private double timeNext;
    private double timeCurrent;
    private ArrayList<Element> elements;

    public Model(List<Element> elements) {
        this.elements = new ArrayList<>(elements);
        timeNext = 0;
        timeCurrent = 0;
        event = 0;
    }

    public void simulate(double timeSimulation) {
        while (timeCurrent < timeSimulation) {
            timeNext = Double.MAX_VALUE;

            for (Element e : elements) {
                if (e.getTimeNext() < timeNext) {
                    timeNext = e.getTimeNext();
                    event = e.getId();
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

            printInfo();
        }
        printResults();
    }

    private void printInfo() {
        for (Element e : elements) {
            e.printInfo();
        }
    }

    private void printResults() {
        System.out.println("\n---------------RESULTS---------------");
        for (Element e : elements) {
            e.printResult();
            if (e instanceof Process p) {
                System.out.println("mean length of queue: " + p.getMeanQueue() / timeCurrent);
                System.out.println("failure probability: " + p.getFailure() / p.getQuantity());
            }
            System.out.println();
        }
    }
}
