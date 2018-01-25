package com.example.jhalloran.zoo;

import android.app.Application;
import android.util.Log;
import com.example.jhalloran.zoo.io.ZooFileManager;
import com.example.jhalloran.zoo.model.Zoo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by jhalloran on 1/15/18.
 */

public class ZooApplication extends Application {
  private final ZooFileManager zooFileManager = new ZooFileManager(this);

  @Override
  public void onCreate() {
    super.onCreate();
    zooFileManager.initializeZooFromFile();
  }
}
