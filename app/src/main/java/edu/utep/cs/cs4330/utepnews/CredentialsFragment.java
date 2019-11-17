package edu.utep.cs.cs4330.utepnews;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CredentialsFragment extends Fragment {
    private static final int LOGIN_LAYOUT = 0;
    private static final int REGISTER_LAYOUT = 1;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView aboutTextView;
    private TextView contactTextView;
    private TextView actionTextView;

    public CredentialsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getView();

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordAgainEditText = view.findViewById(R.id.passwordAgainEditText);
        submitButton = view.findViewById(R.id.submitButton);
        progressBar = view.findViewById(R.id.progressBar);
        aboutTextView = view.findViewById(R.id.aboutTextView);
        contactTextView = view.findViewById(R.id.contactTextView);
        actionTextView = view.findViewById(R.id.actionTextView);

        return inflater.inflate(R.layout.fragment_credentials, container, false);
    }

    public void adjustLayout(int type) {
        switch (type) {
            case LOGIN_LAYOUT:
                passwordAgainEditText.setVisibility(View.GONE);
                submitButton.setText(getString(R.string.login));
                actionTextView.setText(getString(R.string.register));
                submitButton.setOnClickListener(this::loginClicked);
                break;
            case REGISTER_LAYOUT:
                submitButton.setOnClickListener(this::registerClicked);
                break;
            default:
                break;
        }
    }

    private void registerClicked(View view) {}

    private void loginClicked(View view) {}
}
