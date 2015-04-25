/*
 * @(#)QuaquaBaseline.java  1.1  2005-07-17
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

import org.jdesktop.layout.*;
import java.awt.*;
import java.lang.reflect.*;
import javax.swing.*;
/**
 * QuaquaBaseline.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-07-17 Adapted to changes in interface VisuallyLayoutable.
 * <br>1.0 June 26, 2005 Created.
 */
public class QuaquaBaseline extends Baseline {
    
    /**
     * Creates a new instance.
     */
    public QuaquaBaseline() {
    }
    
    /**
     * Returns the baseline for the specified component, or -1 if the
     * baseline can not be determined.  The baseline is measured from
     * the top of the component.
     *
     * @param component JComponent to calculate baseline for
     * @param width Width of the component to determine baseline for.
     * @param height Height of the component to determine baseline for.
     * @return baseline for the specified component
     */
    public int getComponentBaseline(JComponent component, int width, int height) {
        int baseline = getBaselineFromUI(component, width, height);
//System.out.println("QuaquaBaseline.getComponentBaseline():"+baseline+" for "+component);
        return baseline;
    }
    
    private int getBaselineFromUI(JComponent component, int width, int height) {
        try {
            Method getUI = component.getClass().getMethod("getUI", new Class[0]);
            Object ui = getUI.invoke(component, new Object[0]);
            if (ui instanceof VisuallyLayoutable) {
                return ((VisuallyLayoutable) ui).getBaseline(component, width, height);
            }
        } catch (Exception e) {
            InternalError error = new InternalError();
            //error.initCause(e);
            throw error;
        }
        return -1;
    }
}
