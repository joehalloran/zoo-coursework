package com.example.jhalloran.zoo.ui.manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.ZooConstants;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.ui.detail.AnimalDetailActivity;
import com.example.jhalloran.zoo.ui.detail.PenDetailActivity;
import com.example.jhalloran.zoo.ui.detail.ZookeeperDetailActivity;
import java.util.List;
import java.util.UUID;

/**
 * Adapter to link Zoo data to view.
 */
public class ZooItemCustomAdapter extends
    RecyclerView.Adapter<ZooItemCustomAdapter.ZooItemViewHolder> {

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

  // Binds model data to the zoo.
  @Override
  public void onBindViewHolder(ZooItemViewHolder viewHolder, final int position) {
    Object item = zoo.getAnyItemById(dataSet.get(position));
    viewHolder.getTextView().setText(item.toString());
    TextView hintView = viewHolder.getHintView();
    // Adds "unassigned" warning for animals and pens.
    if (item instanceof Animal) {
      Animal animal = (Animal) item;
      if (!animal.isAssigned()) {
        hintView.setText(R.string.unassigned);
      } else {
        hintView.setText("");
      }
      return;
    }
    if (item instanceof Enclosable) {
      Enclosable pen = (Enclosable) item;
      if (!pen.isAssigned()) {
        hintView.setText(R.string.unassigned);
      } else {
        hintView.setText("");
      }
    }
  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  // View holder for each item in the dataset
  static class ZooItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;
    private final TextView hintView;
    private final Zoo zoo = Zoo.getInstance();

    ZooItemViewHolder(View v, final List<UUID> dataSet) {
      super(v);
      // Define click listener for each item.
      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Context context = v.getContext();
          UUID uuid = dataSet.get(getAdapterPosition());
          Object item = zoo.getAnyItemById(uuid);
          if (item instanceof Animal) {
            Intent animalDetailIntent = new Intent(context, AnimalDetailActivity.class);
            animalDetailIntent.putExtra(ZooConstants.ITEM_ID.getValue(), uuid.toString());
            context.startActivity(animalDetailIntent);
          } else if (item instanceof Enclosable) {
            Intent penDetailIntent = new Intent(context, PenDetailActivity.class);
            penDetailIntent.putExtra(ZooConstants.ITEM_ID.getValue(), uuid.toString());
            context.startActivity(penDetailIntent);
          } else if (item instanceof Zookeeper) {
            Intent zookeeperDetailIntent = new Intent(context, ZookeeperDetailActivity.class);
            zookeeperDetailIntent.putExtra(ZooConstants.ITEM_ID.getValue(), uuid.toString());
            context.startActivity(zookeeperDetailIntent);
          }
        }
      });
      hintView = v.findViewById(R.id.zooRowItemHint);
      textView = v.findViewById(R.id.zooRowItemText);
    }

    TextView getTextView() {
      return textView;
    }

    TextView getHintView() {
      return hintView;
    }
  }
}
