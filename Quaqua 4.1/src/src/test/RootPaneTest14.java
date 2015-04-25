/*
 * @(#)RootPaneTest14.java  1.0  June 29, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package test;

import ch.randelshofer.quaqua.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * /**
 * RootPaneTest14.
 * 
 * @author Werner Randelshofer
 * @version 1.0 June 29, 2005 Created.
 */
public class RootPaneTest14 extends javax.swing.JPanel {
    
    /**
     * Creates a new instance.
     */
    public RootPaneTest14() {
        initComponents();
    }
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("Quaqua RootPane Test 2");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RootPaneTest14 p = new RootPaneTest14();
        f.getRootPane().putClientProperty("windowModified", Boolean.TRUE);
        f.getRootPane().setDefaultButton(p.defaultButton);
        f.getContentPane().add(p);
        ((JComponent) f.getContentPane()).setBorder(new EmptyBorder(9,17,17,17));
        f.pack();
        f.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        textField = new javax.swing.JTextField();
        formattedTextField = new javax.swing.JFormattedTextField();
        defaultButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        textField.setColumns(15);
        textField.setText("normal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(textField, gridBagConstraints);

        formattedTextField.setColumns(15);
        formattedTextField.setText("formatted");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(formattedTextField, gridBagConstraints);

        defaultButton.setText("Default Button");
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(defaultButton, gridBagConstraints);

    }//GEN-END:initComponents

    private void defaultButtonPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultButtonPerformed
        // TODO add your handling code here:
        textField.setText(Long.toString(System.currentTimeMillis()));
    }//GEN-LAST:event_defaultButtonPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton defaultButton;
    private javax.swing.JFormattedTextField formattedTextField;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables
    
}
