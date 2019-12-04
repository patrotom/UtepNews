package edu.utep.cs.cs4330.utepnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class NoInternetActivity extends AppCompatActivity {
    private boolean retryAlreadyClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        retryAlreadyClicked = false;
    }

    public void retryClicked(View view) {
        if (!retryAlreadyClicked) {
            new AsyncTask<Context, Void, Pair<Context, Boolean>>() {
                protected void onPreExecute() {
                    retryAlreadyClicked = true;
                }
                protected Pair<Context, Boolean> doInBackground(Context... params) {
                    return new Pair<>(params[0], Utilities.isInternetWorking(params[0]));
                }
                protected void onPostExecute(Pair<Context, Boolean> result) {
                    if (result.second.booleanValue()) {
                        startService(new Intent(result.first, ConnectionCheckService.class));
                        startActivity(new Intent(result.first, MainActivity.class));
                    }
                    else {
                        makeToast();
                    }
                    retryAlreadyClicked = false;
                }
            }.execute(this);
        }
        else
            makeToast();
    }

    private void makeToast() {
        Toast.makeText(this, "Still no Internet!", Toast.LENGTH_LONG).show();
    }
}
