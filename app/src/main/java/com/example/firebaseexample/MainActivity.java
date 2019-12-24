package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    private EditText inPutID,inputName;
    private Button btnRead,btnSave;

    private TextView textViewID,textViewName;


    private DatabaseReference UserRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        inPutID=findViewById(R.id.inputID);
        inputName=findViewById(R.id.inputName);


        btnRead=findViewById(R.id.btnRead);
        btnSave=findViewById(R.id.btnSave);


        textViewID=findViewById(R.id.textViewID);
        textViewName=findViewById(R.id.textViewName);

        UserRef=FirebaseDatabase.getInstance().getReference().child("User");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=Integer.parseInt(inPutID.getText().toString());
                String name=inputName.getText().toString();
                
                Person person=new Person(id,name);
                UserRef.setValue(person).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Data is Added", Toast.LENGTH_SHORT).show();
                    }
                });
                
            }
        });


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Person person=dataSnapshot.getValue(Person.class);

                        textViewID.setText(""+person.getId());
                        textViewName.setText(person.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });








    }
}
