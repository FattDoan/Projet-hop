import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int randNum(int a, int b) { //[a, b] inclus
        return ThreadLocalRandom.current().nextInt(a, b + 1);
    }
    // <!> Level is 1-indexed while its associated array is 0-indexed
    public static int at(int[] arr, int i) {
        if (i < 0 || i > arr.length) {
            System.out.println("Invalid array index " + i + " access at " + arr);
            i = Math.max(i, 0);
        }
        i = Math.min(i - 1, arr.length - 1);
        return arr[i];
    }
    public static double at(double[] arr, int i) {
        if (i < 0 || i > arr.length) {
            System.out.println("Invalid array index " + i + " access at " + arr);
            i = Math.max(i, 0);
        }
        i = Math.min(i - 1, arr.length - 1);
        return arr[i];
    }
    public static void runCode(Runnable code) {
        if (code != null) {
            code.run();
        }
    }
}

