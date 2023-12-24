package experiment;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class IntervalResults {
    public static Map<Integer, ArrayList<Double>> meanLoading = new TreeMap<>();
    public static Map<Integer, ArrayList<Double>> meanWaiting = new TreeMap<>();

    public static void addLoadingResult(int timeMod, ArrayList<Double> meanL) {
        meanLoading.put(timeMod, meanL);
    }

    public static void addWaitingResult(int timeMod, ArrayList<Double> meanW) {
        meanWaiting.put(timeMod, meanW);
    }

    public static Map<Integer, ArrayList<Double>> getMeanLoading() {
        return meanLoading;
    }

    public static Map<Integer, ArrayList<Double>> getMeanWaiting() {
        return meanWaiting;
    }
}
