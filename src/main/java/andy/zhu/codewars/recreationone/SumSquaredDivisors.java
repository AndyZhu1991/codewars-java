package andy.zhu.codewars.recreationone;

import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/55aa075506463dac6600010d
 */
public class SumSquaredDivisors {
    public static String listSquared(long m, long n) {
        // your code
        String[] tempResult = LongStream
                .rangeClosed(m, n)
                .boxed()
                .map(l -> new long[] {l, sumSquaredDivisors(l)})
                .filter(ls -> isSquared(ls[1]))
                .map(Arrays::toString)
                .toArray(String[]::new);
        return Arrays.toString(tempResult);
    }

    private static boolean isSquared(long n) {
        long sqrtN = (long) Math.sqrt(n);
        return sqrtN * sqrtN == n;
    }

    private static long sumSquaredDivisors(long n) {
        return LongStream
                .rangeClosed(1, (long) Math.sqrt(n))
                .filter(l -> n % l == 0)
                .map(l -> (l * l) + ((n / l) == l ? 0 : (n / l) * (n / l)))
                .sum();
    }
}
