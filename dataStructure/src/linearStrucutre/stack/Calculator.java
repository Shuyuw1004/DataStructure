package linearStrucutre.stack;

public class Calculator {
    public static void main(String[] args) {
        //表达式
        String expression = "70-6*1-8";
        //创建两个栈，一个存放数字，一个存放运算符
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        //定义需要的相关变量
        int index = 0; //用于扫描
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch;//将每次扫描得到的char保存到ch
        String keepNum = "";// 用于拼接多位数
        //开始while循环扫描expression
        while (true) {
            //依次得到expression的每一个字符
            ch = expression.charAt(index);
            //判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)) { //如果是运算符
                //判断当前符号栈是否为空
                if (!operStack.isEmpty()) {
                    //如果符号栈有操作符，就进行比较，如果当前的操作符的优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数
                    //再从符号栈中pop出一个符号进行运算得到结果，将结果入数栈，然后将当前操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        //按照栈的pop顺序，expression是从右向左运算的，在遇到第一个运算符为减号，第二个运算符优先级高于减号，第三个运算符优先级低于减号时，结果会出错
                        //因此，需要循环判断
                        while (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                            num1 = numStack.pop();
                            num2 = numStack.pop();
                            oper = operStack.pop();
                            res = numStack.cal(num1, num2, oper);
                            //把运算的结果入数栈
                            numStack.push(res);
                        }
                        //将当前操作符入符号栈
                        operStack.push(ch);
                    } else {
                        //如果当前操作符优先级大于栈中的操作符，就直接入符号栈
                        operStack.push(ch);
                    }
                } else {
                    //如果为空直接入栈
                    operStack.push(ch);
                }
            } else { //如果是数字，则直接入数栈
                //numStack.push(ch - 48); //根据ASCII码表得出'1' = 49；
                // numStack.push(ch - '0');
                //1.当处理多位数时，不能发现是一个数就立即入栈，因为可能是多位数
                //2.在处理数时，需要向expression表达式的index后再看一位，如果是数字就进行扫描，是符号才入栈
                //3.因此，我们需要定义一个字符串变量，用于拼接

                //处理多位数
                keepNum += ch;
                //如果ch已经是expression的最后一位，就直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    //判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则将keepNum入栈
                    if (operStack.isOper(expression.charAt(index + 1))) {
                        //如果后一位是运算符，则入栈
                        numStack.push(Integer.parseInt(keepNum));
                        //清空keepNum
                        keepNum = "";
                    }
                }
            }
            //让index+1，并判断是否扫描到expression的最后
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        //当表达式扫描完毕，就顺序地从符号栈和数栈中pop出相应的数和符号，并运行
        while (true) {
            //如果符号栈为空，则计算到最后的结果，数栈中只有一个数字，即结果
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            //把运算的结果入数栈
            numStack.push(res);
        }
        int res2 = numStack.pop();
        System.out.printf("表达式%s = %d", expression, res2);
    }
}

//定义一个ArrayStack2表示栈，需要扩展功能
class ArrayStack2 {
    private int maxSize; //栈的大小
    private int[] stack; //数组模拟栈，数据就放在该数组中
    private int top = -1; //表示栈顶，初始化为-1

    //构造方法
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //返回当前栈顶的值，但是不出栈
    public int peek() {
        if (isEmpty()) {
            return -1;
        } else {
            return stack[top];
        }
    }

    //栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    //栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //入栈-push
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        //top++;
        //stack[top] = value;
        stack[++top] = value;
    }

    //出栈-pop, 将栈顶的数据返回
    public int pop() {
        if (isEmpty()) {
            //抛出异常
            throw new RuntimeException("栈空,没有数据");
        }
        //int res = stack[top];
        //top--;
        //return res;
        return stack[top--];
    }

    //显示栈的情况[遍历栈]，遍历时，需要从栈顶开始显示数据
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }
        //需要从栈顶开始显示数据
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d] = %d\n", i, stack[i]);
        }
    }

    //返回运算符的优先级，优先级使用数字表示，数字越大，优先级越高。
    public int priority(int oper) { //int类型和char类型可以混用
        if (oper == '*' || oper == '/') {
            return 2;
        } else if (oper == '-') {
            return 1; }
        else if (oper == '+') {
            return 0;
        } else {
            return -1; //假定目前表达式只有+，-，*，/
        }
    }

    //判断是不是一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    //计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0; //用于存放计算的结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1; // 注意顺序，后弹出来的数是被减数
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}
