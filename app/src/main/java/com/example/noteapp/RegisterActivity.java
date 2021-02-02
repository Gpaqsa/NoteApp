package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.*;
import static android.widget.Toast.makeText;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title, RegisterUser;
    private EditText fullname, age, email, password;
  //  private Button registeruser;



    private FirebaseAuth mAuth;
    private Object User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        title = (TextView) findViewById(R.id.titleid);
        title.setOnClickListener(this);
        RegisterUser = (Button) findViewById(R.id.RegisteUser);

        fullname = (EditText) findViewById(R.id.fullnameid);
        age = (EditText) findViewById(R.id.ageid);
        email = (EditText) findViewById(R.id.textemailid2);
        password = (EditText)findViewById(R.id.passwordid);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View v) {
        register_user();
        }




    private void register_user() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Fullname = fullname.getText().toString().trim();
        String Age = age.getText().toString().trim();

        if ( Fullname.isEmpty() ) {
            fullname.setError("Full name is requried");
            fullname.requestFocus();
            return;
        }
        if ( Age.isEmpty() ) {
            age.setError("Age is requried");
            age.requestFocus();
            return;
        }
        if ( Password.isEmpty() ) {
            password.setError("Password is requried");
            password.requestFocus();
            return;
        }
        if ( Email.isEmpty() ) {
            email.setError("Email is requried");
            email.requestFocus();
            return;
        }
        if ( !Patterns.EMAIL_ADDRESS.matcher(Email).matches() ) {
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }
        if ( password.length() < 6 ) {
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            User user = new User(Fullname, Age, Email);
                            Intent intent = new Intent(RegisterActivity.this,NoteActivity.class);
                            startActivity(intent);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if ( task.isSuccessful() ) {
                                        Toast.makeText(RegisterActivity.this, "User has been sucessfully", LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Sucessful to register!", LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
