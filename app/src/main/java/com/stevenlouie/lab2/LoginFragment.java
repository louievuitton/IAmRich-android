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

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private TextView signupBtn, emailWarning, passwordWarning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        signupBtn = view.findViewById(R.id.signupBtn);
        loginBtn = view.findViewById(R.id.loginBtn);
        emailWarning = view.findViewById(R.id.emailWarning);
        passwordWarning = view.findViewById(R.id.passwordWarning);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    emailWarning.setVisibility(View.INVISIBLE);
                    passwordWarning.setVisibility(View.INVISIBLE);

                    Database db = new Database(getActivity());
                    String success = db.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
                    if (!success.equals("")) {
                        Toast.makeText(getActivity(), "Successfully logged in.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), IAmRichActivity.class);
                        intent.putExtra("name", success);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "User is not registered, please sign up.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(2);
            }
        });


        return view;
    }

    private boolean validateData() {
        if (emailEditText.getText().toString().equals("")) {
            emailWarning.setVisibility(View.VISIBLE);
            return false;
        }
        if (passwordEditText.getText().toString().equals("")) {
            passwordWarning.setVisibility(View.VISIBLE);
            return false;
        }

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(emailEditText.getText().toString()).matches()) {
            // enter valid email
            emailWarning.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }
}
