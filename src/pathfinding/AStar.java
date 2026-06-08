package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    public enum Heuristic { MANHATTAN, EUCLIDEAN }

    public static PathResult search(Grid grid, Node start, Node end,
                                    Heuristic heuristic, boolean diagonal) {
        int nodesVisited = 0;
        long startTime = System.nanoTime();

        PriorityQueue<Node> open = new PriorityQueue<>();
        start.gCost = 0;
        start.fCost = h(start, end, heuristic);
        open.add(start);

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.visited) continue;
            current.visited = true;
            nodesVisited++;

            if (current == end) break;

            for (Node neighbor : grid.neighbors(current, diagonal)) {
                if (neighbor.visited) continue;
                boolean isDiag = (neighbor.row != current.row && neighbor.col != current.col);
                double edgeCost = isDiag ? Math.sqrt(2) : 1.0;
                double newG = current.gCost + edgeCost;
                if (newG < neighbor.gCost) {
                    neighbor.gCost = newG;
                    neighbor.fCost = newG + h(neighbor, end, heuristic);
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new PathResult(reconstructPath(end), nodesVisited, elapsed);
    }

    private static double h(Node a, Node b, Heuristic heuristic) {
        int dr = Math.abs(a.row - b.row);
        int dc = Math.abs(a.col - b.col);
        if (heuristic == Heuristic.EUCLIDEAN) {
            return Math.sqrt(dr * dr + dc * dc);
        }
        return dr + dc; // Manhattan
    }

    private static List<Node> reconstructPath(Node end) {
        if (end.parent == null && end.gCost == Double.MAX_VALUE) return Collections.emptyList();
        List<Node> path = new ArrayList<>();
        for (Node n = end; n != null; n = n.parent) path.add(n);
        Collections.reverse(path);
        return path;
    }
}
