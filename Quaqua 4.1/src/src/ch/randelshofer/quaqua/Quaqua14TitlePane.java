/*
 * @(#)Quaqua14TitlePane.java  2.0  2007-11-01
 *
 * Copyright (c) 2006-2007 Werner Randelshofer
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

import ch.randelshofer.quaqua.util.TextureColor;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.util.Locale;
import java.awt.geom.*;

/**
 * Quaqua14TitlePane.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2007-11-01 Rewritten for Leopard support.
 * <br>1.0.1 2006-06-11 Minor tweaking.
 * <br>1.0 February 12, 2006 Created.
 */
public class Quaqua14TitlePane extends JComponent {
    private static final Border handyEmptyBorder = new EmptyBorder(0,0,0,0);
    private static final int IMAGE_HEIGHT = 16;
    private static final int IMAGE_WIDTH = 16;
    
    /**
     * PropertyChangeListener added to the Window.
     */
    private PropertyChangeListener windowPropertyListener;
    /**
     * PropertyChangeListener added to the JRootPane.
     */
    private PropertyChangeListener rootPropertyListener;
    
    /**
     * JMenuBar, typically renders the system menu items.
     */
    private JMenuBar menuBar;
    /**
     * Action used to close the Window.
     */
    private Action closeAction;
    
    /**
     * Action used to iconify the Frame.
     */
    private Action iconifyAction;
    
    /**
     * Action to restore the Frame size.
     */
    private Action restoreAction;
    
    /**
     * Action to restore the Frame size.
     */
    private Action maximizeAction;
    
    /**
     * Button used to maximize or restore the Frame.
     */
    private JButton toggleButton;
    
    /**
     * Button used to maximize or restore the Frame.
     */
    private JButton iconifyButton;
    
    /**
     * Button used to maximize or restore the Frame.
     */
    private JButton closeButton;
    
    /**
     * Icon used for toggleButton when window is normal size.
     */
    private Icon maximizeIcon;
    
    /**
     * Icon used for toggleButton when window is maximized.
     */
    private Icon minimizeIcon;
    
    /**
     * Listens for changes in the state of the Window listener to update
     * the state of the widgets.
     */
    private WindowListener windowListener;
    
    /**
     * Window we're currently in.
     */
    private Window window;
    
    /**
     * JRootPane rendering for.
     */
    private JRootPane rootPane;
    
    /**
     * Room remaining in title for bumps.
     */
    private int buttonsWidth;
    
    /**
     * Buffered Frame.state property. As state isn't bound, this is kept
     * to determine when to avoid updating widgets.
     */
    private int state;
    
    /**
     * Quaqua14RootPaneUI that created us.
     */
    private Quaqua14RootPaneUI rootPaneUI;
    
    
    // Colors
    private Color inactiveBackground = UIManager.getColor("inactiveCaption");
    private Color inactiveForeground = UIManager.getColor("inactiveCaptionText");
    private Color inactiveShadow = new Color(0xb3b3b3);
    private Color activeBackground = null;
    private Color activeForeground = null;
    private Color activeShadow = null;
    
    /* Components used for "arming" the window buttons.
     */
    private JComponent armer1, armer2;
    
    public Quaqua14TitlePane(JRootPane root, Quaqua14RootPaneUI ui) {
        this.rootPane = root;
        rootPaneUI = ui;
        
        state = -1;
        
        rootPropertyListener = createRootPropertyChangeListener();
        rootPane.addPropertyChangeListener(rootPropertyListener);
        
        installSubcomponents();
        updateIconsAndTextures();
        installDefaults();
        setLayout(createLayout());
    }
    
    /**
     * Uninstalls the necessary state.
     */
    private void uninstall() {
        uninstallListeners();
        window = null;
        removeAll();
    }
    
    /**
     * Installs the necessary listeners.
     */
    private void installListeners() {
        if (window != null) {
            windowListener = createWindowListener();
            window.addWindowListener(windowListener);
            windowPropertyListener = createWindowPropertyChangeListener();
            window.addPropertyChangeListener(windowPropertyListener);
        }
    }
    
    /**
     * Uninstalls the necessary listeners.
     */
    private void uninstallListeners() {
        if (window != null) {
            window.removeWindowListener(windowListener);
            window.removePropertyChangeListener(windowPropertyListener);
        }
    }
    
    /**
     * Returns the <code>WindowListener</code> to add to the
     * <code>Window</code>.
     */
    private WindowListener createWindowListener() {
        return new WindowHandler();
    }
    
    /**
     * Returns the <code>PropertyChangeListener</code> to install on
     * the <code>Window</code>.
     */
    private PropertyChangeListener createWindowPropertyChangeListener() {
        return new WindowPropertyHandler();
    }
    /**
     * Returns the <code>PropertyChangeListener</code> to install on
     * the <code>Window</code>.
     */
    private PropertyChangeListener createRootPropertyChangeListener() {
        return new RootPropertyHandler();
    }
    
    /**
     * Returns the <code>JRootPane</code> this was created for.
     */
    public JRootPane getRootPane() {
        return rootPane;
    }
    
    /**
     * Returns the decoration style of the <code>JRootPane</code>.
     */
    private int getWindowDecorationStyle() {
        return getRootPane().getWindowDecorationStyle();
    }
    
    public void addNotify() {
        super.addNotify();
        
        uninstallListeners();
        
        window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            if (window instanceof Frame) {
                setState(((Frame)window).getExtendedState());
            } else {
                setState(0);
            }
            setActive(window.isActive());
            installListeners();
        }
    }
    
    public void removeNotify() {
        super.removeNotify();
        
        uninstallListeners();
        window = null;
    }
    
    /**
     * Adds any sub-Components contained in the <code>Quaqua14TitlePane</code>.
     */
    private void installSubcomponents() {
        //if (getWindowDecorationStyle() == JRootPane.FRAME) {
        createActions();
        createButtons();
        add(iconifyButton);
        add(toggleButton);
        add(closeButton);
        //}
    }
    
    /**
     * Installs the fonts and necessary properties on the Quaqua14TitlePane.
     */
    private void installDefaults() {
        // We get the font from the root pane.
        //setFont(UIManager.getFont("InternalFrame.titleFont", getLocale()));
        
        // make title pane translucent
    }
    
    /**
     * Uninstalls any previously installed UI values.
     */
    private void uninstallDefaults() {
    }
    
    /**
     * Closes the Window.
     */
    private void close() {
        Window window = getWindow();
        
        if (window != null) {
            window.dispatchEvent(new WindowEvent(
                    window, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    /**
     * Iconifies the Frame.
     */
    private void iconify() {
        Frame frame = getFrame();
        if (frame != null) {
            frame.setExtendedState(state | Frame.ICONIFIED);
        }
    }
    
    /**
     * Maximizes the Frame.
     */
    private void maximize() {
        Frame frame = getFrame();
        if (frame != null) {
            frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
        }
    }
    
    /**
     * Restores the Frame size.
     */
    private void restore() {
        Frame frame = getFrame();
        
        if (frame == null) {
            return;
        }
        
        if ((state & Frame.ICONIFIED) != 0) {
            frame.setExtendedState(state & ~Frame.ICONIFIED);
        } else {
            frame.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
        }
    }
    
    /**
     * Create the <code>Action</code>s that get associated with the
     * buttons and menu items.
     */
    private void createActions() {
        closeAction = new CloseAction();
        iconifyAction = new IconifyAction();
        restoreAction = new RestoreAction();
        maximizeAction = new MaximizeAction();
    }
    
    /**
     * Returns a <code>JButton</code> appropriate for placement on the
     * TitlePane.
     */
    private JButton createTitleButton() {
        JButton button = new JButton();
        
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setOpaque(false);
        return button;
    }
    
    /**
     * Creates the Buttons that will be placed on the TitlePane.
     */
    private void createButtons() {
        closeButton = createTitleButton();
        closeButton.setAction(closeAction);
        closeButton.setText(null);
        closeButton.putClientProperty("paintActive", Boolean.TRUE);
        closeButton.setBorder(handyEmptyBorder);
        closeButton.getAccessibleContext().setAccessibleName("Close");
        
        
        iconifyButton = createTitleButton();
        iconifyButton.setAction(iconifyAction);
        iconifyButton.setText(null);
        iconifyButton.putClientProperty("paintActive", Boolean.TRUE);
        iconifyButton.setBorder(handyEmptyBorder);
        iconifyButton.getAccessibleContext().setAccessibleName("Iconify");
        
        toggleButton = createTitleButton();
        toggleButton.setAction(restoreAction);
        toggleButton.putClientProperty("paintActive", Boolean.TRUE);
        toggleButton.setBorder(handyEmptyBorder);
        toggleButton.getAccessibleContext().setAccessibleName("Maximize");
        
        armer1 = new JPanel(null);
        armer1.setOpaque(false);
        armer2 = new JPanel(null);
        armer2.setOpaque(false);
        
        MouseListener buttonArmer = new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                closeButton.putClientProperty("paintRollover", Boolean.TRUE);
                iconifyButton.putClientProperty("paintRollover", Boolean.TRUE);
                toggleButton.putClientProperty("paintRollover", Boolean.TRUE);
                closeButton.repaint();
                iconifyButton.repaint();
                toggleButton.repaint();
            }
            public void mouseExited(MouseEvent e) {
                closeButton.putClientProperty("paintRollover", Boolean.FALSE);
                iconifyButton.putClientProperty("paintRollover", Boolean.FALSE);
                toggleButton.putClientProperty("paintRollover", Boolean.FALSE);
                closeButton.repaint();
                iconifyButton.repaint();
                toggleButton.repaint();
            }
        };
        closeButton.addMouseListener(buttonArmer);
        iconifyButton.addMouseListener(buttonArmer);
        toggleButton.addMouseListener(buttonArmer);
        armer1.addMouseListener(buttonArmer);
        armer2.addMouseListener(buttonArmer);
    }
    
    /**
     * Update all textures and icons for the new font used by the JRootPane.
     */
    private void updateIconsAndTextures() {
        int size = getFont().getSize();
        boolean isVertical = isVertical();
        
        Icon closeIcon, iconifyIcon;
        String prefix = "InternalFrame.";
        String suffix;
        if (size <= 9) {
            suffix = ".mini";
        } else if (size <= 11) {
            suffix = ".small";
        } else {
            suffix = "";
        }
        maximizeIcon = UIManager.getIcon(prefix+"maximizeIcon"+suffix);
        minimizeIcon = UIManager.getIcon(prefix+"maximizeIcon"+suffix);
        closeIcon = UIManager.getIcon(prefix+"closeIcon"+suffix);
        iconifyIcon = UIManager.getIcon(prefix+"iconifyIcon"+suffix);
        activeBackground = UIManager.getColor(prefix+((isVertical) ? "vTitlePaneBackground" : "titlePaneBackground")+suffix);
        activeForeground = UIManager.getColor(prefix+"titlePaneForeground"+suffix);
        activeShadow = UIManager.getColor(prefix+"titlePaneShadow"+suffix);
        
        
        closeButton.setIcon(closeIcon);
        iconifyButton.setIcon(iconifyIcon);
        toggleButton.setIcon(maximizeIcon);
    }
    
    
    /**
     * Returns the <code>LayoutManager</code> that should be installed on
     * the <code>Quaqua14TitlePane</code>.
     */
    private LayoutManager createLayout() {
        return new TitlePaneLayout();
    }
    
    /**
     * Updates state dependant upon the Window's active state.
     */
    private void setActive(boolean isActive) {
        //  if (getWindowDecorationStyle() == JRootPane.FRAME) {
        Boolean activeB = isActive ? Boolean.TRUE : Boolean.FALSE;
        
        iconifyButton.putClientProperty("paintActive", activeB);
        closeButton.putClientProperty("paintActive", activeB);
        toggleButton.putClientProperty("paintActive", activeB);
        // }
        // Repaint the whole thing as the Borders that are used have
        // different colors for active vs inactive
        getRootPane().repaint();
    }
    
    /**
     * Sets the state of the Window.
     */
    private void setState(int state) {
        setState(state, false);
    }
    
    /**
     * Sets the state of the window. If <code>updateRegardless</code> is
     * true and the state has not changed, this will update anyway.
     */
    private void setState(int state, boolean updateRegardless) {
        Window w = getWindow();
        
        // if (w != null && getWindowDecorationStyle() == JRootPane.FRAME) {
        if (this.state == state && !updateRegardless) {
            return;
        }
        boolean isPalette = (Boolean) rootPane.getClientProperty("Quaqua.RootPane.isPalette")  == Boolean.TRUE;
        Frame frame = getFrame();
        
        if (frame != null  && ! isPalette) {
            JRootPane rootPane = getRootPane();
            
            if (((state & Frame.MAXIMIZED_BOTH) != 0) &&
                    (rootPane.getBorder() == null ||
                    (rootPane.getBorder() instanceof UIResource)) &&
                    frame.isShowing()) {
                rootPane.setBorder(null);
            } else if ((state & Frame.MAXIMIZED_BOTH) == 0) {
                // This is a croak, if state becomes bound, this can
                // be nuked.
                rootPaneUI.installBorder(rootPane);
            }
            if (armer1.getParent() == null) {
                add(armer1);
            }
            if (armer2.getParent() == null) {
                add(armer2);
            }
            if (frame.isResizable()) {
                if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                    updateToggleButton(restoreAction, minimizeIcon);
                    maximizeAction.setEnabled(false);
                    restoreAction.setEnabled(true);
                } else {
                    updateToggleButton(maximizeAction, maximizeIcon);
                    maximizeAction.setEnabled(true);
                    restoreAction.setEnabled(false);
                }
                if (toggleButton.getParent() == null ||
                        iconifyButton.getParent() == null) {
                    add(toggleButton);
                    add(iconifyButton);
                    revalidate();
                    repaint();
                }
                toggleButton.setText(null);
            } else {
                maximizeAction.setEnabled(false);
                restoreAction.setEnabled(false);
                if (toggleButton.getParent() != null) {
                    remove(toggleButton);
                    revalidate();
                    repaint();
                }
            }
        } else {
            // Not contained in a Frame
            maximizeAction.setEnabled(false);
            restoreAction.setEnabled(false);
            iconifyAction.setEnabled(false);
            remove(armer1);
            remove(armer2);
            remove(toggleButton);
            remove(iconifyButton);
            revalidate();
            repaint();
        }
        closeAction.setEnabled(true);
        this.state = state;
        //}
    }
    
    /**
     * Updates the toggle button to contain the Icon <code>icon</code>, and
     * Action <code>action</code>.
     */
    private void updateToggleButton(Action action, Icon icon) {
        toggleButton.setAction(action);
        toggleButton.setIcon(icon);
        toggleButton.setText(null);
    }
    
    /**
     * Returns the Frame rendering in. This will return null if the
     * <code>JRootPane</code> is not contained in a <code>Frame</code>.
     */
    private Frame getFrame() {
        Window window = getWindow();
        
        if (window instanceof Frame) {
            return (Frame)window;
        }
        return null;
    }
    
    /**
     * Returns the Dialog rendering in. This will return null if the
     * <code>JRootPane</code> is not contained in a <code>Frame</code>.
     */
    private Dialog getDialog() {
        Window window = getWindow();
        
        if (window instanceof Dialog) {
            return (Dialog) window;
        }
        return null;
    }
    
    public Font getFont() {
        return rootPane.getFont();
        /*
        Window window = getWindow();
        return (window != null) ? window.getFont() : rootPane.getFont();
         */
    }
    
    /**
     * Returns the <code>Window</code> the <code>JRootPane</code> is
     * contained in. This will return null if there is no parent ancestor
     * of the <code>JRootPane</code>.
     */
    private Window getWindow() {
        return window;
    }
    
    /**
     * Returns the String to display as the title.
     */
    private String getTitle() {
        Window w = getWindow();
        
        if (w instanceof Frame) {
            return ((Frame)w).getTitle();
        } else if (w instanceof Dialog) {
            return ((Dialog)w).getTitle();
        }
        return null;
    }
    
    /**
     * Renders the TitlePane.
     */
    public void paintComponent(Graphics gr)  {
        Graphics2D g = (Graphics2D) gr;
        
        
        Object oldHints = QuaquaUtilities.beginGraphics(g);
        
        // As state isn't bound, we need a convenience place to check
        // if it has changed. Changing the state typically changes the
        if (getFrame() != null) {
            setState(getFrame().getExtendedState());
        }
        Window window = getWindow();
        
        JRootPane rootPane = getRootPane();
        boolean isVertical = isVertical();
        
        boolean leftToRight = (window == null) ?
            rootPane.getComponentOrientation().isLeftToRight() :
            window.getComponentOrientation().isLeftToRight();
        
        boolean isPalette = (Boolean) rootPane.getClientProperty("Quaqua.RootPane.isPalette")  == Boolean.TRUE;
        boolean isSelected = (window == null) ? false : window.isActive();
        isSelected |= isPalette;
        
        int width = getWidth();
        int height = getHeight();
        
        Color background;
        Color foreground;
        Color darkShadow;
        Border titleBorder;
        
        int fontSize = getFont().getSize();
        String suffix = (fontSize <= 9) ? ".mini" :
            (fontSize <= 11) ? ".small" : "";
        if (isVertical()) {
            suffix = ".vertical"+suffix;
        }
        
        if (isSelected) {
            background = activeBackground;
            foreground = activeForeground;
            darkShadow = activeShadow;
            titleBorder = ((Border[]) UIManager.get("Frame.titlePaneBorders"+suffix))[0];
        } else {
            background = inactiveBackground;
            foreground = inactiveForeground;
            darkShadow = inactiveShadow;
            titleBorder = ((Border[]) UIManager.get("Frame.titlePaneBorders"+suffix))[1];
        }
        
        if (/*isPalette ||*/ titleBorder == null) {
            g.setPaint(TextureColor.getPaint(background, window));
            g.fillRect(0, 0, width, height);
            g.setPaint(TextureColor.getPaint(darkShadow, window));
            if (isVertical) {
                g.drawLine( width - 1, 0, width - 1, height);
            } else {
                g.drawLine( 0, height - 1, width, height -1);
            }
        } else {
            // Make background fully transparent (0 alpha)
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            titleBorder.paintBorder(this, g, 0, 0, width, height);
        }
        
        int xOffset = leftToRight ? 5 : width - 5;
        
        // if (getWindowDecorationStyle() == JRootPane.FRAME) {
        xOffset += leftToRight ? IMAGE_WIDTH + 5 : - IMAGE_WIDTH - 5;
        // }
        
        String theTitle = getTitle();
        
        if (theTitle != null) {
            Font f = getFont();
            FontMetrics fm = g.getFontMetrics();
            
            int yOffset = ( (height - fm.getHeight() ) / 2 ) + fm.getAscent();
            
            Rectangle rect = new Rectangle(0, 0, 0, 0);
            if (iconifyButton != null && iconifyButton.getParent() != null) {
                rect = iconifyButton.getBounds();
            }
            Rectangle toggleRect = new Rectangle(0, 0, 0, 0);
            if (toggleButton != null && toggleButton.getParent() != null) {
                toggleRect = toggleButton.getBounds();
            }
            Rectangle closeRect = new Rectangle(0, 0, 0, 0);
            if (closeButton != null && closeButton.getParent() != null) {
                closeRect = closeButton.getBounds();
            }
            int titleW;
            
            if (isVertical) {
                titleW = height - Math.max(toggleRect.y+toggleRect.height,closeRect.y+closeRect.height);
                theTitle = clippedText(theTitle, fm, titleW);
                xOffset = ( (width - fm.getHeight() ) / 2 ) + fm.getAscent();
                
                int titleLength = SwingUtilities.computeStringWidth(fm, theTitle);
                AffineTransform at = g.getTransform();
                int y = Math.max(Math.max(closeRect.y + closeRect.height,toggleRect.y + toggleRect.height) + 5, (getHeight() - titleLength) / 2) + titleLength;
                g.rotate((float) (Math.PI / -2d), xOffset, y);
                if (UIManager.getColor("Frame.titlePaneEmbossForeground") != null) {
                    g.setPaint(UIManager.getColor("Frame.titlePaneEmbossForeground"));
                    g.drawString(theTitle, xOffset, y + 1);
                }
                g.setPaint(TextureColor.getPaint(foreground, window));
                g.drawString(theTitle, xOffset, y);
                g.setTransform(at);
            } else {
                if( leftToRight ) {
                    if (rect.x == 0) {
                        rect.x = window.getWidth() - window.getInsets().right-2;
                    }
                    titleW = getWidth() - Math.max(toggleRect.x+toggleRect.width,closeRect.x+closeRect.width) - 10;
                    theTitle = clippedText(theTitle, fm, titleW);
                } else {
                    titleW = getWidth() - Math.max(toggleRect.x+toggleRect.width,closeRect.x+closeRect.width) - 10;
                    theTitle = clippedText(theTitle, fm, titleW);
                    xOffset -= SwingUtilities.computeStringWidth(fm, theTitle);
                }
                int titleLength = SwingUtilities.computeStringWidth(fm, theTitle);
                if (UIManager.getColor("Frame.titlePaneEmbossForeground") != null) {
                    g.setPaint(UIManager.getColor("Frame.titlePaneEmbossForeground"));
                    g.drawString( theTitle, Math.max(Math.max(closeRect.x + closeRect.width, toggleRect.x + toggleRect.width) + 5, (getWidth() - titleLength) / 2), yOffset + 1);
                }
                g.setPaint(TextureColor.getPaint(foreground, window));
                g.drawString( theTitle, Math.max(Math.max(closeRect.x + closeRect.width, toggleRect.x + toggleRect.width) + 5, (getWidth() - titleLength) / 2), yOffset );
                xOffset += leftToRight ? titleLength + 5  : -5;
            }
        }
        /*
        Composite savedComposite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
        g.setColor(new Color(0x00ff00,true));
        g.fill(new Polygon(new int[] {0,10,0}, new int[] {0,0,10}, 3));
        g.setComposite(savedComposite);
         */
        QuaquaUtilities.endGraphics(g, oldHints);
    }
    
    /**
     * Convenience method to clip the passed in text to the specified
     * size.
     */
    private String clippedText(String text, FontMetrics fm,
            int availTextWidth) {
        if ((text == null) || (text.equals("")))  {
            return "";
        }
        int textWidth = SwingUtilities.computeStringWidth(fm, text);
        String clipString = "...";
        if (textWidth > availTextWidth) {
            int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
            int nChars;
            for(nChars = 0; nChars < text.length(); nChars++) {
                totalWidth += fm.charWidth(text.charAt(nChars));
                if (totalWidth > availTextWidth) {
                    break;
                }
            }
            text = text.substring(0, nChars) + clipString;
        }
        return text;
    }
    
    private boolean isVertical() {
        return rootPane.getClientProperty("Quaqua.RootPane.isVertical") == Boolean.TRUE;
    }
    
    /**
     * Actions used to <code>close</code> the <code>Window</code>.
     */
    private class CloseAction extends AbstractAction {
        public CloseAction() {
            super(UIManager.getString("Quaqua14TitlePane.closeTitle",
                    getLocale()));
        }
        
        public void actionPerformed(ActionEvent e) {
            close();
        }
    }
    
    
    /**
     * Actions used to <code>iconfiy</code> the <code>Frame</code>.
     */
    private class IconifyAction extends AbstractAction {
        public IconifyAction() {
            super(UIManager.getString("Quaqua14TitlePane.iconifyTitle",
                    getLocale()));
        }
        
        public void actionPerformed(ActionEvent e) {
            iconify();
        }
    }
    
    
    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     */
    private class RestoreAction extends AbstractAction {
        public RestoreAction() {
            super(UIManager.getString
                    ("Quaqua14TitlePane.restoreTitle", getLocale()));
        }
        
        public void actionPerformed(ActionEvent e) {
            restore();
        }
    }
    
    
    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     */
    private class MaximizeAction extends AbstractAction {
        public MaximizeAction() {
            super(UIManager.getString("Quaqua14TitlePane.maximizeTitle",
                    getLocale()));
        }
        
        public void actionPerformed(ActionEvent e) {
            maximize();
        }
    }
    
    
    /**
     * Class responsible for drawing the system menu. Looks up the
     * image to draw from the Frame associated with the
     * <code>JRootPane</code>.
     */
    private class SystemMenuBar extends JMenuBar {
        public void paint(Graphics gr) {
            Graphics2D g = (Graphics2D) gr;
            Frame frame = getFrame();
            
            if (isOpaque()) {
                g.setPaint(TextureColor.getPaint(getBackground(), this));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            Image image = (frame != null) ? frame.getIconImage() : null;
            
            if (image != null) {
                g.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            } else {
                Icon icon = UIManager.getIcon("InternalFrame.icon");
                
                if (icon != null) {
                    icon.paintIcon(this, g, 0, 0);
                }
            }
        }
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            
            return new Dimension(Math.max(IMAGE_WIDTH, size.width),
                    Math.max(size.height, IMAGE_HEIGHT));
        }
    }
    
    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <Foo>.
     */
    private class TitlePaneLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component c) {}
        public void removeLayoutComponent(Component c) {}
        public Dimension preferredLayoutSize(Container c)  {
            int height = computeHeight();
            return new Dimension(height, height);
        }
        
        public Dimension minimumLayoutSize(Container c) {
            return preferredLayoutSize(c);
        }
        
        private int computeHeight() {
            int fontSize = getFont().getSize();
            if (fontSize <= 9) {
                return 12;
            } else if (fontSize <= 11) {
                return 16;
            } else {
                return 22;
            }
            /*
            FontMetrics fm
            = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
            int fontHeight = fm.getHeight();
             
             
            fontHeight += 2;
            int iconHeight = 0;
            // if (getWindowDecorationStyle() == JRootPane.FRAME) {
            iconHeight = IMAGE_HEIGHT;
            // }
             
            int finalHeight = Math.max( fontHeight, iconHeight );
            return finalHeight;
             */
        }
        
        
        public void layoutContainer(Container c) {
            JRootPane rootPane = getRootPane();
            boolean isVertical = isVertical();
            if (isVertical) {
                layoutVerticalContainer(c);
            } else {
                layoutHorizontalContainer(c);
            }
        }
        public void layoutHorizontalContainer(Container c) {
       /*
            if (getWindowDecorationStyle() != JRootPane.FRAME) {
                buttonsWidth = 0;
                return;
            }* /
            boolean leftToRight = (window == null) ?
                               getRootPane().getComponentOrientation().isLeftToRight() :
                               window.getComponentOrientation().isLeftToRight();
        */
            int fontSize = getFont().getSize();
            boolean leftToRight;
            if (window == null) {
                leftToRight = !getRootPane().getComponentOrientation().isLeftToRight();
            } else {
                leftToRight = !window.getComponentOrientation().isLeftToRight();
            }
            
            int w = getWidth();
            int x;
            int y;
            int spacing;
            int buttonHeight;
            int buttonWidth;
            
            if (fontSize <= 9) {
                y = 1;
            } else if (fontSize <= 11) {
                y = 1;
            } else {
                y = 4;
            }
            
            if (closeButton != null && closeButton.getIcon() != null) {
                buttonHeight = closeButton.getIcon().getIconHeight();
                buttonWidth = closeButton.getIcon().getIconWidth();
            } else {
                buttonHeight = IMAGE_HEIGHT;
                buttonWidth = IMAGE_WIDTH;
            }
            
            // assumes all buttons have the same dimensions
            // these dimensions include the borders
            
            x = leftToRight ? w : 0;
            
            spacing = 5;
            x = leftToRight ? spacing : w - buttonWidth - spacing;
            if (menuBar != null) {
                menuBar.setBounds(x, y, buttonWidth, buttonHeight);
            }
            
            x = leftToRight ? w : 0;
            spacing = (fontSize <= 11) ? 5 : 7;
            x += leftToRight ? -spacing -buttonWidth : spacing;
            
            if (closeButton != null) {
                closeButton.setBounds(x, y, buttonWidth, buttonHeight);
            }
            
            if( !leftToRight ) x += buttonWidth;
            
            if (iconifyButton != null && iconifyButton.getParent() != null) {
                spacing = 5;
                armer1.setBounds(x, y, spacing, buttonHeight);
                x += leftToRight ? -spacing -buttonWidth : spacing;
                iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
                if (!leftToRight) {
                    x += buttonWidth;
                }
            } else {
                armer1.setBounds(0,0,0,0);
            }
            
            if (Toolkit.getDefaultToolkit().isFrameStateSupported(
                    Frame.MAXIMIZED_BOTH)) {
                if (toggleButton.getParent() != null) {
                    spacing = 5;
                    armer2.setBounds(x, y, spacing, buttonHeight);
                    x += leftToRight ? -spacing -buttonWidth : spacing;
                    toggleButton.setBounds(x, y, buttonWidth, buttonHeight);
                    if (!leftToRight) {
                        x += buttonWidth;
                    }
                }
            } else {
                armer2.setBounds(0,0,0,0);
            }
            
            buttonsWidth = leftToRight ? w - x : x;
        }
    }
    public void layoutVerticalContainer(Container c) {
        int h = getHeight();
        int x;
        int y;
        int spacing;
        int buttonHeight;
        int buttonWidth;
        
        int fontSize = getFont().getSize();
        if (fontSize <= 9) {
            x = 2;
        } else if (fontSize <= 11) {
            x = 1;
        } else {
            x = 2;
        }
        
        if (closeButton != null && closeButton.getIcon() != null) {
            buttonHeight = closeButton.getIcon().getIconHeight();
            buttonWidth = closeButton.getIcon().getIconWidth();
        } else {
            buttonHeight = IMAGE_HEIGHT;
            buttonWidth = IMAGE_WIDTH;
        }
        
        // assumes all buttons have the same dimensions
        // these dimensions include the borders
        
        spacing = 5;
        y = spacing;
        if (menuBar != null) {
            menuBar.setBounds(x, y, buttonWidth, buttonHeight);
        }
        
        if (fontSize <= 9) {
            y = 0;
        } else if (fontSize <= 11) {
            y = 0;
        } else {
            y = 4;
        }
        spacing = 2;
        y += spacing;
        
        if (closeButton != null) {
            closeButton.setBounds(x, y, buttonWidth, buttonHeight);
        }
        
        y += buttonHeight;
        
        if (iconifyButton != null && iconifyButton.getParent() != null) {
            spacing = 5;
            armer1.setBounds(x, y, buttonWidth, spacing);
            y += spacing;
            iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
            y += buttonHeight;
        } else {
            armer1.setBounds(0,0,0,0);
        }
        
        if (Toolkit.getDefaultToolkit().isFrameStateSupported(
                Frame.MAXIMIZED_BOTH)) {
            if (toggleButton.getParent() != null) {
                spacing = 5;
                armer2.setBounds(x, y, buttonWidth, spacing);
                y += spacing;
                toggleButton.setBounds(x, y, buttonWidth, buttonHeight);
                y += buttonHeight;
            }
        } else {
            armer2.setBounds(0,0,0,0);
        }
        
        buttonsWidth = x + buttonWidth;
        
    }
    
    
    
    /**
     * PropertyChangeListener installed on the JRootPane. Updates the necessary
     * state as the state of the JRootPane changes.
     */
    private class RootPropertyHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent pce) {
            String name = pce.getPropertyName();
            if ("Quaqua.RootPane.isVertical".equals(name)) {
                updateIconsAndTextures();
                revalidate();
                repaint();
            } else if ("windowModified".equals(name)) {
                closeButton.setSelected(((Boolean) pce.getNewValue()).booleanValue());
            } else if ("font".equals(name)) {
                updateIconsAndTextures();
                revalidate();
                repaint();
            }
            
        }
    }
    /**
     * PropertyChangeListener installed on the Window. Updates the necessary
     * state as the state of the Window changes.
     */
    private class WindowPropertyHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent pce) {
            String name = pce.getPropertyName();
            // Frame.state isn't currently bound.
            if ("resizable".equals(name) || "state".equals(name)) {
                Frame frame = getFrame();
                if (frame != null) {
                    setState(frame.getExtendedState(), true);
                }
                if ("resizable".equals(name)) {
                    getRootPane().repaint();
                }
            } else if ("title".equals(name)) {
                repaint();
            } else if ("Quaqua.RootPane.isVertical".equals(name)) {
                updateIconsAndTextures();
                revalidate();
                repaint();
            } else if ("componentOrientation".equals(name)) {
                revalidate();
                repaint();
            }
        }
    }
    
    
    /**
     * WindowListener installed on the Window, updates the state as necessary.
     */
    private class WindowHandler extends WindowAdapter {
        public void windowActivated(WindowEvent ev) {
            setActive(true);
        }
        
        public void windowDeactivated(WindowEvent ev) {
            setActive(false);
        }
    }
}
