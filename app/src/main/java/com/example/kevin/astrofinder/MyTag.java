package com.example.kevin.astrofinder;

import android.util.Log;

public class MyTag {
    private int Tag;

    public MyTag (int tag) {

        int Tag = tag;
    }

    public int getMyTag(){
        Log.d("getMyTag", Integer.toString(Tag));
        return Tag;}
}

