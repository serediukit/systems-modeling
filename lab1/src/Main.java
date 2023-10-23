import util.*;

public class Main {
    public static void main(String[] args) {
        //ExpTest test1 = new ExpTest("exp_results");
        ChiCritical test = new ChiCritical();
        System.out.println(ChiCritical.getChiSquaredCritical(0.05, 34));
    }
}