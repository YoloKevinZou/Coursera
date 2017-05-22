import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by KZ on 5/15/17.
 */
public class Main {

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUF quickFindUf = new QuickFindUF(n);
        while(!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!quickFindUf.connected(p,q)){
                quickFindUf.union(p,q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
