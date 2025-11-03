#ifndef LIBMVERIFY_JNI_H
#define LIBMVERIFY_JNI_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

/**
 * JNI方法：tmc
 * Java包名：com.mylrc.mymusic.tool.MusicUrlHelper
 *
 * @param env JNI环境指针
 * @param thiz Java对象引用
 * @param text1 输入文本1（jstring）
 * @param text2 输入文本2（jstring）
 * @return 签名结果的十六进制字符串（jstring）
 */
JNIEXPORT jstring JNICALL
Java_com_mylrc_mymusic_tool_MusicUrlHelper_tmc(
        JNIEnv *env,
        jobject thiz,
        jstring text1,
        jstring text2
);

#ifdef __cplusplus
}
#endif

#endif // LIBMVERIFY_JNI_H