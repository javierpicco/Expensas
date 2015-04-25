/*
 * @(#)QuaquaLeopardSideBarSelectionBorder.java  1.0  November 10, 2007
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Staldenmattweg 2, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.leopard;

import ch.randelshofer.quaqua.*;
import java.awt.*;
import java.awt.Insets;
import javax.swing.border.*;
import javax.swing.plaf.UIResource;

/**
 * QuaquaLeopardSideBarSelectionBorder.
 *
 * @author Werner Randelshofer
 * @version 1.0 November 10, 2007 Created.
 */
public class QuaquaLeopardSideBarSelectionBorder implements Border, UIResource {
    
    /** Creates a new instance. */
    public QuaquaLeopardSideBarSelectionBorder() {
    }
    
    public void paintBorder(Component c, Graphics gr, int x, int y, int width, int height) {
        Graphics2D g = (Graphics2D) gr;
        if (QuaquaUtilities.isFocused(c)) {
            // top line: 0x4580c8
            g.setColor(new Color(0x4580c8));
            g.fillRect(0, y, width, 1);
            g.setPaint(new GradientPaint(
                    x, y + 1, new Color(0x5c93d5),
                    x, y + height - 1, new Color(0x1a58ad),
                    true
                    ));
        } else {
            if (QuaquaUtilities.isOnActiveWindow(c)) {
                // top line: 0x91a0c0
            g.setColor(new Color(0x91a0c0));
            g.fillRect(0, y, width, 1);
                g.setPaint(new GradientPaint(
                        x, y + 1,new Color(0xa9b1d0),
                        x, y + height - 1,new Color(0x6e81a9),
                        true
                        ));
                
            } else {
                // top line: 0x979797
            g.setColor(new Color(0x979797));
            g.fillRect(0, y, width, 1);
                g.setPaint(new GradientPaint(
                        x, y + 1,new Color(0xb4b4b4),
                        x, y + height - 1,new Color(0x8a8a8a)
                        ));
            }
        }
        g.fillRect(x, y + 1, width, height - 1);
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }
    
    public boolean isBorderOpaque() {
        return true;
    }
    
}
