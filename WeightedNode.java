package homework2;

/**
 * A WeightedNode class is a simple record type which contains a name
 * and a cost. 
 **/
public class WeightedNode implements Comparable<WeightedNode> {
	
	/**
	 * Name of this node.
	 */
  	private final String name;
  	
  	
  	/**
  	 * Cost of this node.
  	 */
  	private final int cost;


  	/**
     * Creates a WeightedNode.
     * @effects creates new WeightedNode with the name
     * <tt>name</tt> and the cost <tt>cost</tt>.
     *
     */
  	public WeightedNode(String name, int cost) {
    	this.name = name;
      	this.cost = cost;
  	}

  
	/**
	 * Returns this.name.
     * @return this.name
     */
  	public String getName() {
    	return name;
  	}


	/**
	 * Returns this.cost.
     * @return this.cost
     */
  	public int getCost() {
    	return cost;
  	}

	
	/**
	 * Standard equality operation.
	 * @return true iff o.instaceOf(WeightedNode) &&
	 *         (this.name.eqauls(o.name) && (this.cost == o.cost)
	 */
	public boolean equals(Object o) {
    	if (o instanceof WeightedNode) {
      		WeightedNode other = (WeightedNode)o;
      		return this.name.equals(other.name) &&
				   (this.cost == other.cost);
    	}
    	return false;
  	}
  
  
	/**
	 * Returns a hash code value for this.
	 * @return a hash code value for this.
	 */
  	public int hashCode() {
    	return name.hashCode();
  	}


	/**
	 * Standard object to string conversion.
	 * @return a string representation of this in the form [name: cost].
	 */
  	public String toString() {
    	return "[" + name + ": " + cost + "]";
  	}


	/**
	 * Compares this with the specified object for order.
	 * @return a negative integer, zero, or a positive integer as this
	 * 		   object is respectively less than, equal to, or greater than
	 *         the specified object .
	 * 		   <p>
     * 		   WeightedNodes are ordered lexicographically by their name.
     * 		   When two nodes share a name, their ordering is determined by
     * 		   the numeric ordering of their costs.
     */
  	public int compareTo(WeightedNode o) {	
    	int c = name.compareTo(o.name);
      	if (c == 0)
			return cost - o.cost;
      	else
			return c;
  	}

}
