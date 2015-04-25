/*
 * @(#)LeopardFileRenderer.java  1.0  2007-11-18
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.leopard.filechooser;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.filechooser.*;

/**
 * The FileRenderer is used to render a file in the JBrowser of one of the
 * Quaqua FileChooserUI's.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2007-11-18 Created.
 */
public class LeopardFileRenderer extends JComponent implements ListCellRenderer  {
    private Color labelForeground, labelDisabledForeground;
    private Icon selectedExpandingIcon;
    private Icon selectedExpandedIcon;
    private Icon expandingIcon;
    private Icon expandedIcon;
    private Icon emptyIcon;
    private JFileChooser fileChooser;
    
    
    private Icon icon;
    private String text;
    private Icon arrowIcon;
    private Color labelColor, labelBrightColor;
    private boolean isSelected;
    private boolean isGrayed;
    
    public LeopardFileRenderer(JFileChooser fileChooser,
    Icon expandingIcon, Icon expandedIcon,
    Icon selectedExpandingIcon, Icon selectedExpandedIcon
    ) {
        this.fileChooser = fileChooser;
        this.expandingIcon = expandingIcon;
        this.expandedIcon = expandedIcon;
        this.selectedExpandingIcon = selectedExpandingIcon;
        this.selectedExpandedIcon = selectedExpandedIcon;
        emptyIcon = new Icon() {
            public int getIconWidth() {
                return LeopardFileRenderer.this.expandedIcon.getIconWidth();
            }
            public int getIconHeight() {
                return LeopardFileRenderer.this.expandedIcon.getIconHeight();
            }
            public void paintIcon(Component c, Graphics g, int x, int y) {
            }
            
        };
        
        labelForeground = UIManager.getColor("Label.foreground");
        labelDisabledForeground = UIManager.getColor("Label.disabledForeground");
        setOpaque(true);
    }
    
    
    // Overridden for performance reasons.
    public void validate() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {}
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {}
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {}
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {}
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
    
    
    public Component getListCellRendererComponent(JList list, Object value,
    int index, boolean isSelected,
    boolean cellHasFocus) {
        Integer viewTooltipRow = (Integer) list.getClientProperty("ViewTooltips.row");
        
        FileInfo info = (FileInfo) value;
        File file = info.getFile();
        
        boolean isGrayed =
                (fileChooser.getDialogType() == JFileChooser.SAVE_DIALOG && ! info.isTraversable()) ||
                ! info.isAcceptable();
        
        labelColor = Files.getLabelColor(info.getFileLabel(), (isGrayed) ? 2 : 0);
        labelBrightColor = Files.getLabelColor(info.getFileLabel(), (isGrayed) ? 3 : 1);
        
        this.isSelected = isSelected && ! isGrayed;
        if (this.isSelected) {
            if (list.hasFocus() && QuaquaUtilities.isOnActiveWindow(list)) {
                setBackground(UIManager.getColor("Browser.selectionBackground"));
                setForeground(UIManager.getColor("Browser.selectionForeground"));
            } else {
                setBackground(UIManager.getColor("Browser.inactiveSelectionBackground"));
                setForeground(UIManager.getColor("Browser.inactiveSelectionForeground"));
            }
        } else {
            //setBackground((labelColor == null) ? list.getBackground() : labelColor);
            setBackground(list.getBackground());
            setForeground((isGrayed) ? labelDisabledForeground : labelForeground);
        }
        if (this.isSelected && labelColor == null) {
            arrowIcon = (info.isValidating()) ? selectedExpandingIcon : selectedExpandedIcon;
        } else {
            arrowIcon = (info.isValidating()) ? expandingIcon : expandedIcon;
        }
        
        text = info.getUserName();
        icon = info.getIcon();

        if (! info.isTraversable()) {
            arrowIcon = (labelColor == null) ? null : emptyIcon;
        }
        
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setBorder((cellHasFocus) ? 
            UIManager.getBorder("FileChooser.browserFocusCellHighlightBorder") : 
            UIManager.getBorder("FileChooser.browserCellBorder") 
            );
        
        return this;
    }
    
    protected void paintComponent(Graphics gr) {
        Object oldHints = QuaquaUtilities.beginGraphics((Graphics2D) gr);
        Graphics2D g = (Graphics2D) gr;
        int width = getWidth();
        
        int height = getHeight();
        Insets insets = getInsets();
        boolean isUseArrow = arrowIcon != null;
        
        resetRects();
        
        viewRect.setBounds(0, 0, width, height);
        viewRect.x += insets.left;
        viewRect.y += insets.top;
        viewRect.width -= insets.right + viewRect.x;
        viewRect.height -= insets.bottom + viewRect.y;
        
        Font textFont = getFont();
        g.setFont(textFont);
        FontMetrics textFM = g.getFontMetrics(textFont);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, width, height);
        }
        boolean isLeftToRight = QuaquaUtilities.isLeftToRight(this);
        
        String clippedText = layoutRenderer(
        textFM, text,
        icon, arrowIcon,
        viewRect, iconRect, textRect, arrowIconRect,
        text == null ? 0 : textIconGap, textIconGap
        );
        
        if (labelColor != null) {
            if (isSelected) {
                r.y = viewRect.y;
                r.width = r.height = viewRect.height - 1;
                r.x = arrowIconRect.x - (arrowIconRect.width - r.width) / 2;
                //r.x = viewRect.width - r.width;
                //g.fillOval(r.x, r.y, r.width, r.height);
            } else {
                r.x = textRect.x - textIconGap + 1;
                r.y = viewRect.y;
                r.width = viewRect.width - r.x;
                r.height = viewRect.height;
            }
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(r.x, r.y, labelBrightColor, r.x, r.y + r.height, labelColor));
            //g.setColor(labelColor);
            g.fillRoundRect(r.x, r.y, r.width, r.height, r.height, r.height);
        }
        
        
        if (icon != null) {
            icon.paintIcon(this, g, iconRect.x, iconRect.y);
        }
        
        
        
        if (clippedText != null && ! clippedText.equals("")) {
            g.setColor(getForeground());
            g.drawString(clippedText, textRect.x, textRect.y + textFM.getAscent());
        }
        
        if (arrowIcon != null) {
            arrowIcon.paintIcon(this, g, arrowIconRect.x, arrowIconRect.y);
        }
        
        QuaquaUtilities.endGraphics((Graphics2D) g, oldHints);
    }
    
    
    /**
     * The following variables are used for layouting the renderer.
     * This variables are static, because FileRenderer is always called
     * from the EventDispatcherThread, and because we do not use them in a
     * reentrant context, where a FileRenderer instance enters a method of
     * anonther FileRenderer instance.
     */
    private static final Rectangle zeroRect = new Rectangle(0,0,0,0);
    private static Rectangle iconRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle arrowIconRect = new Rectangle();
    private static Rectangle viewRect = new Rectangle();
    /** r is used in getPreferredSize and in paintComponent. It must not be
     * used in any method called by one of these.
     */
    private static Rectangle r = new Rectangle();
    
    private final static int textIconGap = 5;
    
    private void resetRects() {
        iconRect.setBounds(zeroRect);
        textRect.setBounds(zeroRect);
        arrowIconRect.setBounds(zeroRect);
        viewRect.setBounds(0, 0, 32767, 32767);
        r.setBounds(zeroRect);
    }
    
    public Dimension getPreferredSize() {
        Font textFont = getFont();
        FontMetrics textFM = getFontMetrics(textFont);
        
        resetRects();
        
        layoutRenderer(
        textFM, text,
        icon, arrowIcon,
        viewRect,
        iconRect, textRect,
        arrowIconRect, text == null ? 0 : textIconGap, textIconGap
        );
        
        r.setBounds(textRect);
        r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width,
        iconRect.height, r);
        
        boolean isUseArrow = arrowIcon != null;
        if (isUseArrow) {
            r.width += arrowIconRect.width;
        }
        
        Insets insets = getInsets();
        if (insets != null) {
            r.width += insets.left + insets.right;
            r.height += insets.top + insets.bottom;
        }
        
        return r.getSize();
    }
    
    /**
     * Layouts the components of the renderer.
     */
    private String layoutRenderer(
    FontMetrics textFM, String text,
    Icon icon, Icon arrowIcon,
    Rectangle viewRect, Rectangle iconRect,
    Rectangle textRect,
    Rectangle arrowIconRect,
    int textIconGap, int textArrowIconGap
    ) {
        
        boolean isUseArrow = arrowIcon != null;
        
        if (isUseArrow) {
            arrowIconRect.width = arrowIcon.getIconWidth();
            arrowIconRect.height = arrowIcon.getIconHeight();
            viewRect.width -= arrowIconRect.width + textIconGap;
        }
        
        text = QuaquaUtilities.layoutCompoundLabel(
        this, textFM, text,
        icon, SwingConstants.TOP, SwingConstants.LEFT,
        SwingConstants.CENTER, SwingConstants.RIGHT,
        viewRect, iconRect, textRect,
        textIconGap
        );
        
        
        if (isUseArrow) {
            viewRect.width += arrowIconRect.width + textIconGap;
        }
        
        Rectangle labelRect = iconRect.union(textRect);
        
        if (isUseArrow) {
            arrowIconRect.x = viewRect.width - arrowIconRect.width;
            arrowIconRect.y = (viewRect.y + labelRect.height / 2 - arrowIconRect.height / 2);
        }
        
        if (! QuaquaUtilities.isLeftToRight(this)) {
            int width = viewRect.width;
            iconRect.x = width - (iconRect.x + iconRect.width);
            textRect.x = width - (textRect.x + textRect.width);
            arrowIconRect.x = width - (arrowIconRect.x + arrowIconRect.width);
        }
        
        return text;
    }
}