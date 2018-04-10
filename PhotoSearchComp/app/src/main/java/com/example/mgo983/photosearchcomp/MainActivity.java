package com.example.mgo983.photosearchcomp;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mgo983.photosearchcomp.adapter.FriendAdapter;
import com.example.mgo983.photosearchcomp.data.Constants;
import com.example.mgo983.photosearchcomp.data.Friends;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {


    private FriendAdapter friendAdapter;
    private ListView friendListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        String username = intent.getStringExtra(Constants.USERNAME);
        String firstName = intent.getStringExtra(Constants.FIRSTNAME);
        String lastName = intent.getStringExtra(Constants.LASTNAME);

        TextView textViewUsername = (TextView) findViewById(R.id.username);

        String capUsername = username.substring(0,1).toUpperCase() + username.substring(1);
        textViewUsername.setText(capUsername);

        friendAdapter = new FriendAdapter(this, R.layout.friend_item);
        friendListView = (ListView) findViewById(R.id.friend_list);



        populateFriendList(firstName, lastName);

        //friendListView.setAdapter(friendAdapter);

        addFriend(username, firstName, lastName);
    }

    public void populateFriendList(String firstName, String lastName){
        DatabaseReference friendDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.PS_FRIEND);

        final ArrayList<Friends> friends = new ArrayList<>();

        final HashMap<String, Friends> hashFriends = new HashMap<>();

        final DatabaseReference databaseReference = friendDatabaseReference.child(firstName + lastName);

        databaseReference.orderByChild(firstName + lastName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Friends currFriend = dataSnapshot.getValue(Friends.class);
                currFriend.setId(dataSnapshot.getKey());
                String username = currFriend.getUsername();
                if (hashFriends.get(username) == null)
                    friends.add(currFriend);
                Log.d("friend id", dataSnapshot.getKey());


               /* for (DataSnapshot child : dataSnapshot.getChildren()){
                    Friends currFriend = new Friends();
                    //Friends iFriend = child.getValue(Friends.class);
                    String username = child.getValue().toString();
                    currFriend.setUsername(username);
                    // check that friends are unique
                    //this check should already have been done when adding friends
                    if (hashFriends.get(username) == null)
                        friends.add(currFriend);
                    hashFriends.put(username, currFriend);
                    Log.d("Datasnapshot key ", "" + child.getKey());
                    Log.d("Datasnapshot value", "" + child.getValue());

                    Log.d("Datasnapshot ", "" + child);
                    //Log.d("Friend id ", iFriend.getId());
                }*/

                friendAdapter.setFriendsArrayList(friends);
                friendListView.setAdapter(friendAdapter);

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

    public  void addFriend(final String userName, final String firstName, final String lastName){
        ImageButton addFriendImageButton = (ImageButton) this.findViewById(R.id.add_friend);
        addFriendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFriendDialog newFragment = AddFriendDialog.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.USERNAME, userName);
                bundle.putString(Constants.FIRSTNAME, firstName);
                bundle.putString(Constants.LASTNAME, lastName);


                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(),"ADD NEW FRIEND");            }
        });

    }
}
