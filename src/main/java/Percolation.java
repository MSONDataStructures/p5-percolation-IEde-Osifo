import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Name:    Kevin Wayne
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *  Description:  Modeling Percolation.
 ******************************************************************************/
public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] openSites;  // Array to track open/blocked sites
    private int n;  // Size of the grid (n-by-n)
    private int openCount;  // Keeps track of the number of open sites

    // Virtual top and bottom site indices
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");

        this.n = n;
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        grid = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n];
        openCount = 0;

        for (int col = 0; col < n; col++) {
            grid.union(virtualTop, index(0, col));
            grid.union(virtualBottom, index(n - 1, col));
        }
    }

    private int index(int row, int col) {
        // TODO: create n-by-n grid, with all sites blocked
        return row * n + col;
    }

    public void open(int row, int col) {
        // TODO: open site (row, col) if it is not open already
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col must be between 1 and " + n);
        }

        int site = index(row - 1, col - 1);
        if (!openSites[site]) {
            openSites[site] = true;
            openCount++;

            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(site, index(row - 2, col - 1));
            }
            if (row < n && isOpen(row + 1, col)) {
                grid.union(site, index(row, col - 1));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(site, index(row - 1, col - 2));
            }
            if (col < n && isOpen(row, col + 1)) {
                grid.union(site, index(row - 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: is site (row, col) open?
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col must be between 1 and " + n);
        }
        return openSites[index(row - 1, col - 1)];
    }

    public boolean isFull(int row, int col) {
        // TODO: is site (row, col) full?
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col must be between 1 and " + n);
        }
        return grid.connected(virtualTop, index(row - 1, col - 1));
    }

    public int numberOfOpenSites() {
        // TODO: number of open sites
        return openCount;
    }

    public boolean percolates() {
        // TODO: does the system percolate?
        return grid.connected(virtualTop, virtualBottom);
    }
}
