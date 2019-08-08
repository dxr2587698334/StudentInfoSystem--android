package com.student.info.system;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    /**
     * 暴露全局context
     * @return Context对象
     */
    public static Context getContext(){
        return context;
    }
}

