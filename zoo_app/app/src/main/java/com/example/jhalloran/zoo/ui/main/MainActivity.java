package com.example.jhalloran.zoo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.io.ZooFileManager;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.ui.manager.ZooManagerActivity;
import com.example.jhalloran.zoo.weather.WeatherUpdate;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import org.openweathermap.api.model.currentweather.CurrentWeather;

/**
 * Controller for Main landing page.
 */
public class MainActivity extends AppCompatActivity {

  private static final String TAG = "ZooMainActivity";
  private final WeatherUpdate weatherUpdate = new WeatherUpdate();
  private final ZooFileManager zooFileManager = new ZooFileManager(this);
  private Zoo zoo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    zoo = Zoo.getInstance();
    setContentView(R.layout.activity_main);

    // TODO Delete
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
    // Update view
    refreshContent();
    // Request and show latest weather
    updateWeather();
    // Write zoo to file, to save latest changes
    Executor executor = ZooThreadPoolManager.getBackgroundThreadExecutor();
    executor.execute(new Runnable() {
      @Override
      public void run() {
        zooFileManager.writeZooToFile();
      }
    });
  }

  private void refreshContent() {
    updateContentThatDoesNotComeFromModel();
    updateContentFromModel();
  }

  // Update items that do not require access to Zoo model
  private void updateContentThatDoesNotComeFromModel() {
    final Button manageZooButton = findViewById(R.id.manageZooButton);
    final Intent manageZooIntent = new Intent(this, ZooManagerActivity.class);
    manageZooButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });

    final ImageButton refreshWeatherButton = findViewById(R.id.refreshWeather);
    refreshWeatherButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateWeather();
      }
    });
  }

  // Update view by querying Zoo model. Requests data in parallel.
  private void updateContentFromModel() {
    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zoo.getName());

    // Request data on a background thread to allow parallel processing
    ListenableFuture<Integer> numZookeepersFuture = ZooThreadPoolManager
        .getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            return zoo.getZookeepers().size();
          }
        });
    // Update view on UI Thread once Future is complete
    Futures.addCallback(numZookeepersFuture, new FutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            TextView zooKeepersTitle = findViewById(R.id.zookeepersOverviewTitle);
            zooKeepersTitle.setText(getResources().getString(R.string.total_zookeepers, result));
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load zookeepers from model: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());

    // Request data on a background thread to allow parallel processing
    ListenableFuture<Integer> numPensFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            return zoo.getPens().size();
          }
        });
    // Update view on UI Thread once Future is complete
    Futures.addCallback(numPensFuture, new FutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            TextView pensTitle = findViewById(R.id.pensOverviewTitle);
            pensTitle.setText(getResources().getString(R.string.total_pens, result));
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load pens from model: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());

    // Request data on a background thread to allow parallel processing
    ListenableFuture<Integer> numAnimalsFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            return zoo.getAnimals().size();
          }
        });
    // Update view on UI Thread once Future is complete
    Futures.addCallback(numAnimalsFuture, new FutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            TextView animalsTitle = findViewById(R.id.animalsOverviewTitle);
            animalsTitle.setText(getResources().getString(R.string.total_animals, result));
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load animals from model: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());


  }

  private void updateWeather() {
    final ViewGroup weatherBox = findViewById(R.id.weather);
    weatherBox.setVisibility(View.INVISIBLE);
    // Request data on a background thread to allow parallel processing
    ListenableFuture<CurrentWeather> currentWeatherFuture = ZooThreadPoolManager
        .getBackgroundThreadExecutor()
        .submit(new Callable<CurrentWeather>() {
          @Override
          public CurrentWeather call() throws Exception {
            return weatherUpdate.getCurrentWeather();
          }
        });
    // Update view on UI Thread once Future is complete
    Futures.addCallback(currentWeatherFuture, new FutureCallback<CurrentWeather>() {
          @Override
          public void onSuccess(CurrentWeather weather) {
            Log.i(TAG, "Weather updated");
            TextView weatherTitle = findViewById(R.id.weatherTitle);
            weatherTitle
                .setText(getResources().getString(R.string.weather_title, weather.getCityName()));

            TextView weatherTemperature = findViewById(R.id.weatherTemperature);
            weatherTemperature.setText(getResources()
                .getString(R.string.temperature, weather.getMainParameters().getTemperature()));

            TextView weatherRain = findViewById(R.id.weatherPrecipitation);
            weatherRain
                .setText(getResources().getString(R.string.wind_speed, weather.getWind().getSpeed()));
            weatherBox.setVisibility(View.VISIBLE);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load weather: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());
  }
}
