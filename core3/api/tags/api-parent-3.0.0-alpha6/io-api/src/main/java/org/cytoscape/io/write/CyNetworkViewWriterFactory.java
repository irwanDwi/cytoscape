package org.cytoscape.io.write;

import org.cytoscape.view.model.CyNetworkView;

/**
 * A specialization of {@link CyWriterFactory} that allows a 
 * {@link CyNetworkView} to be specified and written.
 */
public interface CyNetworkViewWriterFactory extends CyWriterFactory {

	/**
	 * Specifies the {@link CyNetworkView} to be written by the 
	 * {@link CyWriter} Task generated by this factory.  This method must be called 
	 * prior to calling the getWriter() method.
	 * @param view The {@link CyNetworkView} to be written.
	 */
	void setNetworkView(CyNetworkView view);
}
