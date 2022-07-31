package linearStrucutre.ArrayQueue;

import java.util.Scanner;

//数组模拟环形队列
public class CircleArrayQueueDemo {
    public static void main(String[] args){
        System.out.println("测试数组实现队列的案例");
        CircleArray circleQueue = new CircleArray(4);//队列中的有效数据最大是3
        char key = ' '; //接收用户输入
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        //输出一个菜单
        while(loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取出数据");
            System.out.println("h(head):查看列头数据");
            key = scanner.next().charAt(0); //接收一个字符
            switch (key){
                case 's':
                    circleQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数：");
                    int value = scanner.nextInt();
                    circleQueue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int res = circleQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int res = circleQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    }catch (Exception e){
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

class CircleArray{
    private int maxSize; //表示数组的最大容量
    private int front; //队列头, 指向队列的第一个元素，初始值为0
    private int rear; //队列尾，指向最后一个元素的后一个位置，初始值为0
    private int[] arr; //用于存放数据的数组，模拟队列

    public CircleArray(int size){
        maxSize = size;
        arr = new int[maxSize];
        front = 0;
        rear = 0;
    }

    //判断队列是否满
    public boolean isFull(){
        return (rear+1)%maxSize == front;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n){
        if (isFull()){
            System.out.println("队列满，不能加入数据");
            return;
        }
        //直接将数据加入
        arr[rear] = n;
        //将rear后移，这里必须取模，否则有可能数组越界
        rear = (rear+1)%maxSize;
    }

    //从队列中取出数据(出队列)
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()) {
            //通过抛出异常(throw后的代码都无法运行)
            throw new RuntimeException("Empty queue.");
        }
        //这里分析出front是指向队列的第一个元素
        //1. 先把front对应的值保存到一个临时变量
        //2. 将front后移,考虑取模，否则会数组越界
        //3. 将临时保留的值返回
        int value = arr[front];
        front = (front+1)%maxSize;
        return value;
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if (isEmpty()){
            System.out.println("Empty queue.");
            return;
        }
        //思路：从front开始遍历，遍历数组中总的元素个数
        for (int i = front; i < front + size(); i++) {
            i = i%maxSize; //对i取模，因为队列是环形的
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //求出当前队列有效数据个数
    public int size(){
        return (rear-front+maxSize)%maxSize;
    }

    //显示队列的头数据，并不取出
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("Empty queue.");
        }
        return arr[front];
    }
}
