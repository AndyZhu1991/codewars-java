package andy.zhu.codewars.smallfuck;

/**
 * Created by AndyZhu on 2017/5/16.
 * https://www.codewars.com/kata/58678d29dbca9a68d80000d7
 */
public class SmallFuck {

    public static void main(String[] args) {
        System.out.println(interpreter("*>*>[[[[[>[*>*]>*]]]]*]>*>*", "00101100"));
    }

    public static String interpreter(String code, String tape) {
        // Implement your interpreter here
        int pc = 0;
        int pt = 0; // Memory pointer
        int[] memory = tape.chars().map(c -> c - '0').toArray();

        while (true) {
            switch (code.charAt(pc)) {
                case '>':
                    pt++;
                    break;
                case '<':
                    pt--;
                    break;
                case '*':
                    memory[pt] = memory[pt] == 0 ? 1 : 0;
                    break;
                case '[':
                    if (memory[pt] == 0) {
                        int extraLeftBracket = 0;
                        for (pc += 1; pc < code.length(); pc++) {
                            if (code.charAt(pc) == '[') {
                                extraLeftBracket++;
                            }
                            if (code.charAt(pc) == ']') {
                                if (extraLeftBracket == 0) {
                                    break;
                                } else {
                                    extraLeftBracket--;
                                }
                            }
                        }
                    }
                    break;
                case ']':
                    if (memory[pt] != 0) {
                        int extraRightBracket = 0;
                        for (pc -= 1; pc >= 0; pc--) {
                            if (code.charAt(pc) == ']') {
                                extraRightBracket++;
                            }
                            if (code.charAt(pc) == '[') {
                                if (extraRightBracket == 0) {
                                    break;
                                } else {
                                    extraRightBracket--;
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            if (isStopped(code, memory, pc, pt)) return encodeResult(memory);
            pc++;
            if (isStopped(code, memory, pc, pt)) return encodeResult(memory);
        }
    }

    private static boolean isStopped(String code, int[] memory, int pc, int pt) {
        return pc < 0 || pc >= code.length() || pt < 0 || pt >= memory.length;
    }

    private static String encodeResult(int[] memory) {
        StringBuilder sb = new StringBuilder();
        for (int i: memory) {
            sb.append(i);
        }
        return sb.toString();
    }
}
