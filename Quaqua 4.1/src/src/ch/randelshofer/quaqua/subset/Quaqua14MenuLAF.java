/*
 * @(#)Quaqua14MenuLAF.java  1.0  2005-09-10
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */
package ch.randelshofer.quaqua.subset;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.util.ButtonStateIcon;
import ch.randelshofer.quaqua.util.TextureColor;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.security.*;

/**
 * The Quaqua14MenuLAF is an extension for Apple's Aqua Look and Feel
 * for Java 1.4 on Mac OS X 10.3 (Panther). 
 * <p>
 * The Quaqua Look and Feel can not be used on other platforms than Mac OS X.
 * <p>
 * <b>Important:</b> This class is a cut down version of the
 * Quaqua14TigerLookAndFeel. It is for use by applications which don't need
 * (or don't work with) the whole of the Quaqua Look and Feel.
 * <p>
 *
 * <h3>Usage</h3>
 * Please use the <code>QuaquaManager</code> to activate this look and feel in
 * your application. Or use the generic <code>QuaquaLookAndFeel</code>. Both
 * are designed to automatically detect the appropriate Quaqua Look and Feel
 * implementation for current Java VM.
 *
 * @see QuaquaManager
 * @see QuaquaLookAndFeel
 *
 * @author Werner Randelshofer
 * @version 3.0 2005-09-06 Moved from Panther package into subset package.
 * <br>2.1 2005-08-25 QuaquaBrowserUI added.
 * <br>2.0 2005-06-21 Reworked.
 * <br>1.0 2004-10-26  Created.
 */
public class Quaqua14MenuLAF extends LookAndFeelProxy {
    protected final static String commonDir = "/ch/randelshofer/quaqua/images/";
    protected final static String jaguarDir = "/ch/randelshofer/quaqua/jaguar/images/";
    protected final static String pantherDir = "/ch/randelshofer/quaqua/panther/images/";
    protected final static String tigerDir = "/ch/randelshofer/quaqua/panther/images/";
    
    /**
     * The menu font (Lucida Grande Regular 14 pt) is used for text in menus and
     * window title bars.
     */
    protected static final FontUIResource MENU_FONT =
    new FontUIResource("Lucida Grande", Font.PLAIN, 14);
    
    /**
     * Holds a bug fixed version of the UIDefaults provided by the target
     * LookAndFeel.
     * @see #initialize
     * @see #getDefaults
     */
    private UIDefaults myDefaults;
    
    /**
     * Creates a new instance.
     */
    public Quaqua14MenuLAF() {
        String targetClassName = "apple.laf.AquaLookAndFeel";
        try {
            setTarget((LookAndFeel) Class.forName(targetClassName).newInstance());
        } catch (Exception e) {
            throw new InternalError(
            "Unable to instanciate target Look and Feel \""
            +targetClassName
            +"\". "+e.getMessage()
            );
        }
    }
    
    /**
     * Return a one line description of this look and feel implementation,
     * e.g. "The CDE/Motif Look and Feel".   This string is intended for
     * the user, e.g. in the title of a window or in a ToolTip message.
     */
    public String getDescription() {
        return "The Quaqua Tiger Menu Look and Feel for Java 1.4";
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
        return "Quaqua Menu-only LAF";
    }
    
    /**
     * UIManager.setLookAndFeel calls this method before the first
     * call (and typically the only call) to getDefaults().  Subclasses
     * should do any one-time setup they need here, rather than
     * in a static initializer, because look and feel class objects
     * may be loaded just to discover that isSupportedLookAndFeel()
     * returns false.
     *
     * @see #uninitialize
     * @see UIManager#setLookAndFeel
     */
    public void initialize() {
        // Note: We initialize in a privileged block, because if we are
        //       installed as a Standard Extension in the Java VM, we
        //       are allowed to access our resources (i.e. images),
        //       even then, when the calling application is not allowed
        //       to do so.
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                target.initialize();
                myDefaults = target.getDefaults();
                initClassDefaults(myDefaults);
                initComponentDefaults(myDefaults);
                return null;
            }
        });
    }
    /**
     * This method is called once by UIManager.setLookAndFeel to create
     * the look and feel specific defaults table.  Other applications,
     * for example an application builder, may also call this method.
     *
     * @see #initialize
     * @see #uninitialize
     * @see UIManager#setLookAndFeel
     */
    public UIDefaults getDefaults() {
        return myDefaults;
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
        String quaquaPantherPrefix = "ch.randelshofer.quaqua.panther.QuaquaPanther";
        String quaquaTigerPrefix = "ch.randelshofer.quaqua.panther.QuaquaTiger";
        
        // NOTE: Uncomment parts of the code below, to override additional
        // UI classes of the target look and feel.
        Object[] uiDefaults = {
            "MenuSeparatorUI", quaquaPantherPrefix + "SeparatorUI",
            "PopupMenuSeparatorUI", quaquaPantherPrefix + "SeparatorUI",
            "SeparatorUI", quaquaPantherPrefix + "SeparatorUI",
        };
        table.putDefaults(uiDefaults);
        
        // Popup menu fix only works fully when we have all AWT event permission
        boolean hasAllAWTPermission = true;
        try {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkPermission(sun.security.util.SecurityConstants.ALL_AWT_EVENTS_PERMISSION);
            }
        } catch (SecurityException e) {
            hasAllAWTPermission = false;
            
        }
        if (hasAllAWTPermission) {
            uiDefaults = new Object[] {
                "PopupMenuUI", quaquaPrefix +"14"+ "PopupMenuUI",
            };
        } else {
            uiDefaults = new Object[] {
                "PopupMenuUI", quaquaPrefix +"13"+"PopupMenuUI",
            };
        }
        table.putDefaults(uiDefaults);
        
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
    protected void initComponentDefaults(UIDefaults table) {
        Object[] objects;
        
        // Font defaults
        // --------------------------------------
        // Set font sizes according to default size style.
        Font menuFont = MENU_FONT;
        objects = new Object[] {
            "CheckBoxMenuItem.acceleratorFont", menuFont,
            "CheckBoxMenuItem.font", menuFont,
            
            "Menu.acceleratorFont", menuFont,
            "Menu.font", menuFont,
            
            "MenuBar.font", menuFont,
            
            "MenuItem.acceleratorFont", menuFont,
            "MenuItem.font", menuFont,
            
            "PopupMenu.font", menuFont,
            
            "RadioButtonMenuItem.acceleratorFont", menuFont,
            "RadioButtonMenuItem.font", menuFont,
        };
        table.putDefaults(objects);
        
        // Other component defaults
        // --------------------------------------
        // Shared colors
        ColorUIResource textForeground = new ColorUIResource(0x0);
        ColorUIResource menuSelectionForeground = new ColorUIResource(0xffffff);
        objects = new Object[] {
            "CheckBoxMenuItem.foreground", textForeground,
            "CheckBoxMenuItem.selectionForeground", menuSelectionForeground,
            
            "Menu.foreground", textForeground,
            "Menu.selectionForeground", menuSelectionForeground,
            
            "MenuItem.foreground", textForeground,
            "MenuItem.selectionForeground", menuSelectionForeground,
            
            "PopupMenu.foreground", textForeground,
            "PopupMenu.border", new UIDefaults.ProxyLazyValue("ch.randelshofer.quaqua.QuaquaMenuBorder"),
            
            "RadioButtonMenuItem.foreground", textForeground,
            "RadioButtonMenuItem.selectionForeground", menuSelectionForeground,
            
            "Separator.foreground", new ColorUIResource(139,139,139),
            "Separator.highlight", new ColorUIResource(243,243,243),
            "Separator.shadow", new ColorUIResource(213,213,213),
            //"Separator.border", new VisualMargin(),
            //"Separator.border", new BorderUIResource.EmptyBorderUIResource(3,3,3,3),
        };
        table.putDefaults(objects);
        
        // Menu defaults depending whether screen menu bar is in use
        // ---------------------------------------------------------
        // FIXME Implement a screen menu bar by myself. We lose too many features here.
        if (isUseScreenMenuBar()) {
            objects = new Object[] {
                "CheckBoxMenuItem.checkIcon", makeButtonStateIcon(commonDir+"CheckBoxMenuItem.icons.png", 6),
                
                "Menu.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                
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
                
                "MenuBar.background", new TextureColor.UIResource(0xf4f4f4, pantherDir+"MenuBar.texture.png"),
                "MenuBar.border", new BorderUIResource.MatteBorderUIResource(0,0,1,0,new Color(128,128,128)),
                "MenuBar.margin", new InsetsUIResource(1, 8, 1, 8),
                "MenuBar.shadow", null,
                
                "MenuItem.acceleratorSelectionForeground", menuSelectionForeground,
                "MenuItem.checkIcon", makeIcon(getClass(), commonDir+"MenuItem.checkIcon.png"),
                "MenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
                
                "RadioButtonMenuItem.checkIcon", makeButtonStateIcon(commonDir+"RadioButtonMenuItem.icons.png", 6),
                "RadioButtonMenuItem.border", new BorderUIResource.EmptyBorderUIResource(1,1,1,1),
            };
        }
        table.putDefaults(objects);
    }
    protected Icon[] makeIcons(String location, int count, boolean horizontal) {
        Icon[] icons = new Icon[count];
        
        BufferedImage[] images = Images.split(
        Toolkit.getDefaultToolkit().getImage(getClass().getResource(location)),
        count, horizontal
        );
        
        for (int i=0; i < count; i++) {
            icons[i] = new IconUIResource(new ImageIcon(images[i]));
        }
        return icons;
    }
    protected Object makeButtonStateIcon(final String location, final int states) {
        return new UIDefaults.LazyValue() {
            public Object createValue(UIDefaults table) {
                return createButtonStateIcon(location, states);
            }
        };
    }
    protected Icon createButtonStateIcon(final String location, final int states) {
        BufferedImage[] images = Images.split(
        Toolkit.getDefaultToolkit().getImage(getClass().getResource(location)),
        states, true
        );
        
        return new ButtonStateIcon(images);
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
}


