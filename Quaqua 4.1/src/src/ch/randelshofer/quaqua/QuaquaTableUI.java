/*
 * @(#)QuaquaTableUI.java  1.4  2007-01-16
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
import ch.randelshofer.quaqua.util.ViewportPainter;
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
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * QuaquaTableUI.
 *
 * @author  Werner Randelshofer
 * @version 1.4 2007-01-16 Focus border repainting factored out into QuaquaViewportUI.
 * <br>1.3.3 2007-01-15 Change foreground color of cell renderer even if
 * it is not an UIResource.
 * <br>1.3.2 2007-01-05 Issue #6: Selection needs to be drawn differently
 * when table hasn't focus or is disabled or is on an inactive window.
 * Issue #10: Table cells mustn't draw selection background when
 * rowSelectionAllowed is false.
 * <br>1.3.1 2006-05-04 EditorCell was always drawn with alternating
 * row color even when the table style was not set to striped.
 * <br>1.3 2006-02-07 Support for client property "Table.isFileList" added.
 * <br>1.2.1 2005-08-25 If the table is not striped, fill the viewport with
 * the background color of the table.
 * <br>1.2 2005-03-11 LnF Property "Table.alternateBackground" replaced
 * by "Table.alternateBackground.0" and "Table.alternateBackground.1".
 * <br>1.1 2004-07-04 FocusHandler added.
 * <br>1.0  June 22, 2004  Created.
 */
public class QuaquaTableUI extends BasicTableUI
        implements ViewportPainter {
    private PropertyChangeListener propertyChangeListener;
    private boolean isStriped = false;
    
    /** Creates a new instance. */
    public QuaquaTableUI() {
    }
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaTableUI();
    }
    
    
    private Color getAlternateColor(int modulo) {
        if (modulo == 0) {
            return UIManager.getColor("Table.alternateBackground.0");
        } else {
            return UIManager.getColor("Table.alternateBackground.1");
        }
    }
    /**
     * Attaches listeners to the JTable.
     */
    protected void installListeners() {
        super.installListeners();
        propertyChangeListener = createPropertyChangeListener();
        table.addPropertyChangeListener(propertyChangeListener);
        // table.add
    }
    protected void uninstallListeners() {
        super.uninstallListeners();
        table.removePropertyChangeListener(propertyChangeListener);
        
        propertyChangeListener = null;
    }
    protected void installDefaults() {
        super.installDefaults();
        Object property = table.getClientProperty("Quaqua.Table.style");
        isStriped = property != null && property.equals("striped");
        updateStriped();
    }
    private void updateStriped() {
        if (isStriped) {
            table.setIntercellSpacing(new Dimension(1, 1));
            table.setShowHorizontalLines(false);
            table.setShowVerticalLines(true);
            
            Color gridColor = table.getGridColor();
            if (gridColor == null || gridColor instanceof UIResource) {
                table.setGridColor(new ColorUIResource(192, 192, 192));
            }
        } else {
            //getTableHeader().setDefaultRenderer(new DefaultTableHeaderRenderer());
            table.setIntercellSpacing(new Dimension(1, 1));
            table.setShowHorizontalLines(false);
            table.setShowVerticalLines(false);
            Color gridColor = table.getGridColor();
            if (gridColor == null || gridColor instanceof UIResource) {
                table.setGridColor(new ColorUIResource(255, 255, 255));
            }
        }
    }
    
    
    /** Paint a representation of the <code>table</code> instance
     * that was set in installUI().
     */
    public void paint(Graphics g, JComponent c) {
        if (table.getRowCount() <= 0 || table.getColumnCount() <= 0) {
            return;
        }
        Rectangle clip = g.getClipBounds();
        Point upperLeft = clip.getLocation();
        Point lowerRight = new Point(clip.x + clip.width - 1, clip.y + clip.height - 1);
        int rMin = table.rowAtPoint(upperLeft);
        int rMax = table.rowAtPoint(lowerRight);
        // This should never happen.
        if (rMin == -1) {
            rMin = 0;
        }
        // If the table does not have enough rows to fill the view we'll get -1.
        // Replace this with the index of the last row.
        if (rMax == -1) {
            rMax = table.getRowCount() - 1;
        }
        
        boolean ltr = table.getComponentOrientation().isLeftToRight();
        int cMin = table.columnAtPoint(ltr ? upperLeft : lowerRight);
        int cMax = table.columnAtPoint(ltr ? lowerRight : upperLeft);
        // This should never happen.
        if (cMin == -1) {
            cMin = 0;
        }
        // If the table does not have enough columns to fill the view we'll get -1.
        // Replace this with the index of the last column.
        if (cMax == -1) {
            cMax = table.getColumnCount() - 1;
        }
        
        // Paint the grid.
        paintGrid(g, rMin, rMax, cMin, cMax);
        
        // Paint the cells.
        paintCells(g, rMin, rMax, cMin, cMax);
    }
    
    public void paintViewport(Graphics g, JViewport c) {
        if (isStriped) {
            // Fill the viewport with alternate color 1
            g.setColor(getAlternateColor(1));
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
            
            // Now check if we need to paint some stripes
            Dimension vs = c.getSize();
            Dimension ts = table.getSize();
            Point p = table.getLocation();
            int rh = table.getRowHeight();
            int n = table.getRowCount();
            int row = Math.abs(p.y / rh);
            int th = n * rh - row * rh;
            
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
                
                // Paint the vertical grid lines
                g.setColor(Color.lightGray);
                TableColumnModel cm = table.getColumnModel();
                n = cm.getColumnCount();
                y = th;
                int x = table.getX() -1;
                for (int i = 0; i < n; i++) {
                    TableColumn col = cm.getColumn(i);
                    x += col.getWidth();
                    g.drawLine(x, y, x, vs.height);
                }
            }
        } else {
            // Fill the viewport with the background color of the table
            g.setColor(table.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }
    
    
    /*
     * Paints the grid lines within <I>aRect</I>, using the grid
     * color set with <I>setGridColor</I>. Paints vertical lines
     * if <code>getShowVerticalLines()</code> returns true and paints
     * horizontal lines if <code>getShowHorizontalLines()</code>
     * returns true.
     */
    private void paintGrid(Graphics g, int rMin, int rMax, int cMin, int cMax) {
        g.setColor(table.getGridColor());
        
        Rectangle minCell = table.getCellRect(rMin, cMin, true);
        Rectangle maxCell = table.getCellRect(rMax, cMax, true);
        Rectangle damagedArea = minCell.union( maxCell );
        
        if (table.getShowHorizontalLines()) {
            int tableWidth = damagedArea.x + damagedArea.width;
            int y = damagedArea.y;
            for (int row = rMin; row <= rMax; row++) {
                y += table.getRowHeight(row);
                g.drawLine(damagedArea.x, y - 1, tableWidth - 1, y - 1);
            }
        }
        if (table.getShowVerticalLines()) {
            TableColumnModel cm = table.getColumnModel();
            int tableHeight = damagedArea.y + damagedArea.height;
            int x;
            if (table.getComponentOrientation().isLeftToRight()) {
                x = damagedArea.x;
                for (int column = cMin; column <= cMax; column++) {
                    int w = cm.getColumn(column).getWidth();
                    x += w;
                    g.drawLine(x - 1, 0, x - 1, tableHeight - 1);
                }
            } else {
                x = damagedArea.x + damagedArea.width;
                for (int column = cMin; column < cMax; column++) {
                    int w = cm.getColumn(column).getWidth();
                    x -= w;
                    g.drawLine(x - 1, 0, x - 1, tableHeight - 1);
                }
                x -= cm.getColumn(cMax).getWidth();
                g.drawLine(x, 0, x, tableHeight - 1);
            }
        }
    }
    
    private void paintDraggedArea(Graphics g, int rMin, int rMax, TableColumn draggedColumn, int distance) {
        int draggedColumnIndex = viewIndexForColumn(draggedColumn);
        
        Rectangle minCell = table.getCellRect(rMin, draggedColumnIndex, true);
        Rectangle maxCell = table.getCellRect(rMax, draggedColumnIndex, true);
        
        Rectangle vacatedColumnRect = minCell.union(maxCell);
        
        // Paint a gray well in place of the moving column.
        g.setColor(table.getParent().getBackground());
        g.fillRect(vacatedColumnRect.x, vacatedColumnRect.y,
                vacatedColumnRect.width, vacatedColumnRect.height);
        
        // Move to the where the cell has been dragged.
        vacatedColumnRect.x += distance;
        
        // Fill the background.
        g.setColor(table.getBackground());
        g.fillRect(vacatedColumnRect.x, vacatedColumnRect.y,
                vacatedColumnRect.width, vacatedColumnRect.height);
        
        // Paint the vertical grid lines if necessary.
        if (table.getShowVerticalLines()) {
            g.setColor(table.getGridColor());
            int x1 = vacatedColumnRect.x;
            int y1 = vacatedColumnRect.y;
            int x2 = x1 + vacatedColumnRect.width - 1;
            int y2 = y1 + vacatedColumnRect.height - 1;
            // Left
            g.drawLine(x1-1, y1, x1-1, y2);
            // Right
            g.drawLine(x2, y1, x2, y2);
        }
        
        boolean isFocused = table.hasFocus() && QuaquaUtilities.isOnActiveWindow(table);
        
        for(int row = rMin; row <= rMax; row++) {
            // Render the cell value
            Rectangle r = table.getCellRect(row, draggedColumnIndex, false);
            r.x += distance;
            paintCell(g, r, row, draggedColumnIndex, isFocused);
            
            // Paint the (lower) horizontal grid line if necessary.
            if (table.getShowHorizontalLines()) {
                g.setColor(table.getGridColor());
                Rectangle rcr = table.getCellRect(row, draggedColumnIndex, true);
                rcr.x += distance;
                int x1 = rcr.x;
                int y1 = rcr.y;
                int x2 = x1 + rcr.width - 1;
                int y2 = y1 + rcr.height - 1;
                g.drawLine(x1, y2, x2, y2);
            }
        }
    }
    
    private int viewIndexForColumn(TableColumn aColumn) {
        TableColumnModel cm = table.getColumnModel();
        for (int column = 0; column < cm.getColumnCount(); column++) {
            if (cm.getColumn(column) == aColumn) {
                return column;
            }
        }
        return -1;
    }
    
    private void paintCells(Graphics g, int rMin, int rMax, int cMin, int cMax) {
        // Ugly dirty hack to get correct painting of inactive tables
        boolean isFocused = QuaquaUtilities.isFocused(table);
        
        JTableHeader header = table.getTableHeader();
        TableColumn draggedColumn = (header == null) ? null : header.getDraggedColumn();
        
        TableColumnModel cm = table.getColumnModel();
        int columnMargin = cm.getColumnMargin();
        
        Rectangle cellRect;
        TableColumn aColumn;
        int columnWidth;
        if (table.getComponentOrientation().isLeftToRight()) {
            for(int row = rMin; row <= rMax; row++) {
                cellRect = table.getCellRect(row, cMin, false);
                for(int column = cMin; column <= cMax; column++) {
                    aColumn = cm.getColumn(column);
                    columnWidth = aColumn.getWidth();
                    cellRect.width = columnWidth - columnMargin;
                    if (aColumn != draggedColumn) {
                        paintCell(g, cellRect, row, column, isFocused);
                    }
                    cellRect.x += columnWidth;
                }
            }
        } else {
            for(int row = rMin; row <= rMax; row++) {
                cellRect = table.getCellRect(row, cMin, false);
                aColumn = cm.getColumn(cMin);
                if (aColumn != draggedColumn) {
                    columnWidth = aColumn.getWidth();
                    cellRect.width = columnWidth - columnMargin;
                    paintCell(g, cellRect, row, cMin, isFocused);
                }
                for(int column = cMin+1; column <= cMax; column++) {
                    aColumn = cm.getColumn(column);
                    columnWidth = aColumn.getWidth();
                    cellRect.width = columnWidth - columnMargin;
                    cellRect.x -= columnWidth;
                    if (aColumn != draggedColumn) {
                        paintCell(g, cellRect, row, column, isFocused);
                    }
                }
            }
        }
        
        // Paint the dragged column if we are dragging.
        if (draggedColumn != null) {
            paintDraggedArea(g, rMin, rMax, draggedColumn, header.getDraggedDistance());
        }
        
        // Remove any renderers that may be left in the rendererPane.
        rendererPane.removeAll();
        
        // Ugly dirty hack to get proper rendering of inactive tables
        // Here we clean up the values of the "active" property of the selection
        // colors.
        if (! isFocused) {
            Color background = UIManager.getColor("Table.selectionBackground");
            Color foreground = UIManager.getColor("Table.selectionForeground");
            if (background instanceof InactivatableColorUIResource) {
                ((InactivatableColorUIResource) background).setActive(true);
            }
            if (foreground instanceof InactivatableColorUIResource) {
                ((InactivatableColorUIResource) foreground).setActive(true);
            }
        }
    }
    
    private void paintCell(Graphics g, Rectangle cellRect, int row, int column, boolean isFocused) {
        Color background = UIManager.getColor("Table.selectionBackground");
        Color foreground = UIManager.getColor("Table.selectionForeground");
        if (background instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) background).setActive(isFocused);
        }
        if (foreground instanceof InactivatableColorUIResource) {
            ((InactivatableColorUIResource) foreground).setActive(isFocused);
        }
        
        
        if (table.isEditing() && table.getEditingRow()==row &&
                table.getEditingColumn()==column) {
            Component component = table.getEditorComponent();
            component.setBounds(cellRect);
          //  component.setBackground((isStriped) ? getAlternateColor(row % 2) : table.getBackground());
            // We only need to paint the alternate background color for even
            // rows, because the background for uneven rows is painted by
            // method paintViewport().
            
            if (isStriped && row % 2 == 0) {
                g.setColor(getAlternateColor(0));
                g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
            }
            component.setFont(table.getFont());
            component.validate();
        } else {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component component = table.prepareRenderer(renderer, row, column);
            if (table.isEditing() || ! table.isCellSelected(row, column)) {
                if (isStriped) {
                    background = getAlternateColor(row % 2);
                } else {
                    background = table.getBackground();
                }
                if (foreground instanceof InactivatableColorUIResource) {
                    ((InactivatableColorUIResource) foreground).setActive(false);
                }
            }
            
            g.setColor(background);
            g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
            
            if ((component instanceof UIResource) && (component instanceof JComponent)) {
                ((JComponent) component).setOpaque(false);
            }
            
            
           // component.setBackground(background);
            rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y,
                    cellRect.width, cellRect.height, true);
            
        }
    }
    /**
     * Creates the mouse listener for the JTable.
     */
    protected MouseInputListener createMouseInputListener() {
        return new MouseHandler();
    }
    
    /**
     * Creates the property change listener for the JTable.
     */
    private PropertyChangeListener createPropertyChangeListener() {
        return new PropertyChangeHandler();
    }
    /**
     * Creates the focus listener for handling keyboard navigation in the JTable.
     */
    protected FocusListener createFocusListener() {
        return new FocusHandler();
    }
    /**
     * PropertyChangeListener for the table. Updates the appropriate
     * varaible, or TreeState, based on what changes.
     */
    private class PropertyChangeHandler implements
            PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent event) {
            String changeName = event.getPropertyName();
            
            if (changeName.equals("Quaqua.Table.style")) {
                Object value = event.getNewValue();
                isStriped = value != null && value.equals("striped");
                updateStriped();
            }
        }
    } // End of BasicTableUI.PropertyChangeHandler
    
    //
    //  The Table's focus listener
    //
    
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicTableUI.
     */
    public class MouseHandler implements MouseInputListener {
        
        // Component receiving mouse events during editing.
        // May not be editorComponent.
        private Component dispatchComponent;
        private boolean selectedOnPress;
        
        //  The Table's mouse listener methods.
        
        public void mouseClicked(MouseEvent e) {}
        
        private void setDispatchComponent(MouseEvent e) {
            Component editorComponent = table.getEditorComponent();
            Point p = e.getPoint();
            Point p2 = SwingUtilities.convertPoint(table, p, editorComponent);
            dispatchComponent = SwingUtilities.getDeepestComponentAt(editorComponent,
                    p2.x, p2.y);
        }
        
        private boolean repostEvent(MouseEvent e) {
            // Check for isEditing() in case another event has
            // caused the editor to be removed. See bug #4306499.
            if (dispatchComponent == null || !table.isEditing()) {
                return false;
            }
            MouseEvent e2 = SwingUtilities.convertMouseEvent(table, e, dispatchComponent);
            dispatchComponent.dispatchEvent(e2);
            return true;
        }
        
        private void setValueIsAdjusting(boolean flag) {
            table.getSelectionModel().setValueIsAdjusting(flag);
            table.getColumnModel().getSelectionModel().setValueIsAdjusting(flag);
        }
        
        private boolean shouldIgnore(MouseEvent e) {
            return e.isConsumed()
            || (!(SwingUtilities.isLeftMouseButton(e) && table.isEnabled()))
            || e.isPopupTrigger() && 
                    (table.rowAtPoint(e.getPoint()) == -1 || 
                    table.isRowSelected(table.rowAtPoint(e.getPoint())));
        }
        
        public void mousePressed(MouseEvent e) {
            if (e.isConsumed()) {
                selectedOnPress = false;
                return;
            }
            selectedOnPress = true;
            adjustFocusAndSelection(e);
        }
        
        void adjustFocusAndSelection(MouseEvent e) {
            if (shouldIgnore(e)) {
                return;
            }
            
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            int column = table.columnAtPoint(p);
            // The autoscroller can generate drag events outside the Table's range.
            if ((column == -1) || (row == -1)) {
                return;
            }
            
            if (table.editCellAt(row, column, e)) {
                setDispatchComponent(e);
                repostEvent(e);
            } else if (table.isRequestFocusEnabled()) {
                table.requestFocus();
            }
            
            CellEditor editor = table.getCellEditor();
            if (editor == null || editor.shouldSelectCell(e)) {
                boolean adjusting = (e.getID() == MouseEvent.MOUSE_PRESSED) ? true : false;
                setValueIsAdjusting(adjusting);
                table.changeSelection(row, column, e.isMetaDown(), e.isShiftDown());
            }
        }
        
        public void mouseReleased(MouseEvent e) {
            if (selectedOnPress) {
                if (shouldIgnore(e)) {
                    return;
                }
                
                repostEvent(e);
                dispatchComponent = null;
                setValueIsAdjusting(false);
            } else {
                adjustFocusAndSelection(e);
            }
        }
        
        
        public void mouseEntered(MouseEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        
        //  The Table's mouse motion listener methods.
        
        public void mouseMoved(MouseEvent e) {}
        
        public void mouseDragged(MouseEvent e) {
            if (shouldIgnore(e)) {
                return;
            }
            
            repostEvent(e);
            
            CellEditor editor = table.getCellEditor();
            if (editor == null || editor.shouldSelectCell(e)) {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                int column = table.columnAtPoint(p);
                // The autoscroller can generate drag events outside the Table's range.
                if ((column == -1) || (row == -1)) {
                    return;
                }
                // Fix for 4835633
                // Until we support drag-selection, dragging should not change
                // the selection (act like single-select).
                Object bySize = table.getClientProperty("Table.isFileList");
                if (bySize instanceof Boolean &&
                        ((Boolean)bySize).booleanValue()) {
                    return;
                }
                table.changeSelection(row, column, false, true);
            }
        }
    }
    
    private class FocusHandler implements FocusListener {
        // FocusListener
        private void repaintSelection( ) {
            int[] rows = table.getSelectedRows();
            Rectangle dirtyRect = null;
            for (int r=0; r<rows.length; r++) {
                for (int c=0, n = table.getColumnCount(); c < n; c++) {
                    table.repaint(table.getCellRect(rows[r], c, false));
                }
            }
        }

        public void focusGained(FocusEvent e) {
            repaintSelection();
        }

        public void focusLost(FocusEvent e) {
            repaintSelection();
        }
    }
}
