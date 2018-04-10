package com.example.mgo983.photosearchcomp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mgo983.photosearchcomp.MessageActivity;
import com.example.mgo983.photosearchcomp.R;
import com.example.mgo983.photosearchcomp.data.Constants;
import com.example.mgo983.photosearchcomp.data.Friends;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mgo983 on 3/16/18.
 */

public class FriendAdapter extends ArrayAdapter{

    private ArrayList<Friends> friendsArrayList;
    private HashMap<String, Friends> friendsHashMap;
    private LayoutInflater inflater;
    private Context mContext;

    public FriendAdapter(Context context, int resources){
        super(context, resources);
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    public static class FriendViewHolder {
        TextView nameTextView;
        TextView lastMessageTextView;
        ImageView friendImageView;

        public FriendViewHolder(View v){
            nameTextView = (TextView) v.findViewById(R.id.friend_name);
            lastMessageTextView = (TextView) v.findViewById(R.id.friend_last_mssg);
            friendImageView = (ImageView) v.findViewById(R.id.friend_img);
        }
    }

    public void setFriendsArrayList(ArrayList<Friends> mFriendsArrayList){
        friendsArrayList = mFriendsArrayList;
    }



    @Override
    public int getCount(){
        return friendsArrayList.size();
    }


    @Override
    public View getView(int position, View view, final ViewGroup parent){
        if (view == null){
            view =  inflater.inflate(R.layout.friend_item, null);

        }

        FriendViewHolder friendViewHolder = new FriendViewHolder(view);
        final Friends currFriend = friendsArrayList.get(position);
        final String friendName = currFriend.getUsername();
        Log.d("Friendship id ", currFriend.getId());
        String capFriendName = friendName.substring(0,1).toUpperCase() + friendName.substring(1);
        friendViewHolder.nameTextView.setText(friendsArrayList.get(position).getUsername());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra(Constants.FRIEND_ID, currFriend.getId());
                intent.putExtra(Constants.FRIEND_USERNAME, friendName);


                //getIntent from adapter context
                Intent activityIntent = ((Activity) mContext).getIntent();
                String username = activityIntent.getStringExtra(Constants.USERNAME);

                intent.putExtra(Constants.USERNAME, username);
                mContext.startActivity(intent);

            }
        });

        view.setTag(friendViewHolder);
        return view;
    }

}
