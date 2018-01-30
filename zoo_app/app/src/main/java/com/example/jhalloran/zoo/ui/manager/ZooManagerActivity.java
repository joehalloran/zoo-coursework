package com.example.jhalloran.zoo.ui.manager;

import static com.example.jhalloran.zoo.ZooConstants.ARG_PAGE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.jhalloran.zoo.R;
import com.example.jhalloran.zoo.concurrent.ZooThreadPoolManager;
import com.example.jhalloran.zoo.io.ZooFileManager;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.ui.allocator.AutoAllocatorActivity;
import com.example.jhalloran.zoo.ui.create.CreateAnimalActivity;
import com.example.jhalloran.zoo.ui.create.CreatePenActivity;
import com.example.jhalloran.zoo.ui.create.CreateZookeeperActivity;
import java.util.concurrent.Executor;

/**
 * Controller for auto-allocation layout. Tabbed view.
 */
public class ZooManagerActivity extends AppCompatActivity {

  private static final String TAG = "ZooManagerActivity";
  private final ZooFileManager zooFileManager = new ZooFileManager(this);
  private ViewPager viewPager;
  private Zoo zoo = Zoo.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoo_manager);

    // Enable toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Enable up navigation
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    // Initialize button and declare onClick listener
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Outcome is dependant on current active tab.
        startActivity(getCreateItemIntent(viewPager.getCurrentItem()));
      }
    });
  }

  private Intent getCreateItemIntent(int position) {
    Intent createItemIntent;
    switch (position) {
      case 0:
        createItemIntent = new Intent(this, CreateAnimalActivity.class);
        break;
      case 1:
        createItemIntent = new Intent(this, CreatePenActivity.class);
        break;
      case 2:
        createItemIntent = new Intent(this, CreateZookeeperActivity.class);
        break;
      default:
        Log.e(TAG, String.format("Invalid tab value %d when trying to create item", position));
        createItemIntent = new Intent(this, ZooManagerActivity.class);
    }
    return createItemIntent;
  }

  @Override
  protected void onResume() {
    super.onResume();
    //  Update UI
    populatePagerView();
    // Write zoo to file, incase previous action changed state.
    Executor executor = ZooThreadPoolManager.getBackgroundThreadExecutor();
    executor.execute(new Runnable() {
      @Override
      public void run() {
        zooFileManager.writeZooToFile();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_zoo_manager, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.allocate_menu_item:
        startActivity(new Intent(this, AutoAllocatorActivity.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void populatePagerView() {
    // Set up swipe tabs
    ZooContentPagerAdapter zooContentPagerAdapter = new ZooContentPagerAdapter(
        getSupportFragmentManager());
    viewPager = findViewById(R.id.zoo_manager_pager);
    viewPager.setAdapter(zooContentPagerAdapter);

    // Setup tab navigation
    TabLayout tabLayout = findViewById(R.id.zoo_selector_tabs);
    tabLayout.setupWithViewPager(viewPager);
  }

  // Manages tabbed behaviour of layput. Keeps track of current tab.
  public class ZooContentPagerAdapter extends FragmentStatePagerAdapter {

    ZooContentPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int i) {
      Fragment fragment = new ZooContentFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_PAGE_NUMBER.getValue(), i + 1);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public int getCount() {
      return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      String title = null;
      switch (position) {
        case 0:
          title = "Animals";
          break;
        case 1:
          title = "Pens";
          break;
        case 2:
          title = "Zookeepers";
          break;
      }
      return title;
    }
  }
}
