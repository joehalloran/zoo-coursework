package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.ZooManager;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  public static final String EXTRA_ZOO_NAME = "com.example.jhalloran.zoo.zooname";

  private final ZooManager zooManager = ZooManager.getInstance();

  OnClickListener btnClicked = new OnClickListener() {
    @Override
    public void onClick(View v) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    displayZooList();
  }

  public void createZoo(View view){
    Intent intent = new Intent(this, ZooOverviewActivity.class);
    EditText editText = (EditText) findViewById(R.id.createZooText);
    String zooName = editText.getText().toString();
    Zoo newZoo = new Zoo(zooName, true);
    zooManager.addZoo(newZoo);
    zooManager.setActiveZoo(newZoo);
    // intent.putExtra(EXTRA_ZOO_NAME, zooName);
    startActivity(intent);
  }

  @Override
  public void onResume(){
    super.onResume();
    displayZooList();
  }

  private void displayZooList() {
    List<Zoo> zooList = zooManager.getZoos();
    ArrayAdapter<Zoo> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text_row, zooList);
    ListView listView = (ListView) findViewById(R.id.zoosList);
    listView.setAdapter(arrayAdapter);
    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.zoosListLinearLayout);
    linearLayout.removeAllViews();
    Button button;
    final Intent intent = new Intent(this, ZooOverviewActivity.class);
    for (Zoo zoo : zooList) {
      button = new Button(this);
      button.setHeight(50);
      button.setWidth(50);
      button.setTag(zoo.getName());
      button.setText(String.format("Manage %s", zoo.getName()));
      button.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
          intent.putExtra(EXTRA_ZOO_NAME, v.getTag().toString());
          startActivity(intent);
        }
      });
      linearLayout.addView(button);
    }
  }


}
