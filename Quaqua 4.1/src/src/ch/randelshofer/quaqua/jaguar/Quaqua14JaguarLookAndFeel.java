/*
 * @(#)Quaqua14JaguarLookAndFeel.java  3.4.1  2006-10-25
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
package ch.randelshofer.quaqua.jaguar;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.AlphaColorUIResource;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.security.*;

/**
 * The Quaqua14JaguarLookAndFeel is an extension for Apple's Aqua Look and Feel
 * for Java 1.4 on Mac OS X 10.0 through 10.2 (Jaguar). 
 * <p>
 * The Quaqua Look and Feel can not be used on other platforms than Mac OS X.
 * <p>
 * <h3>Usage</h3>
 * Please use the <code>QuaquaManager</code> to activate this look and feel in
 * your application. Or use the generic <code>QuaquaLookAndFeel</code>. Both
 * are designed to automatically detect the appropriate Quaqua Look and Feel
 * implementation.
 *
 * @see QuaquaManager
 * @see QuaquaLookAndFeel
 *
 * @author Werner Randelshofer
 * @version 3.4.1 2006-10-25 Fixed TabbedPaneUI class name.
 * <br>3.4 2005-09-27 QuaquaSplitPaneUI added.
 * <br>3.3 2005-09-25 QuaquaScrollBarUI and QuaquaOptionPaneUI added.
 * 3.2 2005-08-25 QuaquaBrowserUI added.
 * <br>3.1 2005-07-05 SpinnerUI added.
 * <br>3.0.3 2005-06-18 TabbedPane with jaguar design is now the default.
 * <br>3.0.2 2005-05-29 Fixes for Quaqua 3.0 beta 4.
 * <br>3.0 2005-04-05 Reworked.
 * <br>1.2.1 2005-03-02 Added missing key binding for TAB key in
 * multiline text fields. Changed borders of PasswordTextField and
 * FormattedTextField.
 * <br>1.2 2004-12-03 Set input maps for text fields.
 * <br>1.1.2 2004-09-10 Catch AccessControlException when accessing System
 * properties. Replaced all method invocations to method QuaquaManager.getProperty
 * to QuaquaManager.getProperty.
 * <br>1.1.1 2004-08-29 Added ancestorInputMap to ComboBoxUI.
 * <br>1.3 2004-05-02 Added component defaults for BrowserUI.
 * Menu popup offsets set to improve location of sub
 * menus. QuaquaComboBoxUI added.
 * <br>1.0 2004-02-06 Created.
 */
public class Quaqua14JaguarLookAndFeel extends BasicQuaquaLookAndFeel {
    /**
     * Creates a new instance.
     */
    public Quaqua14JaguarLookAndFeel() {
        // Our target look and feel is Apple's AquaLookAndFeel.
        super("apple.laf.AquaLookAndFeel");
    }
    
    /**
     * Return a one line description of this look and feel implementation,
     * e.g. "The CDE/Motif Look and Feel".   This string is intended for
     * the user, e.g. in the title of a window or in a ToolTip message.
     */
    public String getDescription() {
        return "The Quaqua 14 Jaguar Look and Feel version "+QuaquaManager.getVersion();
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
        return "Quaqua Jaguar";
    }
    public boolean getSupportsWindowDecorations() {
        return true;
    }
    
    /**
     * Initialize the uiClassID to BasicComponentUI mapping.
     * The JComponent classes define their own uiClassID constants
     * (see AbstractComponent.getUIClassID).  This table must
     * map those constants to a BasicComponentUI class of the
     * appropriate type.
     *
     * @see #getDefaults
     */
    protected void initClassDefaults(UIDefaults table) {
        String basicPrefix = "javax.swing.plaf.basic.Basic";
        String quaquaPrefix = "ch.randelshofer.quaqua.Quaqua";
        String quaqua14JaguarPrefix = "ch.randelshofer.quaqua.jaguar.Quaqua14Jaguar";
        String quaquaJaguarPrefix = "ch.randelshofer.quaqua.jaguar.QuaquaJaguar";
        
        // NOTE: Change code below, to override different
        // UI classes of the target look and feel.
        Object[] uiDefaults = {
            "BrowserUI", quaquaPrefix + "BrowserUI",
            "ButtonUI", quaquaPrefix + "ButtonUI",
            "CheckBoxUI", quaquaPrefix + "CheckBoxUI",
            "ColorChooserUI", quaquaPrefix + "14ColorChooserUI",
            "FileChooserUI", quaquaJaguarPrefix + "FileChooserUI",
            
            "FormattedTextFieldUI", quaquaPrefix + "14"+"FormattedTextFieldUI",
            "RadioButtonUI", quaquaPrefix + "RadioButtonUI",
            "ToggleButtonUI", quaquaPrefix + "ToggleButtonUI",
            "SeparatorUI", quaquaJaguarPrefix + "SeparatorUI",
            /*  "ProgressBarUI", basicPrefix + "ProgressBarUI",*/
            "ScrollBarUI", quaquaPrefix + "ScrollBarUI",
            "ScrollPaneUI", quaquaPrefix + "ScrollPaneUI",
            "SplitPaneUI", quaquaPrefix + "SplitPaneUI",
            // "SliderUI", basicPrefix + "SliderUI",
            "SpinnerUI", quaquaPrefix + "14"+"SpinnerUI",
            "ToolBarSeparatorUI", quaquaPrefix + "ToolBarSeparatorUI",
            "PopupMenuSeparatorUI", quaquaJaguarPrefix + "SeparatorUI",
            //"TabbedPaneUI", quaquaPrefix + "TabbedPaneUI",
            
            "TextAreaUI", quaquaPrefix + "TextAreaUI",
            "TextFieldUI", quaquaPrefix + "TextFieldUI",
            "PasswordFieldUI", quaquaPrefix + "PasswordFieldUI",
            "TextPaneUI", quaquaPrefix + "TextPaneUI",
            "EditorPaneUI", quaquaPrefix + "EditorPaneUI",
            "TreeUI", quaquaPrefix + "TreeUI",
            "LabelUI", quaquaPrefix + "LabelUI",
            "ListUI", quaquaPrefix + "14"+"ListUI",
            "ToolBarUI", quaquaPrefix + "ToolBarUI",
            /* "ToolTipUI", basicPrefix + "ToolTipUI",*/
            "ComboBoxUI", quaquaPrefix + "ComboBoxUI",
            "TableUI", quaquaPrefix + "TableUI",
            "TableHeaderUI", quaquaPrefix + "TableHeaderUI",
            /* "InternalFrameUI", basicPrefix + "InternalFrameUI",*/
            //"DesktopPaneUI", quaquaPrefix + "DesktopPaneUI",
            /*"DesktopIconUI", basicPrefix + "DesktopIconUI",*/
            "OptionPaneUI", quaquaPrefix + "OptionPaneUI",
            "PanelUI", quaquaPrefix + "PanelUI",
            "ViewportUI", quaquaPrefix + "ViewportUI",
            // Do not create a RootPaneUI on our own, until we have
            // also implemented a ButtonUI. Aqua's RootPaneUI is responsible
            // for updating the border of the ButtonUI, when it is the default,
            // and for propagating window activation/dectivation events to
            // all the child components of a window.
            "RootPaneUI", quaquaPrefix + "14RootPaneUI",
        };
        table.putDefaults(uiDefaults);
        
        // Popup menu fix only works fully when we have all AWT event permission
        SecurityManager security = System.getSecurityManager();
        try {
            if (security != null) {
                security.checkPermission(sun.security.util.SecurityConstants.ALL_AWT_EVENTS_PERMISSION);
            }
            uiDefaults = new Object[] {
                "PopupMenuUI", quaquaPrefix +"14"+ "PopupMenuUI",
            };
        } catch (SecurityException e) {
            uiDefaults = new Object[] {
                "PopupMenuUI", quaquaPrefix +"13"+ "PopupMenuUI",
            };
        }
        table.putDefaults(uiDefaults);
        
        
        // Jaguar design for tabbed panes can be chosen by setting
        // a system property.
        if (isJaguarTabbedPane()) {
            uiDefaults = new Object[] {
                "TabbedPaneUI", quaqua14JaguarPrefix + "TabbedPaneUI",
            };
            
            table.putDefaults(uiDefaults);
        }
        
        // FIXME Menu related workarounds work only if useScreenMenuBar is off.
        if (! isUseScreenMenuBar()) {
            uiDefaults = new Object[] {
                "MenuBarUI", quaquaPrefix + "MenuBarUI",
                "MenuUI", quaquaPrefix + "MenuUI",
                "MenuItemUI", quaquaPrefix + "MenuItemUI",
                "CheckBoxMenuItemUI", quaquaPrefix + "MenuItemUI",
                "RadioButtonMenuItemUI", quaquaPrefix + "MenuItemUI"
            };
            
            table.putDefaults(uiDefaults);
        }
    }
    protected boolean isJaguarTabbedPane() {
        String property;
        try {
            property = QuaquaManager.getProperty("Quaqua.tabLayoutPolicy");
            if (property == null) {
                property = QuaquaManager.getProperty("Quaqua.TabbedPane.design", "auto");
            }
        } catch (AccessControlException e) {
            property = "auto";
        }
        return property.equals("auto")
        || property.equals("jaguar")
        || property.equals("wrap");
    }
    private boolean isBrushedMetal() {
        String property;
        try {
            property = QuaquaManager.getProperty("apple.awt.brushMetalLook", "false");
        } catch (AccessControlException e) {
            property = "false";
        }
        return property.equals("true");
    }
    private boolean isUseScreenMenuBar() {
        String property;
        try {
            property = QuaquaManager.getProperty("apple.laf.useScreenMenuBar", "false");
        } catch (AccessControlException e) {
            property = "false";
        }
        return property.equals("true");
    }
    /**
     * Load the SystemColors into the defaults table.  The keys
     * for SystemColor defaults are the same as the names of
     * the public fields in SystemColor.  If the table is being
     * created on a native Windows platform we use the SystemColor
     * values, otherwise we create color objects whose values match
     * the defaults Windows95 colors.
     */
    protected void initSystemColorDefaults(UIDefaults table) {
        super.initSystemColorDefaults(table);
        
        boolean isBrushedMetal = isBrushedMetal();
        Object controlBackground = (isBrushedMetal)
        ? table.get("control")
        : makeTextureColor(0xf4f4f4, jaguarDir+"Panel.texture.png");
        Object toolBarBackground = (isBrushedMetal)
        ? table.get("ToolBar.background")
        : makeTextureColor(0xf4f4f4, jaguarDir+"ToolBar.texture.png");
        Object menuBackground = (isBrushedMetal)
        ? table.get("menu")
        : makeTextureColor(0xf4f4f4, jaguarDir+"MenuBar.texture.png");
        
        Object[] objects = {
            "window", controlBackground, /* Default color for the interior of windows */
            "control", controlBackground, /* Default color for controls (buttons, sliders, etc) */
            "menu", menuBackground, /* Default color for the interior of menus */
            
            // Quaqua specific 'system' colors
            "listHighlight", table.get("textHighlight"), /* List background color when selected */
            "listHighlightText", table.get("textHighlightText"), /* List color when selected */
            "listHighlightBorder", new ColorUIResource(0x808080), /* List color when selected */
        };
        table.putDefaults(objects);
        
    }
    
    protected void initDesignDefaults(UIDefaults table) {
        boolean isBrushedMetal = isBrushedMetal();
        
        Object toolBarBackground = (isBrushedMetal)
        ? table.get("ToolBar.background")
        : makeTextureColor(0xf4f4f4, jaguarDir+"ToolBar.texture.png");
        
        Object[] objects = {
            "Frame.titlePaneBorders", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.png", new Insets(0,0,22,0), 2, true),
            "Frame.titlePaneBorders.small", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.small.png", new Insets(0,0,16,0), 2, true),
            "Frame.titlePaneBorders.mini", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.mini.png", new Insets(0,0,12,0), 2, true),
            "Frame.titlePaneBorders.vertical", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.vertical.png", new Insets(0,0,0,22), 2, false),
            "Frame.titlePaneBorders.vertical.small", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.vertical.small.png", new Insets(0,0,0,16), 2, false),
            "Frame.titlePaneBorders.vertical.mini", makeImageBevelBorders(jaguarDir+"Frame.titlePaneBorders.vertical.mini.png", new Insets(0,0,0,12), 2, false),
            
            "Separator.foreground", new ColorUIResource(139,139,139),
            "Separator.highlight", new ColorUIResource(243,243,243),
            "Separator.shadow", new ColorUIResource(213,213,213),
            "Separator.border", new VisualMargin(),
            
            "ToolBar.background", toolBarBackground,
        };
        table.putDefaults(objects);
        
        // FIXME Implement a screen menu bar by myself. We lose too many features here.
        if (isUseScreenMenuBar()) {
            objects = new Object[] {
                "CheckBoxMenuItem.checkIcon", makeButtonStateIcon(commonDir+"CheckBoxMenuItem.icons.png", 6),
                
                "Menu.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                
                "PopupMenu.border", new UIDefaults.ProxyLazyValue("ch.randelshofer.quaqua.QuaquaMenuBorder"),
                
                "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6),
            };
        } else {
            objects = new Object[] {
                "CheckBoxMenuItem.checkIcon", makeButtonStateIcon(commonDir+"CheckBoxMenuItem.icons.png", 6),
                "CheckBoxMenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
                
                "Menu.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                "Menu.margin", new InsetsUIResource(0, 8, 0, 8),
                "Menu.menuPopupOffsetX", new Integer(0),
                "Menu.menuPopupOffsetY", new Integer(0),
                "Menu.submenuPopupOffsetX", new Integer(0),
                "Menu.submenuPopupOffsetY", new Integer(-5),
                "Menu.useMenuBarBackgroundForTopLevel", Boolean.TRUE,
                "Menu.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
                
                "MenuBar.border", new BorderUIResource.MatteBorderUIResource(0,0,1,0,new Color(128,128,128)),
                "MenuBar.margin", new InsetsUIResource(1, 8, 1, 8),
                "MenuBar.shadow", null,
                
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                "MenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
                
                "PopupMenu.border", new UIDefaults.ProxyLazyValue("ch.randelshofer.quaqua.QuaquaMenuBorder"),
                
                "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6),
                "RadioButtonMenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
            };
        }
        table.putDefaults(objects);
        if (isJaguarTabbedPane()) {
            objects = new Object[] {
                "TabbedPane.tabInsets", new InsetsUIResource(1, 10, 4, 9),
                "TabbedPane.selectedTabPadInsets", new InsetsUIResource(2, 2, 2, 1),
                "TabbedPane.tabAreaInsets", new InsetsUIResource(4, 16, 0, 16),
                "TabbedPane.contentBorderInsets", new InsetsUIResource(5, 6, 6, 6),
                //"TabbedPane.border", new VisualMargin(3,3,5,3),
            };
        } else {
            objects = new Object[] {
                "TabbedPane.background", new AlphaColorUIResource(0,0,0,0),
            };
        }
        table.putDefaults(objects);
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