import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Created by yuanhaoruan on 10/24/17.
 * ruanx054
 * Part IV Analysis and Comparison of Space Efficiency of your SparseIntMatrix class
 * (1) Because for the matrixEntry, row,column,data,link to next row element and link to next column element each reuqires
 *     one memory unit, one MatrixEntry  require a memory unit of 5. For SparseIntMatrix implementation,only the non-zero
 *     element is represented by the MatrixEntry, so for m non-zero elements, they occupy a memory unit of 5m. For 2D array
 *     implementation, each element occupy one memory unit so the total memory is N^2
 * (2) When N=100,000 and m=1,000,000, SparseIntMatrix implementation has a memory of 5m=5000,000 unit and 2D array implementayion
 *     has a memory of 10,000,000,000, therefore SparseIntMatrix is more space efficient by 9,995,000,000unit, which is worth
 *     the effort.
 *     When 2D array implementation become more space-efficient than the SparseIntMatrix implementation,5m>N^2,m>(N^2)/5
 */
public class SparseIntMatrix {
    private int numRows;
    private int numCols;
    private String inputFile;
    private MatrixEntry[] rowNodes;
    private MatrixEntry[] colNodes;

    /* SparseIntMatrix constructor
       @param numRow a int represents the how many rows of the SparseIntMatrix
       @param numCols a int represents the how many columns of the SparseIntMatrix
    */

    public SparseIntMatrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.rowNodes = new MatrixEntry[numRows];
        this.colNodes = new MatrixEntry[numCols];
        //initialize head of row and column to null, the initial matrix has all the elements to 0
        for (int i = 0; i < numRows; i++) {
            rowNodes[i] = null;
        }
        for (int i = 0; i < numCols; i++) {
            colNodes[i] = null;
        }
    }

    /* SparseIntMatrix constructor
       @param numRow a int represents the how many rows of the SparseIntMatrix
       @param numCols a int represents the how many columns of the SparseIntMatrix
       @param inputFile file name of the file that needs to be read
    */
    public SparseIntMatrix(int numRows, int numCols, String inputFile) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.inputFile = inputFile;
        this.rowNodes = new MatrixEntry[numRows];
        this.colNodes = new MatrixEntry[numCols];
        //initialize head of row and column to null, the initial matrix has all the elements to 0
        for (int i = 0; i < numRows; i++) {
            rowNodes[i] = null;
        }
        for (int i = 0; i < numCols; i++) {
            colNodes[i] = null;
        }
        // a try catch block to deal with the exception
        try {
            // read the file through scanner
            Scanner sc = new Scanner(new File(inputFile));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                // split each line by ","
                // and read the line to array, the first element in array represents the row,
                // second element represents the column and the the element represents the data
                // use set element to set these information into the SparseIntMatrix
                String[] s = line.split(",");
                int r = Integer.parseInt(s[0]);
                int c = Integer.parseInt(s[1]);
                int d = Integer.parseInt(s[2]);
                setElement(r, c, d);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file exist");
        }

    }

    /* SparseIntMatrix constructor
       @param row a int represents the rows of the element to be returned
       @param columns a int represents the rows of the element to be returned
       @return the element of the matrixentry at that row and column, if no MatrixEntry at the row and column,return 0
    */
    public int getElement(int row, int columns) {
        int element = 0;
        // the condition which the head is not null
        if ((rowNodes[row] != null) && (colNodes[columns] != null)) {
            // if the element to be returned is the head of that row and column
            if ((rowNodes[row].getCol() == columns) && (colNodes[columns].getRow() == row)) {
                element = rowNodes[row].getData();
                //the element to be returned is the head of that row but not the head of the columns
            } else if ((rowNodes[row].getCol() == columns) && (colNodes[columns].getRow() != row)) {
                MatrixEntry currentEntry = colNodes[columns];
                while (currentEntry != null && currentEntry.getRow() != row) {
                    currentEntry = currentEntry.getNextRow();
                }
                if (currentEntry == null) {
                    element = 0;
                } else {
                    element = currentEntry.getData();
                }
                //the element to be returned is the head of that column but not the head of the row
            } else if ((rowNodes[row].getCol() != columns) && (colNodes[columns].getRow() == row)) {
                MatrixEntry currentEntry = rowNodes[row];
                while (currentEntry != null && currentEntry.getCol() != columns) {
                    currentEntry = currentEntry.getNextColumn();
                }
                if (currentEntry == null) {
                    element = 0;
                } else {
                    element = currentEntry.getData();
                }
                //the element to be returned is the not the head of that row nor the head of the columns
            } else {
                MatrixEntry currentEntry = rowNodes[row];
                //System.out.println("The value of current entry is:"+currentEntry.getData());
                while (currentEntry != null && currentEntry.getCol() != columns) {
                    //System.out.println("The value of current entry is:"+currentEntry.getData());
                    currentEntry = currentEntry.getNextColumn();
                }
                if (currentEntry == null) {
                    element = 0;
                } else {
                    element = currentEntry.getData();
                }
            }
        }
        return element;
    }

    /* SparseIntMatrix constructor
       @param row a int represents the rows of the element to be added
       @param col a int represents the rows of the element to be added
       @param data a int represents the data of the element to be added
       @return true if the element is added, otherwise false
    */
    public boolean setElement(int row, int col, int data) {
        boolean result = true;
        // if out of bounds,set result to false
        if (row >= numRows || col >= numCols) {
            result = false;
        } else {
            // create new Matrixentry
            MatrixEntry newEntry = new MatrixEntry(row, col, data);
            // if the column and row both have no element, then the elements is the head of the row and column
            if ((rowNodes[row] == null) && (colNodes[col] == null)) {
                rowNodes[row] = newEntry;
                colNodes[col] = newEntry;
                // if the row both has no element, but column has the element
            } else if ((rowNodes[row] == null) && (colNodes[col] != null)) {
                //if the element to be added will be the head of the column
                if (row < colNodes[col].getRow()) {
                    newEntry.setNextRow(colNodes[col]);
                    rowNodes[row] = newEntry;
                    colNodes[col] = newEntry;
                } else {
                    MatrixEntry prevEntry = colNodes[col];
                    MatrixEntry nextEntry = colNodes[col].getNextRow();
                    while (nextEntry != null && row > nextEntry.getRow()) {
                        prevEntry = nextEntry;
                        nextEntry = nextEntry.getNextRow();
                    }
                    prevEntry.setNextRow(newEntry);
                    newEntry.setNextRow(nextEntry);
                    rowNodes[row] = newEntry;
                }
                // if the row has elements, but column has no element
            } else if ((rowNodes[row] != null) && (colNodes[col] == null)) {
                //if the element to be added will be the head of the row
                if (col < rowNodes[row].getCol()) {
                    newEntry.setNextColumn(rowNodes[row]);
                    colNodes[col] = newEntry;
                    rowNodes[row] = newEntry;
                } else {
                    MatrixEntry prevEntry = rowNodes[row];
                    MatrixEntry nextEntry = rowNodes[row].getNextColumn();
                    while (nextEntry != null && col > nextEntry.getCol()) {
                        prevEntry = nextEntry;
                        nextEntry = nextEntry.getNextColumn();
                    }
                    prevEntry.setNextColumn(newEntry);
                    newEntry.setNextColumn(nextEntry);
                    colNodes[col] = newEntry;
                }
                // if the row and column both have elements
            } else {

                if (col < rowNodes[row].getCol() && row < colNodes[col].getRow()) {
                    newEntry.setNextColumn(rowNodes[row]);
                    newEntry.setNextRow(colNodes[col]);
                    rowNodes[row] = newEntry;
                    colNodes[col] = newEntry;
                } else if (col < rowNodes[row].getCol() && row >= colNodes[col].getRow()) {
                    MatrixEntry prevEntry = colNodes[col];
                    MatrixEntry nextEntry = colNodes[col].getNextRow();
                    while (nextEntry != null && row > nextEntry.getRow()) {
                        prevEntry = nextEntry;
                        nextEntry = nextEntry.getNextRow();
                    }
                    prevEntry.setNextRow(newEntry);
                    newEntry.setNextRow(nextEntry);
                    rowNodes[row] = newEntry;
                } else if (col >= rowNodes[row].getCol() && row < colNodes[col].getRow()) {
                    MatrixEntry prevEntry = rowNodes[row];
                    MatrixEntry nextEntry = rowNodes[row].getNextColumn();
                    while (nextEntry != null && col > nextEntry.getCol()) {
                        prevEntry = nextEntry;
                        nextEntry = nextEntry.getNextColumn();
                    }
                    prevEntry.setNextColumn(newEntry);
                    newEntry.setNextColumn(nextEntry);
                    colNodes[col] = newEntry;
                } else {
                    MatrixEntry prevEntry1 = rowNodes[row];
                    MatrixEntry nextEntry1 = rowNodes[row].getNextColumn();
                    while (nextEntry1 != null && col > nextEntry1.getCol()) {
                        prevEntry1 = nextEntry1;
                        nextEntry1 = nextEntry1.getNextColumn();
                    }
                    prevEntry1.setNextColumn(newEntry);
                    newEntry.setNextColumn(nextEntry1);
                    MatrixEntry prevEntry = colNodes[col];
                    MatrixEntry nextEntry = colNodes[col].getNextRow();
                    while (nextEntry != null && row > nextEntry.getRow()) {
                        prevEntry = nextEntry;
                        nextEntry = nextEntry.getNextRow();
                    }
                    prevEntry.setNextRow(newEntry);
                    newEntry.setNextRow(nextEntry);
                }
            }
        }
        return result;
    }

    /*
       @return the row number of SparseIntMatrix
    */

    public int getNumRows() {
        return numRows;
    }
    /*
       @return the col number of SparseIntMatrix
    */

    public int getNumCols() {
        return numCols;
    }

    /*
       @param otherMat another SparseIntMatrix
       @return true if the other SparseIntMatrix is added, otherwise return false
    */

    public boolean plus(SparseIntMatrix otherMat) {
        boolean result = true;
        if (otherMat == null || otherMat.getNumCols() != numCols || otherMat.getNumRows() != numRows) {
            result = false;
        } else {
            for (int i = 0; i < getNumRows(); i++) {
                for (int j = 0; j < getNumCols(); j++) {
                    if (otherMat.getElement(i, j) != 0) {
                        setElement(i, j, (getElement(i, j) + otherMat.getElement(i, j)));
                    }

                }
            }
        }
        return result;
    }

    /*
       @param otherMat another SparseIntMatrix
       @return true if the other SparseIntMatrix is added, otherwise return false
    */
    public boolean minus(SparseIntMatrix otherMat) {
        boolean result = true;
        if (otherMat == null || otherMat.getNumCols() != numCols || otherMat.getNumRows() != numRows) {
            result = false;
        } else {
            for (int i = 0; i < getNumRows(); i++) {
                for (int j = 0; j < getNumCols(); j++) {
                    if (otherMat.getElement(i, j) != 0) {
                        setElement(i, j, (getElement(i, j) - otherMat.getElement(i, j)));
                    }

                }
            }
        }
        return result;
    }
}

