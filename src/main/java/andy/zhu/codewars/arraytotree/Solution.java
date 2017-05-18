package andy.zhu.codewars.arraytotree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyZhu on 2017/5/15.
 * https://www.codewars.com/kata/57e5a6a67fbcc9ba900021cd
 */
public class Solution {

    static TreeNode arrayToTree(int[] array) {
        if (array.length == 0) {
            return null;
        }

        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(new TreeNode(array[0]));
        int curNodeIndex = 0;
        for (int i = 1; i < array.length; i++) {
            TreeNode l = new TreeNode(array[i]);
            nodes.get(curNodeIndex).left = l;
            nodes.add(l);
            i++;

            if (i < array.length) {
                TreeNode r = new TreeNode(array[i]);
                nodes.get(curNodeIndex).right = r;
                nodes.add(r);
            }

            curNodeIndex++;
        }
        return nodes.get(0); // TODO: implementation
    }

    static class TreeNode {

        TreeNode left;
        TreeNode right;
        int value;

        TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        TreeNode(int value) {
            this(value, null, null);
        }
    }
}
