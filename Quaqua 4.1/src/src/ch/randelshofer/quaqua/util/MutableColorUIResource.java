/*
 * @(#)MutableColorUIResource.java  1.0  November 10, 2007
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
import javax.swing.plaf.UIResource;

/**
 * A ColorUIResource which can change its color.
 *
 * @author Werner Randelshofer
 * @version 1.0 November 10, 2007 Created.
 */
public class MutableColorUIResource extends Color implements UIResource {
    private int argb;
    
    /** Creates a new instance. */
    public MutableColorUIResource(int rgb) {
        this(rgb, false);
    }
    public MutableColorUIResource(int argb, boolean hasAlpha) {
        super((hasAlpha) ? argb : 0xff000000 | argb, true);
        this.argb = argb;
    }
    
    public void setColor(Color newValue) {
        setRGB(newValue.getRGB());
    }
    
    public void setRGB(int newValue) {
        argb = newValue;
    }
    
    public int getRGB() {
        return argb;
    }
    
    public PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {
        return new Color(argb, true).createContext(cm, r, r2d, xform, hints);
    }
}
