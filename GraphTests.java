
package homework2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;
/**
 * This class contains a set of test cases that can be used to test the graph
 * and shortest path finding algorithm implementations of homework assignment
 * #2.
 */
public class GraphTests extends ScriptFileTests {

	public GraphTests(java.nio.file.Path testFile) {
		super(testFile);
	}

	private Graph<WeightedNode> graph;
	private PathFinder<WeightedNode> pathFinder;

	@Before
	public void setUp() {
		graph = new Graph<>();
		pathFinder = new PathFinder<>(graph);
	}

	// ==== WeightedNode Tests ====
	@Test
	public void testWeightedNodeEquality() {
		WeightedNode a1 = new WeightedNode("A", 1);
		WeightedNode a2 = new WeightedNode("A", 1);
		WeightedNode b = new WeightedNode("B", 1);
		assertEquals(a1, a2);
		assertNotEquals(a1, b);
	}

	@Test
	public void testWeightedNodeHashCode() {
		WeightedNode a1 = new WeightedNode("A", 1);
		WeightedNode a2 = new WeightedNode("A", 1);
		assertEquals(a1.hashCode(), a2.hashCode());
	}

	@Test
	public void testWeightedNodeToString() {
		WeightedNode a = new WeightedNode("A", 1);
		assertEquals("[A: 1]", a.toString());
	}

	// ==== Graph Tests ====
	@Test
	public void testAddAndContainsNode() {
		WeightedNode node = new WeightedNode("X", 2);
		graph.addNode(node);
		assertTrue(graph.containsNode(node));
	}

	@Test
	public void testAddEdgeCreatesConnection() {
		WeightedNode parent = new WeightedNode("P", 1);
		WeightedNode child = new WeightedNode("C", 2);
		graph.addNode(parent);
		graph.addNode(child);
		assertTrue(graph.addEdge(parent, child));
		assertTrue(graph.containsEdge(parent, child));
	}

	@Test
	public void testGraphGetListNodesSorted() {
		graph.addNode(new WeightedNode("B", 1));
		graph.addNode(new WeightedNode("A", 1));
		graph.addNode(new WeightedNode("C", 1));
		List<WeightedNode> nodes = graph.getListNodes();
		assertEquals("A", nodes.get(0).getName());
		assertEquals("B", nodes.get(1).getName());
		assertEquals("C", nodes.get(2).getName());
	}

	// ==== PathFinder Tests ====
	@Test
	public void testFindPathSelfLoop() {
		WeightedNode node = new WeightedNode("Loop", 1);
		graph.addNode(node);
		graph.addEdge(node, node);
		WeightedNodePath startPath = new WeightedNodePath(node);
		WeightedNodePath result = pathFinder.findShortestPath(startPath, node);
		assertNotNull(result);
		assertEquals(1.0, result.getCost(), 0.001);
		assertEquals(node, result.getEnd());
	}

	@Test
	public void testFindPathWithLinearChain() {
		WeightedNode a = new WeightedNode("A", 1);
		WeightedNode b = new WeightedNode("B", 2);
		WeightedNode c = new WeightedNode("C", 3);
		graph.addNode(a);
		graph.addNode(b);
		graph.addNode(c);
		graph.addEdge(a, b);
		graph.addEdge(b, c);

		WeightedNodePath start = new WeightedNodePath(a);
		WeightedNodePath result = pathFinder.findShortestPath(start, c);
		assertNotNull(result);
		assertEquals(c, result.getEnd());
	}

	@Test
	public void testNoPathExists() {
		WeightedNode a = new WeightedNode("A", 1);
		WeightedNode b = new WeightedNode("B", 1);
		graph.addNode(a);
		graph.addNode(b);
		WeightedNodePath start = new WeightedNodePath(a);
		WeightedNodePath result = pathFinder.findShortestPath(start, b);
		assertNull(result);
	}
}
