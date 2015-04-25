/*
 * @(#)VisuallyLayoutableIcon.java  1.0  May 14, 2005
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

import java.awt.*;
import javax.swing.*;
/**
 * VisuallyLayoutableIcon takes an icon and a layout rectangle. The layout rectangle is
 * relative to the upper left corner of the icon.
 *
 * @author  Werner Randelshofer
 * @version 1.0 May 14, 2005 Created.
 */
public class VisuallyLayoutableIcon implements Icon {
    private Icon icon;
    private Rectangle layoutRect;
    
    /**
     * Creates a new instance.
     */
    public VisuallyLayoutableIcon(Icon icon, int x, int y, int width, int height) {
        this(icon, new Rectangle(x, y, width, height));
    }
    
    /**
     * Creates a new instance.
     */
    public VisuallyLayoutableIcon(Icon icon, Rectangle layoutRect) {
        this.icon = icon;
        this.layoutRect = layoutRect;
    }
    
    public int getIconHeight() {
        return layoutRect.height;
    }
    
    public int getIconWidth() {
        return layoutRect.width;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x - layoutRect.x, y - layoutRect.y);
    }
    
}
