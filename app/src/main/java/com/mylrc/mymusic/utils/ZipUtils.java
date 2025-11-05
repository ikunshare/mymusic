package com.mylrc.mymusic.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipUtils {

  public static byte[] decompress(byte[] bArr) {
    Inflater inflater = new Inflater();
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length)) {
      try {
        inflater.reset();
        inflater.setInput(bArr);
        byte[] bArr2 = new byte[1024];
        while (!inflater.finished()) {
          byteArrayOutputStream.write(bArr2, 0, inflater.inflate(bArr2));
        }
        return byteArrayOutputStream.toByteArray();
      } catch (Exception e2) {
        e2.printStackTrace();
        return bArr;
      }
    } catch (IOException e3) {
      e3.printStackTrace();
      return bArr;
    } finally {
      inflater.end();
    }
  }

  public static byte[] compress(byte[] bArr) {
    Deflater deflater = new Deflater();
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length)) {
      try {
        deflater.reset();
        deflater.setInput(bArr);
        deflater.finish();
        byte[] bArr2 = new byte[1024];
        while (!deflater.finished()) {
          byteArrayOutputStream.write(bArr2, 0, deflater.deflate(bArr2));
        }
        return byteArrayOutputStream.toByteArray();
      } catch (Exception e3) {
        e3.printStackTrace();
        throw new IOException("Compress failed", e3);
      }
    } catch (IOException e2) {
      e2.printStackTrace();
    } finally {
      deflater.end();
    }
    return bArr;
  }
}