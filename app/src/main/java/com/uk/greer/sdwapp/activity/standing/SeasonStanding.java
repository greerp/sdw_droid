package com.uk.greer.sdwapp.activity.standing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.inqbarna.tablefixheaders.samples.FamilyTable;
import com.inqbarna.tablefixheaders.samples.SimpleTable;
import com.inqbarna.tablefixheaders.samples.StyleTable;
import com.uk.greer.sdwapp.R;

public class SeasonStanding extends ActionBarActivity {

    public static Fragment newInstance() {
        String data[][]={
                {"Name","Last","PB","Date"},
                {"Paul Greer","23:45","23:27","12-Sep"}
        };

        return SimpleTable.newInstance(data);
        //return FamilyTable.newInstance(data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.season_standing);
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrx = fragMan.beginTransaction();
        fragTrx.add(R.id.standings_container, newInstance());
        fragTrx.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_season_standing, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
