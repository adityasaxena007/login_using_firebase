package com.example.adity.bid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {
    TextView name,email,num;
    ImageView iv;
    FirebaseAuth auth;
    ListView lv;
    String[] items={"Veryfy Email","Change Password","Change Email Address","Logout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar a=getSupportActionBar();
        a.hide();

        name=(TextView)findViewById(R.id.name1);
        email=(TextView)findViewById(R.id.email1);
        num=(TextView)findViewById(R.id.number1);
        iv=(ImageView)findViewById(R.id.pic);

        char let= FirebaseAuth.getInstance().getCurrentUser().getDisplayName().trim().charAt(0);
        String letter=String.valueOf(let);



        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        TextDrawable drawable1=TextDrawable.builder().buildRound(letter.toUpperCase(), color1);
        iv.setImageDrawable(drawable1);





        DatabaseReference db= FirebaseDatabase.getInstance().getReference();

         auth =FirebaseAuth.getInstance();
        final FirebaseUser user=auth.getCurrentUser();


        name.setText(""+user.getDisplayName());

        email.setText(""+user.getEmail());

        num.setText(""+user.getEmail());

        lv=(ListView)findViewById(R.id.lv);

        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
        {
            items[0]="Email Verified";
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    final FirebaseUser user=auth.getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(profile.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(profile.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                Log.e("", "email sent");


                            } else {
                                Toast.makeText(profile.this,
                                        "Failed to send verification email." + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    lv.getChildAt(0).setEnabled(false);

                }
                else if(position==1)
                {
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    auth.sendPasswordResetEmail(user.getEmail())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(profile.this, "Email Sent", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                        Toast.makeText(profile.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else if(position==3)
                {
                    auth.signOut();
                    startActivity(new Intent(profile.this,login_or_signup.class));
                }

            }
        });










    }
}
