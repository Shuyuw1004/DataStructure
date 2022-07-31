package linearStrucutre.stack;

import java.util.Scanner;

public class ListStackDemo {
    public static void main(String[] args) {
        ListStack stack = new ListStack();
        String key = "";
        boolean loop = true;//控制是否退出菜单
        Scanner scanner = new Scanner(System.in);
        while(loop) {
            System.out.println("show:表示显示栈");
            System.out.println("exit:表示退出程序");
            System.out.println("push:表示添加数据到栈（入栈）");
            System.out.println("pop:表示从栈取出数据（出栈）");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch(key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

class ListStack {
    private ListNode head = new ListNode();
    //栈空
    public boolean isEmpty() {
        return head.next == null;
    }
    //入栈，使用头插法
    public void push(int val) {
        ListNode node = new ListNode(val,head.next);
        head.next = node;
    }
    //出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，没有数据");
        }
        ListNode temp = head.next;
        head.next = head.next.next;
        return temp.val;
    }
    //显示栈[遍历]
    public void list(){
        ListNode curr = head.next;
        if (curr == null) {
            System.out.println("栈空，没有数据");
            return;
        }
        while (curr != null) {
            System.out.println(curr);
            curr = curr.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next;

    //构造方法
    public ListNode() {
    }
    public ListNode(int val) {
        this.val = val;
    }
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    //重写toString方法
    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}