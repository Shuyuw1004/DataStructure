package linearStrucutre.hashtab;

import java.util.Scanner;

public class HashTableDemo {
    public static void main(String[] args){
        HashTab hashTab = new HashTab(7);
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add: 添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建雇员
                    Emp emp = new Emp(id,name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id：");
                    id = scanner.nextInt();
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}

//表示雇员
class Emp {
    public int id;
    public String name;
    public Emp next; //next默认为空
    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}

//创建HashTab，管理多条链表
class HashTab {
    private EmpLinkedList[] empLinkedListsArray;
    private int size; //有多少条链表

    //构造器
    public HashTab (int size) {
        this.size = size;
        empLinkedListsArray = new EmpLinkedList[size];
        //!!!重要，不要忘了分别初始化每个链表,否则会有空指针异常
        for (int i = 0; i < size; i++) {
            empLinkedListsArray[i] = new EmpLinkedList();
        }
    }

    //添加雇员
    public void add(Emp emp) {
        //根据员工id,得到该员工应当添加到哪条链表
        int empLinkedListNO = hashFun(emp.id);
        //将emp添加到对应的链表
        empLinkedListsArray[empLinkedListNO].add(emp);
    }

    //遍历HashTab中所有的链表
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListsArray[i].list(i);
        }
    }

    //根据输入的id查找雇员
    public void findEmpById(int id) {
        int empLinkedListNO = hashFun(id);
        Emp emp = empLinkedListsArray[empLinkedListNO].findEmpById(id);
        if (emp != null) {
            System.out.printf("在第%d条链表中找到雇员id = %d\n", (empLinkedListNO+1));
        } else {
            System.out.println("在哈希表中没有找到该雇员");
        }
    }

    //编写散列函数，使用一个简单的取模法
    public int hashFun(int id) {
        return id % size;
    }
}

//创建EmpLinkedList，表示链表
class EmpLinkedList {
    //头指针，指向第一个Emp，默认为null
    private Emp head;

    //添加雇员到链表
    //假定当添加雇员时，id是自增长的，即id的分配总是从小到达，因此直接将新的雇员添加到链表的最后
    public void add (Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp currEmp = head;
        while (currEmp.next != null) {
            currEmp = currEmp.next;
        }
        currEmp.next = emp;
    }

    //遍历链表的雇员信息
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + (no + 1) + "链表为空");
            return;
        }
        System.out.print("第" + (no + 1) +"链表的信息为：");
        Emp currEmp = head;
        while (currEmp != null) {
            System.out.printf("=> id=%d name=%s\t", currEmp.id, currEmp.name);
            currEmp = currEmp.next;
        }
        System.out.println();
    }

    //根据ID查找雇员
    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Emp currEmp = head;
        while (true) {
            if (currEmp.id == id) {
                break;
            }
            if (currEmp.next != null) {
                currEmp = null;
                break;
            }
            currEmp = currEmp.next;
        }
        return currEmp;
    }
}
