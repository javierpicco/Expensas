/*
 * @(#)TreeTest.java  1.0  13 February 2005
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
import javax.swing.table.*;
import java.util.*;
import javax.swing.tree.*;
/**
 * TreeTest.
 *
 * @author  Werner Randelshofer
 * @version 1.0  13 February 2005  Created.
 */
public class TreeTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public TreeTest() {
        initComponents();
        tree2.putClientProperty("Quaqua.Tree.style","striped");
        tree3.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root")));
        tree3.putClientProperty("Quaqua.Tree.style","striped");
        tree4.setSelectionRow(3);
        tree4.setEnabled(false);
    }
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("Quaqua Tree Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new TreeTest());
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
        scrollPane1 = new javax.swing.JScrollPane();
        tree1 = new javax.swing.JTree();
        scrollPane2 = new javax.swing.JScrollPane();
        tree2 = new javax.swing.JTree();
        scrollPane3 = new javax.swing.JScrollPane();
        tree3 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree4 = new javax.swing.JTree();

        setLayout(new java.awt.GridLayout(2, 2));

        tree1.setEditable(true);
        tree1.setRootVisible(false);
        tree1.setShowsRootHandles(true);
        scrollPane1.setViewportView(tree1);

        add(scrollPane1);

        tree2.setRootVisible(false);
        tree2.setShowsRootHandles(true);
        scrollPane2.setViewportView(tree2);

        add(scrollPane2);

        tree3.setRootVisible(false);
        scrollPane3.setViewportView(tree3);

        add(scrollPane3);

        tree4.setRootVisible(false);
        tree4.setShowsRootHandles(true);
        jScrollPane1.setViewportView(tree4);

        add(jScrollPane1);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JScrollPane scrollPane3;
    private javax.swing.JTree tree1;
    private javax.swing.JTree tree2;
    private javax.swing.JTree tree3;
    private javax.swing.JTree tree4;
    // End of variables declaration//GEN-END:variables
    
}
