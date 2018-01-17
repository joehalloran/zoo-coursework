package com.example.jhalloran.zoo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.example.jhalloran.zoo.model.Zoo;


public class ZooManagerActivity extends AppCompatActivity {
  private static final String TAG = "ZooManagerActivity";

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


    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
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
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_zoo_manager, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onResume(){
    super.onResume();
    populatePagerView();
  }

  private void  populatePagerView() {
    // Set up swipe tabs
    ZooContentPagerAdapter zooContentPagerAdapter = new ZooContentPagerAdapter(getSupportFragmentManager());
    viewPager = findViewById(R.id.zoo_manager_pager);
    viewPager.setAdapter(zooContentPagerAdapter);

    // Setup tab navigation
    TabLayout tabLayout = findViewById(R.id.zoo_selector_tabs);
    tabLayout.setupWithViewPager(viewPager);
  }

  public class ZooContentPagerAdapter extends FragmentStatePagerAdapter {
    ZooContentPagerAdapter(FragmentManager fm){
      super(fm);
    }

    @Override
    public Fragment getItem(int i) {
      Fragment fragment = new ZooContentFragment();
      Bundle args = new Bundle();
      args.putInt(ZooContentFragment.ARG_PAGE_NUMBER, i+1);
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
