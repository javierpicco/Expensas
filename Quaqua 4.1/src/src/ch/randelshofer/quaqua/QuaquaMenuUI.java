/*
 * @(#)QuaquaMenuUI.java 1.2.1  2005-09-25
 *
 * Copyright (c) 2001 Werner Randelshofer
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

import ch.randelshofer.quaqua.BackgroundBorder;
import ch.randelshofer.quaqua.util.PaintableColor;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.View;
/**
 * A replacement for the AquaMenuUI.
 * <p>
 * This class does not fix any particular bug in the Mac LAF or the Aqua LAF.
 * It is just here to achieve a consistent look with the other Quaqua menu UI
 * classes.
 *
 * @author  Werner Randelshofer
 * @version 1.2.1 2005-09-25 Method paintBackground() did not always use a
 * texture.
 * <br>1.2 2005-08-26 Made menu opaque.
 * <br>1.1.1 2005-04-07 Method paintBackground() did not honour background
 * color of parent component when it is the topLevelMenu.
 * <br>1.1 2003-10-06 Layout code outplaced into class QuaquaMenuPainter
 * <br>1.0 2003-06-20 Created.
 */
public class QuaquaMenuUI extends BasicMenuUI implements QuaquaMenuPainterClient {
    // BasicMenuUI also uses this.
    //Handler handler;
    private int lastMnemonic = 0;
    private static boolean crossMenuMnemonic = true;

    public static ComponentUI createUI(JComponent x) {
        return new QuaquaMenuUI();
    }
    protected void installDefaults() {
        super.installDefaults();
	QuaquaUtilities.installProperty(menuItem, "opaque", Boolean.TRUE);
        //menuItem.setOpaque(true);
    }
    /*
    protected MenuDragMouseListener createMenuDragMouseListener(JComponent c) {
	return getHandler();
    }
    
    protected MouseInputListener createMouseInputListener(JComponent c) {
	return getHandler();
    }
    */
    protected void paintMenuItem(Graphics g, JComponent c,
    Icon checkIcon, Icon arrowIcon, Color background,
    Color foreground, int defaultTextIconGap) {
        QuaquaMenuPainter.getInstance().paintMenuItem(this, g, c, checkIcon,
        arrowIcon, background, foreground,
        disabledForeground,
        selectionForeground, defaultTextIconGap,
        acceleratorFont);
    }
    
    protected Dimension getPreferredMenuItemSize(JComponent c,
    Icon checkIcon,
    Icon arrowIcon,
    int defaultTextIconGap) {
        Dimension d = QuaquaMenuPainter.getInstance()
        .getPreferredMenuItemSize(c, checkIcon, arrowIcon, defaultTextIconGap, acceleratorFont);
        return d;
    }
    
    
    public void paintBackground(Graphics gr, JComponent component, int menuWidth, int menuHeight) {
        AbstractButton menuItem = (AbstractButton) component;
        
        if(menuItem.isOpaque()) {
            Graphics2D g = (Graphics2D) gr;
            Color oldColor = g.getColor();
            boolean isTopLevel = ((JMenu) menuItem).isTopLevelMenu();
            ButtonModel model = menuItem.getModel();
            boolean isSelected = model.isArmed() || (menuItem instanceof JMenu && model.isSelected());
            if (isSelected) {
                g.setPaint(PaintableColor.getPaint(selectionBackground, menuItem));
            } else {
                if (isTopLevel && component.getParent() != null) {
                    g.setPaint(PaintableColor.getPaint(component.getParent().getBackground(), menuItem));
                } else {
                    g.setPaint(PaintableColor.getPaint(menuItem.getBackground(), menuItem));
                }
            }
            g.fillRect(0,0, menuWidth, menuHeight);
            
            if (isTopLevel) {
                String bbName = (isSelected) ? "MenuBar.selectedBorder" : "MenuBar.border";
                if (UIManager.getBorder(bbName) instanceof BackgroundBorder) {
                    Border bb = ((BackgroundBorder) UIManager.getBorder(bbName)).getBackgroundBorder();
                    bb.paintBorder(component, gr, 0, 0, menuWidth, menuHeight);
                }
                
                Color shadow = UIManager.getColor("MenuBar.shadow");
                if (shadow != null) {
                    g.setColor(shadow);
                    g.fillRect(0, menuHeight - 1, menuWidth, 1);
                }
            }
            g.setColor(oldColor);
        }
    }

}

