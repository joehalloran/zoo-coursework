package com.example.jhalloran.zoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "ZooMainActivity";
  private static final String FILE_NAME = "zoo.tmp";
  private Zoo zoo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeZooFromFile();
    zoo = Zoo.getInstance();
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshContent();
  }

  private void refreshContent() {
    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zoo.getName());

    TextView zooKeepersTitle = findViewById(R.id.zookeepersOverviewTitle);
    zooKeepersTitle.setText(String.format("Total zookeepers: %d", zoo.getZookeepers().size()));

    TextView pensTitle = findViewById(R.id.pensOverviewTitle);
    pensTitle.setText(String.format("Total pens: %d", zoo.getPens().size()));

    TextView animalsTitle = findViewById(R.id.animalsOverviewTitle);
    animalsTitle.setText(String.format("Total animals: %d", zoo.getAnimals().size()));

    final Button button = findViewById(R.id.manageZooButton);
    final Intent manageZooIntent = new Intent(this, ZooManagerActivity.class);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
  }

  void deleteAllSavedFiles() {
    for (String file: fileList()) {
      Log.e(TAG, "deleted : " + file);
      deleteFile(file);
    }
  }

  void writeZooToFile(Zoo zoo) {
    Log.e(TAG, "Writing");
    try {
      FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(zoo);
      objectOutputStream.close();
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
        Log.i(TAG, String.format("Reading %s from file", zoo.getName()));
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
