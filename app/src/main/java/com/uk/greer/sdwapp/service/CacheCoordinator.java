package com.uk.greer.sdwapp.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by greepau on 23/03/2015.
 */
public class CacheCoordinator {

    private static CacheCoordinator cacheCoordinator;

    private HashMap<String, Boolean> cacheStatus;

    private CacheCoordinator() {
        cacheStatus = new HashMap<>();
    }

    public static CacheCoordinator getInstance() {
        if (cacheCoordinator == null) {
            Log.i("INFO", "New Instance of CacheCoordinator created");
            cacheCoordinator = new CacheCoordinator();
        }
        return cacheCoordinator;

    }

    public boolean getCacheStatus(String key) {

        try {
            return cacheStatus.get(key);
        }
        catch (Exception x){
            return false;
        }
    }


    public void setCacheStatus(String key, boolean status) {
        cacheStatus.put(key, status);
    }

}
