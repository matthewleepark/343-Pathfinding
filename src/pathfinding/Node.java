package pathfinding;

public class Node implements Comparable<Node> {
    public final int row, col;
    public boolean isWall;

    // Pathfinding state (reset between runs)
    public double gCost;   // cost from start
    public double fCost;   // gCost + heuristic (A* only)
    public Node parent;
    public boolean visited;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.isWall = false;
        reset();
    }

    public void reset() {
        gCost = Double.MAX_VALUE;
        fCost = Double.MAX_VALUE;
        parent = null;
        visited = false;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost, other.fCost);
    }
}
