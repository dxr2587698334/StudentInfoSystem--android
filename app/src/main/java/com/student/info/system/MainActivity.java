package com.student.info.system;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.student.info.system.db.DBAdapter;

import static com.student.info.system.db.DBAdapter.S_INFO_TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        DBAdapter mHelper = DBAdapter.getInstance(this);
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
    }

    /**
     * 点击跳转 BasicInfoActivity页面
     * @param view
     */
    public void toBasicInfoActivity(View view) {
        startActivity(new Intent(this,BasicInfoActivity.class));
    }
}
