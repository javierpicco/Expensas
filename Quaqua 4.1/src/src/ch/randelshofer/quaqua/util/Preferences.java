/*
 * @(#)Preferences.java  1.0.1  2006-02-19
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

package ch.randelshofer.quaqua.util;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.ext.nanoxml.*;
import java.io.*;
import java.util.*;
/**
 * Utility class for accessing Mac OS X System Preferences.
 *
 * @author  Werner Randelshofer
 * @version 2006-02-19 Catch Throwable instead of IOException in method
 * load global Preferences.
 * <br>1.0 September 17, 2005 Created.
 */
public class Preferences {
    private static HashMap prefs;
    
    /**
     * Creates a new instance.
     */
    public Preferences() {
    }
    
    public static String getString(String key) {
        return (String) get(key);
    }
    
    public static String getString(String key, String defaultValue) {
        return prefs.containsKey(key) ? (String) get(key) : defaultValue;
    }
    
    public static Object get(String key) {
        if (prefs == null) {
            prefs = new HashMap();
            loadGlobalPreferences();
        }
        //System.out.println("Preferences.get("+key+"):"+prefs.get(key));
        return prefs.get(key);
    }
    
    
    private static void loadGlobalPreferences() {
        // Load Mac OS X global preferences
        // --------------------------------
        
        // Fill preferences with default values, in case we fail to read them
        
        // Appearance: "1"=Blue, "6"=Graphite
        prefs.put("AppleAquaColorVariant","1");
        // Highlight Color: (RGB float values)
        prefs.put("AppleHighlightColor","0.709800 0.835300 1.000000");
        // Collation order: (Language code)
        prefs.put("AppleCollationOrder","en");
        // Place scroll arrows: "Single"=At top and bottom, "DoubleMax"=Together
        prefs.put("AppleScrollBarVariant","DoubleMax");
        // Click in the scroll bar to: "true"=Jump to here, "false"=Jump to next page
        prefs.put("AppleScrollerPagingBehavior","false");
        
        File globalPrefsFile = new File(
        QuaquaManager.getProperty("user.home")
        +"/Library/Preferences/.GlobalPreferences.plist"
        );
        try {
            XMLElement xml = readPList(globalPrefsFile);
            for (Iterator i0 = xml.iterateChildren(); i0.hasNext(); ) {
                XMLElement xml1 = (XMLElement) i0.next();
                
                String key = null;
                for (Iterator i1 = xml1.iterateChildren(); i1.hasNext(); ) {
                    XMLElement xml2 = (XMLElement) i1.next();
                    if (xml2.getName().equals("key")) {
                        key = xml2.getContent();
                    } else {
                        if (key != null) {
                            //System.out.println("Preferences "+key+"="+xml2.getContent());
                            prefs.put(key, xml2.getContent());
                        }
                        key = null;
                    }
                }
            }
        } catch (Throwable e) {
            System.err.println("Warning: ch.randelshofer.quaqua.util.Preferences failed to load Mac OS X global system preferences");
            e.printStackTrace();
        }
    }
    
    
    /**
     * Reads the specified PList file and returns it as an XMLElement.
     * This method can deal with XML encoded and binary encoded PList files.
     */
    private static XMLElement readPList(File plistFile) throws IOException {
        FileReader reader = null;
        XMLElement xml = null;
        try {
            reader =  new FileReader(plistFile);
            xml = new XMLElement(new HashMap(), false, false);
            try {
                xml.parseFromReader(reader);
            } catch (XMLParseException e) {
                xml = new BinaryPListParser().parse(plistFile);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return xml;
    }
}

