package linearStrucutre.SparseArray;

//稀疏数组
public class SparseArray {
    public static void main(String[] args) {
        //创建一个原始的二维数组
        //0：表示没有棋子；1：表示黑子；2：表示蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;

        //输出原始的二维数组
        System.out.println("原始的二维数组：");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //将二维数组转化为稀疏数组
        //1. 先遍历二维数组，得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }

        //创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];

        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        //遍历原始二维数组，将非0的值存放到稀疏数组中去
        int count = 1; //count用于记录是第几个非0数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                    count++;
                }
            }
        }

        //输出稀疏数组的形式
        System.out.println("得到的稀疏数组：");
        for (int[] row : sparseArr) {
            System.out.println();
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
        }
        System.out.println();

        //将稀疏数组恢复成原来的二维数组
        /*
        1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        2. 再读取稀疏数组后面几行的数据，并赋值给原始的而为数组
         */
        int row = sparseArr[0][0];
        int col = sparseArr[0][1];
        int chessArr2[][] = new int[row][col];
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        //
        System.out.println("恢复后的二维数组：");
        for (int[] i : chessArr2) {
            System.out.println();
            for (int data : i) {
                System.out.printf("%d\t", data);
            }
        }
    }
}
