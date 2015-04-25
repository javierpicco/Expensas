/*
 * @(#)InactivatableColorUIResource.java  1.0.1  2007-11-11
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

package ch.randelshofer.quaqua.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * InactivatableColorUIResource is a color, that can be rendered using an
 * an active state and an inactive state.
 *
 * @author Werner Randelshofer
 * @version 1.0.1 2007-11-11 We need to override method createContext as well.
 * <br>1.0 January 16, 2007 Created.
 */
public class InactivatableColorUIResource extends Color {
    private boolean isActive;
    private int inactiveRGB;
    
    /** Creates a new instance. */
    public InactivatableColorUIResource(int activeRGB, int inactiveRGB) {
        super(activeRGB);
        this.inactiveRGB = inactiveRGB | 0xff000000;
    }
    public InactivatableColorUIResource(int activeRGB, int inactiveRGB, boolean hasAlpha) {
        super(activeRGB, hasAlpha);
        this.inactiveRGB = (hasAlpha) ? inactiveRGB : inactiveRGB | 0xff000000;
    }
    
    public void setActive(boolean newValue) {
        isActive = newValue;
    }
    
    public int getRGB() {
        return (isActive) ? super.getRGB() : inactiveRGB;
        
    }
    
    public PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {
        return (isActive) ? super.createContext(cm, r, r2d, xform, hints) :
            new Color(inactiveRGB, true).createContext(cm, r, r2d, xform, hints);
    }
}
