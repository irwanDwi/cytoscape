/*
 File: SelectAdjacentEdgesTask.java

 Copyright (c) 2006, 2010, The Cytoscape Consortium (www.cytoscape.org)

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package org.cytoscape.task.internal.select;


import java.util.HashSet;
import java.util.Set;

import org.cytoscape.model.CyTableUtil;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.view.model.CyNetworkViewManager;


public class SelectAdjacentEdgesTask extends AbstractSelectTask {
	public SelectAdjacentEdgesTask(final CyNetwork net, final CyNetworkViewManager networkViewManager) {
		super(net, networkViewManager);
	}

	public void run(TaskMonitor tm) {
		final Set<CyEdge> edgeSet = new HashSet<CyEdge>();

		// Get the list of selected nodes
		for (CyNode node: CyTableUtil.getNodesInState(net,"selected",true)) {
			// Get the list of edges connected to this node
			edgeSet.addAll( net.getAdjacentEdgeList(node,CyEdge.Type.ANY) );
		}

		SelectUtils.setSelectedEdges(edgeSet,true);
		updateView();
	} 
}
