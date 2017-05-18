package andy.zhu.codewars.secretdetective;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by AndyZhu on 2017/5/17.
 * https://www.codewars.com/kata/53f40dff5f9d31b813000774
 */
public class SecretDetective {

    public static void main(String[] args) {
        char[][] triplets = {
                {'t','u','p'},
                {'w','h','i'},
                {'t','s','u'},
                {'a','t','s'},
                {'h','a','p'},
                {'t','i','s'},
                {'w','h','s'}
        };
        System.out.println(new SecretDetective().recoverSecret(triplets));
    }

    private Map<Character, Set<Character>> mPathMap = new HashMap<>();
    private Set<Character> mHasNext = new HashSet<>();
    private Set<Character> mHasPrev = new HashSet<>();

    public String recoverSecret(char[][] triplets) {
        // Build graphic
        for (char[] triplet: triplets) {
            for (int i = 0; i < triplet.length - 1; i++) {
                Set<Character> pathSet = mPathMap.computeIfAbsent(triplet[i], k -> new HashSet<>());
                pathSet.add(triplet[i + 1]);
                mHasNext.add(triplet[i]);
                mHasPrev.add(triplet[i + 1]);
            }
            mPathMap.computeIfAbsent(triplet[triplet.length - 1], k -> new HashSet<>());
        }

        StringBuilder sb = new StringBuilder();
        for (char c = firstChar(); c != lastChar(); c = next(c)) {
            sb.append(c);
        }
        sb.append(lastChar());
        return sb.toString();
    }

    private char firstChar() {
        return mPathMap.keySet()
                .stream()
                .filter(c -> !mHasPrev.contains(c))
                .findFirst()
                .get();
    }

    private char lastChar() {
        return mPathMap.keySet()
                .stream()
                .filter(c -> !mHasNext.contains(c))
                .findFirst()
                .get();
    }

    private boolean canArrive(char start, char dest) {
        Set<Character> pathSet = mPathMap.get(start);
        return  pathSet.contains(dest) ||
                pathSet.stream().anyMatch(c -> canArrive(c, dest));
    }

    private boolean canArrive(Set<Character> starts, char dest) {
        return starts.stream()
                .anyMatch(start -> canArrive(start, dest));
    }

    private char next(char c) {
        Set<Character> nexts = mPathMap.get(c);
        return nexts.stream()
                .filter(next -> !canArrive(nexts, next))
                .findFirst()
                .get();
    }
}
