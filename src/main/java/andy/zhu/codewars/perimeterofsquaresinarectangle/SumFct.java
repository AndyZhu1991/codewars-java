package andy.zhu.codewars.perimeterofsquaresinarectangle;

import java.math.BigInteger;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/559a28007caad2ac4e000083
 */
public class SumFct {
    public static BigInteger perimeter(BigInteger n) {
        // your code
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(4);
        BigInteger result = BigInteger.ZERO.add(b);
        long ln = n.longValue();
        for (long i = 0; i < ln; i++) {
            BigInteger c = a.add(b);
            result.add(c);
            a = b;
            b = c;
        }
        return result;
    }
}
