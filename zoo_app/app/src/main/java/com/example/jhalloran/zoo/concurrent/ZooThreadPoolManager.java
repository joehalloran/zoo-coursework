package com.example.jhalloran.zoo.concurrent;

import android.os.Handler;
import android.os.Looper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jhalloran on 1/19/18.
 */

public final class ZooThreadPoolManager {
  private static ZooThreadPoolManager instance = null;
  private MainThreadExecutor mainThreadExecutor = new MainThreadExecutor();
  private ListeningExecutorService listeningCachedThreadPool = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

  // Private constructor for singleton
  private ZooThreadPoolManager() {}

  public static ListeningExecutorService getBackgroundThreadExecutor() {
    if (instance == null) {
      instance = new ZooThreadPoolManager();
    }
    return instance.listeningCachedThreadPool;
  }

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
