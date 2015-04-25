/*
 * @(#)Quaqua14TextFieldFocusHandler.java  1.0.1  2007-08-13
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
import java.awt.KeyboardFocusManager;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
/**
 * Quaqua14TextFieldFocusHandler. Selects all text of a JTextComponent, if
 * the user used a keyboard focus traversal key, to transfer the focus on the
 * JTextComponent.
 *
 * @author Werner Randelshofer
 * @version 1.0.1 2007-08-13 Clear lastKeyboardTraversingComponent in keyboard
 * manager after having gained focus.
 * <br>1.0 2007-08-06 reated.
 */
public class Quaqua14TextFieldFocusHandler implements FocusListener {
    private static Quaqua14TextFieldFocusHandler instance;
    
    public static Quaqua14TextFieldFocusHandler getInstance() {
        if (instance == null) {
            instance = new Quaqua14TextFieldFocusHandler();
        }
        return instance;
    }
    
    /**
     * Prevent instance creation.
     */
    private Quaqua14TextFieldFocusHandler() {
    }
    
    public void focusGained(FocusEvent event) {
        //System.out.println("Quaqua14TextFieldFocusHandler focusGained from "+event.getOppositeComponent());
        QuaquaUtilities.repaintBorder((JComponent) event.getComponent());
        
        final JTextComponent tc = (JTextComponent) event.getSource();
        if (tc.isEditable() && tc.isEnabled()) {
            
            String uiProperty;
            if (tc instanceof JPasswordField) {
                uiProperty = "PasswordField.autoSelect";
            } else if (tc instanceof JFormattedTextField) {
                uiProperty = "FormattedTextField.autoSelect";
            } else {
                uiProperty = "TextField.autoSelect";
            }
            
            if (tc.getClientProperty("Quaqua.TextComponent.autoSelect") == Boolean.TRUE ||
                    tc.getClientProperty("Quaqua.TextComponent.autoSelect") == null &&
                    QuaquaManager.getBoolean(uiProperty)
                    ) {
                if (KeyboardFocusManager.getCurrentKeyboardFocusManager() instanceof Quaqua14KeyboardFocusManager) {
                    Quaqua14KeyboardFocusManager kfm = (Quaqua14KeyboardFocusManager) KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    if (event.getOppositeComponent() == kfm.getLastKeyboardTraversingComponent()) {
                        tc.selectAll();
                    }
                }
            }
        }
        if (KeyboardFocusManager.getCurrentKeyboardFocusManager() instanceof Quaqua14KeyboardFocusManager) {
            Quaqua14KeyboardFocusManager kfm = (Quaqua14KeyboardFocusManager) KeyboardFocusManager.getCurrentKeyboardFocusManager();
            kfm.setLastKeyboardTraversingComponent(null);
        }
    }
    
    public void focusLost(FocusEvent event) {
        // System.out.println("Quaqua14TextFieldFocusHandler focusLost to "+event.getOppositeComponent());
        QuaquaUtilities.repaintBorder((JComponent) event.getComponent());
    }
}

