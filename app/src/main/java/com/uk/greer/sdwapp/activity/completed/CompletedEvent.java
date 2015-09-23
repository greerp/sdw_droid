package com.uk.greer.sdwapp.activity.completed;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.activity.upcoming.EventMyStatsFragment;
import com.uk.greer.sdwapp.config.BundleProperty;


public class CompletedEvent extends ActionBarActivity {

    private int ttId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            ttId = bundle.getInt(BundleProperty.TT_EVENT_ID);
        }
        setContentView(R.layout.completed_event);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        //slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_completed_event, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabtitles[] = new String[]
                {"SUMMARY","HCAP RESULT", "SCR RESULT"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return EventSummaryFragment.newInstance(0);
                case 1:
                    return EventResultFragment.newInstance(BundleProperty.COMPETITION.SCRATCH, ttId);

                case 2:
                    return EventResultFragment.newInstance(BundleProperty.COMPETITION.HANDICAP, ttId);
                default:
                    return EventMyStatsFragment.newInstance(ttId);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabtitles[position];
        }
   }
}