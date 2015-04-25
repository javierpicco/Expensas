/*
 * @(#)QuaquaTextFieldBorder.java  3.1  2007-04-12
 *
 * Copyright (c) 2004-2007 Werner Randelshofer
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

import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.BackgroundBorder;
import ch.randelshofer.quaqua.util.Debug;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

/**
 * QuaquaTextFieldBorder.
 *
 * @author  Werner Randelshofer
 * @version 4.0 2007-07-26 Add support for client property "Quaqua.TextField.style"
 * "search", "plain".
 * <br>3.1 2007-04-12 Honour margin of JTextComponent.
 * <br>3.0.1 2006-01-04 Non-editable text field border must be same as
 * disabled text field border.
 * <br>3.0 2005-12-08 Rewritten to create border images lazily and to not
 * hardcode the URL to the border images.
 * <br>2.0 2005-10-01 Turned into a background border.
 * <br>1.3 2005-06-20 1.2 Changed border insets to achieve baseline alignment with other
 * components.
 * <br>1.2 2005-04-10 1.1.1 Alignemnt changed for small size text fields.
 * <br>1.1 2005-03-27 1.1 Updated to take account of larger focus ring and of
 * margins.
 * <br>1.0  July 4, 2004  Created.
 */
public class QuaquaTextFieldBorder extends VisualMargin implements BackgroundBorder {
    /** Location of the border images. */
    private String imagesLocation;
    private String searchImagesLocation;
    private String smallSearchImagesLocation;
    /** Array with image bevel plainBorders.
     * This array is created lazily.
     **/
    private Border[] plainBorders;
    private Border[] searchBorders;
    private Border[] smallSearchBorders;
    
    private Border textFieldBackground = new Border() {
        public Insets getBorderInsets(Component c) {
            return new Insets(0,0,0,0);
        }
        public boolean isBorderOpaque() {
            return false;
        }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            if (c.isOpaque()) {
                g.setColor(UIManager.getColor("Panel.background"));
                g.fillRect(0, 0, width, height);
            }
            
            g.setColor(c.getBackground());
            Insets insets = getVisualMargin(c, new Insets(0,0,0,0));
            if (isSearchField(c)) {
                int arc = Math.min(
                        width - insets.left - insets.right - 8,
                        height - insets.top - insets.bottom - 7);
                
                g.fillRoundRect(
                        insets.left + 4,
                        insets.bottom + 3,
                        width - insets.left - insets.right - 8,
                        height - insets.top - insets.bottom - 7,
                        arc, arc
                        );
            } else {
                g.fillRect(
                        insets.left + 3,
                        insets.bottom + 3,
                        width - insets.left - insets.right - 6,
                        height - insets.top - insets.bottom - 7
                        );
            }
        }
    };
    
    
    /** Creates a new instance. */
    public QuaquaTextFieldBorder(String imagesLocation,
            String searchImagesLocation,
            String smallSearchImagesLocation) {
        super(3,3,2,3);
        this.imagesLocation = imagesLocation;
        this.searchImagesLocation = searchImagesLocation;
        this.smallSearchImagesLocation = smallSearchImagesLocation;
    }
    
    private static boolean isSmall(Component c) {
        return c.getFont().getSize() <= 11;
    }
    private static boolean isSearchField(Component c) {
        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            return jc.getClientProperty("Quaqua.TextField.style") != null &&
                    jc.getClientProperty("Quaqua.TextField.style").equals("search");
        }
        return false;
    }
    
    public Insets getBorderInsets(Component c, Insets insets) {
        insets = getVisualMargin(c, insets);
        Insets inner = isSmall(c) ?
            (isSearchField(c) ? new Insets(6, 10, 5, 10) : new Insets(6, 7, 5, 7)) :
                (isSearchField(c) ? new Insets(6, 9, 6, 9) : new Insets(6, 7, 6, 7));
        InsetsUtil.addTo(inner, insets);
        
        if (c instanceof JTextComponent) {
            Insets margin = ((JTextComponent) c).getMargin();
            if (margin != null) {
                InsetsUtil.addTo(margin, insets);
            }
        }
        
        return insets;
    }
    
    public boolean isBorderOpaque() {
        return false;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Insets margin = getVisualMargin(c, new Insets(0,0,0,0));
        Border border = getBorder(c);
        border.paintBorder(c, g,
                x + margin.left, y + margin.top,
                width - margin.left - margin.right,
                height - margin.top - margin.bottom
                );
        
        if (c instanceof JTextComponent) {
            Debug.paint(g, (JTextComponent) c, ((JTextComponent) c).getUI());
        }
    }
    
    public Border getBackgroundBorder() {
        return textFieldBackground;
    }
    
    private Border getBorder(Component c) {
        Border[] borders;
        if (isSearchField(c)) {
            if (isSmall(c)) {
                if (smallSearchBorders == null) {
                    smallSearchBorders = (Border[]) QuaquaBorderFactory.create(smallSearchImagesLocation, new Insets(11,13,11,13), 3, true);
                }
                borders = smallSearchBorders;
            } else {
                if (searchBorders == null) {
                    searchBorders = (Border[]) QuaquaBorderFactory.create(searchImagesLocation, new Insets(13,13,13,13), 3, true);
                }
                borders = searchBorders;
            }
        } else {
            if (plainBorders == null) {
                plainBorders = (Border[]) QuaquaBorderFactory.create(imagesLocation, new Insets(6,6,5,6), 3, true);
            }
            borders = plainBorders;
        }
        
        boolean isEditable;
        if (c instanceof JTextComponent) {
            isEditable = ((JTextComponent) c).isEditable();
        } else {
            isEditable = true;
        }

        if (QuaquaUtilities.isFocused(c)) {
            return borders[2];
        } else if (c.isEnabled() && isEditable) {
            return borders[0];
        } else {
            return borders[1];
        }
    }
    
    
    public static class UIResource extends QuaquaTextFieldBorder implements javax.swing.plaf.UIResource {
        public UIResource(String imagesLocation, String searchImagesLocation,
                String smallSearchImagesLocation) {
            super(imagesLocation, searchImagesLocation, smallSearchImagesLocation);
        }
    }
}
