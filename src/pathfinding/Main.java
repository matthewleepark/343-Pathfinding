package pathfinding;

public class Main {

    public static void main(String[] args) {
        // --- Demo: small grid with walls ---
        System.out.println("========== DEMO (20x20, 25% walls) ==========");
        int size = 20;
        Grid grid = new Grid(size, size);
        Node start = grid.get(0, 0);
        Node end   = grid.get(size - 1, size - 1);
        grid.randomizeWalls(0.25, start, end, 42);

        System.out.println("--- Dijkstra ---");
        grid.reset();
        PathResult dijkstraResult = Dijkstra.search(grid, start, end);
        Visualizer.print(grid, start, end, dijkstraResult);

        System.out.println("--- A* ---");
        grid.reset();
        PathResult astarResult = AStar.search(grid, start, end);
        Visualizer.print(grid, start, end, astarResult);

        // --- Full benchmark ---
        System.out.println("\n========== BENCHMARK ==========");
        Benchmark.run();
    }
}
