package com.example.adity.bid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by adity on 6/22/2017.
 */

public class Sign_in_activity extends Activity {

    EditText email,pass;
    Button signin;
    FirebaseAuth mAuth;
    ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        signin=(Button)findViewById(R.id.signin);
        pd=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String em=email.getText().toString().trim();
                String pas=pass.getText().toString().trim();

                if((em.equals("")) || !(em.contains("@")) || !(em.contains(".")))
                {   email.setText("");
                    email.setHint("Enter  valid Email");}

               else if(pas.equals(""))
                {   pass.setText("");
                    pass.setHint("please Enter a valid Password");
                }

                else
                {
                    pd.setMessage("Logging In");
                    pd.show();
                    mAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener(Sign_in_activity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             pd.hide();
                             Intent i=new Intent(Sign_in_activity.this,list.class);
                             i.putExtra("email",em);
                             startActivity(i);
                         }

                         else {
                             Toast.makeText(Sign_in_activity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }


                        }
                    });


                }







            }
        });



    }
}
