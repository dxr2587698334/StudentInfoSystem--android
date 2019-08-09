package com.student.info.system.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.student.info.system.db.DBAdapter;

import static com.student.info.system.db.DBAdapter.C_ADDRESS_TABLE_NAME;

/**
 * 籍贯查询
 */
public class NativePlaceService extends Service {

    private static final String TAG = NativePlaceService.class.getSimpleName();
    private MyBinder mBinder = new MyBinder();
    private SQLiteDatabase database;


    public class MyBinder extends Binder {

        public NativePlaceService getService() {
            return NativePlaceService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DBAdapter dbHelper = DBAdapter.getInstance(this);
        database = dbHelper.getReadableDatabase();

    }

    /**
     * 使用身份证前六位查询数据库
     *
     * @param card6 身份证前六位
     * @return 地址
     */
    public String getnNativePlaceInfo(String card6) {
        Log.e(TAG, "开始查询籍贯信息，条件为: "+card6 );
        Cursor cursor = database.query(C_ADDRESS_TABLE_NAME, null, "c_id=?", new String[]{card6}, null, null, null);
        String cAddressName = null;
        while (cursor.moveToNext()) {
            cAddressName = cursor.getString(cursor.getColumnIndex("c_address_name"));
        }
        cursor.close();
        Log.e(TAG, "查询结果为: "+cAddressName );
        return cAddressName;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        database.close();
    }
}
