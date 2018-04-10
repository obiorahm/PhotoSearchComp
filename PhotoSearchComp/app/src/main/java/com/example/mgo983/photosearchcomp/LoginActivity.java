package com.example.mgo983.photosearchcomp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mgo983.photosearchcomp.data.Constants;
import com.example.mgo983.photosearchcomp.data.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mgo983 on 3/14/18.
 */

public class LoginActivity extends Activity{

    DatabaseReference mDatabaseReference;
    TextView textViewFirstName;
    TextView textViewLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        textViewFirstName = (TextView) findViewById(R.id.firstname);
        textViewLastName = (TextView) findViewById(R.id.surname);



        Button signUpBtn = (Button) findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(view);
            }
        });

        Button signInBtn = (Button) findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });
    }

    public void SignUp(View view){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.PS_USER).child("");

        String newFirstName = textViewFirstName.getText().toString();
        String newLastName = textViewLastName.getText().toString();
        User newUser = new User(newFirstName, newLastName);
        mDatabaseReference.child(newFirstName.toLowerCase()+ newLastName.toLowerCase()).setValue(newUser);

        textViewFirstName.setText("");
        textViewLastName.setText("");
    }

    public void signIn(View view){

        final String newFirstName = textViewFirstName.getText().toString().toLowerCase();
        final String newLastName = textViewLastName.getText().toString().toLowerCase();
        final Activity thisActivity = this;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.PS_USER);

        final String queryParam = newFirstName + newLastName;
        Log.d("isEmpty ", queryParam);

        mDatabaseReference.orderByChild(Constants.PS_USER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("isEmpty ", " " + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getKey().toString().equals(queryParam)){
                    Intent intent = new Intent(thisActivity, MainActivity.class);
                    intent.putExtra(Constants.USERNAME, newFirstName);
                    intent.putExtra(Constants.FIRSTNAME, newFirstName);
                    intent.putExtra(Constants.LASTNAME, newLastName);
                    startActivity(intent);
                    return;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
