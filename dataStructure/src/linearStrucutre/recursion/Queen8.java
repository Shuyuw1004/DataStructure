package linearStrucutre.recursion;

public class Queen8 {
    //定义一个max表示一共有多少个皇后
    int max = 8;
    //定义数组array，保存皇后放置位置的结果，比如arr = {0,4,7,5,2,6,1,3}
    int[] array = new int[max];
    static int count = 0;
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.printf("一共有%d种解法", count);
    }

    //编写一个方法，放置第n个皇后
    //特别注意：每一次递归时，进入到check方法中都有for (int i = 0; i < max; i++)，因此会有回溯
    private void check(int n) {
        if (n == max) { //已经放完了，直接print数组
            print();
            return;
        }
        //依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            //先把当前这个皇后n放到该行的第一列
            array[n] = i;
            //判断当前放置第n个皇后到i列时，是否冲突
            if (judge(n)) { //不冲突
                //接着放第n+1个皇后，即开始递归
                check(n + 1);
            }
            //如果冲突，就继续执行array[n] = i；即将第n个皇后放置在当前行后移的一个位置
        }
    }

    //查看当我们放置第n个皇后时，检测该皇后是否和前面已经摆放的皇后冲突

    /**
     * @param n 表示第n个皇后
     * @return
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            //array[i] == array[n]，判断两个皇后是否在同一列
            //Math.abs(n-i) == Math.abs(array[n] -array[i])，判断第n个皇后和第i个皇后是否在同一斜线上。如果行差和列差相等，则说明两个皇后在同一斜线（i.e.正方形的两个对角/y = x，斜率为1）
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    //写一个方法，可以将皇后摆放的位置输出
    private void print() {
        count++;
        for (int i : array) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
