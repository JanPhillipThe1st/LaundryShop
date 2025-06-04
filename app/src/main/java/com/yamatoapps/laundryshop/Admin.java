package com.yamatoapps.laundryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    Button btnTransactionHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnTransactionHistory = findViewById(R.id.btnTransactionHistory);

        btnTransactionHistory.setOnClickListener(view -> {
            startActivity(new Intent(Admin.this,TransactionHistory.class));
        });
    }
}