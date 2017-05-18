package andy.zhu.codewars.intpart;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by AndyZhu on 2017/5/16.
 * https://www.codewars.com/kata/55cf3b567fc0e02b0b00000b
 */
public class IntPart {

    public static void main(String[] args) {
        part(8);
    }

    private static Map<Long, long[][]> partCache = new HashMap<>();

    public static String part(long n) {
        // your code
        long[] prod = Stream.of(getPart(n))
                .map(IntPart::multiple)
                .mapToLong(l -> l)
                .sorted()
                .distinct()
                .toArray();
        long range = prod[prod.length - 1] - prod[0];
        double average = 1.0 * LongStream.of(prod).sum() / prod.length;
        double median = prod.length % 2 == 0 ? (prod[prod.length / 2 - 1] + prod[prod.length / 2]) / 2.0 : prod[prod.length / 2];
        return String.format("Range: %d Average: %.2f Median: %.2f", range, average, median);
    }

    private static long multiple(long[] ls) {
        long result = 1;
        for (long l: ls) {
            result *= l;
        }
        return result;
    }

    private static long[][] getPart(long n) {
        if (partCache.get(n) != null) {
            return partCache.get(n);
        }
        long[][] result =
                LongStream.rangeClosed(1, n)
                .boxed()
                .flatMap(m -> getMStartPart(n, m))
                .toArray(long[][]::new);
        partCache.put(n, result);
        return result;
    }

    private static Stream<long[]> getMStartPart(long n, long m) {
        if (n == m) {
            return Stream.of(new long[] {n});
        } else {
            return Stream.of(getPart(n - m))
                    .filter(ls -> ls[0] <= m)
                    .map(ls -> concat(m, ls));

        }
    }

    private static long[] concat(long l, long[] ls) {
        long[] result = new long[ls.length + 1];
        result[0] = l;
        System.arraycopy(ls, 0, result, 1, ls.length);
        return result;
    }
}
