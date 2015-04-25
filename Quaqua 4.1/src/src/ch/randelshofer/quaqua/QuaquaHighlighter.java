/*
 * @(#)QuaquaHighlighter.java  1.0.1  2007-08-06
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
import javax.swing.plaf.*;
import javax.swing.text.*;

/**
 * QuaquaHighlighter.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2007-08-06 Draw selection with inactive selection background, 
 * of the component is not focused.
 * <br>1.0  July 5, 2004  Created.
 */
public class QuaquaHighlighter extends DefaultHighlighter implements UIResource
{
    public static LayeredHighlighter.LayerPainter painterInstance
	= new QuaquaHighlightPainter(null);
    
    public static class QuaquaHighlightPainter
	extends DefaultHighlighter.DefaultHighlightPainter
    {
	Color highlightColor;
	
	public QuaquaHighlightPainter(Color color) {
	    super(color);
	}
	
	public Color getColor() {
	    return highlightColor == null ? super.getColor() : highlightColor;
	}
	
	void setColor(JTextComponent c) {
	    highlightColor = super.getColor();
	    if (highlightColor == null) {
		highlightColor = c.getSelectionColor();
            }
            if (! QuaquaUtilities.isFocused(c)) {
		highlightColor = UIManager.getColor("TextField.inactiveSelectionBackground");
            }
	}
	
	public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
	    setColor(c);
	    super.paint(g, offs0, offs1, bounds, c);
	}
	
	public Shape paintLayer(Graphics g, int offs0, int offs1,
				Shape bounds, JTextComponent c, View view) {
	    setColor(c);
	    return super.paintLayer(g, offs0, offs1, bounds, c,
				    view);
	}
    }
}
