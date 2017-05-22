import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by KZ on 5/17/17.
 */
public class PercolationStats {

//    private Percolation percolation;
    private double[] x;
    private int t;

    public PercolationStats(int n, int trials){

        if(n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Illegal Argument Exceptions");
        }

        x = new double[trials];
        t = trials;
        for(int i = 0; i < trials; i++){
            runSystem(n, i);
        }
    }

    private void runSystem(int n, int t) {
        Percolation percolation = new Percolation(n);

        while(!percolation.percolates()) {
            openRandomSite(n, percolation);
        }

        x[t] = percolation.numberOfOpenSites()/((double)n*n);
    }

    private void openRandomSite(int n, Percolation percolation) {
        int randomRow = StdRandom.uniform(1,n+1);
        int randomCol = StdRandom.uniform(1, n+1);

        while(percolation.isOpen(randomRow, randomCol)){
            randomRow = StdRandom.uniform(1,n+1);
            randomCol = StdRandom.uniform(1, n+1);
        }

        percolation.open(randomRow, randomCol);
    }

    public double mean() {
        double sum = 0.0;

        for(int i = 0; i < t; i++){
            sum += x[i];
        }

        return sum/t;
    }

    public double stddev() {
        double sampleMean = mean();
        double totalSum = 0;

        for(int i = 0; i < t; i++){
            double value = x[i] - sampleMean;
            value *= value;
            totalSum += value;
        }

        double result = totalSum / (t-1);
        return Math.sqrt(result);
    }

    public double confidenceLo() {
        double sampleMean = mean();
        double stdDev = stddev();
        double result = sampleMean - ((1.96*stdDev)/Math.sqrt(t));

        return result;
    }

    public double confidenceHi()  {
        double sampleMean = mean();
        double stdDev = stddev();
        double result = sampleMean + ((1.96*stdDev)/Math.sqrt(t));

        return result;
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);
        System.out.printf("mean                     = %f\n",percolationStats.mean());
        System.out.printf("stddev                   = %f\n",percolationStats.stddev());
        System.out.printf("95%% confidence interval  = [%f,%f]",percolationStats.confidenceLo(), percolationStats.confidenceHi());

    }
}
