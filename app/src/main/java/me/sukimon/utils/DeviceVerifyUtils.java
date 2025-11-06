package me.sukimon.utils;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeviceVerifyUtils {

  private static final String UNIQUE_ID_FILE = ".unid";

  private static final int RANDOM_ID_LENGTH = 32;

  public DeviceVerifyUtils() {
  }

  public static String getUniqueDeviceRandomId(Context context) throws IOException {
    String filePath = context.getFilesDir().getPath() + File.separator + UNIQUE_ID_FILE;
    File idFile = new File(filePath);

    if (idFile.exists()) {
      return readIdFromFile(idFile);
    } else {
      return generateAndSaveNewId(idFile);
    }
  }

  private static String readIdFromFile(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
      byte[] buffer = new byte[(int) file.length()];
      fis.read(buffer);
      return new String(buffer);
    }
  }

  private static String generateAndSaveNewId(File file) throws IOException {
    String randomId = RandomUtils.randomHex(RANDOM_ID_LENGTH);

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(randomId.getBytes());
    }

    return randomId;
  }
}