/*
 * @(#)Quaqua14ColorChooserLAF.java  1.0.2  2006-04-23
 *
 * Copyright (c) 2005-2006 Werner Randelshofer
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
import ch.randelshofer.quaqua.util.SliderThumbIcon;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.net.*;
import java.security.*;

/**
 * The Quaqua14ColorChooserLAF is an extension for Apple's Aqua Look and Feel
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
 * @version 3.0.2 2006-04-23 "Labels" property removed. Labels are now added
 * directly to the properties table.
 * <br>3.0.1 2005-12-03 "Labels" property added.
 * <br>3.0 2005-09-06 Moved from Panther package into subset package.
 * <br>2.1 2005-08-25 QuaquaBrowserUI added.
 * <br>2.0 2005-06-21 Reworked.
 * <br>1.0 2004-10-26  Created.
 */
public class Quaqua14ColorChooserLAF extends LookAndFeelProxy {
    protected final static String commonDir = "/ch/randelshofer/quaqua/images/";
    protected final static String jaguarDir = "/ch/randelshofer/quaqua/jaguar/images/";
    protected final static String pantherDir = "/ch/randelshofer/quaqua/panther/images/";
    protected final static String tigerDir = "/ch/randelshofer/quaqua/panther/images/";
    
    /**
     * The system font (Lucida Grande Regular 13 pt) is used for text in
     * menus, dialogs, and full-size controls.
     */
    protected static final FontUIResource SYSTEM_FONT =
    new FontUIResource("Lucida Grande", Font.PLAIN, 13);
    /**
     * The small system font (Lucida Grande Regular 11 pt) is used for
     * informative text in alerts. It is also the default font for column
     * headings in lists, for help tags, and for small controls. You can also
     * use it to provide additional information about settings in various
     * windows, such as the QuickTime pane in System Preferences.
     */
    protected static final FontUIResource SMALL_SYSTEM_FONT =
    new FontUIResource("Lucida Grande", Font.PLAIN, 11);
    
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
    public Quaqua14ColorChooserLAF() {
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
        return "The Quaqua Tiger ColorChooser Look and Feel for Java 1.4";
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
        return "Quaqua ColorChooser-only LAF";
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
                initResourceBundle(myDefaults);
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
    protected void initResourceBundle(UIDefaults table) {
        try {
            Methods.invoke(table, "addResourceBundle", "ch.randelshofer.quaqua.Labels");
        } catch (NoSuchMethodException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("ch.randelshofer.quaqua.Labels");
            for (Enumeration i = bundle.getKeys(); i.hasMoreElements(); ) {
                String key = (String) i.nextElement();
                table.put(key, bundle.getObject(key));
            }
        }
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
            "ColorChooserUI", quaquaPrefix + "14"+"ColorChooserUI",
        };
        table.putDefaults(uiDefaults);
    }
    protected void initComponentDefaults(UIDefaults table) {
        Object[] objects;
        
        // visual margin
        // -------------
        int[] values = QuaquaManager.getProperty("Quaqua.visualMargin", new int[] { 1, 1, 1, 1});
        InsetsUIResource visualMargin = new InsetsUIResource(values[0], values[1], values[2], values[3]);

        // Font defaults
        // --------------------------------------
        Font systemFont = SYSTEM_FONT;
        Font smallSystemFont = SMALL_SYSTEM_FONT;
        objects = new Object[] {
            "ColorChooser.font", smallSystemFont,
            "ColorChooser.crayonsFont", systemFont,
        };
        table.putDefaults(objects);
        
        // Other component defaults
        // --------------------------------------
        // Shared colors
        ColorUIResource textForeground = new ColorUIResource(0x0);
        objects = new Object[] {
            // class names of default choosers
            "ColorChooser.defaultChoosers", new String[] {
                "ch.randelshofer.quaqua.colorchooser.ColorWheelChooser",
                "ch.randelshofer.quaqua.colorchooser.ColorSlidersChooser",
                "ch.randelshofer.quaqua.colorchooser.ColorPalettesChooser",
                "ch.randelshofer.quaqua.colorchooser.SwatchesChooser",
                "ch.randelshofer.quaqua.colorchooser.CrayonsChooser"
            },
            //"ColorChooser.background", ...,
            "ColorChooser.foreground", textForeground,
            //"ColorChooser.swatchesDefaultRecentColor", ...,
            //"ColorChooser.swatchesRecentSwatchSize", ...,
            "ColorChooser.swatchesSwatchSize", new DimensionUIResource(5,5),
            "ColorChooser.resetMnemonic", new Integer(-1),
            "ColorChooser.crayonsImage", new UIDefaults.LazyValue() {
                public Object createValue(UIDefaults table) {
                    return createImage(commonDir+"ColorChooser.crayons.png");
                }
            },
            "ColorChooser.textSliderGap", new Integer(0),
            "ColorChooser.colorPalettesIcon", makeButtonStateIcon(commonDir+"ColorChooser.colorPalettesIcons.png",3),
            "ColorChooser.colorSlidersIcon", makeButtonStateIcon(commonDir+"ColorChooser.colorSlidersIcons.png",3),
            "ColorChooser.colorSwatchesIcon", makeButtonStateIcon(commonDir+"ColorChooser.colorSwatchesIcons.png",3),
            "ColorChooser.colorWheelIcon", makeButtonStateIcon(commonDir+"ColorChooser.colorWheelIcons.png",3),
            "ColorChooser.crayonsIcon", makeButtonStateIcon(commonDir+"ColorChooser.crayonsIcons.png",3),
            "ColorChooser.imagePalettesIcon", makeButtonStateIcon(commonDir+"ColorChooser.imagePalettesIcons.png",3),
            
            // Note: The following colors are used in color lists.
            //       It is important that these colors are neutral (black, white
            //       or a shade of gray with saturation 0).
            //       If they aren't neutral, human perception of the color
            //       is negatively affected.
            "ColorChooser.listSelectionBackground", new ColorUIResource(0xd4d4d4),
            "ColorChooser.listSelectionForeground", new ColorUIResource(0x000000),
           
            // The visual margin is used to allow each component having room
            // for a cast shadow and a focus ring, and still supporting a
            // consistent visual arrangement of all components aligned to their
            // visualy perceived lines.
            "Component.visualMargin", visualMargin,

            "Slider.upThumbSmall", makeSliderThumbIcon(commonDir+"Slider.up.thumbs.png"),
            "Slider.leftThumbSmall", makeSliderThumbIcon(commonDir+"Slider.left.thumbs.png"),
        };
        table.putDefaults(objects);
    }
    protected URL getResource(String location) {
        URL url = getClass().getResource(location);
        if (url == null) {
            throw new InternalError("image resource missing: "+location);
        }
        return url;
    }
    
    protected Image createImage(String location) {
        return Toolkit.getDefaultToolkit().createImage(getResource(location));
    }
    protected Icon[] makeIcons(String location, int count, boolean horizontal) {
        Icon[] icons = new Icon[count];
        
        BufferedImage[] images = Images.split(
        createImage(location),
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
        createImage(location),
        states, true
        );
        
        return new ButtonStateIcon(images);
    }
    protected Object makeSliderThumbIcon(final String location) {
        return new UIDefaults.LazyValue() {
            public Object createValue(UIDefaults table) {
                BufferedImage[] images = Images.split(
                createImage(location),
                5, true
                );
                
                return new IconUIResource(new SliderThumbIcon(images));
            }
        };
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
