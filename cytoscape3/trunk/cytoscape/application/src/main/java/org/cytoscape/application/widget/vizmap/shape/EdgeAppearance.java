/*
 File: EdgeAppearance.java

 Copyright (c) 2006, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

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

//----------------------------------------------------------------------------
// $Revision: 10556 $
// $Date: 2007-06-21 12:39:58 -0700 (Thu, 21 Jun 2007) $
// $Author: mes $
//----------------------------------------------------------------------------
package org.cytoscape.application.widget.vizmap.shape;



//----------------------------------------------------------------------------
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_COLOR;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_FONT_FACE;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_FONT_SIZE;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_LABEL;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_LABEL_COLOR;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_LINETYPE;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_LINE_WIDTH;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_SRCARROW;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_SRCARROW_COLOR;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_SRCARROW_SHAPE;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_TGTARROW;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_TGTARROW_COLOR;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_TGTARROW_SHAPE;
import static org.cytoscape.application.widget.vizmap.shape.VisualPropertyType.EDGE_TOOLTIP;


import giny.model.Edge;

import giny.view.EdgeView;
import giny.view.Label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;

import java.util.Properties;

import org.cytoscape.application.util.Cytoscape;
import org.cytoscape.application.widget.vizmap.parsers.ArrowParser;
import org.cytoscape.application.widget.vizmap.parsers.ColorParser;
import org.cytoscape.application.widget.vizmap.parsers.FloatParser;
import org.cytoscape.application.widget.vizmap.parsers.FontParser;
import org.cytoscape.application.widget.vizmap.parsers.ObjectToString;
import org.cytoscape.model.attribute.CyAttributes;


/**
 * Objects of this class hold data describing the appearance of an Edge.
 */
public class EdgeAppearance extends Appearance {

	/**
	 * Creates a new EdgeAppearance object.
	 */
	public EdgeAppearance() {
		super();
	}

	/**
	 * Clone.
	 */
    public Object clone() {
        EdgeAppearance ga = new EdgeAppearance();
        ga.copy(this);
        return ga;
	}

}
