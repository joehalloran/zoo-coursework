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
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.model.Zoo;
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
 * Created by jhalloran on 1/11/18.
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
    beginLoadingFutures();
    Adapter adapter = new ZooItemCustomAdapter(getDataSetForFragment(getArguments().getInt(ARG_PAGE_NUMBER)));
    recyclerView.setAdapter(adapter);
  }

  private void beginLoadingFutures() {
    // Access model in parallel in preparation for loading data to UI.

    ListenableFuture animalsListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getAnimalIds());
          }
        });
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

    ListenableFuture pensListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getPenIds());
          }
        });
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

    ListenableFuture zookeepersListenableFuture = ZooThreadPoolManager.getBackgroundThreadExecutor()
        .submit(new Callable<List<UUID>>() {
          @Override
          public List<UUID> call() throws Exception {
            return new ArrayList<>(zoo.getZookeeperIds());
          }
        });
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
          // future.get() is blocking and waits for completion
          dataSet = animalsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load animals from model");
        }
        break;
      case 2:
        try {
          // future.get() is blocking and waits for completion
          dataSet = pensFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load pens from model");
        }
        break;
      case 3:
        try {
          // future.get() is blocking and waits for completion
          dataSet = zookeepersFuture.get();
        } catch (InterruptedException | ExecutionException e) {
          Log.e(TAG, "Unable to load zookeepers from model");
        }
        break;
    }
    return dataSet;
  }
}