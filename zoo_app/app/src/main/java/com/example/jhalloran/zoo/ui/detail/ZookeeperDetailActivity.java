package com.example.jhalloran.zoo.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.ZooConstants;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Iterator;
import java.util.UUID;

/**
 * Controller for zookeeper detail view. Displays details of a single {@link Zookeeper}
 */
public class ZookeeperDetailActivity extends AppCompatActivity {

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
    // Only refresh content if new content required
    if (!uuid.toString().equals(getIntent().getStringExtra(ZooConstants.ITEM_ID.getValue()))) {
      setViewContent();
    }
  }

  // Get Zookeeper from model and update view.
  private void setViewContent() {
    Intent intent = getIntent();
    uuid = UUID.fromString(intent.getStringExtra(ZooConstants.ITEM_ID.getValue()));
    zookeeper = zoo.getZookeeperById(uuid);

    // Initialize UI items
    TextView zookeeperName = findViewById(R.id.zookeeper_detail_name);
    TextView zookeeperResponsibilities = findViewById(R.id.zookeeper_detail_pen_types_value);

    // Update UI items with Zookeeper details
    zookeeperName.setText(zookeeper.getName());
    StringBuilder stringBuilder = new StringBuilder();
    for (Iterator<PenType> it = zookeeper.getPenTypesCanManage().iterator(); it.hasNext(); ) {
      stringBuilder.append(it.next());
      if (it.hasNext()) {
        stringBuilder.append(", ");
      }
    }
    zookeeperResponsibilities.setText(stringBuilder.toString());
  }
}
