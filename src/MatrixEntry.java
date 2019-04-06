/**
 * Created by yuanhaoruan on 10/24/17.
 * ruanx054
 * MatrxiEntry class
 */
public class MatrixEntry {
    private int row;
    private int col;
    private int data;
    private MatrixEntry nextRow;
    private MatrixEntry nextCol;
    /* MatrixEntry constructor
       @param row a int represents the row of the matrixEntry
       @param column a int represents the column of the matrixEntry
       @param data a int represents the data entry of the matrixEntry
    */
    public MatrixEntry(int row, int col, int data) {
        this.row=row;
        this.col=col;
        this.data=data;
        this.nextRow=null;
        this.nextCol=null;
    }

    /*
        @return the column of the MatrixEntry
    */

    public int getCol() {
        return col;
    }
    /*
        @return the row of the MatrixEntry
     */

    public int getRow() {
        return row;
    }

    /*
        @return the data of the MatrixEntry
    */

    public int getData() {
        return data;
    }

    /*
        @set the column of the MatrixEntry
    */

    public void setCol(int col) {
        this.col = col;
    }

    /*
        @set the row of the MatrixEntry
    */

    public void setRow(int row) {
        this.row = row;
    }

    /*
        @set the data of the MatrixEntry
    */

    public void setData(int data) {
        this.data = data;
    }

    /*
        @return the next column of the MatrixEntry
    */

    public MatrixEntry getNextColumn() {
        return nextCol;
    }

    /*
        @set the next column of the MatrixEntry
    */

    public void setNextColumn(MatrixEntry el) {
        nextCol=el;
    }

    /*
        @return the next row of the MatrixEntry
    */

    public MatrixEntry getNextRow() {
        return nextRow;
    }

    /*
        @set the next column of the MatrixEntry
    */
    public void setNextRow(MatrixEntry el) {
        nextRow=el;
    }

}
