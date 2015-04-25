/*
 * @(#)SliderThumbIcon.java  3.0  2005-10-17
 *
 * Copyright (c) 2003-2005 Werner Randelshofer
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
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
/**
 * An Icon with different visuals reflecting the state of the slider
 * on which it draws on.
 *
 * @author  Werner Randelshofer
 * @version 3.0 2005-10-17 Changed superclass to MultIcon.
 * <br>2.0 2005-03-19 Reworked.
 * <br>1.0 October 5, 2003 Create..
 */
public class SliderThumbIcon extends MultiIcon {
    private final static int E = 0;
    private final static int EP = 1;
    private final static int D = 2;
    private final static int I = 3;
    private final static int DI = 4;
    
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     * If an icon is null, an icon is derived for the state from the
     * other icons.
     */
    public SliderThumbIcon(Icon e, Icon ep, Icon d, Icon i, Icon di) {
        super(new Icon[] {e, ep, d, i, di});
    }
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     *
     * The array indices are used to represente the following states:
     * [0] Enabled
     * [1] Enabled Pressed
     * [2] Disabled
     * [3] Enabled Inactive
     * [4] Disabled Inactive
     *
     * If an array element is null, an icon is derived for the state from the
     * other icons.
     */
    public SliderThumbIcon(Image[] images) {
        super(images);
    }
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     * If an icon is null, nothing is drawn for this state.
     */
    public SliderThumbIcon(Icon[] icons) {
        super(icons);
    }
    
    /**
     * Creates a new instance.
     * The icon representations are created lazily from the image.
     */
    public SliderThumbIcon(Image tiledImage, int tileCount, boolean isTiledHorizontaly) {
        super(tiledImage, tileCount, isTiledHorizontaly);
    }

    protected void generateMissingIcons() {
        Icon[] oldIcons;
        if (icons.length != 5) {
            oldIcons = new Icon[5];
            System.arraycopy(icons, 0, oldIcons, 0, Math.min(icons.length, 5));
        } else {
            oldIcons = icons;
        }
        if (icons[EP] == null) {
            icons[EP] = icons[E];
        }
        if (icons[D] == null) {
            icons[D] = icons[E];
        }
        if (icons[I] == null) {
            icons[I] = icons[E];
        }
        if (icons[DI] == null) {
            icons[DI] = icons[D];
        }
    }
    
    protected Icon getIcon(Component c) {
        Icon icon;
        boolean isActive = QuaquaUtilities.isOnActiveWindow(c);
        
        if (c instanceof JSlider) {
            JSlider slider = (JSlider) c;
            if (isActive) {
                if (c.isEnabled()) {
                    if (slider.getModel().getValueIsAdjusting()) {
                        icon = icons[EP];
                    } else {
                        icon = icons[E];
                    }
                } else {
                    icon = icons[D];
                }
            } else {
                if (c.isEnabled()) {
                    icon = icons[I];
                } else {
                    icon = icons[DI];
                }
            }
        } else {
            if (isActive) {
                if (c.isEnabled()) {
                    icon = icons[E];
                } else {
                    icon = icons[D];
                }
            } else {
                if (c.isEnabled()) {
                    icon = icons[I];
                } else {
                    icon = icons[DI];
                }
            }
        }
        return icon;
    }
}


