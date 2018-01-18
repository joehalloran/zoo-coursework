package com.example.jhalloran.zoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.pen.Flyable;
import com.example.jhalloran.zoo.model.pen.Swimmable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import org.w3c.dom.Text;

public class PenDetailActivity extends AppCompatActivity {
  private static final String TAG = "PenDetail";
  private final Zoo zoo = Zoo.getInstance();
  private Enclosure pen;
  private UUID uuid;

  LinearLayout waterVolumeGroup;
  LinearLayout airVolumeGroup;
  LinearLayout waterTypeGroup;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pen_detail);

    // Enable toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Enable up navigation
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    setViewContent();
  }

  @Override
  public void onResume() {
    super.onResume();
    // Only refresh content if new required
    if (!uuid.toString().equals(getIntent().getStringExtra(ZooConstants.ITEM_ID))) {
      setViewContent();
    }
  }

  private void setViewContent() {
    Intent intent = getIntent();
    uuid = UUID.fromString(intent.getStringExtra(ZooConstants.ITEM_ID));
    pen = zoo.getPenById(uuid);

    waterVolumeGroup = findViewById(R.id.pen_detail_water_volume);
    airVolumeGroup = findViewById(R.id.pen_detail_air_volume);
    waterTypeGroup = findViewById(R.id.pen_detail_water_type);

    TextView penName = findViewById(R.id.pen_detail_name);
    TextView penType = findViewById(R.id.pen_detail_type_value);
    TextView penTemperature = findViewById(R.id.pen_detail_temperature_value);
    TextView landArea = findViewById(R.id.pen_detail_land_area_value);
    TextView waterVolume = findViewById(R.id.pen_detail_water_volume_value);
    TextView airVolume = findViewById(R.id.pen_detail_air_volume_value);
    TextView waterType = findViewById(R.id.pen_detail_water_type_value);

    configureViewForAnimalType();

    penName.setText(pen.toString());
    penType.setText(pen.getType().toString());
    penTemperature.setText(String.valueOf(pen.getTemperature()));
    landArea.setText(String.valueOf(pen.getLandArea()));
    setAssignedToTextField();
    if (pen instanceof Swimmable) {
      Swimmable waterPen = (Swimmable) pen;
      waterVolume.setText(String.valueOf(waterPen.getWaterVolume()));
      waterType.setText(waterPen.getWaterType().toString());
    }
    if (pen instanceof Flyable) {
      Flyable flyingPen = (Flyable) pen;
      airVolume.setText(String.valueOf(flyingPen.getAirVolume()));
    }

    final Button assignButton = findViewById(R.id.pen_detail_assign_button);
    assignButton.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        final List<UUID> zookeeperIds = getSuitableZookeeperIds();
        if (zookeeperIds.size() > 0) {
          CharSequence[] zookeeperNames = new CharSequence[zookeeperIds.size()];
          for (int i = 0; i < zookeeperIds.size(); i++ ) {
            zookeeperNames[i] = zoo.getZookeeperById(zookeeperIds.get(i)).toString();
          }
          builder
              .setTitle("Pick a pen")
              .setItems(zookeeperNames, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  Zookeeper zookeeperSelected = zoo.getZookeeperById(zookeeperIds.get(which));
                  try {
                    pen.assignToZookeeper(zookeeperSelected);
                  } catch (Exception e) {
                    Log.e(TAG, "Error");
                  }
                  setAssignedToTextField();
                }
              });
        } else {
          builder.setTitle("No suitable pens available");
        }
        builder.create().show();
      }
    });
  }

  private void configureViewForAnimalType() {
    if (pen instanceof Swimmable) {
      waterVolumeGroup.setVisibility(View.VISIBLE);
      waterTypeGroup.setVisibility(View.VISIBLE);
      airVolumeGroup.setVisibility(View.GONE);
    } else if (pen instanceof Flyable) {
      waterVolumeGroup.setVisibility(View.GONE);
      waterTypeGroup.setVisibility(View.GONE);
      airVolumeGroup.setVisibility(View.VISIBLE);
    } else {
      waterVolumeGroup.setVisibility(View.GONE);
      waterTypeGroup.setVisibility(View.GONE);
      airVolumeGroup.setVisibility(View.GONE);
    }
  }

  private void setAssignedToTextField() {
    Zookeeper zookeeper = pen.getAssignedToZookeeper();
    TextView assigned = findViewById(R.id.pen_detail_assigned_value);
    if (zookeeper != null) {
      assigned.setText(zookeeper.toString());
    } else {
      assigned.setText(R.string.unassigned);
    }
  }

  private List<UUID> getSuitableZookeeperIds() {
    final List<UUID> zookeeperIds = new ArrayList<>(zoo.getZookeeperIds());
    Predicate<UUID> uuidPredicate = new Predicate<UUID>() {
      @Override
      public boolean test(UUID uuid) {
        return !(zoo.getZookeeperById(uuid).getPenTypesCanManage().contains(pen.getType()));
      }
    };
    zookeeperIds.removeIf(uuidPredicate);
    return zookeeperIds;
  }
}

