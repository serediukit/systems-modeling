package modified;

import java.util.Random;

public class Rand {
    public static double Exp(double timeMean) {
        double a = 0;
        while (a == 0)
            a = Math.random();
        return -timeMean * Math.log(a);
    }

    public static double Unif(double timeMin, double timeMax) {
        double a = 0;
        while (a == 0)
            a = Math.random();
        return timeMin + a * (timeMax - timeMin);
    }

    public static double Norm(double timeMean, double timeDev) {
        Random rnd = new Random();
        return timeMean + timeDev * rnd.nextGaussian();
    }
}
