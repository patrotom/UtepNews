package edu.utep.cs.cs4330.utepnews;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        startService(new Intent(this, ConnectionCheckService.class));

        if (remembered())
            startActivity(new Intent(this, HomePageActivity.class));

        registerButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean remembered() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.remember_me_file_name), Context.MODE_PRIVATE);

        long timestamp = sharedPref.getLong("timestamp", 0);
        long currentTimestamp = System.currentTimeMillis() / 1000L;

        if ((currentTimestamp - timestamp) > 2592000) {
            return false;
        }

        return sharedPref.getBoolean("rememberMe", false);
    }
}
