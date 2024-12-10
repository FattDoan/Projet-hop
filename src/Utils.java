import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int randNum(int a, int b) { //[a, b] inclus
        return ThreadLocalRandom.current().nextInt(a, b + 1);
    }
    public static int at(int[] arr, int i) {
        if (i < 0 || i >= arr.length) {
            System.out.println("Invalid array index " + i + " access at " + arr);
            i = Math.max(i, 0);
        }
        i = Math.min(i, arr.length - 1);
        return arr[i];
    }
    public static double at(double[] arr, int i) {
        if (i < 0 || i >= arr.length) {
            System.out.println("Invalid array index " + i + " access at " + arr);
            i = Math.max(i, 0);
        }
        i = Math.min(i, arr.length - 1);
        return arr[i];
    }

}

