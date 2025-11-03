#include "md5.h"
#include <cstring>
#include <fstream>
#include <sstream>
#include <iomanip>

static const uint8_t PADDING[64] = {
        0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

MD5::MD5() : finalized_(false) {
  ctx_.state[0] = 0x67452301;
    ctx_.state[1] = 0xefcdab89;
    ctx_.state[2] = 0x98badcfe;
    ctx_.state[3] = 0x10325476;
    ctx_.count[0] = 0;
    ctx_.count[1] = 0;
    std::memset(digest_, 0, sizeof(digest_));
}

void MD5::update(const uint8_t *input, size_t length) {
    uint32_t index = (ctx_.count[0] >> 3) & 0x3F;

  if ((ctx_.count[0] += (length << 3)) < (length << 3)) {
        ctx_.count[1]++;
    }
    ctx_.count[1] += (length >> 29);

    uint32_t partLen = 64 - index;
    uint32_t i = 0;

  if (length >= partLen) {
        std::memcpy(&ctx_.buffer[index], input, partLen);
        transform(ctx_.buffer);

        for (i = partLen; i + 63 < length; i += 64) {
            transform(&input[i]);
        }
        index = 0;
    }

  std::memcpy(&ctx_.buffer[index], &input[i], length - i);
}

void MD5::update(const std::string &str) {
    update(reinterpret_cast<const uint8_t *>(str.c_str()), str.length());
}

void MD5::finalize() {
    if (finalized_) return;

    uint8_t bits[8];
    encode(bits, ctx_.count, 8);

  uint32_t index = (ctx_.count[0] >> 3) & 0x3f;
    uint32_t padLen = (index < 56) ? (56 - index) : (120 - index);
    update(PADDING, padLen);

  update(bits, 8);

  encode(digest_, ctx_.state, 16);
    finalized_ = true;
}

std::string MD5::hexdigest() const {
    if (!finalized_) {
        return "";
    }

    std::ostringstream oss;
    oss << std::hex << std::setfill('0');
    for (int i = 0; i < 16; i++) {
        oss << std::setw(2) << static_cast<int>(digest_[i]);
    }
    return oss.str();
}

std::string MD5::hash(const std::string &str) {
    MD5 md5;
    md5.update(str);
    md5.finalize();
    return md5.hexdigest();
}

std::string MD5::hashFile(const std::string &filepath) {
    std::ifstream file(filepath, std::ios::binary);
    if (!file) {
        return "";
    }

    MD5 md5;
    char buffer[1024];
    while (file.read(buffer, sizeof(buffer)) || file.gcount() > 0) {
        md5.update(reinterpret_cast<uint8_t *>(buffer), file.gcount());
    }

    md5.finalize();
    return md5.hexdigest();
}

void MD5::transform(const uint8_t block[64]) {
    uint32_t a = ctx_.state[0];
    uint32_t b = ctx_.state[1];
    uint32_t c = ctx_.state[2];
    uint32_t d = ctx_.state[3];
    uint32_t x[16];

    decode(x, block, 64);

#define FF(a, b, c, d, x, s, ac) { \
        (a) += F((b), (c), (d)) + (x) + (ac); \
        (a) = rotateLeft((a), (s)); \
        (a) += (b); \
    }
#define GG(a, b, c, d, x, s, ac) { \
        (a) += G((b), (c), (d)) + (x) + (ac); \
        (a) = rotateLeft((a), (s)); \
        (a) += (b); \
    }
#define HH(a, b, c, d, x, s, ac) { \
        (a) += H((b), (c), (d)) + (x) + (ac); \
        (a) = rotateLeft((a), (s)); \
        (a) += (b); \
    }
#define II(a, b, c, d, x, s, ac) { \
        (a) += I((b), (c), (d)) + (x) + (ac); \
        (a) = rotateLeft((a), (s)); \
        (a) += (b); \
    }

  FF(a, b, c, d, x[0], 7, 0xd76aa478);
    FF(d, a, b, c, x[1], 12, 0xe8c7b756);
    FF(c, d, a, b, x[2], 17, 0x242070db);
    FF(b, c, d, a, x[3], 22, 0xc1bdceee);
    FF(a, b, c, d, x[4], 7, 0xf57c0faf);
    FF(d, a, b, c, x[5], 12, 0x4787c62a);
    FF(c, d, a, b, x[6], 17, 0xa8304613);
    FF(b, c, d, a, x[7], 22, 0xfd469501);
    FF(a, b, c, d, x[8], 7, 0x698098d8);
    FF(d, a, b, c, x[9], 12, 0x8b44f7af);
    FF(c, d, a, b, x[10], 17, 0xffff5bb1);
    FF(b, c, d, a, x[11], 22, 0x895cd7be);
    FF(a, b, c, d, x[12], 7, 0x6b901122);
    FF(d, a, b, c, x[13], 12, 0xfd987193);
    FF(c, d, a, b, x[14], 17, 0xa679438e);
    FF(b, c, d, a, x[15], 22, 0x49b40821);

  GG(a, b, c, d, x[1], 5, 0xf61e2562);
    GG(d, a, b, c, x[6], 9, 0xc040b340);
    GG(c, d, a, b, x[11], 14, 0x265e5a51);
    GG(b, c, d, a, x[0], 20, 0xe9b6c7aa);
    GG(a, b, c, d, x[5], 5, 0xd62f105d);
    GG(d, a, b, c, x[10], 9, 0x02441453);
    GG(c, d, a, b, x[15], 14, 0xd8a1e681);
    GG(b, c, d, a, x[4], 20, 0xe7d3fbc8);
    GG(a, b, c, d, x[9], 5, 0x21e1cde6);
    GG(d, a, b, c, x[14], 9, 0xc33707d6);
    GG(c, d, a, b, x[3], 14, 0xf4d50d87);
    GG(b, c, d, a, x[8], 20, 0x455a14ed);
    GG(a, b, c, d, x[13], 5, 0xa9e3e905);
    GG(d, a, b, c, x[2], 9, 0xfcefa3f8);
    GG(c, d, a, b, x[7], 14, 0x676f02d9);
    GG(b, c, d, a, x[12], 20, 0x8d2a4c8a);

  HH(a, b, c, d, x[5], 4, 0xfffa3942);
    HH(d, a, b, c, x[8], 11, 0x8771f681);
    HH(c, d, a, b, x[11], 16, 0x6d9d6122);
    HH(b, c, d, a, x[14], 23, 0xfde5380c);
    HH(a, b, c, d, x[1], 4, 0xa4beea44);
    HH(d, a, b, c, x[4], 11, 0x4bdecfa9);
    HH(c, d, a, b, x[7], 16, 0xf6bb4b60);
    HH(b, c, d, a, x[10], 23, 0xbebfbc70);
    HH(a, b, c, d, x[13], 4, 0x289b7ec6);
    HH(d, a, b, c, x[0], 11, 0xeaa127fa);
    HH(c, d, a, b, x[3], 16, 0xd4ef3085);
    HH(b, c, d, a, x[6], 23, 0x04881d05);
    HH(a, b, c, d, x[9], 4, 0xd9d4d039);
    HH(d, a, b, c, x[12], 11, 0xe6db99e5);
    HH(c, d, a, b, x[15], 16, 0x1fa27cf8);
    HH(b, c, d, a, x[2], 23, 0xc4ac5665);

  II(a, b, c, d, x[0], 6, 0xf4292244);
    II(d, a, b, c, x[7], 10, 0x432aff97);
    II(c, d, a, b, x[14], 15, 0xab9423a7);
    II(b, c, d, a, x[5], 21, 0xfc93a039);
    II(a, b, c, d, x[12], 6, 0x655b59c3);
    II(d, a, b, c, x[3], 10, 0x8f0ccc92);
    II(c, d, a, b, x[10], 15, 0xffeff47d);
    II(b, c, d, a, x[1], 21, 0x85845dd1);
    II(a, b, c, d, x[8], 6, 0x6fa87e4f);
    II(d, a, b, c, x[15], 10, 0xfe2ce6e0);
    II(c, d, a, b, x[6], 15, 0xa3014314);
    II(b, c, d, a, x[13], 21, 0x4e0811a1);
    II(a, b, c, d, x[4], 6, 0xf7537e82);
    II(d, a, b, c, x[11], 10, 0xbd3af235);
    II(c, d, a, b, x[2], 15, 0x2ad7d2bb);
    II(b, c, d, a, x[9], 21, 0xeb86d391);

    ctx_.state[0] += a;
    ctx_.state[1] += b;
    ctx_.state[2] += c;
    ctx_.state[3] += d;

#undef FF
#undef GG
#undef HH
#undef II
}

void MD5::encode(uint8_t *output, const uint32_t *input, size_t length) {
    for (size_t i = 0, j = 0; j < length; i++, j += 4) {
        output[j] = (input[i]) & 0xff;
        output[j + 1] = (input[i] >> 8) & 0xff;
        output[j + 2] = (input[i] >> 16) & 0xff;
        output[j + 3] = (input[i] >> 24) & 0xff;
    }
}

void MD5::decode(uint32_t *output, const uint8_t *input, size_t length) {
    for (size_t i = 0, j = 0; j < length; i++, j += 4) {
        output[i] = static_cast<uint32_t>(input[j]) |
                    (static_cast<uint32_t>(input[j + 1]) << 8) |
                    (static_cast<uint32_t>(input[j + 2]) << 16) |
                    (static_cast<uint32_t>(input[j + 3]) << 24);
    }
}