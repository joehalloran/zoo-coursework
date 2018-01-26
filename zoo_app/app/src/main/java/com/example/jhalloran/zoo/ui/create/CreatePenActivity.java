package com.example.jhalloran.zoo.ui.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.pen.AquariumPen;
import com.example.jhalloran.zoo.model.pen.AviaryPen;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.PartWaterPartDryPen;
import com.example.jhalloran.zoo.model.pen.PettingPen;
import com.example.jhalloran.zoo.model.shared.WaterType;
import com.example.jhalloran.zoo.ui.manager.ZooManagerActivity;

public class CreatePenActivity extends AppCompatActivity implements OnItemSelectedListener {

  private final Zoo zoo = Zoo.getInstance();
  private Intent manageZooIntent;
  private TextView penName;
  private String penTypeSelected;
  private EditText penTemperature;
  private EditText penLength;
  private EditText penWidth;
  private EditText penWaterDepth;
  private EditText penHeight;
  private View waterTypeTitle;
  private RadioGroup waterTypeRadios;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_pen);

    final Button cancelButton = findViewById(R.id.create_pen_cancel_button);
    manageZooIntent = new Intent(this, ZooManagerActivity.class);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
    final Button saveButton = findViewById(R.id.create_pen_save_button);
    saveButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        savePen();
      }
    });

    Spinner spinner = findViewById(R.id.create_pen_type_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.pen_types, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    penName = findViewById(R.id.create_pen_name);
    penTemperature = findViewById(R.id.create_pen_temperature);
    penLength = findViewById(R.id.create_pen_length);
    penWidth = findViewById(R.id.create_pen_width);
    penWaterDepth = findViewById(R.id.create_pen_water_depth);
    penHeight = findViewById(R.id.create_pen_air_height);
    waterTypeTitle = findViewById(R.id.create_pen_water_type_title);
    waterTypeRadios = findViewById(R.id.create_pen_water_type_view_group);
  }

  private void savePen() {
    switch (penTypeSelected) {
      case "Part water part dry pen":
        zoo.addPen(createPartWaterPartDryPen());
        break;
      case "Dry pen":
        zoo.addPen(createDryPen());
        break;
      case "Aquarium":
        zoo.addPen(createAquarium());
        break;
      case "Aviary":
        zoo.addPen(createAviary());
        break;
      case "Petting pen":
        zoo.addPen(createPettingPen());
        break;
    }
    startActivity(manageZooIntent);
  }

  private PartWaterPartDryPen createPartWaterPartDryPen() {
    return (new PartWaterPartDryPen(createDryPen(),
        createAquarium())); // TODO, View does not seperate land dims for water dims
  }

  private DryPen createDryPen() {
    return new DryPen(
        penName.getText().toString(),
        Integer.parseInt(penLength.getText().toString()),
        Integer.parseInt(penWidth.getText().toString()),
        Integer.parseInt(penTemperature.getText().toString()));
  }

  private AquariumPen createAquarium() {
    return new AquariumPen(
        penName.getText().toString(),
        getSelectedWaterTypes(),
        Integer.parseInt(penWaterDepth.getText().toString()),
        Integer.parseInt(penLength.getText().toString()),
        Integer.parseInt(penWidth.getText().toString()),
        Integer.parseInt(penTemperature.getText().toString()));
  }

  private WaterType getSelectedWaterTypes() {
    if (waterTypeRadios.getCheckedRadioButtonId() == R.id.fresh_water_type_checkbox) {
      return WaterType.FRESH;
    }
    return WaterType.SALT;
  }

  private AviaryPen createAviary() {
    return new AviaryPen(
        penName.getText().toString(),
        Integer.parseInt(penLength.getText().toString()),
        Integer.parseInt(penWidth.getText().toString()),
        Integer.parseInt(penHeight.getText().toString()),
        Integer.parseInt(penTemperature.getText().toString()));
  }

  private PettingPen createPettingPen() {
    return new PettingPen(
        penName.getText().toString(),
        Integer.parseInt(penLength.getText().toString()),
        Integer.parseInt(penWidth.getText().toString()),
        Integer.parseInt(penTemperature.getText().toString()));
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view,
      int pos, long id) {
    String itemSelected = parent.getItemAtPosition(pos).toString();
    penTypeSelected = itemSelected;
    switch (itemSelected) {
      case "Aquarium":
        configureActivityForAquarium();
        break;
      case "Aviary":
        configureActivityForAviary();
        break;
      case "Dry pen":
        configureActivityForDryPen();
        break;
      case "Petting pen":
        configureActivityForDryPen();
        break;
      case "Part water part dry pen":
        configureActivityForAquarium();
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing
  }

  private void configureActivityForAquarium() {
    penWaterDepth.setVisibility(View.VISIBLE);
    waterTypeTitle.setVisibility(View.VISIBLE);
    waterTypeRadios.setVisibility(View.VISIBLE);
    penHeight.setVisibility(View.GONE);
  }

  private void configureActivityForAviary() {
    penWaterDepth.setVisibility(View.GONE);
    waterTypeTitle.setVisibility(View.GONE);
    waterTypeRadios.setVisibility(View.GONE);
    penHeight.setVisibility(View.VISIBLE);
  }

  private void configureActivityForDryPen() {
    penWaterDepth.setVisibility(View.GONE);
    waterTypeTitle.setVisibility(View.GONE);
    waterTypeRadios.setVisibility(View.GONE);
    penHeight.setVisibility(View.GONE);
  }
}