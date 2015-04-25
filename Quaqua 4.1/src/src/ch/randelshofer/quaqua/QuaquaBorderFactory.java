/*
 * @(#)BorderFactory.java  2.0  2007-10-30
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

package ch.randelshofer.quaqua;

import ch.randelshofer.quaqua.QuaquaButtonBorder;
import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.BackgroundBorderUIResource;
import ch.randelshofer.quaqua.util.ImageBevelBorder13;
import ch.randelshofer.quaqua.util.ImageBevelBorder14;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.lang.reflect.*;
/**
 * Creates an ImageBevelBorder instance optimized for this JVM.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2007-10-30 Dropped support for Java 1.3.
 * <br>1.3 2005-01-04 Method createSmallSquareBorder added.
 * <br>1.2 2005-11-30 Fixed broken creation of CachedImageBevelBorder.
 * <br>1.1 2005-09-07 Support for caching image bevel borders added.
 * <br>1.0  25 April 2005  Created.
 */
public class QuaquaBorderFactory {
    
    /**
     * Prevent instance creation of the factory.
     */
    private QuaquaBorderFactory() {
        //1.4.2_05
    }
    
    /** Creates a new instance of an ImageBevelBorder optimized for this JVM. */
    public static Border create(Image img, Insets borderInsets) {
        return create(img, borderInsets, borderInsets);
    }
    
    /**
     * Creates a new instance of an ImageBevelBorder with the given image and insets.
     * The image has different insets than the border.
     */
    public static Border create(Image img, Insets imageInsets, Insets borderInsets) {
        return create(img, imageInsets, borderInsets, false, true);
    }
    /**
     * Creates a new instance of an ImageBevelBorder with the given image and insets.
     * The image has different insets than the border.
     */
    public static Border create(Image img, Insets borderInsets, boolean fillContentArea, boolean isCaching) {
        return create(img, borderInsets, borderInsets, fillContentArea, isCaching);
    }
    /**
     * Creates a new instance of an ImageBevelBorder with the given image and insets.
     * The image has different insets than the border.
     */
    public static Border create(Image img, Insets imageInsets, Insets borderInsets, boolean fillContentArea) {
        return create(img, imageInsets, borderInsets, fillContentArea, true);
    }
    /**
     * Creates a new instance of an ImageBevelBorder with the given image and insets.
     * The image has different insets than the border.
     */
    public static Border create(Image img, Insets imageInsets, Insets borderInsets, boolean fillContentArea, boolean isCaching) {
        if (isCaching) {
            return new CachedImageBevelBorder14(img, imageInsets, borderInsets, fillContentArea);
        } else {
            return new ImageBevelBorder14(img, imageInsets, borderInsets, fillContentArea);
        }
    }
    /**
     * Creates a new instance of a border for square buttons.
     */
    public static Border createSquareButtonBorder() {
        return new Quaqua14SquareButtonBorder();
    }
    /**
     * Creates a new instance of a border for placard buttons.
     */
    public static Border createPlacardButtonBorder() {
        return new Quaqua14PlacardButtonBorder();
    }
    public static Border create(String location, Insets borderInsets, boolean fill) {
        return create(QuaquaIconFactory.createImage(location), borderInsets, borderInsets, fill, false);
    }
    public static Border create(String location, Insets imageInsets, Insets borderInsets, boolean fill) {
        return create(QuaquaIconFactory.createImage(location), imageInsets, borderInsets, fill, false);
    }
    public static Border createBackgroundBorder(String location, Insets imageInsets, Insets borderInsets, boolean fill) {
        return new BackgroundBorderUIResource(create(QuaquaIconFactory.createImage(location), imageInsets, borderInsets, fill, false));
    }
    
    /**
     * Creates an array of ImageBevelBorders.
     *
     * @param location URL of the image that contains the border images.
     * @param insets Insets of the borders.
     * @param count Number of borders to generate.
     * @param horizontal True, if the image is to be split horizontally to get
     * the individual image of each border. If set to false, the image is split
     * vertically.
     */
    public static Object create(String location, Insets insets, int count, boolean horizontal) {
        return create(location, insets, count, horizontal, true);
    }
    public static Object create(String location, Insets insets, int count, boolean horizontal, boolean fill) {
        BufferedImage[] images = Images.split(
                QuaquaIconFactory.createImage(location),
                count, horizontal
                );
        Border[] borders = new Border[count];
        for (int i=0; i < count; i++) {
            borders[i] = create(images[i], insets, insets, fill);
        }
        return borders;
    }
    
    public static Border createButtonBorder(String type) {
        return new BackgroundBorderUIResource(new QuaquaButtonBorder("push"));
    }
}
