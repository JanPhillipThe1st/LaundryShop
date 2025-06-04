package com.yamatoapps.laundryshop;

import java.util.Date;

public class Transaction {
    public String customer_contact = "";
    public String customer_name = "";
    public String id = "";
    public Date date_paid = new Date();
    public Double grand_total = 0.0;
    public Double nonregular_kg = 0.0;
    public Double nonregular_sub_total = 0.0;
    public Double regular_kg = 0.0;
    public Double regular_sub_total = 0.0;

    public Transaction(String customer_contact, String customer_name, Date date_paid,
                       Double grand_total, Double nonregular_kg,
                       Double nonregular_sub_total, Double regular_kg, Double regular_sub_total,
                       String id ) {
        this.customer_contact = customer_contact;
        this.customer_name = customer_name;
        this.date_paid = date_paid;
        this.grand_total = grand_total;
        this.nonregular_kg = nonregular_kg;
        this.nonregular_sub_total = nonregular_sub_total;
        this.regular_kg = regular_kg;
        this.regular_sub_total = regular_sub_total;
        this.id = id;
    }
}
