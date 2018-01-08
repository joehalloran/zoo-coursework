package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.jhalloran.zoo.model.ZooManager;

public class MainActivity extends AppCompatActivity {
  public static final String EXTRA_ZOO_NAME = "com.example.jhalloran.zoo.zooname";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void createZoo(View view){
    Intent intent = new Intent(this, ZooOverviewActivity.class);
    EditText editText = (EditText) findViewById(R.id.createZooText);
    String zooName = editText.getText().toString();
    ZooManager zooManager = ZooManager.getInstance();
    zooManager.addZoo(zooName);
    intent.putExtra(EXTRA_ZOO_NAME, zooName);
    startActivity(intent);
  }
}
