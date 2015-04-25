/*
 * @(#)QuaquaMenuBarUI.java  1.1  2005-12-10
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

package ch.randelshofer.quaqua;

import ch.randelshofer.quaqua.BackgroundBorder;
import ch.randelshofer.quaqua.util.PaintableColor;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
/**
 * QuaquaMenuBarUI.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-12-10 Support for background border added.
 * <br>1.0  28 December 2004  Created.
 */
public class QuaquaMenuBarUI extends BasicMenuBarUI {
    
    /** Creates a new instance. */
    public QuaquaMenuBarUI() {
    }
    
    public static ComponentUI createUI(JComponent x) {
        return new QuaquaMenuBarUI();
    }
    public void paint(Graphics gr, JComponent c) {
        Graphics2D g = (Graphics2D) gr;
        int w = c.getWidth();
        int h = c.getHeight();
        
        g.setPaint(PaintableColor.getPaint(c.getBackground(), c));
        g.fillRect(0, 0, w, h);
        
        if (c.getBorder() instanceof BackgroundBorder) {
            Border bb = ((BackgroundBorder) c.getBorder()).getBackgroundBorder();
            bb.paintBorder(c, gr, 0, 0, w, h);
        }
        
        Color shadow = UIManager.getColor("MenuBar.shadow");
        if (shadow != null) {
            g.setColor(shadow);
            g.fillRect(0, h - 1, w, 1);
        }
    }
}
