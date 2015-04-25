/*
 * @(#)HalfbrightFilter.java  1.0  28 March 2005
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

package ch.randelshofer.quaqua.util;

import java.awt.*;
import java.awt.image.*;
/**
 * HalfbrightFilter reduces the brightness of an image by 50 percent.
 * Leaves the alpha channel untouched.
 * This is used by the Quaqua Look and Feel, to create a pressed image.
 *
 * @author  Werner Randelshofer
 * @version 1.0  28 March 2005  Created.
 */
public class HalfbrightFilter extends RGBImageFilter {
    /** Creates a new instance. */
    public HalfbrightFilter() {
        canFilterIndexColorModel = true;
    }
    
    /**
     * Creates a halfbright image
     */
    public static Image createHalfbrightImage (Image i) {
	ImageFilter filter = new HalfbrightFilter();
	ImageProducer prod = new FilteredImageSource(i.getSource(), filter);
	Image filteredImage = Toolkit.getDefaultToolkit().createImage(prod);
	return filteredImage;
    }
    
    public int filterRGB(int x, int y, int rgb) {
        return rgb & 0xff000000 // preserve alpha channel
        | (rgb & 0xfefefe) >>> 1; 
    }
    
}
