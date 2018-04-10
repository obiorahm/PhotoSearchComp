package com.example.mgo983.photosearchcomp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mgo983.photosearchcomp.data.Constants;
import com.example.mgo983.photosearchcomp.data.Friends;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by mgo983 on 3/19/18.
 */

public class AddFriendDialog extends DialogFragment {

    public static AddFriendDialog newInstance() {
        AddFriendDialog dialog = new AddFriendDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);

        dialog.setStyle(DialogFragment.STYLE_NO_FRAME, 0);

        return dialog;
    }

    DatabaseReference UserDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.PS_USER);
    String lastName = "";
    String firstName = "";
    String userName = "";

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_friend_dialog, container, false);

        Bundle bundle = this.getArguments();

        lastName = bundle.getString(Constants.LASTNAME);
        firstName = bundle.getString(Constants.FIRSTNAME);
        userName = bundle.getString(Constants.USERNAME);

        editText = rootView.findViewById(R.id.new_friend);

        searchAddFriend(rootView);

        return rootView;

    }

    public void searchAddFriend(View rootView){
        Button addButton = rootView.findViewById(R.id.add_add_friend);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //first search for user handle in user database
                //if (searchUser()) addFriend();
                String currUser = firstName + lastName;
                String friend =   editText.getText().toString();
                searchFriend(friend, currUser);
                //searchUser(firstName + lastName);
                //DatabaseReference friendDatabase = FirebaseDatabase.getInstance().getReference(Constants.PS_FRIEND).child("");

            }
        });
    }

    public void searchFriend(final String friendName, final String currUser){
        HashMap<String, Boolean> UserExists;
        DatabaseReference friendDatabase = FirebaseDatabase.getInstance().getReference(Constants.PS_FRIEND).child(currUser);
        friendDatabase.orderByChild(currUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //1. check that the user is not already a friend
                //2. check that the user has an account in the app database
                Log.d("User's friend", dataSnapshot.getValue().toString());
                // I think this was when I had data snapshots with out values
                if (dataSnapshot.getValue() == null){
                    searchUser(currUser);
                    return;
                }
                boolean friendExists = false;
                // don't replace existing friend

                Friends friends = dataSnapshot.getValue(Friends.class);
                Log.d("usernamex", friends.getUsername());
                if (friends.getUsername().equals(friendName)){
                    Toast.makeText(getActivity(), "This friend already exists.", Toast.LENGTH_LONG);
                    return;
                }
                    //search and add new user
                    searchUser(currUser);
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

    public  void searchUser( final String currUser){

        UserDatabaseReference.orderByChild(Constants.PS_USER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //check that user exists and user is not self and friend does not already exist
                String iFriendUser = dataSnapshot.getKey();
                final String friendUser = editText.getText().toString();
                Log.d("iFriendUser ", iFriendUser + "&" + friendUser);
               if (iFriendUser.equals(friendUser) && !iFriendUser.equals(currUser) ){
                   Toast.makeText(getActivity(), "added new user", Toast.LENGTH_LONG).show();
                DatabaseReference friendDatabase = FirebaseDatabase.getInstance().getReference(Constants.PS_FRIEND ).child(currUser);
                String Key = friendDatabase.push().getKey();
                friendDatabase.child(Key).child("username").setValue(friendUser);
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
