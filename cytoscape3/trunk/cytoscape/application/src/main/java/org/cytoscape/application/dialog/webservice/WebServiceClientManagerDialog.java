
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

package org.cytoscape.application.dialog.webservice;



import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.cytoscape.application.util.Cytoscape;
import org.cytoscape.io.WebServiceClientManager;
import org.cytoscape.io.WebServiceClientManager.ClientType;
import org.cytoscape.io.network.reader.WebServiceClient;


/**
 *
 * @author  kono
 */
public class WebServiceClientManagerDialog extends JDialog implements PropertyChangeListener {
	private static WebServiceClientManagerDialog dialog;
	private static Map<String, JPanel> clientUIs;

	static {
		dialog = new WebServiceClientManagerDialog(null, false);
		clientUIs = new HashMap<String, JPanel>();
	}

	/** Creates new form WSManagerDialog */
	private WebServiceClientManagerDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		
		buildTree();
	}

	private void buildTree() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Web Service Clients");
		DefaultMutableTreeNode attrImport = new DefaultMutableTreeNode("Attribute Import");
		DefaultMutableTreeNode netImport = new DefaultMutableTreeNode("Network Import");
		DefaultMutableTreeNode analysis = new DefaultMutableTreeNode("Analysis");
		DefaultMutableTreeNode other = new DefaultMutableTreeNode("Other");

		rootNode.add(attrImport);
		rootNode.add(netImport);
		rootNode.add(analysis);
		rootNode.add(other);

		
		// Now register clients
		List<WebServiceClient> wsc = WebServiceClientManager.getAllClients();
		for(WebServiceClient ws : wsc) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(ws.getDisplayName());
			if(ws.getClientType().equals(ClientType.ATTRIBUTE)) {
				attrImport.add(node);
			} else if(ws.getClientType().equals(ClientType.NETWORK)) {
				netImport.add(node);
			} else if(ws.getClientType().equals(ClientType.ANALYSIS)) {
				analysis.add(node);
			} else {
				other.add(node);
			}
		}
		
		serviceTree = new JTree(new DefaultTreeModel(rootNode));
		serviceTreeScrollPane.setViewportView(serviceTree);
		serviceTree.repaint();
		repaint();
	}

	/**
	 *  DOCUMENT ME!
	 */
	public static void showDialog() {
		dialog.setLocationRelativeTo(Cytoscape.getDesktop());
		dialog.setVisible(true);
	}

	/**
	 *  DOCUMENT ME!
	 *
	 * @param clientName DOCUMENT ME!
	 * @param panel DOCUMENT ME!
	 */
	public static void registerUI(String clientName, JPanel panel) {
		dialog.clientPanel.removeAll();
		dialog.clientPanel.add(panel, BorderLayout.CENTER);
		clientUIs.put(clientName, panel);
		dialog.pack();
		dialog.clientPanel.repaint();
		dialog.repaint();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed" desc=" Generated Code">                          
	private void initComponents() {
		titlePanel = new javax.swing.JPanel();
		iconLabel = new javax.swing.JLabel();
		titleLabel = new javax.swing.JLabel();
		mainSplitPane = new javax.swing.JSplitPane();
		clientPanel = new javax.swing.JPanel();
		serviceTreePanel = new javax.swing.JPanel();
		serviceTreeScrollPane = new javax.swing.JScrollPane();
		serviceTree = new javax.swing.JTree();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		titlePanel.setBackground(new java.awt.Color(255, 255, 255));
		iconLabel.setIcon(new javax.swing.ImageIcon("/cellar/users/kono/workspace/FRESH4/images/Globe.png"));

		titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24));
		titleLabel.setText("Cytoscape Web Service Client Manager");

		org.jdesktop.layout.GroupLayout titlePanelLayout = new org.jdesktop.layout.GroupLayout(titlePanel);
		titlePanel.setLayout(titlePanelLayout);
		titlePanelLayout.setHorizontalGroup(titlePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                                                    .add(titlePanelLayout.createSequentialGroup()
		                                                                         .addContainerGap()
		                                                                         .add(iconLabel)
		                                                                         .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                                                                         .add(titleLabel)
		                                                                         .addContainerGap(60,
		                                                                                          Short.MAX_VALUE)));
		titlePanelLayout.setVerticalGroup(titlePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                                                  .add(titlePanelLayout.createSequentialGroup()
		                                                                       .addContainerGap()
		                                                                       .add(titlePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                                                                                            .add(titleLabel)
		                                                                                            .add(org.jdesktop.layout.GroupLayout.LEADING,
		                                                                                                 iconLabel,
		                                                                                                 org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                                                                                 org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                                                                                 Short.MAX_VALUE))
		                                                                       .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                                                                        Short.MAX_VALUE)));

		mainSplitPane.setDividerLocation(200);
		mainSplitPane.setDividerSize(5);
		clientPanel.setBackground(new java.awt.Color(255, 255, 255));
		clientPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		clientPanel.setLayout(new BorderLayout());
		//        org.jdesktop.layout.GroupLayout clientPanelLayout = new org.jdesktop.layout.GroupLayout(clientPanel);
		//        clientPanel.setLayout(clientPanelLayout);
		//        clientPanelLayout.setHorizontalGroup(
		//            clientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		//            .add(0, 399, Short.MAX_VALUE)
		//        );
		//        clientPanelLayout.setVerticalGroup(
		//            clientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		//            .add(0, 625, Short.MAX_VALUE)
		//        );
		mainSplitPane.setRightComponent(clientPanel);

		serviceTreePanel.setBackground(new java.awt.Color(255, 255, 255));
		serviceTreePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
		                                                                        "Available Services",
		                                                                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
		                                                                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
		                                                                        new java.awt.Font("SansSerif",
		                                                                                          0,
		                                                                                          12)));
		serviceTreeScrollPane.setViewportView(serviceTree);

		org.jdesktop.layout.GroupLayout serviceTreePanelLayout = new org.jdesktop.layout.GroupLayout(serviceTreePanel);
		serviceTreePanel.setLayout(serviceTreePanelLayout);
		serviceTreePanelLayout.setHorizontalGroup(serviceTreePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                                                                .add(serviceTreeScrollPane,
		                                                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                                                     189, Short.MAX_VALUE));
		serviceTreePanelLayout.setVerticalGroup(serviceTreePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                                                              .add(serviceTreeScrollPane,
		                                                                   org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                                                   602, Short.MAX_VALUE));
		mainSplitPane.setLeftComponent(serviceTreePanel);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                                .add(titlePanel,
		                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                     Short.MAX_VALUE)
		                                .add(mainSplitPane,
		                                     org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 607,
		                                     Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                              .add(layout.createSequentialGroup()
		                                         .add(titlePanel,
		                                              org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
		                                              org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                              org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                                         .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                                         .add(mainSplitPane,
		                                              org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                                              629, Short.MAX_VALUE)));
		pack();
	} // </editor-fold>                        

	// Variables declaration - do not modify                     
	private javax.swing.JPanel clientPanel;
	private javax.swing.JLabel iconLabel;
	private javax.swing.JSplitPane mainSplitPane;
	private javax.swing.JTree serviceTree;
	private javax.swing.JPanel serviceTreePanel;
	private javax.swing.JScrollPane serviceTreeScrollPane;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JPanel titlePanel;
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	// End of variables declaration                   
}
