#ifndef BASE64_H
#define BASE64_H

#include <string>
#include <vector>
#include <cstdint>

/**
 * Base64编解码工具类
 */
class Base64 {
 public:
  // 编码：将二进制数据转为Base64字符串
  static std::string encode(const uint8_t *data, size_t length);

  static std::string encode(const std::string &str);

  // 解码：将Base64字符串转为二进制数据
  static std::vector <uint8_t> decode(const std::string &encoded);

  static std::string decodeToString(const std::string &encoded);

 private:
  static const std::string BASE64_CHARS;

  static inline bool isBase64(uint8_t c) {
    return (isalnum(c) || (c == '+') || (c == '/'));
  }
};

#endif // BASE64_H