/*
 * @(#)ToggleButtonTest.java  1.0  13 February 2005
 *
 * Copyright (c) 2004 Werner Randelshofer
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
 * ToggleButtonTest.
 *
 * @author  Werner Randelshofer
 * @version 1.0  13 February 2005  Created.
 */
public class ToggleButtonTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public ToggleButtonTest() {
        initComponents();
        toggle3.putClientProperty("Quaqua.Button.style","toggleWest");
        toggle4.putClientProperty("Quaqua.Button.style","toggleCenter");
        toggle5.putClientProperty("Quaqua.Button.style","toggleEast");
        toggle6.putClientProperty("Quaqua.Button.style","toggleWest");
        toggle7.putClientProperty("Quaqua.Button.style","toggleCenter");
        toggle8.putClientProperty("Quaqua.Button.style","toggleEast");
        /*
        toggle3.setMargin(new Insets(0,0,0,0));
        toggle4.setMargin(new Insets(0,0,0,0));
        toggle5.setMargin(new Insets(0,0,0,0));
        toggle6.setMargin(new Insets(0,0,0,0));
        toggle7.setMargin(new Insets(0,0,0,0));
        toggle8.setMargin(new Insets(0,0,0,0));
         */
        // Leopard properties
        if (QuaquaManager.getDesign() == QuaquaManager.LEOPARD) {
            toggle1.putClientProperty("JButton.buttonType", "segmented");
            toggle2.putClientProperty("JButton.buttonType", "segmented");
            toggle3.putClientProperty("JButton.buttonType", "segmented");
            toggle4.putClientProperty("JButton.buttonType", "segmented");
            toggle5.putClientProperty("JButton.buttonType", "segmented");
            toggle6.putClientProperty("JButton.buttonType", "segmented");
            toggle7.putClientProperty("JButton.buttonType", "segmented");
            toggle8.putClientProperty("JButton.buttonType", "segmented");
            toggle9.putClientProperty("JButton.buttonType", "segmented");
            toggle10.putClientProperty("JButton.buttonType", "segmented");
            toggle1.putClientProperty("JButton.segmentPosition","only");
            toggle2.putClientProperty("JButton.segmentPosition","only");
            toggle3.putClientProperty("JButton.segmentPosition","first");
            toggle4.putClientProperty("JButton.segmentPosition","middle");
            toggle5.putClientProperty("JButton.segmentPosition","last");
            toggle6.putClientProperty("JButton.segmentPosition","first");
            toggle7.putClientProperty("JButton.segmentPosition","middle");
            toggle8.putClientProperty("JButton.segmentPosition","last");
            toggle6.putClientProperty("JComponent.sizeVariant","small");
            toggle7.putClientProperty("JComponent.sizeVariant","small");
            toggle8.putClientProperty("JComponent.sizeVariant","small");
            toggle9.putClientProperty("JButton.segmentPosition","only");
            toggle10.putClientProperty("JButton.segmentPosition","only");
        }
    }
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("Quaqua Toggle Button Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ToggleButtonTest());
        ((JComponent) f.getContentPane()).setBorder(new EmptyBorder(9,17,17,17));
        f.pack();
        f.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        toggle1 = new javax.swing.JToggleButton();
        toggle2 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        toggle3 = new javax.swing.JToggleButton();
        toggle4 = new javax.swing.JToggleButton();
        toggle5 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        toggle9 = new javax.swing.JToggleButton();
        toggle10 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        toggle6 = new javax.swing.JToggleButton();
        toggle7 = new javax.swing.JToggleButton();
        toggle8 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(toggle1);
        toggle1.setSelected(true);
        toggle1.setText("\u00c5ngstr\u00f6m H");
        toggle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggle1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(toggle1, gridBagConstraints);

        buttonGroup1.add(toggle2);
        toggle2.setText("\u00c5ngstr\u00f6m H");
        toggle2.setEnabled(false);
        toggle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggle2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(toggle2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        buttonGroup2.add(toggle3);
        toggle3.setText("West");
        toggle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggle3ActionPerformed(evt);
            }
        });

        jPanel2.add(toggle3);

        buttonGroup2.add(toggle4);
        toggle4.setText("Center");
        jPanel2.add(toggle4);

        buttonGroup2.add(toggle5);
        toggle5.setText("East");
        jPanel2.add(toggle5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(jPanel2, gridBagConstraints);

        jLabel1.setText("Regular");
        add(jLabel1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        add(jSeparator1, gridBagConstraints);

        buttonGroup1.add(toggle9);
        toggle9.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        toggle9.setSelected(true);
        toggle9.setText("\u00c5ngstr\u00f6m H");
        toggle9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggle9ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(toggle9, gridBagConstraints);

        buttonGroup1.add(toggle10);
        toggle10.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        toggle10.setText("\u00c5ngstr\u00f6m H");
        toggle10.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(toggle10, gridBagConstraints);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        buttonGroup2.add(toggle6);
        toggle6.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        toggle6.setText("West");
        jPanel4.add(toggle6);

        buttonGroup2.add(toggle7);
        toggle7.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        toggle7.setText("Center");
        jPanel4.add(toggle7);

        buttonGroup2.add(toggle8);
        toggle8.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        toggle8.setText("East");
        jPanel4.add(toggle8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(jPanel4, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 11));
        jLabel2.setText("Small");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        add(jLabel2, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    private void toggle9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggle9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggle9ActionPerformed
    
    private void toggle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggle1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggle1ActionPerformed
    
    private void toggle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggle2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggle2ActionPerformed
    
    private void toggle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggle3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggle3ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton toggle1;
    private javax.swing.JToggleButton toggle10;
    private javax.swing.JToggleButton toggle2;
    private javax.swing.JToggleButton toggle3;
    private javax.swing.JToggleButton toggle4;
    private javax.swing.JToggleButton toggle5;
    private javax.swing.JToggleButton toggle6;
    private javax.swing.JToggleButton toggle7;
    private javax.swing.JToggleButton toggle8;
    private javax.swing.JToggleButton toggle9;
    // End of variables declaration//GEN-END:variables
    
}
