/*
 * @(#)ButtonTest.java  1.0  13 February 2005
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
 * ButtonTest.
 *
 * @author  Werner Randelshofer
 * @version 1.0  13 February 2005  Created.
 */
public class PushButtonTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public PushButtonTest() {
        initComponents();
        
        Font smallFont = UIManager.getFont("SmallSystemFont");
        if (smallFont == null) {
            smallFont = new Font("LucidaGrande", Font.PLAIN, 11);
        }
        
        smallBevelButton.setFont(smallFont);
        smallDisabledButton.setFont(smallFont);
        smallEnabledButton.setFont(smallFont);
        smallLabel.setFont(smallFont);
        smallSquareButton.setFont(smallFont);
        smallSquareLabel1.setFont(smallFont);
        
        
        
        squareButton.putClientProperty("JButton.buttonType", "toolbar");
        smallSquareButton.putClientProperty("JButton.buttonType", "toolbar");
        bevelButton.putClientProperty("JButton.buttonType", "icon");
        smallBevelButton.putClientProperty("JButton.buttonType", "icon");
        enabledButton.putClientProperty("JButton.buttonType", "text");
        
        squareButton.putClientProperty("Quaqua.Button.style","square");
        bevelButton.putClientProperty("Quaqua.Button.style","bevel");
        enabledButton.putClientProperty("Quaqua.Button.style","push");
        helpButton.putClientProperty("Quaqua.Button.style", "help");
        smallSquareButton.putClientProperty("Quaqua.Button.style","square");
        smallBevelButton.putClientProperty("Quaqua.Button.style","bevel");
        
        // Leopard properties
        if (QuaquaManager.getDesign() == QuaquaManager.LEOPARD) {
            smallEnabledButton.putClientProperty("JComponent.sizeVariant","small");
            smallDisabledButton.putClientProperty("JComponent.sizeVariant","small");
            smallBevelButton.putClientProperty("JComponent.sizeVariant","small");
            smallSquareButton.putClientProperty("JComponent.sizeVariant","small");
            helpButton.putClientProperty("JButton.buttonType","help");
            helpButton.setText(" ");
            squareButton.putClientProperty("JButton.buttonType", "square");
            smallSquareButton.putClientProperty("JButton.buttonType", "square");
        }
    }
    /*
    public void paint(Graphics g) {
        long start = System.currentTimeMillis();
        super.paint(g);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }*/
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame("Quaqua Push Button Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new PushButtonTest());
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

        enabledButton = new javax.swing.JButton();
        enabledLabel = new javax.swing.JLabel();
        disabledButton = new javax.swing.JButton();
        disabledLabel = new javax.swing.JLabel();
        squareButton = new javax.swing.JButton();
        smallSquareLabel1 = new javax.swing.JLabel();
        bevelButton = new javax.swing.JButton();
        bevelLabel = new javax.swing.JLabel();
        helpButton = new javax.swing.JButton();
        helpLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        smallEnabledButton = new javax.swing.JButton();
        smallLabel = new javax.swing.JLabel();
        smallDisabledButton = new javax.swing.JButton();
        smallSquareButton = new javax.swing.JButton();
        smallBevelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        largeButton = new javax.swing.JButton();
        largeLabel = new javax.swing.JLabel();
        borderlessButton = new javax.swing.JButton();
        borderlessLabel = new javax.swing.JLabel();
        springPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        enabledButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(enabledButton, gridBagConstraints);

        enabledLabel.setText("Enabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(enabledLabel, gridBagConstraints);

        disabledButton.setText("\u00c5ngstr\u00f6m H");
        disabledButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(disabledButton, gridBagConstraints);

        disabledLabel.setText("Disabled");
        disabledLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(disabledLabel, gridBagConstraints);

        squareButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(squareButton, gridBagConstraints);

        smallSquareLabel1.setText("Square Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(smallSquareLabel1, gridBagConstraints);

        bevelButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(bevelButton, gridBagConstraints);

        bevelLabel.setText("Rounded Bevel Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(bevelLabel, gridBagConstraints);

        helpButton.setText("Help");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(helpButton, gridBagConstraints);

        helpLabel.setText("Help Style");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(helpLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        add(jSeparator2, gridBagConstraints);

        smallEnabledButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(smallEnabledButton, gridBagConstraints);

        smallLabel.setText("Small Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(smallLabel, gridBagConstraints);

        smallDisabledButton.setText("\u00c5ngstr\u00f6m H");
        smallDisabledButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(smallDisabledButton, gridBagConstraints);

        smallSquareButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(smallSquareButton, gridBagConstraints);

        smallBevelButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(smallBevelButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        add(jSeparator1, gridBagConstraints);

        largeButton.setFont(new java.awt.Font("Dialog", 0, 24));
        largeButton.setText("\u00c5ngstr\u00f6m H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(largeButton, gridBagConstraints);

        largeLabel.setText("Large Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(largeLabel, gridBagConstraints);

        borderlessButton.setText("\u00c5ngstr\u00f6m H");
        borderlessButton.setBorderPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(borderlessButton, gridBagConstraints);

        borderlessLabel.setText("Borderless");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(borderlessLabel, gridBagConstraints);

        springPanel.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        add(springPanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bevelButton;
    private javax.swing.JLabel bevelLabel;
    private javax.swing.JButton borderlessButton;
    private javax.swing.JLabel borderlessLabel;
    private javax.swing.JButton disabledButton;
    private javax.swing.JLabel disabledLabel;
    private javax.swing.JButton enabledButton;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton largeButton;
    private javax.swing.JLabel largeLabel;
    private javax.swing.JButton smallBevelButton;
    private javax.swing.JButton smallDisabledButton;
    private javax.swing.JButton smallEnabledButton;
    private javax.swing.JLabel smallLabel;
    private javax.swing.JButton smallSquareButton;
    private javax.swing.JLabel smallSquareLabel1;
    private javax.swing.JPanel springPanel;
    private javax.swing.JButton squareButton;
    // End of variables declaration//GEN-END:variables
    
}
