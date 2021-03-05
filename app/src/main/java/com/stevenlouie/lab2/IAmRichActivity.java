package com.stevenlouie.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IAmRichActivity extends AppCompatActivity {

    private TextView richTextView1, richTextView2, richTextView3, textView1, textView2;
    private Button boostBtn;
    private ImageView diamondImageView;
    private String email;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iam_rich);

        initViews();

        // retrieve data passed using Intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            boolean maxRich = intent.getBooleanExtra("maxRich", false);
            if (maxRich) {
                textView1.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                boostBtn.setVisibility(View.INVISIBLE);
                richTextView1.setVisibility(View.VISIBLE);
                richTextView2.setVisibility(View.VISIBLE);
                richTextView3.setVisibility(View.VISIBLE);
                richTextView3.setText(name + " is.");
            }
            else {
                email = intent.getStringExtra("email");
                richTextView1.setVisibility(View.INVISIBLE);
                richTextView2.setVisibility(View.INVISIBLE);
                richTextView3.setVisibility(View.INVISIBLE);
                richTextView3.setText(name + " is.");
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                boostBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    // initializes all UI elements on the page and handles the onclicklisteners of all views
    private void initViews() {
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        boostBtn = findViewById(R.id.boostBtn);
        richTextView1 = findViewById(R.id.richTextView1);
        richTextView2 = findViewById(R.id.richTextView2);
        richTextView3 = findViewById(R.id.richTextView3);
        diamondImageView = findViewById(R.id.diamondImageView);

        // change the size of the diamond when user first visits the page
        db = new Database(IAmRichActivity.this);
        int diamondSize = db.getDiamondSize(getIntent().getStringExtra("email"));
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) diamondImageView.getLayoutParams();
        params.width = dpToPx(diamondSize, IAmRichActivity.this);
        params.height = dpToPx(diamondSize, IAmRichActivity.this);
        diamondImageView.setLayoutParams(params);

        // show a dialog message whenever user clicks on the diamond
        diamondImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(IAmRichActivity.this);
                dialog.setMessage("I am rich" + "\nI deserv it" + "\nI am good," + "\nhealthy &" + "\nsuccessful");
                dialog.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setCancelable(true);
                dialog.create().show();
            }
        });

        // used to update the size of the diamond inside the SQLite database and reflect that update by changing size of the diamond image
        boostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IAmRichActivity.this, "Grind don't stop", Toast.LENGTH_SHORT).show();
                String[] response = db.checkIfElonRich(email);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) diamondImageView.getLayoutParams();
                params.width =  dpToPx((Integer.valueOf(response[1]) + 50), IAmRichActivity.this);
                params.height = dpToPx((Integer.valueOf(response[1]) + 50), IAmRichActivity.this);
                diamondImageView.setLayoutParams(params);
                if (response[1].equals("250")) {
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    boostBtn.setVisibility(View.INVISIBLE);
                    richTextView1.setVisibility(View.VISIBLE);
                    richTextView2.setVisibility(View.VISIBLE);
                    richTextView3.setVisibility(View.VISIBLE);
                }
                db.updateDiamondSize(email, (Integer.valueOf(response[1]) + 50));
            }
        });
    }

    // function used to convert dp to px in order to properly change size of diamond
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // used to handle what happens when user clicks on the logout button on the ActionBar
    // user is navigated back to the HomeFragment page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutBtn:
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IAmRichActivity.this, MainActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }
}
