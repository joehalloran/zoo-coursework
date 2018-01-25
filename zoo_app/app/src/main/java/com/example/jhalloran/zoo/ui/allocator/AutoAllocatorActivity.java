package com.example.jhalloran.zoo.ui.allocator;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.allocator.AutoAllocatorHelper;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.io.ZooFileManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import org.w3c.dom.Text;

public class AutoAllocatorActivity extends AppCompatActivity {
  private static final String TAG = "AutoAllocateActivity";
  AutoAllocatorHelper helper = new AutoAllocatorHelper();
  ZooFileManager zooFileManager = new ZooFileManager(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auto_allocator);

    // Enable toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Enable up navigation
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    final Button allocateAnimalsButton = findViewById(R.id.allocateAnimalsButton);
    allocateAnimalsButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        autoAllocateAnimals();
      }
    });

    final Button allocatePensButton = findViewById(R.id.allocatePensButton);
    allocatePensButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        autoAllocatePens();
      }
    });
  }

  private void autoAllocateAnimals() {
    final TextView animalReportView = findViewById(R.id.allocatorAnimalReport);
    animalReportView.setVisibility(View.INVISIBLE);

    ListenableFuture<String> animalAllocationFuture = ZooThreadPoolManager.getBackgroundThreadExecutor().submit(
        new Callable<String>() {
          @Override
          public String call() throws Exception {
            String animalReport = helper.autoAllocateAnimalsAndGetReport();
            zooFileManager.writeZooToFile();
            return animalReport;
          }
        });

    Futures.addCallback(animalAllocationFuture, new FutureCallback<String>() {
          @Override
          public void onSuccess(String report) {
            animalReportView.setText(report);
            animalReportView.setVisibility(View.VISIBLE);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to complete auto-allocation of animals: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());
  }

  private void autoAllocatePens() {
    final TextView penReportView = findViewById(R.id.allocatorPenReport);
    penReportView.setVisibility(View.INVISIBLE);

    ListenableFuture<String> penAllocationFuture = ZooThreadPoolManager.getBackgroundThreadExecutor().submit(
        new Callable<String>() {
          @Override
          public String call() throws Exception {
            String penReport = helper.autoAllocatePensAndGetReport();
            zooFileManager.writeZooToFile();
            return penReport;
          }
        });

    Futures.addCallback(penAllocationFuture, new FutureCallback<String>() {
          @Override
          public void onSuccess(String report) {
            penReportView.setText(report);
            penReportView.setVisibility(View.VISIBLE);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to complete auto-allocation of pens: %s", t));
          }
        },
        ZooThreadPoolManager.getMainThreadExecutor());
  }
}
