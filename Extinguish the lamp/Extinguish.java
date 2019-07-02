/**
 * 熄灯游戏体现的是一种状态的叠加，元素的状态状态是四周及其本身状态的叠加
 * 另一方面通过分析发现第一行状态确定后其余的几行状态必定由前一行确定，也就是说大大减少了枚举的状态
 * 判定方面只需要判定最后一行的状态满足全部熄灭就可以
 */
public class Extinguish {

    /**
     * 初始灯状态
     * 我们将构造一个比实际矩阵大的矩阵，方便统一操作
     * 例如第一行的状态缺少上方的状态叠加，需要特殊处理
     * 在矩阵的上左右各添加一行/列，这样无需做特殊处理
     * 范围约束 ：
     * 列：[1,puzzle[r].length-2]
     * 行：[1,puzzle.length-1]
     */
    private int[][] puzzle;
    /**
     * 是否按下状态
     * 同上
     */
    private int[][] press;

    private int row;
    private int column;

    public Extinguish(int r, int c) {
        this.column = c;
        this.row = r;
        this.puzzle = new int[r+1][c+2];
        this.press = new int[r+1][c+2];
    }

    /**
     * @param puzzle the puzzle to set
     */
    public void setPuzzle(int[][] puzzle) {
        if(puzzle.length!=row||puzzle[0].length!=column) {
            throw new ArrayIndexOutOfBoundsException("数组需要大小r="+row+",c="+column);
        }
        copyArray(puzzle);
    }

    private void copyArray(int[][] soure) {
        for(int r=1,sr=0;r<puzzle.length;r++,sr++) {
            for(int c=1,sc=0;c<puzzle[r].length-1;c++,sc++){
                puzzle[r][c] = soure[sr][sc];
            }
        }
    }


    /**
     * 枚举第一行的状态，其余的几行的状态（灯状态/是否按下状态）都是根据第一行的状态来决定的，
     * 第一行确定第二行，第二行确定第三行，以此类推，最后一行是否全灭来判第一行的状态是否有解
     */
    private boolean guess() {
        // 不包括最后一行，最后一行是结果需要构造后判断的
        for(int r=1;r<puzzle.length-1;r++) {
            // 最后一列为伪列，不包含
            for(int c=1;c<puzzle[r].length-1;c++) {
                // 当前行未熄灭的由下一行进行熄灭，此处为计算下一行是否按下   
                // 计算的是当前灯的上左右本身，根据数值判断下方是否按下，来熄灭当前灯  
                press[r+1][c] = (puzzle[r][c]+press[r][c]+press[r-1][c]+press[r][c-1]+press[r][c+1])%2;
            }
        }
        int lastRow = puzzle.length-1;
        // 计算最后一行是否全部熄灭
        for(int c=1;c<puzzle[lastRow].length-1;c++ ) {
            if((press[lastRow][c]+press[lastRow][c-1]+press[lastRow][c+1]+press[lastRow-1][c])%2!= puzzle[lastRow][c])
                return false;
        }   
        return true;
    }

    // 通过行计算
    public boolean enumeration() {
        int r1 = 0;
        for(int c=1;c<press[1].length-1;c++) {
            press[1][c] = 0;
        }
        
        while(!guess()){
            int rt = r1++;
            for(int c=1;c<press[1].length-1;c++) {
                press[1][c] = rt&0x1;
                rt = rt>>1;
            }
            if (r1>=(2<<column-1)) {
                return false;
            }
            
        }
        return true;
    }

    /**
     * @return the press
     */
    public int[][] getPress() {
        return press;
    }

    public static void main(String[] args) {
        // int r1 = 16-1;
        // int[] i = new int[4];
        // for(int c=0;c<i.length;c++) {
        //     i[c] = r1&0x1;
        //     r1 = r1>>1;
        //     System.out.print(i[c]);
        // }
        // System.out.println(2<<3);
    
        Extinguish e = new Extinguish(3, 3);
        int[][] puzzle = new int[3][3];
        for (int r = 0; r < puzzle.length; r++) {
            for (int c = 0; c < puzzle[r].length; c++) {
                puzzle[r][c] = (int)(Math.random()*2);
                System.out.print(puzzle[r][c]+"  ");
            }
            System.out.println();
        }
        
        e.setPuzzle(puzzle);
        System.out.println("ok:"+e.enumeration());
        for (int[] var1 : e.getPress()) {
            for (int var : var1) {
                System.out.print(var+"  ");
            }
            System.out.println();
        }

    }
}