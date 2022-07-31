package linearStrucutre.LinkedList;

import java.util.Stack;

//单链表
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "小宋", "及时雨");
        HeroNode hero6 = new HeroNode(6, "小卢", "玉麒麟");
        HeroNode hero7 = new HeroNode(7, "小吴", "智多星");
        HeroNode hero8 = new HeroNode(8, "小林", "豹子头");

        //创建两个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();

        //加入
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero6);
        singleLinkedList.add(hero8);

        singleLinkedList2.add(hero2);
        singleLinkedList2.add(hero4);
        singleLinkedList2.add(hero5);
        singleLinkedList2.add(hero7);

        //加入按照编号的顺序
/*        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);*/

        //测试合并功能
        SingleLinkedList res = combineOrderedList(singleLinkedList.getHead(),singleLinkedList2.getHead());
        res.list();

/*        //测试单链表的反转功能
        reverseList(singleLinkedList.getHead());
        singleLinkedList.list();*/

        //测试逆序打印单链表,没有改变链表的结构
        //reversePrint(singleLinkedList.getHead());

        /*//测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2, "小卢", "小尾巴");
        singleLinkedList.update(newHeroNode);

        //显示修改后的列表信息
        singleLinkedList.list();

        //删除一个节点
        singleLinkedList.del(1);
        singleLinkedList.del(4);
        singleLinkedList.list();

        //求单链表中的节点个数
        System.out.println("有效的节点个数=" + getLength(singleLinkedList.getHead()));

        //测试求倒数第k个节点的元素
        HeroNode res = findLastIndexNode(singleLinkedList.getHead(), 1);
        System.out.println("res=" + res);*/
    }

    //合并两个有序列表，合并之后的列表依然有序
    //思路：1. 新建一个头节点作为合并后list的头节点
    //2. 将两个list从头到尾遍历，将每一个节点一一比较，将较小的节点插入到较大的节点前
    //3. 若一个list已经遍历完了，则直接将遍历完的list的尾节点的next指向未遍历完的list的当前节点
    //4. 返回新的list
    public static SingleLinkedList combineOrderedList(HeroNode head1, HeroNode head2) {
        SingleLinkedList res = new SingleLinkedList();
        HeroNode curr = res.getHead();
        HeroNode temp1 = head1.next;
        HeroNode temp2 = head2.next;
        while (temp1 != null && temp2 != null) {
            if (temp1.no <= temp2.no) {
                curr.next = temp1;
                temp1 = temp1.next;
                curr = curr.next;
            } else {
                curr.next = temp2;
                temp2 = temp2.next;
                curr = curr.next;
            }
        }
        if (temp1 == null) {
            curr.next = temp2;
        }
        if (temp2 == null) {
            curr.next = temp1;
        }
        return res;
    }

    //从尾到头打印链表
    //利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，实现逆序打印的效果

    /**
     * @param head
     */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return; //空链表
        }
        //创建一个栈，将各个节点压入栈中
        Stack<HeroNode> stack = new Stack<HeroNode>();
        HeroNode curr = head.next;
        while (curr != null) {
            stack.push(curr);
            curr = curr.next; //将curr后移，压入下一个节点
        }
        //将栈中的节点进行打印，pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop()); //stack的特点是先进后出
        }
    }

    //将单链表进行反转

    /**
     * @param head 链表的头节点
     */
    public static void reverseList(HeroNode head) {
        //如果当前列表为空或者只有一个节点，则无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        HeroNode reverseHead = new HeroNode(0, "", "");
        //定义一个辅助的指针/变量，帮助我们遍历原来的链表
        HeroNode curr = head.next;
        //指向当前节点curr的下一个节点
        HeroNode next = null;
        //遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead的最前端
        while (curr != null) {
            //先暂时保存当前节点的下一个节点，后面需要使用
            next = curr.next;
            curr.next = reverseHead.next; //让curr的下一个节点指向reverseHead的最前端
            reverseHead.next = curr; //将curr连接到新的链表上
            curr = next; //让curr后移
        }
        //将head.next指向reverseHead.next,完成链表的反转
        head.next = reverseHead.next;
    }

    //查找单链表中的倒数第k个节点
    //思路：1. 编写一个方法，接收head节点，同时接收一个index
    //2. index表示倒数第index个节点
    //3. 先把链表从头到尾遍历，得到链表长度（getLength）
    //4. 得到长度后，我们从链表的头节点开始遍历（length-index）个节点
    //5. 如果找到了，就返回该节点，否则返回null

    /**
     * @param head
     * @param index
     * @return 如果找到了，就返回该节点，否则返回null
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            return null; //没有找到
        }
        //调用getLength得到链表的长度（节点的个数）
        int length = getLength(head);
        //对index进行校验
        if (index <= 0 || index > length) {
            return null;
        }
        //定义一个辅助变量，for循环定位到倒数的index个
        HeroNode curr = head.next;
        for (int i = 0; i < (length - index); i++) {
            curr = curr.next;
        }
        return curr;
    }

    //方法：获取到单链表的节点个数（如果是带头节点的单链表，则不统计头节点

    /**
     * @param head 链表的头节点
     * @return 返回有效节点的个数
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        //这里我们没有统计头节点
        HeroNode current = head.next;
        int length = 0;
        while (current != null) {
            current = current.next;
            length++;
        }
        return length;
    }
}

//定义SingleLinkedList管理我们的英雄
class SingleLinkedList {
    //先初始化一个头节点，头节点不要动，不存放具体数据
    private HeroNode head = new HeroNode(0, "", "");

    //返回头节点
    public HeroNode getHead() {
        return head;
    }

    //添加节点到单项链表
    //当不考虑编号顺序时：
    //1. 找到当前列表的最后节点
    //2. 将这个最后节点的next指向新的节点
    public void add(HeroNode heroNode) {
        //因为头节点不能动，所以我们新建一个temp节点辅助遍历
        HeroNode temp = head;
        //遍历链表，找到最后
        while (true) {
            //找到了最后一个节点，break
            if (temp.next == null) {
                break;
            } else {
                //没有找到最后，将temp后移
                temp = temp.next;
            }
        }
        //当退出while循环时，temp就指向了链表的最后
        //将最后这个节点的next指向新加入的节点
        temp.next = heroNode;
    }

    //第二种添加英雄的方法：根据英雄的排名插入到指定位置
    //（如果排名已存在，则添加失败，并给出提示）
    public void addByOrder(HeroNode heroNode) {
        //因为头节点不能动，因此我们通过一个辅助变量（指针）来帮助找到添加的位置
        //因为是单链表，所以我们找到的temp位置是位于添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        boolean flag = false; //标识添加的英雄节点编号是否存在，默认为false
        while (true) {
            if (temp.next == null) { //说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) { //位置找到，就在temp后面插入
                break;
            } else if (temp.next.no == heroNode.no) { //说明希望添加的heroNode编号已经存在
                flag = true;//说明编号存在
                break;
            }
            temp = temp.next;//将temp后移，继续遍历当前链表
        }

        //判断flag的值
        if (flag) {
            System.out.printf("准备插入的英雄编号%d已经存在了，不能加入\n", heroNode.no);
        } else {
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点的信息：根据no来修改，即no不能修改。
    //说明：根据newHeroNode的no来修改即可
    public void update(HeroNode newHeroNode) {
        //判断列表是否为空
        if (head.next == null) {
            System.out.println("Empty list.");
            return;
        }
        //根据no编号找到需要修改的节点
        HeroNode temp = head.next;
        boolean flag = false; // 表示是否找到该节点
        while (true) {
            if (temp == null) {
                break; //已经遍历完列表
            }
            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到需要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else { //没有找到
            System.out.printf("没有找到编号%d的节点，不能修改\n", newHeroNode.no);
        }
    }

    //删除节点
    //思路：1. head节点不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    //2. 在比较时，是temp.next.no和需要删除的节点no比较
    public void del(int no) {
        if (head.next == null) {
            System.out.println("Empty List");
            return;
        }
        HeroNode temp = head;
        boolean flag = false; //标识是否找到带删除节点的前一个节点
        while (true) {
            if (temp.next == null) { //已经到链表的最后
                break;
            }
            if (temp.next.no == no) { //找到待删除节点的前一个节点temp
                flag = true;
                break;
            }
            temp = temp.next; //temp后移，继续遍历
        }
        //判断flag
        if (flag) {
            temp.next = temp.next.next;//删除
        } else {
            System.out.printf("要删除的%d节点不存在\n", no);
        }
    }

    //通过遍历显示链表
    public void list() {
        //判断列表是否为空
        if (head.next == null) {
            System.out.println("Empty list.");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                System.out.println();
                break;
            } else {
                System.out.println(temp);
                temp = temp.next;
            }
        }
    }
}

//定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    //构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //为了显示方便，重写toString方法
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }

}
