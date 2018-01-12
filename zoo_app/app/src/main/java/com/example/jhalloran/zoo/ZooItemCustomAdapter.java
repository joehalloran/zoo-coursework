package com.example.jhalloran.zoo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by jhalloran on 1/11/18.
 */

public class ZooItemCustomAdapter extends RecyclerView.Adapter<ZooItemCustomAdapter.ZooItemViewHolder> {
  private static final String TAG = "CustomAdapter";
  private List<?> dataSet;

  ZooItemCustomAdapter(List<?> dataSet) {
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
    Log.d(TAG, "Element " + position + " set.");

    // Get element from your dataset at this position and replace the contents of the view
    // with that element
    viewHolder.getTextView().setText(dataSet.get(position).toString());
  }
  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  static class ZooItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;

    ZooItemViewHolder(View v, final List<?> dataSet) {
      super(v);
      // Define click listener for the ViewHolder's View.
      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d(TAG, "Element " + dataSet.get(getAdapterPosition()) + " clicked.");
        }
      });
      textView = v.findViewById(R.id.zooRowItemText); //TODO
    }

    TextView getTextView() {
      return textView;
    }
  }
}
