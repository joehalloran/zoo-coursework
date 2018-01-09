package com.example.jhalloran.zoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.ZooManager;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ZooOverviewActivity extends AppCompatActivity {
  private final ZooManager zooManager = ZooManager.getInstance();
  private String zooName = "placeholder zoo";
  private Zoo zoo = new Zoo(zooName);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoo_overview);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();
    // Enable the Up button
    ab.setDisplayHomeAsUpEnabled(true);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    Intent intent = getIntent();
    zooName = intent.getStringExtra(MainActivity.EXTRA_ZOO_NAME);
    zoo = zooManager.getZoo(zooName);

    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zooName);

    updateZookeepersList();
    updatePensList();
    updateAnimalsList();

  }

  private void updateZookeepersList() {
    List<Zookeeper> zookeeperList = zoo.getZookeepers();
    ArrayAdapter<Zookeeper> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text_row,
        zookeeperList);
    ListView listView = (ListView) findViewById(R.id.zookeepersOverviewList);
    listView.setAdapter(arrayAdapter);
  }
  private void updatePensList() {
    List<Enclosable> penList = zoo.getPens();
    ArrayAdapter<Enclosable> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text_row,
        penList);
    ListView listView = (ListView) findViewById(R.id.pensOverviewList);
    listView.setAdapter(arrayAdapter);
  }

  private void updateAnimalsList(){
    List<Animal> animalList = zoo.getAnimals();
    ArrayAdapter<Animal> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text_row,
        animalList);
    ListView listView = (ListView) findViewById(R.id.animalsOverviewList);
    listView.setAdapter(arrayAdapter);
  }
}

