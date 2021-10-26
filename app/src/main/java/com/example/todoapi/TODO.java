package com.example.todoapi;

public class TODO {

    private String mTask,mDescription;
    private boolean mIsCompleted;
    private int mId;

    TODO(String task , String description , boolean isCompleted , int id){
        mTask = task;
        mDescription = description;
        mIsCompleted = isCompleted;
        mId = id;
    }

    public String getmTask() {
        return mTask;
    }

    public String getmDescription() {
        return mDescription;
    }

    public boolean ismIsCompleted() {
        return mIsCompleted;
    }

    public int getmId() {
        return mId;
    }
}
