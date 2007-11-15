package cytoscape.filters.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class EditRangeDialog extends JDialog implements ActionListener {

	private Vector<String> boundValueVect;
	
    /** Creates new form EditRangeDialog */
    public EditRangeDialog(Component parent, boolean modal, String pCtrlAttribute, Vector<String> pBoundsVect) {
        //super(parent, modal);
        initComponents();
        setTitle("Edit range for "+ pCtrlAttribute);
        
        String message = lbPleaseEnterBoundValues.getText()+ " (" + pBoundsVect.elementAt(2).toString()+ "~" +pBoundsVect.elementAt(3).toString() + "):";
        lbPleaseEnterBoundValues.setText(message);
        
        boundValueVect = pBoundsVect;
		tfLowBound.setText(pBoundsVect.elementAt(0).toString());
		tfHighBound.setText(pBoundsVect.elementAt(1).toString());

		tfLowBound.setSelectionStart(0);
		tfLowBound.setSelectionEnd(pBoundsVect.elementAt(0).toString().length());

		setSize(new java.awt.Dimension(300, 170));
		
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);

    }
    
    private boolean isUserInputValid() {
    
    	// User inputs must be (1) numbers (2) lowBound < highBound 
    	String lowBoundStr = tfLowBound.getText().trim();
    	String highBoundStr = tfHighBound.getText().trim();
    	

    	
    	return true;
    }
    
	/**
	 *  DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		Object _actionObject = e.getSource();

		// handle Button events
		if (_actionObject instanceof JButton) {
			JButton _btn = (JButton) _actionObject;

			if (_btn == btnOK) {
				if (!isUserInputValid()) {
					JOptionPane.showMessageDialog(this, "Invalid bound values", "Warning", JOptionPane.ERROR_MESSAGE);
					return;
				}
				boundValueVect.set(0, tfLowBound.getText().trim());
				boundValueVect.set(1, tfHighBound.getText().trim());
			} 
			//else if (_btn == btnCancel) {
			//}
		}
		this.dispose();
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lbPleaseEnterBoundValues = new javax.swing.JLabel();
        lbLowBound = new javax.swing.JLabel();
        tfLowBound = new javax.swing.JTextField();
        lbHighBound = new javax.swing.JLabel();
        tfHighBound = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lbPleaseEnterBoundValues.setText("Please enter the bound values");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 0);
        getContentPane().add(lbPleaseEnterBoundValues, gridBagConstraints);

        lbLowBound.setText("Low bound");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 10);
        getContentPane().add(lbLowBound, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 20);
        getContentPane().add(tfLowBound, gridBagConstraints);

        lbHighBound.setText("High bound");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 10);
        getContentPane().add(lbHighBound, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        getContentPane().add(tfHighBound, gridBagConstraints);

        btnOK.setText("OK");
        jPanel1.add(btnOK);

        btnCancel.setText("Cancel");
        jPanel1.add(btnCancel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>                        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Vector<String> _boundsVect = new Vector<String>();
            	_boundsVect.add("0.51");
            	_boundsVect.add("1.08");
            	_boundsVect.add("-0.1");
            	_boundsVect.add("2.0");
            	
            	EditRangeDialog theDialog =new EditRangeDialog(new javax.swing.JFrame(), true, "Degree", _boundsVect);
            	
                theDialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbHighBound;
    private javax.swing.JLabel lbLowBound;
    private javax.swing.JLabel lbPleaseEnterBoundValues;
    private javax.swing.JTextField tfHighBound;
    private javax.swing.JTextField tfLowBound;
    // End of variables declaration                   

	
	
	
}
