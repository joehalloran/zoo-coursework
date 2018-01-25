package com.example.jhalloran.zoo.ui.create;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.ui.manager.ZooManagerActivity;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.HashSet;
import java.util.Set;

public class CreateZookeeperActivity extends AppCompatActivity {
  private final Zoo zoo = Zoo.getInstance();
  private Intent manageZooIntent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_zookeeper);

    final Button cancelButton = findViewById(R.id.create_zookeeper_cancel_button);
    manageZooIntent = new Intent(this, ZooManagerActivity.class);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
    final Button saveButton = findViewById(R.id.create_zookeeper_save_button);
    saveButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        saveZookeeper();
      }
    });
  }

  private void saveZookeeper() {
    EditText nameView = findViewById(R.id.create_zookeeper_name);
    zoo.addZookeeper(
        new Zookeeper(nameView.getText().toString(), getSelectedPenTypes()));
    startActivity(manageZooIntent);
  }

  private Set<PenType> getSelectedPenTypes() {
    Set<PenType> penTypes = new HashSet<>();
    CheckBox aquariumCheckBox = findViewById(R.id.aquarium_pen_type_checkbox);
    CheckBox aviaryCheckbox = findViewById(R.id.aviary_pen_type_checkbox);
    CheckBox dryPenCheckbox = findViewById(R.id.dry_pen_type_checkbox);
    if (dryPenCheckbox.isChecked()) {
      penTypes.add(PenType.DRY);
    }
    if (aquariumCheckBox.isChecked()) {
      penTypes.add(PenType.AQUARIUM);
    }
    if (aviaryCheckbox.isChecked()) {
      penTypes.add(PenType.AVIARY);
    }
    return penTypes;
  }
}
