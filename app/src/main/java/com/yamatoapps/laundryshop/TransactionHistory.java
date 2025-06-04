package com.yamatoapps.laundryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class TransactionHistory extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ListView lvTransactions = findViewById(R.id.lvTransactions);
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        TransactionAdapter adapter = new TransactionAdapter(this, transactions);

        db.collection("laundry_shop_transactions").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot transactionDocument : queryDocumentSnapshots){
                adapter.add(new Transaction(transactionDocument.getString("customer_contact"),
                        transactionDocument.getString("customer_name"),
                        transactionDocument.getDate("date_paid", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE),
                        transactionDocument.getDouble("grand_total"),
                        transactionDocument.getDouble("nonregular_kg"),
                        transactionDocument.getDouble("nonregular_sub_total"),
                        transactionDocument.getDouble("regular_kg"),
                        transactionDocument.getDouble("regular_sub_total"),
                        transactionDocument.getId()
                        )
                );
            }

            lvTransactions.setAdapter(adapter);
        });
    }
}