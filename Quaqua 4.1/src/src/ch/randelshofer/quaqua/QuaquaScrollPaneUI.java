/*
 * @(#)QuaquaScrollPaneUI.java  1.2.2  2005-11-26
 *
 * Copyright (c) 2004-2005 Werner Randelshofer
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

import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.util.Debug;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
/**
 * QuaquaScrollPaneUI.
 *
 * @author  Werner Randelshofer
 * @version 1.2.2 2005-11-26 Retrieve default opaqueness from UIManager.
 * <br>1.2.1 2005-09-17 Don't fill background if non-opaque.
 * <br>1.2 2005-08-25 Installs a QuaquaScrollPaneLayout to the scroll
 * pane to avoid overlapping of scroll bars with the grow-box of a Frame or
 * Dialog.
 * <br>1.1 2005-07-17 Adapted to changes in interface VisuallyLayoutable.
 * <br>1.0  June 23, 2004  Created.
 */
public class QuaquaScrollPaneUI extends BasicScrollPaneUI implements VisuallyLayoutable {
    //private HierarchyListener hierarchyListener;
    
    /** Creates a new instance. */
    public QuaquaScrollPaneUI() {
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaScrollPaneUI();
    }
    
    public void installUI(JComponent c) {
        super.installUI(c);
	QuaquaUtilities.installProperty(c, "opaque", UIManager.get("ScrollPane.opaque"));
        //c.setOpaque(QuaquaManager.getBoolean("ScrollPane.opaque"));
        Methods.invokeIfExists(c, "setFocusable", QuaquaManager.getBoolean("ScrollPane.focusable"));
    }
    protected PropertyChangeListener createPropertyChangeListener() {
        return new PropertyChangeHandler(super.createPropertyChangeListener());
    }
    protected void installDefaults(JScrollPane scrollpane) {
        super.installDefaults(scrollpane);
        if (scrollpane.getLayout() instanceof UIResource) {
            ScrollPaneLayout layout = new QuaquaScrollPaneLayout.UIResource();
            scrollpane.setLayout(layout);
            layout.syncWithScrollPane(scrollpane);
        }
    }
    protected void uninstallDefaults(JScrollPane scrollpane) {
        super.uninstallDefaults(scrollpane);
        if (scrollpane.getLayout() instanceof UIResource) {
            ScrollPaneLayout layout = new ScrollPaneLayout.UIResource();
            scrollpane.setLayout(layout);
            layout.syncWithScrollPane(scrollpane);
        }
    }
    
    /*
    protected HierarchyListener createHierarchyListener(JScrollPane c) {
        // FIXME: The ComponentActivationHandler repaints the _whole_ JScrollPane.
        // This is inefficient. We only need the border area of the JScrollPane
        // to be repainted.
        return new ComponentActivationHandler(c);
    }*/
    /*
    protected void installListeners(JScrollPane c) {
        hierarchyListener = createHierarchyListener(c);
        if (hierarchyListener != null) {
            c.addHierarchyListener(hierarchyListener);
        }
        super.installListeners(c);
    }
     
    protected void uninstallListeners(JScrollPane c) {
        if (hierarchyListener != null) {
            c.removeHierarchyListener(hierarchyListener);
            hierarchyListener = null;
        }
        super.uninstallListeners(c);
    }*/
    public Insets getVisualMargin(Component c) {
        Insets margin = (Insets) ((JComponent) c).getClientProperty("Quaqua.Component.visualMargin");
        if (margin == null) margin = UIManager.getInsets("Component.visualMargin");
        return (margin == null) ? new Insets(0, 0, 0 ,0) : margin;
    }
    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            g.setColor(c.getBackground());
            Insets margin = getVisualMargin(c);
            g.fillRect(margin.left, margin.top, c.getWidth() - margin.left - margin.right, c.getHeight() - margin.top - margin.bottom);
            paint(g, c);
            Debug.paint(g, c, this);
        }
    }
    
    public int getBaseline(JComponent c, int width, int height) {
        return -1;
    }
    public Rectangle getVisualBounds(JComponent c, int type, int width, int height) {
        Rectangle bounds = new Rectangle(0,0,width,height);
        if (type == VisuallyLayoutable.CLIP_BOUNDS) {
            return bounds;
        }
        
        JScrollPane b = (JScrollPane) c;
        
        if (type == VisuallyLayoutable.COMPONENT_BOUNDS
        && b.getBorder() != null) {
            Border border = b.getBorder();
            if (border instanceof UIResource) {
                InsetsUtil.subtractInto(getVisualMargin(b), bounds);
            }
            return bounds;
        }
        
        return bounds;
    }
    
    /**
     * PropertyChangeListener for the ScrollBars.
     */
    private class PropertyChangeHandler implements PropertyChangeListener {
        PropertyChangeListener target;
        public PropertyChangeHandler(PropertyChangeListener target) {
            this.target = target;
        }
        // Listens for changes in the model property and reinstalls the
        // horizontal/vertical PropertyChangeListeners.
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            Object source = e.getSource();
            
            if ("Frame.active".equals(propertyName)) {
                QuaquaUtilities.repaintBorder((JComponent) source);
            }
            target.propertyChange(e);
        }
    }
}