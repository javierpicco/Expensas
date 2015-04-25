/*
 * @(#)InputVerifierTest.java  1.0  August 1, 2007
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Staldenmattweg 2, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package test;

import java.awt.*;
import javax.swing.*;

/**
 * InputVerifierTest.
 *
 * @author Werner Randelshofer
 * @version 1.0 August 1, 2007 Created.
 */
public class InputVerifierTest extends javax.swing.JPanel {
    
    /** Creates new instance. */
    public InputVerifierTest() {
        initComponents();
        
        passField.setMinimumSize(passField.getPreferredSize());
        passField.setInputVerifier(new TextFieldVerifier("pass"));
        
        emptyField.setMinimumSize(emptyField.getPreferredSize());
        emptyField.setInputVerifier(new TextFieldVerifier(""));

        checkedBox.setInputVerifier(new CheckBoxVerifier(true));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        emptyField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        checkedBox = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("<html>The following components are guarded by InputVerifiers.You can only move focus out of the components, if their input is valid.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Enter \"pass\":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(jLabel2, gridBagConstraints);

        passField.setColumns(9);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(passField, gridBagConstraints);

        jLabel3.setText("Leave empty:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(jLabel3, gridBagConstraints);

        emptyField.setColumns(9);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(emptyField, gridBagConstraints);

        jLabel4.setText("Check the box:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(jLabel4, gridBagConstraints);

        checkedBox.setText("Checkbox");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        add(checkedBox, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkedBox;
    private javax.swing.JTextField emptyField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField passField;
    // End of variables declaration//GEN-END:variables
    
    private static class TextFieldVerifier extends InputVerifier {
        private String validText;
        
        public TextFieldVerifier(String validText) {
            this.validText = validText;
        }
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            return validText.equals(tf.getText());
        }
    }
    private static class CheckBoxVerifier extends InputVerifier {
        private boolean validSelection;
        
        public CheckBoxVerifier(boolean validSelection) {
            this.validSelection = validSelection;
        }
        public boolean verify(JComponent input) {
            //System.out.println(this+".verify");
            JCheckBox cb = (JCheckBox) input;
            return cb.isSelected() == validSelection;
        }
    }
}
