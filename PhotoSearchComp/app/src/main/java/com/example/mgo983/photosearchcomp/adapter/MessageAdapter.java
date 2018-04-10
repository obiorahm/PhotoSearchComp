package com.example.mgo983.photosearchcomp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mgo983.photosearchcomp.R;
import com.example.mgo983.photosearchcomp.data.Message;

import java.util.ArrayList;


/**
 * Created by mgo983 on 4/5/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private Context mContext;

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView mssgTextView;
        TextView dateTextView;
        ImageView profileImageView;

        public MessageViewHolder(View view){
            super(view);
           mssgTextView = view.findViewById(R.id.message_item_text);
           dateTextView = view.findViewById(R.id.message_date);
           profileImageView = view.findViewById(R.id.profile_img);
        }

    }

    public MessageAdapter(Context context){
        mContext = context;
    }

    public void addItem(Message message){
        messageArrayList.add(message);
        notifyDataSetChanged();
    }


    public void setMessageArrayList(ArrayList<Message> mMessageArrayList){
        messageArrayList = mMessageArrayList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message currMessage = messageArrayList.get(position);
        holder.mssgTextView.setText(currMessage.getMssg());
        holder.dateTextView.setText(currMessage.getDate());
    }

    @Override

public   int getItemCount(){
        Log.d("Item count ", messageArrayList.size() + " ");
        return messageArrayList.size();
    }
}
