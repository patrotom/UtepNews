package edu.utep.cs.cs4330.utepnews;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CredentialsFragment credentialsFragment = (CredentialsFragment)
                getSupportFragmentManager().findFragmentById(R.id.credentialsFragment);
        credentialsFragment.adjustLayout(0);
    }
}
