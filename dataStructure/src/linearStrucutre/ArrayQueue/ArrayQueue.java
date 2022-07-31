package linearStrucutre.ArrayQueue;

import java.util.Scanner;

//数组模拟队列
public class ArrayQueue {
    public static void main(String[] args) {
        System.out.println("测试数组实现队列的案例");
        ArrayQueueClass arrayQueue = new ArrayQueueClass(3);
        char key = ' '; //接收用户输入
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        //输出一个菜单
        while (loop) {
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取出数据");
            System.out.println("h(head):查看列头数据");
            key = scanner.next().charAt(0); //接收一个字符
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数：");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

class ArrayQueueClass {
    private int maxSize; //表示数组的最大容量
    private int front; //队列头
    private int rear; //队列尾
    private int[] arr; //用于存放数据的数组，模拟队列

    public ArrayQueueClass(int arraySize) {
        maxSize = arraySize;
        front = -1; //指向队列头部的前一个位置，并不包含第一个数据
        rear = -1; //指向队列尾部的具体数据（就是队列的最后一个数据）
        arr = new int[maxSize];
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    //判断队列是否空
    public boolean isEmpty() {
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        rear++; //让rear后移
        arr[rear] = n;
    }

    //从队列中取出数据(出队列)
    public int getQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            //通过抛出异常(throw后的代码都无法运行)
            throw new RuntimeException("Empty queue.");
        }
        front++; //front后移
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue() {
        //遍历
        if (isEmpty()) {
            System.out.println("Empty queue.");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //显示队列的头数据，并不取出
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("Empty queue.");
        }
        return arr[front + 1];
    }
}

