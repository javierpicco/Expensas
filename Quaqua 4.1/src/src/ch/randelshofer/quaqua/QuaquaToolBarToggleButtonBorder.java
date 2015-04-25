/*
 * @(#)ToolBarButtonBorder.java  1.0  28 March 2005
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

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
/**
 * ToolBarButtonBorder.
 *
 * @author  Werner Randelshofer
 * @version 1.0  28 March 2005  Created.
 */
public class QuaquaToolBarToggleButtonBorder implements Border {
    private static Color borderColor = new Color(0xb9b9b9);
    private static Color fillColor = new Color(0x1a000000, true);
    public Insets getBorderInsets(Component c) {
        return new Insets(6,10,6,10);
    }
    
    public boolean isBorderOpaque() {
        return false;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel  model = b.getModel();
        if (model.isSelected()) {
            g.setColor(fillColor);
            g.fillRect(x, y, width, height);
            
            int orientation = -1;
            if (b.getParent() instanceof JToolBar) {
                orientation = ((JToolBar) b.getParent()).getOrientation();
            }
            g.setColor(borderColor);
            switch (orientation) {
                case SwingConstants.HORIZONTAL :
                    g.fillRect(x, y, 1, height);
                    g.fillRect(x + width - 1, y, 1, height);
                    break;
                case SwingConstants.VERTICAL :
                    g.fillRect(x, y, width, 1);
                    g.fillRect(x, y + height - 1, width, 1);
                    break;
                default :
                    g.drawRect(x, y, width, height);
                    break;
            }
        }
    }
}
