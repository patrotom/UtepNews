package edu.utep.cs.cs4330.utepnews;

import android.app.IntentService;
import android.content.Intent;

public class ConnectionCheckService extends IntentService {
    public ConnectionCheckService() {
        super("ConnectionCheckService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!Utilities.isInternetWorking(this))
                break;
        }
        startActivity(new Intent(this, NoInternetActivity.class));
    }

}
