package shum.ru.myzp.Controller;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by user on 10/16/17.
 */

public class Message {


    public static void shortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    public static void longToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void debugLog(String message){
        Log.d("DEBUG_LOG", message);
    }





}
