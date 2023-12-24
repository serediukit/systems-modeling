package verification;

public class TestData {
    public double containerDelay;
    public int capacityType1;
    public double planeDelay1;
    public double planeDev1;
    public int capacityType2;
    public double planeDelay2;
    public double planeDev2;
    public double conveyorDelay;


    public TestData(
            double containerDelay,
            int capacityType1,
            double planeDelay1,
            double planeDev1,
            int capacityType2,
            double planeDelay2,
            double planeDev2,
            double conveyorDelay
    ) {
        this.containerDelay = containerDelay;
        this.capacityType1 = capacityType1;
        this.planeDelay1 = planeDelay1;
        this.planeDev1 = planeDev1;
        this.capacityType2 = capacityType2;
        this.planeDelay2 = planeDelay2;
        this.planeDev2 = planeDev2;
        this.conveyorDelay = conveyorDelay;
    }
}
