/*
 * @(#)QuaquaJaguarFileChooserUI.java  1.7.3  2006-04-23
 *
 * Copyright (c) 2003-2006 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.jaguar;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.filechooser.*;
import ch.randelshofer.quaqua.jaguar.filechooser.*;
import ch.randelshofer.quaqua.util.*;

//import ch.randelshofer.gui.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.beans.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

/**
 * A replacement for the AquaFileChooserUI. Provides a column view similar
 * to the one provided with the native Aqua user interface on Mac OS X 10.2
 * (Jaguar).
 *
 * @author Werner Randelshofer
 * @version 1.7.3 2006-04-23 Labels are now retrieved directly from UIManager. 
 * <br>1.7.2 2005-11-07 Get "Labels" resource bundle from UIManager.
 * <br>1.7.1 2005-06-05 Moved calls to System.getProperty into QuaquaManager.
 * <br>1.7 2005-04-22 Tweaked the layout and localized the texts.
 * <br>1.6.1 2004-12-28 Call clearIconCache() on the IconView, if this
 * method is available.
 * <br>1.6 2004-10-31 updateSelection had no effect, when a file with
 * a relative path was used. Support for dropping files on the file chooser
 * added. File and directory selection is now handled in a more straightforward
 * way - this may affect code that depends on the values of methods
 * JFileChooser.getSelectedFiles(), JFileChooser.getSelectedFile() while the
 * file chooser is being shown.
 * <br>1.5.1 2004-10-21 Double clicking the browsers approves the selection.
 * When there is an approvable selection, the approve button is made the default
 * button.
 * <br>1.5 2004-10-17 Resolve alias files. Do not clear fileName text
 * field, when the user selects a directory. Handle relative path names.
 * Display the real name of a file in the file name text field. Enable the
 * approve button only, when the right kind of file (or directory) is selected.
 * Treat non-traversable directories like files. Fixed a bug where the file
 * name in the file name field was used to determine the approved file altough
 * the field was not visible.
 * <br>1.4.4 2004-09-11 Replaced all method invocations to method
 * QuaquaManager.getProperty to QuaquaManager.getProperty.
 * <br>1.4.3 2004-08-28 Set the FileView of the JFileChooser.
 * <br>1.4.2 2004-07-30 Set the text of the approve button to "Choose", when
 * directory selection is enabled.
 * 1.4.1 2004-07-28 After creating a new folder, make the newly created
 * folder the current directory. Fixed a bug which caused that no new folder was
 * created. DirectoryComboBox was not always updated when
 * a directory was selected.
 * <br>1.4 2004-06-26 Cell renderer draws different expanded/expanding
 * icon when selected. Renamed from QuaquaFileChooserUI to
 * QuaquaJaguarFileChooserUI.
 * <br>1.3 2004-05-02 Cell renderer draws visually different selection
 * colors when browser has focus. Each change in the fileNameTextField triggers
 * a change of the selected file in the JFileChooser. When the JFileChooser is
 * no longer shown, we stop autovalidation of the tree model, (instead of
 * invalidating it). Clear the file name text field, when the user selects
 * another directory. The 'New Folder' action created the new folder at the
 * wrong directory. Fixed an NPE in
 * QuaquaJaguarFileChooserUI.DoubleClickListener.mouseClicked().
 * <br>1.2 2004-03-20 Automatic validation of the tree model can be switched
 * off by setting the  system property "Quaqua.FileChooser.autovalidate" to
 * "false".
 * JFileChooser.SAVE_DIALOG mode: When the user selects a file in the browser,
 * we set the file name in the fileNameTextField. This is similar to the
 * behaviour of the FileDialog for Mac OS X 10.3 (Panther). When the file
 * chooser is in JFileChooser.SAVE_DIALOG mode, double clicking a file name does
 * not trigger an 'accept' of the file anymore.
 * <br>1.1  2004-03-14 When the file chooser is in JFileChooser.SAVE_DIALOG
 * mode, file names are grayed out. The tree model is validated (refreshed) only,
 * when the JFileChooser is showing. The OSXFileSystemView is now created using a
 * factory. There are now two different strategies to invalidate the tree model:
 * lazyInvalidate and invalidate.
 * <br>1.0.4 2004-02-12 The JBrowser is updating asynchronously. When an
 * update occures between the two clicks of a double click, users may
 * accidently open or save to the wrong file. We ignore know double clicks, if
 * the first click and the second click occured on different files.
 * <br>1.0.3 2004-02-06 Fixed a bug which prevented the approve button from
 * become enabled, when multiple file selection was turned on. This bug occured
 * on JDK 1.3 only, it did not occur on JDK 1.4 and higher versions.
 * <br>1.0.1 2003-11-12 Added approveSelectionAction as a listener
 * to the fileNameTextField.
 * <br>1.0 2003-10-06 Good enough to bear this version number.
 * <br>0.1 2003-07-24  Created.
 *
 */
public class QuaquaJaguarFileChooserUI extends BasicFileChooserUI {
    
    // Implementation derived from MetalFileChooserUI
    
    /* Models. */
    private DirectoryComboBoxModel directoryComboBoxModel;
    private Action directoryComboBoxAction = new DirectoryComboBoxAction();
    private FileView fileView;
    private FilterComboBoxModel filterComboBoxModel;
    private AliasFileSystemTreeModel model = null;
    
    // Preferred and Minimum sizes for the dialog box
    
    private static int PREF_WIDTH = 430;
    private static int PREF_HEIGHT = 330;
    private static Dimension PREF_SIZE = new Dimension(PREF_WIDTH, PREF_HEIGHT);
    
    private static int MIN_WIDTH = 430;
    private static int MIN_HEIGHT = 330;
    private static Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);
    
    // Labels, mnemonics, and tooltips (oh my!)
    private int    lookInLabelMnemonic = 0;
    private String lookInLabelText = null;
    private String saveInLabelText = null;
    
    private int    fileNameLabelMnemonic = 0;
    private String fileNameLabelText = null;
    
    private int    filesOfTypeLabelMnemonic = 0;
    private String filesOfTypeLabelText = null;
    
    private String upFolderToolTipText = null;
    private String upFolderAccessibleName = null;
    
    private String homeFolderToolTipText = null;
    private String homeFolderAccessibleName = null;
    
    private String newFolderToolTipText = null;
    private String newFolderAccessibleName = null;
    
    protected String chooseButtonText = null;
    
    private String
    newFolderDialogPrompt,
    newFolderDefaultName,
    newFolderErrorText,
    newFolderExistsErrorText,
    newFolderButtonText,
    newFolderTitleText;
    
    /**
     * This listener is used to determine whether the JFileChooser is showing.
     */
    private AncestorListener ancestorListener;
    
    /**
     * This listener is used to handle files that were dropped on the file chooser.
     */
    private FileTransferHandler fileTransferHandler;
    
    /**
     * Actions.
     */
    private Action newFolderAction = new NewFolderAction();
    private Action approveSelectionAction = new ApproveSelectionAction();
    
    
    /**
     * Values greater zero indicate that the UI is adjusting.
     * This is required to prevent the UI from changing the FileChooser's state
     * while processing a PropertyChangeEvent fired from the FileChooser.
     */
    private int isAdjusting = 0;
    
    // Variables declaration - do not modify
    private javax.swing.JPanel accessoryPanel;
    private javax.swing.JButton approveButton;
    private ch.randelshofer.quaqua.JBrowser browser;
    private javax.swing.JScrollPane browserScrollPane;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox directoryComboBox;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JLabel filesOfTypeLabel;
    private javax.swing.JComboBox filterComboBox;
    private javax.swing.JPanel formatPanel;
    private javax.swing.JPanel formatPanel2;
    private javax.swing.JPanel fromPanel;
    private javax.swing.JLabel lookInLabel;
    private javax.swing.JButton newFolderButton;
    private javax.swing.JPanel separatorPanel;
    private javax.swing.JPanel separatorPanel1;
    private javax.swing.JPanel separatorPanel2;
    private javax.swing.JPanel strutPanel1;
    private javax.swing.JPanel strutPanel2;
    // End of variables declaration
    
    //
    // ComponentUI Interface Implementation methods
    //
    public static ComponentUI createUI(JComponent c) {
        return new QuaquaJaguarFileChooserUI((JFileChooser) c);
    }
    
    public QuaquaJaguarFileChooserUI(JFileChooser filechooser) {
        super(filechooser);
    }
    
    public void installUI(JComponent c) {
        super.installUI(c);
    }
    
    public void uninstallComponents(JFileChooser fc) {
        fc.removeAll();
        buttonPanel = null;
    }
    
    public void installComponents(JFileChooser fc) {
        FileSystemView fsv = fc.getFileSystemView();
        
        // Form definition  - do not modify
        java.awt.GridBagConstraints gridBagConstraints;
        
        fromPanel = new javax.swing.JPanel();
        fileNameLabel = new javax.swing.JLabel();
        fileNameTextField = new javax.swing.JTextField();
        strutPanel1 = new javax.swing.JPanel();
        lookInLabel = new javax.swing.JLabel();
        directoryComboBox = new javax.swing.JComboBox();
        strutPanel2 = new javax.swing.JPanel();
        separatorPanel1 = new javax.swing.JPanel();
        separatorPanel2 = new javax.swing.JPanel();
        browserScrollPane = new javax.swing.JScrollPane();
        browser = new ch.randelshofer.quaqua.JBrowser();
        newFolderButton = new javax.swing.JButton();
        separatorPanel = new javax.swing.JPanel();
        formatPanel = new javax.swing.JPanel();
        formatPanel2 = new javax.swing.JPanel();
        filesOfTypeLabel = new javax.swing.JLabel();
        filterComboBox = new javax.swing.JComboBox();
        accessoryPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        approveButton = new javax.swing.JButton();
        
        fc.setLayout(new java.awt.GridBagLayout());
        
        fromPanel.setLayout(new java.awt.GridBagLayout());
        
        fileNameLabel.setText(UIManager.getString("FileChooser.fileNameLabelText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 14, 0);
        fromPanel.add(fileNameLabel, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 14, 0);
        gridBagConstraints.weightx = 1.0;
        fromPanel.add(fileNameTextField, gridBagConstraints);
        
        strutPanel1.setLayout(null);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 5;
        fromPanel.add(strutPanel1, gridBagConstraints);
        
        lookInLabel.setText(UIManager.getString("FileChooser.fromLabelText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        fromPanel.add(lookInLabel, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        fromPanel.add(directoryComboBox, gridBagConstraints);
        
        strutPanel2.setLayout(new java.awt.BorderLayout());
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 5;
        fromPanel.add(strutPanel2, gridBagConstraints);
        
        separatorPanel1.setLayout(new java.awt.BorderLayout());
        
        separatorPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("Separator.foreground"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0E-4;
        fromPanel.add(separatorPanel1, gridBagConstraints);
        
        separatorPanel2.setLayout(new java.awt.BorderLayout());
        
        separatorPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Separator.foreground"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 1;
        fromPanel.add(separatorPanel2, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        fc.add(fromPanel, gridBagConstraints);
        
        browserScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        browserScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        browserScrollPane.setViewportView(browser);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 23, 0, 23);
        fc.add(browserScrollPane, gridBagConstraints);
        
        newFolderButton.setText(UIManager.getString("FileChooser.newFolderButtonText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        fc.add(newFolderButton, gridBagConstraints);
        
        separatorPanel.setLayout(new java.awt.BorderLayout());
        
        separatorPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Separator.foreground"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        fc.add(separatorPanel, gridBagConstraints);
        
        formatPanel.setLayout(new java.awt.GridBagLayout());
        
        formatPanel2.setLayout(new java.awt.BorderLayout(2, 0));
        
        filesOfTypeLabel.setText(UIManager.getString("FileChooser.filesOfTypeLabelText"));
        formatPanel2.add(filesOfTypeLabel, java.awt.BorderLayout.WEST);
        
        formatPanel2.add(filterComboBox, java.awt.BorderLayout.CENTER);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        formatPanel.add(formatPanel2, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        fc.add(formatPanel, gridBagConstraints);
        
        accessoryPanel.setLayout(new java.awt.BorderLayout());
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(14, 20, 0, 20);
        fc.add(accessoryPanel, gridBagConstraints);
        
        buttonPanel.setLayout(new java.awt.GridBagLayout());
        
        cancelButton.setText(UIManager.getString("FileChooser.cancelButtonText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 16, 0);
        buttonPanel.add(cancelButton, gridBagConstraints);
        
        approveButton.setText(UIManager.getString("FileChooser.openButtonText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 16, 22);
        buttonPanel.add(approveButton, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        fc.add(buttonPanel, gridBagConstraints);
        // End of form definition
        
        //Configure JBrowser
        browser.setCellRenderer(
        new FileRenderer(
        fc,
        UIManager.getIcon("Browser.expandingIcon"),
        UIManager.getIcon("Browser.expandedIcon"),
        UIManager.getIcon("Browser.selectedExpandingIcon"),
        UIManager.getIcon("Browser.selectedExpandedIcon")
        )
        );
        if (fc.isMultiSelectionEnabled()) {
            browser.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        } else {
            browser.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        }
        browser.setModel(getTreeModel());
        browser.setPrototypeCellValue(getTreeModel().getPrototypeValue());
        browser.addTreeSelectionListener(createBrowserSelectionListener(fc));
        browser.addMouseListener(createDoubleClickListener(fc));
        
        // Configure separator panels
        separatorPanel.setOpaque(true);
        separatorPanel1.setOpaque(true);
        separatorPanel2.setOpaque(true);
        
        // Configure Format Panel
        formatPanel.setVisible(fc.getChoosableFileFilters().length > 1);
        
        // Configure Accessory Panel
        JComponent accessory = fc.getAccessory();
        if(accessory != null) {
            getAccessoryPanel().add(accessory);
        } else {
            accessoryPanel.setVisible(false);
        }
        
        // Text assignment
        lookInLabel.setText(lookInLabelText);
        lookInLabel.setDisplayedMnemonic(lookInLabelMnemonic);
        newFolderButton.setText(newFolderTitleText);
        newFolderButton.setToolTipText(newFolderToolTipText);
        fileNameLabel.setText(fileNameLabelText);
        fileNameLabel.setDisplayedMnemonic(fileNameLabelMnemonic);
        
        approveButton.setText(getApproveButtonText(fc));
        // Note: Metal does not use mnemonics for approve and cancel
        approveButton.addActionListener(getApproveSelectionAction());
        approveButton.setToolTipText(getApproveButtonToolTipText(fc));
        
        cancelButton.setText(cancelButtonText);
        cancelButton.setToolTipText(cancelButtonToolTipText);
        cancelButton.addActionListener(getCancelSelectionAction());
        
        if(! fc.getControlButtonsAreShown()) {
            cancelButton.setVisible(false);
            approveButton.setVisible(false);
        }
        // End of Text assignment
        
        // Model and Renderer assignment
        directoryComboBoxModel = createDirectoryComboBoxModel(fc);
        directoryComboBox.setModel(directoryComboBoxModel);
        directoryComboBox.setRenderer(createDirectoryComboBoxRenderer(fc));
        filterComboBoxModel = createFilterComboBoxModel();
        fc.addPropertyChangeListener(filterComboBoxModel);
        filterComboBox.setModel(filterComboBoxModel);
        filterComboBox.setRenderer(createFilterComboBoxRenderer());
        // Model and Renderer assignment
        
        // Listener assignment
        directoryComboBox.addActionListener(directoryComboBoxAction);
        newFolderButton.addActionListener(getNewFolderAction());
        fileNameTextField.addFocusListener(new SaveTextFocusListener());
        fileNameTextField.getDocument()
        .addDocumentListener(new SaveTextDocumentListener());
        fileNameTextField.addActionListener(getApproveSelectionAction());
        // End of listener assignment
        
        // Drag and drop assignment
        fileTransferHandler = new FileTransferHandler(fc);
        Component[] dropComponents = {
            fc,
            accessoryPanel,
            approveButton,
            browser,
            browserScrollPane,
            buttonPanel,
            cancelButton,
            directoryComboBox,
            fileNameLabel,
            fileNameTextField,
            filesOfTypeLabel,
            filterComboBox,
            formatPanel,
            formatPanel2,
            fromPanel,
            lookInLabel,
            newFolderButton,
            separatorPanel,
            separatorPanel1,
            separatorPanel2,
            strutPanel1,
            strutPanel2
        };
        for (int i=0; i < dropComponents.length; i++) {
            new DropTarget(dropComponents[i], DnDConstants.ACTION_COPY,fileTransferHandler);
        }
        // End of drag and drop assignment
        
        
        // Change component visibility to match the dialog type
        boolean isSave = (fc.getDialogType() == JFileChooser.SAVE_DIALOG)
        || (fc.getDialogType() == JFileChooser.CUSTOM_DIALOG);
        lookInLabel.setText((isSave) ? saveInLabelText : lookInLabelText);
        fileNameLabel.setVisible(isSave);
        fileNameTextField.setVisible(isSave);
        fileNameTextField.setEnabled(isSave);
        updateSeparatorPanelVisibility();
        separatorPanel1.setVisible(isSave);
        separatorPanel2.setVisible(isSave);
        separatorPanel1.setVisible(isSave);
        newFolderButton.setVisible(isSave);
    }
    
    public JPanel getAccessoryPanel() {
        return accessoryPanel;
    }
    
    protected void installStrings(JFileChooser fc) {
        super.installStrings(fc);
        
        Locale l;
        try {
            l = fc.getLocale();
        } catch (IllegalComponentStateException e) {
            l = Locale.getDefault();
        }
        
        chooseButtonText   = UIManager.getString("FileChooser.chooseButtonText"/*,l*/);
        
        lookInLabelMnemonic = UIManager.getInt("FileChooser.lookInLabelMnemonic");
        lookInLabelText = UIManager.getString("FileChooser.lookInLabelText"/*,l*/);
        if (lookInLabelText == null) lookInLabelText = UIManager.getString("FileChooser.fromLabelText");
        saveInLabelText = UIManager.getString("FileChooser.saveInLabelText"/*,l*/);
        if (saveInLabelText == null) saveInLabelText = UIManager.getString("FileChooser.whereLabelText");
        
        fileNameLabelMnemonic = UIManager.getInt("FileChooser.fileNameLabelMnemonic");
        fileNameLabelText = UIManager.getString("FileChooser.fileNameLabelText"/*,l*/);
        // XXX - Localize "Save as:" text.
        //if (fileNameLabelText == null || fileNameLabelText.charAt(fileNameLabelText.length() -1) != ':') fileNameLabelText = "Save as:";
        
        filesOfTypeLabelMnemonic = UIManager.getInt("FileChooser.filesOfTypeLabelMnemonic");
        filesOfTypeLabelText = UIManager.getString("FileChooser.filesOfTypeLabelText"/*,l*/);
        
        upFolderToolTipText =  UIManager.getString("FileChooser.upFolderToolTipText"/*,l*/);
        upFolderAccessibleName = UIManager.getString("FileChooser.upFolderAccessibleName"/*,l*/);
        
        homeFolderToolTipText =  UIManager.getString("FileChooser.homeFolderToolTipText"/*,l*/);
        homeFolderAccessibleName = UIManager.getString("FileChooser.homeFolderAccessibleName"/*,l*/);
        
        newFolderToolTipText = UIManager.getString("FileChooser.newFolderToolTipText"/*,l*/);
        newFolderAccessibleName = UIManager.getString("FileChooser.newFolderAccessibleName"/*,l*/);
        
        // New Folder Dialog
        newFolderErrorText = getString("FileChooser.newFolderErrorText", l, "Error occured during folder creation");
        newFolderExistsErrorText = getString("FileChooser.newFolderExistsErrorText", l, "That name is already taken");
        newFolderButtonText = getString("FileChooser.newFolderButtonText", l, "New");
        newFolderTitleText = getString("FileChooser.newFolderTitleText", l, "New Folder");
        newFolderDialogPrompt = getString("FileChooser.newFolderPromptText", l, "Name of new folder:");
        newFolderDefaultName = getString("FileChooser.untitledFolderName", l, "untitled folder");
        newFolderTitleText = UIManager.getString("FileChooser.newFolderTitleText"/*, l*/);
        newFolderToolTipText = UIManager.getString("FileChooser.newFolderToolTipText"/*, l*/);
        newFolderAccessibleName = getString("FileChooser.newFolderAccessibleName", l, newFolderTitleText);
    }
    
    /**
     * Gets a locale dependent string.
     */
    private String getString(String string, Locale l, String defaultValue) {
        String value = UIManager.getString(string/*, l*/);
        return (value == null) ? defaultValue : value;
    }
    
    /**
     * Installs listeners.
     * We install the same listeners as BasicFileChooserUI plus an
     * AncestorListener.
     */
    protected void installListeners(JFileChooser fc) {
        super.installListeners(fc);
        ancestorListener = createAncestorListener(fc);
        if(ancestorListener != null) {
            fc.addAncestorListener(ancestorListener);
        }
    }
    protected void uninstallListeners(JFileChooser fc) {
        super.uninstallListeners(fc);
        if(ancestorListener != null) {
            fc.removeAncestorListener(ancestorListener);
        }
    }
    /**
     * Creates an AncestorListener.
     * The AncestorListener is used to take an action when the JFileChooser becomes
     * showing on screen.
     */
    protected AncestorListener createAncestorListener(JFileChooser fc) {
        return new FileChooserAncestorListener();
    }
    
    public void createModel() {
        JFileChooser fc = getFileChooser();
        model = new AliasFileSystemTreeModel(fc);
        model.setResolveFileLabels(false);
        fileView = QuaquaFileSystemView.getQuaquaFileSystemView().createFileView(fc);
        // FIXME - We should not overwrite the FileView attribute
        // of the JFileChooser.
        fc.setFileView(fileView);
        
        // FIXME - We should not overwrite the FileSystemView attribute
        // of the JFileChooser.
        fc.setFileSystemView(QuaquaFileSystemView.getQuaquaFileSystemView());
    }
    public AliasFileSystemTreeModel getTreeModel() {
        return model;
    }
    public void uninstallUI(JComponent c) {
        // Remove listeners
        c.removePropertyChangeListener(filterComboBoxModel);
        cancelButton.removeActionListener(getCancelSelectionAction());
        approveButton.removeActionListener(getApproveSelectionAction());
        fileNameTextField.removeActionListener(getApproveSelectionAction());
        
        super.uninstallUI(c);
    }
    
    /**
     * The array contains the selected file(s) of the JFileChooser.
     * All files have an absolute path.
     * If no file is selected, the length of the array is 0.
     * Always returns a non-null value.
     * All array elements are non-null.
     */
    private File[] getSelectedFiles() {
        JFileChooser fc = getFileChooser();
        
        if (fc.isMultiSelectionEnabled()) {
            File[] selectedFiles = fc.getSelectedFiles();
            ArrayList list = new ArrayList(selectedFiles.length);
            for (int i=0; i < selectedFiles.length; i++) {
                if (selectedFiles[i] != null) {
                    if (selectedFiles[i].isAbsolute()) {
                        list.add(selectedFiles[i]);
                    } else {
                        list.add(new File(QuaquaManager.getProperty("user.home"),selectedFiles[i].getName()));
                    }
                }
            }
            return (File[]) list.toArray(new File[list.size()]);
        } else {
            File f = fc.getSelectedFile();
            if (f == null) {
                return new File[0];
            } else {
                if (f.isAbsolute()) {
                    return new File[] { f };
                } else {
                    return new File[] { new File(QuaquaManager.getProperty("user.home"),f.getName()) };
                }
            }
        }
    }
    
    /**
     * Updates the selection in the JBrowser, to match the selected file/s
     * of the JFileChooser.
     */
    private void updateSelection() {
        JFileChooser fc = getFileChooser();
        
        File[] files = getSelectedFiles();
        if (files.length != 0) {
            TreePath[] paths = new TreePath[files.length];
            ArrayList list = new ArrayList(paths.length);
            for (int i=0; i < files.length; i++) {
                File file = files[i];
                boolean isDirectory = file.isDirectory() && fc.isTraversable(file);
                if (files.length == 1 || ! isDirectory || fc.isDirectorySelectionEnabled()) {
                    list.add(getTreeModel().toPath(file, browser.getSelectionPath()));
                }
            }
            if (list.size() == 0 && files.length > 0) {
                list.add(fc.getFileSystemView().getParentDirectory(files[0]));
            }
            browser.setSelectionPaths((TreePath[]) list.toArray(new TreePath[list.size()]));
            
            // XXX If the selected file is not accepted by the file
            // name filters, we have to write its name into the file name field.
            if (files.length == 1
            && ! files[0].isDirectory() || ! fc.isTraversable(files[0])) {
                setFileName(files[0].getName());
            }
        }
        
        if (files.length == 0) {
            directoryComboBoxModel.addItem(fc.getCurrentDirectory());
        } else if (files[0].isDirectory()) {
            directoryComboBoxModel.addItem(files[0]);
        } else {
            directoryComboBoxModel.addItem(files[0].getParentFile());
        }
        
        updateApproveButtonState();
    }
    
    /**
     * Returns true, if the file name field contains a file name.
     */
    private boolean isFileNameFieldValid() {
        String string = getFileName();
        return string != null && !string.equals("");
    }
    /**
     * Returns true, if the file name field is visible.
     */
    private boolean isFileNameFieldVisible() {
        JFileChooser fc = getFileChooser();
        return (fc.getDialogType() == JFileChooser.SAVE_DIALOG)
        || (fc.getDialogType() == JFileChooser.CUSTOM_DIALOG);
    }
    
    
    
    
    private void updateApproveButtonState() {
        JFileChooser fc = getFileChooser();
        
        if (fc.getControlButtonsAreShown()) {
            File[] files = getSelectedFiles();
            
            boolean isFileSelected = false;
            boolean isDirectorySelected = false;
            for (int i=0; i < files.length; i++) {
                if (files[i].isDirectory() && fc.isTraversable(files[i])) {
                    isDirectorySelected = true;
                } else {
                    isFileSelected = true;
                }
            }
            boolean isEnabled = false;
            switch (fc.getFileSelectionMode()) {
                case JFileChooser.FILES_ONLY :
                    isEnabled = isFileSelected || isFileNameFieldVisible() && isFileNameFieldValid();
                    break;
                case JFileChooser.DIRECTORIES_ONLY :
                    /*
                    isEnabled = ! isFileSelected
                    && (isDirectorySelected || isFileNameFieldVisible() && isFileNameFieldValid());
                     **/
                    isEnabled = ! isFileSelected || files.length == 1 && ! files[0].exists();
                    break;
                case JFileChooser.FILES_AND_DIRECTORIES :
                    /*
                    isEnabled = isFileSelected || isDirectorySelected
                    || isFileNameFieldVisible() && isFileNameFieldValid();
                     */
                    isEnabled = true;
                    break;
            }
            approveButton.setEnabled(isEnabled);
            if (isEnabled) {
                JRootPane rp = approveButton.getRootPane();
                if (rp != null) {
                    rp.setDefaultButton(approveButton);
                }
            }
        }
    }
    private void updateApproveButtonText() {
        JFileChooser fc = getFileChooser();
        
        approveButton.setText(getApproveButtonText(fc));
        approveButton
        .setToolTipText(getApproveButtonToolTipText(fc));
        approveButton.setMnemonic(getApproveButtonMnemonic(fc));
        //cancelButton.setToolTipText(getCancelButtonToolTipText(fc));
    }
    protected TreeSelectionListener createBrowserSelectionListener(JFileChooser fc) {
        return new BrowserSelectionListener();
    }
    /**
     * Selection listener for the list of files and directories.
     */
    protected class BrowserSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            if (isAdjusting != 0) return;
            JFileChooser fc = getFileChooser();
            FileSystemView fsv = fc.getFileSystemView();
            TreePath path = browser.getSelectionPath();
            
            if (path != null) {
                model.lazyInvalidatePath(path);
                model.validatePath(path);
            }
            
            
            TreePath[] paths = browser.getSelectionPaths();
            
            // Determine the selected files. If multiple files are selected,
            // we strip directories from this list, if the JFileChooser does
            // not allow directory selection.
            int count = 0;
            File[] files = new File[(paths == null) ? 0 : paths.length];
            ArrayList list = new ArrayList(files.length);
            for (int i=0; i < files.length; i++) {
                File file = ((AliasFileSystemTreeModel.Node) paths[i].getLastPathComponent()).getResolvedFile();
                boolean isDirectory = file.isDirectory() && fc.isTraversable(file);
                if (files.length == 1 || ! isDirectory || fc.isDirectorySelectionEnabled()) {
                    list.add(file);
                }
            }
            
            
            if (fc.isMultiSelectionEnabled()) {
                fc.setSelectedFiles((File[]) list.toArray(new File[list.size()]));
            } else {
                fc.setSelectedFile((list.size() > 0) ? (File) list.get(0) : null);
            }
        }
    }
    
    /**
     * Returns the preferred size of the specified
     * <code>JFileChooser</code>.
     * The preferred size is at least as large,
     * in both height and width,
     * as the preferred size recommended
     * by the file chooser's layout manager.
     *
     * @param c  a <code>JFileChooser</code>
     * @return   a <code>Dimension</code> specifying the preferred
     *           width and height of the file chooser
     */
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = c.getLayout().preferredLayoutSize(c);
        if (d != null) {
            return new Dimension(
            Math.max(d.width, PREF_SIZE.width),
            Math.max(d.height, PREF_SIZE.height)
            );
        } else {
            return new Dimension(PREF_SIZE.width, PREF_SIZE.height);
        }
    }
    
    /**
     * Returns the minimum size of the <code>JFileChooser</code>.
     *
     * @param c  a <code>JFileChooser</code>
     * @return   a <code>Dimension</code> specifying the minimum
     *           width and height of the file chooser
     */
    public Dimension getMinimumSize(JComponent c) {
        return MIN_SIZE;
    }
    
    /**
     * Returns the maximum size of the <code>JFileChooser</code>.
     *
     * @param c  a <code>JFileChooser</code>
     * @return   a <code>Dimension</code> specifying the maximum
     *           width and height of the file chooser
     */
    public Dimension getMaximumSize(JComponent c) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    
    /* The following methods are used by the PropertyChange Listener */
    
    private void doSelectedFileChanged(PropertyChangeEvent e) {
        updateSelection();
    }
    
    private void doSelectedFilesChanged(PropertyChangeEvent e) {
        updateSelection();
    }
    
    private void doDirectoryChanged(PropertyChangeEvent e) {
        JFileChooser fc = getFileChooser();
        FileSystemView fsv = fc.getFileSystemView();
        
        File[] files = getSelectedFiles();
        
        if (files.length == 0) {
            File dir = (File) e.getNewValue();
            directoryComboBoxModel.addItem(dir);
            browser.setSelectionPath(model.toPath(dir, browser.getSelectionPath()));
            model.lazyInvalidatePath(browser.getSelectionPath());
            
            if(dir != null) {
                getNewFolderAction().setEnabled(dir.canWrite());
                getChangeToParentDirectoryAction().setEnabled(!fsv.isRoot(dir));
                
                if (fc.getDialogType() == JFileChooser.OPEN_DIALOG) {
                    updateApproveButtonState();
                }
                
            }
        }
    }
    
    private void doFilterChanged(PropertyChangeEvent e) {
        clearIconCache();
        model.invalidatePath(browser.getSelectionPath());
        if (getFileChooser().isShowing()) {
            model.validatePath(browser.getSelectionPath());
        }
    }
    
    private void doFileSelectionModeChanged(PropertyChangeEvent e) {
        //Commented out, because there is no reason for clearing the icon cache
        //in this situation.
        //clearIconCache();
        
        JFileChooser fc = getFileChooser();
        File currentDirectory = fc.getCurrentDirectory();
        //setFileName(null);
        updateApproveButtonText();
        updateApproveButtonState();
    }
    
    private void doMultiSelectionChanged(PropertyChangeEvent e) {
        if (getFileChooser().isMultiSelectionEnabled()) {
            browser.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        } else {
            browser.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            getFileChooser().setSelectedFiles(null);
        }
    }
    
    private void doChoosableFilterChanged(PropertyChangeEvent e) {
        JFileChooser fc = getFileChooser();
        boolean isChooserVisible = ((FileFilter[]) e.getNewValue()).length > 1;
        formatPanel.setVisible(isChooserVisible);
        updateSeparatorPanelVisibility();
    }
    private void doAccessoryChanged(PropertyChangeEvent e) {
        if(getAccessoryPanel() != null) {
            if(e.getOldValue() != null) {
                getAccessoryPanel().remove((JComponent) e.getOldValue());
            }
            JComponent accessory = (JComponent) e.getNewValue();
            if(accessory != null) {
                getAccessoryPanel().add(accessory, BorderLayout.CENTER);
            }
            accessoryPanel.setVisible(accessory != null);
        }
        updateSeparatorPanelVisibility();
    }
    
    private void doApproveButtonTextChanged(PropertyChangeEvent e) {
        JFileChooser chooser = getFileChooser();
        approveButton.setText(getApproveButtonText(chooser));
        approveButton.setToolTipText(getApproveButtonToolTipText(chooser));
    }
    
    private void doDialogTypeChanged(PropertyChangeEvent e) {
        JFileChooser fc = getFileChooser();
        approveButton.setText(getApproveButtonText(fc));
        approveButton.setToolTipText(getApproveButtonToolTipText(fc));
        boolean isSave = isFileNameFieldVisible();
        lookInLabel.setText((isSave) ? saveInLabelText : lookInLabelText);
        fileNameLabel.setVisible(isSave);
        fileNameTextField.setVisible(isSave);
        fileNameTextField.setEnabled(isSave);
        updateSeparatorPanelVisibility();
        separatorPanel1.setVisible(isSave);
        separatorPanel2.setVisible(isSave);
        separatorPanel1.setVisible(isSave);
        newFolderButton.setVisible(isSave);
        model.setResolveAliasesToFiles(! isSave);
    }
    
    private void doApproveButtonMnemonicChanged(PropertyChangeEvent e) {
        // Note: Metal does not use mnemonics for approve and cancel
    }
    
    private void doControlButtonsChanged(PropertyChangeEvent e) {
        if(getFileChooser().getControlButtonsAreShown()) {
            addControlButtons();
        } else {
            removeControlButtons();
        }
    }
    
    /*
     * Listen for filechooser property changes, such as
     * the selected file changing, or the type of the dialog changing.
     */
    public PropertyChangeListener createPropertyChangeListener(JFileChooser fc) {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                isAdjusting++;
                
                String s = e.getPropertyName();
                if (s.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                    doSelectedFileChanged(e);
                } else if (s.equals(JFileChooser.SELECTED_FILES_CHANGED_PROPERTY)) {
                    doSelectedFilesChanged(e);
                } else if(s.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
                    doDirectoryChanged(e);
                } else if(s.equals(JFileChooser.FILE_FILTER_CHANGED_PROPERTY)) {
                    doFilterChanged(e);
                } else if(s.equals(JFileChooser.FILE_SELECTION_MODE_CHANGED_PROPERTY)) {
                    doFileSelectionModeChanged(e);
                } else if(s.equals(JFileChooser.MULTI_SELECTION_ENABLED_CHANGED_PROPERTY)) {
                    doMultiSelectionChanged(e);
                } else if(s.equals(JFileChooser.ACCESSORY_CHANGED_PROPERTY)) {
                    doAccessoryChanged(e);
                } else if(s.equals(JFileChooser.CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY)) {
                    doChoosableFilterChanged(e);
                } else if (s.equals(JFileChooser.APPROVE_BUTTON_TEXT_CHANGED_PROPERTY) ||
                s.equals(JFileChooser.APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY)) {
                    doApproveButtonTextChanged(e);
                } else if(s.equals(JFileChooser.DIALOG_TYPE_CHANGED_PROPERTY)) {
                    doDialogTypeChanged(e);
                } else if(s.equals(JFileChooser.APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY)) {
                    doApproveButtonMnemonicChanged(e);
                } else if(s.equals(JFileChooser.CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY)) {
                    doControlButtonsChanged(e);
                } else if (s.equals("componentOrientation")) {
                    /* FIXME - This needs JDK 1.4 to work.
                    ComponentOrientation o = (ComponentOrientation)e.getNewValue();
                    JFileChooser fc = (JFileChooser)e.getSource();
                    if (o != (ComponentOrientation)e.getOldValue()) {
                        fc.applyComponentOrientation(o);
                    }
                     */
                } else if (s.equals("ancestor")) {
                    if (e.getOldValue() == null && e.getNewValue() != null) {
                        // Ancestor was added, ensure path is visible and
                        // set initial focus
                        browser.ensurePathIsVisible(browser.getSelectionPath());
                        fileNameTextField.selectAll();
                        fileNameTextField.requestFocus();
                    }
                }
                
                isAdjusting--;
            }
        };
    }
    
    private void updateSeparatorPanelVisibility() {
        JFileChooser fc = getFileChooser();
        
        boolean isSave = (fc.getDialogType() == JFileChooser.SAVE_DIALOG)
        || (fc.getDialogType() == JFileChooser.CUSTOM_DIALOG);
        
        separatorPanel.setVisible(
        isSave && (
        fc.getControlButtonsAreShown()
        || ! fc.isAcceptAllFileFilterUsed()
        || fc.getAccessory() != null
        )
        );
    }
    
    protected void removeControlButtons() {
        buttonPanel.setVisible(false);
        updateSeparatorPanelVisibility();
    }
    
    protected void addControlButtons() {
        buttonPanel.setVisible(true);
        updateSeparatorPanelVisibility();
    }
    
    
    private void ensurePathIsVisible(TreePath path) {
        browser.ensurePathIsVisible(path);
    }
    
    
    public String getFileName() {
        if (fileNameTextField != null) {
            return fileNameTextField.getText();
        } else {
            return null;
        }
    }
    
    public void setFileName(String filename) {
        if (fileNameTextField != null && (filename == null || !fileNameTextField.getText().equals(filename))) {
            fileNameTextField.setText(filename);
        }
    }
    
    protected DirectoryComboBoxRenderer createDirectoryComboBoxRenderer(JFileChooser fc) {
        return new DirectoryComboBoxRenderer();
    }
    
    
    //
    // Renderer for DirectoryComboBox
    //
    class DirectoryComboBoxRenderer extends DefaultListCellRenderer  {
        final File root = new File("/");
        IndentIcon ii = new IndentIcon();
        public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected,
        boolean cellHasFocus) {
            
            
            // String objects are used to denote delimiters.
            if (value instanceof String) {
                super.getListCellRendererComponent(list, value, index, false, cellHasFocus);
                setText((String) value);
                setPreferredSize(new Dimension(10, 14));
                return this;
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setPreferredSize(null);
            File directory = (File) value;
            /*
            if (directory == null || directory.equals(root)) {
                setText(getFileChooser().getName(root));
                //ii.icon = getFileChooser().getIcon(root);
                ii.icon = UIManager.getIcon("FileView.computerIcon");
            } else {*/
            setText(getFileChooser().getName(directory));
            ii.icon = getFileChooser().getIcon(directory);
            //}
            ii.depth = 0;
            setIcon(ii);
            return this;
        }
    }
    
    final static int space = 10;
    class IndentIcon implements Icon {
        
        Icon icon = null;
        int depth = 0;
        
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (icon != null) {
                if (c.getComponentOrientation().isLeftToRight()) {
                    icon.paintIcon(c, g, x+depth*space, y);
                } else {
                    icon.paintIcon(c, g, x, y);
                }
            }
        }
        
        public int getIconWidth() {
            return (icon == null) ? depth*space : icon.getIconWidth() + depth*space;
        }
        
        public int getIconHeight() {
            return (icon == null) ? 0 : icon.getIconHeight();
        }
        
    }
    
    //
    // DataModel for DirectoryComboxbox
    //
    protected DirectoryComboBoxModel createDirectoryComboBoxModel(JFileChooser fc) {
        return new DirectoryComboBoxModel();
    }
    
    /**
     * Data model for a directory selection combo-box.
     */
    protected class DirectoryComboBoxModel extends AbstractListModel
    implements ComboBoxModel {
        Object directories[] = new Object[5];
        Object selectedDirectory = null;
        JFileChooser chooser = getFileChooser();
        FileSystemView fsv = chooser.getFileSystemView();
        
        public DirectoryComboBoxModel() {
            // Add the current directory to the model, and make it the
            // selectedDirectory
            File dir = getFileChooser().getCurrentDirectory();
            if(dir != null) {
                addItem(dir);
            }
            
            // Hardcode this.
            // The QuaquaJaguarFileChooserUI only works on Mac OS X anyway.
            directories[0] = new File(QuaquaManager.getProperty("user.home"));
            directories[1] = ""; // We use empty String's to denote separators.
            directories[2] = new File(QuaquaManager.getProperty("user.home"), "Desktop");
            directories[3] = new File(QuaquaManager.getProperty("user.home"));
            directories[4] = new File("/");
        }
        
        /**
         * Adds the directory to the model and sets it to be selected,
         * additionally clears out the previous selected directory and
         * the paths leading up to it, if any.
         */
        private void addItem(File directory) {
            isAdjusting++;
            directories[0] = directory;
            selectedDirectory = directory;
            fireContentsChanged(this, -1, -1);
            fireContentsChanged(this, 0, 0);
            isAdjusting--;
        }
        
        public void setSelectedItem(Object selectedDirectory) {
            if (selectedDirectory instanceof File) {
                this.selectedDirectory = (File) selectedDirectory;
                fireContentsChanged(this, -1, -1);
            }
        }
        
        public Object getSelectedItem() {
            return selectedDirectory;
        }
        
        public int getSize() {
            return directories.length;
        }
        
        public Object getElementAt(int index) {
            return directories[index];
        }
    }
    
    //
    // Renderer for Types ComboBox
    //
    protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
        return new FilterComboBoxRenderer();
    }
    
    /**
     * Render different type sizes and styles.
     */
    public class FilterComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list,
        Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value != null && value instanceof FileFilter) {
                setText(((FileFilter)value).getDescription());
            }
            
            return this;
        }
    }
    
    //
    // DataModel for Types Comboxbox
    //
    protected FilterComboBoxModel createFilterComboBoxModel() {
        return new FilterComboBoxModel();
    }
    
    /**
     * Data model for a type-face selection combo-box.
     */
    protected class FilterComboBoxModel
    extends AbstractListModel
    implements ComboBoxModel, PropertyChangeListener {
        protected FileFilter[] filters;
        protected FilterComboBoxModel() {
            super();
            filters = getFileChooser().getChoosableFileFilters();
        }
        
        public void propertyChange(PropertyChangeEvent e) {
            String prop = e.getPropertyName();
            if(prop == JFileChooser.CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY) {
                filters = (FileFilter[]) e.getNewValue();
                fireContentsChanged(this, -1, -1);
            } else if (prop == JFileChooser.FILE_FILTER_CHANGED_PROPERTY) {
                fireContentsChanged(this, -1, -1);
            }
        }
        
        public void setSelectedItem(Object filter) {
            if(filter != null) {
                getFileChooser().setFileFilter((FileFilter) filter);
                setFileName(null);
                fireContentsChanged(this, -1, -1);
            }
        }
        
        public Object getSelectedItem() {
            // Ensure that the current filter is in the list.
            // NOTE: we shouldnt' have to do this, since JFileChooser adds
            // the filter to the choosable filters list when the filter
            // is set. Lets be paranoid just in case someone overrides
            // setFileFilter in JFileChooser.
            FileFilter currentFilter = getFileChooser().getFileFilter();
            boolean found = false;
            if(currentFilter != null) {
                for(int i=0; i < filters.length; i++) {
                    if(filters[i] == currentFilter) {
                        found = true;
                    }
                }
                if(found == false) {
                    getFileChooser().addChoosableFileFilter(currentFilter);
                }
            }
            return getFileChooser().getFileFilter();
        }
        
        public int getSize() {
            if(filters != null) {
                return filters.length;
            } else {
                return 0;
            }
        }
        
        public Object getElementAt(int index) {
            if(index > getSize() - 1) {
                // This shouldn't happen. Try to recover gracefully.
                return getFileChooser().getFileFilter();
            }
            if(filters != null) {
                return filters[index];
            } else {
                return null;
            }
        }
    }
    
    /**
     * Acts when DirectoryComboBox has changed the selected item.
     */
    protected class DirectoryComboBoxAction extends AbstractAction {
        protected DirectoryComboBoxAction() {
            super("DirectoryComboBoxAction");
        }
        
        public void actionPerformed(ActionEvent e) {
            if (isAdjusting != 0) return;
            
            JFileChooser fc = getFileChooser();
            File file = (File) directoryComboBox.getSelectedItem();
            if (file != null) {
                if (fc.isMultiSelectionEnabled()) {
                    fc.setSelectedFiles(new File[] { file });
                } else {
                    fc.setSelectedFile(file);
                }
            }
        }
    }
    
    protected JButton getApproveButton(JFileChooser fc) {
        return approveButton;
    }
    
    public Action getApproveSelectionAction() {
        return approveSelectionAction;
    }
    
    protected class DoubleClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            // Note: We must not react on mouse clicks with clickCount=1.
            //       Because this interfers with the mouse handling code in
            //       the JBrowser which does list selection.
            JFileChooser fc = getFileChooser();
            if (SwingUtilities.isLeftMouseButton(e)
            && e.getClickCount() == 2
            && fc.getDialogType() != JFileChooser.SAVE_DIALOG) {
                maybeApproveSelection();
            }
        }
    }
    /**
     * Responds to an Open or Save request
     */
    protected class ApproveSelectionAction extends AbstractAction {
        protected ApproveSelectionAction() {
            super("approveSelection");
        }
        public void actionPerformed(ActionEvent e) {
            maybeApproveSelection();
        }
    }
    
    /**
     * This method is called, when the user double clicks the JBrowser, or
     * when she clicks at the approve button.
     */
    private void maybeApproveSelection() {
        JFileChooser fc = getFileChooser();
        File selectedFile = null;
        File[] selectedFiles = null;
        
        String filename = null;
        if (isFileNameFieldVisible()) {
            filename = getFileName();
            if (filename.equals("")) filename = null;
        }
        
        if (fc.isMultiSelectionEnabled()) {
            TreePath[] selectedPaths = browser.getSelectionPaths();
            if (filename != null) {
                selectedFiles = new File[] {
                    new File(
                    fc.getFileSystemView().getParentDirectory(
                    ((AliasFileSystemTreeModel.Node) selectedPaths[0]
                    .getLastPathComponent()).lazyGetResolvedFile()
                    ),
                    filename
                    )
                };
            } else {
                selectedFiles = new File[selectedPaths.length];
                for (int i=0; i < selectedPaths.length; i++) {
                    selectedFiles[i] = ((AliasFileSystemTreeModel.Node) selectedPaths[i].getLastPathComponent()).getResolvedFile();
                }
            }
            
        } else {
            selectedFile = ((AliasFileSystemTreeModel.Node) browser.getSelectionPath().getLastPathComponent()).getResolvedFile();
            if (filename != null) {
                selectedFile = new File(selectedFile.isDirectory() && fc.isTraversable(selectedFile) ? selectedFile : fc.getFileSystemView().getParentDirectory(selectedFile), filename);
            }
            if (fc.getFileSelectionMode() == JFileChooser.FILES_ONLY
            && selectedFile.isDirectory() && fc.isTraversable(selectedFile)) {
                // Abort we cannot approve a directory
                return;
            }
        }
        
        if (selectedFiles != null || selectedFile != null) {
            if (selectedFiles != null) {
                fc.setSelectedFiles(selectedFiles);
            } else if (fc.isMultiSelectionEnabled()) {
                fc.setSelectedFiles(new File[] { selectedFile });
            } else {
                fc.setSelectedFile(selectedFile);
            }
            fc.approveSelection();
        } else {
            if (fc.isMultiSelectionEnabled()) {
                fc.setSelectedFiles(null);
            } else {
                fc.setSelectedFile(null);
            }
            fc.cancelSelection();
        }
        
    }
    
    // *****************************
    // ***** Directory Actions *****
    // *****************************
    
    public Action getNewFolderAction() {
        return newFolderAction;
    }
    /**
     * Creates a new folder.
     */
    protected class NewFolderAction extends AbstractAction {
        protected NewFolderAction() {
            super("New Folder");
        }
        
        private String showNewFolderDialog() {
            JOptionPane optionPane = new JOptionPane(
            newFolderDialogPrompt,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION
            );
            optionPane.setWantsInput(true);
            optionPane.setInitialSelectionValue(newFolderDefaultName);
            JDialog dialog = optionPane.createDialog(getFileChooser(), newFolderTitleText);
            dialog.show();
            dialog.dispose();
            
            return (optionPane.getInputValue() == JOptionPane.UNINITIALIZED_VALUE) ? null : (String) optionPane.getInputValue();
        }
        
        public void actionPerformed(ActionEvent actionevent) {
            JFileChooser fc = getFileChooser();
            String newFolderName = showNewFolderDialog();
            
            if (newFolderName != null) {
                
                File newFolder;
                File currentFile = ((AliasFileSystemTreeModel.Node) browser.getSelectionPath().getLastPathComponent()).getResolvedFile();
                if (! currentFile.isDirectory() || ! fc.isTraversable(currentFile)) {
                    currentFile = fc.getFileSystemView().getParentDirectory(currentFile);
                }
                newFolder = new File(currentFile, newFolderName);
                if (newFolder.exists()) {
                    JOptionPane.showMessageDialog(
                    fc,
                    newFolderExistsErrorText,
                    newFolderTitleText, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    newFolder.mkdir();
                    fc.rescanCurrentDirectory();
                    if (fc.isMultiSelectionEnabled()) {
                        fc.setSelectedFiles(new File[] { newFolder });
                    } else {
                        fc.setSelectedFile(newFolder);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                    fc,
                    newFolderErrorText,
                    newFolderTitleText, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    protected class SaveTextFocusListener implements FocusListener {
        public void focusGained(FocusEvent focusevent) {
            updateApproveButtonState();
        }
        
        public void focusLost(FocusEvent focusevent) {
            /* empty */
        }
    }
    protected class SaveTextDocumentListener implements DocumentListener {
        public void insertUpdate(DocumentEvent documentevent) {
            textChanged();
        }
        
        public void removeUpdate(DocumentEvent documentevent) {
            textChanged();
        }
        
        public void changedUpdate(DocumentEvent documentevent) {
            /* empty */
        }
        
        private void textChanged() {
            if (isAdjusting != 0) return;
            
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFileChooser fc = getFileChooser();
                    File file = ((AliasFileSystemTreeModel.Node) browser.getSelectionPath().getLastPathComponent()).getResolvedFile();
                    if (fileNameTextField.getText().length() != 0) {
                        if (file.isDirectory() && fc.isTraversable(file)) {
                            file = new File(file, fileNameTextField.getText());
                        } else {
                            file = new File(fc.getFileSystemView().getParentDirectory(file), fileNameTextField.getText());
                        }
                    }
                    if (fc.isMultiSelectionEnabled()) {
                        fc.setSelectedFiles(new File[] { file });
                    } else {
                        fc.setSelectedFile(file);
                    }
                }
            });
        }
    }
    /**
     * The FileChooserAncestorListener listens for visibility changes of
     * the JFileChooser.
     * This is used to do validations (refreshes) of the tree model only,
     * when the JFileChooser is showing.
     */
    private class FileChooserAncestorListener implements AncestorListener {
        public void ancestorAdded(AncestorEvent event) {
            if (model != null) {
                model.setAutoValidate(QuaquaManager.getBoolean("FileChooser.autovalidate"));
                model.validatePath(browser.getSelectionPath());
            }
            updateApproveButtonState();
        }
        
        public void ancestorRemoved(AncestorEvent event) {
            if (model != null) {
                model.setAutoValidate(false);
                model.stopValidation();
                model.invalidatePath(browser.getSelectionPath());
                clearIconCache();
            }
        }
        
        public void ancestorMoved(AncestorEvent event) {
        }
        
    }
    // *******************************************************
    // ************* FileChooserUI PLAF methods **************
    // *******************************************************
    
    public void ensureFileIsVisible(JFileChooser fc, File f) {
        if (f != null) {
            if (! f.isAbsolute()) {
                f = new File(QuaquaManager.getProperty("user.home"), f.getName());
            }
            ensurePathIsVisible(getTreeModel().toPath(f, browser.getSelectionPath()));
        }
    }
    public String getApproveButtonText(JFileChooser fc) {
        String buttonText = fc.getApproveButtonText();
        if (buttonText != null) {
            return buttonText;
        } else if (fc.isDirectorySelectionEnabled() && chooseButtonText != null) {
            return chooseButtonText;
        } else if (fc.getDialogType() == JFileChooser.OPEN_DIALOG) {
            return openButtonText;
        } else if (fc.getDialogType() == JFileChooser.SAVE_DIALOG) {
            return saveButtonText;
        } else {
            return null;
        }
    }
    public FileView getFileView(JFileChooser fc) {
        return fileView;
    }
    public void rescanCurrentDirectory(JFileChooser fc) {
        // Validation is only necessary, when the JFileChooser is showing.
        if (fc.isShowing()) {
            //clearIconCache();
            model.lazyInvalidatePath(browser.getSelectionPath());
            model.validatePath(browser.getSelectionPath());
        }
    }
    // *******************************************************
    // ******** End of FileChooserUI PLAF methods ************
    // *******************************************************
    
    // *******************************************************
    // ********** BasicFileChooserUI PLAF methods ************
    // *******************************************************
    public void clearIconCache() {
        try {
            fileView.getClass()
            .getMethod("clearIconCache", new Class[0])
            .invoke(fileView, new Object[0]);
        } catch (Exception e) {
            // empty
        }
    }
    protected MouseListener createDoubleClickListener(JFileChooser fc) {
        return new DoubleClickListener();
    }
    
    // *******************************************************
    // ******* End of BasicFileChooserUI PLAF methods ********
    // *******************************************************
    
}