package shum.ru.myzp.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import shum.ru.myzp.R;

/**
 * Created by user on 10/19/17.
 */

public class SPhelper {


    public static void putSharedPreference(Activity activity, String key, int value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static int getSharedPreference(Activity activity, String key, int defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int debit =  sharedPref.getInt(key, defaultValue);
        return debit;
    }

    public static void putSharedPreference(Activity activity, String key, String value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getSharedPreference(Activity activity, String key, String defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String value =  sharedPref.getString(key, defaultValue);
        return value;
    }


}
