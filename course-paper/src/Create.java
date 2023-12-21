public class Create extends Element {
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
        for (Process p : getNextElements()) {
            if (p.getQueue() < p.getMaxQueue() && p.getState() == 0) {
                return p;
            }
        }
        return super.getNextElement();
    }
}
