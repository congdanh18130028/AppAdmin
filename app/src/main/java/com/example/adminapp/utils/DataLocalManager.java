package com.example.adminapp.utils;

import android.content.Context;

public class DataLocalManager {
    private static final String TOKEN = "TOKEN";

    private static DataLocalManager instance;
    private MySharedReferences mySharedReferences;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedReferences = new MySharedReferences(context);

    }
    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setToken(String token){
        DataLocalManager.getInstance().mySharedReferences.putValue(TOKEN, token);

    }

    public static String getToken(){
        return DataLocalManager.getInstance().mySharedReferences.getValueString(TOKEN);
    }

    public static void removeToken(){
        DataLocalManager.getInstance().mySharedReferences.remove(TOKEN);
    }




}
