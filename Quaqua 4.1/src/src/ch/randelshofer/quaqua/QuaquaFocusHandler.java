/*
 * @(#)QuaquaFocusHandler.java  1.1  2007-01-16
 *
 * Copyright (c) 2004-2007 Werner Randelshofer
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

import java.awt.Component;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
/**
 * QuaquaFocusHandler.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2007-01-16 Changed to singleton.
 * <br>1.0  July 4, 2004  Created.
 */
public class QuaquaFocusHandler implements FocusListener {
    private static QuaquaFocusHandler instance;
    
    public static QuaquaFocusHandler getInstance() {
        if (instance == null) {
            instance = new QuaquaFocusHandler();
        }
        return instance;
    }
    
    
    /**
     * Prevent instance creation.
     */
    private QuaquaFocusHandler() {
    }
    
    public void focusGained(FocusEvent event) {
       // System.out.println("QuaquaFocusHandler focusGained from "+event.getOppositeComponent());
            QuaquaUtilities.repaintBorder((JComponent) event.getComponent());
    }
    
    public void focusLost(FocusEvent event) {
       // System.out.println("QuaquaFocusHandler focusLost to "+event.getOppositeComponent());
            QuaquaUtilities.repaintBorder((JComponent) event.getComponent());
    }
}

