/*
 * @(#)JPanel.java  1.0  19 March 2005
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

import ch.randelshofer.quaqua.util.Methods;
import javax.swing.*;
/**
 * JPanel.
 *
 * @author  Werner Randelshofer
 * @version 1.0  19 March 2005  Created.
 */
public class AdjustmentControlsTest extends javax.swing.JPanel {
    
    /** Creates new form. */
    public AdjustmentControlsTest() {
        initComponents();
            tabbedPane.add(new LazyPanel("test.SliderTest"), "Slider");

            tabbedPane.add(new LazyPanel("test.SpinnerTest14"), "Spinner");
            tabbedPane.add(new LazyPanel("test.ProgressBarTest14"), "ProgressBar");
        tabbedPane.add(new LazyPanel("test.ScrollBarTest"), "ScrollBar");

        Methods.invokeIfExists(tabbedPane, "setTabLayoutPolicy", 0); // JTabbedPane.WRAP_TAB_LAYOUT);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        tabbedPane = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());

        add(tabbedPane, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    
}
