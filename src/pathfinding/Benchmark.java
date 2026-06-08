package pathfinding;

public class Benchmark {

    private static final int[] SIZES   = {10, 20, 30, 50, 75, 100, 150, 200};
    private static final double[] DENSITIES = {0.0, 0.15, 0.30};
    private static final int TRIALS = 10;

    public static void run() {


        System.out.println("=== 4-DIR | MANHATTAN | RANDOM WALLS: Nodes Visited ===");
        System.out.println("Size,Density,Dijkstra,AStar_Manhattan");
        for (double density : DENSITIES) {
            for (int size : SIZES) {
                long dNodes = 0, aNodes = 0;
                int valid = 0;
                for (int seed = 0; seed < TRIALS; seed++) {
                    Grid g = makeRandom(size, density, seed);
                    Node s = g.get(0,0), e = g.get(size-1,size-1);
                    g.reset(); PathResult dr = Dijkstra.search(g, s, e, false);
                    g.reset(); PathResult ar = AStar.search(g, s, e, AStar.Heuristic.MANHATTAN, false);
                    if (dr.foundPath() && ar.foundPath()) { dNodes+=dr.nodesVisited; aNodes+=ar.nodesVisited; valid++; }
                }
                if (valid > 0) System.out.printf("%d,%.2f,%d,%d%n", size, density, dNodes/valid, aNodes/valid);
            }
        }


        System.out.println("\n=== 4-DIR | EUCLIDEAN | RANDOM WALLS: Nodes Visited ===");
        System.out.println("Size,Density,Dijkstra,AStar_Euclidean");
        for (double density : DENSITIES) {
            for (int size : SIZES) {
                long dNodes = 0, aNodes = 0;
                int valid = 0;
                for (int seed = 0; seed < TRIALS; seed++) {
                    Grid g = makeRandom(size, density, seed);
                    Node s = g.get(0,0), e = g.get(size-1,size-1);
                    g.reset(); PathResult dr = Dijkstra.search(g, s, e, false);
                    g.reset(); PathResult ar = AStar.search(g, s, e, AStar.Heuristic.EUCLIDEAN, false);
                    if (dr.foundPath() && ar.foundPath()) { dNodes+=dr.nodesVisited; aNodes+=ar.nodesVisited; valid++; }
                }
                if (valid > 0) System.out.printf("%d,%.2f,%d,%d%n", size, density, dNodes/valid, aNodes/valid);
            }
        }


        System.out.println("\n=== 8-DIR DIAGONAL | 0% WALLS: Nodes Visited ===");
        System.out.println("Size,Dijkstra,AStar_Manhattan,AStar_Euclidean");
        for (int size : SIZES) {
            long dNodes = 0, amNodes = 0, aeNodes = 0;
            int valid = 0;
            for (int seed = 0; seed < TRIALS; seed++) {
                Grid g = makeRandom(size, 0.0, seed);
                Node s = g.get(0,0), e = g.get(size-1,size-1);
                g.reset(); PathResult dr  = Dijkstra.search(g, s, e, true);
                g.reset(); PathResult amr = AStar.search(g, s, e, AStar.Heuristic.MANHATTAN, true);
                g.reset(); PathResult aer = AStar.search(g, s, e, AStar.Heuristic.EUCLIDEAN, true);
                if (dr.foundPath() && amr.foundPath() && aer.foundPath()) {
                    dNodes+=dr.nodesVisited; amNodes+=amr.nodesVisited; aeNodes+=aer.nodesVisited; valid++;
                }
            }
            if (valid > 0) System.out.printf("%d,%d,%d,%d%n", size, dNodes/valid, amNodes/valid, aeNodes/valid);
        }


        System.out.println("\n=== MAZE GRIDS: Nodes Visited ===");
        System.out.println("Size,Dijkstra,AStar_Manhattan,AStar_Euclidean");
        int[] mazeSizes = {11, 21, 31, 51, 75, 101};
        for (int size : mazeSizes) {
            long dNodes = 0, amNodes = 0, aeNodes = 0;
            int valid = 0;
            for (int seed = 0; seed < TRIALS; seed++) {
                Grid g = new Grid(size, size);
                Node s = g.get(0,0), e = g.get(size-1,size-1);
                g.generateMaze(s, e, seed);
                g.reset(); PathResult dr  = Dijkstra.search(g, s, e, false);
                g.reset(); PathResult amr = AStar.search(g, s, e, AStar.Heuristic.MANHATTAN, false);
                g.reset(); PathResult aer = AStar.search(g, s, e, AStar.Heuristic.EUCLIDEAN, false);
                if (dr.foundPath() && amr.foundPath() && aer.foundPath()) {
                    dNodes+=dr.nodesVisited; amNodes+=amr.nodesVisited; aeNodes+=aer.nodesVisited; valid++;
                }
            }
            if (valid > 0) System.out.printf("%d,%d,%d,%d%n", size, dNodes/valid, amNodes/valid, aeNodes/valid);
        }


        System.out.println("\n=== EXECUTION TIME (ms) | 4-DIR | OPEN GRID ===");
        System.out.println("Size,Dijkstra,AStar_Manhattan,AStar_Euclidean");
        for (int size : SIZES) {
            long dT = 0, amT = 0, aeT = 0;
            int valid = 0;
            for (int seed = 0; seed < TRIALS; seed++) {
                Grid g = makeRandom(size, 0.0, seed);
                Node s = g.get(0,0), e = g.get(size-1,size-1);
                g.reset(); PathResult dr  = Dijkstra.search(g, s, e, false);
                g.reset(); PathResult amr = AStar.search(g, s, e, AStar.Heuristic.MANHATTAN, false);
                g.reset(); PathResult aer = AStar.search(g, s, e, AStar.Heuristic.EUCLIDEAN, false);
                if (dr.foundPath() && amr.foundPath() && aer.foundPath()) {
                    dT+=dr.timeNanos; amT+=amr.timeNanos; aeT+=aer.timeNanos; valid++;
                }
            }
            if (valid > 0) System.out.printf("%d,%.4f,%.4f,%.4f%n", size,
                    (dT/valid)/1e6, (amT/valid)/1e6, (aeT/valid)/1e6);
        }
    }

    private static Grid makeRandom(int size, double density, int seed) {
        Grid g = new Grid(size, size);
        Node s = g.get(0,0), e = g.get(size-1,size-1);
        g.randomizeWalls(density, s, e, seed);
        return g;
    }
}
