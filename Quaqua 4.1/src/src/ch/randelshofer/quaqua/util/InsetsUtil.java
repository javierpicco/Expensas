/*
 * @(#)InsetsUtil.java  1.1  2005-05-05
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
/**
 * Utilities for Insets.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-05-05 Method addTo(Insets,Insets) added.
 * <br>1.0  03 April 2005  Created.
 */
public class InsetsUtil {
    
    /** Prevent instance creation. */
    private InsetsUtil() {
    }
    
    public static Insets add(Insets i1, Insets i2) {
        return new Insets(
        i1.top + i2.top, 
        i1.left + i2.left, 
        i1.bottom + i2.bottom, 
        i1.right + i2.right
        );
    }
    public static Insets add(int top, int left, int bottom, int right, Insets i1) {
        return new Insets(
        i1.top + top, 
        i1.left + left, 
        i1.bottom + bottom, 
        i1.right + right
        );
    }
    public static void addTo(int top, int left, int bottom, int right, Insets i2) {
        i2.top += top; 
        i2.left += left; 
        i2.bottom += bottom; 
        i2.right += right;
    }
    public static void addTo(Insets i1, Insets i2) {
        i2.top += i1.top; 
        i2.left += i1.left; 
        i2.bottom += i1.bottom; 
        i2.right += i1.right;
    }
    public static void subtractInto(Insets i1, Rectangle r2) {
        r2.x += i1.left;
        r2.y += i1.top;
        r2.width -= i1.left + i1.right;
        r2.height -= i1.top + i1.bottom;
        }
    public static void subtractInto(int top, int left, int bottom, int right, Rectangle r2) {
        r2.x += left;
        r2.y += top;
        r2.width -= left + right;
        r2.height -= top + bottom;
        }
}
