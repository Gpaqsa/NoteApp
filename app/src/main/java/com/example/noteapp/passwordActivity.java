package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordActivity extends AppCompatActivity {

    private EditText emai_address;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emai_address = (EditText)findViewById(R.id.EmailAddress);
        resetpassword = (Button)findViewById(R.id.resetpassbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Useremail = emai_address.getText().toString().trim();

                if ( Useremail.equals("") ){
                    Toast.makeText(passwordActivity.this, "Please enter you registered email", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(Useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if ( task.isSuccessful() ){
                                Toast.makeText(passwordActivity.this, "password reset email sent ! ", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(passwordActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(passwordActivity.this, "Erorr in sending password reset email ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}