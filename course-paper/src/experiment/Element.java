package experiment;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private static int nextId = 0;

    private final String name;
    private final String distribution;
    private final int id;
    private final double delayMean;
    private final double delayDev;

    private int state;
    private double timeNext;
    private double timeCurrent;
    private List<Process> nextElements;

    protected int quantity;

    public Element(String name, String distribution, double delayMean, double delayDev) {
        this.name = name;
        this.distribution = distribution;
        this.delayMean = delayMean;
        this.delayDev = delayDev;

        id = nextId;
        nextId++;

        timeNext = 0;
        timeCurrent = timeNext;
        state = 0;
    }

    protected double getDelay() {
        return switch (distribution.toLowerCase()) {
            case "exp" -> Rand.Exp(delayMean);
            case "unif" -> Rand.Unif(delayMean, delayDev);
            case "norm" -> Rand.Norm(delayMean, delayDev);
            default -> delayMean;
        };
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getTimeNext() {
        return timeNext;
    }

    public void setTimeNext(double timeNext) {
        this.timeNext = timeNext;
    }

    public double getTimeCurrent() {
        return timeCurrent;
    }

    public void setTimeCurrent(double timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public List<Process> getNextElements() {
        return nextElements;
    }

    public void setNextElements(List<Process> nextElements) {
        this.nextElements = new ArrayList<>(nextElements);
    }

    public Element getNextElement() {
        return nextElements.get(0);
    }

    public void inAct() {

    }

    public void outAct() {
        quantity++;
    }

    public void doStatistic(double delta) {

    }

    public void printResult() {
        System.out.println(name + " quantity: " + quantity);
    }

    public void printInfo() {
        System.out.println("\n" + name);
        System.out.println("state: " + state);
        System.out.println("quantity: " + quantity);
        System.out.println("timeNext: " + timeNext);
    }
}
