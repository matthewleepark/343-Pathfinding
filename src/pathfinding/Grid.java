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

    public List<Node> neighbors(Node n, boolean diagonal) {
        List<Node> result = new ArrayList<>();
        int[][] dirs = diagonal
            ? new int[][]{{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}}
            : new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int r = n.row + d[0], c = n.col + d[1];
            if (r >= 0 && r < rows && c >= 0 && c < cols && !nodes[r][c].isWall)
                result.add(nodes[r][c]);
        }
        return result;
    }

    public void reset() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c].reset();
    }

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

    public void generateMaze(Node start, Node end, long seed) {
        Random rng = new Random(seed);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c].isWall = true;

        carve(start.row, start.col, rng);
        nodes[end.row][end.col].isWall = false;
    }

    private void carve(int r, int c, Random rng) {
        nodes[r][c].isWall = false;
        int[][] dirs = {{0,2},{0,-2},{2,0},{-2,0}};
        shuffleDirs(dirs, rng);
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && nodes[nr][nc].isWall) {
                nodes[r + d[0]/2][c + d[1]/2].isWall = false;
                carve(nr, nc, rng);
            }
        }
    }

    private void shuffleDirs(int[][] dirs, Random rng) {
        for (int i = dirs.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int[] tmp = dirs[i]; dirs[i] = dirs[j]; dirs[j] = tmp;
        }
    }

    public void clearWalls() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                nodes[r][c].isWall = false;
    }
}
