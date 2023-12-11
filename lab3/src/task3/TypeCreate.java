package task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeCreate extends Create {
    private ArrayList<Double> typeChances = new ArrayList<>();
    private ArrayList<Double> typeQuantity = new ArrayList<>(Collections.nCopies(3, .0));
    public int lastCreatedType = 0;

    public TypeCreate(String name, String distribution, double delay) {
        super(name, distribution, delay);
    }

    @Override
    public void outAct() {
        super.outAct();
        lastCreatedType = getType();
        typeQuantity.set(lastCreatedType, typeQuantity.get(lastCreatedType) + 1);
        super.setTnext(super.getTcurr() + super.getDelay());
        if (super.getNextElement() != null) {
            TypeProcess p = (TypeProcess) super.getNextElement();
            p.inAct(lastCreatedType);
        }
    }

    public void setTypeChances(List<Double> typeChances) {
        this.typeChances.addAll(typeChances);
    }

    private int getType() {
        double r = Math.random();
        double sum = 0;
        for (int i = 0; i < typeChances.size(); i++) {
            sum += typeChances.get(i);
            if (r <= sum) {
                return i;
            }
        }
        return 0;
    }
}
