package com.example.jhalloran.zoo.ui.manager;

import static com.example.jhalloran.zoo.ZooConstants.ARG_PAGE_NUMBER;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Controller for list section of layput. Displays lists all of {@link Animal}, {@link Enclosable}
 * and {@link Zookeeper}.
 */
public class ZooContentFragment extends Fragment {

  private static final String TAG = "ZooContentFragment";
  private final Zoo zoo = Zoo.getInstance();
  private RecyclerView recyclerView;
  private SettableFuture<List<UUID>> animalsFuture = SettableFuture.create();
  private SettableFuture<List<UUID>> pensFuture = SettableFuture.create();
  private SettableFuture<List<UUID>> zookeepersFuture = SettableFuture.create();

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedStateInstance) {
    View rootView = inflater.inflate(R.layout.zoo_manager_content_fragment, container, false);
    recyclerView = rootView.findViewById(R.id.zoo_manager_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    setViewAdapter();

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    setViewAdapter();
  }

  private void setViewAdapter() {
    // Request data from the model
    beginLoadingFutures();
    // Load dataset into the view.
    Adapter adapter = new ZooItemCustomAdapter(
        getDataSetForFragment(getArguments().getInt(ARG_PAGE_NUMBER.getValue())));
    recyclerView.setAdapter(adapter);
  }

  // Access model in parallel in preparation for loading data to UI.
  private void beginLoadingFutures() {

    // Request data on a background thread to allow parallel processing
    ListenableFuture animalsListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getAnimalIds());
          }
        });
    // Update view with dataset on UI Thread once Future is complete
    Futures.addCallback(animalsListenableFuture, new FutureCallback<List<UUID>>() {
          @Override
          public void onSuccess(List<UUID> result) {
            animalsFuture.set(result);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load animals from model: %s", t));
          }
        },
        ZooThreadPoolManager.getBackgroundThreadExecutor());

    // Request data on a background thread to allow parallel processing
    ListenableFuture pensListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getPenIds());
          }
        });
    // Update view with dataset on UI Thread once Future is complete
    Futures.addCallback(pensListenableFuture, new FutureCallback<List<UUID>>() {
          @Override
          public void onSuccess(List<UUID> result) {
            pensFuture.set(result);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load pens from model: %s", t));
          }
        },
        ZooThreadPoolManager.getBackgroundThreadExecutor());

    // Request data on a background thread to allow parallel processing
    ListenableFuture zookeepersListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getZookeeperIds());
          }
        });
    // Update view with dataset on UI Thread once Future is complete
    Futures.addCallback(zookeepersListenableFuture, new FutureCallback<List<UUID>>() {
          @Override
          public void onSuccess(List<UUID> result) {
            zookeepersFuture.set(result);
          }

          @Override
          public void onFailure(Throwable t) {
            Log.e(TAG, String.format("Unable to load zookeepers from model: %s", t));
          }
        },
        ZooThreadPoolManager.getBackgroundThreadExecutor());
  }

  private List<UUID> getDataSetForFragment(int pageNumber) {
    List<UUID> dataSet = null;
    switch (pageNumber) {
      case 1:
        try {
          // SettableFuture.get() is blocks UI thread and waits for completion
          dataSet = animalsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load animals from model");
        }
        break;
      case 2:
        try {
          // SettableFuture.get() is blocks UI thread and waits for completion
          dataSet = pensFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load pens from model");
        }
        break;
      case 3:
        try {
          // SettableFuture.get() is blocks UI thread and waits for completion
          dataSet = zookeepersFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load zookeepers from model");
        }
        break;
    }
    return dataSet;
  }
}