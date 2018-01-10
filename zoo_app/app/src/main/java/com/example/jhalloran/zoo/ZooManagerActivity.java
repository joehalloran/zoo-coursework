package com.example.jhalloran.zoo;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ZooManagerActivity extends AppCompatActivity {
  private static final String TAG = "ZooManagerActivity";

  private ZooContentPagerAdapter zooContentPagerAdapter;
  private ViewPager viewPager;
  private TabLayout tabLayout;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoo_manager);

    // Enable toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Enable up navigation
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    tabLayout = (TabLayout) findViewById(R.id.zoo_selector_tabs);
    //tabLayout.addOnTabSelectedListener(onTabSelectedListener);

    zooContentPagerAdapter = new ZooContentPagerAdapter(getSupportFragmentManager());
    viewPager = (ViewPager) findViewById(R.id.zoo_manager_pager);
    viewPager.setAdapter(zooContentPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  public class ZooContentPagerAdapter extends FragmentStatePagerAdapter {
    public ZooContentPagerAdapter(FragmentManager fm){
      super(fm);
    }

    @Override
    public Fragment getItem(int i) {
      Fragment fragment = new ZooContentFragment();
      Bundle args = new Bundle();
      args.putInt(ZooContentFragment.ARG_OBJECT, i+1);
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
          title = "Zookeepers";
          break;
        case 1:
          title = "Pens";
          break;
        case 2:
          title = "Animals";
          break;
      }
      return title;
    }
  }

  public static class ZooContentFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedStateInstance) {
      View rootView = inflater.inflate(R.layout.zoo_manager_content_fragment, container, false);
      Bundle args = getArguments();
      ((TextView) rootView.findViewById(R.id.zoo_manager_content_text)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
      return rootView;
    }
  }
}
