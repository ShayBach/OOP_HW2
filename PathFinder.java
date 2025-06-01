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
     * @param startPath the initial path (containing one start node)
     * @param goalNode the goal nodes
     * @requires startPath != null &&  != null &&
     * @return the shortest path from  start node to  goal node,
     *         or null if no path exists
     */
    public <P extends Path<T, P>> P findShortestPath(P startPath, T goalNode) {
        checkRep();

        if (startPath == null || goalNode == null) {
            throw new IllegalArgumentException("Start paths and goal nodes cannot be null");
        }
        //check for self loop (n1 -> n1)
        boolean selfLoop = startPath.getEnd().equals(goalNode);
        boolean noEdgeSelfLoopFirstIter = false;
        if (selfLoop) {
            if (graph.containsEdge( startPath.getEnd(), goalNode)) {
                return startPath;
           }
            else {
                noEdgeSelfLoopFirstIter = true;
            }
        }
        // maps nodes -> paths (shortest path to reach each node)
        Map<T, P> paths = new HashMap<>();

        // Initialize paths map with start nodes
        T startNode = startPath.getEnd();
        //if (!paths.containsKey(startNode) || startPath.getCost() < paths.get(startNode).getCost()) {
        paths.put(startNode, startPath);
        //}

        // The priority queue contains paths with priority equal to their cost
        // (lower cost = higher priority)
        Set<P> startPaths = new HashSet<>();
        startPaths.add(startPath);
        PriorityQueue<P> active = new PriorityQueue<>(startPaths);

        // The set of finished nodes are those for which we know the shortest paths
        // from starts and whose children we have already examined
        Set<T> finished = new HashSet<>();

        while (!active.isEmpty()) {
            // queueMin is the path with the lowest cost
            P queueMinPath = active.poll();
            T queueMin = queueMinPath.getEnd();

            // Check if we've reached a goal
            if (goalNode.equals(queueMin)) {
                //To avoid returning misguided self loop
                // when there is no edge from the node to itself
                if (noEdgeSelfLoopFirstIter) {
                    noEdgeSelfLoopFirstIter = false;
                }
                else {
                    checkRep();
                    return queueMinPath;
                }
            }

            // Skip if we've already processed this node with a better path
            if (finished.contains(queueMin)) {
                continue;
            }
            // Examine all children of the current node
            if (graph.containsNode(queueMin)) {
                for (T child : graph.getListChildren(queueMin)) {
                    //in case of self loop we wish to add the new path
                    // with the first node as a child
                    if (!finished.contains(child) || selfLoop) {
                        // Create new path by extending current path with child
                        P childPath = queueMinPath.extend(child);

                        // Only add if this is the first path to this node,
                        // or if this path is better than the existing one
                        if (!paths.containsKey(child) ||
                                childPath.getCost() < paths.get(child).getCost() || selfLoop) {
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

}