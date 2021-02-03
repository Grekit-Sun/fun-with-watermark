package com.yifan.funwithwatermark.algorithm;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-28
 */
public class Main {

    public int[] sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        return array;
    }

    private static volatile Main mMain;

    private Main() {
    }

    public static Main getInstance() {
        if (mMain == null) {
            synchronized (Main.class) {
                if (mMain == null) {
                    mMain = new Main();
                }
            }
        }
        return mMain;
    }

    /**
     * 链表反转
     */
    public ListNode listNodeReverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 获取ViewGroup的View总和
     */
    public int getViewSum(View view) {
        int cnt = 0;
        if (null == view) {
            return cnt;
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                if (child instanceof ViewGroup) {
                    cnt += getViewSum(view);
                } else {
                    cnt++;
                }
            }
        } else {
            cnt++;
        }
        return cnt;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 创建二叉树
     */
    public List<TreeNode> createBinaryTree(int[] array) {
        List<TreeNode> list = new LinkedList<>();
        for (int a : array) {
            list.add(new TreeNode(a));
        }
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            list.get(parentIndex).left = list.get(parentIndex * 2 + 1);    //左孩子
            list.get(parentIndex).right = list.get(parentIndex * 2 + 2);     //右孩子
        }
        int lastParentIndex = array.length / 2 - 1;
        list.get(lastParentIndex).left = list.get(lastParentIndex * 2 + 1);
        if (array.length % 2 == 1) {
            list.get(lastParentIndex).right = list.get(lastParentIndex * 2 + 2);
        }
        return list;
    }

    class TreeNode {
        int val;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode left;
        TreeNode right;
    }

    /**
     * 从上至下打印出二叉树的节点，同层次从左至右打印（广度优先）
     */
    public List<Integer> breadFirst(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        TreeNode current = root;
        queue.offer(current);
        if (!queue.isEmpty()) {
            current = queue.poll();
            list.add(current.val);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return list;
    }

    /**
     * 先序遍历
     */
//    public List<Integer> preTravsal(TreeNode root) {
//        Stack<TreeNode> stack = new Stack<>();
//        ArrayList<Integer> list = new ArrayList<>();
//        while (root != null || !stack.isEmpty()) {
//            while (root != null) {
//                list.add(root.val);
//                stack.push(root);
//                root = root.left;
//            }
//            if (!stack.isEmpty()) {
//                root = stack.pop();
//                root = root.right;
//            }
//        }
//        return list;
//    }

    /**
     * 中序遍历
     */
    public List<Integer> midTravsal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                list.add(root.val);
                root = root.right;
            }
        }
        return list;
    }

    public List<Integer> preTravsal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        while (root != null) {
            list.add(root.val);
            preTravsal(root.left);
            preTravsal(root.right);
        }
        return list;
    }

    public List<Integer> midTra(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        while (root != null) {
            midTravsal(root.left);
            list.add(root.val);
            midTravsal(root.right);
        }
        return list;
    }

    public boolean binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] == target) {
                return true;
            } else if (target > array[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    public boolean binarySearch(int[] array, int target, int low, int high) {
        int mid;
        if (low <= high) {
            mid = (low + high) / 2;
            if (target == array[mid]) {
                return true;
            } else if (target > mid) {
                binarySearch(array, target, mid + 1, high);
            } else {
                binarySearch(array, target, low, mid - 1);
            }
        }
        return false;
    }


    public int fibonaqi(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonaqi(n - 1) + fibonaqi(n - 2);
    }


}
