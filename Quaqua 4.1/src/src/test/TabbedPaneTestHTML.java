/*
 * @(#)TabbedPaneTestHTML.java  1.0  February 19, 2006
 *
 * Copyright (c) 2006 Werner Randelshofer
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

/**
 * TabbedPaneTestHTML.
 * 
 * @author Werner Randelshofer
 * @version 1.0 February 19, 2006 Created.
 */
public class TabbedPaneTestHTML extends javax.swing.JPanel {
    
    /**
     * Creates a new instance.
     */
    public TabbedPaneTestHTML() {
        initComponents();
        
        scrollPane.putClientProperty("Quaqua.TabbedPaneChild.contentInsets", new Insets(0,0,0,0));
        scrollPane.putClientProperty("Quaqua.TabbedPaneChild.contentBackground", Color.white);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        
    }
    
   public static void main(String args[]) {
      // System.setProperty("Quaqua.tabLayoutPolicy","wrap");
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("TabbedPaneTest3 "+UIManager.getLookAndFeel().getName());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new TabbedPaneTestHTML());
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
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        tabbedPane.addTab("<html><b>Options</b>", jPanel1);

        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextArea1.setText("Lorem Ipsum");
        scrollPane.setViewportView(jTextArea1);

        tabbedPane.addTab("Notes", scrollPane);

        add(tabbedPane, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    
}