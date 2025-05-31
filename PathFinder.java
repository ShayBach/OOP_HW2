package homework2;

import java.util.*;

/**
 * PathFinder provides functionality to find the shortest path in a node-weighted graph.
 * The class implements a variation of Dijkstra's algorithm for node-weighted graphs.
 *
 * @param <T> the type of nodes in the graph
 */
public class PathFinder<T extends Comparable<? super T>> {

    /**
     * Representation Invariant:
     * - graph != null
     *
     * Abstraction Function:
     * PathFinder represents a utility class for finding shortest paths
     * in node-weighted directed graphs using a greedy algorithm.
     */

    private final Graph<T> graph;

    /**
     * Checks the representation invariant.
     */
    private void checkRep() {
        assert graph != null : "graph cannot be null";
    }

    /**
     * Creates a new PathFinder for the given graph.
     * @param graph the graph to find paths in
     * @requires graph != null
     * @effects creates a new PathFinder for the given graph
     */
    public PathFinder(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
        checkRep();
    }

    /**
     * Finds the shortest path from any start node to any goal node.
     * @param startPaths a set of initial paths (each containing one start node)
     * @param goalNodes a set of goal nodes
     * @requires startPaths != null && goalNodes != null &&
     *          all paths in startPaths are non-null and contain exactly one node &&
     *          all nodes in goalNodes are non-null
     * @return the shortest path from any start node to any goal node,
     *         or null if no path exists
     */
    public <P extends Path<T, P>> P findShortestPath(Set<P> startPaths, Set<T> goalNodes) {
        checkRep();

        if (startPaths == null || goalNodes == null) {
            throw new IllegalArgumentException("Start paths and goal nodes cannot be null");
        }

        if (startPaths.isEmpty() || goalNodes.isEmpty()) {
            return null;
        }

        // maps nodes -> paths (shortest path to reach each node)
        Map<T, P> paths = new HashMap<>();

        // Initialize paths map with start nodes
        for (P startPath : startPaths) {
            T startNode = startPath.getEnd();
            if (!paths.containsKey(startNode) || startPath.getCost() < paths.get(startNode).getCost()) {
                paths.put(startNode, startPath);
            }
        }

        // The priority queue contains paths with priority equal to their cost
        // (lower cost = higher priority)
        PriorityQueue<P> active = new PriorityQueue<>(startPaths);

        // The set of finished nodes are those for which we know the shortest paths
        // from starts and whose children we have already examined
        Set<T> finished = new HashSet<>();

        while (!active.isEmpty()) {
            // queueMin is the path with the lowest cost
            P queueMinPath = active.poll();
            T queueMin = queueMinPath.getEnd();

            // Skip if we've already processed this node with a better path
            if (finished.contains(queueMin)) {
                continue;
            }

            // Check if we've reached a goal
            if (goalNodes.contains(queueMin)) {
                checkRep();
                return queueMinPath;
            }

            // Examine all children of the current node
            if (graph.containsNode(queueMin)) {
                for (T child : graph.getListChildren(queueMin)) {
                    if (!finished.contains(child)) {
                        // Create new path by extending current path with child
                        P childPath = queueMinPath.extend(child);

                        // Only add if this is the first path to this node,
                        // or if this path is better than the existing one
                        if (!paths.containsKey(child) ||
                                childPath.getCost() < paths.get(child).getCost()) {
                            paths.put(child, childPath);
                            active.add(childPath);
                        }
                    }
                }
            }

            // Mark current node as finished
            finished.add(queueMin);
        }

        // No path found
        checkRep();
        return null;
    }

    /**
     * Convenience method to find shortest path between single start and goal nodes.
     * @param startPath initial path containing the start node
     * @param goalNode the goal node
     * @requires startPath != null && goalNode != null
     * @return the shortest path from start to goal, or null if no path exists
     */
    public <P extends Path<T, P>> P findShortestPath(P startPath, T goalNode) {
        checkRep();

        if (startPath == null || goalNode == null) {
            throw new IllegalArgumentException("Start path and goal node cannot be null");
        }

        Set<P> startPaths = new HashSet<>();
        startPaths.add(startPath);

        Set<T> goalNodes = new HashSet<>();
        goalNodes.add(goalNode);

        return findShortestPath(startPaths, goalNodes);
    }
}