#ifndef MD5_H
#define MD5_H

#include <string>
#include <cstdint>

/**
 * MD5哈希算法实现
 * 基于RFC 1321标准
 */
class MD5 {
public:
    MD5();

    // 更新MD5状态（添加数据）
    void update(const uint8_t *input, size_t length);

    void update(const std::string &str);

    // 完成计算并返回16字节的哈希值
    void finalize();

    // 获取32位十六进制字符串格式的MD5
    std::string hexdigest() const;

    // 静态便捷方法：直接计算字符串的MD5
    static std::string hash(const std::string &str);

    static std::string hashFile(const std::string &filepath);

private:
    // MD5上下文
    struct Context {
        uint32_t state[4];      // ABCD状态
        uint32_t count[2];      // 位数计数（低32位、高32位）
        uint8_t buffer[64];     // 输入缓冲区
    };

    Context ctx_;
    uint8_t digest_[16];        // 最终的MD5值
    bool finalized_;

    // 内部处理函数
    void transform(const uint8_t block[64]);

    void encode(uint8_t *output, const uint32_t *input, size_t length);

    void decode(uint32_t *output, const uint8_t *input, size_t length);

    // MD5基本操作
    static inline uint32_t F(uint32_t x, uint32_t y, uint32_t z) {
        return (x & y) | (~x & z);
    }

    static inline uint32_t G(uint32_t x, uint32_t y, uint32_t z) {
        return (x & z) | (y & ~z);
    }

    static inline uint32_t H(uint32_t x, uint32_t y, uint32_t z) {
        return x ^ y ^ z;
    }

    static inline uint32_t I(uint32_t x, uint32_t y, uint32_t z) {
        return y ^ (x | ~z);
    }

    static inline uint32_t rotateLeft(uint32_t x, int n) {
        return (x << n) | (x >> (32 - n));
    }
};

#endif // MD5_H