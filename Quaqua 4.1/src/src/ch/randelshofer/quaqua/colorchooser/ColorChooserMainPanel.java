/*
 * @(#)ColorChooserMainPanel.java  1.4.2  2007-02-24
 *
 * Copyright (c) 2005-2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.colorchooser;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

/**
 * The main panel of the color chooser UI.
 *
 * @author  Werner Randelshofer
 * @version 1.4.2 2007-02-24 Identify color picker by its class name
 * instead of by its display name. 
 * <br>1.4.1 2007-02-15 Fixed NPE in addColorChooserPanel caused by 
 * AbstractColorChooserPanel having a null display name. 
 * <br>1.4 2005-12-18 ColorPicker added.
 * <br>1.3 2005-09-05 Get font from UIManager.
 * <br>1.2 2005-08-27 Remember chooser panel.
 * <br>1.1 2005-04-23 Removed main method.
 * <br>1.0  30 March 2005  Created.
 */
public class ColorChooserMainPanel extends javax.swing.JPanel {
    /**
     * We store here the name of the last selected chooser.
     * When the ColorChooserMainPanel is recreated multiple times in the same
     * applicatin, the application 'remembers' which panel the user had opened
     * before.
     */
    private static String lastSelectedChooserName = null;
    
    /** Creates new form. */
    public ColorChooserMainPanel() {
        initComponents();
        toolBar.putClientProperty("Quaqua.ToolBar.isDividerDrawn",Boolean.TRUE);
        
    }
    
    public void setPreviewPanel(JComponent c) {
        previewPanelHolder.removeAll();
        if (c != null) {
            previewPanelHolder.add(c);
        }
    }
    
    public void addColorChooserPanel(final AbstractColorChooserPanel ccp) {
        final String displayName = ccp.getDisplayName();
        if (displayName == null) {
            // Return if we haven't initialized yet
            return;
        }
        
        if (ccp.getClass().getName().equals("ch.randelshofer.quaqua.colorchooser.Quaqua15ColorPicker")) {
            northPanel.add(ccp, BorderLayout.WEST);
        } else {
            Icon displayIcon = ccp.getLargeDisplayIcon();
            JToggleButton tb = new JToggleButton(null, displayIcon);
            tb.setToolTipText(displayName);
            Methods.invokeIfExists(tb, "setFocusable", false);
            tb.setHorizontalTextPosition(SwingConstants.CENTER);
            tb.setVerticalTextPosition(SwingConstants.BOTTOM);
            tb.setFont(UIManager.getFont("ColorChooser.font"));
            tb.putClientProperty("Quaqua.Button.style","toolBarTab");
            JPanel centerView = new JPanel(new BorderLayout());
            centerView.add(ccp);
            chooserPanelHolder.add(centerView, displayName);
            toolBarButtonGroup.add(tb);
            toolBar.add(tb);
            
            if (toolBar.getComponentCount() == 1
            || lastSelectedChooserName != null && lastSelectedChooserName.equals(displayName)) {
                tb.setSelected(true);
                CardLayout cl = (CardLayout) chooserPanelHolder.getLayout();
                cl.show(chooserPanelHolder, displayName);
            }
            
            tb.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        CardLayout cl = (CardLayout) chooserPanelHolder.getLayout();
                        cl.show(chooserPanelHolder, displayName);
                        lastSelectedChooserName = displayName;
                    }
                }
            });
        }
    }
    public void removeAllColorChooserPanels() {
        Component[] tb = toolBar.getComponents();
        for (int i=0; i <tb.length; i++) {
            if (tb[i] instanceof AbstractButton) {
                toolBarButtonGroup.remove((AbstractButton) tb[i]);
            }
        }
        toolBar.removeAll();
        chooserPanelHolder.removeAll();
        
        northPanel.removeAll();
        northPanel.add(previewPanelHolder);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        toolBarButtonGroup = new javax.swing.ButtonGroup();
        toolBar = new javax.swing.JToolBar();
        mainPanel = new javax.swing.JPanel();
        northPanel = new javax.swing.JPanel();
        previewPanelHolder = new javax.swing.JPanel();
        chooserPanelHolder = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);
        add(toolBar, java.awt.BorderLayout.NORTH);

        mainPanel.setLayout(new java.awt.BorderLayout());

        mainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 4, 7, 4)));
        northPanel.setLayout(new java.awt.BorderLayout());

        previewPanelHolder.setLayout(new java.awt.BorderLayout());

        northPanel.add(previewPanelHolder, java.awt.BorderLayout.CENTER);

        mainPanel.add(northPanel, java.awt.BorderLayout.NORTH);

        chooserPanelHolder.setLayout(new java.awt.CardLayout());

        chooserPanelHolder.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 0, 0, 0)));
        mainPanel.add(chooserPanelHolder, java.awt.BorderLayout.CENTER);

        add(mainPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chooserPanelHolder;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel northPanel;
    private javax.swing.JPanel previewPanelHolder;
    private javax.swing.JToolBar toolBar;
    private javax.swing.ButtonGroup toolBarButtonGroup;
    // End of variables declaration//GEN-END:variables
    
}
