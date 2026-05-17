package pathfinding;

import java.util.List;

public class PathResult {
    public final List<Node> path;       // ordered list from start to end, empty if no path
    public final int nodesVisited;      // nodes dequeued/explored
    public final long timeNanos;        // wall-clock time of the search

    public PathResult(List<Node> path, int nodesVisited, long timeNanos) {
        this.path = path;
        this.nodesVisited = nodesVisited;
        this.timeNanos = timeNanos;
    }

    public boolean foundPath() {
        return !path.isEmpty();
    }

    public double timeMillis() {
        return timeNanos / 1_000_000.0;
    }
}
