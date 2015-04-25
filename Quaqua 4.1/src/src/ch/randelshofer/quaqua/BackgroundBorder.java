/*
 * @(#)BackgroundBorder.java  1.0  September 25, 2005
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

package ch.randelshofer.quaqua;

import javax.swing.border.*;
/**
 * BackgroundBorder is used by the Quaqua Look And Feel to tag a
 * border which partially needs to be drawn on to the background of a JComponent.
 *
 * @author  Werner Randelshofer
 * @version 1.0 September 25, 2005 Created.
 */
public interface BackgroundBorder extends Border {
    
    /**
     * Returns the border that needs to be drawn onto the background.
     */
    public Border getBackgroundBorder();
}
