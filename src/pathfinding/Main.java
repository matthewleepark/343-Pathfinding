package pathfinding;

public class Main {

    public static void main(String[] args) {
        System.out.println("========== DEMO: 4-dir, Manhattan (20x20, 25% walls) ==========");
        demo(20, false, AStar.Heuristic.MANHATTAN, false, 0.25);

        System.out.println("\n========== DEMO: 8-dir diagonal, Euclidean (20x20, 25% walls) ==========");
        demo(20, true, AStar.Heuristic.EUCLIDEAN, false, 0.25);

        System.out.println("\n========== DEMO: Maze (21x21) ==========");
        demo(21, false, AStar.Heuristic.MANHATTAN, true, 0.0);

        System.out.println("\n========== FULL BENCHMARK ==========");
        Benchmark.run();
    }

    private static void demo(int size, boolean diagonal, AStar.Heuristic heuristic,
                              boolean maze, double density) {
        Grid grid = new Grid(size, size);
        Node start = grid.get(0, 0);
        Node end   = grid.get(size - 1, size - 1);

        if (maze) grid.generateMaze(start, end, 42);
        else      grid.randomizeWalls(density, start, end, 42);

        System.out.println("--- Dijkstra ---");
        grid.reset();
        PathResult dr = Dijkstra.search(grid, start, end, diagonal);
        Visualizer.print(grid, start, end, dr);

        System.out.println("--- A* (" + heuristic + ") ---");
        grid.reset();
        PathResult ar = AStar.search(grid, start, end, heuristic, diagonal);
        Visualizer.print(grid, start, end, ar);
    }
}
