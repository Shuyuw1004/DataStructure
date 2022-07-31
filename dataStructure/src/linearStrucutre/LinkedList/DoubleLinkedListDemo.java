package linearStrucutre.LinkedList;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        System.out.println("双向链表的测试");
        //创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        //创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
/*        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);*/
        //顺序添加
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        //显示
        doubleLinkedList.list();
        /*//修改
        HeroNode2 hero5 = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(hero5);
        doubleLinkedList.list();
        //删除
        doubleLinkedList.del(3);
        doubleLinkedList.list();*/

    }
}

class DoubleLinkedList{
    //先初始化一个头节点，不能移动，且不存放具体数据
    private HeroNode2 head = new HeroNode2(0,"","");

    //返回头节点
    public HeroNode2 getHead(){
        return head;
    }

    //添加一个节点到双向链表的最后
    public void add(HeroNode2 heroNode) {
        //因为头节点不能动，所以我们新建一个temp节点辅助遍历
        HeroNode2 temp = head;
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
        //形成一个双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    //顺序添加
    public void addByOrder(HeroNode2 heroNode) {
        //因为头节点不能动，因此我们通过一个辅助变量（指针）来帮助找到添加的位置
        HeroNode2 temp = head;
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
            //插入到链表中，temp的前面
            if (temp.next != null){
                heroNode.next = temp.next;
                heroNode.pre = temp;
                temp.next.pre = heroNode;
                temp.next = heroNode;
            } else {
                temp.next = heroNode;
                heroNode.pre = temp;
            }
        }
    }

    //修改一个节点的内容，和单向链表一致
    //修改节点的信息：根据no来修改，即no不能修改。
    //说明：根据newHeroNode的no来修改即可
    public void update(HeroNode2 newHeroNode) {
        //判断列表是否为空
        if (head.next == null) {
            System.out.println("Empty list.");
            return;
        }
        //根据no编号找到需要修改的节点
        HeroNode2 temp = head.next;
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

    //从双向链表中删除一个节点
    //对于双向链表，我们可以直接找到要删除的节点，找到后，自我删除即可
    public void del(int no) {
        //判断当前列表是否为空
        if (head.next == null) {
            System.out.println("Empty List");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false; //标识是否找到带删除节点
        while (true) {
            if (temp == null) { //已经到链表的最后
                break;
            }
            if (temp.no == no) { //找到待删除节点
                flag = true;
                break;
            }
            temp = temp.next; //temp后移，继续遍历
        }
        //判断flag
        if (flag) {
            temp.pre.next = temp.next;
            //如果是最后一个节点，就不需要执行下面这句话，否则会出现空指针
            //此时temp.next = null -> null.pre：空指针
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的%d节点不存在\n", no);
        }
    }

    //遍历并显示双向链表的方法
    public void list() {
        //判断列表是否为空
        if (head.next == null) {
            System.out.println("Empty list.");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;
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
class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next; //指向下一个节点，默认为null
    public HeroNode2 pre; //指向上一个节点，默认为null

    //构造器
    public HeroNode2(int no, String name, String nickname) {
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