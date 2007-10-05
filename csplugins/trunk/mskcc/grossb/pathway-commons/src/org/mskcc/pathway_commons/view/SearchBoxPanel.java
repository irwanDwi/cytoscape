package org.mskcc.pathway_commons.view;

import org.mskcc.pathway_commons.task.ExecutePhysicalEntitySearch;
import org.mskcc.pathway_commons.web_service.PathwayCommonsWebApi;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import cytoscape.task.ui.JTaskConfig;
import cytoscape.task.util.TaskManager;

/**
 * Search Box Panel.
 *
 * @author Ethan Cerami.
 */
class SearchBoxPanel extends JPanel {
    private JButton searchButton;
    private PathwayCommonsWebApi webApi;

    /**
     * Constructor.
     * @param webApi PathwayCommmons Web API.
     */
    public SearchBoxPanel (PathwayCommonsWebApi webApi) {
        this.webApi = webApi;
        setBorder(new TitledBorder("Search Pathway Commons"));
        BoxLayout boxLayout = new BoxLayout (this, BoxLayout.X_AXIS);
        setLayout(boxLayout);
        final JTextField searchField = createSearchField();
        JComboBox organismComboBox = createOrganismComboBox();
        JButton helpButton = new JButton("Help");
        searchButton = createSearchButton(searchField);

        add(searchField);
        add(Box.createRigidArea(new Dimension(5,0)));
        add(organismComboBox);
        add(Box.createRigidArea(new Dimension(5,0)));
        add(searchButton);
        add(Box.createRigidArea(new Dimension(5,0)));
        add(helpButton);
    }

    /**
     * Creates the Organism Combo Box.
     * @return JComboBox Object.
     */
    private JComboBox createOrganismComboBox() {
        //  Organism List is currently hard-coded.
        Vector organismList = new Vector();
        organismList.add("Human");
        organismList.add("Mouse");
        DefaultComboBoxModel organismComboBoxModel = new DefaultComboBoxModel(organismList);
        JComboBox organismComboBox = new JComboBox(organismComboBoxModel);
        organismComboBox.setMaximumSize(new Dimension(200, 9999));
        organismComboBox.setPrototypeDisplayValue("12345678901234567890");
        return organismComboBox;
    }

    /**
     * Creates the Search Field and associated listener(s)
     * @return JTextField Object.
     */
    private JTextField createSearchField() {
        final JTextField searchField  = new JTextField(20);
        searchField.setText("Enter Protein Name or ID");
        searchField.setMaximumSize(new Dimension(200, 9999));
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent focusEvent) {
                if (searchField.getText() != null
                        && searchField.getText().startsWith("Enter")) {
                    searchField.setText("");
                }
            }
        });
        return searchField;
    }

    /**
     * Creates the Search Button and associated action listener.
     * @param searchField JTextField searchField
     * @return
     */
    private JButton createSearchButton(final JTextField searchField) {
        searchButton = new JButton("Go!");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ExecutePhysicalEntitySearch search = new ExecutePhysicalEntitySearch
                        (webApi, searchField.getText(), -1, 1);
                JTaskConfig jTaskConfig = new JTaskConfig();
                jTaskConfig.setAutoDispose(true);
                jTaskConfig.displayCancelButton(false);
                jTaskConfig.displayCloseButton(false);
                //jTaskConfig.setOwner(Cytoscape.getDesktop());
                TaskManager.executeTask(search, jTaskConfig);
            }
        });
        return searchButton;
    }

    /**
     * Initializes Focus to the Search Button.
     */
    public void initFocus() {
        searchButton.requestFocusInWindow();        
    }
}
