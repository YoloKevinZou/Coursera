/**
 * Created by KZ on 5/15/17.
 */
public class QuickUnionImpUF {

    private int[] id;
    private int[] size;

    public QuickUnionImpUF(int n){
        id = new int[n];
        for(int i =0; i < n; i++){
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i){
        while (i != id[i]){
            id[i] = id[id[i]]; // makes each node a child of it's grandparent. Flatten the tree
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);

        if (i == j) return;
        if (size[i] < size[j]) {
            id[i] = j;
            size[j] += size[i];
        } else {
            id[j] = i;
            size[i] += size[j];
        }
    }
}
