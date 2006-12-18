package csplugins.layout.algorithms.graphPartition;

import java.util.*;
import javax.swing.JPanel;
import java.awt.GridLayout;

import cytoscape.*;
import cytoscape.view.*;
import cytoscape.data.*;
import giny.view.*;
import giny.model.*;

import filter.cytoscape.*;
import csplugins.layout.algorithms.graphPartition.AbstractGraphPartition;
import csplugins.layout.Tunable;
import csplugins.layout.LayoutProperties;


public class AttributeCircleLayout extends AbstractGraphPartition {
  CyAttributes data;
  String attribute = null;
  private double spacing = 50.0;
	boolean supportNodeAttributes = true;
	LayoutProperties layoutProperties = null;

  public AttributeCircleLayout ( boolean supportAttributes ) {
    super( );
		initialize(supportAttributes);
  }

  public AttributeCircleLayout ( ) {
    super( );
		initialize(true);
  }

	public void initialize( boolean supportAttributes ) {
		supportNodeAttributes = supportAttributes;
		layoutProperties = new LayoutProperties(getName());
		initialize_properties();
	}
	
	// Required methods for AbstactLayout
	public byte [] supportsNodeAttributes() {
		if (!supportNodeAttributes)
			return null;

		byte[] all = {-1};
		return all;
	}

	/**
	 * Sets the attribute to use for the weights
	 *
	 * @param value the name of the attribute
	 */
	public void setLayoutAttribute(String value) {
		if (value.equals("(none)"))
			this.attribute = null;
		else
			this.attribute = value;
	}

	/**
	 * Get the settings panel for this layout
	 */
	public JPanel getSettingsPanel() {
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.add(layoutProperties.getTunablePanel());
		return panel;
	}

	protected void initialize_properties() {
		layoutProperties.add(new Tunable("spacing", "Circle size",
																		Tunable.DOUBLE, new Double(100.0)));
		layoutProperties.add(new Tunable("attribute", "The attribute to use for the layout", 
																		Tunable.NODEATTRIBUTE, "(none)", (Object)getInitialAttributeList(),
																		(Object)null, 0));
		// We've now set all of our tunables, so we can read the property 
		// file now and adjust as appropriate
		layoutProperties.initializeProperties();

		// Finally, update everything.  We need to do this to update
		// any of our values based on what we read from the property file
		updateSettings(true);
	}

	public void updateSettings() {
		updateSettings(false);
	}

	public void updateSettings(boolean force) {
		layoutProperties.updateValues();
		Tunable t = layoutProperties.get("spacing");
		if (t != null && (t.valueChanged() || force))
			spacing = ((Double)t.getValue()).doubleValue();
		t = layoutProperties.get("attribute");
		if (t != null && (t.valueChanged() || force)) {
			String newValue = (String)t.getValue();
			if (newValue.equals("(none)")) {
				attribute = null;
			} else {
				attribute = newValue;;
			}
		}
	}

	public void revertSettings() {
		layoutProperties.revertProperties();
	}

	/**
	 *
	 * We don't have any special widgets
	 *
	 * @returns List of our "special" weights
	 */
	public List getInitialAttributeList() {
		ArrayList<String>attList = new ArrayList<String>();
		attList.add("(none)");
		return attList;
	}

	public String toString () { 
		if (!supportNodeAttributes) {
			return "Circle Layout"; 
		} else {
			return "Attribute Circle Layout"; 
		}
	}

	public String getName () { 
		if (!supportNodeAttributes)
			return "circle";
		else
			return "attribute-circle"; 
	}

  public void layoutPartion ( GraphPerspective net ) {
		data = Cytoscape.getNodeAttributes();

    int count = net.getNodeCount();
    int r = (int)Math.sqrt(count);
    r*=spacing;

    // nodesList is deprecated, so we need to create our own so
    // that we can hand it off to the sort routine
    List nodes = net.nodesList();

		if (this.attribute != null)
    	Collections.sort( nodes, new AttributeComparator() );

    // Compute angle step
    double phi = 2 * Math.PI / nodes.size();
    
    // Arrange vertices in a circle
    for (int i = 0; i < nodes.size(); i++) {
      Node node = (Node)nodes.get( i );
      layout.setX( node, r + r * Math.sin(i * phi) );
      layout.setY( node, r + r * Math.cos(i * phi) );

    }

  }

  private class AttributeComparator implements Comparator {
    
    private AttributeComparator () {}
    
    public int compare ( Object oo1, Object oo2 ) {
      
      GraphObject o1 = (GraphObject)oo1;
      GraphObject o2 = (GraphObject)oo2;
      
      byte type = data.getType( attribute );
      if ( type == CyAttributes.TYPE_STRING ) {
        String v1 = data.getStringAttribute( o1.getIdentifier(), attribute );
        String v2 = data.getStringAttribute( o2.getIdentifier(), attribute );

				if (v1 != null && v2 != null)
        	return v1.compareToIgnoreCase( v2 );
				else if (v1 == null && v2 != null) return -1;
				else if (v1 == null && v2 == null) return 0;
				else if (v1 != null && v2 == null) return 1;
 
      } else if ( type == CyAttributes.TYPE_FLOATING ) {
        Double v1 = data.getDoubleAttribute( o1.getIdentifier(), attribute );
        Double v2 = data.getDoubleAttribute( o2.getIdentifier(), attribute );

				if (v1 != null && v2 != null)
        	return v1.compareTo( v2 );
				else if (v1 == null && v2 != null) return -1;
				else if (v1 == null && v2 == null) return 0;
				else if (v1 != null && v2 == null) return 1;
        
      } else if ( type == CyAttributes.TYPE_INTEGER ) {
        Integer v1 = data.getIntegerAttribute( o1.getIdentifier(), attribute );
        Integer v2 = data.getIntegerAttribute( o2.getIdentifier(), attribute );
        
				if (v1 != null && v2 != null)
        	return v1.compareTo( v2 );
				else if (v1 == null && v2 != null) return -1;
				else if (v1 == null && v2 == null) return 0;
				else if (v1 != null && v2 == null) return 1;
      } else if ( type == CyAttributes.TYPE_BOOLEAN ) {
        Boolean v1 = data.getBooleanAttribute( o1.getIdentifier(), attribute );
        Boolean v2 = data.getBooleanAttribute( o2.getIdentifier(), attribute );
        
				if (v1 != null && v2 != null) {
        	if ( ( v1.booleanValue() && v2.booleanValue() ) || !v1.booleanValue() && !v2.booleanValue() )
         	 return 0;
        	else if ( v1.booleanValue() && !v2.booleanValue() )
         	 return 1;
        	else if ( !v1.booleanValue() && v2.booleanValue() )
         	 return -1;
				}
				else if (v1 == null && v2 != null) return -1;
				else if (v1 == null && v2 == null) return 0;
				else if (v1 != null && v2 == null) return 1;
      }
      return 0;
    }
  }

  


}
