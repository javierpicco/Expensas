/*
 * @(#)QuaquaTableHeaderUI.java  1.1  2006-04-08
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

import ch.randelshofer.quaqua.BackgroundBorder;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.text.*;
/**
 * QuaquaTableHeaderUI.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-04-08 Take insets of table header border into account
 * when determining the height of the table header.
 * <br>1.0  June 22, 2004  Created.
 */
public class QuaquaTableHeaderUI extends BasicTableHeaderUI {
    // Listeners that are attached to the JTable
    protected TableColumnModelListener columnModelListener;
    
    /** Creates a new instance. */
    public QuaquaTableHeaderUI() {
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaTableHeaderUI();
    }
    
    public void installDefaults() {
        super.installDefaults();
        if (header.getDefaultRenderer() instanceof JLabel) {
            ((JLabel) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEADING);
            //((JLabel) header.getDefaultRenderer()).setOpaque(false);
        }
    }
    
    private int viewIndexForColumn(TableColumn aColumn) {
        TableColumnModel cm = header.getColumnModel();
        for (int column = 0; column < cm.getColumnCount(); column++) {
            if (cm.getColumn(column) == aColumn) {
                return column;
            }
        }
        return -1;
    }
    //
    // Paint Methods and support
    //
    
    public void paint(Graphics g, JComponent c) {
        if (header.getColumnModel().getColumnCount() <= 0) {
            return;
        }
        boolean ltr = header.getComponentOrientation().isLeftToRight();
        
        Rectangle clip = g.getClipBounds();
        Point left = clip.getLocation();
        Point right = new Point( clip.x + clip.width - 1, clip.y );
        TableColumnModel cm = header.getColumnModel();
        int cMin = header.columnAtPoint( ltr ? left : right );
        int cMax = header.columnAtPoint( ltr ? right : left );
        // This should never happen.
        if (cMin == -1) {
            cMin =  0;
        }
        // If the table does not have enough columns to fill the view we'll get -1.
        // Replace this with the index of the last column.
        if (cMax == -1) {
            cMax = cm.getColumnCount()-1;
            Border cellBorder = UIManager.getBorder("TableHeader.cellBorder");
            if (cellBorder instanceof QuaquaTableHeaderBorder) {
                ((QuaquaTableHeaderBorder) cellBorder).setColumnIndex(-1);
            }
            if (cellBorder instanceof BackgroundBorder) {
                cellBorder = ((BackgroundBorder) cellBorder).getBackgroundBorder();
            }
            if (cellBorder != null) {
            cellBorder.paintBorder(header,g, cMax, 0, header.getWidth() - cMax, header.getHeight());
            }
        }
        
        TableColumn draggedColumn = header.getDraggedColumn();
        int columnWidth;
        int columnMargin = cm.getColumnMargin();
        Rectangle cellRect = header.getHeaderRect(cMin);
        TableColumn aColumn;
        if (ltr) {
            for(int column = cMin; column <= cMax ; column++) {
                aColumn = cm.getColumn(column);
                columnWidth = aColumn.getWidth();
                cellRect.width = columnWidth - columnMargin;
                if (aColumn != draggedColumn) {
                    paintCell(g, cellRect, column);
                }
                cellRect.x += columnWidth;
            }
        } else {
            aColumn = cm.getColumn(cMin);
            if (aColumn != draggedColumn) {
                columnWidth = aColumn.getWidth();
                cellRect.width = columnWidth - columnMargin;
                cellRect.x += columnMargin;
                paintCell(g, cellRect, cMin);
            }
            for(int column = cMin+1; column <= cMax; column++) {
                aColumn = cm.getColumn(column);
                columnWidth = aColumn.getWidth();
                cellRect.width = columnWidth - columnMargin;
                cellRect.x -= columnWidth;
                if (aColumn != draggedColumn) {
                    paintCell(g, cellRect, column);
                }
            }
        }
        
        // Paint the dragged column if we are dragging.
        if (draggedColumn != null) {
            int draggedColumnIndex = viewIndexForColumn(draggedColumn);
            Rectangle draggedCellRect = header.getHeaderRect(draggedColumnIndex);
            
            // Draw a gray well in place of the moving column.
            g.setColor(header.getParent().getBackground());
            g.fillRect(draggedCellRect.x, draggedCellRect.y,
            draggedCellRect.width, draggedCellRect.height);
            
            draggedCellRect.x += header.getDraggedDistance();
            
            // Fill the background.
            g.setColor(header.getBackground());
            g.fillRect(draggedCellRect.x, draggedCellRect.y,
            draggedCellRect.width, draggedCellRect.height);
            
            paintCell(g, draggedCellRect, draggedColumnIndex);
        }
        
        // Remove all components in the rendererPane.
        rendererPane.removeAll();
    }
    /**
     * Attaches listeners to the JTableHeader.
     */
    protected void installListeners() {
        columnModelListener = createColumnModelListener();
        
        header.getColumnModel().addColumnModelListener(columnModelListener);
        super.installListeners();
    }
    protected void uninstallListeners() {
        super.uninstallListeners();
        header.getColumnModel().removeColumnModelListener(columnModelListener);
        
        columnModelListener = null;
    }
    
    private TableColumnModelListener createColumnModelListener() {
        return new ColumnHandler();
    }
    
    private Component getHeaderRenderer(int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        if (renderer == null) {
            renderer = header.getDefaultRenderer();
        }
        return renderer.getTableCellRendererComponent(header.getTable(),
        aColumn.getHeaderValue(), false, false,
        -1, columnIndex);
    }
    
    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        Component component = getHeaderRenderer(columnIndex);
        
        if (component instanceof JComponent) {
            if (((JComponent) component).getBorder() instanceof QuaquaTableHeaderBorder) {
                QuaquaTableHeaderBorder thb = (QuaquaTableHeaderBorder) ((JComponent) component).getBorder();
                thb.setColumnIndex(columnIndex);
            }
        }
        
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
        cellRect.width+1, cellRect.height, true);
    }
    
    private class ColumnHandler implements TableColumnModelListener {
        private void updateViewport() {
            JTable table = header.getTable();
            Object property = (table == null) ? null : table.getClientProperty("Quaqua.Table.style");
            if (property != null && property.equals("striped")
            && table.getParent() instanceof JViewport) {
                JViewport viewport = (JViewport) table.getParent();
                if (viewport.getHeight() > table.getHeight()) {
                    viewport.repaint(0, table.getHeight(), viewport.getWidth(), viewport.getHeight() - table.getHeight());
                }
            }
        }
        
        public void columnAdded(TableColumnModelEvent e) {
            updateViewport();
        }
        
        public void columnMarginChanged(ChangeEvent e) {
            updateViewport();
        }
        
        public void columnMoved(TableColumnModelEvent e) {
            updateViewport();
        }
        
        public void columnRemoved(TableColumnModelEvent e) {
            updateViewport();
        }
        
        public void columnSelectionChanged(ListSelectionEvent e) {
            updateViewport();
        }
        
    }
    /**
     * Return the preferred size of the header. The preferred height is the 
     * maximum of the preferred heights of all of the components provided 
     * by the header renderers. The preferred width is the sum of the 
     * preferred widths of each column (plus inter-cell spacing).
     */
    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }
    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = header.getColumnModel();
        // None of the callers include the intercell spacing, do it here.
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int)width, getHeaderHeight());
    }
    private int getHeaderHeight() {
        int height = 0; 
        
        int emptyHeight = 0;
        
	boolean accomodatedDefault = false; 
        TableColumnModel columnModel = header.getColumnModel();
        for(int column = 0; column < columnModel.getColumnCount(); column++) { 
	    TableColumn aColumn = columnModel.getColumn(column); 
	    // Configuring the header renderer to calculate its preferred size is expensive. 
	    // Optimise this by assuming the default renderer always has the same height. 
	    if (aColumn.getHeaderRenderer() != null || !accomodatedDefault) { 
		Component comp = getHeaderRenderer(column); 
		int rendererHeight = comp.getPreferredSize().height; 
		height = Math.max(height, rendererHeight); 
                
                if (comp instanceof JComponent) {
                    Border b = ((JComponent) comp).getBorder();
                    if (b != null && (b instanceof UIResource)) {
                        Insets insets = b.getBorderInsets(comp);
                        emptyHeight = Math.max(emptyHeight, insets.top + insets.bottom);
                        }
                    }
                
                
		// If the header value is empty (== "") in the 
		// first column (and this column is set up 
		// to use the default renderer) we will 
		// return zero from this routine and the header 
		// will disappear altogether. Avoiding the calculation 
		// of the preferred size is such a performance win for 
		// most applications that we will continue to 
		// use this cheaper calculation, handling these 
		// issues as `edge cases'. 
		if (rendererHeight > emptyHeight) { 
		   accomodatedDefault = true; 
		}
	    }
        }
        // If all header cells are empty, we make the border disappear alltogether
        return (height <= emptyHeight) ? 0 : height;
    }
}
