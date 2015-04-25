/*
 * @(#)ViewportPainter.java  1.0  14 December 2004
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
import javax.swing.*;
/**
 * This interface is implemented by user interface delegates that wish to
 * paint onto the content area of a JViewport.
 *
 * @author  Werner Randelshofer
 * @version 1.0  14 December 2004  Created.
 */
public interface ViewportPainter {
    /**
     * Paints the viewport of a JViewport, that contains the component of the
     * user interface delegate.
     * This method is invoked by QuaquaViewportUI.
     */
    public void paintViewport(Graphics g, JViewport c);
}
