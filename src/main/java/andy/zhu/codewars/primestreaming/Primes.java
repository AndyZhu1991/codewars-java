package andy.zhu.codewars.primestreaming;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Created by AndyZhu on 2017/5/17.
 * https://www.codewars.com/kata/5519a584a73e70fa570005f5
 */
public class Primes {
    public static IntStream stream() {
        final PrimitiveIterator.OfInt iterator = new PrimitiveIterator.OfInt() {
            List<Integer> primes = new ArrayList<>();
            int curIndex = 0;
            {
                primes.add(2);
                primes.add(3);
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public int nextInt() {
                genPrimeIfNeed();
                return primes.get(curIndex++);
            }

            private void genPrimeIfNeed() {
                while (curIndex >= primes.size()) {
                    for (int i = primes.get(primes.size() - 1) + 2; ; i += 2) {
                        if (isPrime(i)) {
                            primes.add(i);
                            break;
                        }
                    }
                }
            }

            private boolean isPrime(int n) {
                int sqrtN = (int) Math.round(Math.sqrt(n));
                for (int i = 1; primes.get(i) <= sqrtN; i++) {
                    if (n % primes.get(i) == 0) {
                        return false;
                    }
                }
                return true;
            }
        };
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL), false);
    }
}
