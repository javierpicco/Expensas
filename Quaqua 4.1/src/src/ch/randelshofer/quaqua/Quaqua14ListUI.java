/*
 * @(#)Quaqua13ListUI.java  1.2.4  2007-11-10
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

import ch.randelshofer.quaqua.util.InactivatableColorUIResource;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.lang.reflect.*;

/**
 * Quaqua14ListUI for Java 1.4.
 * 
 * @author Werner Randelshofer
 * @version 1.2.4 2007-10-11 Use List.selectionBackground instead
 * of Tree.selectionBackground. 
 * <br>1.2.3 2007-08-01 Don't change the selection when the user
 * triggers the popup menu.
 * <br>1.2.3 2007-08-01 Don't change the selection when the user
 * triggers the popup menu.
 * <br>1.2.2 2007-05-18 Fixed NPE when ListDataHandler.contentsChanged
 * is called on non-existing rows.
 * <br>1.2.1 2007-04-28 An exception was thrown when items where added
 * to the list and no item was selected.
 * <br>1.2 2007-01-29 Optimized repaint on contentsChanged event.
 * <br>1.1.4 2007-01-16 Focus border repainting factored out into
 * QuaquaViewportUI. Reimplemented fix for Issue #6 in a new way.
 * <br>1.1.3 2007-01-15 Change foreground color of cell renderer even if
 * it is not an UIResource.
 * <br>1.1.2 2007-01-05 Issue #6: Selection needs to be drawn differently
 * when list hasn't focus or is disabled or is on an inactive window.
 * 1.1.1 2006-04-23 Minor fix to make this class compilable with J2SE 1.5
 * <br>1.1 2005-03-13 LnF Property "List.alternateBackground" replaced
 * by "List.alternateBackground.0" and "List.alternateBackground.1".
 * <br>1.0  December 13, 2004  Created.
 */
public class Quaqua14ListUI extends BasicListUI {
    private boolean isStriped = false;
    
    /**
     * This variable has the value of JList.VERTICAL, if Java 1.4 or higher is
     * present. In older Java VM's it has value 0.
     */
    private final static int VERTICAL;
    static {
        int value = 0;
        try {
            value = JList.class.getField("VERTICAL").getInt(null);
        } catch (Exception e) {
        }
        VERTICAL = value;
    }
    
    private final static Method getLayoutOrientation;
    static {
        Method value = null;
        try {
            value = JList.class.getMethod("getLayoutOrientation", (Class[]) null);
        } catch (Exception e) {
        }
        getLayoutOrientation = value;
    }
    
    /** Creates a new instance. */
    public Quaqua14ListUI() {
    }
    
    
    private Color getAlternateColor(int modulo) {
        if (modulo == 0) {
            return UIManager.getColor("List.alternateBackground.0");
        } else {
            return UIManager.getColor("List.alternateBackground.1");
        }
    }
    private void updateStriped() {
        Object value = list.getClientProperty("Quaqua.List.style");
        isStriped = value != null
                && value.equals("striped")
                && getLayoutOrientation() == VERTICAL;
    }
    
    
    private int getLayoutOrientation() {
        if (getLayoutOrientation != null) {
            try {
                return ((Integer) getLayoutOrientation.invoke(list, (Object[]) null)).intValue();
            } catch (Exception e) {
            }
        }
        return VERTICAL;
    }
    
    public void paintStripes(Graphics g, JComponent c) {
        if (isStriped
                && list.getModel() != null
                //&& list.getLayoutOrientation() == JList.VERTICAL
                ) {
            // Now check if we need to paint some stripes
            Dimension vs = c.getSize();
            Dimension ts = list.getSize();
            
            
            Point p = list.getLocation();
            int rh = list.getFixedCellHeight();
            int n = list.getModel().getSize();
            if (rh <= 0) {
                rh = (n == 0)  ? 12 : getCellBounds(list, 0, 0).height;
            }
            int row = Math.abs(p.y / rh);
            int th = n * rh - row * rh;
            
            // Fill the background of the list with stripe color 1
            g.setColor(getAlternateColor(1));
            g.fillRect(0, 0, ts.width, ts.height);
            
            // Fill rectangles with stripe color 0
            g.setColor(getAlternateColor(0));
            
            // Paint empty rows at the right to fill the viewport
            if (ts.width < vs.width) {
                int y = p.y + row * rh;
                while (y < th) {
                    if (row % 2 == 0) {
                        g.fillRect(0, y, vs.width, rh);
                    }
                    y += rh;
                    row++;
                }
            }
            
            
            // Paint empty rows at the bottom to fill the viewport
            if (th < vs.height) {
                row = n;
                int y = th;
                while (y < vs.height) {
                    if (row % 2 == 0) {
                        g.fillRect(0, y, vs.width, rh);
                    }
                    y += rh;
                    row++;
                }
            }
        }
    }
    
    /**
     * The layout orientation of the list.
     */
    private int layoutOrientation;
    
    /**
     * Paint one List cell: compute the relevant state, get the "rubber stamp"
     * cell renderer component, and then use the CellRendererPane to paint it.
     * Subclasses may want to override this method rather than paint().
     *
     * @see #paint
     */
    protected void paintCell(
            Graphics g,
            int row,
            Rectangle rowBounds,
            ListCellRenderer cellRenderer,
            ListModel dataModel,
            ListSelectionModel selModel,
            int leadIndex) {
        Object value = dataModel.getElementAt(row);
        boolean isFocused = list.isEnabled() &&
                QuaquaUtilities.isFocused(list);
        boolean cellHasFocus = isFocused && (row == leadIndex);
        boolean isSelected = selModel.isSelectedIndex(row);
        
        Component rendererComponent =
                cellRenderer.getListCellRendererComponent(list, value, row, isSelected, cellHasFocus);
        
        int cx = rowBounds.x;
        int cy = rowBounds.y;
        int cw = rowBounds.width;
        int ch = rowBounds.height;
        
        if (list.isSelectedIndex(row)) {
            g.setColor(UIManager.getColor("List.selectionBackground"));
            g.fillRect(cx, cy, cw, ch);
        } else {
            if (isStriped) {
                //rendererComponent.setBackground(getAlternateColor(row % 2));
                g.setColor(getAlternateColor(row % 2));
                g.fillRect(cx, cy, cw, ch);
            }
        }
        
        rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw, ch, true);
    }
    
    
    /**
     * Paint the rows that intersect the Graphics objects clipRect.  This
     * method calls paintCell as necessary.  Subclasses
     * may want to override these methods.
     *
     * @see #paintCell
     */
    public void paint(Graphics g, JComponent c) {
        paintStripes(g, c);
        
        boolean isFocused = QuaquaUtilities.isFocused(c);
        Color selectionBackground = UIManager.getColor("List.selectionBackground");
        Color selectionForeground = UIManager.getColor("List.selectionForeground");
        if (selectionBackground instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) selectionBackground).setActive(isFocused);
        }
        if (selectionForeground instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) selectionForeground).setActive(isFocused);
        }
        super.paint(g, c);
        if (selectionBackground instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) selectionBackground).setActive(true);
        }
        if (selectionForeground instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) selectionForeground).setActive(true);
        }
    }
    
    /**
     * Initialize JList properties, e.g. font, foreground, and background,
     * and add the CellRendererPane.  The font, foreground, and background
     * properties are only set if their current value is either null
     * or a UIResource, other properties are set if the current
     * value is null.
     *
     * @see #uninstallDefaults
     * @see #installUI
     * @see CellRendererPane
     */
    protected void installDefaults() {
        super.installDefaults();
        updateStriped();
        
    }
    
    
    
    /**
     * Returns a new instance of Quaqua13ListUI.  Quaqua13ListUI delegates are
     * allocated one per JList.
     * 
     * @return A new ListUI implementation for the Windows look and feel.
     */
    public static ComponentUI createUI(JComponent list) {
        return new Quaqua14ListUI();
    }
    
    
    /**
     * Mouse input, and focus handling for JList.  An instance of this
     * class is added to the appropriate java.awt.Component lists
     * at installUI() time.  Note keyboard input is handled with JComponent
     * KeyboardActions, see installKeyboardActions().
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     *
     * @see #createMouseInputListener
     * @see #installKeyboardActions
     * @see #installUI
     */
    public class MouseInputHandler implements MouseInputListener {
        private boolean mouseReleaseDeselects;
        private boolean mouseDragSelects;
        
        public void mouseClicked(MouseEvent e) {}
        
        public void mouseEntered(MouseEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        
        public void mousePressed(MouseEvent e) {
            int index = locationToIndex(list, e.getPoint());
            /*
            if (index == -1) {
                mouseDragSelects = false;
                mouseReleaseDeselects = false;
            } else {
                mouseDragSelects = ! list.isSelectedIndex(index);
                mouseReleaseDeselects = ! mouseDragSelects &&
                        (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) == 0;
                if (mouseDragSelects) {
                    if ((e.getModifiersEx() & MouseEvent.META_DOWN_MASK) == 0) {
                    list.clearSelection();
                    }
                    Rectangle cellBounds = getCellBounds(list, index, index);
                    list.scrollRectToVisible(cellBounds);
                    list.addSelectionInterval(index, index);
                }
            }*/
            mouseDragSelects = false;
            mouseReleaseDeselects = false;
            if (index != -1) {
                if (list.isSelectedIndex(index) && e.isPopupTrigger()) {
                    // Do not change the selection, if the item is already
                    // selected, and the user triggers the popup menu.
                } else {
                    int anchorIndex = list.getAnchorSelectionIndex();

                    if ((e.getModifiersEx() & (MouseEvent.META_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK)) == MouseEvent.META_DOWN_MASK) {
                        if (list.isSelectedIndex(index)) {
                            list.removeSelectionInterval(index, index);
                        } else {
                            list.addSelectionInterval(index, index);
                            mouseDragSelects = true;
                        }
                    } else if ((e.getModifiersEx() & (MouseEvent.SHIFT_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK)) == MouseEvent.SHIFT_DOWN_MASK &&
                            anchorIndex != -1) {
                        list.setSelectionInterval(anchorIndex, index);
                        mouseDragSelects = true;
                    } else if ((e.getModifiersEx() & (MouseEvent.SHIFT_DOWN_MASK | MouseEvent.META_DOWN_MASK)) == 0) {
                        if (list.isSelectedIndex(index)) {
                            mouseReleaseDeselects = true;
                        } else {
                            list.setSelectionInterval(index, index);
                            mouseDragSelects = true;
                        }
                        list.getSelectionModel().setAnchorSelectionIndex(index);
                    }
                }
            }
            list.getSelectionModel().setValueIsAdjusting(mouseDragSelects);
            list.requestFocus();
        }
        public void mouseDragged(MouseEvent e) {
            mouseReleaseDeselects = false;
            if (mouseDragSelects) {
                int index = locationToIndex(list, e.getPoint());
                if (index != -1) {
                    Rectangle cellBounds = getCellBounds(list, index, index);
                    list.scrollRectToVisible(cellBounds);
                    int anchorIndex = list.getAnchorSelectionIndex();
                    list.addSelectionInterval(anchorIndex, index);
                }
            }
        }
        public void mouseMoved(MouseEvent e) {
        }
        
        public void mouseReleased(MouseEvent e) {
            mouseDragSelects = false;
            if (mouseReleaseDeselects) {
                int index = locationToIndex(list, e.getPoint());
                list.setSelectionInterval(index, index);
            }
            list.getSelectionModel().setValueIsAdjusting(false);
        }
        /*
        public void mousePressed(MouseEvent e) {
            if (e.isConsumed()) {
                // selectedOnPress = false;
                return;
            }
            // selectedOnPress = true;
            adjustFocusAndSelection(e);
        }
         
        public void mouseDragged(MouseEvent e) {
            if (e.isConsumed()) {
                return;
            }
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            if (!list.isEnabled()) {
                return;
            }
            if (e.isShiftDown() || e.isMetaDown()) {
                return;
            }
         
            int row = locationToIndex(list, e.getPoint());
            //int row = convertLocationToModel(e.getX(), e.getY());
            if (row != -1) {
                Rectangle cellBounds = getCellBounds(list, row, row);
                if (cellBounds != null) {
                    list.scrollRectToVisible(cellBounds);
                    list.setSelectionInterval(row, row);
                }
            }
        }
         
        public void mouseMoved(MouseEvent e) {
        }
         
        public void mouseReleased(MouseEvent e) {
            /*if (selectedOnPress) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
         
                list.setValueIsAdjusting(false);
            } else {* /
            adjustFocusAndSelection(e);
            //}
        }
         
        //private boolean selectedOnPress;
        private boolean shouldIgnore(MouseEvent e) {
            return e.isConsumed()
            || (!(SwingUtilities.isLeftMouseButton(e) && list.isEnabled()))
            || e.isPopupTrigger();
        }
         
        void adjustFocusAndSelection(MouseEvent e) {
            if (shouldIgnore(e)) {
                /*
                // I would like to set the lead selection index, without changing
                // the selection. But this does not appear to work.
                if (e.isPopupTrigger()) {
                    int row = convertLocationToModel(e.getX(), e.getY());
                    if (row != -1) {
                        list.getSelectionModel().setLeadSelectionIndex(row);
                    }
         
                }* /
                return;
            }
         
            /* Request focus before updating the list selection.  This implies
         * that the current focus owner will see a focusLost() event
         * before the lists selection is updated IF requestFocus() is
         * synchronous (it is on Windows).  See bug 4122345
         * /
            if (!list.hasFocus() && list.isRequestFocusEnabled()) {
                list.requestFocus();
            }
         
            int row = locationToIndex(list, e.getPoint());
         
         
            //int row = convertLocationToModel(e.getX(), e.getY());
            // BEGIN PATCH
            // if (row != -1) {
            boolean adjusting = (e.getID() == MouseEvent.MOUSE_PRESSED) ? true : false;
            list.setValueIsAdjusting(adjusting);
            if (row != -1) {
                int anchorIndex = list.getAnchorSelectionIndex();
                if (e.isMetaDown()) {
                    if (list.isSelectedIndex(row)) {
                        list.removeSelectionInterval(row, row);
                    } else {
                        list.addSelectionInterval(row, row);
                    }
                } else if (e.isShiftDown() && (anchorIndex != -1)) {
                    list.setSelectionInterval(anchorIndex, row);
                } else {
                    list.setSelectionInterval(row, row);
                }
            }
        }
         */
    }
    
    
    /**
     * Creates a delegate that implements MouseInputListener.
     * The delegate is added to the corresponding java.awt.Component listener
     * lists at installUI() time. Subclasses can override this method to return
     * a custom MouseInputListener, e.g.
     * <pre>
     * class MyListUI extends Quaqua13ListUI {
     *    protected MouseInputListener <b>createMouseInputListener</b>() {
     *        return new MyMouseInputHandler();
     *    }
     *    public class MyMouseInputHandler extends MouseInputHandler {
     *        public void mouseMoved(MouseEvent e) {
     *            // do some extra work when the mouse moves
     *            super.mouseMoved(e);
     *        }
     *    }
     * }
     * </pre>
     * 
     * @see MouseInputHandler
     * @see #installUI
     */
    protected MouseInputListener createMouseInputListener() {
        return new MouseInputHandler();
    }
    
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicTableUI.
     */
    public class FocusHandler implements FocusListener {
        protected void repaintCellFocus() {
            int leadIndex = list.getLeadSelectionIndex();
            if (leadIndex != -1) {
                Rectangle r = getCellBounds(list, leadIndex, leadIndex);
                if (r != null) {
                    list.repaint(r.x, r.y, r.width, r.height);
                }
            }
        }
        
        /* The focusGained() focusLost() methods run when the JList
         * focus changes.
         */
        
        public void focusGained(FocusEvent event) {
            // hasFocus = true;
            repaintCellFocus();
        }
        
        public void focusLost(FocusEvent event) {
            // hasFocus = false;
            repaintCellFocus();
        }
    }
    
    protected FocusListener createFocusListener() {
        return new FocusHandler();
    }
    protected ListDataListener createListDataListener() {
        return new ListDataHandler();
    }
    
    
    /**
     * The PropertyChangeListener that's added to the JList at
     * installUI time.  When the value of a JList property that
     * affects layout changes, we set a bit in updateLayoutStateNeeded.
     * If the JLists model changes we additionally remove our listeners
     * from the old model.  Likewise for the JList selectionModel.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     *
     * @see #maybeUpdateLayoutState
     * @see #createPropertyChangeListener
     * @see #installUI
     */
    public class PropertyChangeHandler extends BasicListUI.PropertyChangeHandler {
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            
            if (propertyName.equals("Quaqua.List.style")) {
                updateStriped();
            } else if ("layoutOrientation".equals(propertyName)) {
                layoutOrientation = ((Integer) e.getNewValue()).intValue();
                updateStriped();
            }
            super.propertyChange(e);
        }
    }
    
    
    /**
     * Creates an instance of PropertyChangeHandler that's added to
     * the JList by installUI().  Subclasses can override this method
     * to return a custom PropertyChangeListener, e.g.
     * <pre>
     * class MyListUI extends Quaqua13ListUI {
     *    protected PropertyChangeListener <b>createPropertyChangeListener</b>() {
     *        return new MyPropertyChangeListener();
     *    }
     *    public class MyPropertyChangeListener extends PropertyChangeHandler {
     *        public void propertyChange(PropertyChangeEvent e) {
     *            if (e.getPropertyName().equals("model")) {
     *                // do some extra work when the model changes
     *            }
     *            super.propertyChange(e);
     *        }
     *    }
     * }
     * </pre>
     * 
     * @see PropertyChangeListener
     * @see #installUI
     */
    protected PropertyChangeListener createPropertyChangeListener() {
        return new PropertyChangeHandler();
    }
    
    protected class ListDataHandler implements ListDataListener {
        //
        // ListDataListener
        //
        public void intervalAdded(ListDataEvent e) {
            updateLayoutStateNeeded = modelChanged;
            
            int minIndex = Math.min(e.getIndex0(), e.getIndex1());
            int maxIndex = Math.max(e.getIndex0(), e.getIndex1());
            
            /* Sync the SelectionModel with the DataModel.
             */
            
            ListSelectionModel sm = list.getSelectionModel();
            if (sm != null && sm.getMinSelectionIndex() != -1) {
                sm.insertIndexInterval(minIndex, maxIndex - minIndex+1, true);
            }
            
            /* Repaint the entire list, from the origin of
             * the first added cell, to the bottom of the
             * component.
             */
            redrawList();
        }
        
        
        public void intervalRemoved(ListDataEvent e) {
            updateLayoutStateNeeded = modelChanged;
            
            /* Sync the SelectionModel with the DataModel.
             */
            
            ListSelectionModel sm = list.getSelectionModel();
            if (sm != null) {
                sm.removeIndexInterval(e.getIndex0(), e.getIndex1());
            }
            
            /* Repaint the entire list, from the origin of
             * the first removed cell, to the bottom of the
             * component.
             */
            
            redrawList();
        }
        
        
        public void contentsChanged(ListDataEvent e) {
            updateLayoutStateNeeded = modelChanged;
            
            if (list.getFixedCellHeight() == -1) {
                redrawList();
            } else {
                Rectangle bounds = list.getCellBounds(e.getIndex0(), e.getIndex1());
                if (bounds == null) {
                    redrawList();
                } else {
                    list.repaint(bounds);
                }
            }
        }
    }
    
    private void redrawList() {
        list.revalidate();
        list.repaint();
    }
}
