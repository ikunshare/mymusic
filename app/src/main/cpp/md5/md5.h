#ifndef MD5_H
#define MD5_H

#include <string>
#include <cstdint>

class MD5 {
 public:
  MD5();

  void update(const uint8_t *input, size_t length);

  void update(const std::string &str);

  void finalize();

  std::string hexdigest() const;

  static std::string hash(const std::string &str);

  static std::string hashFile(const std::string &filepath);

 private:
  struct Context {
    uint32_t state[4];
    uint32_t count[2];
    uint8_t buffer[64];
  };

  Context ctx_;
  uint8_t digest_[16];
  bool finalized_;

  void transform(const uint8_t block[64]);

  void encode(uint8_t *output, const uint32_t *input, size_t length);

  void decode(uint32_t *output, const uint8_t *input, size_t length);

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

#endif