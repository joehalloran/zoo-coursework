package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.pen.Flyable;
import com.example.jhalloran.zoo.model.pen.Swimmable;
import java.util.UUID;

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

    TextView penType = findViewById(R.id.pen_detail_type);
    TextView penTemperature = findViewById(R.id.pen_detail_temperature_value);
    TextView landArea = findViewById(R.id.pen_detail_land_area_value);
    TextView waterVolume = findViewById(R.id.pen_detail_water_volume_value);
    TextView airVolume = findViewById(R.id.pen_detail_air_volume_value);
    TextView waterType = findViewById(R.id.pen_detail_water_type_value);

    configureViewForAnimalType();

    penType.setText(pen.toString());
    penTemperature.setText(String.valueOf(pen.getTemperature()));
    landArea.setText(String.valueOf(pen.getLandArea()));
    if (pen instanceof Swimmable) {
      Swimmable waterPen = (Swimmable) pen;
      waterVolume.setText(String.valueOf(waterPen.getWaterVolume()));
      waterType.setText(waterPen.getWaterType().toString());
    }
    if (pen instanceof Flyable) {
      Flyable flyingPen = (Flyable) pen;
      airVolume.setText(String.valueOf(flyingPen.getAirVolume()));
    }
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
}
