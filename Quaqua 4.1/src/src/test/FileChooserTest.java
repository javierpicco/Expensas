/*
 * @(#)FileChooser.java  1.0  13 February 2005
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

package test;

import ch.randelshofer.quaqua.*;
import java.awt.*;
import java.awt.event.PaintEvent;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.plaf.FileChooserUI;
/**
 * FileChooser.
 *
 * @author  Werner Randelshofer
 * @version 1.0  13 February 2005  Created.
 */
public class FileChooserTest extends javax.swing.JPanel {
    private FileDialog fileDialog;
    private JFileChooser fileChooser;
    
    private static class MyFilenameFilter extends javax.swing.filechooser.FileFilter implements FilenameFilter {
        private HashSet extSet = new HashSet();
        private String extensions;
        
        public boolean accept(File dir, String name) {
            return accept(new File(dir, name));
        }
        
        public void setExtensions(String newValue) {
            extensions = newValue;
            extSet.clear();
            
            StringTokenizer tt = new StringTokenizer(newValue);
            while (tt.hasMoreTokens()) {
                extSet.add(tt.nextToken());
            }
                    
            //extSet.addAll(Arrays.asList(newValue.toLowerCase().split(" ")));
        }
        
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String name = file.getName();
            int p = name.lastIndexOf(".");
            if (p != -1) {
                return extSet.contains(name.substring(p + 1).toLowerCase());
            }
            return false;
        }

        public String getDescription() {
            return extensions;
        }
    }
    private MyFilenameFilter filter = new MyFilenameFilter();
    
    /** Creates new form. */
    public FileChooserTest() {
        initComponents();
        
        setToField.setText(QuaquaManager.getProperty("user.home"));
    }
    
    private void configureFileDialog() {
        if (fileDialog == null) {
            fileDialog = new FileDialog((Frame) SwingUtilities.getWindowAncestor(this));
        }
        filter.setExtensions(filterFilesField.getText());
        fileDialog.setFilenameFilter(filterFilesItem.isSelected() ? filter : null);
    }
    
    private void configureFileChooser() {
        if (fileChooser == null) {
                    long start = System.currentTimeMillis();
            fileChooser = new JFileChooser() {
               /*
                public void repaint(long tm, int x, int y, int w, int h) {
                    System.out.println("FileChooserTest.repaint "+x+","+y+","+w+","+h);
                    super.repaint(tm, x, y, w, h);
                }/*
                public void paint(Graphics g) {
                    long start = System.currentTimeMillis();
                    super.paint(g);
                    long end = System.currentTimeMillis();
                    System.out.println("FileChooserTest.paint e="+(end-start)+" "+g.getClipBounds());
                    //System.out.println("FileChooserTest.paint "+EventQueue.getCurrentEvent());
                    }*/
    public void updateUI() {
                    long start = System.currentTimeMillis();
                    super.updateUI();
                    long end = System.currentTimeMillis();
                    System.out.println("FileChooserTest.updateUI e="+(end-start));
    }
            };
                    long end = System.currentTimeMillis();
                    System.out.println("FileChooserTest newFileChooser elapsed="+(end-start));
        }
        fileChooser.setApproveButtonText(
                customApproveItem.isSelected() ? customApproveField.getText() : null
                );
        
        filter.setExtensions(filterFilesField.getText());
        fileChooser.setFileFilter(filterFilesItem.isSelected() ? filter : null);
        fileChooser.setFileSelectionMode(
               selectFilesOnlyItem.isSelected() ?
                   JFileChooser.FILES_ONLY :
                   (selectDirectoriesOnlyItem.isSelected() ?
                       JFileChooser.DIRECTORIES_ONLY :
                       JFileChooser.FILES_AND_DIRECTORIES)
                );
        fileChooser.setFileHidingEnabled(! hiddenFilesItem.isSelected());
        fileChooser.setMultiSelectionEnabled(multiSelectionItem.isSelected());
    }
    
    private void updateButtons() {
        boolean b =
                ! customApproveItem.isSelected() &&
                selectFilesOnlyItem.isSelected() &&
                ! multiSelectionItem.isSelected() &&
                ! hiddenFilesItem.isSelected();
        
        saveFileDialogButton.setEnabled(b);
        openFileDialogButton.setEnabled(b);
    }
    
    private void analyzeOption(JFileChooser fc, int option) {
        StringBuffer buf = new StringBuffer();
        switch (option) {
            case JFileChooser.CANCEL_OPTION :
                buf.append("canceled");
                break;
            case JFileChooser.APPROVE_OPTION :
                buf.append("approved\n");
                buf.append("file:"+fc.getSelectedFile()+"\n");
                if (fc.getSelectedFile() != null) {
                    setToField.setText(fc.getSelectedFile().toString());
                }
                buf.append("files:"+Arrays.asList(fc.getSelectedFiles())+"\n");
                buf.append("directory:"+fc.getCurrentDirectory()+"\n");
                break;
            case JFileChooser.ERROR_OPTION :
                buf.append("error");
                break;
                
        }
        outputField.setText(buf.toString());
    }
    private void analyzeOption(FileDialog fd) {
        StringBuffer buf = new StringBuffer();
        buf.append("file:"+fd.getFile()+"\n");
        outputField.setText(buf.toString());
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        selectionTypeGroup = new javax.swing.ButtonGroup();
        settingsPanel = new javax.swing.JPanel();
        selectFilesOnlyItem = new javax.swing.JRadioButton();
        selectDirectoriesOnlyItem = new javax.swing.JRadioButton();
        selectFilesAndDirectoriesItem = new javax.swing.JRadioButton();
        multiSelectionItem = new javax.swing.JCheckBox();
        hiddenFilesItem = new javax.swing.JCheckBox();
        customApproveItem = new javax.swing.JCheckBox();
        customApproveField = new javax.swing.JTextField();
        filterFilesItem = new javax.swing.JCheckBox();
        filterFilesField = new javax.swing.JTextField();
        actionPanel = new javax.swing.JPanel();
        openFileDialogButton = new javax.swing.JButton();
        saveFileDialogButton = new javax.swing.JButton();
        openFileButton = new javax.swing.JButton();
        saveFileButton = new javax.swing.JButton();
        resetPanel = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        setToButton = new javax.swing.JButton();
        createWithButton = new javax.swing.JButton();
        setToField = new javax.swing.JTextField();
        outputPanel = new javax.swing.JPanel();
        outputLabel = new javax.swing.JLabel();
        outputScrollPane = new javax.swing.JScrollPane();
        outputField = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 20, 20, 20));
        settingsPanel.setLayout(new java.awt.GridBagLayout());

        selectionTypeGroup.add(selectFilesOnlyItem);
        selectFilesOnlyItem.setSelected(true);
        selectFilesOnlyItem.setText("Select files");
        selectFilesOnlyItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectionTypeGroupChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        settingsPanel.add(selectFilesOnlyItem, gridBagConstraints);

        selectionTypeGroup.add(selectDirectoriesOnlyItem);
        selectDirectoriesOnlyItem.setText("Select directories");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        settingsPanel.add(selectDirectoriesOnlyItem, gridBagConstraints);

        selectionTypeGroup.add(selectFilesAndDirectoriesItem);
        selectFilesAndDirectoriesItem.setText("Select files and directories");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        settingsPanel.add(selectFilesAndDirectoriesItem, gridBagConstraints);

        multiSelectionItem.setText("Allow multiple selection");
        multiSelectionItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                multiSelectionItemChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        settingsPanel.add(multiSelectionItem, gridBagConstraints);

        hiddenFilesItem.setText("Show hidden files");
        hiddenFilesItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hiddenFilesItemChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        settingsPanel.add(hiddenFilesItem, gridBagConstraints);

        customApproveItem.setText("Use custom approve button text");
        customApproveItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                customApproveItemChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        settingsPanel.add(customApproveItem, gridBagConstraints);

        customApproveField.setText("Approve");
        customApproveField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        settingsPanel.add(customApproveField, gridBagConstraints);

        filterFilesItem.setText("Filter files");
        filterFilesItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterFilesItemChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        settingsPanel.add(filterFilesItem, gridBagConstraints);

        filterFilesField.setText("gif jpg png");
        filterFilesField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        settingsPanel.add(filterFilesField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(settingsPanel, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridLayout(0, 2));

        openFileDialogButton.setText("Open AWT File Dialog");
        openFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileDialog(evt);
            }
        });

        actionPanel.add(openFileDialogButton);

        saveFileDialogButton.setText("Save AWT File Dialog");
        saveFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileDialog(evt);
            }
        });

        actionPanel.add(saveFileDialogButton);

        openFileButton.setText("Open Swing File Chooser");
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileChooser(evt);
            }
        });

        actionPanel.add(openFileButton);

        saveFileButton.setText("Save Swing File Chooser");
        saveFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileChooser(evt);
            }
        });

        actionPanel.add(saveFileButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        add(actionPanel, gridBagConstraints);

        resetPanel.setLayout(new java.awt.GridBagLayout());

        resetButton.setText("Reset to:");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset(evt);
            }
        });

        resetPanel.add(resetButton, new java.awt.GridBagConstraints());

        setToButton.setText("Set to:");
        setToButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTo(evt);
            }
        });

        resetPanel.add(setToButton, new java.awt.GridBagConstraints());

        createWithButton.setText("Create With:");
        createWithButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createWith(evt);
            }
        });

        resetPanel.add(createWithButton, new java.awt.GridBagConstraints());

        setToField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        resetPanel.add(setToField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        add(resetPanel, gridBagConstraints);

        outputPanel.setLayout(new java.awt.GridBagLayout());

        outputLabel.setText("Output:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        outputPanel.add(outputLabel, gridBagConstraints);

        outputField.setEditable(false);
        outputField.setLineWrap(true);
        outputField.setRows(5);
        outputScrollPane.setViewportView(outputField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        outputPanel.add(outputScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        add(outputPanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void createWith(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createWith
        reset(evt);
        fileChooser = new JFileChooser(setToField.getText());
        
    }//GEN-LAST:event_createWith
    
    private void hiddenFilesItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hiddenFilesItemChanged
        updateButtons();
    }//GEN-LAST:event_hiddenFilesItemChanged
    
    private void multiSelectionItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_multiSelectionItemChanged
        updateButtons();
    }//GEN-LAST:event_multiSelectionItemChanged
    
    private void selectionTypeGroupChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectionTypeGroupChanged
        updateButtons();
    }//GEN-LAST:event_selectionTypeGroupChanged
    
    private void filterFilesItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterFilesItemChanged
        filterFilesField.setEnabled(filterFilesItem.isSelected());
    }//GEN-LAST:event_filterFilesItemChanged
    
    private void customApproveItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_customApproveItemChanged
        boolean b = customApproveItem.isSelected();
        customApproveField.setEnabled(b);
        updateButtons();
    }//GEN-LAST:event_customApproveItemChanged
    
    private void setTo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setTo
        configureFileChooser();
        fileChooser.setSelectedFile(new File(setToField.getText()));
        configureFileDialog();
        fileDialog.setFile(setToField.getText());
        
    }//GEN-LAST:event_setTo
    
    private void reset(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset
        fileChooser = null;
        fileDialog = null;
        setTo(evt);
    }//GEN-LAST:event_reset
    
    private void saveFileChooser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileChooser
        configureFileChooser();
        int option = fileChooser.showSaveDialog(this);
        analyzeOption(fileChooser, option);
        
    }//GEN-LAST:event_saveFileChooser
    
    private void openFileChooser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileChooser
        configureFileChooser();
         int option = fileChooser.showOpenDialog(this);
        analyzeOption(fileChooser, option);
        
    }//GEN-LAST:event_openFileChooser
    
    private void saveFileDialog(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileDialog
        configureFileDialog();
        fileDialog.setMode(FileDialog.SAVE);
        fileDialog.setVisible(true);
        analyzeOption(fileDialog);
        
    }//GEN-LAST:event_saveFileDialog
    
    private void openFileDialog(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileDialog
        configureFileDialog();
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);
        analyzeOption(fileDialog);
    }//GEN-LAST:event_openFileDialog
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton createWithButton;
    private javax.swing.JTextField customApproveField;
    private javax.swing.JCheckBox customApproveItem;
    private javax.swing.JTextField filterFilesField;
    private javax.swing.JCheckBox filterFilesItem;
    private javax.swing.JCheckBox hiddenFilesItem;
    private javax.swing.JCheckBox multiSelectionItem;
    private javax.swing.JButton openFileButton;
    private javax.swing.JButton openFileDialogButton;
    private javax.swing.JTextArea outputField;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JButton resetButton;
    private javax.swing.JPanel resetPanel;
    private javax.swing.JButton saveFileButton;
    private javax.swing.JButton saveFileDialogButton;
    private javax.swing.JRadioButton selectDirectoriesOnlyItem;
    private javax.swing.JRadioButton selectFilesAndDirectoriesItem;
    private javax.swing.JRadioButton selectFilesOnlyItem;
    private javax.swing.ButtonGroup selectionTypeGroup;
    private javax.swing.JButton setToButton;
    private javax.swing.JTextField setToField;
    private javax.swing.JPanel settingsPanel;
    // End of variables declaration//GEN-END:variables
    
}
