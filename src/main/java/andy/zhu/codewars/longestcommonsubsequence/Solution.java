package andy.zhu.codewars.longestcommonsubsequence;

/**
 * Created by AndyZhu on 2017/5/17.
 * https://www.codewars.com/kata/52756e5ad454534f220001ef
 */
public class Solution {

    public static void main(String[] args) {
        System.out.println(lcs("abcdef", "acf"));
    }

    private static String x;
    private static String y;
    private static String[][] cache;

    public static String lcs(String x, String y) {
        // your code here
        init(x, y);
        return lcsRecur(0, 0);
    }

    private static void init(String x, String y) {
        Solution.x = x;
        Solution.y = y;
        cache = new String[y.length() + 1][];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new String[x.length() + 1];
        }
    }

    private static String lcsRecur(int xStart, int yStart) {
        if (cache[yStart][xStart] != null) {
            return cache[yStart][xStart];
        }

        if (xStart == x.length() || yStart == y.length()) {
            cache[yStart][xStart] = "";
        } else {
            // Include first char of x
            int firstInY = y.indexOf(x.charAt(xStart), yStart);
            String s1 = firstInY < 0 ? "" : x.charAt(xStart) + lcsRecur(xStart + 1, firstInY + 1);
            // Not include first char of x
            String s2 = lcsRecur(xStart + 1, yStart);
            cache[yStart][xStart] = s1.length() > s2.length() ? s1 : s2;
        }

        return cache[yStart][xStart];
    }
}
