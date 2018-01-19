package com.example.jhalloran.zoo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.weather.WeatherUpdate;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import org.openweathermap.api.model.currentweather.CurrentWeather;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "ZooMainActivity";
  private static final String FILE_NAME = "zoo.tmp";
  private Zoo zoo;
  private WeatherUpdate weatherUpdate = new WeatherUpdate();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    zoo = Zoo.getInstance();
    setContentView(R.layout.activity_main);

    final Button deleteAllButton = findViewById(R.id.deleteZooDataButton);
    deleteAllButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        deleteAllSavedFiles();
      }
    });
  }

  private void deleteAllSavedFiles() {
    for (String file : fileList()) {
      Log.e(TAG, "deleted : " + file);
      deleteFile(file);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshContent();

    updateWeather();
    writeZooToFile();
  }

  private void refreshContent() {
    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zoo.getName());

    ListenableFuture<Integer> numZookeepersFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            return zoo.getZookeepers().size();
          }
        });
    Futures.addCallback(numZookeepersFuture, new FutureCallback<Integer>() {
      @Override
      public void onSuccess(Integer result) {
        TextView zooKeepersTitle = findViewById(R.id.zookeepersOverviewTitle);
        zooKeepersTitle.setText(String.format("Total zookeepers: %d", result));
      }

      @Override
      public void onFailure(Throwable t) {

      }
    },
    ZooThreadPoolManager.getMainThreadExecutor());

    ListenableFuture<Integer> numPensFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            Thread.sleep(2000);
            return zoo.getPens().size();
          }
        });
    Futures.addCallback(numPensFuture, new FutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            TextView pensTitle = findViewById(R.id.pensOverviewTitle);
            pensTitle.setText(String.format("Total pens: %d", result));
          }

          @Override
          public void onFailure(Throwable t) {

          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());

    ListenableFuture<Integer> numAnimalsFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            return zoo.getAnimals().size();
          }
        });
    Futures.addCallback(numAnimalsFuture, new FutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            TextView animalsTitle = findViewById(R.id.animalsOverviewTitle);
            animalsTitle.setText(String.format("Total animals: %d", result));
          }

          @Override
          public void onFailure(Throwable t) {

          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());

    final Button button = findViewById(R.id.manageZooButton);
    final Intent manageZooIntent = new Intent(this, ZooManagerActivity.class);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
  }

  private void updateWeather() {
    ListenableFuture<CurrentWeather> currentWeatherFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<CurrentWeather>() {
          @Override
          public CurrentWeather call() throws Exception {
            return weatherUpdate.getCurrentWeather();
          }
        });
    Futures.addCallback(currentWeatherFuture, new FutureCallback<CurrentWeather>() {
          @Override
          public void onSuccess(CurrentWeather weather) {
            TextView weatherLocation = findViewById(R.id.weatherLocation);
            weatherLocation.setText(weather.getCityName());

            TextView weatherTemperature = findViewById(R.id.weatherTemperature);
            weatherTemperature
                .setText(String.valueOf(weather.getMainParameters().getTemperature()) + " \u2103");

          }

          @Override
          public void onFailure(Throwable t) {

          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());
  }


  private void writeZooToFile() {
    AsyncTask.execute(
        new Runnable() {
          @Override
          public void run() {
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
        });
  }
}
