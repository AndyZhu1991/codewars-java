package andy.zhu.codewars.convertstringtocamelcase;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/517abf86da9663f1d2000003
 */
public class Solution {

    static String toCamelCase(String s) {
        String[] words = s.replace('-', '_').split("_");
        StringBuilder sb = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            sb.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
        }
        return sb.toString();
    }

}
