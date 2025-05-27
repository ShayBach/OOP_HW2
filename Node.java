package homework2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an abstraction of a node in a directed and labeled graph.
 * Each node has a unique label and data.
 * The class supports edge-labeled graph navigation.
 */

public class Node<T>{

    private T nodeLabel;
    private Object nodeType;

    private ArrayList<T> children;
    private ArrayList<T> parents;
    private HashMap<T, Edge<T>> edgesOut;
    private HashMap<T, Edge<T>> edgesIn;

    /**
     * Representation Invariant:
     * nodeLabel != null && nodeType != null
     * edgesOut != null && edgesIn != null
     * parents != null && children != null
     * edgesOut.size() == children.size() && edgesIn.size() == parents.size()
     * The edges must be consistent to the parent and children
     */

    /**
     * Abstraction Function:
     * Represents a node in directed and labeled graph
     * NodeLabel its the nodes label and nodeType it is its type
     * edgesOut are the edges that go from the node
     * edgesIn are the edges that go to the node
     * parents and children are lists of the labels of the relevant nodes
     * The edges must be consistent to the parent and children
     */

    private void checkRep(){
        assert this.nodeLabel != null;
        assert this.nodeType != null;
        assert this.edgesOut != null;
        assert this.edgesIn != null;
        assert this.parents != null;
        assert this.children != null;
        assert this.edgesOut.size() == this.children.size();
        assert this.edgesIn.size() == this.parents.size();
        assert isConsistent();
    }

    /**
     * check edges consistency
     */
    private boolean isConsistent(){
        for (Edge<T> edge : this.edgesIn.values()) {
            if (!this.parents.contains(edge.getParent())){
                return false;
            }
        }
        for (Edge<T> edge : this.edgesOut.values()) {
            if (!this.children.contains(edge.getChild())){
                return false;
            }
        }

        return true;
    }

    /**
     * Constructs a node with the label and type.
     * @requires label, type != null
     * @effects creates a new node with no connections
     */
    public Node(T label, Object type) {
        this.nodeLabel = label;
        this.nodeType = type;
        this.children = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.edgesOut = new HashMap<>();
        this.edgesIn = new HashMap<>();
        checkRep();
    }

    public T getNodeLabel() {
        return nodeLabel;
    }

    public Object getType() {
        return nodeType;
    }

    /**
     * Adds a directed and labeled edge from this node to a child node.
     * @requires childNode != null, edgeLabel != null
     * @modifies this, childNode
     * @effects registers childNode and mutual edge labeled edgeLabel
     */
    public void addChild(Node<T> childNode, T edgeLabel) {
        checkRep();
        assert childNode != null;
        Edge<T> edge = new Edge<>(edgeLabel, this.nodeLabel, childNode.getNodeLabel());
        this.children.add(childNode.getNodeLabel());
        this.edgesOut.put(edgeLabel, edge);
        childNode.parents.add(this.nodeLabel);
        childNode.edgesIn.put(edgeLabel, edge);

        checkRep();
        childNode.checkRep();
    }


    /**
     * Adds a directed and labeled edge from a parent node to this node.
     * @requires parentNode != null, edgeLabel != null
     * @modifies this, parentNode
     * @effects registers parentNode and mutual edge labeled edgeLabel
     */
    public void addParent(Node<T> parentNode, T edgeLabel) {
        checkRep();
        parentNode.checkRep();

        Edge<T> edge = new Edge<>(edgeLabel, parentNode.nodeLabel, this.nodeLabel);
        this.parents.add(parentNode.nodeLabel);
        this.edgesIn.put(edgeLabel, edge);
        parentNode.children.add(this.nodeLabel);
        parentNode.edgesOut.put(edgeLabel, edge);

        checkRep();
        parentNode.checkRep();
    }

    /**
     * @return a copy of the children list
     */
    public ArrayList<T> getChildren() {
        checkRep();
        return new ArrayList<>(this.children);
    }
    /**
     * @return a copy of the parents list
     */
    public ArrayList<T> getParents() {
        checkRep();
        return new ArrayList<>(this.parents);
    }

    /**
     * @param child the child to check
     * @return true if the child exists
     */
    public boolean hasChild(T child) {
        checkRep();
        return this.children.contains(child);
    }

    /**
     * @param parent
     * @return true if the parent exist
     */
    public boolean hasParent(T parent) {
        checkRep();
        return this.parents.contains(parent);
    }

    /**
     * @param edgeLabel the edge label to check
     * @return true if the incoming edge exists
     */
    public boolean hasInEdge(T edgeLabel) {
        checkRep();
        return this.edgesIn.containsKey(edgeLabel);
    }

    /**
     * @param edgeLabel the edge label to check
     * @return true if the outgoing edge exists
     */
    public boolean hasOutEdge(T edgeLabel) {
        checkRep();
        return this.edgesOut.containsKey(edgeLabel);
    }

    /**
     * @param edgeLabel the edge label
     * @requires this.hasOutEdge(edgeLabel)
     * @return the child of the edge
     */
    public T getChildByEdge(T edgeLabel) {
        assert this.hasOutEdge(edgeLabel);
        return this.edgesOut.get(edgeLabel).getChild();
    }

    /**
     * @param edgeLabel the edge label
     * @requires this.hasInEdge(edgeLabel)
     * @return the parent of the edge
     */
    public T getParentByEdge(T edgeLabel) {
        assert this.hasInEdge(edgeLabel);
        return this.edgesIn.get(edgeLabel).getParent();
    }






}