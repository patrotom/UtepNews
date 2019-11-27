package edu.utep.cs.cs4330.utepnews;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utilities {
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            Log.e("IIA", e.getMessage());
        }
        return false;
    }
}
