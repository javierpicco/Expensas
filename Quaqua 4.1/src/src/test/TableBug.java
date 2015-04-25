/*
 * @(#)TableBug.java  1.0  February 4, 2006
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

package test;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
/**
 * TableBug.
 *
 * @author  Werner Randelshofer
 * @version 1.0 February 4, 2006 Created.
 */
public class TableBug {
    
    /**
     * Creates a new instance.
     */
    public TableBug() {
    }
    
   public static void main(String[] args)  {
       try {
     UIManager.setLookAndFeel 
("ch.randelshofer.quaqua.QuaquaLookAndFeel");
     
  //   UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
} catch (Throwable t) {
    t.printStackTrace();
}
     JFrame frame = new JFrame();
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.getContentPane().setLayout(new BorderLayout());
     JPanel content = new JPanel(new BorderLayout());

     DefaultTableModel model = new DefaultTableModel();
     model.addColumn("Test");

     JTable jTable = new JTable(model) {
       public Dimension getMinimumSize() {
         return new Dimension(100, 100);
       }
     };
     content.add(new JScrollPane(jTable), BorderLayout.NORTH);
     content.setBorder(new TitledBorder("Border"));

     frame.getContentPane().add(content, BorderLayout.CENTER);
     frame.setBounds(200, 200, 400, 400);
     frame.setVisible(true);
   }

    
}
