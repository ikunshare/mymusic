#include "Mverify.h"
#include "base64.h"
#include "cJSON.h"
#include "md5.h"
#include <string>
#include <cstring>
#include <ctime>
#include <cstdio>
#include <cstdlib>
#include <fcntl.h>
#include <unistd.h>
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
    std::string methodName = Base64::decodeToString("Z2V0UGFja2FnZUNvZGVQYXRo");
    std::string className =
        Base64::decodeToString("YW5kcm9pZC9hcHAvQWN0aXZpdHlUaHJlYWQ=");
    std::string
        currentAppMethod = Base64::decodeToString("Y3VycmVudEFwcGxpY2F0aW9u");
    std::string currentAppSig =
        Base64::decodeToString("KClMYW5kcm9pZC9hcHAvQXBwbGljYXRpb247");
    std::string
        methodSig = Base64::decodeToString("KClMamF2YS9sYW5nL1N0cmluZzs=");

    jclass activityThreadClass = env->FindClass(className.c_str());
    jmethodID currentAppMethodID = env->GetStaticMethodID(activityThreadClass,
                                                          currentAppMethod.c_str(),
                                                          currentAppSig.c_str());
    jobject applicationObj =
        env->CallStaticObjectMethod(activityThreadClass, currentAppMethodID);
    jclass applicationClass = env->GetObjectClass(applicationObj);
    jmethodID getApkPathMethodID = env->GetMethodID(applicationClass,
                                                    methodName.c_str(),
                                                    methodSig.c_str());
    jobject
        apkPathObj = env->CallObjectMethod(applicationObj, getApkPathMethodID);

    const char *chars = env->GetStringUTFChars((jstring)
    apkPathObj, nullptr);
    std::string path(chars);
    env->ReleaseStringUTFChars((jstring)
    apkPathObj, chars);

    LOGD("APK Path: %s", path.c_str());
    return path;
  } catch (...) {
    LOGE("Exception occurred while getting APK path");
    return "";
  }
}

static std::string calculateApkMD5(const char *apkPath) {
  return MD5::hashFile(apkPath);
}

extern "C" {

JNIEXPORT jstring
JNICALL
Java_com_mylrc_mymusic_tool_MusicUrlHelper_tmc(JNIEnv *env,
                                               jobject thiz,
                                               jstring param1,
                                               jstring param2) {
  std::string text1 = jstring2string(env, param1);
  std::string text2 = jstring2string(env, param2);

  std::string apkPath = getApkPath(env, thiz);
  std::string apkMd5 = calculateApkMD5(apkPath.c_str());

  char timeStr[32];
  time_t currentTime = time(nullptr);
  snprintf(timeStr, sizeof(timeStr), "%ld", currentTime);

  std::string sign1 = apkMd5 + std::string(timeStr)
      + "6562653262383463363633646364306534333668";
  std::string encryptedSign1 = MD5::hash(sign1);

  std::string secretKey = Base64::decodeToString("TkRSalpHSXpOemxpTnpFZQ==");
  std::string
      sign2 = text1 + text2 + encryptedSign1 + std::string(timeStr) + secretKey;
  std::string encryptedSign2 = MD5::hash(sign2);

  cJSON *root = cJSON_CreateObject();
  cJSON_AddStringToObject(root, "text_1", text1.c_str());
  cJSON_AddStringToObject(root, "text_2", text2.c_str());
  cJSON_AddStringToObject(root, "sign_1", encryptedSign1.c_str());
  cJSON_AddStringToObject(root, "time", timeStr);
  cJSON_AddStringToObject(root, "sign_2", encryptedSign2.c_str());

  char *jsonString = cJSON_PrintUnformatted(root);
  cJSON_Delete(root);

  std::string hexOutput;
  size_t len = strlen(jsonString);
  for (size_t i = 0; i < len; i++) {
    char hex[3];
    snprintf(hex, sizeof(hex), "%02X", (unsigned char) jsonString[i]);
    hexOutput += hex;
  }

  free(jsonString);

  return env->NewStringUTF(hexOutput.c_str());
}

JNIEXPORT jint
JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
  JNIEnv *env;
  if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
    return JNI_ERR;
  }
  LOGD("libMverify loaded");
  return JNI_VERSION_1_6;
}

}