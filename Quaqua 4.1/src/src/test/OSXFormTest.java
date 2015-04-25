/*
 * @(#)OSXFormTest.java  1.0  June 15, 2007
 *
 * Copyright (c) 2007 Werner Randelshofer
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

import org.jdesktop.layout.GroupLayout;

import javax.swing.*;

import ch.randelshofer.quaqua.QuaquaLookAndFeel;

/**
*/
public class OSXFormTest
{
   public static void main(String[] args)
   {
       try
       {
           UIManager.setLookAndFeel(QuaquaLookAndFeel.class.getName());
       }
       catch(Exception e)
       {
                 }

       JFrame frame = new JFrame("OSXFORMTEST");
       JPanel panel = new JPanel();
       GroupLayout layout = new GroupLayout(panel);
       panel.setLayout(layout);

       if(UIManager.getLookAndFeel().getName().startsWith("Quaqua"));
       {
           layout.setAutocreateGaps(true);
           layout.setAutocreateContainerGaps(true);
       }

       JLabel artistLabel = new JLabel("Artist");
       JLabel albumLabel = new JLabel("Album");
       JTextField artist = new JTextField("Sonic Youth");
       JTextField album = new JTextField("Bad Moon Rising");
       
       
       int labelAlignment = UIManager.getLookAndFeel().getID().equals("Aqua") ?
            GroupLayout.TRAILING : 
            GroupLayout.LEADING;
       
       GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
       hGroup.add(layout.createParallelGroup(labelAlignment).add(artistLabel).add(albumLabel));
       hGroup.add(layout.createParallelGroup().add(artist).add(album));
       layout.setHorizontalGroup(hGroup);

       GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
       vGroup.add(layout.createParallelGroup(GroupLayout.BASELINE).add(artistLabel).add(artist));
       vGroup.add(layout.createParallelGroup(GroupLayout.BASELINE).add(albumLabel).add(album));
       layout.setVerticalGroup(vGroup);
       frame.add(panel);
       frame.pack();
       frame.setVisible(true);
   }
}
