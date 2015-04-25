/*
 * @(#)QuaquaToolBarUI.java  1.1  2005-04-24
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

import ch.randelshofer.quaqua.QuaquaButtonBorder;
import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.BackgroundBorderUIResource;
import ch.randelshofer.quaqua.util.PaintableColor;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.util.*;
/**
 * QuaquaToolBarUI.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-04-24 Made class compilable for Java 1.3.
 * <br>1.0  28 March 2005  Created.
 */
public class QuaquaToolBarUI extends BasicToolBarUI {
    // Rollover button implementation.
    private static String IS_ROLLOVER = "JToolBar.isRollover";
    /*private*/ static String IS_DIVIDER_DRAWN = "Quaqua.ToolBar.isDividerDrawn";
    private static Border rolloverBorder;
    private static Border nonRolloverBorder;
    private static Border nonRolloverToggleBorder;
    private HashMap borderTable = new HashMap();
    private Hashtable rolloverTable = new Hashtable();
    
    public static ComponentUI createUI( JComponent c ) {
        return new QuaquaToolBarUI();
    }
    
    public void paint(Graphics gr, JComponent c) {
        Graphics2D g = (Graphics2D) gr;
        g.setPaint(PaintableColor.getPaint(c.getBackground(), c));
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
    protected void installDefaults() {
	if (rolloverBorder == null) {
	    rolloverBorder = createRolloverBorder();
	}
	if (nonRolloverBorder == null) {
	    nonRolloverBorder = createNonRolloverBorder();
	}
	if (nonRolloverToggleBorder == null) {
	    nonRolloverToggleBorder = createNonRolloverToggleBorder();
	}
        super.installDefaults();
    }
    /**
     * Creates a window which contains the toolbar after it has been
     * dragged out from its container
     * @return a <code>RootPaneContainer</code> object, containing the toolbar.
     */
    protected RootPaneContainer createFloatingWindow(JToolBar toolbar) {
	class ToolBarDialog extends JDialog {
	    public ToolBarDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	    }

	    public ToolBarDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	    }
            
	    // Override createRootPane() to automatically resize
	    // the frame when contents change
	    protected JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane() {
		    private boolean packing = false;

		    public void validate() {
                        putClientProperty(
                        "Quaqua.RootPane.isVertical",
                        toolBar.getOrientation() == JToolBar.VERTICAL ? Boolean.FALSE : Boolean.TRUE
                        );
			super.validate();
			if (!packing) {
			    packing = true;
			    pack();
			    packing = false;
			}
		    }
		};
         rootPane.setFont(UIManager.getFont("ToolBar.titleFont"));
                  rootPane.putClientProperty("Quaqua.RootPane.isPalette", Boolean.TRUE);      
		//rootPane.setOpaque(true);
	QuaquaUtilities.installProperty(rootPane, "opaque", Boolean.TRUE);
		return rootPane;
	    }
	}

	JDialog dialog;
	Window window = SwingUtilities.getWindowAncestor(toolbar);
	if (window instanceof Frame) {
	    dialog = new ToolBarDialog((Frame)window, toolbar.getName(), false);
	} else if (window instanceof Dialog) {
	    dialog = new ToolBarDialog((Dialog)window, toolbar.getName(), false);
	} else {
	    dialog = new ToolBarDialog((Frame)null, toolbar.getName(), false);
	}
       
        try {
Methods.invoke(dialog, "setUndecorated", true);
Methods.invoke(dialog.getRootPane(),"setWindowDecorationStyle", 1);//JRootPane.FRAME);
} catch (NoSuchMethodException e) {
    // Empty
}

	dialog.setTitle(toolbar.getName());
	dialog.setResizable(false);
	WindowListener wl = createFrameListener();
	dialog.addWindowListener(wl);
        
        //RootPaneContainer dialog = super.createFloatingWindow(toolbar);
        dialog.getRootPane().putClientProperty("JDialog.isPalette",Boolean.TRUE);
        dialog.getRootPane().putClientProperty("JFrame.isPalette",Boolean.TRUE);
        dialog.getRootPane().putClientProperty("Dialog.isPalette",Boolean.TRUE);
        dialog.getRootPane().putClientProperty("Frame.isPalette",Boolean.TRUE);
        dialog.getRootPane().putClientProperty("JWindow.isPalette",Boolean.TRUE);
        dialog.getRootPane().putClientProperty("Window.isPalette",Boolean.TRUE);

        return dialog;
        }

    /**
     * Creates a rollover border for toolbar components. The 
     * rollover border will be installed if rollover borders are 
     * enabled. 
     * <p>
     * Override this method to provide an alternate rollover border.
     *
     * @since 1.4
     */
    protected Border createRolloverBorder() {
        return new BackgroundBorderUIResource(new QuaquaButtonBorder("toolBarRollover"));
    }

    /**
     * Creates the non rollover border for toolbar components. This
     * border will be installed as the border for components added
     * to the toolbar if rollover borders are not enabled.
     * <p>
     * Override this method to provide an alternate rollover border.
     *
     * @since 1.4
     */
    protected Border createNonRolloverBorder() {
        return new BackgroundBorderUIResource(new QuaquaButtonBorder("toolBar"));
    }

    /**
     * Creates a non rollover border for Toggle buttons in the toolbar.
     */
    private Border createNonRolloverToggleBorder() {
        return new BackgroundBorderUIResource(new QuaquaButtonBorder("toolBar"));
    }
    /**
     * Sets the border of the component to have a rollover border which
     * was created by <code>createRolloverBorder</code>. 
     *
     * @param c component which will have a rollover border installed 
     * @see #createRolloverBorder
     * @since 1.4
     */
    protected void setBorderToRollover(Component c) {
        if (c instanceof AbstractButton) {
	    AbstractButton b = (AbstractButton)c;
	    
	    Border border = (Border)borderTable.get(b);
	    if (border == null || border instanceof UIResource) {
		borderTable.put(b, b.getBorder());
	    }

	    // Only set the border if its the default border
	    if (b.getBorder() instanceof UIResource) {
		b.setBorder(rolloverBorder);
	    }

	    rolloverTable.put(b, b.isRolloverEnabled()?
			      Boolean.TRUE: Boolean.FALSE);
	    b.setRolloverEnabled(true);
	}
    }

    /**
     * Sets the border of the component to have a non-rollover border which
     * was created by <code>createNonRolloverBorder</code>. 
     *
     * @param c component which will have a non-rollover border installed 
     * @see #createNonRolloverBorder
     * @since 1.4
     */
    protected void setBorderToNonRollover(Component c) {
        if (c instanceof AbstractButton) {
	    AbstractButton b = (AbstractButton)c;
	    
	    Border border = (Border)borderTable.get(b);
	    if (border == null || border instanceof UIResource) {
		borderTable.put(b, b.getBorder());
	    }

	    // Only set the border if its the default border
	    if (b.getBorder() instanceof UIResource) {
		if (b instanceof JToggleButton) {
		    ((JToggleButton)b).setBorder(nonRolloverToggleBorder);
		} else {
		    b.setBorder(nonRolloverBorder);
		}
	    }
	    rolloverTable.put(b, b.isRolloverEnabled()?
			      Boolean.TRUE: Boolean.FALSE);
	    b.setRolloverEnabled(false);
	}
    }
    
    /**
     * Sets the border of the component to have a normal border.
     * A normal border is the original border that was installed on the child
     * component before it was added to the toolbar.
     *
     * @param c component which will have a normal border re-installed 
     * @see #createNonRolloverBorder
     * @since 1.4
     */
    protected void setBorderToNormal(Component c) {
        if (c instanceof AbstractButton) {
	    AbstractButton b = (AbstractButton)c;

	    Border border = (Border)borderTable.remove(b);
	    b.setBorder(border);

	    Boolean value = (Boolean)rolloverTable.remove(b);
	    if (value != null) {
		b.setRolloverEnabled(value.booleanValue());
	    }
	}
    }
}
