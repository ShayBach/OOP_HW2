package homework2;

import java.util.*;

/**
 * A directed graph where T is the type of each node
 * Every edge is going from parent to child
 * The class contains functions that adds nodes and edges to the graph
 */
public class Graph<T> {

    /**
     * Representation Invariant:
     * name != null
     * no null keys or values in nodesList
     * all children sets are not null and contain no nulls
     */

    /**
     * Abstraction Function:
     * A Graph<T> is a directed graph G = (V, E)
     * where V = nodesList.keySet()
     * An edge is the connection of the child being in parents map
     */

    //private final String name;
    private final HashMap<T, Set<T>> nodesList;

    /**
     * Checks the Representation Invariant
     */
    private void checkRep() {
        //assert this.name != null;
        assert nodesList != null : "nodesList cannot be null";
        for (T node : nodesList.keySet()) {
            assert node != null : "nodes cannot be null";
            assert nodesList.get(node) != null : "nodes sets cannot be null";

            for (T child : nodesList.get(node)) {
                assert child != null : "child nodes cannot be null";
            }
        }
    }

    public Graph() {
        nodesList = new HashMap<>();
        //this.name = name;
        checkRep();
    }

    /*public String getName() {
        checkRep();
        return this.name;
    }*/

    /**
     * Adds a node to the graph.
     * @requires node != null and the node doesn't exist yet in the graph
     * @modifies this
     * @effects adds the node to the graph if it doesn't already exist
     * @throws IllegalArgumentException when node already exists
     */
    public void addNode(T node) {
        checkRep();
        assert node != null;
        // adds the node if it doesn't already exist
        if(this.nodesList.containsKey(node)) {
            throw new IllegalArgumentException("The node already exists");
        }
        this.nodesList.put(node, new HashSet<>());
        checkRep();
    }

    /**
     * Adds a directed edge from parent to child.
     * @requires parent and child are non-null and already exist in the graph
     * @modifies this
     * @effects adds a directed edge from parent to child node
     */
    public boolean addEdge(T parent, T child) {
        checkRep();
        if (parent == null || child == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }
        if (!nodesList.containsKey(parent) || !nodesList.containsKey(child)) {
            throw new IllegalArgumentException("Both nodes must be in the graph");
        }
        boolean added = nodesList.get(parent).add(child);
        checkRep();
        return added;
    }

    /**
     * Returns a sorted list to all nodes in the graph.
     */
    public ArrayList<T> getListNodes() {
        checkRep();

        Set<T> nodeSet = this.nodesList.keySet();
        ArrayList<T> sortedNodes = new ArrayList<>(nodeSet);
        Collections.sort(sortedNodes);

        return sortedNodes;
    }


    /**
     * Returns a sorted list of the children for the given node.
     * @requires node exists in the graph and parent != null
     */
    public ArrayList<T> getListChildren(T node) {
        checkRep();
        assert node != null;
        Set<T> nodeSet = this.nodesList.get(node);
        ArrayList<T> sortedNodes = new ArrayList<>(nodeSet);
        Collections.sort(sortedNodes);
        return sortedNodes;
    }

    /**
     * Returns whether this graph contains the given node.
     * @param node the node to check
     * @return true if this graph contains node, false otherwise
     */
    public boolean containsNode(T node) {
        checkRep();
        return nodesList.containsKey(node);
    }

    /**
     * Returns whether this graph contains an edge from parent to child.
     * @param parent the parent node
     * @param child the child node
     * @requires parent != null && child != null
     * @return true if there is an edge from parent to child, false otherwise
     */
    public boolean containsEdge(T parent, T child) {
        checkRep();
        if (parent == null || child == null) {
            return false;
        }
        if (!nodesList.containsKey(parent)) {
            return false;
        }
        return nodesList.get(parent).contains(child);
    }

    /**
     * Returns the number of nodes in this graph.
     * @return the number of nodes in this graph
     */
    public int size() {
        checkRep();
        return nodesList.size();
    }

    /**
     * Returns whether this graph is empty.
     * @return true if this graph contains no nodes, false otherwise
     */
    public boolean isEmpty() {
        checkRep();
        return nodesList.isEmpty();
    }
}