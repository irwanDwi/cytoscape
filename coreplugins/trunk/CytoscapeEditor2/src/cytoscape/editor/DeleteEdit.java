
package cytoscape.editor;

import cytoscape.Cytoscape;
import cytoscape.CyNetwork;
import cytoscape.actions.GinyUtils;
import cytoscape.view.CyNetworkView;
import giny.view.NodeView;
import java.util.List;
import cytoscape.util.undo.CyAbstractEdit;


/**
 * An undoable edit that will undo and redo deletion of nodes and edges.
 */ 
public class DeleteEdit extends CyAbstractEdit {

	int[] nodes;
	int[] edges;
	double[] xPos;
	double[] yPos;
	CyNetwork net;

	public DeleteEdit(CyNetwork net, int[] nodeInd, int[] edgeInd) {
		super("Delete");
		if ( net == null )
			throw new IllegalArgumentException("network is null");
		this.net = net;
		
		nodes = new int[nodeInd.length]; 
		for ( int i = 0; i < nodeInd.length; i++ )
			nodes[i] = nodeInd[i];

		edges = new int[edgeInd.length];
		for ( int i = 0; i < edgeInd.length; i++ ) 
			edges[i] = edgeInd[i];

		// save the positions of the nodes
		xPos = new double[nodes.length]; 
		yPos = new double[nodes.length]; 
		CyNetworkView netView = Cytoscape.getNetworkView(net.getIdentifier());
		if ( netView != null && netView != Cytoscape.getNullNetworkView() ) {
			for ( int i = 0; i < nodes.length; i++ ) {
				NodeView nv = netView.getNodeView(nodes[i]);
				xPos[i] = nv.getXPosition();
				yPos[i] = nv.getYPosition();
			}
		}
	}

	public void redo() {
		super.redo();

		net.hideEdges(edges);
		net.hideNodes(nodes);
        Cytoscape.firePropertyChange(Cytoscape.NETWORK_MODIFIED,
                                     CytoscapeEditorManager.CYTOSCAPE_EDITOR, net);
	}

	public void undo() {
		super.undo();

		net.restoreNodes(nodes);
		net.restoreEdges(edges);

		CyNetworkView netView = Cytoscape.getNetworkView(net.getIdentifier());
		if ( netView != null && netView != Cytoscape.getNullNetworkView() ) {
			for ( int i = 0; i < nodes.length; i++ ) {
				NodeView nv = netView.getNodeView(nodes[i]);
				nv.setOffset( xPos[i], yPos[i] );
			}
		}
        Cytoscape.firePropertyChange(Cytoscape.NETWORK_MODIFIED,
                                     CytoscapeEditorManager.CYTOSCAPE_EDITOR, net);
	}
}
