/*
 * @(#)QuaquaComboBoxEditor.java  1.1.1  2007-07-30
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

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;

import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * The default editor for Quaqua editable combo boxes.
 *
 * @author  Werner Randelshofer
 * @version 1.1.1 2007-07-30 Removed debug output. 
 * <br>1.1 2004-12-02 Remove input map entries from the editor which 
 * interfer with the ancestor map entries of the combo box.
 * <br>1.0 2004-04-09 Created.
 */
public class QuaquaComboBoxEditor extends BasicComboBoxEditor {
    public QuaquaComboBoxEditor() {
        super();
        //editor.removeFocusListener(this);
        editor = new JTextField("",9) {
            // workaround for 4530952
            public void setText(String s) {
                if (getText().equals(s)) {
                    return;
                }
                super.setText(s);
            }
        };
        
       installKeyboardActions();
    }
    
    protected void installKeyboardActions() {
        InputMap km = getInputMap();
	if (km != null) {
	    SwingUtilities.replaceUIInputMap(editor, JComponent.WHEN_FOCUSED, km);
	}
    }

    /**
     * Get the InputMap to use for the UI.  
     */
    InputMap getInputMap() {
	InputMap map = new InputMapUIResource();
	InputMap shared = (InputMap)UIManager.get("ComboBox.editorInputMap");
	if (shared != null) {
	    map.setParent(shared);
	}
	return map;
    }

    /**
     * A subclass of BasicComboBoxEditor that implements UIResource.
     * BasicComboBoxEditor doesn't implement UIResource
     * directly so that applications can safely override the
     * cellRenderer property with BasicListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     */
    public static class UIResource extends QuaquaComboBoxEditor
    implements javax.swing.plaf.UIResource {
    }
}

