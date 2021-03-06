/*
 * @(#)Quaqua14TigerCrossPlatformLookAndFeel.java  2.2  2007-11-24
 *
 * Copyright (c) 2005-2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */
package ch.randelshofer.quaqua.tiger;

import ch.randelshofer.quaqua.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.security.*;

/**
 * The Quaqua14TigerCrossPlatformLookAndFeel provides bug fixes and enhancements for Apple's
 * Aqua Look and Feel for Java 1.4 on Mac OS X 10.4 (Tiger). 
 * <p>
 * The Quaqua Look and Feel can not be used on other platforms than Mac OS X.
 * <p>
 * <h3>Usage</h3>
 * Please use the <code>QuaquaManager</code> to activate this look and feel in
 * your application. Or use the generic <code>QuaquaLookAndFeel</code>. Both
 * are designed to autodetect the appropriate Quaqua Look and Feel
 * implementation for current Java VM.
 * <p>
 * @see QuaquaManager
 * @see QuaquaLookAndFeel
 *
 * @author Werner Randelshofer
 * @version 2.2 2007-11-24 Added support for Darwin.
 * <br>2.1 2006-05-07 Adjusted FileChooser.preview...-properties.
 * <br>2.0.1 2006-02-15 Fixed menu highlight color.
 * <br>2.0 2005-12-09 Reworked and renamed from Quaqua14TigerCrossPlatformLookAndFeel
 * to Quaqua14TigerCrossPlatformLookAndFeel.
 * <br>1.3 2005-11-27 SplitPaneUI added.
 * <br>1.2 2005-09-18 QuaquaScrollBarUI and QuaquaOptionPaneUI added.
 * GroupBox added.
 * <br>1.1 2005-08-27 Improved color fidelity of components.
 * <br>1.0.4 2005-07-10 Added QuaquaSpinnerUI.
 * <br>1.0.3 2005-06-20 Don't use ProxyLazyValue for QuaquaMenuBorder.
 * <br>1.0.2 2005-05-29 Fixes for Quaqua 3.0 beta 4.
 * <br>1.0 2005-05-15 Created.
 */
public class Quaqua14TigerCrossPlatformLookAndFeel extends Quaqua14TigerLookAndFeel {
    
    /**
     * Creates a new instance.
     */
    public Quaqua14TigerCrossPlatformLookAndFeel() {
        super(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    
    /**
     * Return a one line description of this look and feel implementation,
     * e.g. "The CDE/Motif Look and Feel".   This string is intended for
     * the user, e.g. in the title of a window or in a ToolTip message.
     */
    public String getDescription() {
        return "The Quaqua 14 Tiger Cross-Platform Look and Feel version "+QuaquaManager.getVersion();
    }
    
    protected void initSystemColorDefaults(UIDefaults table) {
        super.initSystemColorDefaults(table);
        
        if (! QuaquaManager.isOSX() &&
                QuaquaManager.getOS() != QuaquaManager.DARWIN) {
            boolean isBrushedMetal = isBrushedMetal();
            Object controlBackground = (isBrushedMetal)
            ? table.get("control")
            : makeTextureColor(0xf4f4f4, pantherDir+"Panel.texture.png");
            Object toolBarBackground = (isBrushedMetal)
            ? table.get("ToolBar.background")
            : makeTextureColor(0xf4f4f4, pantherDir+"ToolBar.texture.png");
            Object menuBackground = (isBrushedMetal)
            ? table.get("menu")
            : makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png");
            Object menuHighlight = makeTextureColor(0x3471cf, pantherDir+"MenuBar.texture.S.png");
            
            Object[] objects = {
                "window", controlBackground, /* Default color for the interior of windows */
                "control", controlBackground, /* Default color for controls (buttons, sliders, etc) */
                "menu", menuBackground, /* Default color for the interior of menus */
                "menuHighlight", menuHighlight, /* Default color for the interior of menus */
                
                // Quaqua specific 'system' colors
                "listHighlight", table.get("textHighlight"), /* List background color when selected */
                "listHighlightText", table.get("textHighlightText"), /* List color when selected */
                "listHighlightBorder", new ColorUIResource(0x808080), /* List color when selected */
            };
            table.putDefaults(objects);
        }
        
    }
    
    public boolean getSupportsWindowDecorations() {
        return true;
    }
    protected boolean isUseScreenMenuBar() {
        return false;
    }
    
    protected void initDesignDefaults(UIDefaults table) {
        super.initDesignDefaults(table);
        
        Color menuBackground = (Color) table.get("menu");
        
        Border rootPaneBorder =  new BorderUIResource.LineBorderUIResource(new Color(0xa5a5a5));
        
        Object[] objects = {
            // Set this to true, to sort files by type instead of by name
            "FileChooser.orderByType", Boolean.FALSE,
            "FileChooser.previewLabelForeground", new ColorUIResource(0x808080),
            "FileChooser.previewValueForeground", new ColorUIResource(0x000000),
            "FileChooser.previewLabelInsets",new InsetsUIResource(1,0,0,4),
            "FileChooser.previewLabelDelimiter","",
            
            
            "ColorChooser.colorPickerMagnifier", makeBufferedImage(commonDir+"ColorChooser.colorPickerMagnifierPC.png"),
            "ColorChooser.colorPickerHotSpot", new UIDefaults.ProxyLazyValue("java.awt.Point", new Object[] { new Integer(22), new Integer(22) }),
            "ColorChooser.colorPickerGlassRect", new UIDefaults.ProxyLazyValue("java.awt.Rectangle", new Object[] { new Integer(1), new Integer(1), new Integer(21), new Integer(21) }),
            // Pick point relative to hot spot
            "ColorChooser.colorPickerPickOffset", new UIDefaults.ProxyLazyValue("java.awt.Point", new Object[] { new Integer(-10), new Integer(-10) }),
            "ColorChooser.colorPickerCaptureRect", new UIDefaults.ProxyLazyValue("java.awt.Rectangle", new Object[] { new Integer(-13), new Integer(-13), new Integer(8), new Integer(8) }),
            "ColorChooser.colorPickerZoomRect", new UIDefaults.ProxyLazyValue("java.awt.Rectangle", new Object[] { new Integer(2), new Integer(2), new Integer(24), new Integer(24) }),
            
            "Menu.submenuPopupOffsetY", new Integer(-5),
            
            "PopupMenu.border", new BorderUIResource.CompoundBorderUIResource(
                    rootPaneBorder, BorderFactory.createMatteBorder(4,0,4,0, menuBackground)),
            
            "RootPane.frameBorder", rootPaneBorder,
            "RootPane.plainDialogBorder", rootPaneBorder,
            "RootPane.informationDialogBorder", rootPaneBorder,
            "RootPane.errorDialogBorder", rootPaneBorder,
            "RootPane.colorChooserDialogBorder", rootPaneBorder,
            "RootPane.fileChooserDialogBorder", rootPaneBorder,
            "RootPane.questionDialogBorder", rootPaneBorder,
            "RootPane.warningDialogBorder", rootPaneBorder,
            
            "Sheet.border", rootPaneBorder,
        };
        table.putDefaults(objects);
    }
    /**
     * Return a short string that identifies this look and feel, e.g.
     * "CDE/Motif".  This string should be appropriate for a menu item.
     * Distinct look and feels should have different names, e.g.
     * a subclass of MotifLookAndFeel that changes the way a few components
     * are rendered should be called "CDE/Motif My Way"; something
     * that would be useful to a user trying to select a L&F from a list
     * of names.
     */
    public String getName() {
        return "Quaqua Tiger Cross Platform";
    }
    
    protected void installKeyboardFocusManager() {
        try {
            KeyboardFocusManager.setCurrentKeyboardFocusManager(new Quaqua14KeyboardFocusManager());
        } catch (SecurityException ex) {
            System.err.print("Warning: "+this+" couldn't install QuaquaKeyboardFocusManager.");
            //ex.printStackTrace();
        }
    }
}


