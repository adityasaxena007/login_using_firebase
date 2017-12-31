package com.example.adity.bid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class rfq extends AppCompatActivity {

    EditText obj, ir, discription, date;
    Spinner methods, loi;
    MultiAutoCompleteTextView countries;
    DatabaseReference db;
    Map<String, String> mp = new HashMap<>();
    ProgressDialog pd;
    Context mcontext;
    Activity mactivity;
    PopupWindow pw;
    int year, month, day;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfq);
        ActionBar a=getSupportActionBar();
        a.hide();
        countries = (MultiAutoCompleteTextView) findViewById(R.id.countries);
        String[] country = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, country);
        countries.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        countries.setAdapter(adapter);

        mcontext = getApplicationContext();


        obj = (EditText) findViewById(R.id.obj);
        ir = (EditText) findViewById(R.id.ir);
        discription = (EditText) findViewById(R.id.discription);
        date = (EditText) findViewById(R.id.txtdate);
        methods = (Spinner) findViewById(R.id.method);
        loi = (Spinner) findViewById(R.id.loi);


        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showDialog(0);

            }
        });


        ir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !(ir.getText().toString().contains("%"))&&!(ir.getText().toString().equals(""))) {
                    ir.setText(ir.getText().toString().trim() + " %".trim());
                }

                else if (hasFocus) {
                    ir.setText(ir.getText().toString().replace("%", ""));
                }


            }
        });

        pd = new ProgressDialog(this);


        db = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Button submit = (Button) findViewById(R.id.sub);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);
                pd.setMessage("Submitting ");
                pd.show();
                String objective = obj.getText().toString();
                String IR = ir.getText().toString();
                String disp = discription.getText().toString();
                String dat = date.getText().toString();
                String method = methods.getSelectedItem().toString();
                String length = loi.getSelectedItem().toString();
                String contry = countries.getText().toString();

                mp.put("Email", FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                mp.put("objective", objective);
                mp.put("Method", method);
                mp.put("IR", IR);
                mp.put("LOI", length);
                mp.put("country", contry);
                mp.put("discription", disp);
                mp.put("date", dat);

                db.child("RFQ").push().setValue(mp, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(rfq.this, "submitted", Toast.LENGTH_SHORT).show();
                            pd.hide();
                            finish();
                            startActivity(new Intent(rfq.this, act.class));


                        } else {
                            Toast.makeText(rfq.this, "" + databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            date.setText(selectedDay + " - " + (selectedMonth + 1) + " - " +selectedYear );
        }
    };

}
