package com.yamatoapps.laundryshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.ZoneOffset;
import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TransactionAdapter(@NonNull Context context, @NonNull ArrayList<Transaction> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_card, parent, false);
        }
        TextView tvCustomerName, tvCustomerContact,tvDatePaid, tvGrandTotal;
        Button btnDelete, btnEdit;
        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnDelete = convertView.findViewById(R.id.btnDelete);
        tvCustomerName = convertView.findViewById(R.id.tvCustomerName);
        tvCustomerContact = convertView.findViewById(R.id.tvCustomerContact);
        tvDatePaid = convertView.findViewById(R.id.tvDatePaid);
        tvGrandTotal = convertView.findViewById(R.id.tvGrandTotal);
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());

        btnDelete.setOnClickListener(view -> {
            alertDialogBuilder.setTitle("Deleting data");
            alertDialogBuilder.setMessage("Are you sure you want to delete this data?");
            alertDialogBuilder.setNegativeButton("NO",(dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            alertDialogBuilder.setPositiveButton("YES",(dialogInterface, i) -> {
            MaterialAlertDialogBuilder signupAlertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            signupAlertDialogBuilder.setTitle("Deletion Success");
            signupAlertDialogBuilder.setMessage("The transaction has been successfully deleted!");
            signupAlertDialogBuilder.setPositiveButton("OK", (signupDialogInterface,o) ->{
                signupDialogInterface.dismiss();
                ((Activity)parent.getContext()).finish();
            });
                db.collection("laundry_shop_transactions").document(item.id).delete().addOnSuccessListener(documentReference -> {
                    signupAlertDialogBuilder.create().show();
                });

            });
            alertDialogBuilder.create().show();
            });

        btnEdit.setOnClickListener(view -> {
            Intent intent =        new Intent(parent.getContext(),EditLaundry.class);
            intent.putExtra("document_id",item.id);
            parent.getContext().startActivity(intent);
        });

        tvCustomerName.setText(item.customer_name);
        tvCustomerContact.setText(item.customer_contact);
        tvDatePaid.setText(item.date_paid.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate().toString());
        tvGrandTotal.setText("PHP "+item.grand_total.toString());

        return convertView;
    }
}
