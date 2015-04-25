/*
 * @(#)ch_randelshofer_quaqua_osx_Application.c  1.0  2007-01-15
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

/**
 * Native code for class ch.randelshofer.quaqua.osx.Application.
 *
 * @version 1.0 2007-01-15 Created.
 */

#include <stdio.h>
#include <jni.h>
#include "ch_randelshofer_quaqua_osx_Application.h"
#import <Cocoa/Cocoa.h>
/*
 * Class:     ch_randelshofer_quaqua_osx_Application
 * Method:    requestUserAttention
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_ch_randelshofer_quaqua_osx_Application_jniRequestUserAttention
  (JNIEnv * env, jclass javaClass, jboolean isCritical) 
{
    if (isCritical) {
        [NSApp requestUserAttention: NSCriticalRequest];
    } else {
        [NSApp requestUserAttention: NSInformationalRequest];
    }
}

/*
 * Class:     ch_randelshofer_quaqua_osx_Application
 * Method:    jniGetIconImage
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_ch_randelshofer_quaqua_osx_Application_jniGetIconImage
  (JNIEnv * env, jclass javaClass, jint size)
{
    // Allocate a memory pool
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];

    // Get the icon image
	NSApplication* application = [NSApplication sharedApplication];
	NSSize iconSize = { size, size };
	NSImage* image = [application applicationIconImage];
    /* Don't scale image here - apparently the algorithm only uses bilinear
       interpolation. For optimal results, we need bicubic interpolation.
	image = [image copy];
	[image setScalesWhenResized:true];
	[image setSize:iconSize];
    */
	NSData* data = [image TIFFRepresentation];
	unsigned len = [data length];
	void* bytes = malloc(len);
	[data getBytes:bytes];

	jbyteArray result = (*env)->NewByteArray(env, len);
	(*env)->SetByteArrayRegion(env, result, 0, len, (jbyte*)bytes);
	free(bytes);


    // Release memory pool
	[pool release];

	return result;
}
