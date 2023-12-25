package verification;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ExperimentSystem {
    public static final int testCount = 20;

    public static void main(String[] args) {
        TestData[] testData = new TestData[] {
                new TestData(0.5, 80, 180, 60, 140, 180, 60, 0),
                new TestData(0.5, 120, 180, 60, 140, 180, 60, 0),
                new TestData(1, 80, 180, 60, 140, 180, 60, 0),
                new TestData(1, 120, 180, 60, 140, 180, 60, 0)
        };
        System.out.println("+-----------+----------+------------+----------+------------+-----------++-----------+-----------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
        System.out.println("| contDelay | capType1 | planeTime1 | capType2 | planeTime2 | convDelay || contCount | contMeanQ | planeWaitT1N1 | planeWaitT1N2 | planeWaitT1N3 | planeWaitT2N1 | planeWaitT2N2 | planeLoadT1N1 | planeLoadT1N2 | planeLoadT1N3 | planeLoadT2N1 | planeLoadT2N2 |");
        System.out.println("+-----------+----------+------------+----------+------------+-----------++-----------+-----------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
        for (TestData data : testData) {
            test(
                    data,
                    data.containerDelay,
                    data.capacityType1,
                    data.planeDelay1,
                    data.planeDev1,
                    data.capacityType2,
                    data.planeDelay2,
                    data.planeDev2,
                    data.conveyorDelay
            );
            System.out.println("+-----------+----------+------------+----------+------------+-----------++-----------+-----------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            System.out.println("| MEAN LOAD FOR PLANE 1 TYPE 1 NUMBER - " + Result.meanLoadintFirstPlane / testCount);
            System.out.println("+-----------+----------+------------+----------+------------+-----------++-----------+-----------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            Result.meanLoadintFirstPlane = 0;
        }
    }

    public static void test(
            TestData testData,
            double containerDelay,
            int capacityType1,
            double planeDelay1,
            double planeDev1,
            int capacityType2,
            double planeDelay2,
            double planeDev2,
            double conveyorDelay
    ) {
        for (int test = 0; test < testCount; test++) {
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
            Model model = new Model(list, testData);
            model.simulate(15000);
        }
    }
}
