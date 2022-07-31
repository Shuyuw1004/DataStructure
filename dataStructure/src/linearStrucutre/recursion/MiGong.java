package linearStrucutre.recursion;

public class MiGong {
    public static void main(String args[]) {
        //先创建一个二维数组，表示迷宫
        int[][] map = new int[8][7];
        //用1表示墙，将上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //左右也置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        //打印地图
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        setWay(map,1,1);
        System.out.println("小球走过，并标识过的地图：");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    //使用递归回溯来给小球找路
    //1. 小球从（1，1）出发，若能到达（6，5），则说明找到路径
    //2. 当map[i][j] = 0则表示该点没有走过；2表示通路可以走；3表示该点可以走，但是走不通
    //3. 在走迷宫时，需要确定一个策略：下->右->上->左，如果该点走不通，再回溯

    /**
     * @param map 表示地图
     * @param i   从哪一行开始找
     * @param j   从哪一列开始找
     * @return 如果找到路径，就返回true，否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) { //已经找到通路
            return true;
        } else {
            if (map[i][j] == 0) { //如果当前这个点还没有走过
                //按照策略走:下->右->上->左
                map[i][j] = 2;//假定该点可以走通
                if (setWay(map, i + 1, j)) { //向下走
                    return true;
                } else if (setWay(map, i, j + 1)) { //向右走
                    return true;
                } else if (setWay(map, i - 1, j)) { //向上走
                    return true;
                } else if (setWay(map, i, j - 1)) { //向左走
                    return true;
                } else {
                    //说明该点是走不通的
                    map[i][j] = 3;
                    return false;
                }
            } else { //如果map[i][j] != 0, 可能是1，2，3
                return false;
            }
        }
    }
}
