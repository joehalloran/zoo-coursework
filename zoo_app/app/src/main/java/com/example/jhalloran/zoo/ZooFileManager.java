package com.example.jhalloran.zoo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.jhalloran.zoo.model.Zoo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by jhalloran on 1/12/18.
 */

class ZooFileManager extends AppCompatActivity{
  private static final String TAG = "ZooFileManager";
  private static final String FILE_NAME = "zoo.tmp";

  void writeZooToFile(Zoo zoo) {
    Log.e(TAG, "Writing");
    try {
      FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(zoo);
      objectOutputStream.close();
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, "Writing: FNF");
      e.printStackTrace();
    } catch (IOException e) {
      Log.e(TAG, "Writing: IO");
      // e.printStackTrace();
    }
  }

  void readZooFromFile() {
    Log.e(TAG, "Read");
    try {
      File file = new File(FILE_NAME);
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Zoo zoo = (Zoo) ois.readObject();
      Log.e(TAG, "Zoo found: " + zoo.getName());
      ois.close();
      fis.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, "Read: FNF");
    } catch (IOException e) {
      Log.e(TAG, "Read: IO");
    } catch (ClassNotFoundException e) {
      // e.printStackTrace();
      Log.e(TAG, "Read: CNF");
    }
  }
}
