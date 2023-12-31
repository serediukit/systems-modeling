package task3and4;

public class Element {
    private String name;
    private double tnext;
    private double delayMean;
    private double delayDev;
    private String distribution;
    private int quantity;
    private double tcurr;
    private int state;
    private Element nextElement;
    private static int nextId = 0;
    private int id;

    public Element() {
        tnext = Double.MAX_VALUE;
        delayMean = 1.0;
        distribution = "exp";
        tcurr = tnext;
        state = 0;
        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }
    public Element(double delay) {
        name = "anonymus";
        tnext = 0.0;
        delayMean = delay;
        distribution = "";
        tcurr = tnext;
        state = 0;
        nextElement = null;
        id = nextId;
        nextId++;
    }
    public Element(String nameOfElement, double delay) {
        name = nameOfElement;
        tnext = 0.0;
        delayMean = delay;
        distribution = "exp";
        tcurr = tnext;
        state = 0;
        nextElement = null;
        id = nextId;
        nextId++;
    }

    public double getDelay() {
        return switch (distribution.toLowerCase()) {
            case "exp" -> FunRand.Exp(delayMean);
            case "norm" -> FunRand.Norm(delayMean, delayDev);
            case "unif" -> FunRand.Unif(delayMean, delayDev);
            default -> delayMean;
        };
    }
    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getQuantity() {
        return quantity;
    }
    public double getTcurr() {
        return tcurr;
    }
    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Element getNextElement() {
        return nextElement;
    }
    public void setNextElement(Element nextElement) {
        this.nextElement = nextElement;
    }
    public void inAct() {

    }
    public void outAct() {
        quantity++;
        if (nextElement != null)
            nextElement.inAct();
    }

    public double getTnext() {
        return tnext;
    }
    public void setTnext(double tnext) {
        this.tnext = tnext;
    }
    public int getId() {
        return id;
    }
    public void printResult() {
        System.out.println(getName() + " quantity = " + quantity);
    }
    public void printInfo() {
        System.out.println(getName() + " state = " + state
                + " quantity = " + quantity
                + " tnext = " + tnext
        );
    }
    public String getName() {
        return name;
    }
    public void doStatistics(double delta) {

    }

    public static void clear() {
        nextId = 0;
    }
}