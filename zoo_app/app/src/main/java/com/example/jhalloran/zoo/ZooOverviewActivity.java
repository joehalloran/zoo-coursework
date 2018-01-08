package com.example.jhalloran.zoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.ZooManager;
import com.example.jhalloran.zoo.model.animal.Animal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ZooOverviewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoo_overview);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    Intent intent = getIntent();
    String zooName = intent.getStringExtra(MainActivity.EXTRA_ZOO_NAME);

    ZooManager zooManager = ZooManager.getInstance();
    Zoo zoo = zooManager.getZoo(zooName);

    TextView zooTitle = findViewById(R.id.zooTitle);
    zooTitle.setText(zooName);

    List<Animal> animalList = zoo.getAnimals();

    ArrayAdapter<Animal> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text_row, animalList);

    ListView listView = (ListView) findViewById(R.id.animalsList);
    listView.setAdapter(arrayAdapter);
  }



}

