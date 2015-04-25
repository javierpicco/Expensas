/*
 * @(#)BoxBorder.java  1.1.1  2007-01-04
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

package ch.randelshofer.quaqua.util;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.BackgroundBorder;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * GroupBox draws a Aqua-style group box similar to a native Cocoa NSBox.
 * XXX - This class should go away. We can easily get the same functionality
 * by instanciating one of the existing Quaqua border classes.
 *
 * @author  Werner Randelshofer
 * @version 1.1.1 2007-01-04 Render an EtchedBorder when the component does not
 * have an UI.
 * <br>1.1 2005-10-02 Increased border insets.
 * <br>1.0 September 25, 2005 Created.
 */
public class GroupBox implements BackgroundBorder {
    private static Border boxBackground = new CompoundBorder(
    new EmptyBorder(-4,-3,-3,-3),
    QuaquaBorderFactory.create(
    Toolkit.getDefaultToolkit().createImage(GroupBox.class.getResource("/ch/randelshofer/quaqua/images/GroupBox.png")),
    new Insets(7,7,7,7), new Insets(7,7,7,7), true
    )
    );
    
    private static Border etchedBorder = new EtchedBorder();
    
    public Border getBackgroundBorder() {
        return boxBackground;
    }
    
    public Insets getBorderInsets(Component c) {
        return new Insets(2,3,3,3);
    }
    
    public boolean isBorderOpaque() {
        return false;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        // Components that don't have a UI can't deal with BackgroundBorder
        // interface. As a work around, we paint a Etched border. 
        if ((c instanceof Box) || ! (c instanceof JComponent)) {
            etchedBorder.paintBorder(c, g, x, y, width, height);
        }
    }
}
