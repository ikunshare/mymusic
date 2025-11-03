package com.mylrc.mymusic.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * OkHttp客户端管理器 使用单例模式管理全局唯一的OkHttpClient实例 采用双重检查锁定（Double-Check Locking）确保线程安全
 *
 * @author ikunshare
 * @version 1.0
 */
public class OkHttpClientManager {

  /**
   * 连接超时时间（秒）
   */
  private static final long CONNECT_TIMEOUT = 20L;
  /**
   * 读取超时时间（秒）
   */
  private static final long READ_TIMEOUT = 20L;
  /**
   * 写入超时时间（秒）
   */
  private static final long WRITE_TIMEOUT = 20L;
  /**
   * OkHttpClient单例实例 使用volatile关键字确保多线程环境下的可见性
   */
  private static volatile OkHttpClient instance;

  /**
   * 私有构造函数，防止外部实例化
   */
  private OkHttpClientManager() {
    // 防止通过反射创建实例
    if (instance != null) {
      throw new IllegalStateException("Instance already exists!");
    }
  }

  /**
   * 获取OkHttpClient单例实例 使用双重检查锁定模式，既保证线程安全，又提高性能
   *
   * @return OkHttpClient实例
   */
  public static OkHttpClient getInstance() {
    // 第一次检查，避免不必要的同步
    if (instance == null) {
      synchronized (OkHttpClientManager.class) {
        // 第二次检查，确保只创建一个实例
        if (instance == null) {
          instance = createClient();
        }
      }
    }
    return instance;
  }

  /**
   * 创建OkHttpClient实例 配置超时时间等参数
   *
   * @return 配置好的OkHttpClient实例
   */
  private static OkHttpClient createClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 连接超时
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 读取超时
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 写入超时
        .retryOnConnectionFailure(true)                     // 连接失败时重试
        .build();
  }

  /**
   * 重置实例（主要用于测试） 注意：在生产环境中慎用此方法
   */
  public static void resetInstance() {
    synchronized (OkHttpClientManager.class) {
      if (instance != null) {
        // 关闭连接池和清理资源
        instance.dispatcher().executorService().shutdown();
        instance.connectionPool().evictAll();
        instance = null;
      }
    }
  }

  /**
   * 获取自定义配置的OkHttpClient 适用于需要特殊配置的场景
   *
   * @param connectTimeout 连接超时时间（秒）
   * @param readTimeout    读取超时时间（秒）
   * @param writeTimeout   写入超时时间（秒）
   * @return 配置好的OkHttpClient实例
   */
  public static OkHttpClient getCustomClient(long connectTimeout,
      long readTimeout,
      long writeTimeout) {
    return new OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
  }
}