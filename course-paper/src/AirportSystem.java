import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AirportSystem {
    public static void main(String[] args) {
        Create creator = new Create("CREATOR", "number", 0.5, 0);
        Process[] planes = new Process[] {
                new Process("PLANE TYPE 80 NUM 1", "unif", 120, 240),
                new Process("PLANE TYPE 80 NUM 2", "unif", 120, 240),
                new Process("PLANE TYPE 80 NUM 3", "unif", 120, 240),
                new Process("PLANE TYPE 140 NUM 1", "unif", 120, 240),
                new Process("PLANE TYPE 140 NUM 2", "unif", 120, 240)
        };
        planes[0].setMaxQueue(80);
        planes[1].setMaxQueue(80);
        planes[2].setMaxQueue(80);
        planes[3].setMaxQueue(140);
        planes[4].setMaxQueue(140);
        creator.setNextElements(asList(planes));
        List<Element> list = new ArrayList<>(List.of(creator));
        list.addAll(asList(planes));
        Model model = new Model(list);
        model.simulate(1000);
        System.out.println(list.stream().map(Element::getName).toList());
    }
}
