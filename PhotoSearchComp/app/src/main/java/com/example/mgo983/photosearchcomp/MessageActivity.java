package com.example.mgo983.photosearchcomp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mgo983.photosearchcomp.adapter.MessageAdapter;
import com.example.mgo983.photosearchcomp.data.Constants;
import com.example.mgo983.photosearchcomp.data.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * Created by mgo983 on 4/5/18.
 */

public class MessageActivity extends Activity {

    public MessageAdapter messageAdapter;
    public RecyclerView recyclerView;
    private String CLEAR_TEXT = "";

    @Override
    public void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_message);

        messageAdapter = new MessageAdapter(this);
        recyclerView = findViewById(R.id.message_recycler);

        Intent intent = this.getIntent();
        final String friendId = intent.getStringExtra(Constants.FRIEND_ID);
        final String senderId = intent.getStringExtra(Constants.USERNAME);
        final String recepientId = intent.getStringExtra(Constants.FRIEND_USERNAME);


        loadMessages(friendId);

        final EditText editText = findViewById(R.id.message_text);

        Button button = findViewById(R.id.send_message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.PS_MESSAGE);
                String message = editText.getText().toString();
                Calendar calendar = Calendar.getInstance();
                String date = calendar.getTime().toString();
                Message wholeMessage = new Message(friendId, senderId, recepientId,message, date);
                databaseReference.child(friendId).push().setValue(wholeMessage);
                editText.setText(CLEAR_TEXT);
            }
        });

        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    void loadMessages(String friendshipId){
        //final ArrayList<Message> messages = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.PS_MESSAGE).child(friendshipId);
        databaseReference.orderByChild(friendshipId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message currMessage = dataSnapshot.getValue(Message.class);
               // messages.add(currMessage);
                messageAdapter.addItem(currMessage);

                Log.d("datasnapshot ", dataSnapshot.getValue().toString());
                Log.d("datasnapshot ", currMessage.getMssg());

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
        //messageAdapter.setMessageArrayList(messages);
    }
}
