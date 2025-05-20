package homework2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * A NodeCountingPath characterizes a path of WeightedNodes. The cost for
 * a path is the number of WeightedNodes it contains; the cost of the
 * WeightedNodes are ignored.
 * <p>
 * A NodeCountingPath is immutable. A new NodeCountingPath is returned
 * through the extend path operation.
 * <p>
 * The main purpose of this class is to illustrate that there can be multiple
 * implementations of Paths of WeightedNodes. 
 */
public class NodeCountingPath implements Path<WeightedNode, NodeCountingPath> {

	// RepInvariant:
  	//   (node != null) &&
  	//   (path == null) ==> (cost == 1) &&
  	//   (path != null) ==> (cost == 1 + path.cost)
  	//

  	// Abstraction Function:
  	//
  	// The abstract state is given in terms of the specification fields of
  	// the Path interface, namely the cost and elements of a path.
   	//
  	// The Abstraction Function uses two helper functions, which map a
  	// concrete state 'c' to the abstract state.
  	//
  	//   AF(c) = <ncpcost(c), ncpelms(c)>
  	//
  	//   ncpcost(c) = c.cost
  	//   ncpelms(c) = / [c.node]                   if (c.path == null)
  	//                \ ncpelms(c.path) + [c.node] if (c.path != null)
    //	
	// (Note that [c.node] appears at the end not the start of the path
	//  sequence.)


	/**
	 * The WeightedNode at the end of the path.
	 */
  	private final WeightedNode node;

  	/**
  	 * A NodeCountingPath which, when extended with 'node' at the end, is
  	 * equal to this. May be null iff this path has only 1 element.
  	 */
  	private final NodeCountingPath path;

  	/**
  	 * Cost of this NodeCountingPath (that is, its length).
  	 */
  	private final int cost;


  	/**
     * Creates a NodeCountingPath containing one node.
     * @requires node != null
     * @effects Creates a new NodeCountingPath which originates at
     * <tte>node</tt>.
     */
  	public NodeCountingPath(WeightedNode node) {
    	this(node, null);
  	}


	/**
	 * Creates a NodeCountingPath from a WeightedNode and
	 * a NodeCountingPath.
     * @requires node != null
     * @effects Creates a new NodeCountingPath 'res' such that
     * 			res.elements = path.elements + [node]
     */
  	private NodeCountingPath(WeightedNode node, NodeCountingPath path) {
    	if (node == null)
      		throw new IllegalArgumentException();

    	this.node = node;
    	this.path = path;

    	if (path != null)
      		this.cost = 1 + path.cost;
    	else
      		this.cost = 1;
  	}

	
  	/**
     * Creates an extended path by adding a new node to its end.
     * @requires node != null
     * @return a new NodeCountingPath p such that
     *	         p.elements == this.elements + [n] &&
     *           p.cost >= this.cost
     */
  	public NodeCountingPath extend(WeightedNode node) {
  		return new NodeCountingPath(node, this);
  	}


	/**
	 * Returns this.cost.
	 * @return this.cost
	 */
  	public double getCost() {
    	return this.cost;
  	}


  	/**
     * Returns an Iterator over the elements in the path .
     * @return an Iterator over the elements in the path in order from start
     *         to end.
     */
  	public Iterator<WeightedNode> iterator() {
  		// reverse the linked list, so that elements are returned in order
  		// from start to end of the path.
  		List<WeightedNode> nodes = new LinkedList<WeightedNode>();
  		for (NodeCountingPath curNode = this; curNode != null; curNode = curNode.path) {
  			nodes.add(0, curNode.getEnd());
  		}
  		return nodes.iterator();
  	}



	/**
	 * Standard object to string conversion.
	 * @return a string representation of this in the form
	 * 		   [NodeCountingPath: node1, node2, node3, ...].
	 */
  	public String toString() {
    	StringBuffer buff = new StringBuffer();
    	buff.append("[NodeCountingPath: ");
    	boolean first = true;
    	for (WeightedNode wn : this) {
    		if (first)
    			first = false;
    		else
    			buff.append(", ");
    		buff.append(wn);
    	}
    	buff.append("]");
    	return buff.toString();
  	}


	/**
	 * Standard equality operation.
     * @return true iff o is a NodeCountingPath and o.elements is the
     * 		   same sequence as this.elements
     */
  	public boolean equals(Object o) {
    	if (o instanceof NodeCountingPath)
      		return this.equals((NodeCountingPath)o);
    	return false;
  	}


	/**
	 * Standard equality operation.
     * @return true iff wnp.elements is the same sequence as this.elements
     */
  	public boolean equals(NodeCountingPath wnp) {
    	return (wnp != null) &&
      		   this.node.equals(wnp.node) &&
      		   (this.path == null ?
      		   		wnp.path == null : this.path.equals(wnp.path));
  	}


	/**
	 * Returns a hash code value for this.
	 * @return a hash code value for this.
	 */
  	public int hashCode() {
    	return node.hashCode() + (this.path == null ? 0 : 13*path.hashCode());
  	}
  	
  	
  	/** Compares the cost of this path to the given path.
  	 * @return the value 0 if the cost of this path is equal to the
  	 *         cost of the given path; a value less than 0 if this.cost is
  	 *         less than p.cost; and a value greater than 0 if this.cost
  	 *         is greater than p.cost.
  	 * @see java.lang.Comparable#compareTo
  	 */
  	public int compareTo(Path<?,?> p){
  		return Double.compare(this.getCost(), p.getCost());
  	}
  	        
  	
  	/**
  	 * Return the end of this path
  	 */
  	public WeightedNode getEnd(){
  		return node;
  	}
}
