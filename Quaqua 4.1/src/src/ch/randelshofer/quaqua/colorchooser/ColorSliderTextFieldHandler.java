/*
 * @(#)ColorSliderTextFieldHandler.java  2.0  2007-02-24
 *
 * Copyright (c) 2005-2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.colorchooser;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
/**
 * This handler adjusts the value of a component in the color slider model,
 * when the user enters text into the text field.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2007-02-24 Made subclassable.
 * <br>1.0 November 22, 2005 Created.
 */
public class ColorSliderTextFieldHandler implements DocumentListener, ChangeListener {
    protected JTextField textField;
    protected ColorSliderModel ccModel;
    protected int component;
    
    public ColorSliderTextFieldHandler(JTextField textField, ColorSliderModel ccModel, int component) {
        this.textField = textField;
        this.ccModel = ccModel;
        this.component = component;
        
        textField.getDocument().addDocumentListener(this);
        ccModel.getBoundedRangeModel(component).addChangeListener(this);
    }
    
    public void changedUpdate(DocumentEvent evt) {
        docChanged();
    }
    public void removeUpdate(DocumentEvent evt) {
        docChanged();
    }
    public void insertUpdate(DocumentEvent evt) {
        docChanged();
    }
    protected void docChanged() {
        if (textField.hasFocus()) {
            BoundedRangeModel brm = ccModel.getBoundedRangeModel(component);
            try {
                int value = Integer.decode(textField.getText()).intValue();
                if (brm.getMinimum() <= value && value <= brm.getMaximum()) {
                    brm.setValue(value);
                }
            } catch (NumberFormatException e) {
                // Don't change value if it isn't numeric.
            }
        }
    }
    public void stateChanged(ChangeEvent e) {
        if (! textField.hasFocus()) {
            textField.setText(Integer.toString(ccModel.getBoundedRangeModel(component).getValue()));
        }
    }
}

