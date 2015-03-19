package com.uk.greer.sdwapp;

import android.app.Application;

import com.uk.greer.sdwapp.persist.LocalDataStore;

/**
 * Created by greepau on 18/03/2015.
 */
public class AppManager extends Application {

    private LocalDataStore localDataStore;

    public LocalDataStore getLocalDataStore(){
        if ( localDataStore==null){
            localDataStore = new LocalDataStore(this);
        }

        return localDataStore;
    }

}
