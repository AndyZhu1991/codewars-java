package andy.zhu.codewars.carmileage;

import java.util.stream.IntStream;

/**
 * Created by AndyZhu on 2017/5/16.
 * https://www.codewars.com/kata/52c4dd683bfd3b434c000292
 */
public class CarMileage {

    public static void main(String[] args) {
        System.out.println(isInteresting(12320, new int[0]));
    }

    public static int isInteresting(int number, int[] awesomePhrases) {
        //Go to town
        if (_isInteresting(number, awesomePhrases)) {
            return 2;
        } else if (_isInteresting(number + 1, awesomePhrases) || _isInteresting(number + 2, awesomePhrases)) {
            return 1;
        } else {
            return 0;
        }
    }

    private static boolean _isInteresting(int number, int[] awesomePhrases) {
        if (number < 100) return false;
        String  numStr = String.valueOf(number);
        return  numStr.chars().skip(1).filter(i -> i != '0').count() == 0 ||
                numStr.chars().distinct().count() == 1 ||
                "1234567890".contains(numStr) ||
                "9876543210".contains(numStr) ||
                numStr.equals(new StringBuilder(numStr).reverse().toString()) ||
                IntStream.of(awesomePhrases).anyMatch(i -> i == number);
    }
}
