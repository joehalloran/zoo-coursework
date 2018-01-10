package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.ZooManager;
import org.w3c.dom.Text;

public class ZooOverviewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoo_overview);

    // Enable toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Enable up navigation
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    ZooManager zooManager = ZooManager.getInstance();
    Zoo zoo = zooManager.getActiveZoo();

    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zoo.getName());

    TextView zooKeepersTitle = findViewById(R.id.zookeepersOverviewTitle);
    zooKeepersTitle.setText(String.format("Total zookeepers: %d", zoo.getZookeepers().size()));

    TextView pensTitle = findViewById(R.id.pensOverviewTitle);
    pensTitle.setText(String.format("Total pens: %d", zoo.getPens().size()));

    TextView animalsTitle = findViewById(R.id.animalsOverviewTitle);
    animalsTitle.setText(String.format("Total zookeepers: %d", zoo.getAnimals().size()));

    final Button button = findViewById(R.id.manageZooButton);
    final Intent manageZooIntent = new Intent(this, ZooManagerActivity.class);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startActivity(manageZooIntent);
      }
    });
  }
}
