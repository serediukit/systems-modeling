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

            //printInfo();
        }
        printResults();
        printResult();
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
            if (e instanceof Create c) {
                System.out.println("mean length of queue: " + c.getMeanQueue() / timeCurrent);
            }
            if (e instanceof Process p) {
                System.out.println("total amount of containers: " + p.getQuantity() * p.getMaxQueue());
                System.out.println("mean length of queue: " + p.getMeanQueue() / timeCurrent);
                System.out.println("failure probability: " + p.getFailure() / p.getQuantity());
            }
            System.out.println();
        }
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
            if (e instanceof Process p) {
                System.out.printf("| %-28s | %11.6f |\n", p.getName(), p.getMeanTimeWaiting() / p.getWaitsCount());
            }
        }
        System.out.println("+------------------------------+-------------+");
        System.out.println();
        System.out.println("+--------------------------------------------+");
        System.out.println("|            Average time loading            |");
        System.out.println("+------------------------------+-------------+");
        for (Element e : elements) {
            if (e instanceof Process p) {
                System.out.printf("| %-28s | %11.8f |\n", p.getName(), p.getMeanTimeLoading() / timeCurrent);
            }
        }
        System.out.println("+------------------------------+-------------+");
    }
}
