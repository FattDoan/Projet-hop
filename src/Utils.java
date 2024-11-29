import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int randNum(int a, int b) { //[a, b] inclus
        return ThreadLocalRandom.current().nextInt(a, b + 1);
    }
}

