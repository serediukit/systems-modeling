package task6;

public class Create extends Element {
    public Create(String name, double delay) {
        super(name, delay);
        super.setTnext(0.0); // імітація розпочнеться з події Create
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        if (super.getNextElement() != null)
            super.getNextElement().inAct();
    }
}