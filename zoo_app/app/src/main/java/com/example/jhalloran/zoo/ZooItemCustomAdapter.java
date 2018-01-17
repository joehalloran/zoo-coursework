package com.example.jhalloran.zoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import java.util.List;
import java.util.UUID;

/**
 * Created by jhalloran on 1/11/18.
 */

public class ZooItemCustomAdapter extends RecyclerView.Adapter<ZooItemCustomAdapter.ZooItemViewHolder> {
  private static final String TAG = "ZooCustomAdapter";
  private final Zoo zoo = Zoo.getInstance();
  private List<UUID> dataSet;

  ZooItemCustomAdapter(List<UUID> dataSet) {
    this.dataSet = dataSet;
  }

  @Override
  public ZooItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    // Create a new view.
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.zoo_manager_row_item, viewGroup, false);

    return new ZooItemViewHolder(v, dataSet);
  }

  @Override
  public void onBindViewHolder(ZooItemViewHolder viewHolder, final int position) {
    Object item = zoo.getAnyItemById(dataSet.get(position));
    viewHolder.getTextView().setText(item.toString());
    if (item instanceof Animal) {
      Animal animal = (Animal) item;
      Log.e(TAG ,String.format("%s is unassigned %s", animal.getName(), animal.isAssigned()));
      if (!animal.isAssigned()) {
        // ERROR HERE;
        viewHolder.getHintView().setText(R.string.unassigned);
      };
    } if (item instanceof Enclosure) {
      Enclosure pen = (Enclosure) item;
      if (!pen.isAssigned()) {
        viewHolder.getHintView().setText(R.string.unassigned);
      };
    }
  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  static class ZooItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    private final TextView hintView;
    private final Zoo zoo = Zoo.getInstance();

    ZooItemViewHolder(View v, final List<UUID> dataSet) {
      super(v);
      // Define click listener for the ViewHolder's View.
      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Context context = v.getContext();
          UUID uuid = dataSet.get(getAdapterPosition());
          Object item = zoo.getAnyItemById(uuid);
          if (item instanceof Animal) {
            Intent animalDetailIntent = new Intent(context, AnimalDetailActivity.class);
            animalDetailIntent.putExtra(ZooConstants.ITEM_ID, uuid.toString());
            context.startActivity(animalDetailIntent);
          } else if (item instanceof Enclosure) {
            Intent penDetailIntent = new Intent(context, PenDetailActivity.class);
            penDetailIntent.putExtra(ZooConstants.ITEM_ID, uuid.toString());
            context.startActivity(penDetailIntent);
          } else if (item instanceof Zookeeper) {
          Intent zookeeperDetailIntent = new Intent(context, ZookeeperDetailActivity.class);
          zookeeperDetailIntent.putExtra(ZooConstants.ITEM_ID, uuid.toString());
          context.startActivity(zookeeperDetailIntent);
        }
        }
      });
      hintView = v.findViewById(R.id.zooRowItemHint);
      textView = v.findViewById(R.id.zooRowItemText); //TODO
    }

    TextView getTextView() {
      return textView;
    }

    TextView getHintView() {
      return hintView;
    }
  }
}
