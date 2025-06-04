package com.yamatoapps.laundryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    TextView tvUsername,tvPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Please wait");
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);

        btnLogin.setOnClickListener(view ->{
            progressDialog.show();
            if (tvUsername.getText().toString().equals("admin")){
                //Admin
                //Proceed to main screen
                alertDialogBuilder.setMessage("Logged in as Admin.\nPress 'OK' to proceed");
                alertDialogBuilder.setTitle("Login Success");
                alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
                    startActivity(new Intent(MainActivity.this, Admin.class));

                    progressDialog.dismiss();
                });
                alertDialogBuilder.create().show();
            }
            else{
                //Login as player
                db.collection("laundry_shop_users").where(Filter.and(
                        Filter.equalTo("username",tvUsername.getText().toString()),
                        Filter.equalTo("password",tvPassword.getText().toString()),
                        Filter.equalTo("type","worker"))
                ).get().addOnSuccessListener(playerQueryDocumentSnapshots -> {
                    if (playerQueryDocumentSnapshots.size() > 0){
                        //Proceed to main screen
                        alertDialogBuilder.setMessage("Logged in as Worker.\nPress 'OK' to proceed");
                        alertDialogBuilder.setTitle("Login Success");
                        alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
                            startActivity(new Intent(MainActivity.this, NewLaundry.class));
                            progressDialog.dismiss();
                        });
                        alertDialogBuilder.create().show();
                    }
                    else{
                        alertDialogBuilder.setMessage("No account found. Please try again.");
                        alertDialogBuilder.setTitle("Login Failed");
                        alertDialogBuilder.setPositiveButton("Try again", (dialogInterface, i) -> {
                            progressDialog.dismiss();
                        });
                        alertDialogBuilder.create().show();
                    }
                });
            }
        });//btnLogin
        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(this,Register.class));
        });
    }

}