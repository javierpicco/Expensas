/*
 * @(#)QuaquaButtonUI.java  1.0  05 March 2005
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

package ch.randelshofer.quaqua;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import java.io.Serializable;
import java.beans.*;

/**
 * QuaquaButtonUI.
 *
 * @author  Werner Randelshofer
 * @version 1.0  05 March 2005  Created.
 */
public class QuaquaToggleButtonUI extends QuaquaButtonUI {
    // Shared UI object
    private final static QuaquaToggleButtonUI buttonUI = new QuaquaToggleButtonUI();
    
    
    // ********************************
    //          Create PLAF
    // ********************************
    public static ComponentUI createUI(JComponent c) {
        return buttonUI;
    }
    
    protected String getPropertyPrefix() {
        return "ToggleButton.";
    }
}
