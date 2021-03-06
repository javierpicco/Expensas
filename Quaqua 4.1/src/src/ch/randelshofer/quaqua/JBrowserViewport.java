/*
 * @(#)JBrowserViewport.java  1.0  August 27, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
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

import java.awt.*;
import javax.swing.*;
/**
 * JBrowserViewport is a viewport for use with a JBrowser. The viewport fills
 * a JScrollPane with empty columns, so that the JBrowser always appears to fill
 * the whole JScrollPane.
 * <p>
 * Example:
 * <pre>
 * JBrowser browser = new JBrowser();
 * JScrollPane scrollPane = new JScrollPane();
 * scrollPane.setViewport(new JBrowserViewport());
 * scrollPane.setViewportView(browser);
 * </pre>
 * <p>
 * Note: The JBrowserViewport is only needed, if the JBrowser is used without
 * the Quaqua Look and Feel. The Quaqua Look and Feel automatically fills
 * the viewport with empty columns.
 *
 * @see JBrowserViewport
 *
 * @author  Werner Randelshofer
 * @version 1.0 August 27, 2005 Created.
 */
public class JBrowserViewport extends JViewport {
    /** This scrollbar is used as a cell renderere 'rubber stamp' to render fake JBrowser columns
     * in the viewport.
     */
    private static JScrollBar scrollBarRenderer = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, 1) {
        /** Overidde isShowing to fulfill contract with CellRendererPane. */
        public boolean isShowing() {
            return true;
        }
        
        /** FIXME - Apparently we need to override paintChildren in order
         *          to paint the scrollbar correctly. This shouldn't be
         *          necessary.
         */
        protected void paintChildren(Graphics g) {
            Component[] c = getComponents();
            for (int i=0; i < c.length; i++) {
                Graphics cg = g.create(c[i].getX(), c[i].getY(), c[i].getWidth(), c[i].getHeight());
                c[i].paint(cg);
                cg.dispose();
            }
        }
    };
    
    /** Shared cell renderer pane. */
    private static CellRendererPane cellRendererPane = new CellRendererPane();
    
    public void paintComponent(Graphics g) {
        if (getView() instanceof JBrowser) {
            JBrowser browser = (JBrowser) getView();
            if (browser != null) {
                Dimension vs = getSize();
                Dimension bs = browser.getSize();
                Point p = browser.getLocation();
                
                Dimension ss = scrollBarRenderer.getPreferredSize();
                
                // Paint scroll bar tracks at the right to fill the viewport
                if (bs.width < vs.width) {
                    int fixedCellWidth = browser.getFixedCellWidth();
                    
                    g.setColor(browser.getBackground());
                    g.fillRect(bs.width, 0, vs.width - bs.width, vs.height);
                    
                    scrollBarRenderer.setSize(ss.width,vs.height);
                    scrollBarRenderer.doLayout();
                    
                    for (int x = browser.getWidth() + fixedCellWidth; x < vs.width; x += fixedCellWidth + ss.width) {
                        cellRendererPane.paintComponent(g, scrollBarRenderer, this, x, 0, ss.width, vs.height, false);
                    }
                }
            }
        }
    }
}

