/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uk.greer.sdwapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.inqbarna.tablefixheaders.samples.FamilyTable;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uk.greer.sdwapp.activity.completed.CompletedListFragment;
import com.uk.greer.sdwapp.activity.standing.SeasonStanding;
import com.uk.greer.sdwapp.activity.standing.SeasonStandingListFragment;
import com.uk.greer.sdwapp.activity.upcoming.UpcomingListFragment;
import com.uk.greer.sdwapp.activity.upcoming.UpcomingRefreshFragment;
import com.uk.greer.sdwapp.service.CacheCoordinator;

import java.util.List;


public class Main extends FragmentActivity
        implements UpcomingRefreshFragment.OnFragmentInteractionListener {

    protected SpiceManager spiceManager
            = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private Intent intent;


    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    private void performRequest() {

        spiceManager.execute(new SpiceRequest<String>(String.class) {
            @Override
            public String loadDataFromNetwork() throws Exception {
                Thread.sleep(2000);
                return "finished";
            }
        }, new RequestListener<String>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
            }

            @Override
            public void onRequestSuccess(String o) {
                CacheCoordinator.getInstance().setCacheStatus("events", true);
                CacheCoordinator.getInstance().setCacheStatus("standings", true);
                List<Fragment> fl = getSupportFragmentManager().getFragments();
                for (Fragment f : fl) {
                    ((OnDataReady) f).Notify();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("INFO", "Configuration Changed");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //TODO: Save position in list - Test with a configuratino change - Ctrl 11 in emulatro
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long ttId = getIntent().getLongExtra("TT_ID", -1);
        Log.i("INFO", "TT ID: " + Long.toString(ttId));

        setContentView(R.layout.main);

        // Create the adapter that will return a fragment for each of the three primary sections of the app.
        FragmentManager fm = getSupportFragmentManager();
        AppSectionsPagerAdapter pagerAdapter = new AppSectionsPagerAdapter(fm);

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical parent.
        getActionBar().setHomeButtonEnabled(false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AppSectionsPagerAdapter(getSupportFragmentManager()));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        performRequest();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        private String tabtitles[] = new String[] {
            getResources().getString(R.string.page_upcoming),
            getResources().getString(R.string.page_completed),
            getResources().getString(R.string.page_standings)
        };

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return UpcomingListFragment.newInstance(i,tabtitles[i]);
                case 1:
                    return CompletedListFragment.newInstance(i, tabtitles[i]);
                case 2:
                    return SeasonStandingListFragment.newInstance(i,tabtitles[i],3);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabtitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}
