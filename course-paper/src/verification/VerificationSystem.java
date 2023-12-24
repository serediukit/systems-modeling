package verification;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class VerificationSystem {
    public static void main(String[] args) {
        TestData[] testData = new TestData[] {
                new TestData(0.5, 80, 180, 60, 140, 180, 60, 0),
                new TestData(1.0, 80, 180, 60, 140, 180, 60, 0),
                new TestData(0.5, 160, 180, 60, 140, 180, 60, 0),
                new TestData(0.5, 80, 360, 60, 140, 180, 60, 0),
                new TestData(0.5, 80, 180, 60, 280, 180, 60, 0),
                new TestData(0.5, 80, 180, 60, 140, 360, 60, 0),
                new TestData(0.5, 80, 180, 60, 140, 180, 60, 1)
        };
        for (TestData data : testData) {
            test(
                    data.containerDelay,
                    data.capacityType1,
                    data.planeDelay1,
                    data.planeDev1,
                    data.capacityType2,
                    data.planeDelay2,
                    data.planeDev2,
                    data.conveyorDelay
            );
        }
    }

    public static void test(
            double containerDelay,
            int capacityType1,
            double planeDelay1,
            double planeDev1,
            int capacityType2,
            double planeDelay2,
            double planeDev2,
            double conveyorDelay
    ) {
        Create creator = new Create("CREATOR", "number", containerDelay, 0);
        creator.setTimeNext(containerDelay);
        Process[] conveyors = new Process[] {
                new Process("CONVEYOR FOR PLANE TYPE 1 NUM 1", "def", conveyorDelay, 0, capacityType1),
                new Process("CONVEYOR FOR PLANE TYPE 1 NUM 2", "def", conveyorDelay, 0, capacityType1),
                new Process("CONVEYOR FOR PLANE TYPE 1 NUM 3", "def", conveyorDelay, 0, capacityType1),
                new Process("CONVEYOR FOR PLANE TYPE 2 NUM 1", "def", conveyorDelay, 0, capacityType2),
                new Process("CONVEYOR FOR PLANE TYPE 2 NUM 2", "def", conveyorDelay, 0, capacityType2)
        };
        PlaneProcess[] planes = new PlaneProcess[] {
                new PlaneProcess("PLANE TYPE 1 NUM 1", "norm", planeDelay1, planeDev1, capacityType1),
                new PlaneProcess("PLANE TYPE 1 NUM 2", "norm", planeDelay1, planeDev1, capacityType1),
                new PlaneProcess("PLANE TYPE 1 NUM 3", "norm", planeDelay1, planeDev1, capacityType1),
                new PlaneProcess("PLANE TYPE 2 NUM 1", "norm", planeDelay2, planeDev2, capacityType2),
                new PlaneProcess("PLANE TYPE 2 NUM 2", "norm", planeDelay2, planeDev2, capacityType2)
        };
        creator.setNextElements(asList(conveyors));
        for (int i = 0; i < 5; i++) {
            conveyors[i].setNextElements(List.of(planes[i]));
        }
        List<Element> list = new ArrayList<>(List.of(creator));
        list.addAll(asList(conveyors));
        list.addAll(asList(planes));
        Model model = new Model(list);
        model.simulate(10000);
    }
}
