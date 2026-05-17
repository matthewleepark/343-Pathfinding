package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    /**
     * Run Dijkstra on the grid from start to end.
     * The grid must be reset before calling this.
     */
    public static PathResult search(Grid grid, Node start, Node end) {
        int nodesVisited = 0;
        long startTime = System.nanoTime();

        // fCost is unused by Dijkstra; we use gCost as the priority key
        PriorityQueue<Node> open = new PriorityQueue<>((a, b) -> Double.compare(a.gCost, b.gCost));
        start.gCost = 0;
        open.add(start);

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.visited) continue;
            current.visited = true;
            nodesVisited++;

            if (current == end) break;

            for (Node neighbor : grid.neighbors(current)) {
                if (neighbor.visited) continue;
                double newG = current.gCost + 1; // uniform edge weight of 1
                if (newG < neighbor.gCost) {
                    neighbor.gCost = newG;
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new PathResult(reconstructPath(end), nodesVisited, elapsed);
    }

    private static List<Node> reconstructPath(Node end) {
        if (end.parent == null && end.gCost == Double.MAX_VALUE) return Collections.emptyList();
        List<Node> path = new ArrayList<>();
        for (Node n = end; n != null; n = n.parent) path.add(n);
        Collections.reverse(path);
        return path;
    }
}
