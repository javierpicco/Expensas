/*
 * @(#)ColorWheelTest.java  1.0  August 27, 2005
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
import ch.randelshofer.quaqua.colorchooser.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
/**
 * ColorWheelTest.
 *
 * @author  Werner Randelshofer
 * @version 1.0 August 27, 2005 Created.
 */
public class ColorWheelTest extends javax.swing.JPanel {
    
    /**
     * Creates a new instance.
     */
    public ColorWheelTest() {
        initComponents();
        ColorWheel cw = new ColorWheel();
        cw.getModel().setValue(2, 100);
        add(cw);
        add(new JLabel("Color Wheel Test"), BorderLayout.SOUTH);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFrame f = new JFrame("Color Wheel Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ColorWheelTest());
        f.pack();
        f.setVisible(true);
    }
}
