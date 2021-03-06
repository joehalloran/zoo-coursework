package com.example.jhalloran.zoo.ui.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.ZooConstants;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Controller for Animal detail view. Displays details of a single {@link Animal}
 */
public class AnimalDetailActivity extends AppCompatActivity {

  private static final String TAG = "AnimalDetail";
  private final Zoo zoo = Zoo.getInstance();
  private Animal animal;
  private UUID uuid;
  private LinearLayout waterVolumeRequiredGroup;
  private LinearLayout airVolumeRequiredGroup;
  private LinearLayout waterTypesGroup;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animal_detail);

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

  // Get animal from model and update view.
  private void setViewContent() {
    Intent intent = getIntent();
    uuid = UUID.fromString(intent.getStringExtra(ZooConstants.ITEM_ID.getValue()));
    animal = zoo.getAnimalById(uuid);

    // Initialize UI items
    waterVolumeRequiredGroup = findViewById(R.id.animal_detail_water_volume);
    airVolumeRequiredGroup = findViewById(R.id.animal_detail_air_volume);
    waterTypesGroup = findViewById(R.id.animal_detail_water_types);
    TextView animalName = findViewById(R.id.animal_detail_name);
    TextView penTypes = findViewById(R.id.animal_detail_pen_types_value);
    CheckBox dangerous = findViewById(R.id.animal_detail_dangerous_value);
    TextView landAreaRequired = findViewById(R.id.animal_detail_land_area_value);
    TextView waterVolumeRequired = findViewById(R.id.animal_detail_water_volume_value);
    TextView airVolumeRequired = findViewById(R.id.animal_detail_air_volume_value);
    TextView waterTypes = findViewById(R.id.animal_detail_water_types_value);

    configureViewForAnimalType();

    // Update UI items with animal details
    animalName.setText(animal.getName());
    penTypes.setText(getPenTypesText());
    dangerous.setChecked(animal.isDangerous());
    landAreaRequired.setText(String.valueOf(animal.getLandAreaRequired()));
    if (animal instanceof Swimmer) {
      Swimmer swimmingAnimal = (Swimmer) animal;
      waterVolumeRequired.setText(String.valueOf(swimmingAnimal.getWaterVolumeRequired()));
      waterTypes.setText(getWaterTypesText(swimmingAnimal));
    }
    if (animal instanceof Flyer) {
      Flyer flyingAnimal = (Flyer) animal;
      airVolumeRequired.setText(String.valueOf(flyingAnimal.getAirVolumeRequired()));
    }

    setAssignedToTextField();

    // Initialize assignment button with onClick listener
    final Button assignButton = findViewById(R.id.animal_detail_assign_button);
    assignButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        final List<UUID> penIds = getSuitablePenIds();
        if (penIds.size() > 0) {
          CharSequence[] penNames = new CharSequence[penIds.size()];
          for (int i = 0; i < penIds.size(); i++) {
            penNames[i] = zoo.getPenById(penIds.get(i)).toString();
          }
          builder
              .setTitle("Pick a pen")
              .setItems(penNames, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  Enclosable penSelected = zoo.getPenById(penIds.get(which));
                  animal.assignToPen(penSelected);
                  setAssignedToTextField();
                }
              });
        } else if (animal.isAssigned()) {
          builder.setTitle("No alternative pens available \n");
        } else {
          builder.setTitle("No suitable pens available \n");
        }
        builder.create().show();
      }
    });

  }

  // Hide / show relevant fields for different animals
  private void configureViewForAnimalType() {
    if (animal instanceof SwimmingAnimal) {
      waterVolumeRequiredGroup.setVisibility(View.VISIBLE);
      waterTypesGroup.setVisibility(View.VISIBLE);
      airVolumeRequiredGroup.setVisibility(View.GONE);
    } else if (animal instanceof FlyingAnimal) {
      waterVolumeRequiredGroup.setVisibility(View.GONE);
      waterTypesGroup.setVisibility(View.GONE);
      airVolumeRequiredGroup.setVisibility(View.VISIBLE);
    } else if (animal instanceof LandAnimal) {
      waterVolumeRequiredGroup.setVisibility(View.GONE);
      waterTypesGroup.setVisibility(View.GONE);
      airVolumeRequiredGroup.setVisibility(View.GONE);
    } else {
      Log.e(TAG, String.format("Animal %s is not of valid type", animal.getName()));
    }
  }

  // Build string for PenType field
  private String getPenTypesText() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Iterator<PenType> it = animal.getPenTypes().iterator(); it.hasNext(); ) {
      stringBuilder.append(it.next());
      if (it.hasNext()) {
        stringBuilder.append(", ");
      }
    }
    return stringBuilder.toString();
  }

  // Build string for WaterType field
  private String getWaterTypesText(Swimmer swimmer) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Iterator<WaterType> it = swimmer.getWaterTypes().iterator(); it.hasNext(); ) {
      stringBuilder.append(it.next());
      if (it.hasNext()) {
        stringBuilder.append(", ");
      }
    }
    return stringBuilder.toString();
  }

  // Display name of pen assigned to
  private void setAssignedToTextField() {
    Enclosable pen = animal.getAssignedToPen();
    TextView assigned = findViewById(R.id.animal_detail_assigned_value);
    if (pen != null) {
      assigned.setText(pen.toString());
    } else {
      assigned.setText(R.string.unassigned);
    }
  }

  // Filter suitable pens when attempting assignment
  private List<UUID> getSuitablePenIds() {
    final List<UUID> penIds = new ArrayList<>(zoo.getPenIds());
    Predicate<UUID> uuidPredicate = new Predicate<UUID>() {
      @Override
      public boolean test(UUID uuid) {
        Enclosable pen = zoo.getPenById(uuid);
        return !((pen != animal.getAssignedToPen()) && pen.canLiveHere(animal));
      }
    };
    penIds.removeIf(uuidPredicate);
    return penIds;
  }
}
