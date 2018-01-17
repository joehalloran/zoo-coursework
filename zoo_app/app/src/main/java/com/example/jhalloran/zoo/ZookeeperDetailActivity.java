package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Iterator;
import java.util.UUID;

public class ZookeeperDetailActivity extends AppCompatActivity {
  private static final String TAG = "ZookeeperDetail";
  private final Zoo zoo = Zoo.getInstance();
  private Zookeeper zookeeper;
  private UUID uuid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zookeeper_detail);

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
    zookeeper = zoo.getZookeeperById(uuid);

    TextView zookeeperName = findViewById(R.id.zookeeper_detail_name);
    TextView zookeeperResponsibilities = findViewById(R.id.zookeeper_detail_pen_types_value);

    zookeeperName.setText(zookeeper.getName());

    StringBuilder stringBuilder = new StringBuilder();
    for (Iterator<PenType> it = zookeeper.getPenTypesCanManage().iterator(); it.hasNext();) {
      stringBuilder.append(it.next());
      if (it.hasNext()) {
        stringBuilder.append(", ");
      }
    }
    zookeeperResponsibilities.setText(stringBuilder.toString());
  }
}
