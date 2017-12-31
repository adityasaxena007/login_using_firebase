package com.example.adity.bid;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class list extends AppCompatActivity {
    TextView name,email,num;
    ImageView iv;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
       // Bundle b=getIntent().getExtras();
        ActionBar a=getSupportActionBar();
        a.hide();

        name=(TextView)findViewById(R.id.username);
        email=(TextView)findViewById(R.id.useremail);
        iv=(ImageView)findViewById(R.id.profilepic);

        char let= FirebaseAuth.getInstance().getCurrentUser().getDisplayName().trim().charAt(0);
        String letter=String.valueOf(let);
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        TextDrawable drawable1=TextDrawable.builder().buildRound(letter.toUpperCase(), color1);
        iv.setImageDrawable(drawable1);


        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


        name.setText("Welcome, "+user.getDisplayName().toUpperCase());

        email.setText(""+user.getEmail());




        mAuth=FirebaseAuth.getInstance();





        Button rfq=(Button)findViewById(R.id.RFQ);
        rfq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(list.this,rfq.class);
                startActivity(i);
            }
        });



        Button prof=(Button)findViewById(R.id.Profile);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(list.this, profile.class));
            }
        });

        Button History=(Button)findViewById(R.id.history);
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(list.this,History.class));
            }
        });




    }
}
