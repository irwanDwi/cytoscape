package fing.model.test;

import fing.model.FingRootGraphFactory;
import giny.model.Edge;
import giny.model.Node;
import giny.model.RootGraph;

import java.util.Iterator;

public final class AllRootGraphMethodsTest
{

  // No constructor.
  private AllRootGraphMethodsTest() { }

  public static final void main(String[] args)
    throws ClassNotFoundException, InstantiationException,
           IllegalAccessException
  {
    final RootGraph root = FingRootGraphFactory.instantiateRootGraph();

    // Don't change this!  Any change here implies re-reading all the test
    // code below and making appropriate changes there.
    int[] nodeInx = new int[4];
    for (int i = 0; i < nodeInx.length; i++) nodeInx[i] = root.createNode();
    int[] edgeInx = new int[7];
    edgeInx[0] = root.createEdge(nodeInx[0], nodeInx[1], true);
    edgeInx[1] = root.createEdge(nodeInx[1], nodeInx[2], false);
    edgeInx[2] = root.createEdge(nodeInx[2], nodeInx[0], true);
    edgeInx[3] = root.createEdge(nodeInx[2], nodeInx[2], true);
    edgeInx[4] = root.createEdge(nodeInx[1], nodeInx[1], false);
    edgeInx[5] = root.createEdge(nodeInx[1], nodeInx[0], true);
    edgeInx[6] = root.createEdge(nodeInx[3], nodeInx[2], true);

    // nodesIterator() and edgesIterator().
    Iterator nodesIter = root.nodesIterator();
    Iterator edgesIter = root.edgesIterator();
    Node[] twoNodes = new Node[] { (Node) nodesIter.next(),
                                   (Node) nodesIter.next() };
    Edge[] twoEdges = new Edge[] { (Edge) edgesIter.next(),
                                   (Edge) edgesIter.next() };

    // createGraphPerspective(Node[], Edge[].
    if (root.createGraphPerspective(twoNodes, null).getNodeCount() != 2)
      throw new IllegalStateException
        ("GraphPerspective does not have two nodes");
    if (root.createGraphPerspective(null, twoEdges).getEdgeCount() != 2)
      throw new IllegalStateException
        ("GraphPerspective does not have two edges");
    if (root.createGraphPerspective(twoNodes, twoEdges).getNodeCount() < 2)
      throw new IllegalStateException
        ("GraphPerspective has less than two nodes");
    if (root.createGraphPerspective(twoNodes, twoEdges).getEdgeCount() < 2)
      throw new IllegalStateException
        ("GraphPerspective has less than two edges");
    if (root.createGraphPerspective((Node[]) null, (Edge[]) null) == null)
      throw new IllegalStateException("GraphPerspective is null");
    if (root.createGraphPerspective(new Node[0], new Edge[0]) == null)
      throw new IllegalStateException("GraphPerspective is null");
    RootGraph root2 = FingRootGraphFactory.instantiateRootGraph();
    root2.createNode();
    root2.createEdge
      (((Node) root2.nodesIterator().next()).getRootGraphIndex(),
       ((Node) root2.nodesIterator().next()).getRootGraphIndex());
    Node root2Node = (Node) root2.nodesIterator().next();
    Edge root2Edge = (Edge) root2.edgesIterator().next();
    if (root.createGraphPerspective(new Node[] { root2Node }, null) != null)
      throw new IllegalStateException("GraphPerspective is not null");
    if (root.createGraphPerspective(null, new Edge[] { root2Edge }) != null)
      throw new IllegalStateException("GraphPerspective is not null");
    if (root.createGraphPerspective(new Node[] { twoNodes[0], root2Node },
                                    new Edge[] { twoEdges[0], root2Edge })
        != null)
      throw new IllegalStateException("GraphPerspective is not null");

    // createGraphPerspective(int[], int[]).
    int[] twoNodeInx = new int[] { twoNodes[0].getRootGraphIndex(),
                                   twoNodes[1].getRootGraphIndex() };
    int[] twoEdgeInx = new int[] { twoEdges[0].getRootGraphIndex(),
                                   twoEdges[1].getRootGraphIndex() };
    if (root.createGraphPerspective(twoNodeInx, null).getNodeCount() != 2)
      throw new IllegalStateException
        ("GraphPerspective does not have two nodes");
    if (root.createGraphPerspective(null, twoEdgeInx).getEdgeCount() != 2)
      throw new IllegalStateException
        ("GraphPerspective does not have two edges");
    if (root.createGraphPerspective(twoNodeInx, twoEdgeInx).getNodeCount() < 2)
      throw new IllegalStateException
        ("GraphPerspective has less than two nodes");
    if (root.createGraphPerspective(twoNodeInx, twoEdgeInx).getEdgeCount() < 2)
      throw new IllegalStateException
        ("GraphPerspective has less than two edges");
    if (root.createGraphPerspective((int[]) null, (int[]) null) == null)
      throw new IllegalStateException("GraphPerspective is null");
    if (root.createGraphPerspective(new int[0], new int[0]) == null)
      throw new IllegalStateException("GraphPerspective is null");
    if (root.createGraphPerspective(new int[] { 0 }, null) != null)
      throw new IllegalStateException("GraphPerspective is not null");
    if (root.createGraphPerspective(null, new int[] { 0 }) != null)
      throw new IllegalStateException("GraphPerspective is not null");
    if (root.createGraphPerspective(new int[] { twoNodeInx[0], 0 },
                                    new int[] { twoEdgeInx[0], 9999 }) != null)
      throw new IllegalStateException("GraphPerspective is not null");

    // getNodeCount() and getEdgeCount().
    if (root.getNodeCount() != 4 || root.getEdgeCount() != 7)
      throw new IllegalStateException("incorrect nodes or edges count");

    // nodesList().
    java.util.List nodesList = root.nodesList();
    if (nodesList.size() != 4)
      throw new IllegalStateException("incorrect node List size");
    for (int i = 0; i < nodesList.size(); i++) {
      Node n = (Node) nodesList.get(i); }

    // getNodeIndicesArray().
    int[] nodeIndicesArray = root.getNodeIndicesArray();
    if (nodeIndicesArray.length != nodesList.size())
      throw new IllegalStateException
        ("size of nodes List and length of node indices array don't match");
    if (root.createGraphPerspective(nodeIndicesArray, null) == null)
      throw new IllegalStateException("GraphPerspective is null");

    // edgesList().
    java.util.List edgesList = root.edgesList();
    if (edgesList.size() != 7)
      throw new IllegalStateException("incorrect edge List size");
    for (int i = 0; i < edgesList.size(); i++) {
      Edge e = (Edge) edgesList.get(i); }

    // getEdgeIndicesArray().
    int[] edgeIndicesArray = root.getEdgeIndicesArray();
    if (edgeIndicesArray.length != edgesList.size())
      throw new IllegalStateException
        ("size of edges List and length of edge indices array don't match");
    if (root.createGraphPerspective(null, edgeIndicesArray) == null)
      throw new IllegalStateException("GraphPerspective is null");

    // Create and remove node/edge functionality is tested in other code.

    // containsNode(Node).
    if (!root.containsNode(twoNodes[1]))
      throw new IllegalStateException("RootGraph does not contain node");
    if (root.containsNode(root2Node))
      throw new IllegalStateException("RootGraph contains node from other");

    // containsEdge(Edge).
    if (!root.containsEdge(twoEdges[1]))
      throw new IllegalStateException("RootGraph does not contain edge");
    if (root.containsEdge(root2Edge))
      throw new IllegalStateException("RootGraph contains edge from other");

    // neighborsList(Node)
    java.util.List neighList = root.neighborsList(root.getNode(nodeInx[0]));
    if (neighList.size() != 2)
      throw new IllegalStateException("wrong number of neighbors");
  }

}
