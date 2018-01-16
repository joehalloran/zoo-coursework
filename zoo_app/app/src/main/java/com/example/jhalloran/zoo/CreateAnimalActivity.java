package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class CreateAnimalActivity extends AppCompatActivity implements OnItemSelectedListener {

  private final Zoo zoo = Zoo.getInstance();
  private Intent manageZooIntent;
  private EditText species;
  private EditText landAreaRequired;
  private String animalTypeSelected;
  private CheckBox aquariumCheckBox;
  private CheckBox avairyCheckbox;
  private CheckBox dryPenCheckbox;
  private EditText waterVolumeInput;
  private EditText airVolumeInput;
  private View waterTypeTitle;
  private RadioGroup waterTypeRadios;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_animal);

    final Button cancelButton = findViewById(R.id.create_animal_cancel_button);
    manageZooIntent = new Intent(this, ZooManagerActivity.class);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
    final Button saveButton = findViewById(R.id.create_animal_save_button);
    saveButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        saveAnimal();
      }
    });

    Spinner spinner = (Spinner) findViewById(R.id.create_animal_type_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.animal_types, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    species = findViewById(R.id.create_animal_input_species);
    landAreaRequired = findViewById(R.id.create_animal_land_area_required);
    aquariumCheckBox = findViewById(R.id.aquarium_pen_type_checkbox);
    avairyCheckbox = findViewById(R.id.aviary_pen_type_checkbox);
    dryPenCheckbox = findViewById(R.id.dry_pen_type_checkbox);
    waterVolumeInput = findViewById(R.id.create_animal_water_volume_required);
    airVolumeInput = findViewById(R.id.create_animal_air_volume_required);
    waterTypeRadios = findViewById(R.id.create_animal_water_type_view_group);
    waterTypeTitle = findViewById(R.id.create_animal_water_type_title);

  }

  private void saveAnimal() {
    startActivity(new Intent(this, ZooManagerActivity.class));
    switch (animalTypeSelected) {
      case "Land animal":
        createLandAnimal();
        break;
      case "Flying animal":
        createFlyingAnimal();
        break;
      case "Swimming animal":
        createSwimmingAnimal();
        break;
    }
    startActivity(manageZooIntent);
  }

  private void createLandAnimal() {
    zoo.addAnimal(new LandAnimal(species.getText().toString(),
        Integer.parseInt(landAreaRequired.getText().toString()), getSelectedPenTypes()));
  }

  private void createFlyingAnimal() {
    zoo.addAnimal(
        new FlyingAnimal.Builder()
            .setName(species.getText().toString())
            .setLandAreaRequired(Integer.parseInt(landAreaRequired.getText().toString()))
            .setAirVolumeRequired(Integer.parseInt(airVolumeInput.getText().toString()))
            .setPenTypes(getSelectedPenTypes())
            .build());
  }

  private void createSwimmingAnimal() {
    zoo.addAnimal(
        new SwimmingAnimal.Builder()
            .setName(species.getText().toString())
            .setLandAreaRequired(Integer.parseInt(landAreaRequired.getText().toString()))
            .setWaterVolumeRequired(Integer.parseInt(waterVolumeInput.getText().toString()))
            .setPenTypes(getSelectedPenTypes())
            .setWaterTypes(getSelectedWaterTypes())
            .build());
  }

  private Set<PenType> getSelectedPenTypes() {
    Set<PenType> penTypes = new HashSet<>();
    if (dryPenCheckbox.isChecked()) {
      penTypes.add(PenType.DRY);
    }
    if (aquariumCheckBox.isChecked()) {
      penTypes.add(PenType.AQUARIUM);
    }
    if (avairyCheckbox.isChecked()) {
      penTypes.add(PenType.AVIARY);
    }
    return penTypes;
  }

  private Set<WaterType> getSelectedWaterTypes() {
    if (waterTypeRadios.getCheckedRadioButtonId() == R.id.fresh_water_type_checkbox) {
      return EnumSet.of(WaterType.FRESH);
    }
    return EnumSet.of(WaterType.SALT);
  }


  @Override
  public void onItemSelected(AdapterView<?> parent, View view,
      int pos, long id) {
    String itemSelected = parent.getItemAtPosition(pos).toString();
    animalTypeSelected = itemSelected;
    switch (itemSelected) {
      case "Land animal":
        configureActivityForLandAnimal();
        break;
      case "Flying animal":
        configureActivityForFlyingAnimal();
        break;
      case "Swimming animal":
        configureActivityForSwimmingAnimal();
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing
  }

  private void configureActivityForLandAnimal() {
    hideAndUnCheckBox(aquariumCheckBox);
    hideAndUnCheckBox(avairyCheckbox);
    dryPenCheckbox.setVisibility(View.VISIBLE);
    waterVolumeInput.setVisibility(View.GONE);
    waterTypeRadios.setVisibility(View.GONE);
    waterTypeTitle.setVisibility(View.GONE);
    airVolumeInput.setVisibility(View.GONE);
  }

  private void configureActivityForFlyingAnimal() {
    hideAndUnCheckBox(aquariumCheckBox);
    hideAndUnCheckBox(dryPenCheckbox);
    avairyCheckbox.setVisibility(View.VISIBLE);
    waterVolumeInput.setVisibility(View.GONE);
    waterTypeRadios.setVisibility(View.GONE);
    waterTypeTitle.setVisibility(View.GONE);
    airVolumeInput.setVisibility(View.VISIBLE);
  }

  private void configureActivityForSwimmingAnimal() {
    hideAndUnCheckBox(avairyCheckbox);
    hideAndUnCheckBox(dryPenCheckbox);
    aquariumCheckBox.setVisibility(View.VISIBLE);
    waterVolumeInput.setVisibility(View.VISIBLE);
    waterTypeRadios.setVisibility(View.VISIBLE);
    waterTypeTitle.setVisibility(View.VISIBLE);
    airVolumeInput.setVisibility(View.GONE);
  }

  private void hideAndUnCheckBox(CheckBox checkbox) {
    checkbox.setVisibility(View.GONE);
    checkbox.setChecked(false);
  }


}
