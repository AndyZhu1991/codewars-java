package andy.zhu.codewars.rectanglerotation;

/**
 * Created by AndyZhu on 2017/5/17.
 * https://www.codewars.com/kata/5886e082a836a691340000c3
 */
public class Solution {

    static int rectangleRotation(final int a, final int b) {
        int ac = (int) (a / 2.0 / Math.sqrt(0.5));
        int bc = (int) (b / 2.0 / Math.sqrt(0.5));
        return (ac * 2 + 1) * (bc * 2 + 1) / 2 + ((ac + bc) % 2 == 0 ? 1 : 0);
    }
}
