/*
 * @(#)QuaquaComboBoxVisualMargin.java  1.0  July 30, 2007
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Staldenmattweg 2, CH-6405 Immensee, Switzerland
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
import javax.swing.border.*;
import javax.swing.plaf.*;
/**
 * QuaquaComboBoxVisualMargin.
 *
 * @author Werner Randelshofer
 * @version 1.0 July 30, 2007 Created.
 */
public class QuaquaComboBoxVisualMargin extends VisualMargin {
    
    /** Creates a new instance. */
    public QuaquaComboBoxVisualMargin() {
        super();
    }
    
    /**
     * Creates a new VisualMargin.
     *
     * @param top Defines the margin from the clip bounds of the
     * component to its visual bounds.
     * @param left Defines the margin from the clip bounds of the
     * component to its visual bounds.
     * @param bottom Defines the margin from the clip bounds of the
     * component to its visual bounds.
     * @param right Defines the margin from the clip bounds of the
     * component to its visual bounds.
     */
    public QuaquaComboBoxVisualMargin(int top, int left, int bottom, int right) {
        super(top, left, bottom, right);
    }
    public QuaquaComboBoxVisualMargin(int top, int left, int bottom, int right, boolean ftop, boolean fleft, boolean fbottom, boolean fright) {
        super(top, left, bottom, right, ftop, fleft, fbottom, fright);
    }
    public QuaquaComboBoxVisualMargin(boolean ftop, boolean fleft, boolean fbottom, boolean fright) {
        super(ftop, fleft, fbottom, fright);
    }
    /**
     * Creates a new VisualMargin.
     *
     * @param layoutMargin Defines the margin from the clip bounds of the
     * component to its visual bounds. The margin has usually negative values!
     */
    public QuaquaComboBoxVisualMargin(Insets layoutMargin) {
        super(layoutMargin);
    }
    
    /**
     * Reinitializes the insets parameter with this Border's current Insets.
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     * @return the <code>insets</code> object
     */
    protected Insets getVisualMargin(Component c, Insets insets) {
        Insets i = super.getVisualMargin(c, insets);
        if (QuaquaManager.getBoolean("ComboBox.harmonizePreferredHeight")) {
            if (! ((JComboBox) c).isEditable()) {
                i.top += 1;
                i.bottom += 1;
            }
        }
        return i;
    }
}
