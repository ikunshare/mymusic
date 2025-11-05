package utils;

import java.util.concurrent.TimeUnit;

public class OkHttpClient {

  private static final long CONNECT_TIMEOUT = 20L;
  private static final long READ_TIMEOUT = 20L;
  private static final long WRITE_TIMEOUT = 20L;
  private static volatile okhttp3.OkHttpClient instance;

  private OkHttpClient() {
    if (instance != null) {
      throw new IllegalStateException("Instance already exists!");
    }
  }

  public static okhttp3.OkHttpClient getInstance() {
    if (instance == null) {
      synchronized (OkHttpClient.class) {
        if (instance == null) {
          instance = createClient();
        }
      }
    }
    return instance;
  }

  private static okhttp3.OkHttpClient createClient() {
    return new okhttp3.OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
  }

  public static void resetInstance() {
    synchronized (OkHttpClient.class) {
      if (instance != null) {
        instance.dispatcher().executorService().shutdown();
        instance.connectionPool().evictAll();
        instance = null;
      }
    }
  }

  public static okhttp3.OkHttpClient getCustomClient(long connectTimeout,
      long readTimeout,
      long writeTimeout) {
    return new okhttp3.OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
  }
}