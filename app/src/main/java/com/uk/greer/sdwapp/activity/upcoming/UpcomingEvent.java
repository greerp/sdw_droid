package com.uk.greer.sdwapp.activity.upcoming;

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

/**
 * Displays the details for a specific TT event
 */
public class UpcomingEvent extends ActionBarActivity {

    private static final String TT_ID = "tt_id";
    private int ttId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            ttId = bundle.getInt(TT_ID);
        }
        setContentView(R.layout.upcoming_event);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Set a custom tab item
        //slidingTabLayout.setCustomTabView(R.layout.tab_title, R.id.tabtext);
        //slidingTabLayout.canScrollHorizontally(View.TEXT_DIRECTION_ANY_RTL);
        //slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upcoming_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if ( id==android.R.id.home ) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 4;
        private String tabtitles[] = new String[]
               {"INFO", "ENTRIES", "MY STATS","RECORDS"};

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
                    return EventInfoFragment.newInstance(ttId);

                case 1:
                    return EntriesListFragment.newInstance(ttId);

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
