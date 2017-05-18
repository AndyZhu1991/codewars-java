package andy.zhu.codewars.checkinggroups;

import java.util.Stack;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/54b80308488cb6cd31000161
 */
public class Groups{

    public static boolean groupCheck(String s){
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c: chars) {
            if (isLeft(c)) {
                stack.push(c);
            } else {
                if (stack.empty()) {
                    return false;
                }
                char l = stack.pop();
                if (!isLeft(l) || !isPair(l, c)) {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    private static boolean isLeft(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    private static boolean isPair(char l, char r) {
        switch (l) {
            case '(':
                return r == ')';
            case '{':
                return r == '}';
            case '[':
                return r == ']';
        }
        return false;
    }

}
