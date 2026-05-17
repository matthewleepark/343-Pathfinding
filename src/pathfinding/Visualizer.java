package pathfinding;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Prints a text-based representation of the grid after a search.
 *
 * Legend:
 *   S  = start
 *   E  = end
 *   #  = wall
 *   *  = shortest path
 *   .  = explored node
 *      = unvisited
 */
public class Visualizer {

    public static void print(Grid grid, Node start, Node end, PathResult result) {
        Set<Node> pathSet = new HashSet<>(result.path);

        for (int r = 0; r < grid.rows; r++) {
            for (int c = 0; c < grid.cols; c++) {
                Node n = grid.get(r, c);
                if (n == start)          System.out.print("S ");
                else if (n == end)       System.out.print("E ");
                else if (n.isWall)       System.out.print("# ");
                else if (pathSet.contains(n)) System.out.print("* ");
                else if (n.visited)      System.out.print(". ");
                else                     System.out.print("  ");
            }
            System.out.println();
        }

        System.out.println();
        if (result.foundPath()) {
            System.out.println("Path length : " + result.path.size());
        } else {
            System.out.println("No path found.");
        }
        System.out.println("Nodes visited: " + result.nodesVisited);
        System.out.printf("Time: %.4f ms%n", result.timeMillis());
    }
}
