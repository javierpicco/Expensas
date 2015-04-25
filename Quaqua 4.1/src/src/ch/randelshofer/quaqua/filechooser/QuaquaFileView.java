/*
 * @(#)QuaquaFileView.java  1.0  August 26, 2005
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

package ch.randelshofer.quaqua.filechooser;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
/**
 * A FileView for the Quaqua Look and Feel. 
 *
 * @author  Werner Randelshofer
 * @version 1.0 August 26, 2005 Created.
 */
public class QuaquaFileView extends FileView {
    private QuaquaFileSystemView fsv;
    
    /**
     * Creates a new instance.
     */
    public QuaquaFileView(QuaquaFileSystemView fsv) {
        this.fsv = fsv;
    }
    
    /**
     * The name of the file. Normally this would be simply f.getName()
     */
    public String getName(File f) {
            return fsv.getSystemDisplayName(f);
    }

    /**
     * A human readable description of the file. For example,
     * a file named jag.jpg might have a description that read:
     * "A JPEG image file of James Gosling's face"
     */
    public String getDescription(File f) {
	return null;
    }

    /**
     * A human readable description of the type of the file. For
     * example, a jpg file might have a type description of:
     * "A JPEG Compressed Image File"
     */
    public String getTypeDescription(File f) {
	return fsv.getSystemTypeDescription(f);
    }

    /**
     * The icon that represents this file in the JFileChooser.
     */
    public Icon getIcon(File f) {
	return fsv.getSystemIcon(f);
    }

    /**
     * Whether the directory is traversable or not. This might be
     * useful, for example, if you want a directory to represent
     * a compound document and don't want the user to descend into it.
     */
    public Boolean isTraversable(File f) {
	return fsv.isTraversable(f);
    }
}
