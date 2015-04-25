/*
 * @(#)QuaquaSliderUI.java  1.0  December 9, 2005
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

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
/**
 * QuaquaSliderUI.
 *
 * @author  Werner Randelshofer
 * @version 1.0 December 9, 2005 Created.
 */
public class QuaquaSliderUI extends BasicSliderUI {
    
    public static ComponentUI createUI(JComponent b)    {
        return new QuaquaSliderUI((JSlider)b);
    }
    
    public QuaquaSliderUI(JSlider b)   {
        super(b);
    }
    
    public void installUI(JComponent c)   {
        super.installUI(c);
	QuaquaUtilities.installProperty(c, "opaque", UIManager.get("Slider.opaque"));
        //slider.setOpaque(false);
    }
}
