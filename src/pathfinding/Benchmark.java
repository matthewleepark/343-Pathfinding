package pathfinding;

public class Benchmark {

    // Grid sizes to test (N x N)
    private static final int[] SIZES = {10, 20, 30, 50, 75, 100, 150, 200};

    // Wall densities to test
    private static final double[] DENSITIES = {0.0, 0.15, 0.30};

    // Number of random seeds averaged per configuration
    private static final int TRIALS = 10;

    public static void run() {
        System.out.println("=== BENCHMARK: Nodes Visited ===");
        System.out.println("Size,Density,Dijkstra_Nodes,AStar_Nodes");

        for (double density : DENSITIES) {
            for (int size : SIZES) {
                long dijkstraNodes = 0, astarNodes = 0;
                int validTrials = 0;

                for (int seed = 0; seed < TRIALS; seed++) {
                    Grid grid = new Grid(size, size);
                    Node start = grid.get(0, 0);
                    Node end   = grid.get(size - 1, size - 1);
                    grid.randomizeWalls(density, start, end, seed);

                    grid.reset();
                    PathResult dResult = Dijkstra.search(grid, start, end);

                    grid.reset();
                    PathResult aResult = AStar.search(grid, start, end);

                    // Only count trials where a path exists
                    if (dResult.foundPath() && aResult.foundPath()) {
                        dijkstraNodes += dResult.nodesVisited;
                        astarNodes    += aResult.nodesVisited;
                        validTrials++;
                    }
                }

                if (validTrials > 0) {
                    System.out.printf("%d,%.2f,%d,%d%n",
                            size, density,
                            dijkstraNodes / validTrials,
                            astarNodes    / validTrials);
                }
            }
        }

        System.out.println();
        System.out.println("=== BENCHMARK: Execution Time (ms) ===");
        System.out.println("Size,Density,Dijkstra_ms,AStar_ms");

        for (double density : DENSITIES) {
            for (int size : SIZES) {
                long dijkstraTime = 0, astarTime = 0;
                int validTrials = 0;

                for (int seed = 0; seed < TRIALS; seed++) {
                    Grid grid = new Grid(size, size);
                    Node start = grid.get(0, 0);
                    Node end   = grid.get(size - 1, size - 1);
                    grid.randomizeWalls(density, start, end, seed);

                    grid.reset();
                    PathResult dResult = Dijkstra.search(grid, start, end);

                    grid.reset();
                    PathResult aResult = AStar.search(grid, start, end);

                    if (dResult.foundPath() && aResult.foundPath()) {
                        dijkstraTime += dResult.timeNanos;
                        astarTime    += aResult.timeNanos;
                        validTrials++;
                    }
                }

                if (validTrials > 0) {
                    System.out.printf("%d,%.2f,%.4f,%.4f%n",
                            size, density,
                            (dijkstraTime / validTrials) / 1_000_000.0,
                            (astarTime    / validTrials) / 1_000_000.0);
                }
            }
        }
    }
}
