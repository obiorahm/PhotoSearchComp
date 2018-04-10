package com.example.mgo983.photosearchcomp.data;

/**
 * Created by mgo983 on 3/15/18.
 */

public class Friends {
    private String id;
    private String username;
    private String person;
    private String friend;

    public Friends(){}

    public Friends(String mId, String mPerson, String mFriend){
        id = mId;

        this.person = mPerson;
        this.friend = mFriend;
    }

    public Friends(String mId, String mFriendUserName){
        id = mId;
        username = mFriendUserName;
    }

    public void setId(String mId ){ id = mId;}
    public void setPerson(String mPerson){ person = mPerson;}
    public void setFriend(String mFriend){ friend = mFriend;}

    public  void setUsername(String mFriendUserName){username = mFriendUserName; }

    public String getId(){return  id;}
    public String getUsername(){return username;}
    public String getPerson(){return person;}
    public String getFriend(){return friend;}
}
