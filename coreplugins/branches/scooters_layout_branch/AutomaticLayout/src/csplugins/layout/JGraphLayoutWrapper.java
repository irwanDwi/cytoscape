package csplugins.layout;

import org.jgraph.JGraph;
import org.jgraph.graph.*;

import org.jgraph.layout.*;

import giny.model.*;
import giny.view.*;
import giny.util.*;

import javax.swing.*;

import java.util.*;

import java.awt.geom.Rectangle2D;
import java.awt.Color;

import cern.colt.map.*;

import cytoscape.view.*;

import cytoscape.task.TaskMonitor;
import cytoscape.Cytoscape;

import cytoscape.layout.AbstractLayout;

public class JGraphLayoutWrapper extends AbstractLayout {

  public static final int ANNEALING       = 0;
  public static final int MOEN            = 1;
  public static final int CIRCLE_GRAPH    = 2;
  public static final int RADIAL_TREE     = 3;
  public static final int GEM             = 4;
  public static final int SPRING_EMBEDDED = 5;
  public static final int SUGIYAMA        = 6;
  public static final int TREE            = 7;

  int layout_type = 0;
  private JGraphLayoutAlgorithm layout = null;
	private JGraphLayoutSettings layoutSettings = null;
	private boolean canceled = false;

  public JGraphLayoutWrapper ( int layout_type) {
		super();
    this.layout_type = layout_type;
		switch (layout_type) {
    case ANNEALING:
      layout = new AnnealingLayoutAlgorithm();
			break;
    case MOEN:
      layout = new MoenLayoutAlgorithm();
			break;
    case CIRCLE_GRAPH:
      layout = new CircleGraphLayout();
			break;
    case RADIAL_TREE:
      layout = new RadialTreeLayoutAlgorithm();
			break;
    case GEM:
      layout = new GEMLayoutAlgorithm( new AnnealingLayoutAlgorithm() );
			break;
    case SPRING_EMBEDDED:
      layout = new SpringEmbeddedLayoutAlgorithm();
			break;
    case SUGIYAMA:
      layout = new SugiyamaLayoutAlgorithm();
			break;
    case TREE:
      layout = new TreeLayoutAlgorithm();
			break;
		}
		layoutSettings = layout.createSettings();
  }

	public String getName () { 
		switch (layout_type) {
		case ANNEALING:
			return "jgraph-annealing";
		case MOEN:
			return "jgraph-moen";
		case CIRCLE_GRAPH:
			return "jgraph-circle";
		case RADIAL_TREE:
			return "jgraph-radial-tree";
		case GEM:
			return "jgraph-gem";
		case SPRING_EMBEDDED:
			return "jgraph-spring";
		case SUGIYAMA:
			return "jgraph-sugiyama";
		case TREE:
			return "jgraph-tree";
		}
		return "";
	}

	public String toString () { 
		switch (layout_type) {
		case ANNEALING:
			return "Simulated Annealing Layout";
		case MOEN:
			return "MOEN Layout";
		case CIRCLE_GRAPH:
			return "Circle Layout";
		case RADIAL_TREE:
			return "Radial Tree Layout";
		case GEM:
			return "GEM Layout";
		case SPRING_EMBEDDED:
			return "Spring Embedded Layout";
		case SUGIYAMA:
			return "Sugiyama Layout";
		case TREE:
			return "Tree Layout";
		}
		return "";
	}

	/**
	 * Get the settings panel for this layout
	 */
	public JPanel getSettingsPanel() {
		return (JPanel)layoutSettings;
	}

	public void updateSettings() {
		if (layoutSettings != null)
			layoutSettings.apply();
	}

	public void revertSettings() {
		if (layoutSettings != null)
			layoutSettings.revert();
	}

	public void halt() {
		canceled = true;
		if (layout != null) layout.setCanceled();
	}

  public void construct ( )
  {
		canceled = false;
		initialize();
		double currentProgress = 0, percentProgressPerIter = 0;
    GraphPerspective perspective = networkView.getGraphPerspective();
    Map j_giny_node_map = new HashMap( PrimeFinder.nextPrime( perspective.getNodeCount() ) );
    Map giny_j_node_map = new HashMap( PrimeFinder.nextPrime( perspective.getNodeCount() ) );
    Map j_giny_edge_map = new HashMap( PrimeFinder.nextPrime( perspective.getEdgeCount() ) );

    Iterator node_iterator = perspective.nodesIterator();
    Iterator edge_iterator = perspective.edgesIterator();

		taskMonitor.setStatus("Executing Layout");
		taskMonitor.setPercentCompleted((int)currentProgress);

    // Construct Model and Graph
    //
    GraphModel model = new DefaultGraphModel();
    JGraph graph = new JGraph(model);
   
    // Create Nested Map (from Cells to Attributes)
    //
    Map attributes = new Hashtable();

   
    Set cells = new HashSet();

		// update progress bar
		currentProgress = 20;
		taskMonitor.setPercentCompleted((int)currentProgress);
		percentProgressPerIter = 20 / (double) (networkView.getNodeViewCount());

    // create Vertices
    while ( node_iterator.hasNext() && !canceled ) {

      // get the GINY node and node view
      giny.model.Node giny = ( giny.model.Node )node_iterator.next();
      NodeView node_view = networkView.getNodeView( giny );

      DefaultGraphCell jcell = new DefaultGraphCell( giny.getIdentifier() );
      
      // Set bounds
      Rectangle2D bounds = new Rectangle2D.Double( node_view.getXPosition(), 
                                                   node_view.getYPosition(),
                                                   node_view.getWidth(),
                                                   node_view.getHeight() );

      GraphConstants.setBounds( jcell.getAttributes(), bounds);

      j_giny_node_map.put( jcell, giny );
      giny_j_node_map.put( giny, jcell );

      cells.add( jcell );

	  	// update progress bar
		  currentProgress += percentProgressPerIter;
		  taskMonitor.setPercentCompleted((int)currentProgress);
	  }

		// update progress bar
		percentProgressPerIter = 20 / (double) (networkView.getEdgeViewCount());

    while ( edge_iterator.hasNext() && !canceled) {
      
      giny.model.Edge giny = ( giny.model.Edge )edge_iterator.next();
      
      DefaultGraphCell j_source = ( DefaultGraphCell )giny_j_node_map.get( giny.getSource() );
      DefaultGraphCell j_target = ( DefaultGraphCell )giny_j_node_map.get( giny.getTarget() );

      DefaultPort source_port = new DefaultPort();
      DefaultPort target_port = new DefaultPort();

      j_source.add( source_port );
      j_target.add( target_port );

      source_port.setParent(j_source);
      target_port.setParent(j_target);

      // create the edge
      DefaultEdge jedge = new DefaultEdge();
      j_giny_edge_map.put( jedge, giny );


      // Connect Edge
      //
      ConnectionSet cs = new ConnectionSet( jedge, source_port, target_port);
      Object[] ecells = new Object[] { jedge, j_source, j_target };
      
      // Insert into Model
      //
      model.insert( ecells, attributes, cs, null, null);
      
      cells.add( jedge );

	  	// update progress bar
		  currentProgress += percentProgressPerIter;
		  taskMonitor.setPercentCompleted((int)currentProgress);

    }

    layout.run( graph, cells.toArray());
    GraphLayoutCache cache = graph.getGraphLayoutCache();

    CellView cellViews[] = graph.getGraphLayoutCache().getAllDescendants(
                             graph.getGraphLayoutCache().getRoots());

		currentProgress = 80;
		taskMonitor.setPercentCompleted((int)currentProgress);
		percentProgressPerIter = 20 / (double) (cellViews.length);

		if (canceled) return;
    for (int i = 0; i < cellViews.length; i++)
    {
      CellView cell_view = cellViews[i];
      if ( cell_view instanceof VertexView ) {
        // ok, we found a node
        Rectangle2D rect = graph.getCellBounds(cell_view.getCell());
        giny.model.Node giny = (giny.model.Node) j_giny_node_map.get(
	                                           cell_view.getCell());
        NodeView node_view = networkView.getNodeView( giny );
        node_view.setXPosition( rect.getX(), false );
        node_view.setYPosition( rect.getY(), false );
        node_view.setNodePosition( true );
        
				// update progress bar
				currentProgress += percentProgressPerIter;
				taskMonitor.setPercentCompleted((int)currentProgress);
      }
    }

    // I don't think that any of the current layouts have edge components, 
    // so I won't bother for now.

    model = null;
    graph = null;
    attributes = null;
    cells = null;
    System.gc();
		networkView.fitContent();
		networkView.updateView();
  }
}
