/*
 * @(#)JBrowser.java  1.7  2007-07-30
 *
 * Copyright (c) 2003-2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 *
 * Original code is copyright (c) 2003 Steve Roy.
 * Personal homepage: <http://homepage.mac.com/sroy>
 * Projects homepage: <http://www.roydesign.net>
 * http://www.roydesign.net/aquadialogs/
 */
package ch.randelshofer.quaqua;

import ch.randelshofer.quaqua.util.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;

/**
 * JBrowser provides a user interface for displaying and selecting items from a
 * list of data or from hierarchically organized lists of data such as directory
 * paths. When working with a hierarchy of data, the levels are displayed in
 * columns, which are numbered from left to right.
 * <p>
 * JBrowser is a clean-room implementation of an Aqua column view (NSBrowser),
 * which is used by file dialogs and the finder of Mac OS X.
 *
 * <h4>Usage</h4>
 * In general a JBrowser can be used whenever a JTree is suitable.
 * JBrowsers uses a TreeModel like a JTree.
 * <p>
 * If you are using JBrowser without the Quaqua Look and Feel, you may notice
 * that it does not fill the viewport with empty columns. To work around this,
 * you may want to use JBrowser together with the helper class JBrowserViewport.
 *
 * <h4>Known bugs</h4>
 * <ul>
 * <li><b>XXX</b> - Instances of this class are not serializable.</li>
 * <li><b>FIXME</b> - TreeSelectionModel.CONTIGUOUS_TREE_SELECTION does not work
 * (I need to fix method addSelectionInterval in the inner ListSelectionModel
 * class).</li>
 * <li><b>FIXME</b> - treeStructureChange events may result in an inconsistent
 * state of the JBrowser when selected path(s) are removed.</li>
 * <li><b>FIXME</b> - There is currently no functionality provided to make nodes
 * editable by the user.</li>
 * </ul>
 *
 * @see JBrowserViewport
 *
 *
 * @author  Werner Randelshofer
 * @version 1.7 2007-07-30 Registered KeyListener's receive key events which 
 * occur on the column lists. 
 * <br>1.6 2007-07-06 Support for ActionListener added. 
 * <br>1.5 2007-01-08 Added TreeRootHandler to detect tree structure
 * changes in the root of the tree. Lead selection of lists was not updated
 * correctly when nodes in tree were inserted/removed.
 * <br>1.4.1 2006-09-23 Made method updatePreviewColumn public.
 * <br>1.4 2005-11-26 Code for configuring enclosing scroll pane added.
 * <br>1.3.1 2005-09-17 Fixed scollable block increment.
 * <br>1.3 2005-09-07 Support for cell tips added.
 * <br>1.2 2005-08-25 JBrowser has now its own pluggable BrowserUI.
 * <br>1.1 2005-01-24 Support for a preview column added.
 * " <br>1.0.12 2004-10-22 If a node is removed from the tree model, which
 * is on the selection path, we truncate the selection path to the parent of
 * the node. Truncating is done only, if only one node is selected.
 * If a drop target is set, set it to all columns of the browser.
 * <br>1.0.11 2004-10-21 Forward mouse clicked events to MouseListeners of
 * the JBrowser.
 * <br>1.0.10 2004-10-16 Update expanded state on mouse released only.
 * Before we did update it on mouse pressed as well, but that's not consistent
 * with the behaviour of the native file chooser on OS X. Nodes that have been
 * removed from the tree model must be removed from the selection model.
 * <br>1.0.9 2004-07-27 Mouse listener fired mouseClicked event on
 * * mousePressed instead of refiring mousePressed. Tree structure changes in
 * underlying tree model caused inconsistent state in ColumnSelectionModel.
 * <br>1.0.8 2004-05-02 Request focus after mouse release on a column.
 * <br>1.0.7 2004-04-03 Method getPreferredScrollableViewPortSize returns
 * now a value based on the preferred size of the JLists in the JBrowser.
 * Fixed a NullPointerException which was thrown when on a multi-selection enabled
 * JFileChooser, a file was deselected.
 * 1.0.6 2004-03-20 Method getSelectionPath() does not throw an
 * IndexOutOfBoundsException, when no item is selected in the last column of
 * the browser.
 * <br>1.0.5 2004-03-14 Fixed a minor bug in ColumnKeyListener.
 * <br>1.0.4 2004-02-14 Mouse locations did not take the relative position
 * of a JList in its parent JViewport into account. Removed all calls to
 * getLocationOnScreen(). The changes for version 1.0.4 obsolete the changes
 * for version 1.0.3.
 * <br>1.0.3 2004-02-06 Only process mouse events when the JBrowser is
 * displayable.
 * <br>1.0.2 2003-10-09 Mouse listener on columns changes expanded already
 * state on mouse down instead of on mouse up only. This way we get a snappier
 * user experience.
 * <br>1.0.1 2003-10-09 Fixed a potential NPE caused by method
 * ensurePathIsVisible.
 * <br>1.0 2003-10-04 Heavy redesign of JBrowser. It's API is now similar to
 * the one of javax.swing.JTree. Also it supports now a TreeModel instead of
 * a specialized BrowserModel class.
 * <br>0.1 2003-07-23 Copied from Steve Roy's JBrowser.
 */
public class JBrowser extends javax.swing.JComponent implements Scrollable {
    /**
     * @see #getUIClassID
     * @see #writeObject
     */
    private static final String uiClassID = "BrowserUI";
    /**
     * The currently expanded path.
     * <p><b>Note:</b> This path does not include the additional column being
     * shown when this path points to a non-leaf node.
     */
    private TreePath expandedPath;
    
    /**
     * The fixed width of the column cells.
     */
    private int fixedCellWidth = 175;
    
    /**
     * The model that defines the tree displayed by this object.
     */
    private TreeModel treeModel;
    
    /**
     * Models the set of selected nodes in this tree.
     */
    protected transient TreeSelectionModel selectionModel;
    
    /**
     * Updates the selection models of the columns when the selection mode
     * of the <code>selectionModel</code> changes.
     */
    private transient SelectionModeUpdater
            selectionModeUpdater = new SelectionModeUpdater();
    
    /**
     * Expands the columns to match the current selection in the
     * <code>selectionModel</code>.
     * Creates a new event and passes it off the <code>selectionListeners</code>.
     */
    private transient TreeSelectionUpdater
            treeSelectionUpdater = new TreeSelectionUpdater();
    
    /**
     * Handles changes in the tree root.
     * We need this special handler, because we do not display the root node
     * using one of our columns and would thus miss changes on the root node.
     */
    private TreeRootHandler
            treeRootHandler = new TreeRootHandler();
    
    
    
    /**
     * Changes the selection when mouse events occur on the columns.
     */
    private transient ColumnMouseListener
            columnMouseListener = new ColumnMouseListener();
    
    /**
     * Moves the focus to another column when key events occur on the columns.
     * <br>
     * FIXME - this listener works only, if it receives listener events
     * <b>after</b> the KeyListener of the ListUI of a column has
     * processed its events.
     */
    private transient ColumnKeyListener columnKeyListener = new ColumnKeyListener();
    
    /**
     * Listens for focus changes, and sets the client property "Quaqua.drawFocusBorder"
     * of the JBrowser to Boolean.TRUE or Boolean.FALSE.
     * This is used for drawing focus border.
     * <br>
     * FIXME - the focus border drawing works only when the Quaqua Look and Feel
     * is used.
     */
    private transient ColumnFocusListener columnFocusListener = new ColumnFocusListener();
    
    /**
     * The cell used to draw nodes. If <code>null</code>, the UI uses a default
     * <code>cellRenderer</code>.
     * <p>
     * <b>FIXME</b> - the default cell renderer is provided by ListUI. We should
     * have a BrowserUI which provides a better default cell renderer (one which
     * shows an icon when a node is not a leaf).
     */
    private ListCellRenderer cellRenderer;
    /**
     * The cell used to draw a node in the preview column.
     * If <code>null</code>, no preview column is drawn.
     */
    private BrowserPreviewRenderer previewRenderer;
    
    /**
     * This column is only non-null, when we have a preview renderer.
     * The previewColumn contains a SizeConstrainedPanel as its child.
     * The SizeConstrainedPanel contains the component returned by the
     * preview renderer.
     * The prefererred width of the SizeConstrainedPanel is set to the
     * value of variable fixedCellWidth.
     */
    private JScrollPane previewColumn;
    
    
    //
    // Bound property names
    //
    /** Bound property name for <code>cellRenderer</code>. */
    public final static String        CELL_RENDERER_PROPERTY = "cellRenderer";
    /** Bound property name for <code>cellRenderer</code>. */
    public final static String        PREVIEW_RENDERER_PROPERTY = "previewRenderer";
    /** Bound property name for <code>treeModel</code>. */
    public final static String        TREE_MODEL_PROPERTY = "model";
    /** Bound property name for selectionModel. */
    public final static String        SELECTION_MODEL_PROPERTY = "selectionModel";
    /** Bound property name for fixedCellWidth. */
    public final static String        FIXED_CELL_WIDTH_PROPERTY = "fixedCellWidth";
    
    private Object prototypeCellValue;
    
    /**
     * If this is set to true, JBrowser shows tooltips with the cell value
     * for cells which don't fit into the current width of a column.
     */
    private boolean isShowCellTips;
    
    /**
     * Origin of the cell tip relative to the origin of the cell renderer.
     */
    private Point cellTipOrigin = new Point(0,1);
    
    /**
     * Creates a <code>JBrowser</code> with a sample model.
     * The default model used by the browser defines a leaf node as any node
     * without children.
     *
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser() {
        this(getDefaultTreeModel());
    }
    /**
     * Creates a <code>JBrowser</code> with each element of the
     * specified array as the child of a new root node which is not displayed.
     * By default, the browser defines a leaf node as any node without
     * children.
     *
     * @param value  an array of <code>Object</code>s
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser(Object[] value) {
        this(createTreeModel(value));
        expandRoot();
    }
    
    /**
     * Creates a <code>JBrowser</code> with each element of the specified
     * <code>Vector</code> as the child of a new root node which is not
     * displayed. By default, the
     * tree defines a leaf node as any node without children.
     *
     * @param value  a <code>Vector</code>
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser(Vector value) {
        this(createTreeModel(value));
        expandRoot();
    }
    
    /**
     * Creates a <code>JBrowser</code> created from a <code>Hashtable</code>
     * which does not display with root.
     * Each value-half of the key/value pairs in the <code>HashTable</code>
     * becomes a child of the new root node. By default, the tree defines
     * a leaf node as any node without children.
     *
     * @param value  a <code>Hashtable</code>
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser(Hashtable value) {
        this(createTreeModel(value));
        expandRoot();
    }
    
    /**
     * Creates a <code>JBrowser</code> with the specified
     * <code>TreeNode</code> as its root, which does not display the root node.
     * By default, the tree defines a leaf node as any node without children.
     *
     * @param root  a <code>TreeNode</code> object
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser(TreeNode root) {
        this(root, false);
    }
    
    /**
     * Creates a <code>JBrowser</code> with the specified <code>TreeNode</code>
     * as its root, which does not display
     * the root node and which decides whether a node is a
     * leaf node in the specified manner.
     *
     * @param root  a <code>TreeNode</code> object
     * @param asksAllowsChildren  if false, any node without children is a
     *              leaf node; if true, only nodes that do not allow
     *              children are leaf nodes
     * @see DefaultTreeModel#asksAllowsChildren
     */
    public JBrowser(TreeNode root, boolean asksAllowsChildren) {
        this(new DefaultTreeModel(root, asksAllowsChildren));
    }
    
    /**
     * Creates an instance of <code>JBrowser</code> which does not display the
     * root node -- the tree is created using the specified data model.
     *
     * @param newModel  the <code>TreeModel</code> to use as the data model
     */
    public JBrowser(TreeModel newModel) {
        super();
        
        initComponents();
        
        selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        selectionModel.addPropertyChangeListener(selectionModeUpdater);
        selectionModel.addTreeSelectionListener(treeSelectionUpdater);
        
        //cellRenderer = new DefaultBrowserCellRenderer(this);
        setOpaque(true);
       // setFocusCycleRoot(true);
        updateUI();
        setModel(newModel);
        Methods.invokeIfExists(this, "setFocusable", false);
    }
    
    
    /**
     * Returns the look and feel (L&F) object that renders this component.
     *
     * @return the <code>ListUI</code> object that renders this component
     */
    public BrowserUI getUI() {
        return (BrowserUI)ui;
    }
    
    
    /**
     * Sets the look and feel (L&F) object that renders this component.
     *
     * @param ui  the <code>BrowserUI</code> L&F object
     * @see UIDefaults#getUI
     */
    public void setUI(BrowserUI ui) {
        super.setUI(ui);
    }
    
    
    /**
     * Resets the UI property with the value from the current look and feel.
     *
     * @see UIManager#getUI
     */
    public void updateUI() {
        // Try to get a browser UI from the UIManager.
        // Fall back to BasicBrowserUI, if none is available.
        if (UIManager.get(getUIClassID()) != null) {
            setUI((BrowserUI)UIManager.getUI(this));
        } else {
            setUI(new BasicBrowserUI());
        }
        invalidate();
    }
    
    
    /**
     * Returns the suffix used to construct the name of the look and feel
     * (L&F) class used to render this component.
     *
     * @return the string "BrowserUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }
    
    public void setPrototypeCellValue(Object prototypeCellValue) {
        this.prototypeCellValue = prototypeCellValue;
        
    }
    
    /**
     * Creates and returns a sample <code>TreeModel</code>.
     * Used primarily for beanbuilders to show something interesting.
     *
     * @return the default <code>TreeModel</code>
     */
    protected static TreeModel getDefaultTreeModel() {
        DefaultMutableTreeNode      root = new DefaultMutableTreeNode("JBrowser");
        DefaultMutableTreeNode      parent;
        
        parent = new DefaultMutableTreeNode("colors");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("blue"));
        parent.add(new DefaultMutableTreeNode("violet"));
        parent.add(new DefaultMutableTreeNode("red"));
        parent.add(new DefaultMutableTreeNode("yellow"));
        
        parent = new DefaultMutableTreeNode("sports");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("basketball"));
        parent.add(new DefaultMutableTreeNode("soccer"));
        parent.add(new DefaultMutableTreeNode("football"));
        parent.add(new DefaultMutableTreeNode("hockey"));
        
        parent = new DefaultMutableTreeNode("food");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("hot dogs"));
        parent.add(new DefaultMutableTreeNode("pizza"));
        parent.add(new DefaultMutableTreeNode("ravioli"));
        parent.add(new DefaultMutableTreeNode("bananas"));
        return new DefaultTreeModel(root);
    }
    
    /**
     * Returns a <code>TreeModel</code> wrapping the specified object.
     * If the object is:<ul>
     * <li>an array of <code>Object</code>s,
     * <li>a <code>Hashtable</code>, or
     * <li>a <code>Vector</code>
     * </ul>then a new root node is created with each of the incoming
     * objects as children. Otherwise, a new root is created with the
     * specified object as its value.
     *
     * @param value  the <code>Object</code> used as the foundation for
     *		the <code>TreeModel</code>
     * @return a <code>TreeModel</code> wrapping the specified object
     */
    protected static TreeModel createTreeModel(Object value) {
        DefaultMutableTreeNode           root;
        
        if((value instanceof Object[]) || (value instanceof Hashtable) ||
                (value instanceof Vector)) {
            root = new DefaultMutableTreeNode("root");
            JTree.DynamicUtilTreeNode.createChildren(root, value);
        } else {
            root = new JTree.DynamicUtilTreeNode("root", value);
        }
        return new DefaultTreeModel(root, false);
    }
    
    /**
     * Expands the root path, assuming the current TreeModel has been set.
     */
    private void expandRoot() {
        selectionModel.clearSelection();
        expandPath(new TreePath(treeModel.getRoot()));
    }
    
    /**
     * Returns the path to the node that is closest to x,y.  If
     * no nodes are currently viewable, or there is no model, returns
     * <code>null</code>, otherwise it always returns a valid path.  To test if
     * the node is exactly at x, y, get the node's bounds and
     * test x, y against that.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return  the <code>TreePath</code> for the node closest to that location,
     *
     *
     * @see #getPathForLocation
     * @see #getPathBounds
     */
    public TreePath getClosestPathForLocation(int x, int y) {
        Component c = getComponentAt(x, y);
        if (c != null && (c instanceof JScrollPane)) {
            x -= c.getX();
            y -= c.getY();
            c = c.getComponentAt(x, y);
            if (c != null && (c instanceof JViewport)) {
                x -= c.getX();
                y -= c.getY();
                c = c.getComponentAt(x, y);
                if (c != null && (c instanceof JList)) {
                    x -= c.getX();
                    y -= c.getY();
                    
                    JList l = (JList) c;
                    JBrowser.ColumnListModel m = (ColumnListModel) l.getModel();
                    int index = l.locationToIndex(new Point(x, y));
                    if (index != -1) {
                        return m.path.pathByAddingChild(m.getElementAt(index));
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the <code>Rectangle</code> that the specified node will be drawn
     * into. Returns <code>null</code> if any component in the path is hidden
     * (under a collapsed parent).
     * <p>
     * Note:<br>
     * This method returns a valid rectangle, even if the specified
     * node is not currently displayed.
     *
     * @param path the <code>TreePath</code> identifying the node
     * @return the <code>Rectangle</code> the node is drawn in,
     *		or <code>null</code>
     */
    public Rectangle getPathBounds(TreePath path) {
        if (path.getPathCount() <= getListColumnCount()) {
            JList list = getColumnList(path.getPathCount() - 1);
            int index;
            if (path.getPathCount() > 1) {
                index = treeModel.getIndexOfChild(path.getPathComponent(path.getPathCount() - 2), path.getLastPathComponent());
            } else {
                index = treeModel.getIndexOfChild(null, path.getLastPathComponent());
            }
            
            // A column list is presented inside of a JViewport and a JPanel
            // in the JBrowser.
            Rectangle bounds = list.getCellBounds(index, index);
            bounds.x += list.getLocation().x
                    + list.getParent().getLocation().x
                    + list.getParent().getParent().getLocation().x;
            bounds.y += list.getLocation().y
                    + list.getParent().getLocation().y
                    + list.getParent().getParent().getLocation().y;
            
            return bounds;
        } else {
            return null;
        }
    }
    
    /**
     * Set this to true, if you want to JBrowser to display a tooltip for the
     * cell over which the mouse is hovering.
     * The tooltip is shown only for cells which are wider than the current
     * width of the browser column.
     */
    public void setShowCellTips(boolean newValue) {
        boolean oldValue = isShowCellTips;
        if (oldValue != newValue) {
            isShowCellTips = newValue;
            String tipText = (newValue) ? "cell tip" : null;
            for (int i=0, n = getColumnCount(); i < n; i++) {
                getColumnList(i).setToolTipText(tipText);
            }
            firePropertyChange("showCellTips", oldValue, newValue);
        }
    }
    
    /**
     * Returns true if the JBrowser shows cell tips for list cells that don't
     * fit into a column.
     */
    public boolean isShowCellTips() {
        return isShowCellTips;
    }
    
    /**
     * Sets the origin of the cell tip tooltip relative to the origin of
     * the cell renderer.
     */
    public void setShowCellTipOrigin(Point newValue) {
        Point oldValue = cellTipOrigin;
        cellTipOrigin = (newValue == null) ? new Point(0,0) : (Point) newValue.clone();
        firePropertyChange("cellTipOrigin", oldValue, newValue);
    }
    
    /**
     * Returns the origin of the cell tip tooltip relative to the origin of
     * the cell renderer.
     */
    public Point getCellTipOrigin() {
        return (Point) cellTipOrigin.clone();
    }
    
    
    /**
     * Returns true if the item identified by the path is currently selected.
     *
     * @param path a <code>TreePath</code> identifying a node
     * @return true if the node is selected
     */
    public boolean isPathSelected(TreePath path) {
        TreePath[] selectionPaths = getSelectionPaths();
        for (int i=0; i < selectionPaths.length; i++) {
            if (selectionPaths[i].equals(path)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Adds the node identified by the specified <code>TreePath</code>
     * to the current selection. If any component of the path isn't
     * viewable, and <code>getExpandsSelectedPaths</code> is true it is
     * made viewable.
     * <p>
     * Note that <code>JBrowser</code> does not allow duplicate nodes to
     * exist as children under the same parent -- each sibling must be
     * a unique object.
     *
     * @param path the <code>TreePath</code> to add
     */
    public void addSelectionPath(TreePath path) {
        for (int i=0; i < path.getPathCount(); i++) {
            JList columnList = getColumnList(i);
            int index = treeModel.getIndexOfChild((i == 0) ? null : path.getPathComponent(i - 1), path.getPathComponent(i));
            if (! columnList.isSelectedIndex(index)) {
                if (i < path.getPathCount() - 1) {
                    columnList.setSelectionInterval(index, index);
                } else {
                    columnList.addSelectionInterval(index, index);
                }
            }
        }
    }
    /**
     * Ensures that the last node of the specified path is visible.
     * <br>
     * Nothing happens, if it is impossible to make the node
     * visible without changing the selection of the JBrowser.
     *
     * @param path the <code>TreePath</code> specifying the node to make
     * visible.
     */
    public void ensurePathIsVisible(TreePath path) {
        TreePath selectionPath = selectionModel.getSelectionPath();
        if (selectionPath != null
                && (path.isDescendant(selectionPath) || selectionPath.isDescendant(path))
                ) {
            if (! isValid()) {
                setSize(getPreferredSize());
                doLayout();
            }
            
            
            JList columnList;
            for (int i=0; i < path.getPathCount() - 1 && i < getListColumnCount(); i++) {
                columnList = getColumnList(i);
                int index0 = columnList.getSelectedIndex();
                
                Rectangle bounds = columnList.getCellBounds(index0, index0);
                
                if (index0 != -1 && bounds != null ) {
                    columnList.scrollRectToVisible( bounds );
                }
            }
            
            Component component = getComponent(getColumnCount() - 1);
            
            if( component != null && component.getBounds() != null) {
                scrollRectToVisible( component.getBounds());
            }
        }
    }
    /**
     * Selects the node identified by the specified path.
     *
     * @param path the <code>TreePath</code> specifying the node to select
     */
    public void setSelectionPath(TreePath path) {
        selectionModel.setSelectionPath(path);
    }
    /**
     * Removes the node identified by the specified path from the current
     * selection.
     *
     * @param path  the <code>TreePath</code> identifying a node
     */
    public void removeSelectionPath(TreePath path) {
        if (path.getPathCount() <= getListColumnCount()) {
            JList columnList = getColumnList(path.getPathCount() - 1);
            int index = treeModel.getIndexOfChild(path.getPathComponent(path.getPathCount() - 2), path.getLastPathComponent());
            columnList.removeSelectionInterval(index, index);
        }
    }
    
    /**
     * Returns the path for the node at the specified location.
     *
     * @param x an integer giving the number of pixels horizontally from
     *          the left edge of the display area, minus any left margin
     * @param y an integer giving the number of pixels vertically from
     *          the top of the display area, minus any top margin
     * @return  the <code>TreePath</code> for the node at that location
     */
    public TreePath getPathForLocation(int x, int y) {
        // XXX - Check if closest path intersects x,y.
        return getClosestPathForLocation(x, y);
        /*
        TreePath closestPath = getClosestPathForLocation(x, y);
         
        if (closestPath != null) {
            Rectangle pathBounds = getPathBounds(closestPath);
         
            if(pathBounds != null &&
            x >= pathBounds.x && x < (pathBounds.x + pathBounds.width) &&
            y >= pathBounds.y && y < (pathBounds.y + pathBounds.height))
                return closestPath;
        }
        return null;
         */
    }
    
    /**
     * Clears the selection.
     * This collapses all columns.
     */
    public void clearSelection() {
        setSelectionPath(new TreePath(treeModel.getRoot()));
    }
    
    /**
     * Sets the width of every cell in the browser.  If <code>width</code> is -1,
     * cell widths are computed by applying <code>getPreferredSize</code>
     * to the <code>cellRenderer</code> component for each tree node.
     * <p>
     * The default value of this property is 175.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param width   the width, in pixels, for all cells in this list
     * @see #setFixedCellWidth
     * @see JComponent#addPropertyChangeListener
     */
    public void setFixedCellWidth(int width) {
        int oldValue = fixedCellWidth;
        fixedCellWidth = width;
        for (int i=0; i < getListColumnCount(); i++) {
            getColumnList(i).setFixedCellWidth(width);
        }
        if (previewColumn != null) {
            SizeConstrainedPanel p = (SizeConstrainedPanel) previewColumn.getComponent(0);
            p.setPreferredWidth(width);
        }
        if (getParent() != null) {
            getParent().validate();
        }
        
        firePropertyChange(FIXED_CELL_WIDTH_PROPERTY, oldValue, fixedCellWidth);
    }
    public int getFixedCellWidth() {
        return fixedCellWidth;
    }
    /**
     * Sets the delegate that's used to paint each cell in the browser.
     * <p>
     * The default value of this property is provided by the ListUI
     * delegate, i.e. by the look and feel implementation.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param cellRenderer the <code>ListCellRenderer</code>
     * 				that paints browser cells
     * @see #getCellRenderer
     */
    public void setCellRenderer(ListCellRenderer cellRenderer) {
        ListCellRenderer oldValue = this.cellRenderer;
        this.cellRenderer = cellRenderer;
        
        for (int i=0; i < getListColumnCount(); i++) {
            getColumnList(i).setCellRenderer(cellRenderer);
        }
        
        firePropertyChange(CELL_RENDERER_PROPERTY, oldValue, cellRenderer);
    }
    /**
     * Sets the delegate that's used to paint the preview column in the browser.
     * <p>
     * The default value of this property is null. If null, no preview column
     * is shown.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param newValue the <code>ListCellRenderer</code>
     * 				that paints the preview column.
     * @see #getCellRenderer
     */
    public void setPreviewRenderer(BrowserPreviewRenderer newValue) {
        BrowserPreviewRenderer oldValue = this.previewRenderer;
        this.previewRenderer = newValue;
        
        // TODO: update preview column
        if (newValue == null) {
            if (previewColumn != null) {
                remove(previewColumn);
                previewColumn = null;
            }
        } else {
            if (previewColumn == null) {
                SizeConstrainedPanel p = new SizeConstrainedPanel();
                p.setPreferredWidth(fixedCellWidth);
                previewColumn = new JScrollPane(p,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                previewColumn.setBorder(null);
                Methods.invokeIfExists(previewColumn, "setFocusable", false);
                Methods.invokeIfExists(previewColumn.getHorizontalScrollBar(), "setFocusable", false);
                Methods.invokeIfExists(previewColumn.getVerticalScrollBar(), "setFocusable", false);
                if (getDropTarget() != null) {
                    new DropTarget(previewColumn, getDropTarget().getDefaultActions(), getDropTarget());
                    new DropTarget(p, getDropTarget().getDefaultActions(), getDropTarget());
                }
            }
            updatePreviewColumn();
        }
        
        firePropertyChange(CELL_RENDERER_PROPERTY, oldValue, newValue);
    }
    /**
     * Gets the delegate that's used to paint each cell in the browser.
     * <p>
     * The default value of this property is provided by the ListUI
     * delegate, i.e. by the look and feel implementation.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @return The <code>ListCellRenderer</code>
     * 				that paints browser cells
     */
    public ListCellRenderer getCellRenderer() {
        return cellRenderer;
    }
    /**
     * Returns the number of columns this includes the count of list columns
     * and the preview column (if it is shown).
     */
    private int getColumnCount() {
        return getComponentCount();
    }
    /**
     * Returns the number of list columns. This count does not include the
     * preview column.
     */
    private int getListColumnCount() {
        int count = getComponentCount();
        if (previewColumn != null && previewColumn.getParent() == this) {
            return count - 1;
        } else {
            return count;
        }
    }
    
    public void setDropTarget(DropTarget t) {
        super.setDropTarget(t);
        if (t != null) {
            for (int i=0, n=getListColumnCount(); i < n; i++) {
                new DropTarget(getColumnList(i), getDropTarget().getDefaultActions(), getDropTarget());
                //getColumnList(i).setDropTarget(t);
            }
        }
        if (previewColumn != null) {
            new DropTarget(previewColumn, getDropTarget().getDefaultActions(), getDropTarget());
            new DropTarget((SizeConstrainedPanel) previewColumn.getViewport().getView(), getDropTarget().getDefaultActions(), getDropTarget());
        }
    }
    
    /**
     * Convenience method for accessing the JList at the specified column.
     */
    private JList getColumnList(int column) {
        return (JList) ((JScrollPane)getComponent(column)).getViewport().getView();
    }
    /**
     * Convenience method for accessing the list model at the specified column.
     */
    private ColumnListModel getColumnListModel(int column) {
        return (ColumnListModel) ((JList) ((JScrollPane)getComponent(column)).getViewport().getView()).getModel();
    }
    /**
     * Convenience method for accessing the TreePath at the specified column.
     */
    private TreePath getColumnPath(int column) {
        return ((ColumnListModel) ((JList) ((JScrollPane)getComponent(column)).getViewport().getView()).getModel()).path;
    }
    
    
    /**
     * Returns the number of nodes selected.
     *
     * @return the number of nodes selected
     */
    public int getSelectionCount() {
        return selectionModel.getSelectionCount();
    }
    
    /**
     * Sets the tree's selection model.
     *
     * @param selectionModel the <code>TreeSelectionModel</code> to use.
     * @see TreeSelectionModel
     *
     * @exception IllegalArgumentException if the selectionModel is null.
     */
    public void setSelectionModel(TreeSelectionModel selectionModel) {
        if(selectionModel == null) throw new IllegalArgumentException();
        
        TreeSelectionModel oldValue = this.selectionModel;
        
        if (this.selectionModel != null) {
            this.selectionModel.removeTreeSelectionListener(treeSelectionUpdater);
            this.selectionModel.removePropertyChangeListener(selectionModeUpdater);
        }
        if (accessibleContext != null) {
            this.selectionModel.removeTreeSelectionListener((TreeSelectionListener)accessibleContext);
            selectionModel.addTreeSelectionListener((TreeSelectionListener)accessibleContext);
        }
        
        this.selectionModel = selectionModel;
        if (selectionModel != null) {
            this.selectionModel.addTreeSelectionListener(treeSelectionUpdater);
            this.selectionModel.addPropertyChangeListener(selectionModeUpdater);
        }
        firePropertyChange(SELECTION_MODEL_PROPERTY, oldValue,
                this.selectionModel);
                           /*
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_SELECTION_PROPERTY,
                    Boolean.valueOf(false), Boolean.valueOf(true));
        }*/
    }
    
    /**
     * Returns the model for selections. This should always return a
     * non-<code>null</code> value. If you don't want to allow anything
     * to be selected
     * set the selection model to <code>null</code>, which forces an empty
     * selection model to be used.
     *
     * @see #setSelectionModel
     */
    public TreeSelectionModel getSelectionModel() {
        return selectionModel;
    }
    
    /**
     * Sets the selection mode, which must be one of SINGLE_TREE_SELECTION,
     * CONTIGUOUS_TREE_SELECTION or DISCONTIGUOUS_TREE_SELECTION.
     * <p>
     * This may change the selection if the current selection is not valid
     * for the new mode. For example, if three TreePaths are
     * selected when the mode is changed to <code>SINGLE_TREE_SELECTION</code>,
     * only one TreePath will remain selected. It is up to the particular
     * implementation to decide what TreePath remains selected.
     */
    public void setSelectionMode(int selectionMode) {
        selectionModel.setSelectionMode(selectionMode);
    }
    
    /**
     * Returns the path to the first selected node.
     *
     * @return the <code>TreePath</code> for the first selected node,
     *		or <code>null</code> if nothing is currently selected
     */
    public TreePath getSelectionPath() {
        JList list = getColumnList(getListColumnCount() - 1);
        ColumnListModel m = getColumnListModel(getListColumnCount() - 1);
        int i = list.getSelectedIndex();
        Object pathComponent = (i == -1 || i >= m.getSize()) ? null : list.getSelectedValue();
        return (pathComponent == null) ? m.path : m.path.pathByAddingChild(pathComponent);
    }
    /**
     * Returns the paths of all selected values.
     *
     * @return an array of <code>TreePath</code> objects indicating the selected
     *         nodes, or <code>null</code> if nothing is currently selected
     */
    public TreePath[] getSelectionPaths() {
        return getSelectionModel().getSelectionPaths();
    }
    
    /**
     * Selects the nodes identified by the specified array of paths.
     * All path components except the last one of the paths must be equal.
     *
     * @param paths an array of <code>TreePath</code> objects that specifies
     *		the nodes to select
     */
    public void setSelectionPaths(TreePath[] paths) {
        try {
            getSelectionModel().setSelectionPaths(paths);
        } catch (ClassCastException e) {
            for (int i=0; i < paths.length; i++) {
                System.err.println(paths[i]);
            }
            e.printStackTrace();
        }
    }
    
    /**
     * Returns an array of all the key listeners
     * registered on this component.
     *
     * @return all of this component's <code>KeyListener</code>s
     *         or an empty array if no key
     *         listeners are currently registered
     */
    public synchronized KeyListener[] getKeyListeners() {
        return (KeyListener[]) (getListeners(KeyListener.class));
    }
    public void updatePreviewColumn() {
        if (previewColumn != null) {
            SizeConstrainedPanel p = (SizeConstrainedPanel) previewColumn.getViewport().getView();
            TreePath[] paths = getSelectionPaths();
            switch ((paths == null) ? 0 : paths.length) {
                case 0 :
                    remove(previewColumn);
                    break;
                case 1 :
                    if (treeModel.isLeaf(paths[0].getLastPathComponent())) {
                        p.removeAll();
                        p.add(previewRenderer.getPreviewRendererComponent(this, paths));
                        add(previewColumn);
                    } else {
                        remove(previewColumn);
                    }
                    break;
                default :
                    p.removeAll();
                    p.add(previewRenderer.getPreviewRendererComponent(this, paths));
                    add(previewColumn);
                    break;
            }
        }
    }
    
    
    /**
     * Adds a listener for <code>TreeSelection</code> events.
     *
     * @param tsl the <code>TreeSelectionListener</code> that will be notified
     *            when a node is selected or deselected (a "negative
     *            selection")
     */
    public void addTreeSelectionListener(TreeSelectionListener tsl) {
        listenerList.add(TreeSelectionListener.class,tsl);
    }
    /**
     * Removes a <code>TreeSelection</code> listener.
     *
     * @param tsl the <code>TreeSelectionListener</code> to remove
     */
    public void removeTreeSelectionListener(TreeSelectionListener tsl) {
        listenerList.remove(TreeSelectionListener.class,tsl);
    }
    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * @param e the <code>TreeSelectionEvent</code> to be fired;
     *          generated by the
     *		<code>TreeSelectionModel</code>
     *          when a node is selected or deselected
     * @see EventListenerList
     */
    protected void fireValueChanged(TreeSelectionEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            // TreeSelectionEvent e = null;
            if (listeners[i]==TreeSelectionListener.class) {
                // Lazily create the event:
                // if (e == null)
                // e = new ListSelectionEvent(this, firstIndex, lastIndex);
                ((TreeSelectionListener)listeners[i+1]).valueChanged(e);
            }
        }
    }
    
    /**
     * Returns the <code>TreeModel</code> that is providing the data.
     *
     * @return the <code>TreeModel</code> that is providing the data
     */
    public TreeModel getModel() {
        return treeModel;
    }
    
    /**
     * Sets the <code>TreeModel</code> that will provide the data.
     *
     * @param newModel the <code>TreeModel</code> that is to provide the data
     */
    public void setModel(TreeModel newModel) {
        TreeModel oldModel = treeModel;
        
        if (oldModel != null) {
            oldModel.removeTreeModelListener(treeRootHandler);
        }
        treeModel = newModel;
        if (newModel != null) {
            newModel.addTreeModelListener(treeRootHandler);
        }
        
        for (int i=getListColumnCount() - 1; i >= 0; i--) {
            removeColumn();
        }
        
        if(treeModel != null) {
            // Clear the expanded path and expand the root
            expandedPath = null;
            expandRoot();
        }
        
        firePropertyChange(TREE_MODEL_PROPERTY, oldModel, treeModel);
    }
    
    /**
     * Ensures that the node identified by the specified path is
     * expanded and viewable. If the last item in the path is not a
     * leaf, an additional column is shown.
     *
     * @param path  the <code>TreePath</code> identifying a node
     */
    private void expandPath(TreePath path) {
        if (expandedPath == null || ! expandedPath.equals(path)) {
            // Remove all extraneous columns
            boolean removeAll = getListColumnCount() > 0 && ! getColumnListModel(0).path.isDescendant(path);
            for (int i=getListColumnCount() - 1; i >= 0; i--) {
                if (removeAll || ! getColumnListModel(i).path.isDescendant(path)) {
                    removeColumn();
                } else {
                    break;
                }
            }
            
            // Add new columns if necessary
            java.util.List components = Arrays.asList(path.getPath());
            for (int i=getListColumnCount(); i < path.getPathCount() - 1; i++) {
                addColumn(new TreePath(components.subList(0, i + 1).toArray()));
            }
            if (getListColumnCount() == path.getPathCount() - 1
                    && ! treeModel.isLeaf(path.getLastPathComponent())
                    && getSelectionCount() <= 1) {
                addColumn(path);
            }
            
            // Set the selection to match the new path
            // Note: We do not change the selection of the column from
            // getPathCount() - 1 onwards, because the selection of this column
            // must be set by the method calling us.
            int i;
            for (i=0; i < path.getPathCount() - 1; i++) {
                
                getColumnList(i).setSelectedIndex(treeModel.getIndexOfChild(path.getPathComponent(i), path.getPathComponent(i + 1)));
                //getColumnList(i).getSelectionModel().setLeadSelectionIndex(treeModel.getIndexOfChild(path.getPathComponent(i), path.getPathComponent(i + 1)));
            }
            
            /*
            if (getParent() != null) {
                getParent().validate();
            } else {
                validate();
            }*/
            expandedPath = path;
        }
        //scrollRectToVisible(getComponent(getComponentCount() - 1).getBounds());
    }
    
    /**
     * Appends a new column to the browser.
     */
    protected void addColumn(TreePath path) {
        JList l;
        l = new JList(new ColumnListModel(path, treeModel)) {
            public String getToolTipText(MouseEvent event) {
                Point mouseLocation = event.getPoint();
                int index = locationToIndex(mouseLocation);
                if (index != -1) {
                    Rectangle cellBounds = getCellBounds(index, index);
                    if (cellBounds.contains(mouseLocation)) {
                        Object value = getModel().getElementAt(index);
                        Component renderer = getCellRenderer().getListCellRendererComponent(this, value, index, false, false);
                        if (renderer.getPreferredSize().width > getWidth()) {
                            return value.toString();
                        }
                    }
                }
                return null;
            }
            public Point getToolTipLocation(MouseEvent event) {
                Point mouseLocation = event.getPoint();
                int index = locationToIndex(mouseLocation);
                if (index != -1) {
                    Rectangle cellBounds = getCellBounds(index, index);
                    if (cellBounds.contains(mouseLocation)) {
                        Object value = getModel().getElementAt(index);
                        Component renderer = getCellRenderer().getListCellRendererComponent(this, value, index, false, false);
                        if (renderer.getPreferredSize().width > getWidth()) {
                            Point location = cellBounds.getLocation();
                            location.x += cellTipOrigin.x;
                            location.y += cellTipOrigin.y;
                            return location;
                        }
                    }
                }
                return null;
            }
        };
        if (isShowCellTips) {
            l.setToolTipText("cell tip");
        }
        if (getDropTarget() != null) {
            new DropTarget(l, getDropTarget().getDefaultActions(), getDropTarget());
            //l.setDropTarget(getDropTarget());
        }
        if (cellRenderer != null) {
            l.setCellRenderer(cellRenderer);
        }
        if (prototypeCellValue != null) {
            l.setPrototypeCellValue(prototypeCellValue);
        }
        l.setSelectionModel(new ColumnSelectionModel());
        
        int selectionMode;
        switch (selectionModel.getSelectionMode()) {
            case TreeSelectionModel.CONTIGUOUS_TREE_SELECTION :
                selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
                break;
            case TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION :
                selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
                break;
            case TreeSelectionModel.SINGLE_TREE_SELECTION :
            default :
                selectionMode = ListSelectionModel.SINGLE_SELECTION ;
                break;
        }
        l.setSelectionMode(selectionMode);
        l.addMouseListener(columnMouseListener);
        l.addKeyListener(columnKeyListener);
        l.addFocusListener(columnFocusListener);
        l.setFixedCellWidth(fixedCellWidth);
        JScrollPane sp = new JScrollPane(l,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        Methods.invokeIfExists(sp, "setFocusable", false);
        Methods.invokeIfExists(sp.getVerticalScrollBar(), "setFocusable", false);
        Methods.invokeIfExists(sp.getHorizontalScrollBar(), "setFocusable", false);
        
        add(sp, getListColumnCount());
    }
    
    /**
     * Removes the last column from the browser.
     */
    protected void removeColumn() {
        int columnIndex = getListColumnCount() - 1;
        
        JList l = getColumnList(columnIndex);
        
        JScrollPane sp = (JScrollPane) getComponent(columnIndex);
        sp.remove(l);
        remove(sp);
        
        if (l.hasFocus()) {
            putClientProperty("Quaqua.drawFocusBorder", Boolean.FALSE);
            repaintParentBorder();
        }
        
        l.removeMouseListener(columnMouseListener);
        l.removeKeyListener(columnKeyListener);
        l.removeFocusListener(columnFocusListener);
        
        ((ColumnListModel) l.getModel()).dispose();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.GridLayout(1, 0));

    }//GEN-END:initComponents
    
    
    /**
     * Returns the preferred display size of a <code>JBrowser</code>.
     * The height is determined from <code>getVisibleRowCount</code> and
     * the width is the current preferred width of two columns.
     *
     * @return a <code>Dimension</code> object containing the preferred size
     */
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = new Dimension();
        if (getComponentCount() == 0) {
            JScrollPane dummyColumn = new JScrollPane();
            JList list = new JList();
            list.setPrototypeCellValue(prototypeCellValue);
            dummyColumn.setViewportView(list);
            size.setSize(dummyColumn.getPreferredSize());
            size.width *= 2;
        } else {
            for (int i=0; i < 2 && i < getComponentCount(); i++) {
                Dimension componentSize = getComponent(i).getPreferredSize();
                size.height = Math.max(size.height, componentSize.height);
                size.width += componentSize.width;
            }
        }
        /*
        size.height = 10;
        size.width = 10;
         */
        return size;
    }
    
    /**
     * Returns the amount to increment when scrolling. The amount is
     * the width of the first/last displayed column that isn't completely in
     * view or, if it is totally displayed, the width of the next column in the
     * scrolling direction.
     * <p>
     * The height is always 10.
     * <p>
     * <b>FIXME</b> - Find a better way to compute the height.
     *
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code>
     *		or <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left,
     *		greater than zero for down/right
     * @return the "unit" increment for scrolling in the specified direction
     * @see JScrollBar#setUnitIncrement(int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        Component[] components = getComponents();
        int increment = 0;
        if (direction > 0) {
            switch (orientation) {
                case SwingConstants.HORIZONTAL :
                    for (int i=components.length - 1 ; i >= 0; i--) {
                        Rectangle cbounds = components[i].getBounds();
                        if (cbounds.x + cbounds.width > visibleRect.x + visibleRect.width) {
                            increment = cbounds.x + cbounds.width - visibleRect.x - visibleRect.width;
                        }
                    }
                    break;
                case SwingConstants.VERTICAL :
                    increment = 10;
                    break;
            }
        } else {
            switch (orientation) {
                case SwingConstants.HORIZONTAL :
                    for (int i=0 ; i < components.length; i++) {
                        Rectangle cbounds = components[i].getBounds();
                        if (cbounds.x < visibleRect.x) {
                            increment = visibleRect.x - cbounds.x;
                        }
                    }
                    break;
                case SwingConstants.VERTICAL :
                    increment = 10;
                    break;
            }
        }
        return increment;
    }
    
    /**
     * Returns true to indicate that the height of the viewport
     * determines the height of the browser.
     * <p>
     * In other words: the JScrollPane must never show a vertical scroll bar,
     * because each column of the JBrowser provides one.
     *
     * @return true
     * @see Scrollable#getScrollableTracksViewportHeight
     */
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }
    
    /**
     * Returns false to indicate that the width of the viewport does not
     * determine the width of the browser, unless the preferred width of
     * the browser is smaller than the viewports width.  In other words:
     * ensure that the browser is never smaller than its viewport.
     *
     * @return false
     * @see Scrollable#getScrollableTracksViewportWidth
     */
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
    
    /**
     * Returns the amount to increment when scrolling. The amount is
     * the distance to the next column in the scrolling direction which
     * is outside of the view.
     * <p>
     * The height is always 10.
     * <p>
     * <b>FIXME</b> - Find a better way to compute the height.
     *
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code>
     *		or <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left,
     *		greater than zero for down/right
     * @return the "unit" increment for scrolling in the specified direction
     * @see JScrollBar#setUnitIncrement(int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        Component[] components = getComponents();
        int increment = 0;
        if (direction > 0) {
            switch (orientation) {
                case SwingConstants.HORIZONTAL :
                    for (int i=components.length - 1 ; i >= 0; i--) {
                        Rectangle cbounds = components[i].getBounds();
                        /*
                        if (cbounds.x + cbounds.width > visibleRect.x + visibleRect.width) {
                            increment = cbounds.x + cbounds.width - visibleRect.x - visibleRect.width;
                        }*/
                        if (cbounds.x > visibleRect.x + visibleRect.width) {
                            increment = cbounds.x - visibleRect.x;
                        }
                    }
                    break;
                case SwingConstants.VERTICAL :
                    increment = 10;
                    break;
            }
        } else {
            switch (orientation) {
                case SwingConstants.HORIZONTAL :
                    for (int i=0 ; i < components.length; i++) {
                        Rectangle cbounds = components[i].getBounds();
                        if (cbounds.x + cbounds.width < visibleRect.x) {
                            increment = visibleRect.x - cbounds.x;
                        }
                    }
                    if (increment == 0) {
                        increment = visibleRect.x - components[0].getBounds().x;
                    }
                    break;
                case SwingConstants.VERTICAL :
                    increment = 10;
                    break;
            }
        }
        return increment;
    }
    /**
     * Calls the <code>configureEnclosingScrollPane</code> method.
     *
     * @see #configureEnclosingScrollPane
     */
    public void addNotify() {
        super.addNotify();
        configureEnclosingScrollPane();
    }
    
    /**
     * If this <code>JTable</code> is the <code>viewportView</code> of an enclosing <code>JScrollPane</code>
     * (the usual situation), configure this <code>ScrollPane</code> by, amongst other things,
     * installing the table's <code>tableHeader</code> as the <code>columnHeaderView</code> of the scroll pane.
     * When a <code>JTable</code> is added to a <code>JScrollPane</code> in the usual way,
     * using <code>new JScrollPane(myTable)</code>, <code>addNotify</code> is
     * called in the <code>JTable</code> (when the table is added to the viewport).
     * <code>JTable</code>'s <code>addNotify</code> method in turn calls this method,
     * which is protected so that this default installation procedure can
     * be overridden by a subclass.
     *
     * @see #addNotify
     */
    protected void configureEnclosingScrollPane() {
        /*
        Container p = getParent();
        if (p instanceof JViewport) {
            JViewport viewport = (JViewport) p;
            //viewport.setOpaque(true);
        }*/
    }
    
    /**
     * Calls the <code>unconfigureEnclosingScrollPane</code> method.
     *
     * @see #unconfigureEnclosingScrollPane
     */
    public void removeNotify() {
        unconfigureEnclosingScrollPane();
        super.removeNotify();
    }
    
    /**
     * Reverses the effect of <code>configureEnclosingScrollPane</code>
     * by replacing the <code>columnHeaderView</code> of the enclosing
     * scroll pane with <code>null</code>. <code>JTable</code>'s
     * <code>removeNotify</code> method calls
     * this method, which is protected so that this default uninstallation
     * procedure can be overridden by a subclass.
     *
     * @see #removeNotify
     * @see #configureEnclosingScrollPane
     */
    protected void unconfigureEnclosingScrollPane() {
        /*
        Container p = getParent();
        if (p instanceof JViewport) {
         
        }*/
    }
    
    public void requestFocus() {
        TreePath tp = getSelectionPath();
        if (tp != null && tp.getPathCount() > 1) {
            getColumnList(tp.getPathCount() - 2).requestFocus();
        } else {
            getColumnList(getListColumnCount() - 1).requestFocus();
        }
    }
    
    
    private void repaintParentBorder() {
        Component parent = getParent();
        if (parent != null && (parent instanceof JViewport)) {
            parent = parent.getParent();
            Insets insets = ((JComponent) parent).getInsets();
            Dimension size = parent.getSize();
            parent.repaint(0, 0, size.width, insets.top);
            parent.repaint(0, size.height - insets.bottom, size.width, insets.bottom);
            parent.repaint(0, insets.top, insets.left, size.height - insets.top - insets.bottom);
            parent.repaint(size.width - insets.right, insets.top, insets.right, size.height - insets.top - insets.bottom);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    /**
     * This is the list model used to map a tree node of the <code>treeModel</code>
     * to a JList displaying its children.
     */
    private class ColumnListModel extends AbstractListModel implements TreeModelListener {
        private TreePath path;
        private TreeModel model;
        /**
         * Wee need to copy the number of children of the underlying tree node
         * into this instance variable, because we have to generate a proper
         * interval added/interval removed even upon a change in the tree structure.
         */
        private int size;
        
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append('{');
            for (int i=0, n=getSize(); i < n; i++) {
                if (i!=0) buf.append(',');
                buf.append(getElementAt(i));
            }
            buf.append("} #");
            buf.append(hashCode());
            return buf.toString();
        }
        
        public ColumnListModel(TreePath path, TreeModel model) {
            this.path = path;
            this.model = model;
            model.addTreeModelListener(this);
            updateSize();
        }
        
        public void dispose() {
            model.removeTreeModelListener(this);
        }
        
        public int getSize() {
            return size;
            //return model.getChildCount(path.getLastPathComponent());
        }
        
        private void updateSize() {
            this.size = model.getChildCount(path.getLastPathComponent());
        }
        
        public Object getElementAt(int row) {
            return model.getChild(path.getLastPathComponent(), row);
        }
        
        public void treeNodesChanged(TreeModelEvent e) {
            if (e.getTreePath().equals(path)) {
                int[] indices = e.getChildIndices();
                fireContentsChanged(this, indices[0], indices[indices.length - 1]);
            }
        }
        
        public void treeNodesInserted(TreeModelEvent e) {
            if (e.getTreePath().equals(path)) {
                updateSize();
                
                // We analyze the indices for contiguous intervals
                // and fire interval added events for all intervals we find.
                int[] indices = e.getChildIndices();
                int start = 0;
                int startIndex;
                int end;
                do {
                    startIndex = indices[start];
                    for (end=start + 1; end < indices.length; end++) {
                        if (indices[end] != startIndex + end - start) break;
                    }
                    fireIntervalAdded(this, startIndex, indices[end - 1]);
                    start = end;
                } while (start < indices.length);
            }
        }
        
        public void treeNodesRemoved(TreeModelEvent e) {
            if (e.getTreePath().equals(path)) {
                updateSize();
                
                // We analyze the indices for contiguous intervals
                // and fire interval removed events for all intervals we find.
                int[] indices = e.getChildIndices();
                int start = 0;
                int startIndex;
                int end;
                int offset = 0;
                do {
                    startIndex = indices[start];
                    for (end=start + 1; end < indices.length; end++) {
                        if (indices[end] != startIndex + end - start) break;
                    }
                    fireIntervalRemoved(this, startIndex - offset, indices[end - 1] - offset);
                    offset += indices[end - 1] - startIndex + 1;
                    start = end;
                } while (start < indices.length);
                
                // RemovedChildren can't be selected.
                if (selectionModel.getSelectionCount() > 0) {
                    TreePath[] selectionPaths = selectionModel.getSelectionPaths();
                    Object[] removedChildren = e.getChildren();
                    for (int i=0; i < removedChildren.length; i++) {
                        TreePath removedPath = e.getTreePath().pathByAddingChild(removedChildren[i]);
                        for (int j=0; j < selectionPaths.length; j++) {
                            if (removedPath.isDescendant(selectionPaths[j])) {
                                selectionPaths[j] = e.getTreePath();
                            }
                        }
                        setSelectionPaths(selectionPaths);
                    }
                }
            }
        }
        
        public void treeStructureChanged(TreeModelEvent e) {
            TreePath changedPath = e.getTreePath();
            if (changedPath.equals(path) || path.getPathCount() == 1 && changedPath.getPathCount() == 1) {
                int oldSize = getSize();
                path = changedPath;
                updateSize();
                int newSize = getSize();
                path = changedPath;
                int diff = newSize - oldSize;
                if (diff < 0) {
                    if (newSize > 0) {
                        fireContentsChanged(this, 0, newSize - 1);
                    }
                    fireIntervalRemoved(this, newSize, oldSize - 1);
                } else if (diff > 0) {
                    if (oldSize > 0) {
                        fireContentsChanged(this, 0, oldSize - 1);
                    }
                    fireIntervalAdded(this, oldSize, newSize - 1);
                } else {
                    fireContentsChanged(this, 0, oldSize - 1);
                }
                
                setSelectionPath(changedPath);
            }
        }
    }
    /**
     * Expands columns of the JBrowser to ensure that selected columns
     * are visible.
     * <p>
     * Handles creating a new <code>TreeSelectionEvent</code> with the
     * <code>JBrowser</code> as the source and passing it off to all the
     * listeners.
     */
    private class TreeSelectionUpdater
            implements java.io.Serializable, TreeSelectionListener {
        /**
         * Invoked by the <code>TreeSelectionModel</code> when the
         * selection changes.
         *
         * @param e the <code>TreeSelectionEvent</code> generated by the
         *		<code>TreeSelectionModel</code>
         */
        public void valueChanged(TreeSelectionEvent evt) {
            // Expand columns to match the selection
            switch (selectionModel.getSelectionCount()) {
                case 0 :
                    expandPath(new TreePath(treeModel.getRoot()));
                    updatePreviewColumn();
                    break;
                case 1 : {
                    TreePath selectionPath = selectionModel.getSelectionPath();
                    expandPath(selectionPath);
                    if (! treeModel.isLeaf(selectionPath.getLastPathComponent())) {
                        getColumnList(getListColumnCount() - 1).clearSelection();
                    }
                    updatePreviewColumn();
                    break;
                }
                default : {
                    TreePath leadSelectionPath = selectionModel.getLeadSelectionPath();
                    TreePath parentPath = leadSelectionPath.getParentPath();
                    expandPath(parentPath);
                    JList list = getColumnList(parentPath.getPathCount() - 1);
                    
                    TreePath[] selectionPaths = selectionModel.getSelectionPaths();
                    int[] indices = new int[selectionPaths.length];
                    int leadPathIndex = -1;
                    for (int i=0; i < selectionPaths.length; i++) {
                        indices[i] = treeModel.getIndexOfChild(parentPath.getLastPathComponent(), selectionPaths[i].getLastPathComponent());
                        if (selectionPaths[i].equals(leadSelectionPath)) {
                            leadPathIndex = i;
                        }
                    }
                    int swap = indices[leadPathIndex];
                    indices[leadPathIndex] = indices[indices.length - 1];
                    indices[indices.length - 1] = swap;
                    list.setSelectedIndices(indices);
                    updatePreviewColumn();
                    break;
                }
            }
            
            //validate();
            if (getParent() != null) {
                getParent().validate();
            }
            scrollRectToVisible(getComponent(getComponentCount() - 1).getBounds());
            getComponent(getComponentCount() - 1).repaint();
            
            
            // Propagate event to listeners of the JBrowser
            if(listenerList.getListenerCount(TreeSelectionListener.class) != 0) {
                TreeSelectionEvent newE;
                
                newE = (TreeSelectionEvent) evt.cloneWithSource(JBrowser.this);
                fireValueChanged(newE);
            }
        }
    } // End of class JBrowser.TreeSelectionModelListener
    /**
     * Propagates selection mode changes of the TreeSelectionModel to the
     * ListSelectionModel's of the columns.
     */
    private class SelectionModeUpdater
            implements java.io.Serializable, PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("selectionMode")) {
                int selectionMode;
                switch (selectionModel.getSelectionMode()) {
                    case TreeSelectionModel.CONTIGUOUS_TREE_SELECTION :
                        selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
                        break;
                    case TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION :
                        selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
                        break;
                    case TreeSelectionModel.SINGLE_TREE_SELECTION :
                    default :
                        selectionMode = ListSelectionModel.SINGLE_SELECTION ;
                        break;
                }
                
                for (int i=0; i < getListColumnCount(); i++) {
                    getColumnList(i).getSelectionModel().setSelectionMode(selectionMode);
                }
            }
        }
    } // End of class JBrowser.SelectionModeUpdater
    /**
     * This listener listens on mouse events that occur on the columns.
     * <br>
     * FIXME - this listener works only, when it receives listener events
     * <b>after</b> the ListSelectionListener of the ListUI of a column has
     * processed its events.
     */
    private class ColumnMouseListener extends MouseAdapter {
        public void mouseReleased(MouseEvent evt) {
            JList columnList = (JList) evt.getComponent();
            EventListener[] listeners = JBrowser.this.getListeners(MouseListener.class);
            if (listeners.length > 0) {
                int x = evt.getX();
                int y = evt.getY();
                Component c = columnList;
                while (c != JBrowser.this) {
                    x += c.getX();
                    y += c.getY();
                    c = c.getParent();
                }
                MouseEvent refiredEvent = new MouseEvent(
                        JBrowser.this, evt.getID(), evt.getWhen(), evt.getModifiers(),
                        x, y,
                        evt.getClickCount(), evt.isPopupTrigger() //, evt.getButton()
                        );
                for (int i=0; i < listeners.length; i++) {
                    ((MouseListener) listeners[i]).mouseReleased(refiredEvent);
                }
            }
            // selectionModel.
            updateExpandedState(columnList);
            //columnList.requestFocus();
        }
        private void updateExpandedState(JList columnList) {
            ColumnListModel columnModel = (ColumnListModel) columnList.getModel();
            TreePath columnPath = columnModel.path;
            int[] selectedIndices = columnList.getSelectedIndices();
            TreePath leadPath;
            if (selectedIndices.length == 0) {
                leadPath = columnModel.path;
                /*if (getListColumnCount() > 1) {
                    getColumnList(getListColumnCount() - 2).requestFocus();
                }*/
                selectionModel.setSelectionPath(leadPath);
            } else if (selectedIndices.length == 1) {
                leadPath = columnPath.pathByAddingChild(columnList.getSelectedValue());
                selectionModel.setSelectionPath(leadPath);
            } else {
                leadPath = columnPath.pathByAddingChild(columnModel.getElementAt(columnList.getLeadSelectionIndex()));
                TreePath[] paths = new TreePath[selectedIndices.length];
                int leadPathIndex = -1;
                for (int i=0; i < selectedIndices.length; i++) {
                    paths[i] = columnModel.path.pathByAddingChild(columnModel.getElementAt(selectedIndices[i]));
                    if (paths[i].equals(leadPath)) {
                        leadPathIndex = i;
                    }
                }
                if (leadPathIndex != -1) {
                    paths[leadPathIndex] = paths[paths.length - 1];
                    paths[paths.length - 1] = leadPath;
                }
                selectionModel.setSelectionPaths(paths);
            }
        }
        public void mouseClicked(MouseEvent evt) {
            JList columnList = (JList) evt.getComponent();
            EventListener[] listeners = JBrowser.this.getListeners(MouseListener.class);
            if (listeners.length > 0) {
                int x = evt.getX();
                int y = evt.getY();
                Component c = columnList;
                while (c != JBrowser.this) {
                    x += c.getX();
                    y += c.getY();
                    c = c.getParent();
                }
                MouseEvent refiredEvent = new MouseEvent(
                        JBrowser.this, evt.getID(), evt.getWhen(), evt.getModifiers(),
                        x, y,
                        evt.getClickCount(), evt.isPopupTrigger() //, evt.getButton()
                        );
                for (int i=0; i < listeners.length; i++) {
                    ((MouseListener) listeners[i]).mouseClicked(refiredEvent);
                }
            }
        }
        public void mousePressed(MouseEvent evt) {
            JList columnList = (JList) evt.getComponent();
            EventListener[] listeners = JBrowser.this.getListeners(MouseListener.class);
            if (listeners.length > 0) {
                int x = evt.getX();
                int y = evt.getY();
                Component c = columnList;
                while (c != JBrowser.this) {
                    x += c.getX();
                    y += c.getY();
                    c = c.getParent();
                }
                MouseEvent refiredEvent = new MouseEvent(
                        JBrowser.this, evt.getID(), evt.getWhen(), evt.getModifiers(),
                        x, y,
                        evt.getClickCount(), evt.isPopupTrigger() /*, evt.getButton()*/
                        );
                for (int i=0; i < listeners.length; i++) {
                    ((MouseListener) listeners[i]).mousePressed(refiredEvent);
                }
            }
            // Do not change the expanded state while the mouse is pressed.
            // We update it only on mouse released, to be more consistent with
            // the native file chooser.
            //updateExpandedState(columnList);
        }
    }
    /**
     * This listener listens on focus events that occur on the columns.
     */
    private class ColumnFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            putClientProperty("Quaqua.drawFocusBorder", Boolean.TRUE);
            repaintParentBorder();
        }
        public void focusLost(FocusEvent e) {
            putClientProperty("Quaqua.drawFocusBorder", Boolean.FALSE);
            repaintParentBorder();
        }
    }
    /**
     * This listener listens on key released events that occur on the columns.
     * <br>
     * FIXME - this listener works only, when it receives listener events
     * <b>after</b> the KeyListener of the ListUI of a column has
     * processed its events.
     */
    private class ColumnKeyListener implements KeyListener {
        public void keyReleased(KeyEvent evt) {
            JList columnList = (JList) evt.getComponent();
            ColumnListModel columnModel = (ColumnListModel) columnList.getModel();
            TreePath columnPath = columnModel.path;
            
            if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
                if (columnPath.getPathCount() > 1) {
                    evt.consume();
                    columnList.clearSelection();
                    JList parentColumnList = getColumnList(columnPath.getPathCount() - 2);
                    selectionModel.setSelectionPath(columnPath);
                    parentColumnList.requestFocus();
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                JList childColumnList =getColumnList(getListColumnCount() - 1);
                if (childColumnList.getSelectedIndex() == -1
                        && childColumnList.getModel().getSize() != 0) {
                    evt.consume();
                    childColumnList.setSelectedIndex(0);
                    selectionModel.setSelectionPath(((ColumnListModel) childColumnList.getModel()).path.pathByAddingChild(childColumnList.getSelectedValue()));
                }
                childColumnList.requestFocus();
            } else {
                int[] selectedIndices = columnList.getSelectedIndices();
                TreePath leadPath;
                if (selectedIndices.length == 0) {
                    leadPath = columnModel.path;
                    selectionModel.setSelectionPath(leadPath);
                    if (getListColumnCount() > 1) {
                        getColumnList(getListColumnCount() - 2).requestFocus();
                    }
                } else if (selectedIndices.length == 1) {
                    leadPath = columnPath.pathByAddingChild(columnList.getSelectedValue());
                    selectionModel.setSelectionPath(leadPath);
                } else {
                    leadPath = columnPath.pathByAddingChild(columnModel.getElementAt(columnList.getLeadSelectionIndex()));
                    TreePath[] paths = new TreePath[selectedIndices.length];
                    int leadPathIndex = -1;
                    for (int i=0; i < selectedIndices.length; i++) {
                        paths[i] = columnModel.path.pathByAddingChild(columnModel.getElementAt(selectedIndices[i]));
                        if (paths[i].equals(leadPath)) {
                            leadPathIndex = i;
                        }
                    }
                    paths[leadPathIndex] = paths[paths.length - 1];
                    paths[paths.length - 1] = leadPath;
                    
                    selectionModel.setSelectionPaths(paths);
                }
            }
            if (! evt.isConsumed()) {
               KeyListener[] kl = getKeyListeners();
               for (int i=0; i < kl.length; i++) {
                   kl[i].keyReleased(evt);
               }
            }
        }

        public void keyTyped(KeyEvent evt) {
            if (! evt.isConsumed()) {
               KeyListener[] kl = getKeyListeners();
               for (int i=0; i < kl.length; i++) {
                   kl[i].keyTyped(evt);
               }
            }
        }

        public void keyPressed(KeyEvent evt) {
            if (! evt.isConsumed()) {
               KeyListener[] kl = getKeyListeners();
               for (int i=0; i < kl.length; i++) {
                   kl[i].keyPressed(evt);
               }
            }
        }
    }
    /**
     * This is a copy of DefaultListSelectionModel.
     * I wish I could subclass it, because the only method which is differs
     * to DefaultListSelectionModel is method insertIndexInterval(...).
     * Unfortunately the instance variable "value" can not be accessed
     * by subclasses.
     */
    private static class ColumnSelectionModel implements ListSelectionModel, Cloneable, java.io.Serializable {
        private static final int MIN = -1;
        private static final int MAX = Integer.MAX_VALUE;
        private int selectionMode = MULTIPLE_INTERVAL_SELECTION;
        private int minIndex = MAX;
        private int maxIndex = MIN;
        private int anchorIndex = -1;
        private int leadIndex = -1;
        private int firstAdjustedIndex = MAX;
        private int lastAdjustedIndex = MIN;
        private boolean isAdjusting = false;
        
        private int firstChangedIndex = MAX;
        private int lastChangedIndex = MIN;
        
        private BitSet value = new BitSet(32);
        protected EventListenerList listenerList = new EventListenerList();
        
        protected boolean leadAnchorNotificationEnabled = true;
        
        // implements javax.swing.ListSelectionModel
        public int getMinSelectionIndex() { return isSelectionEmpty() ? -1 : minIndex; }
        
        // implements javax.swing.ListSelectionModel
        public int getMaxSelectionIndex() { return maxIndex; }
        
        // implements javax.swing.ListSelectionModel
        public boolean getValueIsAdjusting() { return isAdjusting; }
        
        // implements javax.swing.ListSelectionModel
        /**
         * Returns the selection mode.
         * @return  one of the these values:
         * <ul>
         * <li>SINGLE_SELECTION
         * <li>SINGLE_INTERVAL_SELECTION
         * <li>MULTIPLE_INTERVAL_SELECTION
         * </ul>
         * @see #getSelectionMode
         */
        public int getSelectionMode() { return selectionMode; }
        
        // implements javax.swing.ListSelectionModel
        /**
         * Sets the selection mode.  The default is
         * MULTIPLE_INTERVAL_SELECTION.
         * @param selectionMode  one of three values:
         * <ul>
         * <li>SINGLE_SELECTION
         * <li>SINGLE_INTERVAL_SELECTION
         * <li>MULTIPLE_INTERVAL_SELECTION
         * </ul>
         * @exception IllegalArgumentException  if <code>selectionMode</code>
         *		is not one of the legal values shown above
         * @see #setSelectionMode
         */
        public void setSelectionMode(int selectionMode) {
            switch (selectionMode) {
                case SINGLE_SELECTION:
                case SINGLE_INTERVAL_SELECTION:
                case MULTIPLE_INTERVAL_SELECTION:
                    this.selectionMode = selectionMode;
                    break;
                default:
                    throw new IllegalArgumentException("invalid selectionMode");
            }
        }
        
        // implements javax.swing.ListSelectionModel
        public boolean isSelectedIndex(int index) {
            return ((index < minIndex) || (index > maxIndex)) ? false : value.get(index);
        }
        
        // implements javax.swing.ListSelectionModel
        public boolean isSelectionEmpty() {
            return (minIndex > maxIndex);
        }
        
        // implements javax.swing.ListSelectionModel
        public void addListSelectionListener(ListSelectionListener l) {
            listenerList.add(ListSelectionListener.class, l);
        }
        
        // implements javax.swing.ListSelectionModel
        public void removeListSelectionListener(ListSelectionListener l) {
            listenerList.remove(ListSelectionListener.class, l);
        }
        
        /**
         * Returns an array of all the list selection listeners
         * registered on this <code>DefaultListSelectionModel</code>.
         *
         * @return all of this model's <code>ListSelectionListener</code>s
         *         or an empty
         *         array if no list selection listeners are currently registered
         *
         * @see #addListSelectionListener
         * @see #removeListSelectionListener
         *
         * @since 1.4
         */
        public ListSelectionListener[] getListSelectionListeners() {
            return (ListSelectionListener[])listenerList.getListeners(
                    ListSelectionListener.class);
        }
        
        /**
         * Notifies listeners that we have ended a series of adjustments.
         */
        protected void fireValueChanged(boolean isAdjusting) {
            if (lastChangedIndex == MIN) {
                return;
            }
        /* Change the values before sending the event to the
         * listeners in case the event causes a listener to make
         * another change to the selection.
         */
            int oldFirstChangedIndex = firstChangedIndex;
            int oldLastChangedIndex = lastChangedIndex;
            firstChangedIndex = MAX;
            lastChangedIndex = MIN;
            fireValueChanged(oldFirstChangedIndex, oldLastChangedIndex, isAdjusting);
        }
        
        
        /**
         * Notifies <code>ListSelectionListeners</code> that the value
         * of the selection, in the closed interval <code>firstIndex</code>,
         * <code>lastIndex</code>, has changed.
         */
        protected void fireValueChanged(int firstIndex, int lastIndex) {
            fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
        }
        
        /**
         * @param firstIndex the first index in the interval
         * @param lastIndex the last index in the interval
         * @param isAdjusting true if this is the final change in a series of
         *		adjustments
         * @see EventListenerList
         */
        protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
            Object[] listeners = listenerList.getListenerList();
            ListSelectionEvent e = null;
            
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == ListSelectionListener.class) {
                    if (e == null) {
                        e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
                    }
                    ((ListSelectionListener)listeners[i+1]).valueChanged(e);
                }
            }
        }
        
        private void fireValueChanged() {
            if (lastAdjustedIndex == MIN) {
                return;
            }
        /* If getValueAdjusting() is true, (eg. during a drag opereration)
         * record the bounds of the changes so that, when the drag finishes (and
         * setValueAdjusting(false) is called) we can post a single event
         * with bounds covering all of these individual adjustments.
         */
            if (getValueIsAdjusting()) {
                firstChangedIndex = Math.min(firstChangedIndex, firstAdjustedIndex);
                lastChangedIndex = Math.max(lastChangedIndex, lastAdjustedIndex);
            }
        /* Change the values before sending the event to the
         * listeners in case the event causes a listener to make
         * another change to the selection.
         */
            int oldFirstAdjustedIndex = firstAdjustedIndex;
            int oldLastAdjustedIndex = lastAdjustedIndex;
            firstAdjustedIndex = MAX;
            lastAdjustedIndex = MIN;
            
            fireValueChanged(oldFirstAdjustedIndex, oldLastAdjustedIndex);
        }
        
        /**
         * Returns an array of all the objects currently registered as
         * <code><em>Foo</em>Listener</code>s
         * upon this model.
         * <code><em>Foo</em>Listener</code>s
         * are registered using the <code>add<em>Foo</em>Listener</code> method.
         * <p>
         * You can specify the <code>listenerType</code> argument
         * with a class literal, such as <code><em>Foo</em>Listener.class</code>.
         * For example, you can query a <code>DefaultListSelectionModel</code>
         * instance <code>m</code>
         * for its list selection listeners
         * with the following code:
         *
         * <pre>ListSelectionListener[] lsls = (ListSelectionListener[])(m.getListeners(ListSelectionListener.class));</pre>
         *
         * If no such listeners exist,
         * this method returns an empty array.
         *
         * @param listenerType  the type of listeners requested;
         *          this parameter should specify an interface
         *          that descends from <code>java.util.EventListener</code>
         * @return an array of all objects registered as
         *          <code><em>Foo</em>Listener</code>s
         *          on this model,
         *          or an empty array if no such
         *          listeners have been added
         * @exception ClassCastException if <code>listenerType</code> doesn't
         *          specify a class or interface that implements
         *          <code>java.util.EventListener</code>
         *
         * @see #getListSelectionListeners
         *
         * @since 1.3
         */
        public EventListener[] getListeners(Class listenerType) {
            return listenerList.getListeners(listenerType);
        }
        
        // Updates first and last change indices
        private void markAsDirty(int r) {
            firstAdjustedIndex = Math.min(firstAdjustedIndex, r);
            lastAdjustedIndex =  Math.max(lastAdjustedIndex, r);
        }
        
        // Sets the state at this index and update all relevant state.
        private void set(int r) {
            if (value.get(r)) {
                return;
            }
            value.set(r);
            markAsDirty(r);
            
            // Update minimum and maximum indices
            minIndex = Math.min(minIndex, r);
            maxIndex = Math.max(maxIndex, r);
        }
        
        // Clears the state at this index and update all relevant state.
        private void clear(int r) {
            if (!value.get(r)) {
                return;
            }
            value.clear(r);
            markAsDirty(r);
            
            // Update minimum and maximum indices
        /*
           If (r > minIndex) the minimum has not changed.
           The case (r < minIndex) is not possible because r'th value was set.
           We only need to check for the case when lowest entry has been cleared,
           and in this case we need to search for the first value set above it.
         */
            if (r == minIndex) {
                for(minIndex = minIndex + 1; minIndex <= maxIndex; minIndex++) {
                    if (value.get(minIndex)) {
                        break;
                    }
                }
            }
        /*
           If (r < maxIndex) the maximum has not changed.
           The case (r > maxIndex) is not possible because r'th value was set.
           We only need to check for the case when highest entry has been cleared,
           and in this case we need to search for the first value set below it.
         */
            if (r == maxIndex) {
                for(maxIndex = maxIndex - 1; minIndex <= maxIndex; maxIndex--) {
                    if (value.get(maxIndex)) {
                        break;
                    }
                }
            }
        /* Performance note: This method is called from inside a loop in
           changeSelection() but we will only iterate in the loops
           above on the basis of one iteration per deselected cell - in total.
           Ie. the next time this method is called the work of the previous
           deselection will not be repeated.
         
           We also don't need to worry about the case when the min and max
           values are in their unassigned states. This cannot happen because
           this method's initial check ensures that the selection was not empty
           and therefore that the minIndex and maxIndex had 'real' values.
         
           If we have cleared the whole selection, set the minIndex and maxIndex
           to their cannonical values so that the next set command always works
           just by using Math.min and Math.max.
         */
            if (isSelectionEmpty()) {
                minIndex = MAX;
                maxIndex = MIN;
            }
        }
        
        /**
         * Sets the value of the leadAnchorNotificationEnabled flag.
         * @see		#isLeadAnchorNotificationEnabled()
         */
        public void setLeadAnchorNotificationEnabled(boolean flag) {
            leadAnchorNotificationEnabled = flag;
        }
        
        /**
         * Returns the value of the <code>leadAnchorNotificationEnabled</code> flag.
         * When <code>leadAnchorNotificationEnabled</code> is true the model
         * generates notification events with bounds that cover all the changes to
         * the selection plus the changes to the lead and anchor indices.
         * Setting the flag to false causes a narrowing of the event's bounds to
         * include only the elements that have been selected or deselected since
         * the last change. Either way, the model continues to maintain the lead
         * and anchor variables internally. The default is true.
         * @return 	the value of the <code>leadAnchorNotificationEnabled</code> flag
         * @see		#setLeadAnchorNotificationEnabled(boolean)
         */
        public boolean isLeadAnchorNotificationEnabled() {
            return leadAnchorNotificationEnabled;
        }
        
        private void updateLeadAnchorIndices(int anchorIndex, int leadIndex) {
            if (leadAnchorNotificationEnabled) {
                if (this.anchorIndex != anchorIndex) {
                    if (this.anchorIndex != -1) { // The unassigned state.
                        markAsDirty(this.anchorIndex);
                    }
                    markAsDirty(anchorIndex);
                }
                
                if (this.leadIndex != leadIndex) {
                    if (this.leadIndex != -1) { // The unassigned state.
                        markAsDirty(this.leadIndex);
                    }
                    markAsDirty(leadIndex);
                }
            }
            this.anchorIndex = anchorIndex;
            this.leadIndex = leadIndex;
        }
        
        private boolean contains(int a, int b, int i) {
            return (i >= a) && (i <= b);
        }
        
        private void changeSelection(int clearMin, int clearMax,
                int setMin, int setMax, boolean clearFirst) {
            for(int i = Math.min(setMin, clearMin); i <= Math.max(setMax, clearMax); i++) {
                
                boolean shouldClear = contains(clearMin, clearMax, i);
                boolean shouldSet = contains(setMin, setMax, i);
                
                if (shouldSet && shouldClear) {
                    if (clearFirst) {
                        shouldClear = false;
                    } else {
                        shouldSet = false;
                    }
                }
                
                if (shouldSet) {
                    set(i);
                }
                if (shouldClear) {
                    clear(i);
                }
            }
            fireValueChanged();
        }
        
        /**   Change the selection with the effect of first clearing the values
         *   in the inclusive range [clearMin, clearMax] then setting the values
         *   in the inclusive range [setMin, setMax]. Do this in one pass so
         *   that no values are cleared if they would later be set.
         */
        private void changeSelection(int clearMin, int clearMax, int setMin, int setMax) {
            changeSelection(clearMin, clearMax, setMin, setMax, true);
        }
        
        // implements javax.swing.ListSelectionModel
        public void clearSelection() {
            removeSelectionInterval(minIndex, maxIndex);
        }
        
        // implements javax.swing.ListSelectionModel
        public void setSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }
            
            if (getSelectionMode() == SINGLE_SELECTION) {
                index0 = index1;
            }
            
            updateLeadAnchorIndices(index0, index1);
            
            int clearMin = minIndex;
            int clearMax = maxIndex;
            int setMin = Math.min(index0, index1);
            int setMax = Math.max(index0, index1);
            
            changeSelection(clearMin, clearMax, setMin, setMax);
        }
        
        // implements javax.swing.ListSelectionModel
        public void addSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }
            
            if (getSelectionMode() != MULTIPLE_INTERVAL_SELECTION) {
                setSelectionInterval(index0, index1);
                return;
            }
            
            updateLeadAnchorIndices(index0, index1);
            
            int clearMin = MAX;
            int clearMax = MIN;
            int setMin = Math.min(index0, index1);
            int setMax = Math.max(index0, index1);
            
            changeSelection(clearMin, clearMax, setMin, setMax);
        }
        
        
        // implements javax.swing.ListSelectionModel
        public void removeSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }
            
            updateLeadAnchorIndices(index0, index1);
            
            int clearMin = Math.min(index0, index1);
            int clearMax = Math.max(index0, index1);
            int setMin = MAX;
            int setMax = MIN;
            
            // If the removal would produce to two disjoint selections in a mode
            // that only allows one, extend the removal to the end of the selection.
            if (getSelectionMode() != MULTIPLE_INTERVAL_SELECTION &&
                    clearMin > minIndex && clearMax < maxIndex) {
                clearMax = maxIndex;
            }
            
            changeSelection(clearMin, clearMax, setMin, setMax);
        }
        
        private void setState(int index, boolean state) {
            if (state) {
                set(index);
            } else {
                clear(index);
            }
        }
        
        /**
         * THIS METHOD DIFFERS FROM DefaultListSelectionModel:
         * <p>
         * Insert length indices beginning before/after index. Even the value
         * at index is itself selected all of the newly inserted items
         * are marked as unselected.
         * This method is typically called to sync the selection model with a
         * corresponding change in the data model.
         */
        public void insertIndexInterval(int index, int length, boolean before) {
        /* The first new index will appear at insMinIndex and the last
         * one will appear at insMaxIndex
         */
            int insMinIndex = (before) ? index : index + 1;
            int insMaxIndex = (insMinIndex + length) - 1;
            
        /* Right shift the entire bitset by length, beginning with
         * index-1 if before is true, index+1 if it's false (i.e. with
         * insMinIndex).
         */
            for(int i = maxIndex; i >= insMinIndex; i--) {
                setState(i + length, value.get(i));
            }
            
            
        /* Initialize the newly inserted indices.
         */
            // This is the only difference to DefaultListSelectionModel:
            // We _never_ select newly inserted values.
            //boolean setInsertedValues = ((getSelectionMode() == SINGLE_SELECTION) ? false : value.get(index));
            boolean wasSelected = value.get(index);
            boolean setInsertedValues = false;
            for(int i = insMinIndex; i <= insMaxIndex; i++) {
                setState(i, setInsertedValues);
            }
            
            // Move the leadIndex
            if (index <= leadIndex) {
                leadIndex += length;
            }
            
            
            fireValueChanged();
        }
        
        
        /**
         * THIS METHOD DIFFERS FROM DefaultListSelectionModel:
         * <p>
         * Remove the indices in the interval index0,index1 (inclusive) from
         * the selection model.  This is typically called to sync the selection
         * model width a corresponding change in the data model.  Note
         * that (as always) index0 need not be <= index1.
         */
        public void removeIndexInterval(int index0, int index1) {
            int rmMinIndex = Math.min(index0, index1);
            int rmMaxIndex = Math.max(index0, index1);
            int gapLength = (rmMaxIndex - rmMinIndex) + 1;
            
        /* Shift the entire bitset to the left to close the index0, index1
         * gap.
         */
            for(int i = rmMinIndex; i <= maxIndex; i++) {
                setState(i, value.get(i + gapLength));
            }
            
            /* Shift the lead index */
            if (leadIndex >= index1) {
                leadIndex = leadIndex - (index1 - index0) - 1;
            } else if (leadIndex >= index0) {
                leadIndex = index0;
            }
            
            fireValueChanged();
        }
        
        
        // implements javax.swing.ListSelectionModel
        public void setValueIsAdjusting(boolean isAdjusting) {
            if (isAdjusting != this.isAdjusting) {
                this.isAdjusting = isAdjusting;
                this.fireValueChanged(isAdjusting);
            }
        }
        
        
        /**
         * Returns a string that displays and identifies this
         * object's properties.
         *
         * @return a <code>String</code> representation of this object
         */
        public String toString() {
            String s =  ((getValueIsAdjusting()) ? "~" : "=") + value.toString();
            return getClass().getName() + " " + Integer.toString(hashCode()) + " " + s;
        }
        
        /**
         * Returns a clone of this selection model with the same selection.
         * <code>listenerLists</code> are not duplicated.
         *
         * @exception CloneNotSupportedException if the selection model does not
         *    both (a) implement the Cloneable interface and (b) define a
         *    <code>clone</code> method.
         */
        public Object clone() throws CloneNotSupportedException {
            ColumnSelectionModel clone = (ColumnSelectionModel)super.clone();
            clone.value = (BitSet)value.clone();
            clone.listenerList = new EventListenerList();
            return clone;
        }
        
        // implements javax.swing.ListSelectionModel
        public int getAnchorSelectionIndex() {
            return anchorIndex;
        }
        
        // implements javax.swing.ListSelectionModel
        public int getLeadSelectionIndex() {
            return leadIndex;
        }
        
        /**
         * Set the anchor selection index, leaving all selection values unchanged.
         * If leadAnchorNotificationEnabled is true, send a notification covering
         * the old and new anchor cells.
         *
         * @see #getAnchorSelectionIndex
         * @see #setLeadSelectionIndex
         */
        public void setAnchorSelectionIndex(int anchorIndex) {
            updateLeadAnchorIndices(anchorIndex, this.leadIndex);
            this.anchorIndex = anchorIndex;
            fireValueChanged();
        }
        
        /**
         * Sets the lead selection index, ensuring that values between the
         * anchor and the new lead are either all selected or all deselected.
         * If the value at the anchor index is selected, first clear all the
         * values in the range [anchor, oldLeadIndex], then select all the values
         * values in the range [anchor, newLeadIndex], where oldLeadIndex is the old
         * leadIndex and newLeadIndex is the new one.
         * <p>
         * If the value at the anchor index is not selected, do the same thing in
         * reverse selecting values in the old range and deslecting values in the
         * new one.
         * <p>
         * Generate a single event for this change and notify all listeners.
         * For the purposes of generating minimal bounds in this event, do the
         * operation in a single pass; that way the first and last index inside the
         * ListSelectionEvent that is broadcast will refer to cells that actually
         * changed value because of this method. If, instead, this operation were
         * done in two steps the effect on the selection state would be the same
         * but two events would be generated and the bounds around the changed
         * values would be wider, including cells that had been first cleared only
         * to later be set.
         * <p>
         * This method can be used in the <code>mouseDragged</code> method
         * of a UI class to extend a selection.
         *
         * @see #getLeadSelectionIndex
         * @see #setAnchorSelectionIndex
         */
        public void setLeadSelectionIndex(int leadIndex) {
            int anchorIndex = this.anchorIndex;
            
            if ((anchorIndex == -1) || (leadIndex == -1)) {
                return;
            }
            
            if (this.leadIndex == -1) {
                this.leadIndex = leadIndex;
            }
            
            boolean shouldSelect = value.get(this.anchorIndex);
            
            if (getSelectionMode() == SINGLE_SELECTION) {
                anchorIndex = leadIndex;
                shouldSelect = true;
            }
            
            int oldMin = Math.min(this.anchorIndex, this.leadIndex);
            int oldMax = Math.max(this.anchorIndex, this.leadIndex);
            int newMin = Math.min(anchorIndex, leadIndex);
            int newMax = Math.max(anchorIndex, leadIndex);
            
            updateLeadAnchorIndices(anchorIndex, leadIndex);
            
            if (shouldSelect) {
                changeSelection(oldMin, oldMax, newMin, newMax);
            } else {
                changeSelection(newMin, newMax, oldMin, oldMax, false);
            }
        }
        
        
    } // End of class ColumnSelectionModel.
    /**
     * Handles changes of the tree root.
     */
    private class TreeRootHandler implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
        }
        
        public void treeNodesInserted(TreeModelEvent e) {
        }
        
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        
        public void treeStructureChanged(TreeModelEvent e) {
            // Detect if the whole tree structure has changed.
            if (e.getPath().length == 1) {
                
                // Remove all columns
                for (int i=getListColumnCount() - 1; i >= 0; i--) {
                    removeColumn();
                }
                expandedPath = null;
                expandRoot();
                setSelectionPath(e.getTreePath());
            }
        }
    } // End of class TreeRootHandler
}
