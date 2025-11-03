#include "Mverify.h"
#include "signer.h"
#include <string>
#include <android/log.h>

#define LOG_TAG "Mverify"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static std::string jstring2string(JNIEnv *env, jstring jstr) {
    if (!jstr) {
        return "";
    }

    const char *chars = env->GetStringUTFChars(jstr, nullptr);
    std::string result(chars);
    env->ReleaseStringUTFChars(jstr, chars);
    return result;
}

static std::string getApkPath(JNIEnv *env, jobject thiz) {
    try {
      jclass contextClass = env->FindClass("android/content/Context");
        if (!contextClass) {
            LOGE("Failed to find Context class");
            return "";
        }

      jmethodID getPackageCodePathMethod = env->GetMethodID(
                contextClass,
                "getPackageCodePath",
                "()Ljava/lang/String;"
        );

        if (!getPackageCodePathMethod) {
            LOGE("Failed to find getPackageCodePath method");
            return "";
        }

      jstring pathJStr = (jstring) env->CallObjectMethod(thiz, getPackageCodePathMethod);
        if (!pathJStr) {
            LOGE("Failed to get APK path");
            return "";
        }

        std::string path = jstring2string(env, pathJStr);
        env->DeleteLocalRef(pathJStr);
        env->DeleteLocalRef(contextClass);

        LOGD("APK Path: %s", path.c_str());
        return path;

    } catch (...) {
        LOGE("Exception occurred while getting APK path");
        return "";
    }
}

JNIEXPORT jstring JNICALL
Java_com_mylrc_mymusic_tool_MusicUrlHelper_tmc(
        JNIEnv *env,
        jobject thiz,
        jstring jtext1,
        jstring jtext2
) {
  std::string text1 = jstring2string(env, jtext1);
    std::string text2 = jstring2string(env, jtext2);

    LOGD("tmc called: text1=%s, text2=%s", text1.c_str(), text2.c_str());

  std::string apkPath = getApkPath(env, thiz);

  Signer::SignResult result = Signer::generateSign(text1, text2, apkPath);

  std::string hexOutput = result.toHexString();

    LOGD("Sign generated: sign1=%s, sign2=%s",
         result.sign1.c_str(), result.sign2.c_str());
    LOGD("Hex output length: %zu", hexOutput.length());

  return env->NewStringUTF(hexOutput.c_str());
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGD("libMverify loaded successfully");
    return JNI_VERSION_1_6;
}