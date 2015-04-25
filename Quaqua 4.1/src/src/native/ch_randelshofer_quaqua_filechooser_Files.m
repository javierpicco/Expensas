/*
 * @(#)ch_randelshofer_quaqua_filechooser_Files.m  3.1  2007-11-25
 *
 * Copyright (c) 2004-2007 Werner Randelshofer
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
 * Native code for class ch.randelshofer.quaqua.filechooser.Files.
 *
 * @version 3.1 2007-11-25 Scale icon images down if they are too big. 
 * <br>3.0 2007-04-28 Rewritten with Cocoa instead of Carbon. Added
 * functions getKindString, getIconImage, based on code by Rolf Howarth.
 * <br>2.0 2007-04-18 Rewritten with better function names.
 * <br>1.0.1 2005-06-18 Fixed signs of variables.
 * <br>1.0 2004-11-04 Created.
 */

#include <stdio.h>
#include <jni.h>
#include "ch_randelshofer_quaqua_filechooser_Files.h"
#import <Cocoa/Cocoa.h>
#import <CoreServices/CoreServices.h>

/*
 * Related documentation:
 * ----------------------
 * Serializing an Alias into a stream of bytes:
 * http://developer.apple.com/qa/qa2004/qa1350.html
 */



/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    getFileType
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_getFileType
  (JNIEnv *env, jclass instance, jstring pathJ) {

    // Assert arguments
    if (pathJ == NULL) return false;

    // Convert Java String to C char array
    const char *pathC;
    pathC = (*env)->GetStringUTFChars(env, pathJ, 0);

    // Do the API calls
    FSRef fileRef;
    OSErr err;
    Boolean isAlias, isFolder;
    err = FSPathMakeRef(pathC, &fileRef, NULL);
    if (err == 0) {
        err = FSIsAliasFile(&fileRef, &isAlias, &isFolder);
    }

    // Release the C char array
    (*env)->ReleaseStringUTFChars(env, pathJ, pathC);

    // Return the result
    return (err == 0) ?
		((isAlias) ? 2 : ((isFolder) ? 1 : 0)) :
		-1;
}
/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    resolveAlias
 * Signature: (Ljava/lang/String;Z)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_resolveAlias
 (JNIEnv *env, jclass instance, jstring aliasPathJ, jboolean noUI)
{
    // Assert arguments
    if (aliasPathJ == NULL) return false;

    // Convert Java filename to C filename
    const char *aliasPathC;
    aliasPathC = (*env)->GetStringUTFChars(env, aliasPathJ, 0);
    
    // Do the API calls
    FSRef fileRef;
    OSErr err;
    OSStatus status;
    Boolean wasAliased, targetIsFolder;
    UInt8 resolvedPathC[2048];

    int outputBufLen;
    err = FSPathMakeRef(aliasPathC, &fileRef, NULL);
    if (err == 0) {
        err = FSResolveAliasFileWithMountFlags(
                             &fileRef, 
                             true, // resolve alias chains
                             &targetIsFolder,
                             &wasAliased,
                             (noUI) ? kResolveAliasFileNoUI : 0 // mount flags
              );
    }
    if (err == 0) {
        if (wasAliased) {
            status = FSRefMakePath(&fileRef, resolvedPathC, 2048);
            if (status != 0) err = 1;
        }
    }

    // Release the C filename
    (*env)->ReleaseStringUTFChars(env, aliasPathJ, aliasPathC);


    // Return the result
    return (err == 0 && wasAliased) ? (*env)->NewStringUTF(env, resolvedPathC) : NULL;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    resolveAliasType
 * Signature: (Ljava/lang/String;Z)I
 */
JNIEXPORT jint JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_resolveAliasType
   (JNIEnv *env, jclass instance, jstring aliasPathJ, jboolean noUI)
{
    // Assert arguments
    if (aliasPathJ == NULL) return false;

    // Convert Java filename to C filename
    const char *aliasPathC;
    aliasPathC = (*env)->GetStringUTFChars(env, aliasPathJ, 0);
    
    // Do the API calls
    FSRef fileRef;
    OSErr err;
    OSStatus status;
    Boolean wasAliased, targetIsFolder;
    UInt8 resolvedPathC[2048];

    int outputBufLen;
    err = FSPathMakeRef(aliasPathC, &fileRef, NULL);
    if (err == 0) {
        err = FSResolveAliasFileWithMountFlags(
                             &fileRef, 
                             false, // resolve alias chains
                             &targetIsFolder,
                             &wasAliased,
                             (noUI) ? kResolveAliasFileNoUI : 0 // mount flags
              );
    }
    if (err == 0) {
        if (wasAliased) {
            status = FSRefMakePath(&fileRef, resolvedPathC, 2048);
            if (status != 0) err = 1;
        }
    }

    // Release the C filename
    (*env)->ReleaseStringUTFChars(env, aliasPathJ, aliasPathC);


    // Return the result
    return (err == 0) ? ((targetIsFolder) ? 1 : 0) : -1;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    toSerializedAlias
 * Signature: (Ljava/lang/String;)[B
 */
JNIEXPORT jbyteArray JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_toSerializedAlias
  (JNIEnv *env, jclass instance, jstring aliasPathJ)
{
    // Assert arguments
    if (aliasPathJ == NULL) return NULL;

    //
    jbyteArray serializedAlias = NULL;

    // Convert Java filename to C filename
    const char *aliasPathC;
    aliasPathC = (*env)->GetStringUTFChars(env, aliasPathJ, 0);
    
    // Do the API calls
    FSRef fileRef;
    OSErr err;
    AliasHandle aliasHdl;
    CFDataRef dataRef;
    const UInt8* dataBytes; // bytes of dataRef
    int length; // length of the dataBytes array

    err = FSPathMakeRef(aliasPathC, &fileRef, NULL);
    if (err == 0) {
        err = FSNewAlias(NULL, &fileRef, &aliasHdl);
    }
    if (err == 0) {
        dataRef = CFDataCreate(
                        kCFAllocatorDefault,
                        (UInt8*) *aliasHdl,
                        GetHandleSize((Handle) aliasHdl)
                  );
        err = (NULL == dataRef);
    }
    if (err == 0) {
        length = CFDataGetLength(dataRef);
        serializedAlias = (*env)->NewByteArray(env, length);
        err = (NULL == serializedAlias);
    }
    if (err == 0) {
        dataBytes = CFDataGetBytePtr(dataRef);
        (*env)->SetByteArrayRegion(env, serializedAlias, 0, length, dataBytes);
    }

    // Release the C filename
    (*env)->ReleaseStringUTFChars(env, aliasPathJ, aliasPathC);

    // Release the other stuff
    if (dataRef != NULL) CFRelease(dataRef);
    if (aliasHdl != NULL) DisposeHandle((Handle) aliasHdl);

    // Return the result
    return serializedAlias;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    jniResolveAlias
 * Signature: ([BZ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_jniResolveAlias
  (JNIEnv *env, jclass instance, jbyteArray serializedAlias, jboolean noUI) 
{
    // Assert arguments
    if (serializedAlias == NULL) return false;

    
    //
    FSRef fileRef;
    OSErr err;
    AliasHandle aliasHdl;
    CFDataRef dataRef;
    UInt8* serializedAliasBytes; // bytes of serializedAlias
    int length; // length of serializedAlias
    UInt8 resolvedPathC[2048];
    Boolean wasChanged;
    OSStatus status;

    length = (*env)->GetArrayLength(env, serializedAlias);
    serializedAliasBytes = (*env)->GetByteArrayElements(env, serializedAlias, NULL);
    err = (NULL == serializedAliasBytes);

    if (err == 0) {
        dataRef = CFDataCreate(kCFAllocatorDefault,
                               (UInt8*) serializedAliasBytes, 
                               length
                  );

        aliasHdl = (AliasHandle) NewHandle(length);
        err = (NULL == aliasHdl);
    }
    if (err == 0) {
        CFDataGetBytes(dataRef,
                       CFRangeMake(0, length),
                       (UInt8*) *aliasHdl
       );
    
        err = FSResolveAliasWithMountFlags(NULL,
                             aliasHdl,
                             &fileRef,
                             &wasChanged,
                             (noUI) ? kResolveAliasFileNoUI : 0
              );
    }
    if (err == 0) {
        status = FSRefMakePath(&fileRef, resolvedPathC, 2048);
        if (status != 0) err = 1;
    }

    // Release allocated stuff
    (*env)->ReleaseByteArrayElements(env, serializedAlias, serializedAliasBytes, JNI_ABORT);
    if (aliasHdl != NULL) DisposeHandle((Handle) aliasHdl);

    // Return the result
    return (err == 0) ? (*env)->NewStringUTF(env, resolvedPathC) : NULL;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    jniResolveAliasType
 * Signature: ([BZ)I
 */
JNIEXPORT jint JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_jniResolveAliasType
  (JNIEnv *env, jclass instance, jbyteArray serializedAlias, jboolean noUI) 
{
    // Assert arguments
    if (serializedAlias == NULL) return false;

    
    //
    OSErr err;
    AliasHandle aliasHdl;
    CFDataRef dataRef;
    UInt8* serializedAliasBytes; // bytes of serializedAlias
    int length; // length of serializedAlias
    UInt8 resolvedPathC[2048];
    OSStatus status;
	FSAliasInfoBitmap whichInfo;
    FSAliasInfo info;

    length = (*env)->GetArrayLength(env, serializedAlias);
    serializedAliasBytes = (*env)->GetByteArrayElements(env, serializedAlias, NULL);
    err = (NULL == serializedAliasBytes);

    if (err == 0) {
        dataRef = CFDataCreate(kCFAllocatorDefault,
                               (UInt8*) serializedAliasBytes, 
                               length
                  );

        aliasHdl = (AliasHandle) NewHandle(length);
        err = (NULL == aliasHdl);
    }
    if (err == 0) {
        CFDataGetBytes(dataRef,
                       CFRangeMake(0, length),
                       (UInt8*) *aliasHdl
       );
    
		err = FSCopyAliasInfo (
			aliasHdl,
			NULL, //targetName
			NULL, //volumeName
			NULL, //pathString
			&whichInfo,
			&info
		);
    }
	
    // Release allocated stuff
    (*env)->ReleaseByteArrayElements(env, serializedAlias, serializedAliasBytes, JNI_ABORT);
    if (aliasHdl != NULL) DisposeHandle((Handle) aliasHdl);

    // Return the result
    return (err == 0 && (whichInfo & kFSAliasInfoIsDirectory) != 0) ?
		 ((info.isDirectory) ? 1 : 0) : 
		 -1;
}
  

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    getLabel
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_getLabel
  (JNIEnv *env, jclass instance, jstring pathJ) {

    // Assert arguments
    if (pathJ == NULL) return -1;

    // Convert Java String to C char array
    const char *pathC = (*env)->GetStringUTFChars(env, pathJ, 0);

    // Do the API calls
    FSRef fileRef;
    OSErr err;
    FSCatalogInfo catalogInfo;
    FInfo *fileInfo;
    int fileLabel;
    err = FSPathMakeRef(pathC, &fileRef, NULL);
    if (err == 0) {
        err = FSGetCatalogInfo(&fileRef, kFSCatInfoFinderInfo, &catalogInfo, NULL, NULL, NULL);
    }
    if (err == 0) {
        fileInfo = (FInfo*) &catalogInfo.finderInfo;
        fileLabel = (fileInfo->fdFlags & 0xe) >> 1;
    }

    // Release the C char array
    (*env)->ReleaseStringUTFChars(env, pathJ, pathC);

    // Return the result
    return (err == 0) ? fileLabel : -1;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    getKindString
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_getKindString
  (JNIEnv *env, jclass instance, jstring pathJ) {

    // Assert arguments
    if (pathJ == NULL) return -1;

    // Convert Java String to C char array
    const char *pathC = (*env)->GetStringUTFChars(env, pathJ, 0);

    // Do the API calls
    FSRef fileRef;
    OSErr err;
	CFStringRef outKindString;
    err = FSPathMakeRef(pathC, &fileRef, NULL);
	jstring kindJ;
    if (err == 0) {
        err = LSCopyKindStringForRef(&fileRef, &outKindString);
    }
    if (err == 0) {
		CFRange range;
		range.location = 0;
		// Note that CFStringGetLength returns the number of UTF-16 characters,
		// which is not necessarily the number of printed/composed characters
		range.length = CFStringGetLength(outKindString);
		UniChar charBuf[range.length];
		CFStringGetCharacters(outKindString, range, charBuf);
		kindJ = (*env)->NewString(env, (jchar *)charBuf, (jsize)range.length);
    }
	CFRelease(outKindString);

    // Release the C char array
    (*env)->ReleaseStringUTFChars(env, pathJ, pathC);

    // Return the result
    return (err == 0) ? kindJ : -1;
}

/*
 * Class:     ch_randelshofer_quaqua_filechooser_Files
 * Method:    getIconImage
 * Signature: (Ljava/lang/String;I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_ch_randelshofer_quaqua_filechooser_Files_getIconImage
  (JNIEnv *env, jclass instance, jstring pathJ, jint size) {

    // Assert arguments
    if (pathJ == NULL) return NULL;

    // Allocate a memory pool
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];

	// Convert Java String to NS String
	const jchar *pathC = (*env)->GetStringChars(env, pathJ, NULL);
	NSString *pathNS = [NSString stringWithCharacters:(UniChar *)pathC
            length:(*env)->GetStringLength(env, pathJ)];

    // Get the icon image
	NSWorkspace* workspace = [NSWorkspace sharedWorkspace];
	NSSize iconSize = { size, size };
	NSImage* image = [workspace iconForFile:pathNS];

    // Set the desired size of the image
    [image setSize:iconSize];

    // Unfortunately, setting the desired size does not always have an effect,
    // we need to choose the best image representation by ourselves.
    NSArray* reps = [image representations];
    NSEnumerator *enumerator = [reps objectEnumerator];
    NSImageRep* imageRep;
    while (imageRep = [enumerator nextObject]) {
        if ([imageRep pixelsWide] == size) {
            image = imageRep;
            break;
        }
    }
    //NSLog (@"%@", image);


	NSData* data = [image TIFFRepresentation];
	unsigned len = [data length];
	void* bytes = malloc(len);
	[data getBytes:bytes];

	jbyteArray result = (*env)->NewByteArray(env, len);
	(*env)->SetByteArrayRegion(env, result, 0, len, (jbyte*)bytes);
	free(bytes);


    // Release the NS String
	(*env)->ReleaseStringChars(env, pathNS, pathC);

    // Release memory pool
	[pool release];

	return result;
}



/*JNI function definitions end*/
