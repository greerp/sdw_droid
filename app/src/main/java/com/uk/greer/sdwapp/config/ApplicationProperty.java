package com.uk.greer.sdwapp.config;

import android.util.Log;

import com.uk.greer.sdwapp.AppManager;

import java.util.Properties;

/**
 * Created by greepau on 16/03/2015.
 */
public class ApplicationProperty {

    private static Properties properties;
    private static boolean initialized = false;

    private ApplicationProperty() {
    }

    private static Properties getInstance() {
        if (!initialized) {
            //AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(MainActivity2.getContext());
            AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(AppManager.getContext());
            properties = assetsPropertyReader.getProperties("sdw.properties");
            initialized = true;
        }
        return properties;
    }

    public static String get(String name) {

        Properties p = getInstance();

        if (p == null) {
            Log.e("CONFIG", "Unable to read sdw.properties");
            return null;
        } else {
            String value = p.getProperty(name, "");
            Log.d("CONFIG", "value of property " + name + ":" + value);
            return value;
        }
    }

    public static String get(String name, String defaultValue) {
        String value = get(name);
        if (value==null | value=="")
            return defaultValue;
        else
            return value;
    }
}
