/*
 * @(#)ComboBoxTest2.java  1.0  November 13, 2006
 *
 * Copyright (c) 2006 Werner Randelshofer
 * Staldenmattweg 2, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package test;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.border.*;
/**
 * ComboBoxTest2.
 *
 * @author Werner Randelshofer
 * @version 1.0 November 13, 2006 Created.
 */
public class ComboBoxTest2 {
    private final static Color b1 = Color.black;
    private final static Color b2 = Color.white;
    public static class MyComboBox extends JComboBox {
        public MyComboBox() {
            super();
            setOpaque(true);
            //setBackground(defaultBackground);
            setEditable(false);
            setBorder(new EmptyBorder(1, 1, 1, 1));  // ensure background can be seen
            setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
                public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                    g.setColor(b1);
                    g.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
                    g.setColor(b2);
                    g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 3, bounds.height - 3);
                }
            });
            //setFont(valueFont);
            setMaximumRowCount(30);
        }
    }
    /** Creates a new instance. */
    public ComboBoxTest2() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame f = new JFrame("Le Frame");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new MyComboBox());
        f.pack();
        f.show();
    }
    
}
