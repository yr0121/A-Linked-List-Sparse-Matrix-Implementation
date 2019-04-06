/**
 * Created by yuanhaoruan on 10/28/17.
 * Main method to display the graphs
 */
public class Main {
    public static void main(String[] args) {
        SparseIntMatrix mat1 = new SparseIntMatrix(1000, 1000, "matrix1_data.txt");
        SparseIntMatrix mat2 = new SparseIntMatrix(1000, 1000, "matrix2_data.txt");
        SparseIntMatrix mat3 = new SparseIntMatrix(1000, 1000, "matrix2_noise.txt");
        MatrixViewer.show(mat1);
        MatrixViewer.show(mat2);
        MatrixViewer.show(mat3);
        mat2.minus(mat3);
        MatrixViewer.show(mat2);
    }
}
