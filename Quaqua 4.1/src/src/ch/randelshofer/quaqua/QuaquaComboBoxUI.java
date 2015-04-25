/*
 * @(#)QuaquaComboBoxUI.java 1.4.4  2007-02-17
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

import ch.randelshofer.quaqua.util.*;
import ch.randelshofer.quaqua.util.Debug;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import java.io.Serializable;
import java.beans.*;


/**
 * Quaqua UI for JComboBox.
 *
 * @author  Werner Randelshofer
 * @version 1.4.4 2007-02-17 Don't set background of text editor in method
 * paint.
 * <br>1.4.3 2007-01-18 Set opaque to false on the combo box.
 * <br>1.4.2 2007-01-05 Issue #12: RectangleForCurrentValue returns
 * incorrect value when combo box is a table cell editor.
 * <br>1.4.1 2007-01-04 Retrieve maximum row count from UIManager.
 * <br>1.4 2005-07-17 Adapted to changes in interface VisuallyLayoutable.
 * <br>1.3.2 200-06-25 Fixed method getMaxSize.
 * <br>1.3.1 2005-06-11 Method getVisualBounds returned bad values.
 * <br>1.3 2005-03-06 When the combo box was a table cell editor we
 * accidentaly used always the popup icon. The "small" style is now used when
 * the font size is less than 12 (prior less than 13 was used).
 * <br>1.2 2004-12-29 For Java 1.3, we have to use the property
 * "JComboBox.lightweightKeyboardNavigation" to guess, whether we are
 * a table cell editor or not.
 * <br>1.1 2004-07-31 Draw cell editor borderless, when the combo box is
 * used as a table cell editor.
 * <br>1.0 2004-04-09 Created.
 */
public class QuaquaComboBoxUI extends BasicComboBoxUI implements VisuallyLayoutable {
    //private HierarchyListener hierarchyListener;
    //MetalComboBoxUI
    // Control the selection behavior of the JComboBox when it is used
    // in the JTable DefaultCellEditor.
    private boolean isTableCellEditor = false;
    public static final String IS_TABLE_CELL_EDITOR = "JComboBox.isTableCellEditor";
    private final static Border tableCellEditorBorder = new EmptyBorder(0,2,0,0);
    
    static final StringBuffer HIDE_POPUP_KEY
            = new StringBuffer("HidePopupKey");
    /**
     * Preferred spacing between combo boxes and other components.
     * /
     * private final static Insets regularSpacing = new Insets(12,12,12,12);
     * private final static Insets smallSpacing = new Insets(10,10,10,10);
     * private final static Insets miniSpacing = new Insets(8,8,8,8);
     */
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaComboBoxUI();
    }
    
    public void installUI( JComponent c ) {
        super.installUI(c);
        
        // Is this combo box a cell editor?
        Boolean value = (Boolean)c.getClientProperty(IS_TABLE_CELL_EDITOR);
        if (value == null) {
            value = (Boolean) c.getClientProperty("JComboBox.lightweightKeyboardNavigation");
        }
        setTableCellEditor(value != null && value.equals(Boolean.TRUE));
        
        // Note: we need to invoke c.setOpaque explicitly, installProperty does
        //       not seem to work.
        //LookAndFeel.installProperty(c, "opaque", UIManager.get("ComboBox.opaque"));
        c.setOpaque(QuaquaManager.getBoolean("ComboBox.opaque"));
        
        comboBox.setRequestFocusEnabled(QuaquaManager.getBoolean("ComboBox.requestFocusEnabled"));
    }
    protected void installDefaults() {
        super.installDefaults();
        comboBox.setMaximumRowCount(UIManager.getInt("ComboBox.maximumRowCount"));
    }
    /**
     * Create and install the listeners for the combo box and its model.
     * This method is called when the UI is installed.
     */
    protected void installListeners() {
        if ( (itemListener = createItemListener()) != null) {
            comboBox.addItemListener( itemListener );
        }
        if ( (propertyChangeListener = createPropertyChangeListener()) != null ) {
            comboBox.addPropertyChangeListener( propertyChangeListener );
        }
        if ( (keyListener = createKeyListener()) != null ) {
            comboBox.addKeyListener( keyListener );
        }
        if ( (focusListener = createFocusListener()) != null ) {
            comboBox.addFocusListener( focusListener );
        }
        if ((popupMouseListener = popup.getMouseListener()) != null) {
            comboBox.addMouseListener( popupMouseListener );
        }
        if ((popupMouseMotionListener = popup.getMouseMotionListener()) != null) {
            comboBox.addMouseMotionListener( popupMouseMotionListener );
        }
        if ((popupKeyListener = popup.getKeyListener()) != null) {
            comboBox.addKeyListener(popupKeyListener);
        }
        /*
        if ((hierarchyListener = createHierarchyListener()) != null) {
            comboBox.addHierarchyListener(hierarchyListener);
        }
         */
        if ( comboBox.getModel() != null ) {
            if ( (listDataListener = createListDataListener()) != null ) {
                comboBox.getModel().addListDataListener( listDataListener );
            }
        }
    }
    
    /**
     * Remove the installed listeners from the combo box and its model.
     * The number and types of listeners removed and in this method should be
     * the same that was added in <code>installListeners</code>
     */
    protected void uninstallListeners() {
        super.uninstallListeners();
        /*
        if (hierarchyListener != null) {
            comboBox.removeHierarchyListener(hierarchyListener);
            hierarchyListener = null;
        }*/
    }
    /*
    protected HierarchyListener createHierarchyListener() {
        return new ComponentActivationHandler(comboBox);
    }*/
    boolean isTableCellEditor() {
        return isTableCellEditor;
    }
    protected ComboBoxEditor createEditor() {
        return new QuaquaComboBoxEditor.UIResource();
    }
    
    
    protected ComboPopup createPopup() {
        QuaquaComboPopup popup = new QuaquaComboPopup(comboBox, this);
        popup.getAccessibleContext().setAccessibleParent(comboBox);
        return popup;
    }
    
    protected JButton createArrowButton() {
        JButton button = new QuaquaComboBoxButton( this, comboBox, getArrowIcon(),
                comboBox.isEditable(),
                currentValuePane,
                listBox );
        button.setMargin( new Insets( 0, 1, 1, 3 ) );
        return button;
    }
    
    public PropertyChangeListener createPropertyChangeListener() {
        return new QuaquaPropertyChangeListener();
    }
    private void setTableCellEditor(boolean b) {
        isTableCellEditor = b;
        updateTableCellEditor();
    }
    private void updateTableCellEditor() {
        boolean b = isTableCellEditor();
        //comboBox.setOpaque(b);
        if (editor instanceof JComponent) {
            JComponent jeditor = (JComponent) editor;
            jeditor.setBorder(b ? tableCellEditorBorder : UIManager.getBorder("TextField.border"));
        }
    }
    
    
    public void paint( Graphics g, JComponent c ) {
        if (editor != null &&
                QuaquaManager.getBoolean("ComboBox.changeEditorForeground")) {
            editor.setForeground(c.getForeground());
        }
        Debug.paint(g, c, this);
    }
    public void paintCurrentValue(Graphics g,Rectangle bounds,boolean hasFocus) {
    }
    
    /**
     * Paints the background of the currently selected item.
     */
    public void paintCurrentValueBackground(Graphics g,Rectangle bounds,boolean hasFocus) {
    }
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */
    public class QuaquaPropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler {
        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange( e );
            String propertyName = e.getPropertyName();
            
            if (propertyName.equals("editable")) {
                QuaquaComboBoxButton button = (QuaquaComboBoxButton)arrowButton;
                button.setIconOnly(comboBox.isEditable());
                updateTableCellEditor();
                comboBox.repaint();
            } else if ( propertyName.equals( "background" ) ) {
                Color color = (Color)e.getNewValue();
                arrowButton.setBackground(color);
            } else if ( propertyName.equals( "foreground" ) ) {
                Color color = (Color)e.getNewValue();
                arrowButton.setForeground(color);
                listBox.setForeground(color);
            } else if ( propertyName.equals(IS_TABLE_CELL_EDITOR) ) {
                Boolean inTable = (Boolean)e.getNewValue();
                setTableCellEditor(inTable.equals(Boolean.TRUE) ? true : false);
            } else if (propertyName.equals("JComboBox.lightweightKeyboardNavigation")) {
                // In Java 1.3 we have to use this property to guess whether we
                // are a table cell editor or not.
                setTableCellEditor(e.getNewValue() != null && e.getNewValue().equals("Lightweight"));
            }
        }
    }
    
    
    
    
    /**
     * As of Java 2 platform v1.4 this method is no longer used. Do not call or
     * override. All the functionality of this method is in the
     * QuaquaPropertyChangeListener.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void editablePropertyChanged( PropertyChangeEvent e ) { }
    
    protected LayoutManager createLayoutManager() {
        return new QuaquaComboBoxLayoutManager();
    }
    
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */
    public class QuaquaComboBoxLayoutManager extends BasicComboBoxUI.ComboBoxLayoutManager {
        public void layoutContainer( Container parent ) {
            layoutComboBox( parent, this );
        }
        public void superLayout( Container parent ) {
            JComboBox cb = (JComboBox)parent;
            int width = cb.getWidth();
            int height = cb.getHeight();
            
            Insets insets = getInsets();
            Insets margin = getMargin();
            int buttonSize = height - (insets.top + insets.bottom);
            
            Rectangle cvb;
            if (arrowButton != null) {
                if(QuaquaUtilities.isLeftToRight(cb)) {
                    // FIXME - This should be 6 minus 2, whereas two needs to be
                    // derived from the TextFieldUI
                    //int plusHeight = (isSmall()) ? 4 : 4;
                    int plusHeight = (isSmall()) ? - 2 : - 2;
                    arrowButton.setBounds(
                            width - getArrowWidth()-insets.right,
                            insets.top /*+ margin.top - 3*/,
                            getArrowWidth(),
                            buttonSize /*- margin.top - margin.bottom*/ + plusHeight
                            );
                } else {
                    arrowButton.setBounds(insets.left, insets.top,
                            getArrowWidth(), buttonSize);
                }
            }
            if (editor != null) {
                cvb = rectangleForCurrentValue();
                editor.setBounds(cvb);
            }
        }
    }
    
    // This is here because of a bug in the compiler.
    // When a protected-inner-class-savvy compiler comes out we
    // should move this into QuaquaComboBoxLayoutManager.
    public void layoutComboBox( Container parent, QuaquaComboBoxLayoutManager manager ) {
        if (comboBox.isEditable()) {
            manager.superLayout(parent);
        } else {
            if (arrowButton != null) {
                Insets insets = comboBox.getInsets();
                int width = comboBox.getWidth();
                int height = comboBox.getHeight();
                arrowButton.setBounds(insets.left, insets.top,
                        width - (insets.left + insets.right),
                        height - (insets.top + insets.bottom));
            }
        }
    }
    
    protected Icon getArrowIcon() {
        if (isTableCellEditor()) {
            return UIManager.getIcon("ComboBox.smallPopupIcon");
                /* The following does not work as expected:
            if (comboBox.isEditable()) {
                return UIManager.getIcon("ComboBox.smallDropDownIcon");
            } else {
                return UIManager.getIcon("ComboBox.smallPopupIcon");
            }*/
        } else {
            if (comboBox.isEditable()) {
                if (isSmall()) {
                    return UIManager.getIcon("ComboBox.smallDropDownIcon");
                } else {
                    return UIManager.getIcon("ComboBox.dropDownIcon");
                }
            } else {
                if (isSmall()) {
                    return UIManager.getIcon("ComboBox.smallPopupIcon");
                } else {
                    return UIManager.getIcon("ComboBox.popupIcon");
                }
            }
        }
    }
    protected int getArrowWidth() {
        if (isTableCellEditor()) {
            return 7;
        } else {
            if (comboBox.isEditable()) {
                if (isSmall()) {
                    return 17;
                } else {
                    return 19;
                }
            } else {
                if (isSmall()) {
                    return 17+3;
                } else {
                    return 19+4;
                }
            }
        }
    }
    
    /**
     * As of Java 2 platform v1.4 this method is no
     * longer used.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void removeListeners() {
        if ( propertyChangeListener != null ) {
            comboBox.removePropertyChangeListener( propertyChangeListener );
        }
    }
    
    protected boolean isSmall() {
        return comboBox.getFont().getSize() <= 11;
    }
    
    /**
     * Returns the area that is reserved for drawing the currently selected item.
     * Note: Changes in this method also require changes in method getMinimumSize.
     */
    protected Rectangle rectangleForCurrentValue() {
        return rectangleForCurrentValue(comboBox.getWidth(), comboBox.getHeight());
    }
    /**
     * Returns the area that is reserved for drawing the currently selected item.
     * Note: Changes in this method also require changes in method getMinimumSize.
     */
    protected Rectangle rectangleForCurrentValue(int width, int height) {
        Insets insets = getInsets();
        Insets margin = getMargin();
        if (comboBox.isEditable()) {
            if (!isTableCellEditor()) {
                insets.right -= margin.right;
                /*
                insets.left--;
                insets.top--;
                insets.bottom--;*/
                insets.left -= margin.left - 2;
                insets.top -= margin.top - 2;
                insets.bottom -= margin.bottom - 2;
            }
        } else {
            
            if (isTableCellEditor()) {
                insets.top -= 1;
            } else {
                
                //insets.right += margin.right; no right-margin because we
                // want no gap between button and renderer!
                insets.left += 6;
                insets.top += margin.top;
                insets.left += margin.left;
                insets.bottom += margin.bottom;
            }
            
            
        }
        return new Rectangle(
                insets.left,
                insets.top,
                width - getArrowWidth() - insets.right - insets.left,
                height - insets.top - insets.bottom
                );
    }
    
    protected Insets getMargin() {
        Insets margin = (Insets) comboBox.getClientProperty("Quaqua.Component.visualMargin");
        if (margin == null) margin = UIManager.getInsets("Component.visualMargin");
        return (Insets) margin.clone();
    }
    
    /**
     * Note: Changes in this method also require changes in method rectangelForCurrentValue.
     */
    public Dimension getMinimumSize( JComponent c ) {
        if ( !isMinimumSizeDirty ) {
            return new Dimension( cachedMinimumSize );
        }
        
        Dimension size = null;
        if ( !comboBox.isEditable() &&
                arrowButton != null &&
                arrowButton instanceof QuaquaComboBoxButton ) {
            
            QuaquaComboBoxButton button = (QuaquaComboBoxButton)arrowButton;
            Insets buttonInsets = new Insets(4, 11, 3, getArrowWidth() + 5);
            if (isSmall()) {
                buttonInsets.bottom -= 1;
            }
            Insets insets = getInsets();
            size = getDisplaySize();
            size.width += insets.left + insets.right
                    + buttonInsets.left + buttonInsets.right;
            size.height += insets.top + insets.bottom
                    + buttonInsets.top + buttonInsets.bottom;
            
        } else if ( comboBox.isEditable()
        && arrowButton != null
                && editor != null ) {
            QuaquaComboBoxButton button = (QuaquaComboBoxButton)arrowButton;
            Insets buttonInsets;
            Insets insets = comboBox.getInsets();
            Insets margin = getMargin();
            buttonInsets = new Insets(2 - margin.top, 4 - margin.left, 2 - margin.bottom, getArrowWidth());
            
            // Margin is included in display size, therefore no need to add
            // it to size. We subtract the margin at the right, because we
            // want the text field's focus ring to glow over the right button.
            size = getDisplaySize();
            size.width += insets.left + insets.right
                    + buttonInsets.left + buttonInsets.right;
            size.height += insets.top + insets.bottom
                    + buttonInsets.top + buttonInsets.bottom;
        } else {
            size = super.getMinimumSize( c );
        }
        
        cachedMinimumSize.setSize( size.width, size.height );
        isMinimumSizeDirty = false;
        return new Dimension( cachedMinimumSize );
    }
    
    public Dimension getMaximumSize(JComponent c) {
        Dimension size = getPreferredSize(c);
        if (! (c.getParent() instanceof JToolBar)) {
            size.width = Short.MAX_VALUE;
        }
        return size;
    }
    
    /**
     * Creates a <code>FocusListener</code> which will be added to the combo box.
     * If this method returns null then it will not be added to the combo box.
     *
     * @return an instance of a <code>FocusListener</code> or null
     */
    protected FocusListener createFocusListener() {
        return new GlowFocusHandler();
    }
    
    public int getBaseline(JComponent c, int width, int height) {
        Rectangle vb = getVisualBounds(c, VisuallyLayoutable.TEXT_BOUNDS, width, height);
        return (vb == null) ? -1 : vb.y + vb.height;
    }
    public Rectangle getVisualBounds(JComponent c, int layoutType, int width, int height) {
        Rectangle bounds = new Rectangle(0,0,width,height);
        if (layoutType == VisuallyLayoutable.CLIP_BOUNDS) {
            return bounds;
        }
        
        JComboBox cb = (JComboBox) c;
        
        Rectangle buttonRect = new Rectangle();
        Rectangle editorRect = null;
        
        Insets insets = getInsets();
        Insets margin = getMargin();
        int buttonSize = height - (insets.top + insets.bottom);
        Rectangle cvb;
        if (arrowButton != null) {
            if(QuaquaUtilities.isLeftToRight(cb)) {
                int plusHeight = (isSmall()) ? 5 : 4;
                buttonRect.setBounds(
                        width - getArrowWidth()-insets.right,
                        insets.top + margin.top - 2,
                        getArrowWidth(),
                        buttonSize - margin.top - margin.bottom + plusHeight
                        );
            } else {
                buttonRect.setBounds(insets.left, insets.top,
                        getArrowWidth(), buttonSize);
            }
        }
        editorRect = rectangleForCurrentValue(width, height);
        
        // FIXME we shouldn't hardcode this and determine the real visual
        // bounds of the renderer instead.
        // Subtract 2 from x because of the insets of the renderer
        editorRect.x += 1;
        editorRect.width -= 2;
        
        switch (layoutType) {
            case VisuallyLayoutable.COMPONENT_BOUNDS :
                if (! isTableCellEditor()) {
                    if (editor != null) {
                        bounds.x += margin.left;
                        bounds.y += margin.top;
                        bounds.width -= margin.left + margin.right;
                        bounds.height -= margin.top + margin.bottom + 1;
                    } else {
                        bounds.x += margin.left;
                        bounds.y += margin.top;
                        bounds.width -= margin.left + margin.right;
                        bounds.height -= margin.top + margin.bottom;
                    }
                }
                break;
            case VisuallyLayoutable.TEXT_BOUNDS :
                Object renderer = (editor == null)
                ? (Object) cb.getRenderer().getListCellRendererComponent(listBox, cb.getSelectedItem(), cb.getSelectedIndex(), false, cb.hasFocus())
                : (Object) editor;
                if ((renderer instanceof JComponent)
                && (Methods.invokeGetter(renderer,"getUI", null) instanceof VisuallyLayoutable)) {
                    bounds = ((VisuallyLayoutable) Methods.invokeGetter(renderer, "getUI", null)).getVisualBounds((JComponent) renderer, layoutType, editorRect.width, editorRect.height);
                    bounds.x += editorRect.x;
                    bounds.y += editorRect.y;
                } else {
                    bounds.setBounds(editorRect);
                }
                break;
        }
        return bounds;
    }
    
    /**
     * This listener hides the popup when the focus is lost.  It also repaints
     * when focus is gained or lost.
     *
     * This public inner class should be treated as protected.
     * Instantiate it only within subclasses of
     * <code>BasicComboBoxUI</code>.
     */
    public class GlowFocusHandler extends BasicComboBoxUI.FocusHandler {
        public void focusGained( FocusEvent e ) {
            super.focusGained(e);
            glowyRepaint();
        }
        
        public void focusLost( FocusEvent e ) {
            super.focusLost(e);
            glowyRepaint();
        }
        private void glowyRepaint() {
            if (comboBox.getParent() != null) {
                Rectangle r = comboBox.getBounds();
                r.grow(2,2);
                comboBox.getParent().repaint(r.x, r.y, r.width, r.height);
            }
        }
    }
}


