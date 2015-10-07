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

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uk.greer.sdwapp.activity.Preferences;
import com.uk.greer.sdwapp.activity.current_season;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.DrawerListAdapter;
import com.uk.greer.sdwapp.service.NavItem;
import com.uk.greer.sdwapp.service.OnDataReady;
import com.uk.greer.sdwapp.service.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;


public class Main extends FragmentActivity
        implements OnFragmentInteractionListener {

    protected SpiceManager spiceManager
            = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerPane;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    // http://www.codepuppet.com/2013/10/06/using-fragments-in-android-with-fragmentactivity/
    private List<Fragment> _fragments = new ArrayList<Fragment>();




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long ttId = getIntent().getLongExtra("TT_ID", -1);
        Log.i("INFO", "TT ID: " + Long.toString(ttId));

        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.main);

        //TODO: Selected item should be driven by user preference
        configureSlidingMenu();
        selectItemFromDrawer(0);
        performRequest();

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

    private void performRequest() {

        Log.i(getLocalClassName(), "Creating listener");
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

                Log.i(getLocalClassName(), "Notifying fragments");
                for (android.support.v4.app.Fragment f : fl) {
                    ((OnDataReady) f).Notify();
                }
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void configureSlidingMenu(){

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavItems.add(new NavItem("Current Season", "TT Events and results for 2014/15", R.drawable.ic_home_black_48dp));
        mNavItems.add(new NavItem("Previous Seasons", "TT results from previous seasons", R.drawable.ic_directions_bike_black_48dp));
        mNavItems.add(new NavItem("Athletes", "Performnace statistics of club athletes", R.drawable.ic_group_black_48dp));
        mNavItems.add(new NavItem("Me", "My performnace statistics", R.drawable.ic_person_black_48dp));
        mNavItems.add(new NavItem("Preferences", "user Preferences", R.drawable.ic_settings_black_48dp));
        mNavItems.add(new NavItem("About", "About the application", R.drawable.ic_info_black_48dp));


        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
    }

    private void selectItemFromDrawer(int position) {
        NavItem navItem = mNavItems.get(position);
        Fragment content=null;

        switch (position) {
            case 0:
                content = current_season.newInstance("", "");
                break;
            case 1:
                content = Preferences.newInstance(navItem.mTitle, "");
                break;
            case 2:
                content = Preferences.newInstance(navItem.mTitle, "");
                break;
            case 3:
                content = Preferences.newInstance(navItem.mTitle, "");
                break;
            default:
                content = Preferences.newInstance(navItem.mTitle, "");
                break;
        }

        if ( content!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.maincanvas, content).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mNavItems.get(position).mTitle);
            mDrawerLayout.closeDrawer(mDrawerPane);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        // Contained Fragments can call back into this

    }
}

