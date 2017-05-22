import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by KZ on 5/17/17.
 */
public class Percolation {

    private WeightedQuickUnionUF weightedSites;
    private int[][] sites;
    private int openSites = 0;
    private int weightedSize;
    private int rowSize;
    private int colSize;

    public Percolation(int n){

        if(n <= 0){
            throw new IllegalArgumentException("Illegal Argument Exceptions");
        }

        weightedSites = new WeightedQuickUnionUF((n*n)+2);
        weightedSize = n*n+2;
        sites = new int[n+2][n];
        rowSize = n+2;
        colSize = n;

        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                sites[i][j] = 0;
            }
        }

        for(int i = 0; i < colSize; i++){
            sites[0][i] = 1;
            sites[rowSize-1][i] = 1;
        }
    }

    public void open(int row, int col) {

        // TODO-me validate indice of the site that it receives
        validIndices(row, col);

        // TODO mark the site as open
        if (!isOpen(row,col)){
            openSites += 1;
        }
        sites[row][col-1] = 1;

        // TODO perform some sequence of WeightedQuickUnionUF operations that links the site in question to its open neighbors.
        int currentIndex = xyTo1D(row, col);

        if (row == 1) {
            weightedSites.union(0, currentIndex);
        }

        if (row == rowSize-2) {
            weightedSites.union(weightedSize-1, currentIndex);
        }

        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow > 0 && newRow < rowSize - 1 && newCol > 0 && newCol <= colSize) {
                if (isOpen(newRow, newCol)) {
                    int newIndex = xyTo1D(newRow, newCol);
                    weightedSites.union(currentIndex, newIndex);
                }
            }
        }

    }

    public boolean isOpen(int row, int col){
        validIndices(row, col);
        return sites[row][col-1] == 1;
    }

    public boolean isFull(int row, int col){
        validIndices(row, col);
        int index = xyTo1D(row, col);
        return weightedSites.connected(0, index);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return weightedSites.connected(0, weightedSize-1);
    }

    private int xyTo1D(int row, int col) {
        return  (row*colSize) + col - colSize;
    }

    private void validIndices(int row, int col) {
        if (row <= 0 || row >= rowSize-1) {
            throw new IndexOutOfBoundsException("Row index: " + row + " Col Index: " + col + " out of bounds");
        }
    }

    public static void main(String[] args) {

        int n = 10;

        Percolation percolation = new Percolation(n);

        percolation.isFull(0,6);
        percolation.isFull(6,0);
    }
}
