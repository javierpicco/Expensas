/*
 * @(#)LazyPanel.java  1.0  December 23, 2005
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * A LazyPanel creates its child component only when it becomes visible.
 * LazyPanel is intended for use as a child of a JTabbedPane.
 *
 * @author  Werner Randelshofer
 * @version 1.0 December 23, 2005 Created.
 */
public class LazyPanel extends javax.swing.JPanel implements ComponentListener {
    private String childClassName;
    
    
    /**
     * Creates a new instance.
     */
    public LazyPanel() {
        initComponents();
        addComponentListener(this);
    }
    public LazyPanel(String childClassName) {
        this();
        setChildClassName(childClassName);
    }
    
    public void setChildClassName(String childClassName) {
        this.childClassName = childClassName;
    }
    
    public void addNotify() {
        super.addNotify();
    }
    
    
    public void instantiateChild() {
        if (childClassName != null) {
            long start = System.currentTimeMillis();
            try {
//System.out.println("*** "+childClassName+" ***");                
                Class childClass = Class.forName(childClassName);
                Component child = (Component) childClass.newInstance();
                add(child);
            } catch (Throwable e) {
                add(new JLabel("Unable to instantiate "+childClassName));
//                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            //System.out.println("create "+childClassName+" "+(end-start));
            childClassName = null;
            start = end;
            validate();
            end = System.currentTimeMillis();
            //System.out.println("validate "+(end-start));
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        
        setLayout(new java.awt.BorderLayout());
        
    }//GEN-END:initComponents
    
    public void componentHidden(ComponentEvent e) {
    }
    
    public void componentMoved(ComponentEvent e) {
    }
    
    public void componentResized(ComponentEvent e) {
    }
    
    public void componentShown(ComponentEvent e) {
        if (childClassName != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() { instantiateChild(); }
            });
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
