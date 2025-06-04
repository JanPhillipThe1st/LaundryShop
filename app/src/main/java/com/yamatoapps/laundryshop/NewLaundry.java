package com.yamatoapps.laundryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewLaundry extends AppCompatActivity {

    TextInputEditText tvDate, tvName, tvContact, tvRegularKilos,
            tvNonRegularKilos, tvAmountRegular, tvAmountNonRegular;
    TextView tvKilosTotalRegular, tvAmountTotalRegular, tvKilosTotalNonRegular,
            tvAmountTotalNonRegular, tvGrandTotal;
    Button btnPayBill;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Double regularAmount = 0.0, nonRegularAmount= 0.0, grandTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_laundry);

        tvDate = findViewById(R.id.tvDate);
        tvName = findViewById(R.id.tvName);
        tvContact = findViewById(R.id.tvContact);
        tvRegularKilos = findViewById(R.id.tvRegularKilos);
        tvNonRegularKilos = findViewById(R.id.tvNonRegularKilos);
        tvAmountRegular = findViewById(R.id.tvAmountRegular);
        tvAmountNonRegular = findViewById(R.id.tvAmountNonRegular);

        tvKilosTotalRegular = findViewById(R.id.tvKilosTotalRegular);
        tvAmountTotalRegular = findViewById(R.id.tvAmountTotalRegular);
        tvKilosTotalNonRegular = findViewById(R.id.tvKilosTotalNonRegular);
        tvAmountTotalNonRegular = findViewById(R.id.tvAmountTotalNonRegular);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        Map<String,Object> laundry_data = new HashMap<String,Object>();

        btnPayBill = findViewById(R.id.btnPayBill);

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(NewLaundry.this);
        btnPayBill.setOnClickListener(view -> {
            alertDialogBuilder.setTitle("Payment");
            alertDialogBuilder.setMessage("Are you sure you want to proceed?");
            alertDialogBuilder.setNegativeButton("NO",(dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setPositiveButton("YES",(dialogInterface, i) -> {
                ProgressDialog progressDialog  = new ProgressDialog(NewLaundry.this);
                progressDialog.setTitle("Saving");
                progressDialog.setMessage("Saving your data. Please wait.");
                progressDialog.show();
                laundry_data.put("grand_total",grandTotal);
                laundry_data.put("regular_kg",Double.parseDouble(tvRegularKilos.getText().toString()));
                laundry_data.put("nonregular_kg",Double.parseDouble(tvNonRegularKilos.getText().toString()));
                laundry_data.put("regular_sub_total",regularAmount);
                laundry_data.put("nonregular_sub_total",nonRegularAmount);
                laundry_data.put("date_paid", Calendar.getInstance().getTime());
                laundry_data.put("customer_name", tvName.getText().toString());
                laundry_data.put("customer_contact", tvContact.getText().toString());

                MaterialAlertDialogBuilder signupAlertDialogBuilder = new MaterialAlertDialogBuilder(NewLaundry.this);
                signupAlertDialogBuilder.setTitle("Payment Success");
                signupAlertDialogBuilder.setMessage("Your transaction has been successfully saved!");
                signupAlertDialogBuilder.setPositiveButton("OK", (signupDialogInterface,o) ->{
                    signupDialogInterface.dismiss();
                    finish();
                });
                db.collection("laundry_shop_transactions").add(laundry_data).addOnSuccessListener(documentReference -> {
                    signupAlertDialogBuilder.create().show();
                });

            });
            alertDialogBuilder.create().show();
        });

        tvDate.setText( Calendar.getInstance().getTime().toString());

        tvRegularKilos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                 regularAmount = 0.0;

                try {
                    regularAmount = 60 * Double.parseDouble(tvRegularKilos.getText().toString());
                } catch (NumberFormatException e) {
                }
                tvAmountRegular.setText("PHP "+regularAmount.toString());
                tvAmountTotalRegular.setText("PHP "+regularAmount.toString());
                tvKilosTotalRegular.setText(tvRegularKilos.getText().toString());
                grandTotal = regularAmount + nonRegularAmount;
                tvGrandTotal.setText(grandTotal.toString());
            }
        });

        tvNonRegularKilos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                 nonRegularAmount = 0.0;
                 try {
                    nonRegularAmount = 80 * Double.parseDouble(tvNonRegularKilos.getText().toString());
                } catch (NumberFormatException e) {
                }
                tvAmountTotalNonRegular.setText("PHP "+nonRegularAmount.toString());
                tvAmountNonRegular.setText("PHP "+nonRegularAmount.toString());
                tvKilosTotalNonRegular.setText(tvNonRegularKilos.getText().toString());
                grandTotal = regularAmount + nonRegularAmount;
                tvGrandTotal.setText(grandTotal.toString());
            }
        });
    }
}