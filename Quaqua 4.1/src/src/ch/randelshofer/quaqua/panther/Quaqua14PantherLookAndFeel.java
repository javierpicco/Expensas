/*
 * @(#)Quaqua14PantherLookAndFeel.java  3.6  2006-05-13
 *
 * Copyright (c) 2004-2006 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */
package ch.randelshofer.quaqua.panther;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.util.GroupBox;

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
 * The Quaqua14PantherLookAndFeel provides bug fixes and enhancments for Apple's
 * Aqua Look and Feel for Java 1.4 on Mac OS X 10.3 (Panther). 
 * <p>
 * The Quaqua Look and Feel can not be used on other platforms than Mac OS X.
 * <p>
 * <h3>Usage</h3>
 * Please use the <code>QuaquaManager</code> to activate this look and feel in
 * your application. Or use the generic <code>QuaquaLookAndFeel</code>. Both
 * are designed to autodetect the appropriate Quaqua Look and Feel
 * implementation.
 * <p>
 * @see QuaquaManager
 * @see QuaquaLookAndFeel
 *
 * @author Werner Randelshofer
 * @version 3.6 2006-05-13 Improved menu fidelity when screen menu bar is on. 
 * <br>3.5.1 2006-04-08 Added missing properties for TabbedPane.
 * <br>3.5 2006-02-05 Quaqua14PantherTabbedPaneUI added.
 * <br>3.4 2005-11-27 QuaquaSplitPaneUI added.
 * <br>3.3 2005-09-25 QuaquaScrollBarUI and QuaquaOptionPaneUI added.
 * Invocation of deriveColors()  removed. GroupBox added.
 * <br>3.2 2005-08-25 QuaquaBrowserUI added.
 * <br>3.1 2005-07-05 SpinnerUI added.
 * <br>3.0.2 2005-05-29 Fixes for Quaqua 3.0 beta 4.
 * <br>3.0.1 2005-04-17 Minor fixes.
 * <br>3.0 2005-03-19 Reworked.
 * <br>1.4 2005-03-05 Added QuaquaButtonUI. Fixed combo box icon. Replaced
 * property "Table.alternateBackground" by "Table.alternateBackground.0" and
 * "Table.alternateBackground.1".
 * <br>1.3.1 2005-03-02 Added missing key binding for TAB key in multiline
 * text fields. Changed borders of PasswordTextField and
 * FormattedTextField.
 * <br>1.3 2004-12-28 Fixed vertical alignment of menu items. Changed
 * icon of radio button menu items to be round instead of diamond shaped.
 * <br>1.2 2004-12-03 Set input maps for text fields.
 * <br>1.1.2 2004-09-11 Catch AccessControlException when accessing
 * System properties. Replaced all method invocations to method QuaquaManager.getProperty
 * to QuaquaManager.getProperty.
 * <br>1.1.1 2004-08-29 Added ancestorInputMap to ComboBoxUI.
 * <br>1.1 2004-07-31 Selection colors of lists and tables are now not
 * overriden anymore, to exactly match the Panther color scheme. This had to
 * be done, because many applications require that the foreground color of
 * a selection is darker than the background color (in Panther, the foreground
 * is white).
 * <br>1.0 2004-06-21 Created.
 */
public class Quaqua14PantherLookAndFeel extends BasicQuaquaLookAndFeel {
    
    /**
     * Creates a new instance.
     */
    public Quaqua14PantherLookAndFeel() {
        // Our target look and feel is Apple's AquaLookAndFeel.
        super("apple.laf.AquaLookAndFeel");
    }
    
    /**
     * Return a one line description of this look and feel implementation,
     * e.g. "The CDE/Motif Look and Feel".   This string is intended for
     * the user, e.g. in the title of a window or in a ToolTip message.
     */
    public String getDescription() {
        return "The Quaqua 14 Panther Look and Feel version "+QuaquaManager.getVersion();
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
        return "Quaqua Panther";
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
        String quaquaJaguarPrefix = "ch.randelshofer.quaqua.jaguar.QuaquaJaguar";
        String quaquaPantherPrefix = "ch.randelshofer.quaqua.panther.QuaquaPanther";
        String quaqua14PantherPrefix = "ch.randelshofer.quaqua.panther.Quaqua14Panther";
        
        // NOTE: Change code below, to override different
        // UI classes of the target look and feel.
        Object[] uiDefaults = {
            "BrowserUI", quaquaPrefix + "BrowserUI",
            "ButtonUI", quaquaPrefix + "ButtonUI",
            "CheckBoxUI", quaquaPrefix + "CheckBoxUI",
            "ColorChooserUI", quaquaPrefix + "14"+"ColorChooserUI",
            "FileChooserUI", quaquaPantherPrefix + "FileChooserUI",
            "FormattedTextFieldUI", quaquaPrefix + "14" + "FormattedTextFieldUI",
            "RadioButtonUI", quaquaPrefix + "RadioButtonUI",
            "ToggleButtonUI", quaquaPrefix + "ToggleButtonUI",
            "SeparatorUI", quaquaPantherPrefix + "SeparatorUI",
            /*  "ProgressBarUI", basicPrefix + "ProgressBarUI",*/
            "ScrollBarUI", quaquaPrefix + "ScrollBarUI",
            "ScrollPaneUI", quaquaPrefix + "ScrollPaneUI",
                "SplitPaneUI", quaquaPrefix + "SplitPaneUI",
                   //"SliderUI", basicPrefix + "SliderUI",
            "SpinnerUI", quaquaPrefix +"14"+"SpinnerUI",
                "TabbedPaneUI", quaqua14PantherPrefix + "TabbedPaneUI",
            "ToolBarSeparatorUI", quaquaPrefix + "ToolBarSeparatorUI",
            "PopupMenuSeparatorUI", quaquaPantherPrefix + "SeparatorUI",
            "TabbedPaneUI", quaqua14PantherPrefix + "TabbedPaneUI",
            
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
            
            // Do not create a RootPaneUI on our own, until we also
            // create our own ButtonUI. Aqua's RootPaneUI is responsible
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
        
        
        
        // FIXME Menu related workarounds work only if useScreenMenuBar is off.
        String property;
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
    
    protected void initDesignDefaults(UIDefaults table) {
        boolean isBrushedMetal = isBrushedMetal();
        ColorUIResource disabledForeground = new ColorUIResource(128, 128, 128);
        ColorUIResource menuSelectionForeground = new ColorUIResource(255,255,255);
        Object panelBackground = (isBrushedMetal) ? 
            table.get("TabbedPane.background") : 
            makeTextureColor(0xf4f4f4, pantherDir+"Panel.texture.png");

        Object[] objects = {
            "FileChooser.cellTipOrigin", new Point(18,0),
            
            "Frame.titlePaneBorders", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.png", new Insets(0,0,22,0), 2, true),
            "Frame.titlePaneBorders.small", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.small.png", new Insets(0,0,16,0), 2, true),
            "Frame.titlePaneBorders.mini", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.mini.png", new Insets(0,0,12,0), 2, true),
            "Frame.titlePaneBorders.vertical", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.vertical.png", new Insets(0,0,0,22), 2, false),
            "Frame.titlePaneBorders.vertical.small", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.vertical.small.png", new Insets(0,0,0,16), 2, false),
            "Frame.titlePaneBorders.vertical.mini", makeImageBevelBorders(pantherDir+"Frame.titlePaneBorders.vertical.mini.png", new Insets(0,0,0,12), 2, false),
            
            "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6),
            "RadioButtonMenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
            
            // Performance issue: Use Apple's texture colors instead of our own.
            //"RootPane.background", new TextureColorUIResource(0xf4f4f4, getClass().getResource(pantherDir+"Panel.texture.png")),
            "RootPane.background", panelBackground,
            
            
            "Separator.foreground", new ColorUIResource(139,139,139),
            "Separator.highlight", new ColorUIResource(243,243,243),
            "Separator.shadow", new ColorUIResource(213,213,213),
            "Separator.border", new VisualMargin(),
            
            "TabbedPane.disabledForeground", disabledForeground,
            "TabbedPane.tabInsets", new InsetsUIResource(1, 10, 4, 9),
            "TabbedPane.selectedTabPadInsets", new InsetsUIResource(2, 2, 2, 1),
            "TabbedPane.tabAreaInsets", new InsetsUIResource(4, 16, 0, 16),
            "TabbedPane.contentBorderInsets", new InsetsUIResource(5, 6, 6, 6),
            "TabbedPane.background", (isBrushedMetal) ? table.get("TabbedPane.background") : makeTextureColor(0xf4f4f4, pantherDir+"Panel.texture.png"),
            "TabbedPane.tabLayoutPolicy", new Integer(isJaguarTabbedPane() ? JTabbedPane.WRAP_TAB_LAYOUT : JTabbedPane.SCROLL_TAB_LAYOUT),
            "TabbedPane.wrap.disabledForeground", disabledForeground,
            "TabbedPane.wrap.tabInsets", new InsetsUIResource(1, 10, 4, 9),
            "TabbedPane.wrap.selectedTabPadInsets", new InsetsUIResource(2, 2, 2, 1),
            "TabbedPane.wrap.tabAreaInsets", new InsetsUIResource(4, 16, 0, 16),
            "TabbedPane.wrap.contentBorderInsets", new InsetsUIResource(2, 3, 3, 3),
            "TabbedPane.wrap.background", (isBrushedMetal) ? table.get("TabbedPane.background") : makeTextureColor(0xf4f4f4, pantherDir+"Panel.texture.png"),
            "TabbedPane.scroll.selectedTabPadInsets", new InsetsUIResource(0,0,0,0),
            "TabbedPane.scroll.tabRunOverlay", new Integer(0),
            "TabbedPane.scroll.tabInsets", new InsetsUIResource(1, 7, 2, 7),
            "TabbedPane.scroll.smallTabInsets", new InsetsUIResource(1, 5, 2, 5),
            "TabbedPane.scroll.outerTabInsets", new InsetsUIResource(1, 11, 2, 11),
            "TabbedPane.scroll.smallOuterTabInsets", new InsetsUIResource(1, 9, 2, 9),
            "TabbedPane.scroll.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2),
            "TabbedPane.scroll.tabAreaInsets", new InsetsUIResource(1, 16, 1, 16),
            "TabbedPane.scroll.contentBorder", makeImageBevelBorder(
            commonDir+"GroupBox.png", new Insets(7,7,7,7), true
            ),
            "TabbedPane.scroll.emptyContentBorder", makeImageBevelBorder(
            commonDir+"GroupBox.empty.png", new Insets(7,7,7,7), true
            ),
            "TabbedPane.scroll.tabBorders", makeImageBevelBorders(commonDir+"Toggle.borders.png",
            new Insets(8, 10, 15, 10), 10, true
            ),
            "TabbedPane.scroll.tabFocusRing", makeImageBevelBorder(commonDir+"Toggle.focusRing.png",
            new Insets(8, 10, 15, 10), true
            ),
            "TabbedPane.scroll.eastTabBorders", makeImageBevelBorders(commonDir+"Toggle.east.borders.png",
            new Insets(8, 1, 15, 10), 10, true
            ),
            "TabbedPane.scroll.eastTabFocusRing", makeImageBevelBorder(commonDir+"Toggle.east.focusRing.png",
            new Insets(8, 4, 15, 10), true
            ),
            "TabbedPane.scroll.centerTabBorders", makeImageBevelBorders(commonDir+"Toggle.center.borders.png",
            new Insets(8, 0, 15, 1), 10, true
            ),
            "TabbedPane.scroll.centerTabFocusRing", makeImageBevelBorder(commonDir+"Toggle.center.focusRing.png",
            new Insets(8, 4, 15, 4), false
            ),
            "TabbedPane.scroll.westTabBorders", makeImageBevelBorders(commonDir+"Toggle.west.borders.png",
            new Insets(8, 10, 15, 1), 10, true
            ),
            "TabbedPane.scroll.westTabFocusRing", makeImageBevelBorder(commonDir+"Toggle.west.focusRing.png",
            new Insets(8, 10, 15, 4), true
            ),

            "TitledBorder.border", new GroupBox(),
            "TitledBorder.titleColor", new ColorUIResource(0x303030),
            
            "ToolBar.background", (isBrushedMetal) ? table.get("ToolBar.background") : makeTextureColor(0xf4f4f4, pantherDir+"ToolBar.texture.png"),
            
            "ToolBarSeparator.foreground", new ColorUIResource(0x808080),
        };
        table.putDefaults(objects);
        
        // FIXME Implement a screen menu bar by myself. We lose too many features here.
        if (isUseScreenMenuBar()) {
            objects = new Object[] {
                "CheckBoxMenuItem.checkIcon", makeButtonStateIcon(commonDir+"CheckBoxMenuItem.icons.png", 6, new Rectangle(5,1,17,12)),
                "CheckBoxMenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,0),
                
                "Menu.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png", new Point(1,0)),
                "Menu.arrowIcon", makeButtonStateIcon(commonDir+"MenuItem.arrowIcons.png", 2, new Rectangle(-12,1,0,12)),
                "Menu.border", new BorderUIResource.EmptyBorderUIResource(0,5,2,4),
                "Menu.margin", new InsetsUIResource(0, 8, 0, 8),
                "Menu.menuPopupOffsetX", new Integer(0),
                "Menu.menuPopupOffsetY", new Integer(1),
                "Menu.submenuPopupOffsetX", new Integer(0),
                "Menu.submenuPopupOffsetY", new Integer(-5),
                
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png", new Point(1,0)),
                "MenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,5,2,0),
                
                "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6, new Rectangle(5,0,17,12)),
                "RadioButtonMenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,0),
                
                "PopupMenu.border", new UIDefaults.ProxyLazyValue("ch.randelshofer.quaqua.QuaquaMenuBorder"),
                "PopupMenu.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "PopupMenu.foreground", new ColorUIResource(Color.black),
            };
        } else {
            objects = new Object[] {
                "CheckBoxMenuItem.checkIcon", makeButtonStateIcon(commonDir+"CheckBoxMenuItem.icons.png", 6, new Point(0,1)),
                "CheckBoxMenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,3),
                "CheckBoxMenuItem.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "CheckBoxMenuItem.foreground", new ColorUIResource(0x000000),
                
                "Menu.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                "Menu.arrowIcon", makeButtonStateIcon(commonDir+"MenuItem.arrowIcons.png", 2, new Point(0,1)),
                "Menu.margin", new InsetsUIResource(0, 8, 0, 8),
                "Menu.menuPopupOffsetX", new Integer(0),
                "Menu.menuPopupOffsetY", new Integer(0),
                "Menu.submenuPopupOffsetX", new Integer(0),
                "Menu.submenuPopupOffsetY", new Integer(-5),
                "Menu.useMenuBarBackgroundForTopLevel", Boolean.TRUE,
                "Menu.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,3),
                "Menu.selectionBackground", table.get("MenuItem.selectionBackground"),
                "Menu.selectionForeground", table.get("MenuItem.selectionForeground"),
                "Menu.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "Menu.foreground", new ColorUIResource(0x000000),
                
                "MenuBar.border", makeImageBevelBackgroundBorder(tigerDir+"MenuBar.border.png", new Insets(10,0,11,0), new Insets(0,0,0,0), true),
                "MenuBar.selectedBorder", makeImageBevelBackgroundBorder(tigerDir+"MenuBar.selectedBorder.png", new Insets(1,0,20,0), new Insets(0,0,0,0), true),
                "MenuBar.margin", new InsetsUIResource(1, 8, 2, 8),
                "MenuBar.shadow", null,
                "MenuBar.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "MenuBar.foreground", new ColorUIResource(0x000000),
                
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                "MenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,3),
                "MenuItem.acceleratorSelectionForeground", menuSelectionForeground,
                "MenuItem.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "MenuItem.foreground", new ColorUIResource(0x000000),
                
                "PopupMenu.border", new UIDefaults.ProxyLazyValue("ch.randelshofer.quaqua.QuaquaMenuBorder"),
                "PopupMenu.background", makeTextureColor(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "PopupMenu.foreground", new ColorUIResource(Color.black),
                
                "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6),
                "RadioButtonMenuItem.border", new BorderUIResource.EmptyBorderUIResource(0,0,2,3),
                "RadioButtonMenuItem.background", panelBackground,
                "RadioButtonMenuItem.foreground", new ColorUIResource(0x000000),
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


