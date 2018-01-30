package com.example.jhalloran.zoo.concurrent;

import android.os.Handler;
import android.os.Looper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Singleton to provide reusable thread pools.
 */
public final class ZooThreadPoolManager {

  private static ZooThreadPoolManager instance = null;
  private MainThreadExecutor mainThreadExecutor = new MainThreadExecutor();
  private ListeningExecutorService listeningCachedThreadPool = MoreExecutors
      .listeningDecorator(Executors.newCachedThreadPool());

  // Private constructor for singleton
  private ZooThreadPoolManager() {
  }

  /**
   * Provides a thread pool to run tasks on the background thread.
   *
   * @return an executor services suitable for use with {@code ListenableFuture}
   */
  public static ListeningExecutorService getBackgroundThreadExecutor() {
    if (instance == null) {
      instance = new ZooThreadPoolManager();
    }
    return instance.listeningCachedThreadPool;
  }

  /**
   * Provides a thread pool to run tasks on the main thread.
   *
   * @return UI thread executor services
   */
  public static MainThreadExecutor getMainThreadExecutor() {
    if (instance == null) {
      instance = new ZooThreadPoolManager();
    }
    return instance.mainThreadExecutor;
  }

  private class MainThreadExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
      handler.post(runnable);
    }
  }

}
