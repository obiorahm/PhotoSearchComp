package com.example.mgo983.photosearchcomp.data;

/**
 * Created by mgo983 on 3/15/18.
 */

public class Message {
    private String id;
    private String sender_id;
    private String recipient_id;
    private String mssg;
    private String date;

    public Message(String mId, String mSenderId, String mRecipientId, String mMssg, String mDate){
        id = mId;
        sender_id = mSenderId;
        recipient_id = mRecipientId;
        mssg = mMssg;
        date = mDate;
    }

    public Message(){}

    public void setId(String mId){
        id = mId;
    }

    public void setSender_id(String mSenderId){
        sender_id = mSenderId;
    }

    public void setRecipient_id(String mRecipientId){
        recipient_id = mRecipientId;
    }

    public void setMssg(String mMssg){
        mssg = mMssg;
    }

    public void setDate(String mDate) {date = mDate;}

    public String getId(){ return id;}
    public String getSender_id(){ return sender_id;}
    public String getRecipient_id(){ return recipient_id;}
    public String getMssg(){return mssg;}
    public String getDate(){return date;}
}
