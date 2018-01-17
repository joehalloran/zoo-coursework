package com.example.jhalloran.zoo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jhalloran.zoo.model.Zoo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jhalloran on 1/11/18.
 */
public class ZooContentFragment extends Fragment {
  public static final String ARG_PAGE_NUMBER =
      "com.example.jhalloran.zoo.zoocontentfragment.pagenum";

  private RecyclerView recyclerView;
  private Zoo zoo = Zoo.getInstance();

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
    Adapter adapter = new ZooItemCustomAdapter(getDataSetForFragment(getArguments().getInt(ARG_PAGE_NUMBER)));
    recyclerView.setAdapter(adapter);
  }

  private List<UUID> getDataSetForFragment(int pageNumber) {
    List<UUID> dataSet = null;
    switch (pageNumber) {
      case 1:
        dataSet = new ArrayList<>(zoo.getAnimalIds());
        break;
      case 2:
        dataSet = new ArrayList<>(zoo.getPenIds());
        break;
      case 3:
        dataSet = new ArrayList<>(zoo.getZookeeperIds());
        break;
    }
    return dataSet;
    //return zoo.getAllIds();
  }
}