package com.yll.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yll on 18/3/22.
 * 一些算法
 */

public class SomeAlgorithm {

    /**
     * 2、替换空格
     */
    public String replaceSpace(StringBuffer str) {
        StringBuffer newStr = new StringBuffer();
        char[] characters = str.toString().toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (' ' == characters[i]) {
                newStr.append("%20");
            } else {
                newStr.append(characters[i]);
            }
        }
        return newStr.toString();
    }


    /**
     * 3、输入一个链表，从尾到头打印链表每个节点的值。
     */
    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> newList = new ArrayList<>();
        ListNode p = listNode;
        while (p != null) {
            list.add(p.val);
            p = p.next;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            newList.add(list.get(i));
        }
        return newList;
    }

    /**
     * 先序遍历：遍历顺序规则为【根左右】
     * 中序遍历：遍历顺序规则为【左根右】
     * 后序遍历：遍历顺序规则为【左右根】
     * 4、输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {

        if (pre == null || pre.length == 0 || in == null || in.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return root;
    }

    /**
     * 5、用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */

    private Stack<Integer> stack1 = new Stack<Integer>();
    private Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        int result = stack2.pop();

        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        return result;
    }

    /**
     * 6、旋转数组的最小数字
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。 NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     */

    public int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int left = 0, right = array.length - 1, middle = 0;

        while (array[left] >= array[right]) {
            if (right - left <= 1) {
                middle = right;
                break;
            }
            middle = left + (right - left) / 2;
            if (array[middle] >= array[left]) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return array[middle];
    }

    /**
     * 7、大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。
     */
    public int fibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * 8、跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     */

    public int jumpFloor(int target) {
        if (target <= 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }

    /**
     * 9、变态跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
     * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
     */
    public int JumpFloorII(int target) {
        if (target == 1) {
            return 1;
        }
        return JumpFloorII(target - 1) + 1;
    }

    /**
     * 10、矩形覆盖
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     */
    public int RectCover(int target) {
        if (target <= 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        return RectCover(target - 1) + RectCover(target - 2);
    }

    /**
     * 11、二进制中1的个数
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示
     */
    public int NumberOf1(int n) {
        /**
         如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，那么原来处在整数最右边的1就会变为0，
         原来在1后面的所有的0都会变成1(如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
         */
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    /**
     * 12、数值的整数次方
     * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
     */
    public double Power(double base, int n) {

        /**
          * 1.全面考察指数的正负、底数是否为零等情况。
          * 2.写出指数的二进制表达，例如13表达为二进制1101。
          * 3.举例:10^1101 = 10^0001*10^0100*10^1000。
          * 4.通过&1和>>1来逐位读取1101，为1时将该位代表的乘数累乘到最终结果。
          */
        double result = 1, curr = base;
        int exponent;
        if (n > 0) {
            exponent = n;
        } else if (n < 0) {
            if (base == 0) {
                throw new RuntimeException("分母不能为0");
            }
            exponent = -n;
        } else {
            return 1;
        }
        while (exponent != 0) {
            if ((exponent & 1) == 1) {
                result = result * curr;
            }
            curr = curr * curr;
            exponent = exponent >> 1;
        }
        return n > 0 ? result : (1 / result);
    }

    /**
     * 13、调整数组顺序使奇数位于偶数前面
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
     * 所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。*/
    public void reOrderArray(int [] array) {

        ArrayList<Integer> evenList = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            if (array[i] % 2 == 1){
                array[i - evenList.size()] = array[i];
            }else {
                evenList.add(array[i]);
            }
        }
        for (int i = 0; i < evenList.size(); i++){
            array[array.length - evenList.size() + i] = evenList.get(i);
        }
    }

    /**14、链表中倒数第k个元素
     * 输入一个链表，输出该链表中倒数第k个结点*/
    public ListNode FindKthToTail(ListNode head,int k) {
        //p和pre都指向head，p先跑，p跑了k-1个节点后pre开始跑，则p跑到尾部时pre就指向倒数第k个节点
        ListNode p = head, pre = head;
        if (k <= 0){
            return null;
        }
        int tempK = k, count = 0;
        while (p != null){
            p = p.next;
            count++;
            if (tempK < 1){
                pre = pre.next;
            }
            tempK--;
        }
        if (k > count){
            return null;
        }
        return pre;
    }

    /**15、反转链表
     * 输入一个链表，反转链表后，输出链表的所有元素*/
    public ListNode ReverseList(ListNode head) {
        //三个指针遍历
        ListNode pre = null, next = null;
        while (head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**16、合并两个排序的链表
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。*/
    public ListNode Merge(ListNode list1,ListNode list2) {
        if (list1 == null){
            return list2;
        }
        if (list2 == null){
            return list1;
        }
        ListNode head;
        if (list1.val <= list2.val){
            head = list1;
            list1 = list1.next;
        }else {
            head = list2;
            list2 = list2.next;
        }
        ListNode p = head;
        while (p != null){
            if (list1 != null && list2 != null){
                if (list1.val <= list2.val){
                    p.next = list1;
                    list1 = list1.next;
                }else {
                    p.next = list2;
                    list2 = list2.next;
                }
            }else {
                if (list1 == null){
                    p.next = list2;
                    if (list2 != null) {
                        list2 = list2.next;
                    }
                }else {
                    p.next = list1;
                    list1 = list1.next;
                }
            }
            p = p.next;
        }
        return head;
    }
    /**可用递归实现*/
    public ListNode Merge2(ListNode list1,ListNode list2) {
        if (list1 == null){
            return list2;
        }
        if (list2 == null){
            return list1;
        }
        ListNode result = null;
        if (list1.val <= list2.val){
            result = list1;
            result.next = Merge2(list1.next, list2);
        }else {
            result = list2;
            result.next = Merge2(list1, list2.next);
        }
        return result;
    }


    /**17、树的子结构
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）*/
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null){
            return false;
        }
        //递归判断 先判断roo1是否包含root2,如果不包含则依次判断 root1的左子树和右子树是否包含root2
        boolean result = false;
        if (root1.val == root2.val){
            result = doesThree1HasThree2(root1, root2);
        }
        if (!result){
            result = HasSubtree(root1.left, root2);
        }
        if (!result){
            result = HasSubtree(root1.right, root2);
        }
        return result;
    }

    /**tree1 是否包含tree2*/
    private boolean doesThree1HasThree2(TreeNode tree1, TreeNode tree2){
        if (tree1 == null && tree2 != null){
            return false;
        }
        if (tree2 == null){
            return true;
        }
        if(tree1.val != tree2.val) {
            return false;
        }
        return doesThree1HasThree2(tree1.left, tree2.left) && doesThree1HasThree2(tree1.right, tree2.right);
    }

    /**18、二叉树的镜像
     * 操作给定的二叉树，将其变换为源二叉树的镜像。(左右子树交换)*/
    public void Mirror(TreeNode root) {
        if (root != null){
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            Mirror(root.left);
            Mirror(root.right);
        }
    }

    /**19、顺时针打印矩阵
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字*/
    public static ArrayList<Integer> printMatrix(int [][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new ArrayList<>();
        }
        return printMatrix(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }

    private static ArrayList<Integer> printMatrix(int [][] matrix, int startRow, int endRow, int startCol, int endCol){
        ArrayList<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return result;
        }
        if (startRow > endRow || startCol > endCol){
            return result;
        }

        for (int i = startCol; i <= endCol; i++){
            result.add(matrix[startRow][i]);
        }
        for (int i = startRow + 1; i <= endRow; i++){
            result.add(matrix[i][endCol]);
        }
        if (startRow != endRow) {
            for (int i = endCol - 1; i >= startCol; i--) {
                result.add(matrix[endRow][i]);
            }
        }
        if (startCol != endCol) {
            for (int i = endRow - 1; i >= startRow + 1; i--) {
                result.add(matrix[i][startCol]);
            }
        }
        result.addAll(printMatrix(matrix, startRow + 1, endRow - 1, startCol + 1, endCol - 1));
        return result;
    }

    /**20、包含min函数的栈
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。*/
    public class Solution {
        //使用辅助栈min存放小元素，push的值如果比min的top值更小则同时push到min里
        //pop的值如果和min的top值一样，则min也需要pop
        Stack<Integer> data = new Stack<>(), min = new Stack<>();

        public void push(int node) {
            data.push(node);
            if (min.empty()){
                min.push(node);
            }else {
                if (node < min.peek()){
                    min.push(node);
                }
            }
        }

        public void pop() {
            int num = data.pop();
            if (!min.empty()){
                if (num == min.peek()){
                    min.pop();
                }
            }
        }

        public int top() {
            return data.peek();
        }

        public int min() {
            return min.peek();
        }
    }

    /**21、栈的压入、弹出序列
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
     * 例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，
     * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）*/

    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if (pushA == null || pushA.length == 0){
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0, j = 0; i < pushA.length; i++){
            stack.push(pushA[i]);
            while (j < popA.length && popA[j] == stack.peek()){
                stack.pop();
                j++;
            }
        }
        return stack.empty();
    }

    /**22、从上往下打印二叉树
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。*/
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        //需要借助一个队列 保存二叉树的节点
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null){
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode treeNode;
        while (!queue.isEmpty()){
            treeNode = queue.poll();
            list.add(treeNode.val);
            if (treeNode.left != null){
                queue.add(treeNode.left);
            }
            if (treeNode.right != null){
                queue.add(treeNode.right);
            }
        }
        return list;
    }


    /**23、二叉搜索树的后序遍历序列
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。*/

    public static boolean VerifySquenceOfBST(int [] sequence) {
        //递归
        //二叉搜索树后序遍历比如 2,9,5,16,17,15,19,18,12 最后是根节点，其余可以分为两部分，第一部分值全小于根，第二部分值全大于根（某部分可能为空如1,2,3,4,5）
        //分开的两部分又继续满足上述条件
        if (sequence == null || sequence.length == 0){
            return false;
        }
        return VerifySquenceOfBST(sequence, 0, sequence.length - 1);
    }

    private static boolean VerifySquenceOfBST(int[] array, int start, int end){
        if (start >= end){
            return true;
        }
        int root = array[end];
        int i = start;
        //找到第一个大于root的点
        for (; i < end ; i++){
            if (array[i] > root){
                break;
            }
        }
        //判断剩下的点都要大于root
        for (int j = i + 1; j < end; j++){
            if (array[j] < root){
                return false;
            }
        }

        return VerifySquenceOfBST(array, start, i - 1) && VerifySquenceOfBST(array, i, end - 1);
    }

    /**24、二叉树中和为某值的路径
     * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。*/

    private ArrayList<ArrayList<Integer>> allList = new ArrayList<>();
    private ArrayList<Integer> list = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if (root == null){
            return allList;
        }
        list.add(root.val);
        target = target - root.val;
        if (target == 0 && root.left == null && root.right == null){
            //找到一条路径
            allList.add(new ArrayList<>(list));
        }
        FindPath(root.left, target);
        FindPath(root.right, target);
        //找到叶子节点但不满足条件时，需要回缩到上级节点
        list.remove(list.size() - 1);
        return allList;
    }

    /**25、复杂链表的复制
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。 */

    public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }
    /**思路
     * 1、每个节点之后插入该节点的复制节点，如复制A得到A1然后插到A后面
     * 2、遍历链表 赋值 A1.random = A.random
     * 3、拆分原链表和复制后的链表*/
    public RandomListNode Clone(RandomListNode pHead){
        if (pHead == null){
            return null;
        }
        RandomListNode p = pHead;
        while (p != null){
            RandomListNode p1 = new RandomListNode(p.label);
            p1.next = p.next;
            p.next = p1;
            p = p1.next;
        }

        p = pHead;
        while (p != null){
            if (p.random != null){
                p.next.random = p.random.next;
            }
            if (p.next != null) {
                p = p.next.next;
            }
        }

        RandomListNode resultHead = pHead.next;
        RandomListNode tempP = resultHead;
        p = pHead;
        while (p != null){
            p.next = tempP.next;
            if (p.next != null){
                tempP.next = p.next.next;
            }
            p = p.next;
            tempP = tempP.next;
        }
        return resultHead;
    }

    /**26、二叉搜索树与双向链表
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。*/

    public TreeNode Convert(TreeNode pRootOfTree) {
        /**1、中序遍历搜索树得到排序的节点列表
         * 2、遍历列表改变节点指针指向*/
        ArrayList<TreeNode> list = inOrder(pRootOfTree);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).left = i == 0 ? null : list.get(i - 1);
                list.get(i).right = i == list.size() - 1 ? null : list.get(i + 1);
            }
            return list.get(0);
        }
        return null;
    }

    public ArrayList<TreeNode> inOrder(TreeNode treeNode){
        ArrayList<TreeNode> list = new ArrayList<>();
        if (treeNode != null){
            if (treeNode.left != null){
                list.addAll(inOrder(treeNode.left));
            }
            list.add(treeNode);
            if (treeNode.right != null){
                list.addAll(inOrder(treeNode.right));
            }
        }
        return list;
    }


    /**27、字符串的排列
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。*/

    public ArrayList<String> Permutation(String str) {
        //思路 回溯算法，依次交换字符
        ArrayList<String> list = new ArrayList<>();
        if (str != null && str.length() > 0){
            PermutationHelper(str.toCharArray(), 0, list);
            Collections.sort(list);
        }
        return list;
    }

    private void PermutationHelper(char[] chars, int i, ArrayList<String> list){
        if (i == chars.length - 1){
            list.add(String.valueOf(chars));
        }else {
            for (int j = i; j < chars.length; j++){
                if (i != j && chars[i] == chars[j]){
                    //相同字符不用交换
                    continue;
                }
                swapChar(chars, i, j);
                PermutationHelper(chars, i + 1, list);
                swapChar(chars, j, i);  //换回来，不然会影响下次交换
            }
        }
    }

    private void swapChar(char[] chars, int i, int j){
        if (chars != null && i != j){
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
    }

    /**28、数组中出现超过一半的数字
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。如果不存在则输出0。*/
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array == null || array.length == 0){
            return 0;
        }
        int num = array[0];
        int times = 1;
        //数组有数字出现次数超过长度一半一点能找出，但如果没有结果就不确定，所以之后需要在验证一下
        for (int i = 1; i < array.length; i++){
            if (num == array[i]){
                times++;
            }else {
                times--;
            }
            if (times == 0){
                num = array[i];
                times++;
            }
        }
        times = 0;
        for (int i = 0; i < array.length; i++){
            if (array[i] == num){
                times++;
            }
        }
        if (times > array.length >> 1){
            return num;
        }
        return 0;
    }

    /**29、最小的k个数
     * 输入n个整数，找出其中最小的K个数。*/
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (k > input.length || k <= 0){
            return list;
        }
        /**最大堆，每次add元素时会自动排序，保证第一个元素是最大值*/
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i2 - i1;
            }
        });
        for (int i = 0; i < input.length; i++){
            if (maxHeap.size() < k){
                maxHeap.add(input[i]);
            }else {
                if (maxHeap.peek() > input[i]){
                    maxHeap.poll();
                    maxHeap.add(input[i]);
                }
            }
        }
        while (!maxHeap.isEmpty()){
            list.add(maxHeap.poll());
        }
        return list;
    }

    /**30、连续子数组的最大和
     * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8*/
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0){
            return 0;
        }
        //动态规划
        int sum = array[0];
        int tempSum = array[0];
        for (int i = 1; i < array.length; i++){
            tempSum = (tempSum < 0) ? array[i] : tempSum + array[i];
            sum = tempSum > sum ? tempSum : sum;
        }
        return sum;
    }
}
