/*
 * @(#)QuaquaPanelUI.java  1.1  2005-05-25
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

import ch.randelshofer.quaqua.BackgroundBorder;
import ch.randelshofer.quaqua.util.Debug;
import ch.randelshofer.quaqua.util.PaintableColor;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
/**
 * QuaquaPanelUI.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-05-25 Make panel opaque depending on UIManager property
 * "Panel.isOpaque". Support for BackgroundBorder added.
 * <br>1.0  06 February 2005  Created.
 */
public class QuaquaPanelUI extends BasicPanelUI {
    // Shared UI object
    private static PanelUI panelUI;
    
    public static ComponentUI createUI(JComponent c) {
        if(panelUI == null) {
            panelUI = new QuaquaPanelUI();
        }
        return panelUI;
    }
    protected void installDefaults(JPanel p) {
        super.installDefaults(p);
	QuaquaUtilities.installProperty(p, "opaque", UIManager.get("Panel.opaque"));
        //p.setOpaque(QuaquaManager.getBoolean("Panel.opaque"));
    }
    
    protected void uninstallDefaults(JPanel p) {
        super.uninstallDefaults(p);
    }
    
    public static boolean isInTabbedPane(Component comp) {
        if(comp == null)
            return false;
        Container parent = comp.getParent();
        while (parent != null) {
            if (parent instanceof JTabbedPane) {
                return true;
            } else if (parent instanceof JRootPane) {
                return false;
            } else if (parent instanceof RootPaneContainer) {
                return false;
            } else if (parent instanceof Window) {
                return false;
            }
            parent = parent.getParent();
        }
        return false;
    }
    
    public void paint(Graphics gr, JComponent c) {
        if (c.isOpaque()) {
            Graphics2D g = (Graphics2D) gr;
            g.setPaint(PaintableColor.getPaint(c.getBackground(), c));
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        
        Border backgroundBorder = null;
        Insets insets = new Insets(0,0,0,0);
        if (c.getBorder() instanceof BackgroundBorder) {
            backgroundBorder = ((BackgroundBorder) c.getBorder()).getBackgroundBorder();
        } else if (c.getBorder() instanceof TitledBorder) {
            Border titledBorderBorder = ((TitledBorder) c.getBorder()).getBorder();
            if (titledBorderBorder instanceof BackgroundBorder) {
                backgroundBorder = ((BackgroundBorder) titledBorderBorder).getBackgroundBorder();
                insets = c.getBorder().getBorderInsets(c);
            }
        }
        if (backgroundBorder != null) {
            backgroundBorder.paintBorder(c, gr, insets.left, insets.top, c.getWidth() - insets.left - insets.right, c.getHeight() - insets.top - insets.bottom);
        }
        
        Debug.paint(gr, c, this);
    }
}
