package utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.mylrc.mymusic.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ImageLoaderConfig {

  private static final int THREAD_POOL_SIZE = 3;

  private static final int THREAD_PRIORITY_OFFSET = 2;

  private static final int MEMORY_CACHE_SIZE = 2 * 1024 * 1024;

  private static final int MEMORY_CACHE_PERCENTAGE = 13;

  private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;

  private static final int DISK_CACHE_FILE_COUNT = 100;

  private static final int CORNER_RADIUS = 17;

  private static volatile boolean isInitialized = false;

  public static DisplayImageOptions createDefaultOptions() {
    return new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_avatar)
        .showImageOnFail(R.drawable.ic_avatar)
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .resetViewBeforeLoading(true)
        .displayer(new RoundedBitmapDisplayer(CORNER_RADIUS))
        .build();
  }

  public static synchronized void initImageLoader(Context context) {
    if (isInitialized) {
      return;
    }

    java.io.File cacheDir = StorageUtils.getCacheDirectory(context);

    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .threadPoolSize(THREAD_POOL_SIZE)
        .threadPriority(Thread.NORM_PRIORITY - THREAD_PRIORITY_OFFSET)
        .tasksProcessingOrder(QueueProcessingType.FIFO)
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(MEMORY_CACHE_SIZE))
        .memoryCacheSize(MEMORY_CACHE_SIZE)
        .memoryCacheSizePercentage(MEMORY_CACHE_PERCENTAGE)
        .diskCache(new UnlimitedDiskCache(cacheDir))
        .diskCacheSize(DISK_CACHE_SIZE)
        .diskCacheFileCount(DISK_CACHE_FILE_COUNT)
        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
        .imageDownloader(new BaseImageDownloader(context))
        .imageDecoder(new BaseImageDecoder(true))
        .defaultDisplayImageOptions(createDefaultOptions())
        .writeDebugLogs()
        .build();

    ImageLoader.getInstance().init(config);
    isInitialized = true;
  }

  public static boolean isInitialized() {
    return isInitialized;
  }
}