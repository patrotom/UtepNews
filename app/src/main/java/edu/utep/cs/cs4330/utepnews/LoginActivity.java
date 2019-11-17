package edu.utep.cs.cs4330.utepnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CredentialsFragment credentialsFragment = (CredentialsFragment)
                getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        credentialsFragment.adjustLayout(0);
    }
}
