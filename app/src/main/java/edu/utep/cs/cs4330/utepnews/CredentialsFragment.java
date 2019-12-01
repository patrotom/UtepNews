package edu.utep.cs.cs4330.utepnews;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CredentialsFragment extends Fragment {
    private static final int LOGIN_LAYOUT = 0;
    private static final int REGISTER_LAYOUT = 1;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Button submitButton;
    private TextView actionTextView;
    private CheckBox rememberMeCheckBox;

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_credentials, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordAgainEditText = view.findViewById(R.id.passwordAgainEditText);
        submitButton = view.findViewById(R.id.submitButton);
        actionTextView = view.findViewById(R.id.actionTextView);
        rememberMeCheckBox = view.findViewById(R.id.rememberMeCheckBox);

        auth = FirebaseAuth.getInstance();

        return view;
    }

    public void adjustLayout(int type) {
        switch (type) {
            case LOGIN_LAYOUT:
                passwordAgainEditText.setVisibility(View.GONE);
                submitButton.setText(getString(R.string.login));
                actionTextView.setText(getString(R.string.register));
                submitButton.setOnClickListener(this::loginClicked);
                actionTextView.setOnClickListener((View view) -> {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                });
                break;
            case REGISTER_LAYOUT:
                rememberMeCheckBox.setVisibility(View.GONE);
                submitButton.setOnClickListener(this::registerClicked);
                actionTextView.setOnClickListener((View view) -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                });
                break;
            default:
                break;
        }
    }

    private void registerClicked(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        if (!isValidEmail(email)) {
            Toast.makeText(view.getContext(), "Invalid email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(view.getContext(), "Password can not be empty!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!TextUtils.equals(password, passwordAgain)) {
            Toast.makeText(view.getContext(), "Passwords do not match!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(view.getContext(), "Registration successful!",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(view.getContext(), "Registration failed!",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loginClicked(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!isValidEmail(email)) {
            Toast.makeText(view.getContext(), "Invalid email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(view.getContext(), "Password can not be empty!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(view.getContext(), "Login successful!",
                                Toast.LENGTH_LONG).show();

                        rememberMe();

                        startActivity(new Intent(getActivity(), HomePageActivity.class));
                    }
                    else {
                        Toast.makeText(view.getContext(), "Login failed!",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void rememberMe() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.remember_me_file_name), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("rememberMe", rememberMeCheckBox.isChecked());
        editor.putLong("timestamp", System.currentTimeMillis() / 1000L);

        editor.apply();
    }
}
