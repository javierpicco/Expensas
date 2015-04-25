/*
 * @(#)ToolbarButtonStateBorder.java  1.1  2005-11-30
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

import ch.randelshofer.quaqua.ButtonStateBorder;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * ToolbarButtonStateBorder.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-11-30 Constructor with tiled image added.
 * <br>1.0  29 March 2005  Created.
 */
public class QuaquaToolBarButtonStateBorder extends ButtonStateBorder {
    private boolean isRollover;
    
    public QuaquaToolBarButtonStateBorder(Border e, Border ep, Border es, Border eps, Border d, Border ds, Border i, Border is, Border di, Border dis, boolean isRollover) {
        super(e,ep,es,eps,d,ds,i,is,di,dis);
    }
    /**
     * Creates a new instance.
     * All borders must have the same insets.
     */
    public QuaquaToolBarButtonStateBorder(Border[] borders, boolean isRollover) {
        super(borders);
        this.isRollover = isRollover;
    }
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     */
    public QuaquaToolBarButtonStateBorder(Image[] images, Insets borderInsets, Insets imageInsets, boolean fill, boolean isRollover) {
        super(images, borderInsets, imageInsets, fill);
        this.isRollover = isRollover;
    }
    
    /**
     * Creates a new instance.
     * All borders must have the same dimensions.
     */
    public QuaquaToolBarButtonStateBorder(Image tiledImage, int tileCount, boolean isTiledHorizontaly,
    Insets imageInsets, Insets borderInsets, boolean fill, boolean isRollover) {
        super(tiledImage, tileCount, isTiledHorizontaly, imageInsets, borderInsets, fill);
        this.isRollover = isRollover;
    }
    
    public void paintBorder(Component c, Graphics gr, int x, int y, int width, int height) {
        boolean paint = false;
        if (c instanceof AbstractButton) {
            ButtonModel model = ((AbstractButton) c).getModel();
            
            if (isRollover) {
                paint = model.isRollover() || model.isSelected();
            } else {
                paint = model.isSelected();
            }
        }
        if (paint) {
            super.paintBorder(c, gr, x, y, width, height);
        }
    }
}
