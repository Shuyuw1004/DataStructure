package linearStrucutre.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        //完成将一个中缀表达式转后缀表达式的功能
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("后缀表达式对应的List" + infixExpressionList);
        List<String> parseSuffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("中缀表达式对应的List" + parseSuffixExpressionList);

        System.out.printf("expression=%d",calculate(parseSuffixExpressionList));

        //先定义一个逆波兰表达式
        //（3+4）*5-6 -> 3 4 + 5 * 6 -
        //为了方便，逆波兰表达式的数字和符号使用空格格开
/*        String suffixExpression = "3 4 + 5 * 6 -";
        //1.先将"3 4 + 5 * 6 -"放到ArrayList中
        //2.将ArrayList传递一个方法，遍历配合栈完成计算
        List<String> rpnList = getListString(suffixExpression);
        int res = calculate(rpnList);
        System.out.println("计算的结果是 = " + res);*/
    }

    //将一个中缀表达式转成对应的List
    public static List<String> toInfixExpressionList(String s) {
        //定义一个List,存放中缀表达式对应的内容
        List<String> list = new ArrayList<String>();
        int i = 0; //指针，用于遍历中缀表达式字符串s
        String str; //对多位数的拼接工作
        char c; //每遍历到一个字符，就放入到c中
        do {
            //如果c是一个非数字，直接加入到list
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                list.add("" + c);
                i++;
            } else { //如果是一个数字，需要考虑多位数的问题
                str = ""; //先将str置空
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                list.add(str);
            }
        } while (i < s.length());
        return list;
    }

    //将中缀表达式得到的List转化为后缀表达式对应的List
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<String>(); //符号栈
        //因为s2栈在整个转化的过程中没有pop操作，而且后面还需要逆序输出结果，比较麻烦
        //所以，这里我们不使用Stack<String>，直接使用List<String> s2
        List<String> s2 = new ArrayList<String>(); //储存中间结果的List2

        //遍历ls
        for (String item : ls) {
            //如果是一个数，加入s2
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号"）"，则依次弹出s1栈顶的运算符，并加入s2，直到遇到左括号为止
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                //将一对括号丢弃, 将"（"弹出s1栈，消除小括号
                s1.pop();
            } else {
                //当item的优先级小于或等于s1栈顶的运算符时，将s1栈顶的运算符弹出并加入到s2中，再次将item与s1中新的栈顶运算符相比较
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                //还需要将item压入符号栈s1中
                s1.push(item);
            }
        }

        //将s1中剩余的运算符依次弹出并加入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }

        return s2; //注意，因为是存放到List中，因此按顺序输出就是对应的逆波兰（后缀）表达式
    }

    //将逆波兰表达式的数据和运算符依次放到ArrayList中
    public static List<String> getListString(String suffixExpression) {
        //将suffixExpression分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<String>();
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    //完成对逆波兰表达式的计算
    public static int calculate(List<String> ls) {
        //只需要一个栈即可
        Stack<String> stack = new Stack<String>();
        //遍历ls
        for (String item : ls) {
            //这里使用正则表达式来取出数
            if (item.matches("\\d+")) { //匹配的是多位数
                //入栈
                stack.push(item);
            } else {
                //pop出两个数并运算后再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                //把res入栈
                stack.push(String.valueOf(res));
            }
        }
        //最后留在栈中的就是结果
        return Integer.parseInt(stack.pop());
    }
}

//返回一个运算符对应的优先级
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写一个方法，返回对应的优先级数字
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
}
