import java.util.ArrayList;

import static java.util.Arrays.asList;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(asList(6453,6354,123,5,213,5));
        list.sort(null);
        System.out.println(list);
    }
}
