/*
 * @(#)QuaquaTreeUI.java  2.1  2007-11-18
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
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.text.*;
/**
 * QuaquaTreeUI.
 *
 * XXX - Without copying a substantial amount of code from BasicTreeUI,
 * we can't implement the proper selection behavior for a JTree.
 *
 * @author  Werner Randelshofer
 * @version 2.1 2007-11-18 Paint tree icons differently if tree uses sideBar style.
 * <br>2.0 2007-11-10 Copied Handler and DragFixHandler from BasicTreeUI into
 * this class along with all the other code that got dragged in.
 * Support for client property "Quaqua.Tree.style"="sideBar" added.
 * <br>1.3 2007-08-04 Don't change selection, if a popup trigger
 * occurs on an already selected row. Don't change selection on mouse pressed
 * on a selected row. Change selection on mouse released on a selected row.
 * <br>1.2.4 2007-01-16 Focus border repainting factored out into
 * QuaquaViewportUI.
 * <br>1.2.3 2007-01-15 Change foreground color of cell renderer even if
 * it is not an UIResource.
 * <br>1.2.2 2007-01-11 Selection was not completely repainted on
 * focusLost/focusGained.
 * <br>1.2.1 2007-01-05 Issue #2: Tree stripes need to be drawn when tree
 * is empty. Issue #6: Selection needs to be drawn differently when tree hasn't
 * focus or is disabled or is on an inactive window.
 * <br>1.2 2005-09-28 List cells can now be selected on the whole cell
 * line. Inspired by 'FittsLawMouseListener' by Adam Walker.
 * http://www.walkersoftware.net
 * <br>1.1 2005-07-11 Support for striped style added.
 * <br>1.0  15 December 2004  Created.
 */
public class QuaquaTreeUI extends BasicTreeUI {
    static private final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    /** Last width the tree was at when painted. This is used when
     * !leftToRigth to notice the bounds have changed so that we can instruct
     * the TreeState to relayout. */
    private int                 lastWidth;
    
    /**
     * The time factor to treate the series of typed alphanumeric key
     * as prefix for first letter navigation.
     */
    private long timeFactor = 1000L;
    private Handler handler;
    /**
     * A temporary variable for communication between startEditingOnRelease
     * and startEditing.
     */
    private MouseEvent releaseEvent;
    
    /** If true, the property change event for LEAD_SELECTION_PATH_PROPERTY,
     * or ANCHOR_SELECTION_PATH_PROPERTY will not generate a repaint. */
    private boolean             ignoreLAChange;
    
    private boolean mouseReleaseDeselects;
    /** Row correspondin to lead path. */
    private int leadRow;
    private static DropTargetListener defaultDropTargetListener = null;
    
    /** Creates a new instance. */
    public QuaquaTreeUI() {
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaTreeUI();
    }
    
    protected void installDefaults() {
        super.installDefaults();
        
        // By default, we commit an edit when it is closed.
        tree.setInvokesStopCellEditing(true);
    }
    /**
     * Invoked after the <code>tree</code> instance variable has been
     * set, but before any defaults/listeners have been installed.
     */
    protected void prepareForUIInstall() {
        super.prepareForUIInstall();
        leadRow = -1;
    }
    
    protected void uninstallListeners() {
        super.uninstallListeners();
        
        handler = null;
    }
    
    private static class QuaquaTreeCellEditor extends DefaultTreeCellEditor implements UIResource {
        public QuaquaTreeCellEditor(JTree tree,
                DefaultTreeCellRenderer renderer) {
            super(tree, renderer);
        }
        // FIXME - We should explicitly turn the real editing component
        // opaque.
        protected Container createContainer() {
            return new DefaultTreeCellEditor.EditorContainer() {
                public void paint(Graphics gr) {
                    Graphics2D g = (Graphics2D) gr;
                    g.setColor(UIManager.getColor("TextField.background"));
                    Component[] c = getComponents();
                    for (int i=0; i < c.length; i++) {
                        g.fill(c[i].getBounds());
                    }
                    super.paint(g);
                }
            };
        }
    }
    private static class QuaquaTreeCellRenderer extends DefaultTreeCellRenderer implements UIResource {
    }
    
    /**
     * Creates a default cell editor.
     */
    protected TreeCellEditor createDefaultCellEditor() {
        if(currentCellRenderer != null &&
                (currentCellRenderer instanceof DefaultTreeCellRenderer)) {
            DefaultTreeCellEditor editor = new QuaquaTreeCellEditor(
                    tree, (DefaultTreeCellRenderer) currentCellRenderer
                    );
            return editor;
        }
        return new DefaultTreeCellEditor(tree, null);
    }
    /**
     * Returns the default cell renderer that is used to do the
     * stamping of each node.
     */
    protected TreeCellRenderer createDefaultCellRenderer() {
        return new QuaquaTreeCellRenderer();
    }
    
    /**
     * Creates the listener reponsible for getting key events from
     * the tree.
     */
    protected KeyListener createKeyListener() {
        return getHandler();
    }
    
    /**
     * Creates the listener responsible for getting property change
     * events from the selection model.
     */
    protected PropertyChangeListener createSelectionModelPropertyChangeListener() {
        return getHandler();
    }
    
    /**
     * Creates the listener that updates the display based on selection change
     * methods.
     */
    protected TreeSelectionListener createTreeSelectionListener() {
        return getHandler();
    }
    
    /**
     * Creates a listener to handle events from the current editor.
     */
    protected CellEditorListener createCellEditorListener() {
        return getHandler();
    }
    
    /**
     * Creates and returns the object responsible for updating the treestate
     * when nodes expanded state changes.
     */
    protected TreeExpansionListener createTreeExpansionListener() {
        return getHandler();
    }
    /**
     * Creates a listener that is responsible that updates the UI based on
     * how the tree changes.
     */
    protected PropertyChangeListener createPropertyChangeListener() {
        return getHandler();
    }
    
    private Handler getHandler() {
        if (handler == null) {
            //handler = DRAG_FIX ? new DragFixHandler() : new Handler();
            handler = new DragFixHandler();
        }
        return handler;
    }
    /**
     * Returning true signifies a mouse event on the node should toggle
     * the selection of only the row under mouse.
     */
    protected boolean isToggleSelectionEvent(MouseEvent event) {
        return event.getID() == MouseEvent.MOUSE_PRESSED &&
                SwingUtilities.isLeftMouseButton(event) &&
                event.isMetaDown();
    }
    protected boolean isToggleEvent(MouseEvent event) {
        if(event.getID() != MouseEvent.MOUSE_PRESSED ||
                !SwingUtilities.isLeftMouseButton(event)) {
            return false;
        }
        int           clickCount = tree.getToggleClickCount();
        
        if(clickCount <= 0) {
            return false;
        }
        return ((event.getClickCount() % clickCount) == 0);
    }
    
    /**
     * Returning true signifies a mouse event on the node should select
     * from the anchor point.
     */
    protected boolean isMultiSelectEvent(MouseEvent event) {
        return (SwingUtilities.isLeftMouseButton(event) &&
                event.isShiftDown());
    }
    
    //
    // The following selection methods (lead/anchor) are covers for the
    // methods in JTree.
    //
    private void setAnchorSelectionPath(TreePath newPath) {
        ignoreLAChange = true;
        try {
            tree.setAnchorSelectionPath(newPath);
        } finally{
            ignoreLAChange = false;
        }
    }
    
    private TreePath getAnchorSelectionPath() {
        return tree.getAnchorSelectionPath();
    }
    
    private void setLeadSelectionPath(TreePath newPath, boolean repaint) {
        Rectangle       bounds = repaint ?
            getPathBounds(tree, getLeadSelectionPath()) : null;
        
        ignoreLAChange = true;
        try {
            tree.setLeadSelectionPath(newPath);
        } finally {
            ignoreLAChange = false;
        }
        leadRow = getRowForPath(tree, newPath);
        
        if(repaint) {
            if(bounds != null)
                tree.repaint(bounds);
            bounds = getPathBounds(tree, newPath);
            if(bounds != null)
                tree.repaint(bounds);
        }
    }
    private TreePath getLeadSelectionPath() {
        return tree.getLeadSelectionPath();
    }
    private void setLeadSelectionPath(TreePath newPath) {
        setLeadSelectionPath(newPath, false);
    }
    
    private void updateLeadRow() {
        leadRow = getRowForPath(tree, getLeadSelectionPath());
    }
    private int getLeadSelectionRow() {
        return leadRow;
    }
    /*
    private int getLeadSelectionRow() {
        TreePath leadPath = tree.getLeadSelectionPath();
        return (leadPath == null) ? -1 : getRowForPath(tree, leadPath);
     
    }*/
    /**
     * Invokes <code>repaint</code> on the JTree for the passed in TreePath,
     * <code>path</code>.
     */
    private void repaintPath(TreePath path) {
        if (path != null) {
            Rectangle bounds = getPathBounds(tree, path);
            if (bounds != null) {
                tree.repaint(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }
    
    /**
     * Paints the expand (toggle) part of a row. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.
     */
    protected void paintExpandControl(Graphics g,
            Rectangle clipBounds, Insets insets,
            Rectangle bounds, TreePath path,
            int row, boolean isExpanded,
            boolean hasBeenExpanded,
            boolean isLeaf) {
        Object       value = path.getLastPathComponent();
        
        // Draw icons if not a leaf and either hasn't been loaded,
        // or the model child count is > 0.
        if (!isLeaf && (!hasBeenExpanded ||
                treeModel.getChildCount(value) > 0)) {
            int middleXOfKnob;
            if (QuaquaUtilities.isLeftToRight(tree)) {
                middleXOfKnob = bounds.x - (getRightChildIndent() - 1);
            } else {
                middleXOfKnob = bounds.x + bounds.width + getRightChildIndent();
            }
            int middleYOfKnob = bounds.y + (bounds.height / 2);
            
            Icon treeIcon = getTreeIcon(isExpanded, tree.isRowSelected(row));
            if(treeIcon != null)
                drawCentered(tree, g, treeIcon, middleXOfKnob,
                        middleYOfKnob );
        }
    }
    
    private Icon getTreeIcon(boolean isExpanded, boolean isSelected) {
        Icon[] icons = (Icon[]) UIManager.get("Tree.icons");
        if (icons == null) {
            return UIManager.getIcon((isExpanded) ? "Tree.expandedIcon" : "Tree.collapsedIcon");
        } else {
            Object property = tree.getClientProperty("Quaqua.Tree.style");
            boolean isSideBar = property != null && property.equals("sideBar");
            
            int index = (isExpanded) ? 3 : 0;
            if (! isSideBar && ! QuaquaUtilities.isOnActiveWindow(tree)) {
                index += 2;
            } else {
                if (isSelected && QuaquaUtilities.isFocused(tree)) {
                    index++;
                }
            }
            return icons[index];
        }
    }
    
    /**
     * Returns the location, along the x-axis, to render a particular row
     * at. The return value does not include any Insets specified on the JTree.
     * This does not check for the validity of the row or depth, it is assumed
     * to be correct and will not throw an Exception if the row or depth
     * doesn't match that of the tree.
     *
     * @param row Row to return x location for
     * @param depth Depth of the row
     * @return amount to indent the given row.
     * @since 1.5
     */
    protected int getRowX(int row, int depth) {
        Object property = tree.getClientProperty("Quaqua.Tree.style");
        boolean isSideBar = property != null && property.equals("sideBar");
        
        if (isSideBar) {
            return totalChildIndent * (Math.max(1, depth - 2) + depthOffset);
        } else {
            return totalChildIndent * (depth + depthOffset);
        }
    }
    
    
    // cover method for startEditing that allows us to pass extra
    // information into that method via a class variable
    private boolean startEditingOnRelease(TreePath path,
            MouseEvent event,
            MouseEvent releaseEvent) {
        this.releaseEvent = releaseEvent;
        try {
            return startEditing(path, event);
        } finally {
            this.releaseEvent = null;
        }
    }
    InputMap getInputMap(int condition) {
        if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
            return (InputMap)UIManager.get("Tree.ancestorInputMap");
        } else if (condition == JComponent.WHEN_FOCUSED) {
            InputMap keyMap = (InputMap)UIManager.get("Tree.focusInputMap");
            InputMap rtlKeyMap;
            
            if (tree.getComponentOrientation().isLeftToRight() ||
                    ((rtlKeyMap = (InputMap)UIManager.get(
                    "Tree.focusInputMap.RightToLeft")) == null)) {
                return keyMap;
            } else {
                rtlKeyMap.setParent(keyMap);
                return rtlKeyMap;
            }
        }
        return null;
    }
    
    /**
     * Messaged to update the selection based on a MouseEvent over a
     * particular row. If the event is a toggle selection event, the
     * row is either selected, or deselected. If the event identifies
     * a multi selection event, the selection is updated from the
     * anchor point. Otherwise the row is selected, and if the event
     * specified a toggle event the row is expanded/collapsed.
     */
    protected void selectPathForEvent(TreePath path, MouseEvent event) {
        if (event.isPopupTrigger()) {
            /*
            // I would like to set the lead selection path upon a
            // popup trigger, but since this does not work with
            // lists and tables as well, I don't do it for trees too.
            // Because this would make the user interface inconistent.
               tree.setLeadSelectionPath(path);
             */
            
            // If the popup trigger ocurred on a row, which is not
            // selected yet, select it.
            if (event.getID() == MouseEvent.MOUSE_PRESSED) {
                if (! tree.isPathSelected(path) &&
                        0 == (event.getModifiers() & (MouseEvent.ALT_MASK | MouseEvent.SHIFT_MASK | MouseEvent.META_MASK))) {
                    tree.setSelectionPath(path);
                }
            }
            
        } else {
            // If the left mouse button is pressed over an
            // already selected row, do nothing.
            if (event.getID() == MouseEvent.MOUSE_PRESSED) {
                if (tree.isPathSelected(path) &&
                        0 == (event.getModifiers() & (MouseEvent.ALT_MASK | MouseEvent.SHIFT_MASK | MouseEvent.META_MASK))) {
                    return;
                }
                // If the left mouse button is released over an
                // already selected row, only select this row.
            } else if (event.getID() == MouseEvent.MOUSE_RELEASED) {
                if (tree.isPathSelected(path) &&
                        0 == (event.getModifiers() & (MouseEvent.ALT_MASK | MouseEvent.SHIFT_MASK | MouseEvent.META_MASK))) {
                    tree.setSelectionPath(path);
                    return;
                }
            }
            
            super.selectPathForEvent(path, event);
        }
    }
    
    /**
     * Creates the focus listener for handling keyboard navigation in the JTable.
     */
    protected FocusListener createFocusListener() {
        return new FocusHandler();
    }
    protected MouseListener createMouseListener() {
        return getHandler();
    }
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicTableUI.
     */
    public class FocusHandler extends BasicTreeUI.FocusHandler {
        
        public void focusGained(FocusEvent event) {
            if(tree != null) {
                Rectangle                 pBounds = null;
                
                TreePath[] selectionPaths = tree.getSelectionPaths();
                if (selectionPaths != null) {
                    for (int i=0; i < selectionPaths.length; i++) {
                        if (i == 0) {
                            pBounds = getPathBounds(tree, selectionPaths[i]);
                        } else {
                            pBounds.add(getPathBounds(tree, selectionPaths[i]));
                        }
                    }
                    if(pBounds != null) {
                        tree.repaint(0, pBounds.y, tree.getWidth(), pBounds.height);
                    }
                }
            }
        }
        
        public void focusLost(FocusEvent event) {
            focusGained(event);
        }
    }
    /**
     * TreeMouseListener is responsible for updating the selection
     * based on mouse events.
     */
    public class MouseHandler extends MouseAdapter implements MouseMotionListener {
        /**
         * Invoked when a mouse button has been pressed on a component.
         */
        public void mousePressed(MouseEvent e) {
            if (! e.isConsumed()) {
                handleSelection(e);
                selectedOnPress = 0 != (e.getModifiers() & (MouseEvent.ALT_MASK | MouseEvent.SHIFT_MASK | MouseEvent.META_MASK));
            } else {
                selectedOnPress = false;
            }
        }
        
        void handleSelection(MouseEvent e) {
            if(tree != null && tree.isEnabled()) {
                if (isEditing(tree) && tree.getInvokesStopCellEditing() &&
                        !stopEditing(tree)) {
                    return;
                }
                
                if (tree.isRequestFocusEnabled()) {
                    tree.requestFocus();
                }
                TreePath     path = getClosestPathForLocation(tree, e.getX(),
                        e.getY());
                
                if (path != null) {
                    Rectangle       bounds = getPathBounds(tree, path);
                    
                    if(e.getY() > (bounds.y + bounds.height)) {
                        return;
                    }
                    
                    // Preferably checkForClickInExpandControl could take
                    // the Event to do this it self!
                    if(SwingUtilities.isLeftMouseButton(e) && e.getID() == MouseEvent.MOUSE_PRESSED) {
                        checkForClickInExpandControl(path, e.getX(), e.getY());
                    }
                    
                    // Perhaps they clicked the cell itself. If so,
                    // select it.
                    if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                        int x = e.getX();
                        if (x > bounds.x) {
                            if (x <= (bounds.x + bounds.width) &&
                                    !startEditing(path, e)) {
                            }
                            selectedOnPress = ! tree.isPathSelected(path);
                            selectPathForEvent(path, e);
                        }
                    }
                    // PENDING: Should select on mouse down, start a drag if
                    // the mouse moves, and fire selection change notice on
                    // mouse up. That is, the explorer highlights on mouse
                    // down, but doesn't update the pane to the right (and
                    // open the folder icon) until mouse up.
                }
            }
        }
        
        public void mouseDragged(MouseEvent e) {
            // If the user dragged the mouse, he either has selected on press,
            // or does not want to deselect on mouse release.
            selectedOnPress = true;
        }
        
        /**
         * Invoked when the mouse button has been moved on a component
         * (with no buttons no down).
         */
        public void mouseMoved(MouseEvent e) {
        }
        
        public void mouseReleased(MouseEvent e) {
            if (! e.isConsumed() && ! isEditing(tree)) {
                if (selectedOnPress) {
                    
                } else {
                    handleSelection(e);
                }
            }
        }
        
        boolean selectedOnPress;
    } // End of QuaquaTreeUI.MouseHandler
//
// Painting routines.
//
    
    public void paint(Graphics gr, JComponent c) {
        if (tree != c) {
            throw new InternalError("incorrect component");
        }
        Graphics2D g = (Graphics2D) gr;
        Object property = tree.getClientProperty("Quaqua.Tree.style");
        boolean isStriped = property != null && property.equals("striped");
        boolean isSideBar = property != null && property.equals("sideBar");
        Color[] stripes = { UIManager.getColor("Tree.alternateBackground.0"), UIManager.getColor("Tree.alternateBackground.1")};
        boolean isEnabled = c.isEnabled();
        boolean isFocused = QuaquaUtilities.isFocused(c);
        boolean isActive = QuaquaUtilities.isOnActiveWindow(c);
        Color selectionBackground = UIManager.getColor("Tree.selectionBackground");
        Color selectionForeground = UIManager.getColor("Tree.selectionForeground");
        if (selectionBackground instanceof MutableColorUIResource) {
            ((MutableColorUIResource) selectionBackground).setColor(
                    UIManager.getColor((isFocused) ? "Tree.activeSelectionBackground" : "Tree.inactiveSelectionBackground")
                    );
        }
        if (selectionForeground instanceof MutableColorUIResource) {
            ((MutableColorUIResource) selectionForeground).setColor(
                    UIManager.getColor((isFocused) ? "Tree.activeSelectionForeground" : "Tree.inactiveSelectionForeground")
                    );
        }
        
        // Should never happen if installed for a UI
        if(treeState == null) {
            return;
        }
        boolean leftToRight = QuaquaUtilities.isLeftToRight(tree);
        // Update the lastWidth if necessary.
        // This should really come from a ComponentListener installed on
        // the JTree, but for the time being it is here.
        int width = tree.getWidth();
        int height = tree.getHeight();
        
        if (width != lastWidth) {
            lastWidth = width;
            if (! leftToRight) {
                // For RTL when the size changes, we have to refresh the
                // cache as the X position is based off the width.
                redoTheLayout();
                updateSize();
            }
        }
        
        Rectangle        paintBounds = g.getClipBounds();
        Insets           insets = tree.getInsets();
        
        if(insets == null) {
            insets = EMPTY_INSETS;
        }
        TreePath         initialPath = getClosestPathForLocation(tree, 0, paintBounds.y);
        Enumeration      paintingEnumerator = treeState.getVisiblePathsFrom(initialPath);
        int              row = treeState.getRowForPath(initialPath);
        int              endY = paintBounds.y + paintBounds.height;
        
        drawingCache.clear();
        
        Color background;
        Border selectionBorder;
        if (isSideBar) {
            background = UIManager.getColor("Tree.background.sideBar");
            selectionBorder = UIManager.getBorder("Tree.selectionBorder.sideBar");
            if (selectionBackground instanceof MutableColorUIResource) {
                ((MutableColorUIResource) selectionBackground).setColor(UIManager.getColor("Tree.selectionBackground.sideBar"));
            }
            if (selectionForeground instanceof MutableColorUIResource) {
                ((MutableColorUIResource) selectionForeground).setColor(UIManager.getColor("Tree.selectionForeground.sideBar"));
            }
        } else {
            background = tree.getBackground();
            selectionBorder = null;
        }
        if (tree.isOpaque()) {
            if (background instanceof InactivatableColorUIResource) {
                ((InactivatableColorUIResource) background).setActive(isActive);
            }
            g.setColor(background);
            g.fillRect(0, 0, width, height);
        }
        
        
        if(initialPath != null && paintingEnumerator != null) {
            TreePath   parentPath = initialPath;
            boolean         hasBeenExpanded;
            boolean         done = false;
            boolean         isLeaf;
            boolean         isExpanded;
            TreePath        path;
            Rectangle       bounds = null;
            Rectangle       boundsBuffer = new Rectangle();
            boolean         rootVisible = isRootVisible();
            int             rwidth = width - insets.left - insets.left;
            
            // Draw the alternating row colors
            //*****
            if (isStriped) {
                while(!done && paintingEnumerator.hasMoreElements()) {
                    path = (TreePath)paintingEnumerator.nextElement();
                    if(path != null) {
                        isLeaf = treeModel.isLeaf(path.getLastPathComponent());
                        if(isLeaf)
                            isExpanded = hasBeenExpanded = false;
                        else {
                            isExpanded = treeState.getExpandedState(path);
                            hasBeenExpanded = tree.hasBeenExpanded(path);
                        }
                        bounds = treeState.getBounds(path, boundsBuffer);
                        if (bounds == null) {
                            // This will only happen if the model changes out
                            // from under us (usually in another thread).
                            // Swing isn't multithreaded, but I'll put this
                            // check in anyway.
                            return;
                        }
                        
                        bounds.x += insets.left;
                        bounds.y += insets.top;
                        
                        if (tree.isRowSelected(row) && ! tree.isEditing()) {
                            if (selectionBorder == null) {
                                g.setColor(selectionBackground);
                                g.fillRect(insets.left, bounds.y, rwidth, bounds.height);
                            } else {
                                selectionBorder.paintBorder(tree, g, insets.left, bounds.y, rwidth, bounds.height);
                            }
                        } else {
                            g.setColor(stripes[row % 2]);
                            g.fillRect(insets.left, bounds.y, rwidth, bounds.height);
                        }
                        if((bounds.y + bounds.height) >= endY) {
                            done = true;
                        }
                    } else {
                        done = true;
                    }
                    row++;
                }
                
                int rheight = tree.getRowHeight();
                if (rheight <= 0) {
                    // FIXME - Use the cell renderer to determine the height
                    rheight = tree.getFont().getSize() + 4;
                }
                int startY = (bounds != null) ? bounds.y + bounds.height : 0;
                
                for (int y=startY; y < height; y += rheight) {
                    g.setColor(stripes[row % 2]);
                    g.fillRect(insets.left, y, rwidth, rheight);
                    row++;
                }
            } else {
                g.setColor(selectionBackground);
                while(!done && paintingEnumerator.hasMoreElements()) {
                    path = (TreePath)paintingEnumerator.nextElement();
                    if(path != null) {
                        isLeaf = treeModel.isLeaf(path.getLastPathComponent());
                        if(isLeaf)
                            isExpanded = hasBeenExpanded = false;
                        else {
                            isExpanded = treeState.getExpandedState(path);
                            hasBeenExpanded = tree.hasBeenExpanded(path);
                        }
                        bounds = treeState.getBounds(path, boundsBuffer);
                        if (bounds == null) {
                            // This will only happen if the model changes out
                            // from under us (usually in another thread).
                            // Swing isn't multithreaded, but I'll put this
                            // check in anyway.
                            return;
                        }
                        
                        bounds.x += insets.left;
                        bounds.y += insets.top;
                        
                        if (tree.isRowSelected(row) && ! tree.isEditing()) {
                            if (selectionBorder == null) {
                                g.fillRect(insets.left, bounds.y, rwidth, bounds.height);
                            } else {
                                selectionBorder.paintBorder(tree, g, insets.left, bounds.y, rwidth, bounds.height);
                            }
                        }
                        if((bounds.y + bounds.height) >= endY) {
                            done = true;
                        }
                    } else {
                        done = true;
                    }
                    row++;
                }
            }
            //********
            paintingEnumerator = treeState.getVisiblePathsFrom(initialPath);
            row = treeState.getRowForPath(initialPath);
            // Draw the lines, knobs, and rows
            // Find each parent and have them draw a line to their last child
            parentPath = parentPath.getParentPath();
            while(parentPath != null) {
                paintVerticalPartOfLeg(g, paintBounds, insets, parentPath);
                drawingCache.put(parentPath, Boolean.TRUE);
                parentPath = parentPath.getParentPath();
            }
            
            // Information for the node being rendered.
            done = false;
            
            while(!done && paintingEnumerator.hasMoreElements()) {
                path = (TreePath)paintingEnumerator.nextElement();
                if(path != null) {
                    isLeaf = treeModel.isLeaf(path.getLastPathComponent());
                    if(isLeaf)
                        isExpanded = hasBeenExpanded = false;
                    else {
                        isExpanded = treeState.getExpandedState(path);
                        hasBeenExpanded = tree.hasBeenExpanded(path);
                    }
                    bounds = treeState.getBounds(path, boundsBuffer);
                    if(bounds == null)
                        // This will only happen if the model changes out
                        // from under us (usually in another thread).
                        // Swing isn't multithreaded, but I'll put this
                        // check in anyway.
                        return;
                    
                    bounds.x += insets.left;
                    bounds.y += insets.top;
                    
                    // See if the vertical line to the parent has been drawn.
                    parentPath = path.getParentPath();
                    if(parentPath != null) {
                        if(drawingCache.get(parentPath) == null) {
                            paintVerticalPartOfLeg(g, paintBounds,
                                    insets, parentPath);
                            drawingCache.put(parentPath, Boolean.TRUE);
                        }
                        paintHorizontalPartOfLeg(g, paintBounds, insets,
                                bounds, path, row,
                                isExpanded,
                                hasBeenExpanded, isLeaf);
                    } else if(rootVisible && row == 0) {
                        paintHorizontalPartOfLeg(g, paintBounds, insets,
                                bounds, path, row,
                                isExpanded,
                                hasBeenExpanded, isLeaf);
                    }
                    if(shouldPaintExpandControl(path, row, isExpanded,
                            hasBeenExpanded, isLeaf)) {
                        paintExpandControl(g, paintBounds, insets, bounds,
                                path, row, isExpanded,
                                hasBeenExpanded, isLeaf);
                    }
                    //This is the quick fix for bug 4259260.  Somewhere we
                    //are out by 4 pixels in the RTL layout.  Its probably
                    //due to built in right-side padding in some icons.  Rather
                    //than ferret out problem at the source, this compensates.
                    if (!leftToRight) {
                        bounds.x +=4;
                    }
                    paintRow(g, paintBounds, insets, bounds, path,
                            row, isExpanded, hasBeenExpanded, isLeaf, isEnabled, isFocused);
                    if((bounds.y + bounds.height) >= endY)
                        done = true;
                } else {
                    done = true;
                }
                row++;
            }
        } else {
            if (isStriped) {
                // Draw stripes on empty tree
                int rwidth = width - insets.left - insets.left;
                int rheight = tree.getRowHeight();
                if (rheight <= 0) {
                    // FIXME - Use the cell renderer to determine the height
                    rheight = tree.getFont().getSize() + 4;
                }
                row = 0;
                for (int y=0; y < height; y += rheight) {
                    g.setColor(stripes[row % 2]);
                    g.fillRect(insets.left, y, rwidth, rheight);
                    row++;
                }
            }
        }
        
        // Empty out the renderer pane, allowing renderers to be gc'ed.
        rendererPane.removeAll();
        if (selectionBackground instanceof MutableColorUIResource) {
            ((MutableColorUIResource) selectionBackground).setColor(
                    UIManager.getColor("Tree.activeSelectionBackground")
                    );
        }
        if (selectionForeground instanceof MutableColorUIResource) {
            ((MutableColorUIResource) selectionForeground).setColor(
                    UIManager.getColor("Tree.activeSelectionForeground")
                    );
        }
    }
    /**
     * Recomputes the right margin, and invalidates any tree states
     */
    private void redoTheLayout() {
        if (treeState != null) {
            treeState.invalidateSizes();
        }
    }
    /**
     * Paints the vertical part of the leg. The receiver should
     * NOT modify <code>clipBounds</code>, <code>insets</code>.<p>
     */
    protected void paintVerticalPartOfLeg(Graphics g, Rectangle clipBounds,
            Insets insets, TreePath path) {
        /* Never draw lines
        if (QuaquaManager.getBoolean("Tree.paintLines")) {
            super.paintVerticalPartOfLeg(g, clipBounds, insets, path);
        }
         */
    }
    /**
     * Paints the horizontal part of the leg. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.<p>
     * NOTE: <code>parentRow</code> can be -1 if the root is not visible.
     */
    protected void paintHorizontalPartOfLeg(Graphics g, Rectangle clipBounds,
            Insets insets, Rectangle bounds,
            TreePath path, int row,
            boolean isExpanded,
            boolean hasBeenExpanded, boolean
            isLeaf) {
        /* Never draw lines
        if (QuaquaManager.getBoolean("Tree.paintLines")) {
            super.paintHorizontalPartOfLeg(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
        }
         */
    }
    /**
     * Paints a vertical line.
     */
    protected void paintVerticalLine(Graphics g, JComponent c, int x, int top,
            int bottom) {
        /*
        if (QuaquaManager.getBoolean("Tree.paintLines")) {
            super.paintVerticalLine(g, c, x, top, bottom);
        }*/
    }
    
    /**
     * Paints a horizontal line.
     */
    protected void paintHorizontalLine(Graphics g, JComponent c, int y,
            int left, int right) {
        /*
        if (QuaquaManager.getBoolean("Tree.paintLines")) {
            super.paintHorizontalLine(g, c, y, left, right);
        }*/
    }
    
    
    /**
     * Paints the renderer part of a row. The receiver should
     * NOT modify <code>clipBounds</code>, or <code>insets</code>.
     */
    protected void paintRow(Graphics g, Rectangle clipBounds,
            Insets insets, Rectangle bounds, TreePath path,
            int row, boolean isExpanded,
            boolean hasBeenExpanded, boolean isLeaf, boolean isEnabled, boolean isFocused) {
        // Don't paint the renderer if editing this row.
        if (editingComponent != null && editingRow == row) {
            return;
        }
        
        int leadIndex;
        
        if (tree.hasFocus()) {
            leadIndex = getLeadSelectionRow();
        } else {
            leadIndex = -1;
        }
        
        Component component;
        
        boolean isRowSelected = tree.isRowSelected(row);
        
        component = currentCellRenderer.getTreeCellRendererComponent
                (tree, path.getLastPathComponent(),
                isRowSelected, isExpanded, isLeaf, row,
                (leadIndex == row));
        
        rendererPane.paintComponent(g, component, tree, bounds.x, bounds.y,
                bounds.width, bounds.height, true);
    }
    
    private class Handler implements CellEditorListener, FocusListener,
            KeyListener, MouseListener, PropertyChangeListener,
            TreeExpansionListener, TreeModelListener,
            TreeSelectionListener {
        //
        // KeyListener
        //
        private String prefix = "";
        private String typedString = "";
        private long lastTime = 0L;
        
        /**
         * Invoked when a key has been typed.
         *
         * Moves the keyboard focus to the first element whose prefix matches the
         * sequence of alphanumeric keys pressed by the user with delay less
         * than value of <code>timeFactor</code> property (or 1000 milliseconds
         * if it is not defined). Subsequent same key presses move the keyboard
         * focus to the next object that starts with the same letter until another
         * key is pressed, then it is treated as the prefix with appropriate number
         * of the same letters followed by first typed another letter.
         */
        public void keyTyped(KeyEvent e) {
            // handle first letter navigation
            if(tree != null && tree.getRowCount()>0 && tree.hasFocus() &&
                    tree.isEnabled()) {
                if (e.isAltDown() || e.isControlDown() || e.isMetaDown() ||
                        isNavigationKey(e)) {
                    return;
                }
                boolean startingFromSelection = true;
                
                char c = e.getKeyChar();
                
                long time = e.getWhen();
                int startingRow = tree.getLeadSelectionRow();
                if (time - lastTime < timeFactor) {
                    typedString += c;
                    if((prefix.length() == 1) && (c == prefix.charAt(0))) {
                        // Subsequent same key presses move the keyboard focus to the next
                        // object that starts with the same letter.
                        startingRow++;
                    } else {
                        prefix = typedString;
                    }
                } else {
                    startingRow++;
                    typedString = "" + c;
                    prefix = typedString;
                }
                lastTime = time;
                
                if (startingRow < 0 || startingRow >= tree.getRowCount()) {
                    startingFromSelection = false;
                    startingRow = 0;
                }
                TreePath path = tree.getNextMatch(prefix, startingRow,
                        Position.Bias.Forward);
                if (path != null) {
                    tree.setSelectionPath(path);
                    int row = getRowForPath(tree, path);
                    ensureRowsAreVisible(row, row);
                } else if (startingFromSelection) {
                    path = tree.getNextMatch(prefix, 0,
                            Position.Bias.Forward);
                    if (path != null) {
                        tree.setSelectionPath(path);
                        int row = getRowForPath(tree, path);
                        ensureRowsAreVisible(row, row);
                    }
                }
            }
        }
        
        /**
         * Invoked when a key has been pressed.
         *
         * Checks to see if the key event is a navigation key to prevent
         * dispatching these keys for the first letter navigation.
         */
        public void keyPressed(KeyEvent e) {
            if ( isNavigationKey(e) ) {
                prefix = "";
                typedString = "";
                lastTime = 0L;
            }
        }
        
        public void keyReleased(KeyEvent e) {
        }
        
        /**
         * Returns whether or not the supplied key event maps to a key that is used for
         * navigation.  This is used for optimizing key input by only passing non-
         * navigation keys to the first letter navigation mechanism.
         */
        private boolean isNavigationKey(KeyEvent event) {
            InputMap inputMap = tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke key = KeyStroke.getKeyStrokeForEvent(event);
            
            if (inputMap != null && inputMap.get(key) != null) {
                return true;
            }
            return false;
        }
        
        
        //
        // PropertyChangeListener
        //
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getSource() == treeSelectionModel) {
                treeSelectionModel.resetRowSelection();
            } else if(event.getSource() == tree) {
                String changeName = event.getPropertyName();
                
                if (changeName == "Frame.active") {
                    if (tree.getClientProperty("Quaqua.Tree.style") != null &&
                            tree.getClientProperty("Quaqua.Tree.style").equals("sideBar")) {
                        tree.repaint();
                    }
                } else if (changeName == JTree.LEAD_SELECTION_PATH_PROPERTY) {
                    if (!ignoreLAChange) {
                        updateLeadRow();
                        repaintPath((TreePath)event.getOldValue());
                        repaintPath((TreePath)event.getNewValue());
                    }
                } else if (changeName == JTree.ANCHOR_SELECTION_PATH_PROPERTY) {
                    if (!ignoreLAChange) {
                        repaintPath((TreePath)event.getOldValue());
                        repaintPath((TreePath)event.getNewValue());
                    }
                }
                if(changeName == JTree.CELL_RENDERER_PROPERTY) {
                    setCellRenderer((TreeCellRenderer)event.getNewValue());
                    redoTheLayout();
                } else if(changeName == JTree.TREE_MODEL_PROPERTY) {
                    setModel((TreeModel)event.getNewValue());
                } else if(changeName == JTree.ROOT_VISIBLE_PROPERTY) {
                    setRootVisible(((Boolean)event.getNewValue()).
                            booleanValue());
                } else if(changeName == JTree.SHOWS_ROOT_HANDLES_PROPERTY) {
                    setShowsRootHandles(((Boolean)event.getNewValue()).
                            booleanValue());
                } else if(changeName == JTree.ROW_HEIGHT_PROPERTY) {
                    setRowHeight(((Integer)event.getNewValue()).
                            intValue());
                } else if(changeName == JTree.CELL_EDITOR_PROPERTY) {
                    setCellEditor((TreeCellEditor)event.getNewValue());
                } else if(changeName == JTree.EDITABLE_PROPERTY) {
                    setEditable(((Boolean)event.getNewValue()).booleanValue());
                } else if(changeName == JTree.LARGE_MODEL_PROPERTY) {
                    setLargeModel(tree.isLargeModel());
                } else if(changeName == JTree.SELECTION_MODEL_PROPERTY) {
                    setSelectionModel(tree.getSelectionModel());
                } else if(changeName == "font") {
                    completeEditing();
                    if(treeState != null)
                        treeState.invalidateSizes();
                    updateSize();
                } else if (changeName == "componentOrientation") {
                    if (tree != null) {
                        //leftToRight = QuaquaUtilities.isLeftToRight(tree);
                        redoTheLayout();
                        tree.treeDidChange();
                        
                        InputMap km = getInputMap(JComponent.WHEN_FOCUSED);
                        SwingUtilities.replaceUIInputMap(tree,
                                JComponent.WHEN_FOCUSED, km);
                    }
                } else if ("transferHandler" == changeName) {
                    DropTarget dropTarget = tree.getDropTarget();
                    if (dropTarget instanceof UIResource) {
                        if (defaultDropTargetListener == null) {
                            defaultDropTargetListener = new TreeDropTargetListener();
                        }
                        try {
                            dropTarget.addDropTargetListener(defaultDropTargetListener);
                        } catch (TooManyListenersException tmle) {
                            // should not happen... swing drop target is multicast
                        }
                    }
                }
            }
        }
        
        //
        // MouseListener
        //
        private boolean selectedOnPress;
        
        public void mouseClicked(MouseEvent e) {
        }
        
        public void mouseEntered(MouseEvent e) {
        }
        
        public void mouseExited(MouseEvent e) {
        }
        
        /**
         * Invoked when a mouse button has been pressed on a component.
         */
        public void mousePressed(MouseEvent e) {
            if (! e.isConsumed()) {
                handleSelection(e);
                selectedOnPress = true;
            } else {
                selectedOnPress = false;
            }
        }
        
        void handleSelection(MouseEvent e) {
            if(tree != null && tree.isEnabled()) {
                if (isEditing(tree) && tree.getInvokesStopCellEditing() &&
                        !stopEditing(tree)) {
                    return;
                }
                
                QuaquaUtilities.adjustFocus(tree);
                
                TreePath     path = getClosestPathForLocation(tree, e.getX(),
                        e.getY());
                
                handleSelectionImpl(e, path);
            }
        }
        
        protected void handleSelectionImpl(MouseEvent e, TreePath path) {
            if(path != null) {
                Rectangle       bounds = getPathBounds(tree, path);
                
                int x = e.getX();
                int y = e.getY();
                
                if(y > (bounds.y + bounds.height)) {
                    return;
                }
                
                // Preferably checkForClickInExpandControl could take
                // the Event to do this it self!
                if(SwingUtilities.isLeftMouseButton(e))
                    checkForClickInExpandControl(path, x, y);
                
                // Perhaps they clicked the cell itself. If so,
                // select it.
                //if (x > bounds.x && x <= (bounds.x + bounds.width)) {
                if (! isLocationInExpandControl(path, x, y)) {
                    
                    if ((/*DRAG_FIX && */tree.getDragEnabled())
                    || !startEditing(path, e)) {
                        
                        selectPathForEvent(path, e);
                    }
                }
            }
        }
        
        public void mouseDragged(MouseEvent e) {
        }
        
        /**
         * Invoked when the mouse button has been moved on a component
         * (with no buttons no down).
         */
        public void mouseMoved(MouseEvent e) {
        }
        
        public void mouseReleased(MouseEvent e) {
            if ((! e.isConsumed()) && (! selectedOnPress)) {
                handleSelection(e);
            }
        }
        
        //
        // FocusListener
        //
        public void focusGained(FocusEvent e) {
            if(tree != null) {
                Rectangle                 pBounds;
                
                pBounds = getPathBounds(tree, tree.getLeadSelectionPath());
                if(pBounds != null)
                    tree.repaint(pBounds);
                pBounds = getPathBounds(tree, getLeadSelectionPath());
                if(pBounds != null)
                    tree.repaint(pBounds);
            }
        }
        
        public void focusLost(FocusEvent e) {
            focusGained(e);
        }
        
        
        //
        // CellEditorListener
        //
        public void editingStopped(ChangeEvent e) {
            completeEditing(false, false, true);
        }
        
        /** Messaged when editing has been canceled in the tree. */
        public void editingCanceled(ChangeEvent e) {
            completeEditing(false, false, false);
        }
        
        
        //
        // TreeSelectionListener
        //
        public void valueChanged(TreeSelectionEvent event) {
            // Stop editing
            completeEditing();
            // Make sure all the paths are visible, if necessary.
            // PENDING: This should be tweaked when isAdjusting is added
            if(tree.getExpandsSelectedPaths() && treeSelectionModel != null) {
                TreePath[]           paths = treeSelectionModel
                        .getSelectionPaths();
                
                if(paths != null) {
                    for(int counter = paths.length - 1; counter >= 0;
                    counter--) {
                        TreePath path = paths[counter].getParentPath();
                        boolean expand = true;
                        
                        while (path != null) {
                            // Indicates this path isn't valid anymore,
                            // we shouldn't attempt to expand it then.
                            if (treeModel.isLeaf(path.getLastPathComponent())){
                                expand = false;
                                path = null;
                            } else {
                                path = path.getParentPath();
                            }
                        }
                        if (expand) {
                            tree.makeVisible(paths[counter]);
                        }
                    }
                }
            }
            
            TreePath oldLead = getLeadSelectionPath();
            lastSelectedRow = tree.getMinSelectionRow();
            TreePath lead = tree.getSelectionModel().getLeadSelectionPath();
            setAnchorSelectionPath(lead);
            setLeadSelectionPath(lead);
            
            TreePath[]       changedPaths = event.getPaths();
            Rectangle        nodeBounds;
            Rectangle        visRect = tree.getVisibleRect();
            boolean          paintPaths = true;
            int              nWidth = tree.getWidth();
            
            if(changedPaths != null) {
                int              counter, maxCounter = changedPaths.length;
                
                if(maxCounter > 4) {
                    tree.repaint();
                    paintPaths = false;
                } else {
                    for (counter = 0; counter < maxCounter; counter++) {
                        nodeBounds = getPathBounds(tree,
                                changedPaths[counter]);
                        if(nodeBounds != null &&
                                visRect.intersects(nodeBounds))
                            tree.repaint(0, nodeBounds.y, nWidth,
                                    nodeBounds.height);
                    }
                }
            }
            if(paintPaths) {
                nodeBounds = getPathBounds(tree, oldLead);
                if(nodeBounds != null && visRect.intersects(nodeBounds))
                    tree.repaint(0, nodeBounds.y, nWidth, nodeBounds.height);
                nodeBounds = getPathBounds(tree, lead);
                if(nodeBounds != null && visRect.intersects(nodeBounds))
                    tree.repaint(0, nodeBounds.y, nWidth, nodeBounds.height);
            }
        }
        
        
        //
        // TreeExpansionListener
        //
        public void treeExpanded(TreeExpansionEvent event) {
            if(event != null && tree != null) {
                TreePath      path = event.getPath();
                
                updateExpandedDescendants(path);
            }
        }
        
        public void treeCollapsed(TreeExpansionEvent event) {
            if(event != null && tree != null) {
                TreePath        path = event.getPath();
                
                completeEditing();
                if(path != null && tree.isVisible(path)) {
                    treeState.setExpandedState(path, false);
                    updateLeadRow();
                    updateSize();
                }
            }
        }
        
        //
        // TreeModelListener
        //
        public void treeNodesChanged(TreeModelEvent e) {
            if(treeState != null && e != null) {
                treeState.treeNodesChanged(e);
                
                TreePath       pPath = e.getTreePath().getParentPath();
                
                if(pPath == null || treeState.isExpanded(pPath))
                    updateSize();
            }
        }
        
        public void treeNodesInserted(TreeModelEvent e) {
            if(treeState != null && e != null) {
                treeState.treeNodesInserted(e);
                
                updateLeadRow();
                
                TreePath       path = e.getTreePath();
                
                if(treeState.isExpanded(path)) {
                    updateSize();
                } else {
                    // PENDING(sky): Need a method in TreeModelEvent
                    // that can return the count, getChildIndices allocs
                    // a new array!
                    int[]      indices = e.getChildIndices();
                    int        childCount = treeModel.getChildCount
                            (path.getLastPathComponent());
                    
                    if(indices != null && (childCount - indices.length) == 0)
                        updateSize();
                }
            }
        }
        
        public void treeNodesRemoved(TreeModelEvent e) {
            if(treeState != null && e != null) {
                treeState.treeNodesRemoved(e);
                
                updateLeadRow();
                
                TreePath       path = e.getTreePath();
                
                if(treeState.isExpanded(path) ||
                        treeModel.getChildCount(path.getLastPathComponent()) == 0)
                    updateSize();
            }
        }
        
        public void treeStructureChanged(TreeModelEvent e) {
            if(treeState != null && e != null) {
                treeState.treeStructureChanged(e);
                
                updateLeadRow();
                
                TreePath       pPath = e.getTreePath();
                
                if (pPath != null) {
                    pPath = pPath.getParentPath();
                }
                if(pPath == null || treeState.isExpanded(pPath))
                    updateSize();
            }
        }
    }
    
    private class DragFixHandler extends Handler implements MouseMotionListener,
            QuaquaDragRecognitionSupport.BeforeDrag {
        
        // Whether or not the mouse press (which is being considered as part
        // of a drag sequence) also caused the selection change to be fully
        // processed.
        private boolean dragPressDidSelection;
        
        // Set to true when a drag gesture has been fully recognized and DnD
        // begins. Use this to ignore further mouse events which could be
        // delivered if DnD is cancelled (via ESCAPE for example)
        private boolean dragStarted;
        
        // The path over which the press occurred and the press event itself
        private TreePath pressedPath;
        private MouseEvent pressedEvent;
        
        // Used to detect whether the press event causes a selection change.
        // If it does, we won't try to start editing on the release.
        private boolean valueChangedOnPress;
        
        private boolean isActualPath(TreePath path, int x, int y) {
            if (path == null) {
                return false;
            }
            
            Rectangle bounds = getPathBounds(tree, path);
            if (y > (bounds.y + bounds.height)) {
                return false;
            }
            
            return (x >= bounds.x) && (x <= (bounds.x + bounds.width));
        }
        /**
         * Invoked when a mouse button has been pressed on a component.
         */
        public void mousePressed(MouseEvent e) {
            if (QuaquaUtilities.shouldIgnore(e, tree)) {
                return;
            }
            
            // if we can't stop any ongoing editing, do nothing
            if (isEditing(tree) && tree.getInvokesStopCellEditing()
            && !stopEditing(tree)) {
                return;
            }
            
            completeEditing();
            
            pressedPath = getClosestPathForLocation(tree, e.getX(), e.getY());
            
            if (tree.getDragEnabled()) {
                mousePressedDND(e);
            } else {
                QuaquaUtilities.adjustFocus(tree);
                handleSelectionImpl(e, pressedPath);
            }
        }
        
        private void mousePressedDND(MouseEvent e) {
            pressedEvent = e;
            boolean grabFocus = true;
            dragStarted = false;
            valueChangedOnPress = false;
            
            // if we have a valid path and this is a drag initiating event
            if (isActualPath(pressedPath, e.getX(), e.getY()) &&
                    QuaquaDragRecognitionSupport.mousePressed(e)) {
                
                dragPressDidSelection = false;
                
                if (e.isControlDown()) {
                    // do nothing for control - will be handled on release
                    // or when drag starts
                    return;
                } else if (!e.isShiftDown() && tree.isPathSelected(pressedPath)) {
                    // clicking on something that's already selected
                    // and need to make it the lead now
                    setAnchorSelectionPath(pressedPath);
                    setLeadSelectionPath(pressedPath, true);
                    return;
                }
                
                dragPressDidSelection = true;
                
                // could be a drag initiating event - don't grab focus
                grabFocus = false;
            }
            
            if (grabFocus) {
                QuaquaUtilities.adjustFocus(tree);
            }
            
            handleSelectionImpl(e, pressedPath);
        }
        
        public void dragStarting(MouseEvent me) {
            dragStarted = true;
            
            if (me.isControlDown()) {
                tree.addSelectionPath(pressedPath);
                setAnchorSelectionPath(pressedPath);
                setLeadSelectionPath(pressedPath, true);
            }
            
            pressedEvent = null;
            pressedPath = null;
        }
        
        public void mouseDragged(MouseEvent e) {
            if (QuaquaUtilities.shouldIgnore(e, tree)) {
                return;
            }
            
            if (tree.getDragEnabled()) {
                QuaquaDragRecognitionSupport.mouseDragged(e, this);
            }
        }
        
        public void mouseReleased(MouseEvent e) {
            if (QuaquaUtilities.shouldIgnore(e, tree)) {
                return;
            }
            
            if (tree.getDragEnabled()) {
                mouseReleasedDND(e);
            }
            
            pressedEvent = null;
            pressedPath = null;
        }
        
        private void mouseReleasedDND(MouseEvent e) {
            MouseEvent me = QuaquaDragRecognitionSupport.mouseReleased(e);
            if (me != null) {
                QuaquaUtilities.adjustFocus(tree);
                if (!dragPressDidSelection) {
                    handleSelectionImpl(me, pressedPath);
                }
            }
            
            if (!dragStarted) {
                
                // Note: We don't give the tree a chance to start editing if the
                // mouse press caused a selection change. Otherwise the default
                // tree cell editor will start editing on EVERY press and
                // release. If it turns out that this affects some editors, we
                // can always parameterize this with a client property. ex:
                //
                // if (pressedPath != null &&
                //         (Boolean.TRUE == tree.getClientProperty("Tree.DnD.canEditOnValueChange") ||
                //          !valueChangedOnPress) && ...
                if (pressedPath != null && !valueChangedOnPress &&
                        isActualPath(pressedPath, pressedEvent.getX(), pressedEvent.getY())) {
                    
                    startEditingOnRelease(pressedPath, pressedEvent, e);
                }
            }
        }
        
        public void valueChanged(TreeSelectionEvent event) {
            valueChangedOnPress = true;
            super.valueChanged(event);
        }
    }
    /**
     * A DropTargetListener to extend the default Swing handling of drop operations
     * by moving the tree selection to the nearest location to the mouse pointer.
     * Also adds autoscroll capability.
     */
    static class TreeDropTargetListener extends QuaquaDropTargetListener {
        
        /**
         * called to save the state of a component in case it needs to
         * be restored because a drop is not performed.
         */
        protected void saveComponentState(JComponent comp) {
            JTree tree = (JTree) comp;
            selectedIndices = tree.getSelectionRows();
        }
        
        /**
         * called to restore the state of a component
         * because a drop was not performed.
         */
        protected void restoreComponentState(JComponent comp) {
            JTree tree = (JTree) comp;
            tree.setSelectionRows(selectedIndices);
        }
        
        /**
         * called to set the insertion location to match the current
         * mouse pointer coordinates.
         */
        protected void updateInsertionLocation(JComponent comp, Point p) {
            JTree tree = (JTree) comp;
            BasicTreeUI ui = (BasicTreeUI) tree.getUI();
            TreePath path = ui.getClosestPathForLocation(tree, p.x, p.y);
            if (path != null) {
                tree.setSelectionPath(path);
            }
        }
        
        private int[] selectedIndices;
    }
}
