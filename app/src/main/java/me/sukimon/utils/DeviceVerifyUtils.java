package me.sukimon.utils;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 设备验证工具类 用于生成和获取设备唯一随机ID
 */
public class DeviceVerifyUtils {

  /**
   * 隐藏ID文件名
   */
  private static final String UNIQUE_ID_FILE = ".unid";

  /**
   * 随机ID长度（字节数）
   */
  private static final int RANDOM_ID_LENGTH = 32;

  /**
   * 构造函数
   */
  public DeviceVerifyUtils() {
  }

  /**
   * 获取设备唯一随机ID 如果本地文件存在则读取，否则生成新的随机ID并保存
   *
   * @param context 应用上下文
   * @return 设备唯一随机ID字符串
   * @throws IOException 文件读写异常
   */
  public static String getUniqueDeviceRandomId(Context context) throws IOException {
    // 构建ID文件路径：filesDir/.unid
    String filePath = context.getFilesDir().getPath() + File.separator + UNIQUE_ID_FILE;
    File idFile = new File(filePath);

    // 如果文件已存在，读取并返回
    if (idFile.exists()) {
      return readIdFromFile(idFile);
    } else {
      // 文件不存在，生成新的随机ID并保存
      return generateAndSaveNewId(idFile);
    }
  }

  /**
   * 从文件中读取ID
   *
   * @param file ID文件
   * @return 读取到的ID字符串
   * @throws IOException 文件读取异常
   */
  private static String readIdFromFile(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
      // 读取文件内容
      byte[] buffer = new byte[(int) file.length()];
      fis.read(buffer);
      return new String(buffer);
    }
  }

  /**
   * 生成新的随机ID并保存到文件
   *
   * @param file ID文件
   * @return 生成的新ID字符串
   * @throws IOException 文件写入异常
   */
  private static String generateAndSaveNewId(File file) throws IOException {
    // 生成32字节的随机十六进制字符串
    String randomId = RandomUtils.randomHex(RANDOM_ID_LENGTH);

    // 保存到文件
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(randomId.getBytes());
    }

    return randomId;
  }
}