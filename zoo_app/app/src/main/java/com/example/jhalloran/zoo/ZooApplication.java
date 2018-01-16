package com.example.jhalloran.zoo;

import android.app.Application;
import android.util.Log;
import com.example.jhalloran.zoo.model.Zoo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by jhalloran on 1/15/18.
 */

public class ZooApplication extends Application {

  private static final String TAG = "ZooApplication";
  private static final String FILE_NAME = "zoo.tmp"; // TODO: Move to central location

  private Zoo zoo = Zoo.getInstance();

  @Override
  public void onCreate() {
    super.onCreate();
    initializeZooFromFile();
  }

  private void initializeZooFromFile() {
    try {
      FileInputStream fis = openFileInput(FILE_NAME);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Zoo zoo = (Zoo) ois.readObject();
      ois.close();
      fis.close();
      if (zoo != null) {
        Zoo.initializeZoo(zoo);
        Log.i(TAG, String.format("%s intialized from file", zoo.getName()));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
