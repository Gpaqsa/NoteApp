 package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class MainActivity extends AppCompatActivity  {

     private TextView register;
     private EditText editTextemail, editTextpassword;
     private Button singIn;
     private FirebaseAuth mAuth;
    private TextView forgotpassword;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);


         register = (TextView) findViewById(R.id.registerid);


         editTextemail = (EditText) findViewById(R.id.textemailid);
         editTextpassword = (EditText) findViewById(R.id.passwordid);
         singIn = (Button) findViewById(R.id.signin);
        forgotpassword = (TextView) findViewById(R.id.forgotpasswordid);

         mAuth = FirebaseAuth.getInstance();

         if(getSupportActionBar() != null) {
             getSupportActionBar().hide();
         }

         forgotpassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, passwordActivity.class));
             }
         });

     }

     public  void  onregistrer(View view){
         Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
         startActivity(intent2);
     }

     public void loginbtn(View v) {
         Intent intent = new Intent(MainActivity.this, NoteActivity.class);
         startActivity(intent);
         userLogin();
         }


     private void userLogin() {
         String email = editTextemail.getText().toString().trim();
         String password = editTextpassword.getText().toString().trim();

         if (email.isEmpty()){
             editTextemail.setError("Email is required");
             editTextemail.requestFocus();
             return;
         }
         if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             editTextemail.setError("please enter a valid email!");
             editTextemail.requestFocus();
             return;
         }
         if ( password.isEmpty() ){
             editTextpassword.setError("password is required");
             editTextpassword.requestFocus();
             return;
         }
         if ( password.length()< 6){
             editTextpassword.setError("Min password length should be 6 characters!");
             editTextpassword.requestFocus();
             return;
         }


         mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if ( task.isSuccessful() ){
                     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                     startActivity(new Intent(MainActivity.this, NoteActivity.class));
                     if(user.isEmailVerified()){
                         Toast.makeText(MainActivity.this, "Emails is verified!", Toast.LENGTH_SHORT).show();


                     }else{
                         user.sendEmailVerification();
                         Toast.makeText(MainActivity.this, "Check your email to veryfiy account",Toast.LENGTH_SHORT).show();
                     }

                 }else {
                     Toast.makeText(MainActivity.this,"Failed to login! please check you email or password!",Toast.LENGTH_SHORT).show();
                 }
             }
         });

     }
 }