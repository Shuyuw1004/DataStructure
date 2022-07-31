package linearStrucutre.LinkedList;

public class Josephu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);//加入五个Boy节点
        circleSingleLinkedList.showBoy();
        circleSingleLinkedList.countBoy(1,2,5);//2->4->1->5->3
    }
}

//创建一个环形的单向链表
class CircleSingleLinkedList {
    //创建一个first节点，当前没有编号
    private Boy first = null;

    //添加Boy节点，构建成一个环形的链表
    public void addBoy(int nums) {
        //对nums做一个数据校验
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }
        Boy currBoy = null; //辅助指针，帮助构建环形链表
        //使用for循环创建环形链表
        for (int i = 1; i <= nums; i++) {
            //根据编号，创建Boy节点
            Boy boy = new Boy(i);
            //如果是第一个小孩
            if (i == 1) {
                first = boy;
                first.setNext(first); //构成环
                currBoy = first; //让currBoy指向第一个节点
            } else {
                currBoy.setNext(boy);
                boy.setNext(first);
                currBoy = boy;
            }
        }
    }

    //遍历当前环形链表
    public void showBoy() {
        //判断链表是否为空
        if (first == null) {
            System.out.println("没有任何Boy节点");
            return;
        }
        //因为first不能移动，因此我们仍然使用一个辅助指针完成遍历
        Boy currBoy = first;
        while (true) {
            System.out.printf("Boy的编号%d\n", currBoy.getNo());
            if (currBoy.getNext() == first) { //说明已经遍历完了
                break;
            }
            currBoy = currBoy.getNext(); //currBoy后移
        }
    }

    //根据用户的输入，计算Boy节点出圈的顺序

    /**
     * @param startNo  表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums     表示最初有多少小孩在圈中
     */
    public void countBoy(int startNo, int countNum, int nums) {
        //对参数进行校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误，请重新输入");
            return;
        }
        //创建一个辅助指针，帮助完成Boy节点出圈
        Boy helper = first;
        //需求创建一个辅助指针（变量）helper，事先应该指向环形链表最后一个Boy节点
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }
        //报数前，先让first和helper移动startNo-1次到指定位置
        for (int j = 0; j < startNo - 1; j++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //当小孩报数时，让first和helper指针同时移动countNum-1次，然后出圈
        //这里是循环操作，直到圈中只有一个节点
        while(true){
            if (first.getNext() == first){ //说明圈中只有一个节点
                break;
            }
            //让first和helper指针同时移动countNum-1
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //此时first指向的节点就是要出圈的Boy节点
            System.out.printf("Boy%d出圈\n",first.getNo());
            //将first指向的Boy节点出圈
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的Boy节点编号是%d\n", first.getNo());
    }
}

//创建一个Boy类，表示一个节点
class Boy {
    private int no; //编号
    private Boy next; //指向下一个节点，默认是null

    public Boy(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
