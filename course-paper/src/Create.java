public class Create extends Element {
    private Process nextElement;
    public Create(String name, String distribution, double delayMean, double delayDev) {
        super(name, distribution, delayMean, delayDev);
    }

    @Override
    public void outAct() {
        super.outAct();
        setTimeNext(getTimeCurrent() + getDelay());
        if (getNextElements() != null) {
            getNextElement().inAct();
        }
    }

    @Override
    public Element getNextElement() {
        if (nextElement == null) {
            nextElement = getNextElements().get(0);
            System.out.println("Choosing " + nextElement.getName());
            return nextElement;
        }
        if (nextElement.getQueue() == nextElement.getMaxQueue() || nextElement.getState() == 1) {
            for (Process p : getNextElements()) {
                if (p.getQueue() < p.getMaxQueue() && p.getState() == 0) {
                    nextElement = p;
                    System.out.println("Choosing " + nextElement.getName());
                    return nextElement;
                }
            }
        }
        return nextElement;
    }
}
