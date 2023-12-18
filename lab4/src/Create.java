public class Create extends Element {
    public Create(String name, String distribution, double delay) {
        super(name, distribution, delay);
        super.setTnext(0.0);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        if (super.getNextElement() != null)
            super.getNextElement().inAct();
    }
}