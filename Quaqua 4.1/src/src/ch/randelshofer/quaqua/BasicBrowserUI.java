/*
 * @(#)BasicBrowserUI.java  1.0  August 27, 2005
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
import javax.swing.plaf.*;

/**
 * BasicBrowserUI.
 *
 * @author  Werner Randelshofer
 * @version 1.0 August 27, 2005 Created.
 */
public class BasicBrowserUI extends BrowserUI {
    protected JBrowser browser;
    /**
     * Creates a new instance.
     */
    public BasicBrowserUI() {
    }
    
    public void installUI(JComponent c) {
        c.setBackground(UIManager.getColor("List.background"));
        
        browser = (JBrowser) c;
        installDefaults();
    }
    protected void installDefaults() {
        if (browser.getCellRenderer() == null ||
                (browser.getCellRenderer() instanceof UIResource)) {
            browser.setCellRenderer(createCellRenderer());
        }
    }
    protected ListCellRenderer createCellRenderer() {
        return  new DefaultBrowserCellRenderer.UIResource(browser);
    }
}
