package homework2;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A directed graph where T is the type of each node and each edge in the graph
 * All edges of the node are unique and all nodes are unique.
 */
public class Graph<T> {

    /**
     * Representation Invariant:
     * nodesList != null
     * no null keys or values in nodesList
     * all children sets are not null and contain no nulls
     */

    /**
     * Abstraction Function:
     * A Graph<T> is a directed graph G = (V, E)
     * where V = nodesList.keySet()
     * and for each v in V, nodesList.get(v) contains the set of children of v
     */

    private HashMap<T, Node<T>> nodesList;

    /**
     * Checks the Representation Invariant
     */
    private void checkRep() {
        assert nodesjList != null;
        for (Map.Entry<T, Node<T>> entry : nodes.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
        }
    }

    /**
     * Constructs an empty graph
     * @effects creates an empty graph with no nodes or edges
     */
    public Graph() {
        nodesList = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the graph. No effect if already exists.
     * @requires node != null
     * @modifies this
     * @throws IllegalArgumentException if there is already node with the give node label
     * @effects adds the node to the graph if it doesn't already exist
     */
    public void addNode(T nodeLabel, Object nodeType)  throws  IllegalArgumentException{
        checkRep();
        assert nodeLabel != null;
        assert nodeType != null;

        // adds the node if it doesn't already exist
        if(this.nodesList.containsKey(nodeLabel)) {
            throw new IllegalArgumentException("The node already exists");
        }
        Node<T> newNode = new Node<>(nodeLabel, nodeType);
        this.nodesList.put(nodeLabel, newNode);
        checkRep();
    }

    /**
     * Adds a directed edge from parent to child.
     * @requires parent and child are non-null and already exist in the graph
     * @modifies this
     * @effects adds a directed edge from parent to child node
     */
    public void addEdge(T parent, T child, T edgeLabel) {
        checkRep();
        assert parent != null;
        assert child != null;
        assert edgeLabel != null;
        /**
         * check if the parent or the child don't exist
         */
        assert nodesList.containsKey(parent);
        assert nodesList.containsKey(child);

        Node<T> parentNode = nodesList.get(parent);
        Node<T> childNode = nodesList.get(child);
        parentNode.addChild(childNode, edgeLabel);
        checkRep();
    }

    /**
     * Returns a sorted list of all nodes in the graph.
     * @requires all nodes are Comparable
     * @modifies nothing
     * @effects returns a new list containing all nodes in sorted order
     */
    public ArrayList<T> listNodes() {
        checkRep();

        ArrayList<T> nodes = new ArrayList<>(nodesList.keySet());
        Collections.sort((ArrayList) nodes);

        return nodes;
    }

    /**
     * Returns a sorted list of parents (nodes pointing to this node).
     * @requires node != null and exists in the graph
     * @modifies nothing
     * @effects returns a list of nodes that have an edge to nodeLabel
     */
    public ArrayList<T> listParents(T node) {
        checkRep();
        assert node != null;
        assert nodesList.containsKey(node);

        ArrayList<T> parents = new ArrayList<>(this.nodesList.get(node).getParents());
        Collections.sort((ArrayList) parents);
        checkRep();
        return parents;
    }

    /**
     * Returns a sorted list of children for the given node.
     * @requires node exists in the graph and all children are Comparable
     * @modifies nothing
     * @effects returns a new list containing all children of the node in sorted order
     */
    public ArrayList<T> listChildren(T node) {
        checkRep();
        assert node != null;
        assert nodesList.containsKey(node);
        ArrayList<T> children = new ArrayList<>(nodesList.get(node).getChildren());
        Collections.sort((ArrayList) children);
        checkRep();
        return children;
    }

    /**
     * Getting a parent via the edge
     * @requires node and edge are not null
     * @modifies nothing
     * @effects returns the parent of node connected by the edge
     */
    public T getParentByEdgeLabel(T node, T edge) {
        checkRep();
        assert node != null;
        assert edge != null;
        assert nodesList.containsKey(node);

        Node<T> child = this.nodesList.get(node);
        checkRep();
        return child.getParentbyEdge(edge);
    }

    /**
     * Getting a child via edge label.
     * @requires node and edge are not null
     * @modifies nothing
     * @effects returns the child of node connected by the edge
     * @throws UnsupportedOperationException
     */
    public T getChildByEdgeLabel(T node, T edge) {
        checkRep();
        assert node != null;
        assert edge != null;
        assert nodesList.containsKey(node);

        Node<T> parent = this.nodesList.get(node);
        checkRep();
        return parent.getChildbyEdge(edge);
    }
    /**
     * Checks if a node exists in the graph
     * @requires node != null
     * @modifies nothing
     * @effects returns true if node exists in the graph
     */
    public boolean containsNode(T node) {
        checkRep();
        assert node != null;
        return nodesList.containsKey(node);
    }

    /**
     * Returns the number of nodes in the graph
     * @modifies nothing
     * @effects returns the number of nodes in this graph
     */
    public int size() {
        checkRep();
        return nodesList.size();
    }


}