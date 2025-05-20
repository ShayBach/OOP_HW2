package homework2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A WeightedNodePath characterizes a path of WeightedNodes. The cost for
 * a path is the sum of the costs of the WeightedNodes it contains.
 * <p>
 * A WeightedNodePath is immutable. A new WeightedNodePath is returned through
 * the extend path operation. 
 */
public class WeightedNodePath implements Path<WeightedNode, WeightedNodePath> {

	// RepInvariant:
  	//   (node != null) &&
  	//   (path == null) ==> (cost == node.cost) &&
  	//   (path != null) ==> (cost == node.cost + path.cost)
  	//

  	// Abstraction Function:
  	//
  	// The abstract state is given in terms of the specification fields of
  	// the Path interface, namely the cost and elements of a path.
   	//
  	// The Abstraction Function uses two helper functions, which map a
  	// concrete state 'c' to the abstract state.
  	//
  	//   AF(c) = <wnpcost(c), wnpelms(c)>
  	//
  	//   wnpcost(c) = c.cost
  	//   wnpelms(c) = / [c.node]                   if (c.path == null)
  	//                \ wnpelms(c.path) + [c.node] if (c.path != null)
    //	
	// (Note that [c.node] appears at the end not the start of the path
	//  sequence.)


	/**
	 * The WeightedNode at the end of the path.
	 */
  	private final WeightedNode node;

  	/**
  	 * A WeightedNodePath which, when extended with 'node' at the end, is
  	 * equal to this. May be null iff this path has only 1 element.
  	 */
  	private final WeightedNodePath path;

  	/**
  	 * Cost of this WeightedNodePath.
  	 */
  	private final int cost;


  	/**
     * Creates a WeightedNodePath containing one node.
     * @requires node != null
     * @effects Creates a new WeightedNodePath which originates at
     * <tte>node</tt>.
     */
  	public WeightedNodePath(WeightedNode node) {
    	this(node, null);
  	}


	/**
	 * Creates a WeightedNodePath from a WeightedNode and
	 * a WeigthedNodePath.
     * @requires node != null
     * @effects Creates a new WeightedNodePath 'res' such that
     * 			res.elements = path.elements + [node]
     */
  	private WeightedNodePath(WeightedNode node, WeightedNodePath path) {
    	if (node == null)
      		throw new IllegalArgumentException();

    	this.node = node;
    	this.path = path;

    	if (path != null)
      		this.cost = node.getCost() + path.cost;
    	else
      		this.cost = node.getCost();
  	}

	
  	/**
     * Creates an extended path by adding a new node to its end.
     * @requires node != null
     * @return a new WeightedNodePath p such that
     *	         p.elements == this.elements + [n] &&
     *           p.cost >= this.cost
     */
  	public WeightedNodePath extend(WeightedNode node) {
  		return new WeightedNodePath(node, this);
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
  		for (WeightedNodePath curNode = this; curNode != null; curNode = curNode.path) {
  			nodes.add(0, curNode.getEnd());
  		}
  		return nodes.iterator();
  	}



	/**
	 * Standard object to string conversion.
	 * @return a string representation of this in the form
	 * 		   [WeightedNodePath: node1, node2, node3, ...].
	 */
  	public String toString() {
    	StringBuffer buff = new StringBuffer();
    	buff.append("[WeightedNodePath: ");
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
     * @return true iff o is a WeightedNodePath and o.elements is the
     * 		   same sequence as this.elements
     */
  	public boolean equals(Object o) {
    	if (o instanceof WeightedNodePath)
      		return this.equals((WeightedNodePath)o);
    	return false;
  	}


	/**
	 * Standard equality operation.
     * @return true iff wnp.elements is the same sequence as this.elements
     */
  	public boolean equals(WeightedNodePath wnp) {
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
