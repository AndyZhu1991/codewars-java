package andy.zhu.codewars.josephussurvivor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/555624b601231dc7a400017a
 */
public class JosephusSurvivor {
    public static int josephusSurvivor(final int n, final int k) {
        List<Integer> list = IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());
        int index = 0;
        while (list.size() > 1) {
            index += k - 1;
            index %= list.size();
            list.remove(index);
        }
        return list.get(0);
    }
}
