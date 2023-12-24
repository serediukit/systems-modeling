package modified;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Experiment {
    public static final int testCount = 20;
    public static final int timeSimulation = 15000;

    public static void main(String[] args) {
        for (int test = 0; test < testCount; test++) {
            Create creator = new Create("CREATOR", "number", .5, 0);
            creator.setTimeNext(.5);
            Process[] conveyors = new Process[] {
                    new Process("CONVEYOR FOR PLANE 1", "def", 1, 0, 80),
                    new Process("CONVEYOR FOR PLANE 2", "def", 1, 0, 80),
                    new Process("CONVEYOR FOR PLANE 3", "def", 1, 0, 80),
                    new Process("CONVEYOR FOR PLANE 4", "def", 1, 0, 140),
                    new Process("CONVEYOR FOR PLANE 5", "def", 1, 0, 140)
            };
            PlaneProcess[] planes = new modified.PlaneProcess[] {
                    new PlaneProcess("PLANE TYPE 80 NUM 1", "norm", 180, 60, 80),
                    new PlaneProcess("PLANE TYPE 80 NUM 2", "norm", 180, 60, 80),
                    new PlaneProcess("PLANE TYPE 80 NUM 3", "norm", 180, 60, 80),
                    new PlaneProcess("PLANE TYPE 140 NUM 1", "norm", 180, 60, 140),
                    new PlaneProcess("PLANE TYPE 140 NUM 2", "norm", 180, 60, 140)
            };
            creator.setNextElements(asList(conveyors));
            for (int i = 0; i < 5; i++) {
                conveyors[i].setNextElements(List.of(planes[i]));
            }
            List<Element> list = new ArrayList<>(List.of(creator));
            list.addAll(asList(conveyors));
            list.addAll(asList(planes));
            Model model = new Model(list);
            model.simulate(timeSimulation);
        }
    }
}
