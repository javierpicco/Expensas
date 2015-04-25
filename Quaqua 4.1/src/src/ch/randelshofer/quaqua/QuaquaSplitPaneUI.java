/*
 * @(#)QuaquaSplitPaneUI.java  1.0  November 27, 2005
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

package ch.randelshofer.quaqua;

import ch.randelshofer.quaqua.util.Methods;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
 * QuaquaSplitPaneUI.
 *
 * @author  Werner Randelshofer
 * @version 1.0 November 27, 2005 Created.
 */
public class QuaquaSplitPaneUI extends BasicSplitPaneUI {
    
    /**
     * Creates a new instance.
     */
    public QuaquaSplitPaneUI() {
    }
    

    /**
     * Creates a new BasicSplitPaneUI instance
     */
    public static ComponentUI createUI(JComponent x) {
        return new QuaquaSplitPaneUI();
    }

    /**
     * Installs the UI defaults.
     */
    protected void installDefaults() {
        super.installDefaults();
	QuaquaUtilities.installProperty(splitPane, "opaque", UIManager.get("SplitPane.opaque"));
        //splitPane.setOpaque(QuaquaManager.getBoolean("SplitPane.opaque"));
        /*
        splitPane.setContinuousLayout(true);
        setContinuousLayout(splitPane.isContinuousLayout());
         */
        Methods.invokeIfExists(splitPane,"setFocusable",QuaquaManager.getBoolean("SplitPane.focusable"));
    }
    /**
     * Creates the default divider.
     */
    public BasicSplitPaneDivider createDefaultDivider() {
        return new QuaquaSplitPaneDivider(this);
    }

    /**
     * Messaged after the JSplitPane the receiver is providing the look
     * and feel for paints its children.
     */
    public void finishedPaintingChildren(JSplitPane jc, Graphics g) {
        if(jc == splitPane && getLastDragLocation() != -1 &&
           !isContinuousLayout() && !draggingHW) {
            Dimension      size = splitPane.getSize();
            
            g.setColor(UIManager.getColor("SplitPaneDivider.draggingColor"));
            if(getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                g.fillRect(getLastDragLocation(), 0, dividerSize,
                           size.height);
            } else {
                g.fillRect(0, getLastDragLocation(), size.width,
                           dividerSize);
            }
        }
    }
    /**
     * Returns the default non continuous layout divider, which is an
     * instanceof Canvas that fills the background in dark gray.
     */
    protected Component createDefaultNonContinuousLayoutDivider() {
        return new Canvas() {
            public void paint(Graphics g) {
                if(!isContinuousLayout() && getLastDragLocation() != -1) {
                    Dimension      size = splitPane.getSize();
                    g.setColor(UIManager.getColor("SplitPaneDivider.draggingColor"));
                    if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                        g.fillRect(0, 0, dividerSize, size.height);
                    } else {
                        g.fillRect(0, 0, size.width, dividerSize);
                    }
                }
            }
        };
    }
}
