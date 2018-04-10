package com.example.mgo983.photosearchcomp.data;

/**
 * Created by mgo983 on 3/14/18.
 */

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String friends_id;


    public User(String mFirstName, String mLastName){
        first_name = mFirstName;
        last_name = mLastName;
    }

    public User(String mId, String mFirstName, String mLastName){
        id = mId;
        first_name = mFirstName;
        last_name = mLastName;
    }

    public void setFirstName(String mFirstName){
        first_name = mFirstName;
    }

    public void setLastName(String mLastName){
        last_name = mLastName;
    }

    public void setId(String mId){
        id = mId;
    }

    public String getId(){ return id;}
    public String getFirst_name(){return first_name;}
    public String getLast_name(){return last_name;}
}
