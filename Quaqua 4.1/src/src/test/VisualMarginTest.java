/*
 * @(#)VisualMarginTest.java  1.0  07 April 2005
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

import java.awt.*;
/**
 * VisualMarginTest.
 *
 * @author  Werner Randelshofer
 * @version 1.0  07 April 2005  Created.
 */
public class VisualMarginTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public VisualMarginTest() {
        initComponents();
        jTextField1.putClientProperty("Quaqua.Component.visualMargin",new Insets(3,3,3,3));
        jTextField2.putClientProperty("Quaqua.Component.visualMargin",new Insets(3,3,3,3));
        jTextField3.putClientProperty("Quaqua.Component.visualMargin",new Insets(3,3,3,3));
        jTextField4.putClientProperty("Quaqua.Component.visualMargin",new Insets(2,2,2,2));
        jTextField5.putClientProperty("Quaqua.Component.visualMargin",new Insets(2,2,2,2));
        jTextField6.putClientProperty("Quaqua.Component.visualMargin",new Insets(2,2,2,2));
        jTextField7.putClientProperty("Quaqua.Component.visualMargin",new Insets(1,1,1,1));
        jTextField8.putClientProperty("Quaqua.Component.visualMargin",new Insets(1,1,1,1));
        jTextField9.putClientProperty("Quaqua.Component.visualMargin",new Insets(1,1,1,1));
        jTextField10.putClientProperty("Quaqua.Component.visualMargin",new Insets(0,0,0,0));
        jTextField11.putClientProperty("Quaqua.Component.visualMargin",new Insets(0,0,0,0));
        jTextField12.putClientProperty("Quaqua.Component.visualMargin",new Insets(0,0,0,0));
        jLabel1.putClientProperty("Quaqua.Component.visualMargin",new Insets(3,3,3,3));
        jLabel2.putClientProperty("Quaqua.Component.visualMargin",new Insets(2,2,2,2));
        jLabel3.putClientProperty("Quaqua.Component.visualMargin",new Insets(1,1,1,1));
        jLabel4.putClientProperty("Quaqua.Component.visualMargin",new Insets(0,0,0,0));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        jTextField1.setText("\u00c5ngstr\u00f6m H");
        jPanel1.add(jTextField1);

        jTextField2.setText("\u00c5ngstr\u00f6m H");
        jPanel1.add(jTextField2);

        jTextField3.setText("\u00c5ngstr\u00f6m H");
        jPanel1.add(jTextField3);

        add(jPanel1, new java.awt.GridBagConstraints());

        jLabel1.setText("Visual Margin 3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel1, gridBagConstraints);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        jTextField4.setText("\u00c5ngstr\u00f6m H");
        jPanel2.add(jTextField4);

        jTextField5.setText("\u00c5ngstr\u00f6m H");
        jPanel2.add(jTextField5);

        jTextField6.setText("\u00c5ngstr\u00f6m H");
        jPanel2.add(jTextField6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        add(jPanel2, gridBagConstraints);

        jLabel2.setText("Visual Margin 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel2, gridBagConstraints);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        jTextField7.setText("\u00c5ngstr\u00f6m H");
        jPanel3.add(jTextField7);

        jTextField8.setText("\u00c5ngstr\u00f6m H");
        jPanel3.add(jTextField8);

        jTextField9.setText("\u00c5ngstr\u00f6m H");
        jPanel3.add(jTextField9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        add(jPanel3, gridBagConstraints);

        jLabel3.setText("Visual Margin 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel3, gridBagConstraints);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jTextField10.setText("\u00c5ngstr\u00f6m H");
        jPanel4.add(jTextField10);

        jTextField11.setText("\u00c5ngstr\u00f6m H");
        jPanel4.add(jTextField11);

        jTextField12.setText("\u00c5ngstr\u00f6m H");
        jPanel4.add(jTextField12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        add(jPanel4, gridBagConstraints);

        jLabel4.setText("Visual Margin 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel4, gridBagConstraints);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
    
}