package org.mskcc.pathway_commons.view;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Summary Panel.
 *
 * @author Ethan Cerami.
 */
public class SummaryPanel extends JPanel {
    private Document doc;
    private JTextPane textPane;

    /**
     * Constructor.
     */
    public SummaryPanel() {
        this.setLayout(new BorderLayout());
        this.setBorder(GuiUtils.createTitledBorder("Gene Summary"));
        textPane = createTextPane();
        doc = textPane.getDocument();
        JScrollPane scrollPane = encloseInJScrollPane (textPane);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setAlpha(0.0f);
    }

    /**
     * Sets the Alpha Channel.
     * @param v alpha channel.
     */
    public void setAlpha(float v) {
        //super.setAlpha(v);
        repaint();
    }

    /**
     * Gets the summary document model.
     * @return Document object.
     */
    public Document getDocument() {
        return doc;
    }

    /**
     * Gets the summary text pane object.
     * @return JTextPane Object.
     */
    public JTextPane getTextPane() {
        return textPane;
    }

    /**
     * Encloses the specified JTextPane in a JScrollPane.
     *
     * @param textPane JTextPane Object.
     * @return JScrollPane Object.
     */
    private JScrollPane encloseInJScrollPane(JTextPane textPane) {
        JScrollPane scrollPane = new JScrollPane(textPane);
        return scrollPane;
    }

    /**
     * Creates a JTextPane with correct line wrap settings.
     *
     * @return JTextPane Object.
     */
    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBorder(new EmptyBorder(7,7,7,7));
        return textPane;
    }
}
