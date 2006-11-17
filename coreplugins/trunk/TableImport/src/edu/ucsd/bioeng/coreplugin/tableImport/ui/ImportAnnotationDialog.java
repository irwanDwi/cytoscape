package edu.ucsd.bioeng.coreplugin.tableImport.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.bookmarks.Bookmarks;
import cytoscape.bookmarks.DataSource;
import cytoscape.data.CyAttributes;
import cytoscape.data.ontology.Ontology;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.task.ui.JTaskConfig;
import cytoscape.task.util.TaskManager;
import cytoscape.util.BookmarksUtil;
import cytoscape.util.FileUtil;
import cytoscape.util.URLUtil;
import edu.ucsd.bioeng.coreplugin.tableImport.reader.TextTableReader;
import giny.model.Node;

/*
 * ImportAttributesDialog.java
 *
 * Created on 2006/08/09, 10:33
 */
/**
 * GUI for importing annotation data (maninly for Gene Association files)<br>
 * 
 * @author kono
 */
public class ImportAnnotationDialog extends JDialog {

	private static final String ID = "ID";
	private static final String GENE_ASSOCIATION = "gene_association";

	// Default key columns for Gene Association file
	private static final int PRIMARY_KEY = 2;
	private static final int ONTOLOGY_COL = 4;
	private static final int ALIAS = 10;
	private static final int SPECIES = 12;

	// Default color
	protected static final Color KEY_ATTR_COLOR = Color.RED;
	protected static final Color PRIMARY_KEY_COLOR = new Color(51, 51, 255);
	protected static final Color ONTOLOGY_COLOR = new Color(0, 255, 255);
	protected static final Color ALIAS_COLOR = new Color(51, 204, 0);
	protected static final Color SPECIES_COLOR = new Color(182, 36, 212);

	private int primaryKey;
	private int ontologyColumn;
	private int species;
	private int[] aliases;

	private Bookmarks bookmarks;
	private CyAttributes nodeAttributes;
	private Map<String, String> urlMap;
	private Map<String, String> formatMap;

	/** Creates new form ImportAttributesDialog */
	public ImportAnnotationDialog(Frame parent, boolean modal) {
		super(parent, modal);

		// Set default values
		this.primaryKey = PRIMARY_KEY;
		this.ontologyColumn = ONTOLOGY_COL;
		this.species = SPECIES;
		this.aliases = new int[1];
		aliases[0] = ALIAS;

		nodeAttributes = Cytoscape.getNodeAttributes();
		urlMap = new HashMap<String, String>();
		formatMap = new HashMap<String, String>();
		initComponents();
		updateComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code">
	private void initComponents() {
		titleLabel = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		previewSplitPane = new javax.swing.JSplitPane();
		nodeKeyScrollPane = new javax.swing.JScrollPane();
		nodeKeyList = new javax.swing.JList();
		previewTableScrollPane = new javax.swing.JScrollPane();
		previewTable = new javax.swing.JTable();
		importButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		helpButton = new javax.swing.JButton();
		basicPanel = new javax.swing.JPanel();
		ontologyComboBox = new javax.swing.JComboBox();
		ontologyLabel = new javax.swing.JLabel();
		sourceLabel = new javax.swing.JLabel();
		sourceComboBox = new javax.swing.JComboBox();
		browseButton = new javax.swing.JButton();
		speciesCheckBox = new javax.swing.JCheckBox();
		advancedPanel = new javax.swing.JPanel();
		nodeKeyLabel = new javax.swing.JLabel();
		primaryKeyLabel = new javax.swing.JLabel();
		nodeKeyComboBox = new javax.swing.JComboBox();
		primaryKeyComboBox = new javax.swing.JComboBox();
		aliasLabel = new javax.swing.JLabel();
		aliasScrollPane = new javax.swing.JScrollPane();
		aliasList = new javax.swing.JList();
		ontologyColumnLabel = new javax.swing.JLabel();
		ontologyColumnComboBox = new javax.swing.JComboBox();
		advancedCheckBox = new javax.swing.JCheckBox();

		model = new DefaultTableModel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Import Annotation File");
		titleLabel.setFont(new java.awt.Font("SansSerif", 1, 14));
		titleLabel.setText("Import Annotation for Ontology");

		jSeparator1.setForeground(new java.awt.Color(0, 0, 255));

		previewSplitPane.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Preview"));
		previewSplitPane.setDividerLocation(150);
		previewSplitPane.setDividerSize(5);
		nodeKeyScrollPane.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Keys"));
		nodeKeyList.setForeground(new java.awt.Color(255, 0, 51));

		nodeKeyScrollPane.setViewportView(nodeKeyList);

		previewSplitPane.setLeftComponent(nodeKeyScrollPane);

		previewTableScrollPane
				.setBorder(javax.swing.BorderFactory
						.createTitledBorder("Entries in annotation (first 100 entries)"));

		previewTableScrollPane.setViewportView(previewTable);

		previewSplitPane.setRightComponent(previewTableScrollPane);

		importButton.setText("Import");
		importButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					importButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		helpButton.setBackground(new java.awt.Color(255, 255, 255));
		helpButton.setText("?");
		helpButton.setToolTipText("Need Help?");
		helpButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
		helpButton.setPreferredSize(new java.awt.Dimension(14, 14));

		basicPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Basic Parameters"));

		ontologyComboBox.setModel(new javax.swing.DefaultComboBoxModel());
		ontologyComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ontologyComboBoxActionPerformed(evt);
			}
		});

		ontologyLabel.setText("Ontology");

		sourceLabel.setText("Source");

		sourceComboBox.setModel(new DefaultComboBoxModel());
		sourceComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					sourceComboBoxActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		browseButton.setText("Browse");
		browseButton.setToolTipText("Use local annotation data file.");
		browseButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					browseButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		speciesCheckBox.setForeground(SPECIES_COLOR);
		speciesCheckBox.setSelected(true);
		speciesCheckBox
				.setText("Transfer species name? (for Gene Association only)");
		speciesCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(
				0, 0, 0, 0));
		speciesCheckBox.setEnabled(false);
		speciesCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		speciesCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				speciesCheckBoxActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout basicPanelLayout = new org.jdesktop.layout.GroupLayout(
				basicPanel);
		basicPanel.setLayout(basicPanelLayout);
		basicPanelLayout
				.setHorizontalGroup(basicPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								basicPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												basicPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																basicPanelLayout
																		.createSequentialGroup()
																		.add(
																				basicPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								ontologyLabel)
																						.add(
																								sourceLabel))
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				basicPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								basicPanelLayout
																										.createSequentialGroup()
																										.add(
																												sourceComboBox,
																												0,
																												468,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												org.jdesktop.layout.LayoutStyle.RELATED)
																										.add(
																												browseButton))
																						.add(
																								ontologyComboBox,
																								0,
																								554,
																								Short.MAX_VALUE)))
														.add(speciesCheckBox))
										.addContainerGap()));
		basicPanelLayout.setVerticalGroup(basicPanelLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				basicPanelLayout.createSequentialGroup().add(
						basicPanelLayout.createParallelGroup(
								org.jdesktop.layout.GroupLayout.BASELINE).add(
								ontologyComboBox,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(ontologyLabel)).addPreferredGap(
						org.jdesktop.layout.LayoutStyle.RELATED).add(
						basicPanelLayout.createParallelGroup(
								org.jdesktop.layout.GroupLayout.BASELINE).add(
								browseButton).add(sourceComboBox,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(sourceLabel)).addPreferredGap(
						org.jdesktop.layout.LayoutStyle.RELATED).add(
						speciesCheckBox).addContainerGap(13, Short.MAX_VALUE)));

		advancedPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Advanced"));
		advancedPanel.setEnabled(false);
		nodeKeyLabel.setForeground(KEY_ATTR_COLOR);
		nodeKeyLabel.setText("Key Attribute");

		primaryKeyLabel.setForeground(PRIMARY_KEY_COLOR);
		primaryKeyLabel.setText("Primary Key in Attribute");

		nodeKeyComboBox.setForeground(new java.awt.Color(255, 0, 51));
		nodeKeyComboBox.setModel(new DefaultComboBoxModel());
		nodeKeyComboBox.setEnabled(false);
		nodeKeyComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nodeKeyComboBoxActionPerformed(evt);
			}
		});

		primaryKeyComboBox.setForeground(new java.awt.Color(51, 51, 255));
		primaryKeyComboBox.setModel(new DefaultComboBoxModel());
		primaryKeyComboBox.setEnabled(false);
		primaryKeyComboBox
				.setToolTipText("Select a column which will be used as the primary key for mapping.");
		primaryKeyComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				primaryKeyComboBoxActionPerformed(evt);
			}
		});

		aliasLabel.setForeground(ALIAS_COLOR);
		aliasLabel.setText("Aliases");

		aliasList.setForeground(ALIAS_COLOR);
		aliasList.setEnabled(false);
		aliasList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						aliasListValueChanged(evt);
					}
				});

		aliasScrollPane.setViewportView(aliasList);

		ontologyColumnLabel.setForeground(ONTOLOGY_COLOR);
		ontologyColumnLabel.setText("Ontology ID Column");

		ontologyColumnComboBox.setForeground(ONTOLOGY_COLOR);
		ontologyColumnComboBox.setEnabled(false);
		ontologyColumnComboBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						ontologyColumnComboBoxActionPerformed(evt);
					}
				});

		advancedCheckBox.setText("Enable Advanced Options");
		advancedCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		advancedCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		advancedCheckBox.setEnabled(false);
		advancedCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				advancedCheckBoxActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout advancedPanelLayout = new org.jdesktop.layout.GroupLayout(
				advancedPanel);
		advancedPanel.setLayout(advancedPanelLayout);
		advancedPanelLayout
				.setHorizontalGroup(advancedPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								advancedPanelLayout
										.createSequentialGroup()
										.add(
												advancedPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																advancedPanelLayout
																		.createSequentialGroup()
																		.add(
																				advancedPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								advancedPanelLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.add(
																												primaryKeyLabel))
																						.add(
																								advancedPanelLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.add(
																												nodeKeyLabel))
																						.add(
																								advancedPanelLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.add(
																												aliasLabel))
																						.add(
																								advancedPanelLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.add(
																												ontologyColumnLabel)))
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				advancedPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.TRAILING)
																						.add(
																								org.jdesktop.layout.GroupLayout.LEADING,
																								primaryKeyComboBox,
																								0,
																								476,
																								Short.MAX_VALUE)
																						.add(
																								ontologyColumnComboBox,
																								0,
																								476,
																								Short.MAX_VALUE)
																						.add(
																								aliasScrollPane,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								476,
																								Short.MAX_VALUE)
																						.add(
																								nodeKeyComboBox,
																								0,
																								0,
																								Short.MAX_VALUE)))
														.add(
																advancedPanelLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				advancedCheckBox)))
										.addContainerGap()));
		advancedPanelLayout
				.setVerticalGroup(advancedPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								advancedPanelLayout
										.createSequentialGroup()
										.add(advancedCheckBox)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(nodeKeyLabel)
														.add(
																nodeKeyComboBox,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																primaryKeyComboBox,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(primaryKeyLabel))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																aliasScrollPane,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																54,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(aliasLabel))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																ontologyColumnComboBox,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(
																ontologyColumnLabel))
										.addContainerGap()));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				previewSplitPane,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				672,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				jSeparator1,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				672,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				titleLabel)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED,
																				415,
																				Short.MAX_VALUE)
																		.add(
																				helpButton,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.add(
																				23,
																				23,
																				23))
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				basicPanel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																layout
																		.createSequentialGroup()
																		.add(
																				importButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				cancelButton)
																		.addContainerGap())
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				advancedPanel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addContainerGap()))));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(titleLabel)
														.add(
																helpButton,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																20,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												jSeparator1,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												basicPanel,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedPanel,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												previewSplitPane,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												206, Short.MAX_VALUE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(cancelButton).add(
																importButton))
										.addContainerGap()));
		pack();
	}// </editor-fold>

	private void speciesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void advancedCheckBoxActionPerformed(ActionEvent evt) {
		if (advancedCheckBox.isSelected()) {
			primaryKeyComboBox.setEnabled(true);
			nodeKeyComboBox.setEnabled(true);
			ontologyColumnComboBox.setEnabled(true);
			aliasList.setEnabled(true);

			// Get current values
			primaryKey = primaryKeyComboBox.getSelectedIndex();
			ontologyColumn = ontologyColumnComboBox.getSelectedIndex();
			aliases = aliasList.getSelectedIndices();
		} else {
			primaryKeyComboBox.setEnabled(false);
			nodeKeyComboBox.setEnabled(false);
			ontologyColumnComboBox.setEnabled(false);
			aliasList.setEnabled(false);

			// Restore default values
			primaryKey = PRIMARY_KEY;
			ontologyColumn = ONTOLOGY_COL;
			aliases = new int[1];
			aliases[0] = ALIAS;
		}
		updatePreviewTable();
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		dispose();
	}

	/**
	 * Start importing annotation data.
	 * 
	 * @param evt
	 * @throws IOException
	 */
	private void importButtonActionPerformed(ActionEvent evt)
			throws IOException {

		/*
		 * Cannot execute this command if ontology is not selected.
		 */
		if (ontologyComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "Ontology is not selected.",
					"Error!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		TextTableReader reader = null;
		final String ontologyName = (String) ontologyComboBox.getSelectedItem();
		final String dataSourceString = (String) urlMap.get(this.sourceComboBox
				.getSelectedItem());

//		if (advancedCheckBox.isSelected() == false) {
//			/*
//			 * Import as normal Gene Association file.
//			 */
//			reader = new GeneAssociationReader(ontologyName, new URL(
//					dataSourceString), false, TAB, ID);
//		} else {
//			/*
//			 * Check user selection.
//			 */
//			final String keyAttribute = nodeKeyComboBox.getSelectedItem()
//					.toString();
//
//			if ((!keyAttribute.equals(ID)) && primaryKey == PRIMARY_KEY
//					&& ontologyColumn == ONTOLOGY_COL && aliases.length == 1
//					&& aliases[0] == ALIAS) {
//				
//				reader = new GeneAssociationReader(ontologyName, new URL(
//						dataSourceString), false, TAB, keyAttribute);
//			}
			/*
			 * Import GA file using selected attribute
			 */

//		}
//		loadAnnotation(reader, ontologyName, dataSourceString);
		dispose();
	}

	private void aliasListValueChanged(ListSelectionEvent evt) {
		aliases = aliasList.getSelectedIndices();
		updatePreviewTable();
	}

	private void ontologyColumnComboBoxActionPerformed(ActionEvent evt) {
		ontologyColumn = ontologyColumnComboBox.getSelectedIndex();
		updatePreviewTable();
	}

	private void primaryKeyComboBoxActionPerformed(ActionEvent evt) {
		primaryKey = primaryKeyComboBox.getSelectedIndex();
		updatePreviewTable();
	}

	private void updatePreviewTable() {
		previewTable.setDefaultRenderer(Object.class,
				new AnnotationPreviewTableCellRenderer(primaryKey, aliases,
						ontologyColumn, species));
		previewTable.setModel(model);
		previewTable.repaint();
	}

	private void nodeKeyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
		setKeyList();
	}

	private void browseButtonActionPerformed(ActionEvent evt)
			throws IOException {
		final File localFile = FileUtil.getFile(
				"Select local annotation file...", FileUtil.LOAD);

		if (localFile == null) {
			return;
		}

		final String key = "Local annotation file: " + localFile.getName();
		sourceComboBox.addItem(key);

		urlMap.put(key, localFile.toURL().toString());
		if (localFile.getName().startsWith(GENE_ASSOCIATION)) {
			formatMap.put(key, "Gene Association");
		} else {
			formatMap.put(key, "Unknown");
		}

		sourceComboBox.setSelectedItem(key);
	}

	private void sourceComboBoxActionPerformed(java.awt.event.ActionEvent evt)
			throws IOException {
		

		readAnnotationForPreview();

		// Enable advanced check box.
		advancedCheckBox.setEnabled(true);
		setNodeKeyComboBox();
		setPrimaryKeyComboBox();
		setAliasList();
		setOntologyColumnComboBox();
		
		sourceComboBox.setToolTipText("Source URL: "
				+ urlMap.get(sourceComboBox.getSelectedItem()));
		if (formatMap.get(sourceComboBox.getSelectedItem()).equals(
				"Gene Association")) {
			speciesCheckBox.setEnabled(true);
			ontologyColumnComboBox.setSelectedIndex(ONTOLOGY_COL);
			primaryKeyComboBox.setSelectedIndex(PRIMARY_KEY);
			aliasList.setSelectedIndex(ALIAS);
		} else {
			speciesCheckBox.setEnabled(false);
		}
	}

	private void ontologyComboBoxActionPerformed(ActionEvent evt) {

	}

	private void updateComponents() {

		setOntologyComboBox();
		setSourceComboBox();

		setNodeKeyComboBox();

		setKeyList();
	}

	private Map getOntologies() {
		return Cytoscape.getOntologyServer().getOntologies();
	}

	private void setOntologyComboBox() {
		for (Object ontologyName : getOntologies().keySet()) {
			ontologyComboBox.addItem(ontologyName);
		}
	}

	private void setSourceComboBox() {

		bookmarks = Cytoscape.getOntologyServer().getBookmarks();
		List<DataSource> annotations = BookmarksUtil.getDataSourceList(
				"annotation", bookmarks.getCategory());
		String key = null;
		for (DataSource source : annotations) {
			key = source.getName() + " (Curator: "
					+ BookmarksUtil.getAttribute(source, "curator") + ")";
			sourceComboBox.addItem(key);
			urlMap.put(key, source.getHref());
			formatMap.put(key, source.getFormat());
		}

		sourceComboBox.setToolTipText("Source URL: "
				+ urlMap.get(sourceComboBox.getSelectedItem()));

		if (formatMap.get(sourceComboBox.getSelectedItem()).equals(
				"Gene Association")) {
			speciesCheckBox.setEnabled(true);
		} else {
			speciesCheckBox.setEnabled(false);
		}

	}

	private void setPrimaryKeyComboBox() {
		if (model != null) {
			primaryKeyComboBox.removeAllItems();

			for (int i = 0; i < model.getColumnCount(); i++) {
				primaryKeyComboBox.addItem("Column " + (i + 1));
			}
		}
	}

	private void setNodeKeyComboBox() {
		String[] nodeAttributeNames = Cytoscape.getNodeAttributes()
				.getAttributeNames();
		nodeKeyComboBox.removeAllItems();

		nodeKeyComboBox.addItem(ID);
		for (String name : nodeAttributeNames) {
			if (nodeAttributes.getType(name) == nodeAttributes.TYPE_STRING
					|| nodeAttributes.getType(name) == nodeAttributes.TYPE_FLOATING) {
				nodeKeyComboBox.addItem(name);
			}
		}
	}

	private void setAliasList() {
		if (model != null) {
			final String[] items = new String[model.getColumnCount()];
			for (int i = 0; i < model.getColumnCount(); i++) {
				items[i] = "Column " + (i + 1);
			}
			aliasList.setListData(items);
		}
	}

	private void setOntologyColumnComboBox() {
		if (model != null) {
			ontologyColumnComboBox.removeAllItems();
			for (int i = 0; i < model.getColumnCount(); i++) {
				ontologyColumnComboBox.addItem("Column " + (i + 1));
			}
		}
	}

	private void setKeyList() {

		String key = nodeKeyComboBox.getSelectedItem().toString();
		List<Node> nodeList = Cytoscape.getCyNodesList();
		Set<String> valueSet = new TreeSet<String>();
		if (key.equals(ID)) {

			final Set<CyNetwork> networkSet = Cytoscape.getNetworkSet();
			for (CyNetwork network : networkSet) {
				// Try to get isOntology Attribute
				Boolean isOntology = Cytoscape.getNetworkAttributes()
						.getBooleanAttribute(network.getIdentifier(),
								Ontology.IS_ONTOLOGY);
				if (isOntology == null || isOntology == false) {
					Iterator nodeIt = network.nodesIterator();
					while (nodeIt.hasNext()) {
						Node node = (Node) nodeIt.next();
						valueSet.add(node.getIdentifier());
					}
				}
			}

		} else {
			Object value = null;
			for (Node node : nodeList) {
				value = nodeAttributes.getStringAttribute(node.getIdentifier(),
						key);
				if (value != null) {
					valueSet.add(value.toString());
				}
			}
		}

		nodeKeyList.setListData(valueSet.toArray());
	}

	private void readAnnotationForPreview() throws IOException {
		final String selectedSourceName = sourceComboBox.getSelectedItem().toString();
		final URL sourceURL = new URL(urlMap.get(selectedSourceName));
		final BufferedReader bufRd = new BufferedReader(new InputStreamReader(
				URLUtil.getInputStream(sourceURL)));
		String line;

		String[] columnNames = new String[15];
		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = "C" + (i + 1);
		}
		model = new DefaultTableModel(columnNames, 0);
		/*
		 * Read & extract one line at a time. The line can be Tab delimited,
		 */
		int counter = 0;
		String[] parts;
		while ((line = bufRd.readLine()) != null && counter < 100) {

			if (line.startsWith("!") || line.trim().length() == 0) {
				// ignore
			} else {
				parts = line.split("\\t");
				model.addRow(parts);
			}
			counter++;
		}
		bufRd.close();

		int[] aliases = new int[1];
		aliases[0] = ALIAS;
		previewTable.setDefaultRenderer(Object.class,
				new AnnotationPreviewTableCellRenderer(primaryKey, aliases,
						ontologyColumn, species));
		previewTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		previewTable.setModel(model);
		previewTable.repaint();
		
		
	}

	private void loadAnnotation(TextTableReader reader, String ontology,
			String source) {
		// Create LoadNetwork Task
		ImportAnnotationTask task = new ImportAnnotationTask(reader, ontology,
				source);

		// Configure JTask Dialog Pop-Up Box
		JTaskConfig jTaskConfig = new JTaskConfig();
		jTaskConfig.setOwner(Cytoscape.getDesktop());
		jTaskConfig.displayCloseButton(true);
		jTaskConfig.displayStatus(true);
		jTaskConfig.setAutoDispose(false);

		// Execute Task in New Thread; pops open JTask Dialog Box.
		TaskManager.executeTask(task, jTaskConfig);
	}

	// Variables declaration - do not modify
	private javax.swing.JCheckBox advancedCheckBox;
	private javax.swing.JPanel advancedPanel;
	private javax.swing.JLabel aliasLabel;
	private javax.swing.JList aliasList;
	private javax.swing.JScrollPane aliasScrollPane;
	private javax.swing.JPanel basicPanel;
	private javax.swing.JButton browseButton;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton helpButton;
	private javax.swing.JButton importButton;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JComboBox nodeKeyComboBox;
	private javax.swing.JLabel nodeKeyLabel;
	private javax.swing.JList nodeKeyList;
	private javax.swing.JScrollPane nodeKeyScrollPane;
	private javax.swing.JComboBox ontologyComboBox;
	private javax.swing.JComboBox ontologyColumnComboBox;
	private javax.swing.JLabel ontologyColumnLabel;
	private javax.swing.JLabel ontologyLabel;
	private javax.swing.JSplitPane previewSplitPane;
	private javax.swing.JTable previewTable;
	private javax.swing.JScrollPane previewTableScrollPane;
	private javax.swing.JLabel primaryKeyLabel;
	private javax.swing.JComboBox primaryKeyComboBox;
	private javax.swing.JComboBox sourceComboBox;
	private javax.swing.JLabel sourceLabel;
	private javax.swing.JCheckBox speciesCheckBox;
	private javax.swing.JLabel titleLabel;
	// End of variables declaration
	private DefaultTableModel model;
}

/*
 * Cell renderer for preview table
 * 
 */
class AnnotationPreviewTableCellRenderer extends JLabel implements
		TableCellRenderer {
	private static final Font selectedFont = new Font("Sans-serif", Font.BOLD,
			12);

	private int primaryKey;
	private List<Integer> aliases;
	private int ontologyColumn;
	private int species;

	public AnnotationPreviewTableCellRenderer(int primaryKey, int[] aliases,
			int ontologyColumn) {
		this(primaryKey, aliases, ontologyColumn, -1);
	}

	public AnnotationPreviewTableCellRenderer(int primaryKey, int[] aliases,
			int ontologyColumn, int species) {
		super();
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.primaryKey = primaryKey;
		this.ontologyColumn = ontologyColumn;
		this.aliases = new ArrayList<Integer>();
		for (Integer alias : aliases) {
			this.aliases.add(alias);
		}
		this.species = species;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// setForeground(table.getForeground());

		if (column == primaryKey) {
			setForeground(ImportAnnotationDialog.PRIMARY_KEY_COLOR);
			setFont(selectedFont);

			// super.setBackground(Color.RED);
		} else if (column == ontologyColumn) {
			setForeground(ImportAnnotationDialog.ONTOLOGY_COLOR);
			setFont(selectedFont);
		} else if (aliases.contains(column)) {
			setForeground(ImportAnnotationDialog.ALIAS_COLOR);
			setFont(selectedFont);
		} else if (column == species) {
			setForeground(ImportAnnotationDialog.SPECIES_COLOR);
			setFont(selectedFont);
		} else {
			setForeground(Color.BLACK);
			super.setBackground(table.getBackground());
			setFont(table.getFont());
		}

		setHorizontalAlignment(LEFT);

		if (aliases.contains(column)) {
			String newValue = "";
			String[] synonym = (value.toString()).split("\\|");
			for (int i = 0; i < synonym.length; i++) {
				newValue = newValue + synonym[i];
				if (i != synonym.length - 1) {
					newValue = newValue + ",  ";
				}
			}
			setText((value == null) ? "" : newValue.toString());

		} else {
			setText((value == null) ? "" : value.toString());

		}
		return this;
	}

}

class ImportAnnotationTask implements Task {
	private TextTableReader reader;

	private String ontology;
	private String source;

	private TaskMonitor taskMonitor;

	/**
	 * Constructor.
	 * 
	 * @param file
	 *            File.
	 * @param fileType
	 *            FileType, e.g. Cytoscape.FILE_SIF or Cytoscape.FILE_GML.
	 */

	public ImportAnnotationTask(TextTableReader reader, String ontology,
			String source) {
		this.reader = reader;
		this.ontology = ontology;
		this.source = source;
	}

	/**
	 * Executes Task.
	 */
	public void run() {
		taskMonitor.setStatus("Importing annotation data...");
		taskMonitor.setPercentCompleted(-1);

		try {
			reader.readTable();
			taskMonitor.setPercentCompleted(100);
		} catch (IOException e) {
			taskMonitor.setException(e, "Unable to import annotation data.");
		}

		informUserOfAnnotationStats();
	}

	/**
	 * Inform User of Network Stats.
	 */
	private void informUserOfAnnotationStats() {
		StringBuffer sb = new StringBuffer();

		// Give the user some confirmation
		sb.append("Succesfully loaded annotation data for " + ontology);
		sb.append(" from: \n" + source + "\n");
		sb.append("\n\nAnnotation data source contains ");

		taskMonitor.setStatus(sb.toString());
	}

	/**
	 * Halts the Task: Not Currently Implemented.
	 */
	public void halt() {
		// Task can not currently be halted.
	}

	/**
	 * Sets the Task Monitor.
	 * 
	 * @param taskMonitor
	 *            TaskMonitor Object.
	 */
	public void setTaskMonitor(TaskMonitor taskMonitor)
			throws IllegalThreadStateException {
		this.taskMonitor = taskMonitor;
	}

	/**
	 * Gets the Task Title.
	 * 
	 * @return Task Title.
	 */
	public String getTitle() {
		return new String("Loading Network");
	}

}
