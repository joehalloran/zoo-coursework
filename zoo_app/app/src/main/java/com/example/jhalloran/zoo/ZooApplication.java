package com.example.jhalloran.zoo;

import android.app.Application;
import com.example.jhalloran.zoo.io.ZooFileManager;

/**
 * An extension of Android Application class for Zoo app. Initialized whenever the app is launched.
 */
public class ZooApplication extends Application {

  private final ZooFileManager zooFileManager = new ZooFileManager(this);

  @Override
  public void onCreate() {
    super.onCreate();
    zooFileManager.initializeZooFromFile();
  }
}
