
/*
 Copyright (c) 2006, 2007, The Cytoscape Consortium (www.cytoscape.org)

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

package org.cytoscape.application.widget.vizmap.shape;

import java.awt.BasicStroke;
import java.awt.Stroke;
import javax.swing.Icon;

import org.cytoscape.application.widget.vizmap.icon.LineTypeIcon;
import org.cytoscape.application.widget.vizmap.icon.VisualPropertyIcon;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import cytoscape.visual.ui.icon.*;

/**
 *
 * Define line stroke.
 *
 * TODO: need to modify rendering engine to fully support dash lines.
 *
 * @author kono
 *
 */
public enum LineStyle {
	SOLID(null,"line"),
	LONG_DASH("10.0f,4.0f","dash");

	// DASH("4.0f,4.0f"),
	// DASH_DOT("12.0f,3.0f,3.0f,3.0f"),

	private final float[] strokeDef;
	private String regex;

	private LineStyle(String def, String regex) {
		if (def == null)
			strokeDef = null;
		else {
			final String[] parts = def.split(",");
			strokeDef = new float[parts.length];

			for (int i = 0; i < strokeDef.length; i++)
				strokeDef[i] = Float.parseFloat(parts[i]);
		}

		this.regex = regex;
	}

	private String getRegex() {
		return regex;
	}

	public static LineStyle parse(String val) {
		// first check the style names
		for ( LineStyle ls : values() ) {
			if ( ls.toString().equals(val) )
				return ls;
		}

		// then try regex matching instead 
		for ( LineStyle ls : values() ) {
			Pattern p = Pattern.compile(ls.getRegex(),Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(val);
			if ( m.matches() ) {
				return ls;
			}
		}

		// default
		return SOLID;
	}

	private static Pattern numPattern = Pattern.compile("(\\d+)");

	/** 
	 * This method attempts to extract a width from a string that has
	 * a number in it like "dashed1" or "line2". This exists to support
	 * old-style line type definitions.
	 * @return The parsed value or if something doesn't match, 1.0
	 */
	public static float parseWidth(String s) {
		Matcher m = numPattern.matcher(s);
		if ( m.matches() ) {
			try {
				return (new Float(m.group(1))).floatValue();
			} catch (Exception e) { }
		}
		return 1.0f;
	}

	/**
	 * A method that attempts to figure out if a stroke is dashed
	 * or not.  If the Stroke object is not a BasicStroke, it will
	 * return SOLID by default.
	 * @return the LineStyle guessed based on the BasicStroke dash array.
	 */
	public static LineStyle extractLineStyle(Stroke stroke) {
		if ( stroke instanceof BasicStroke ) {
        	final float[] dash = ((BasicStroke)stroke).getDashArray();
			if ( dash == null )
				return SOLID;
			else
				return LONG_DASH;
		} 

		return SOLID;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param width DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Stroke getStroke(float width) {
		if (strokeDef != null)
			return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
			                       strokeDef, 0.0f);
		else
			return new BasicStroke(width);
	}
	
	public float[] getDashDef() {
		return strokeDef;
	}

    public static Map<Object,Icon> getIconSet() {
        Map<Object,Icon> icons = new HashMap<Object,Icon>();

        for (LineStyle def : values()) {
            LineTypeIcon icon = new LineTypeIcon((BasicStroke) def.getStroke(5.0f), 
                                                 VisualPropertyIcon.DEFAULT_ICON_SIZE * 4, 
                                                 VisualPropertyIcon.DEFAULT_ICON_SIZE, 
												 def.name());
            icons.put(def, icon);
        }

        return icons;
    }
}
