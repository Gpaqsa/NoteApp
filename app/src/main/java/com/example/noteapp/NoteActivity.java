package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView list_view;
    private EditText add_items;
    private Button addbtn;
    private Button logout;


    private  String userId;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
 //   private ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_note);

            mDatabase= FirebaseDatabase.getInstance().getReference();

            add_items = (EditText) findViewById(R.id.add_items);
            addbtn = (Button) findViewById(R.id.addbtn);
            list_view = (ListView) findViewById(R.id.list_view);
            logout = (Button) findViewById(R.id.singOut) ;

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrayList);


             if(getSupportActionBar() != null) {
                 getSupportActionBar().hide();
             }


             logout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     FirebaseAuth.getInstance().signOut();
                     startActivity(new Intent( NoteActivity.this, MainActivity.class));
                 }
             });

             list_view.setAdapter(adapter);
                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.push().setValue(add_items.getText().toString());

                    }
                });


                    
                    mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                
                String string = snapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String string = snapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }





}





