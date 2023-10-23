package util;

import java.util.*;
import java.util.stream.Collectors;

public class ChiCritical {
    private static final Map<Integer, ArrayList<Double>> TABLE_OF_CHI_SQUARED;

    static {
        TABLE_OF_CHI_SQUARED = new HashMap<>();

        TABLE_OF_CHI_SQUARED.put(1, createArrayList(0.001, 0.001, 0.001, 0.004, 0.016, 2.706, 3.841, 5.024, 6.635, 7.879));
        TABLE_OF_CHI_SQUARED.put(2, createArrayList(0.01, 0.02, 0.051, 0.103, 0.211, 4.605, 5.991, 7.378, 9.21, 10.597));
        TABLE_OF_CHI_SQUARED.put(3, createArrayList(0.072, 0.115, 0.216, 0.352, 0.584, 6.251, 7.815, 9.348, 11.345, 12.838));
        TABLE_OF_CHI_SQUARED.put(4, createArrayList(0.207, 0.297, 0.484, 0.711, 1.064, 7.779, 9.488, 11.143, 13.277, 14.86));
        TABLE_OF_CHI_SQUARED.put(5, createArrayList(0.412, 0.554, 0.831, 1.145, 1.61, 9.236, 11.07, 12.833, 15.086, 16.75));
        TABLE_OF_CHI_SQUARED.put(6, createArrayList(0.676, 0.872, 1.237, 1.635, 2.204, 10.645, 12.592, 14.449, 16.812, 18.548));
        TABLE_OF_CHI_SQUARED.put(7, createArrayList(0.989, 1.239, 1.69, 2.167, 2.833, 12.017, 14.067, 16.013, 18.475, 20.278));
        TABLE_OF_CHI_SQUARED.put(8, createArrayList(1.344, 1.646, 2.18, 2.733, 3.49, 13.362, 15.507, 17.535, 20.09, 21.955));
        TABLE_OF_CHI_SQUARED.put(9, createArrayList(1.735, 2.088, 2.7, 3.325, 4.168, 14.684, 16.919, 19.023, 21.666, 23.589));
        TABLE_OF_CHI_SQUARED.put(10, createArrayList(2.156, 2.558, 3.247, 3.94, 4.865, 15.987, 18.307, 20.483, 23.209, 25.188));
        TABLE_OF_CHI_SQUARED.put(11, createArrayList(2.603, 3.053, 3.816, 4.575, 5.578, 17.275, 19.675, 21.92, 24.725, 26.757));
        TABLE_OF_CHI_SQUARED.put(12, createArrayList(3.074, 3.571, 4.404, 5.226, 6.304, 18.549, 21.026, 23.337, 26.217, 28.3));
        TABLE_OF_CHI_SQUARED.put(13, createArrayList(3.565, 4.107, 5.009, 5.892, 7.042, 19.812, 22.362, 24.736, 27.688, 29.819));
        TABLE_OF_CHI_SQUARED.put(14, createArrayList(4.075, 4.66, 5.629, 6.571, 7.79, 21.064, 23.685, 26.119, 29.141, 31.319));
        TABLE_OF_CHI_SQUARED.put(15, createArrayList(4.601, 5.229, 6.262, 7.261, 8.547, 22.307, 24.996, 27.488, 30.578, 32.801));
        TABLE_OF_CHI_SQUARED.put(16, createArrayList(5.142, 5.812, 6.908, 7.962, 9.312, 23.542, 26.296, 28.845, 32, 34.267));
        TABLE_OF_CHI_SQUARED.put(17, createArrayList(5.697, 6.408, 7.564, 8.672, 10.085, 24.769, 27.587, 30.191, 33.409, 35.718));
        TABLE_OF_CHI_SQUARED.put(18, createArrayList(6.265, 7.015, 8.231, 9.39, 10.865, 25.989, 28.869, 31.526, 34.805, 37.156));
        TABLE_OF_CHI_SQUARED.put(19, createArrayList(6.844, 7.633, 8.907, 10.117, 11.651, 27.204, 30.144, 32.852, 36.191, 38.582));
        TABLE_OF_CHI_SQUARED.put(20, createArrayList(7.434, 8.260, 9.591, 10.851, 12.443, 28.412, 31.410, 34.170, 37.566, 39.997));
        TABLE_OF_CHI_SQUARED.put(21, createArrayList(8.034, 8.897, 10.283, 11.591, 13.240, 29.615, 32.671, 35.479, 38.932, 41.401));
        TABLE_OF_CHI_SQUARED.put(22, createArrayList(8.643, 9.542, 10.982, 12.338, 14.041, 30.813, 33.924, 36.781, 40.289, 42.796));
        TABLE_OF_CHI_SQUARED.put(23, createArrayList(9.260, 10.196, 11.689, 13.091, 14.848, 32.007, 35.172, 38.076, 41.638, 44.181));
        TABLE_OF_CHI_SQUARED.put(24, createArrayList(9.886, 10.856, 12.401, 13.848, 15.659, 33.196, 36.415, 39.364, 42.980, 45.559));
        TABLE_OF_CHI_SQUARED.put(25, createArrayList(10.520, 11.524, 13.120, 14.611, 16.473, 34.382, 37.652, 40.646, 44.314, 46.928));
        TABLE_OF_CHI_SQUARED.put(26, createArrayList(11.160, 12.198, 13.844, 15.379, 17.292, 35.563, 38.885, 41.923, 45.642, 48.290));
        TABLE_OF_CHI_SQUARED.put(27, createArrayList(11.808, 12.879, 14.573, 16.151, 18.114, 36.741, 40.113, 43.195, 46.963, 49.645));
        TABLE_OF_CHI_SQUARED.put(28, createArrayList(12.461, 13.565, 15.308, 16.928, 18.939, 37.916, 41.337, 44.461, 48.278, 50.993));
        TABLE_OF_CHI_SQUARED.put(29, createArrayList(13.121, 14.256, 16.047, 17.708, 19.768, 39.087, 42.557, 45.722, 49.588, 52.336));
        TABLE_OF_CHI_SQUARED.put(30, createArrayList(13.787, 14.953, 16.791, 18.493, 20.599, 40.256, 43.773, 46.979, 50.892, 53.672));
        TABLE_OF_CHI_SQUARED.put(40, createArrayList(20.707, 22.164, 24.433, 26.509, 29.051, 51.805, 55.758, 59.342, 63.691, 66.766));
        TABLE_OF_CHI_SQUARED.put(50, createArrayList(27.991, 29.707, 32.357, 34.764, 37.689, 63.167, 67.505, 71.42, 76.154, 79.49));
        TABLE_OF_CHI_SQUARED.put(60, createArrayList(35.534, 37.485, 40.482, 43.188, 46.459, 74.397, 79.082, 83.298, 88.379, 91.952));
        TABLE_OF_CHI_SQUARED.put(70, createArrayList(43.275, 45.442, 48.758, 51.739, 55.329, 85.527, 90.531, 95.023, 100.425, 104.215));
        TABLE_OF_CHI_SQUARED.put(80, createArrayList(51.172, 53.54, 57.153, 60.391, 64.278, 96.578, 101.879, 106.629, 112.329, 116.321));
        TABLE_OF_CHI_SQUARED.put(90, createArrayList(59.196, 61.754, 65.647, 69.126, 73.291, 107.565, 113.145, 118.136, 124.116, 128.299));
        TABLE_OF_CHI_SQUARED.put(100, createArrayList(67.328, 70.065, 74.222, 77.929, 82.358, 118.498, 124.342, 129.561, 135.807, 140.169));
    }

    public static double getChiSquaredCritical(double alpha, int degreesOfFreedom) {
        return TABLE_OF_CHI_SQUARED.get(convertDegreesToIndex(degreesOfFreedom)).get(convertAlphaToIndex(alpha));
    }

    private static int convertDegreesToIndex(int degrees) {
        if (TABLE_OF_CHI_SQUARED.containsKey(degrees))
            return degrees;
        else {
            ArrayList<Integer> keys = new ArrayList<>(TABLE_OF_CHI_SQUARED.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); i++) {
                if (degrees >= keys.get(i) && degrees < keys.get(i + 1))
                    return keys.get(i);
            }
        }
        return 50;
    }

    private static int convertAlphaToIndex(double alpha) {
        if (alpha >= .995)
            return 0;
        else if (alpha >= .99)
            return 1;
        else if (alpha >= .975)
            return 2;
        else if (alpha >= .95)
            return 3;
        else if (alpha >= .9)
            return 4;
        else if (alpha >= .1)
            return 5;
        else if (alpha >= .05)
            return 6;
        else if (alpha >= .025)
            return 7;
        else if (alpha >= .01)
            return 8;
        else
            return 9;
    }

    private static ArrayList<Double> createArrayList(double... values) {
        ArrayList<Double> list = new ArrayList<>();
        for (double value : values) {
            list.add(value);
        }
        return list;
    }
}
