#ifndef SIGNER_H
#define SIGNER_H

#include <string>
#include <ctime>

/**
 * 签名生成器
 * 实现原libMverify.so中的双层签名机制
 */
class Signer {
public:
    // 签名结果结构体
    struct SignResult {
        std::string text1;      // 输入文本1
        std::string text2;      // 输入文本2
        std::string sign1;      // 第一层签名
        std::string timestamp;  // 时间戳
        std::string sign2;      // 第二层签名

        // 转为JSON格式字符串
        std::string toJson() const;

        // 转为十六进制格式（原库中的最终输出格式）
        std::string toHexString() const;
    };

    /**
     * 生成签名
     * @param text1 输入文本1
     * @param text2 输入文本2
     * @param apkPath APK文件路径（用于计算文件MD5）
     * @return 签名结果
     */
    static SignResult generateSign(const std::string &text1,
                                   const std::string &text2,
                                   const std::string &apkPath = "");

    /**
     * 生成sign_1
     * sign_1 = MD5(文件MD5 + 时间戳 + SALT1)
     */
    static std::string generateSign1(const std::string &fileMd5,
                                     const std::string &timestamp);

    /**
     * 生成sign_2
     * sign_2 = MD5(text1 + text2 + sign1 + 时间戳 + SALT2)
     */
    static std::string generateSign2(const std::string &text1,
                                     const std::string &text2,
                                     const std::string &sign1,
                                     const std::string &timestamp);

    // 获取当前时间戳字符串
    static std::string getTimestamp();

private:
    // 硬编码的密钥（从原库中提取）
    static constexpr const char *SALT1 = "9765643845533561346698467313346766565243";
    static constexpr const char *SALT2 = "orksEOprOjSEmTHW";  // Base64解码后的值
};

#endif // SIGNER_H