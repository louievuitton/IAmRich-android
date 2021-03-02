package com.stevenlouie.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private Button signupBtn;
    private TextView loginBtn, firstNameWarning, lastNameWarning, emailWarning, passwordWarning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginBtn = view.findViewById(R.id.loginBtn);
        signupBtn = view.findViewById(R.id.signupBtn);
        firstNameWarning = view.findViewById(R.id.firstNameWarning);
        lastNameWarning = view.findViewById(R.id.lastNameWarning);
        emailWarning = view.findViewById(R.id.emailWarning);
        passwordWarning = view.findViewById(R.id.passwordWarning);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    firstNameWarning.setVisibility(View.INVISIBLE);
                    lastNameWarning.setVisibility(View.INVISIBLE);
                    emailWarning.setVisibility(View.INVISIBLE);
                    passwordWarning.setVisibility(View.INVISIBLE);

                    User user = new User(-1, firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
                    Database db = new Database(getActivity());
                    boolean success = db.signup(user);
                    if (success) {
                        Toast.makeText(getActivity(), "Successfully signed up.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), IAmRichActivity.class);
                        intent.putExtra("name", firstNameEditText.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "User is already registered, please log in.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });

        return view;
    }

    private boolean validateData() {
        if (firstNameEditText.getText().toString().equals("")) {
            firstNameWarning.setVisibility(View.VISIBLE);
            return false;
        }
        if (lastNameEditText.getText().toString().equals("")) {
            lastNameWarning.setVisibility(View.VISIBLE);
            return false;
        }
        if (emailEditText.getText().toString().equals("")) {
            emailWarning.setVisibility(View.VISIBLE);
            return false;
        }
        if (passwordEditText.getText().toString().equals("")) {
            passwordWarning.setVisibility(View.VISIBLE);
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
//        Pattern pattern = Pattern.compile(emailRegex);
        if (!Pattern.compile(emailRegex).matcher(emailEditText.getText().toString()).matches()) {
            // enter valid email
            emailWarning.setVisibility(View.VISIBLE);
            return false;
        }

        String passwordRegex = "^[a-zA-Z0-9]$";
        if (!Pattern.compile(passwordRegex).matcher(passwordEditText.getText().toString()).matches()) {
            // enter valid password
            passwordWarning.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }
}
