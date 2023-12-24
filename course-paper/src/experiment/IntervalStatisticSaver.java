package experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntervalStatisticSaver extends Element {
    ArrayList<PlaneProcess> planes;

    public IntervalStatisticSaver(String name, String distribution, double delayMean, double delayDev) {
        super(name, distribution, delayMean, delayDev);
        setTimeNext(delayMean);
    }

    public void setPlanes(List<PlaneProcess> planes) {
        this.planes = new ArrayList<>(planes);
    }

    @Override
    public void outAct() {
        ArrayList<Double> meanL = new ArrayList<>();
        ArrayList<Double> meanW = new ArrayList<>();
        for (PlaneProcess p : planes) {
            meanL.add(p.getMeanTimeLoading() / getTimeCurrent());
            meanW.add(p.getMeanTimeWaiting() / p.getWaitsCount());
        }
        IntervalResults.addLoadingResult((int) getTimeCurrent(), meanL);
        IntervalResults.addWaitingResult((int) getTimeCurrent(), meanW);
        setTimeNext(getTimeCurrent() + getDelay());
    }

    public void printStatistic() {
        System.out.println("RESULT FOR WAITING");
        Map<Integer, ArrayList<Double>> res = IntervalResults.getMeanWaiting();
        for (Integer key : res.keySet()) {
            System.out.print(key);
            for (Double value : res.get(key)) {
                System.out.print(", " + value);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println("RESULT FOR LOADING");
        res = IntervalResults.getMeanLoading();
        for (Integer key : res.keySet()) {
            System.out.print(key);
            for (Double value : res.get(key)) {
                System.out.print(", " + value);
            }
            System.out.println();
        }
    }
}
