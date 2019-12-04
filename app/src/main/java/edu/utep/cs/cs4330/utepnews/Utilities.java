package edu.utep.cs.cs4330.utepnews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class Utilities {
    public static boolean isInternetWorking(Context ctx) {
        if (isNetworkAvailable(ctx)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("asd", "Error checking internet connection", e);
            }
        } else {
            Log.d("asd", "No network available!");
        }
        return false;
    }

    private static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
