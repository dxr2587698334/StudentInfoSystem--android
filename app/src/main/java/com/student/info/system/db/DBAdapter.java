package com.student.info.system.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.student.info.system.App;
import com.student.info.system.utils.FileUtils;

public class DBAdapter extends SQLiteOpenHelper {

    private static final String TAG = DBAdapter.class.getSimpleName();

    /**
     * 数据库文件名
     */
    private static final String DB_NAME = "student_info_database1.db";
    /**
     * 身份证前6为对应位置的数据库表
     */
    public static final String C_ADDRESS_TABLE_NAME = "c_address";
    public static String cId = "c_id";
    public static String cAddressName = "c_address_name";
    /**
     * 学生信息表
     */
    public static final String S_INFO_TABLE_NAME = "s_info";
    /**学号*/
    public static String stuIdLabel="stu_id_label";
    /**姓名*/
    public static String nameLabel="name_label";
    /**身份证号*/
    public static String idNumberLabel ="id_number_label";
    /**性别*/
    public static String sexLabel="sex_label";
    /**籍贯*/
    public static String nativePlaceLabel="native_place_label";
    /**生日*/
    public static String birthdayLabel ="birthday_label";
    /**所在学院*/
    public static String school ="school";
    /**专业*/
    public static String majorLabel="major_label";
    /**班级*/
    public static String stuClassLabel ="stu_class_label";
    /**电话*/
    public static String phoneLabel="phone_label";
    /**电子邮箱*/
    public static String emailLabel="email_label";
    /**微信*/
    public static String weixinLabel="weixin_label";
    /**特长*/
    public static String specialSkillLabel="special_skill_label";

    /**
     * 数据库版本号
     */
    private static final int DB_VERSION = 1;


    /**
     * 当数据库文件创建时，执行初始化操作，并且只执行一次
     *
     * @param context Context对象
     */
    private DBAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    private volatile static DBAdapter dbAdapter;

    /**
     * 使用单例模式防止打开并发多个数据库操作者
     * @param context 上下文
     * @return DBAdapter
     */
    public static DBAdapter getInstance(Context context) {
        if (dbAdapter == null) {
            synchronized (DBAdapter.class) {
                if (dbAdapter == null) {
                    dbAdapter = new DBAdapter(context);
                }
            }
        }
        return dbAdapter;
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {
        Toast.makeText(App.getContext(), "正在初始化数据库...", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onCreate: 进入" );
        //1、1创建地区表
        String sql = "create table if not exists " +
                C_ADDRESS_TABLE_NAME +
                "(id integer primary key autoincrement, " +
                cId + " varchar, " +
                cAddressName + " varchar"
                + ")";
        Log.e(TAG, "onCreate:创建表：《"+C_ADDRESS_TABLE_NAME+"》sql\n"+ sql);
        db.execSQL(sql);
        //1、2创建学生信息表
        String sqlStudent = "create table if not exists " +
                S_INFO_TABLE_NAME +
                "(id integer primary key autoincrement, " +
                stuIdLabel + " varchar, " +
                nameLabel + " varchar, "+
                idNumberLabel + " varchar, "+
                sexLabel + " varchar, "+
                nativePlaceLabel + " varchar, "+
                birthdayLabel + " varchar, "+
                school + " varchar, "+
                majorLabel + " varchar, "+
                stuClassLabel + " varchar, "+
                phoneLabel + " varchar, "+
                emailLabel + " varchar, "+
                weixinLabel + " varchar, "+
                specialSkillLabel + " varchar"
                + ")";
        Log.e(TAG, "onCreate:创建表：《"+S_INFO_TABLE_NAME+"》sql\n"+ sqlStudent);
        db.execSQL(sqlStudent);


        //2、初始化数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String addressData = FileUtils.readFileToString(App.getContext(), "location.txt");
                if (!"".equals(addressData)) {
                    String[] allAddressInfo = addressData.split(" ");
                    Log.e(TAG, "插入数据个数："+allAddressInfo.length );
                    for (String addressInfos : allAddressInfo) {
                        String[] splitInfo = addressInfos.split(":");
                        //身份证前6位
                        String cardId = splitInfo[0];
                        //身份证前6位 对应的地址
                        String addressInfo = splitInfo[1];
                        ContentValues values=new ContentValues();
                        values.put(cId,cardId);
                        values.put(cAddressName,addressInfo);
                        db.insert(C_ADDRESS_TABLE_NAME,null,values);
                    }
                }

            }
        }).start();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库更新时（version++时）调用此方法
    }

    /**
     * 插入或更新
     * @param table 表名
     * @param selection 条件
     * @param selectionArg 条件值
     * @param contentValues 插入或更新值
     * @param db 数据库
     */
    public void insertOrUpdata(String table,String selection,String selectionArg,ContentValues contentValues,SQLiteDatabase db){
        Cursor query = db.query(table, null, selection, new String[]{selectionArg}, null, null, null);
        if (query.getCount()>0){
            //有值代表更新
            db.update(table,contentValues,selection,new String[]{selectionArg});
        }else {
            //无值代表新增
            db.insert(table,null,contentValues);
        }
        query.close();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
}
