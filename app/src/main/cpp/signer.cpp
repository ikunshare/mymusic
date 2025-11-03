#include "signer.h"
#include "md5.h"
#include "base64.h"
#include <sstream>
#include <iomanip>

std::string Signer::generateSign1(const std::string &fileMd5,
                                  const std::string &timestamp) {
  std::string data = fileMd5 + timestamp + SALT1;
    return MD5::hash(data);
}

std::string Signer::generateSign2(const std::string &text1,
                                  const std::string &text2,
                                  const std::string &sign1,
                                  const std::string &timestamp) {
  std::string data = text1 + text2 + sign1 + timestamp + SALT2;
    return MD5::hash(data);
}

std::string Signer::getTimestamp() {
    std::time_t now = std::time(nullptr);
    std::ostringstream oss;
    oss << now;
    return oss.str();
}

Signer::SignResult Signer::generateSign(const std::string &text1,
                                        const std::string &text2,
                                        const std::string &apkPath) {
    SignResult result;
    result.text1 = text1;
    result.text2 = text2;
    result.timestamp = getTimestamp();

  std::string fileMd5;
    if (!apkPath.empty()) {
        fileMd5 = MD5::hashFile(apkPath);
    } else {
      fileMd5 = MD5::hash("");
    }

  result.sign1 = generateSign1(fileMd5, result.timestamp);

  result.sign2 = generateSign2(text1, text2, result.sign1, result.timestamp);

    return result;
}

std::string Signer::SignResult::toJson() const {
    std::ostringstream oss;
    oss << "{"
        << R"("text_1":")" << text1 << "\","
        << R"("text_2":")" << text2 << "\","
        << R"("sign_1":")" << sign1 << "\","
        << R"("time":")" << timestamp << "\","
        << R"("sign_2":")" << sign2 << "\""
        << "}";
    return oss.str();
}

std::string Signer::SignResult::toHexString() const {
  std::string json = toJson();
    std::ostringstream oss;
    oss << std::hex << std::uppercase << std::setfill('0');

    for (unsigned char c: json) {
        oss << std::setw(2) << static_cast<int>(c);
    }

    return oss.str();
}