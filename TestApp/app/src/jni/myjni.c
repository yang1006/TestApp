//
// Created by 杨林龙 on 18/3/16.
//

#include "yll_self_testapp_jni_MyJni.h"
#include <stdio.h>

jstring s = "222";

JNIEXPORT void JNICALL Java_yll_self_testapp_jni_MyJni_set
  (JNIEnv * env, jobject obj, jstring ss){
    s = ss;
  }

/*
 * Class:     yll_self_testapp_jni_MyJni
 * Method:    get
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_yll_self_testapp_jni_MyJni_get
  (JNIEnv * env, jobject obj){
    return (*env)->NewStringUTF(env,  "调用jni成功，还需深入了解");
  }
