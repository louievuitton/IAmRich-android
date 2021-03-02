package com.stevenlouie.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IAmRichActivity extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView diamondImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iam_rich);

        nameTextView = findViewById(R.id.nameTextView);
        diamondImageView = findViewById(R.id.diamondImageView);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            nameTextView.setText(name + " is.");
        }

        diamondImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create a dialog here
                AlertDialog.Builder dialog = new AlertDialog.Builder(IAmRichActivity.this);
                dialog.setMessage("I am rich" + "\nI deserv it" + "\nI am good," + "\nhealthy &" + "\nsuccessful");
                dialog.setPositiveButton("Got it bby", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setCancelable(true);
                dialog.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutBtn:
                Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IAmRichActivity.this, MainActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

        @Override
    public void onBackPressed() {
    }
}
