/*
 * @(#)AlphaColorUIResource.java  1.0  06 February 2005
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
import javax.swing.plaf.*;
/**
 * A ColorUIResource whith an alpha channel.
 *
 * @author  Werner Randelshofer
 * @version 1.0  06 February 2005  Created.
 */
public class AlphaColorUIResource extends Color implements UIResource {
    public AlphaColorUIResource(int r, int g, int b, int a) {
        super(r, g, b, a);
    }
    public AlphaColorUIResource(int rgba) {
        super(rgba, true);
    }
}
