package com.example.jhalloran.zoo.ui.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.ZooConstants;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.pen.Flyable;
import com.example.jhalloran.zoo.model.pen.Swimmable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Controller for pen detail view. Displays details of a single {@link Enclosable}
 */
public class PenDetailActivity extends AppCompatActivity {
  private static final String TAG = "PenDetail";
  private final Zoo zoo = Zoo.getInstance();
  private Enclosable pen;
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
    // Only refresh content if new content required
    if (!uuid.toString().equals(getIntent().getStringExtra(ZooConstants.ITEM_ID.getValue()))) {
      setViewContent();
    }
  }

  // Get pen from model and update view.
  private void setViewContent() {
    Intent intent = getIntent();
    uuid = UUID.fromString(intent.getStringExtra(ZooConstants.ITEM_ID.getValue()));
    pen = zoo.getPenById(uuid);

    // Initialize UI items
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

    // Update UI items with pen details
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

    // Initialize assignment button with onClick listener
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
                  pen.assignToZookeeper(zookeeperSelected);
                  setAssignedToTextField();
                }
              });
        } else if (pen.isAssigned()) {
          builder.setTitle("No alternative zookeepers available \n");
        } else {
          builder.setTitle("No suitable pens available \n");
        }
        builder.create().show();
      }
    });
  }

  // Hide / show relevant fields for different pens
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

  // Display name of Zookeeper assigned to
  private void setAssignedToTextField() {
    Zookeeper zookeeper = pen.getAssignedToZookeeper();
    TextView assigned = findViewById(R.id.pen_detail_assigned_value);
    if (zookeeper != null) {
      assigned.setText(zookeeper.toString());
    } else {
      assigned.setText(R.string.unassigned);
    }
  }

  // Filter suitable Zookeepers when attempting assignment
  private List<UUID> getSuitableZookeeperIds() {
    final List<UUID> zookeeperIds = new ArrayList<>(zoo.getZookeeperIds());
    Predicate<UUID> uuidPredicate = new Predicate<UUID>() {
      @Override
      public boolean test(UUID uuid) {
        Zookeeper zookeeper = zoo.getZookeeperById(uuid);
        return !((zookeeper != pen.getAssignedToZookeeper()) && zookeeper.canManagerPen(pen));
      }
    };
    zookeeperIds.removeIf(uuidPredicate);
    return zookeeperIds;
  }
}

