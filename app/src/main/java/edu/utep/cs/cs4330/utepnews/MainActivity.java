package edu.utep.cs.cs4330.utepnews;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Button registerButton;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        checkAsyncConn();
    }

    private void checkAsyncConn() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {}

            @Override
            protected Void doInBackground(Void... unused) {
                while (true) {
                    if (!Utilities.isInternetAvailable())
                        break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {}
        }.execute();
    }
}
