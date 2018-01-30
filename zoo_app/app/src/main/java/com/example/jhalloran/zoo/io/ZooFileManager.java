package com.example.jhalloran.zoo.io;

import android.content.Context;
import android.util.Log;
import com.example.jhalloran.zoo.model.Zoo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles IO for Zoo app persistence layer.
 */
public class ZooFileManager {

  private static final String TAG = "ZooFileManager";
  private static final String FILE_NAME = "zoo.tmp";
  private final Context context;

  public ZooFileManager(Context context) {
    this.context = context;
  }

  /**
   * Saves contents of Zoo.
   */
  public synchronized void writeZooToFile() {
    Log.i(TAG, "Writing zoo to file");
    try {
      FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(Zoo.getInstance());
      objectOutputStream.close();
      fileOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Reads data from file and initializes saved Zoo.
   */
  public synchronized void initializeZooFromFile() {
    try {
      FileInputStream fis = context.openFileInput(FILE_NAME);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Zoo zoo = (Zoo) ois.readObject();
      ois.close();
      fis.close();
      if (zoo != null) {
        Zoo.initializeZoo(zoo);
        Log.i(TAG, String.format("%s initialized from file", zoo.getName()));
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
