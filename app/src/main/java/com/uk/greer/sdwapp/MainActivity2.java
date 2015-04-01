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

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uk.greer.sdwapp.activity.upcoming.UpcomingListFragment;
import com.uk.greer.sdwapp.activity.upcoming.UpcomingRefreshFragment;
import com.uk.greer.sdwapp.service.CacheCoordinator;

import java.util.List;


public class MainActivity2 extends FragmentActivity
        implements ActionBar.TabListener, UpcomingRefreshFragment.OnFragmentInteractionListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    private static Context context;
    ViewPager mViewPager;

    protected SpiceManager spiceManager
            = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private Intent intent;


    public static Context getContext() {
        return context;
    }

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

    private void performRequest(String user) {

        spiceManager.execute(new SpiceRequest<String>(String.class) {
            @Override
            public String loadDataFromNetwork() throws Exception {

                //CacheCoordinator.getInstance().setCacheStatus("events",false);

                Thread.sleep(2000);
                return "finished";
            }
        }, new RequestListener<String>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(String o) {

                CacheCoordinator.getInstance().setCacheStatus("events",true);
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
        Log.i("INFO", "MainActivity2 - Configuration Changed");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //TODO: Save position in list - Test with a configuratino change - Ctrl 11 in emulatro

//        String r = Double.toString(Math.random());
//        outState.putString("RANDOM", r);
//        Log.i("INFO", "Saving " + r + " to activity state");
        super.onSaveInstanceState(outState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        long ttId = getIntent().getLongExtra("TT_ID",-1);
        Log.i("INFO","TT ID: " + Long.toString(ttId));

        context = this.getApplicationContext();
        setContentView(R.layout.activity_main_activity2);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.

        FragmentManager fm = getSupportFragmentManager();
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(fm);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(actionBar.newTab().setText("Upcoming").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Completed").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Standings").setTabListener(this));

        performRequest("");
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return UpcomingListFragment.newInstance(i, getPageTitle(i).toString());
                case 1:
                    return UpcomingListFragment.newInstance(i, getPageTitle(i).toString());
                case 2:
                    return UpcomingListFragment.newInstance(i, "");
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position);
        }

    }

}
