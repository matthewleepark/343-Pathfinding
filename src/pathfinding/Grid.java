package pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    public final int rows, cols;
    private final Node[][] nodes;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        nodes = new Node[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c] = new Node(r, c);
    }

    public Node get(int row, int col) {
        return nodes[row][col];
    }

    /** Returns 4-directional neighbors that are not walls. */
    public List<Node> neighbors(Node n) {
        List<Node> result = new ArrayList<>();
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int r = n.row + d[0], c = n.col + d[1];
            if (r >= 0 && r < rows && c >= 0 && c < cols && !nodes[r][c].isWall)
                result.add(nodes[r][c]);
        }
        return result;
    }

    /** Reset all node pathfinding state without changing walls. */
    public void reset() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c].reset();
    }

    /**
     * Randomly place walls with the given density (0.0–1.0).
     * Start and end cells are never walls.
     */
    public void randomizeWalls(double density, Node start, Node end, long seed) {
        Random rng = new Random(seed);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Node n = nodes[r][c];
                if (n == start || n == end) continue;
                n.isWall = rng.nextDouble() < density;
            }
        }
    }

    /** Clear all walls. */
    public void clearWalls() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c].isWall = false;
    }
}
