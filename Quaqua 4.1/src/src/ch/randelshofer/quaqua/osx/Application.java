/*
 * @(#)Application.java  2.1  2007-11-24
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

package ch.randelshofer.quaqua.osx;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.io.*;
import ch.randelshofer.quaqua.ext.batik.ext.awt.image.codec.tiff.*;
import ch.randelshofer.quaqua.ext.batik.ext.awt.image.codec.util.*;

/**
 * Application.
 *
 * @author Werner Randelshofer
 * @version 2.1 2007-11-24 Only use Java to Cocoa Bridge when running on 
 * Mac OS X. On UnsatisfiedLinkError retry with absolute
 * path to load libquaqua.jnilib.
 * <br>2.0.8 2007-09-08 Security exception in static initalizer of this
 * class caused that option pane icons were not displayed.
 * <br>2.0.1 2007-07-25 Method getIconImage returned icon with wrong
 * size, when no native code was available.
 * <br>2.0 2007-04-28 Added methods getIconImage, jniGetIconImage.
 * <br>1.0 January 15, 2007 Created.
 */
public class Application {
    private final static boolean DEBUG = true;
    /**
     * This variable is set to true, if native code is available.
     */
    private final static boolean isNativeCodeAvailable;
    
    /**
     * Load the native code.
     * FIXME - Replace this by a lazy implementation which loads the JNI library
     * only when it is needed.
     */
    static {
        boolean success;
        
        String value = QuaquaManager.getProperty("Quaqua.jniIsPreloaded");
        if (value == null) {
            value = QuaquaManager.getProperty("Quaqua.JNI.isPreloaded");
        }
        if (value != null && value.equals("true")) {
            success = true;
        } else {
            try {
                System.loadLibrary("quaqua");
                success = true;
            } catch (UnsatisfiedLinkError e) {
                String libpath=new File(System.getProperty("java.library.path"),"libquaqua.jnilib").getAbsolutePath();
                try {
                    Runtime.getRuntime().load(libpath);
                    success = true;
                } catch (Throwable e2) {
                System.err.println("Warning: "+Application.class+" couldn't load library libquaqua.jnilib. "+e);
                    System.err.println("Warning: "+Application.class+" couldn't load library with path "+libpath);
                    success = false;
                }
                //e.printStackTrace();
            } catch (Throwable e) {
                //e.printStackTrace();
                System.err.println("Warning: "+Application.class+" couldn't load library libquaqua.jnilib. "+e);
                success = false;
            }
        }
        isNativeCodeAvailable = success;
    }
    
    /** Prevent instance creation. */
    private Application() {
    }
    /**
     * Requests user attention through JNI or through Cocoa Java.
     * This method will fail silently if neither JNI nor Cocoa Java is available.
     *
     * @param requestCritical Set this to true, if your application invokes
     * a modal dialog. Set this to false, in all other cases.
     */
    public static void requestUserAttention(boolean requestCritical) {
        if (isNativeCodeAvailable) {
            jniRequestUserAttention(true);
        } else {
            // We may only use the Java to Cocoa Bridge when we run on OS X.
            // If we run on Darwin unter OS X, this will crash our application
            // with the following console message:
            // "ObjCJava FATAL: Detected more than one VM... ObjCJava Exit".
            
            if (QuaquaManager.isOSX()) {
            /*
            NSApplication app = NSApplication.sharedApplication();
            int id = app.requestUserAttention(
                                    NSApplication.UserAttentionRequestInformational);
             */
                try {
                    Object app = Methods.invokeStatic("com.apple.cocoa.application.NSApplication", "sharedApplication");
                    Methods.invoke(app, "requestUserAttention", app.getClass().getDeclaredField("UserAttentionRequestInformational").getInt(app));
                } catch (Throwable ex) {
                    System.err.println("Quaqua Warning: Couldn't invoke NSApplication.requestUserAttention");
                }
            }
        }
    }
    
    /**
     * Requests user attention through JNI.
     * @param requestCritical Set this to true, if your application invokes
     * a modal dialog. Set this to false, in all other cases.
     * @exception java.lang.UnsatisfiedLinkError if JNI is not available.
     */
    private static native void jniRequestUserAttention(boolean requestCritical);
    
    /**
     * Returns the icon image of the application.
     *
     * @param size the desired size of the icon in pixels (width and height)
     * @return The application image. Returns a generic application image if
     * JNI is not available.
     */
    public static BufferedImage getIconImage(int size) {
        BufferedImage image = null;
        if (isNativeCodeAvailable) {
            try {
                byte[] tiffData = jniGetIconImage(size);
                
                TIFFImageDecoder decoder = new TIFFImageDecoder(
                        new MemoryCacheSeekableStream(new ByteArrayInputStream(tiffData)),
                        new TIFFDecodeParam()
                        );
                
                RenderedImage rImg = decoder.decodeAsRenderedImage(0);
                image = Images.toBufferedImage(rImg);
                /*
                if (rImg instanceof BufferedImage) {
                    image = (BufferedImage) rImg;
                } else {
                    Raster r = rImg.getData();
                    WritableRaster wr = WritableRaster.createWritableRaster(
                            r.getSampleModel(), null);
                    rImg.copyData(wr);
                    image = new BufferedImage(
                            rImg.getColorModel(),
                            wr,
                            rImg.getColorModel().isAlphaPremultiplied(),
                            null
                            );
                }*/
            } catch (IOException ex) {
                if (DEBUG) ex.printStackTrace();
                // suppress, we return a default image
            }
        }
        
        if (image == null) {
            image = Images.toBufferedImage(
                    Images.createImage(
                    QuaquaIconFactory.class.getResource("/ch/randelshofer/quaqua/images/ApplicationIcon.png")
                    )
                    );
        }
        
        if (image.getWidth() != size) {
            BufferedImage tmp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = tmp.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(image, 0, 0, size, size, null);
            g.dispose();
            image = tmp;
            // Unfortunately, the following code does not yield optimal results
            // (we get black pixels instead of translucent pixels)
            // image = Images.toBufferedImage(image.getScaledInstance(size, size, BufferedImage.SCALE_SMOOTH));
        }
        
        return image;
    }
    
    /**
     * Returns the icon image of the application through JNI.
     *
     * @param size the desired size of the icon in pixels (width and height)
     * @return Byte array with TIFF image data or null in case of failure.
     */
    private static native byte[] jniGetIconImage(int size);
    
}
