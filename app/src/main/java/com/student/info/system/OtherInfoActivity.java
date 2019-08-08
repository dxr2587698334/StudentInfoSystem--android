package com.student.info.system;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.student.info.system.bean.StudentInfoEntities;
import com.student.info.system.db.DBAdapter;
import com.student.info.system.receiver.MyBroadcastReceiver;

import static com.student.info.system.db.DBAdapter.S_INFO_TABLE_NAME;

public class OtherInfoActivity extends AppCompatActivity {

    private static final String TAG = OtherInfoActivity.class.getSimpleName();
    private static final int SYSTEM_ALERT_WINDOW_CODE = 1;
    private EditText phoneLabel;
    private EditText emailLabel;
    private EditText weixinLabel;
    private CheckBox music;
    private CheckBox arts;
    private CheckBox handwriting;
    private CheckBox basketball;
    private CheckBox football;
    private CheckBox swimming;
    private StudentInfoEntities studentInfo;
    private static OtherInfoActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);

        init();
        initView();
        getIntentData();
    }

    private void init() {
        activity = this;
    }

    private void getIntentData() {
        Intent intent = getIntent();
        studentInfo = intent.getParcelableExtra("studentInfo");
    }

    /**
     * 初始化view
     */
    private void initView() {
        phoneLabel = findViewById(R.id.phone_label);
        emailLabel = findViewById(R.id.email_label);
        weixinLabel = findViewById(R.id.weixin_label);
        music = findViewById(R.id.music);
        arts = findViewById(R.id.arts);
        handwriting = findViewById(R.id.handwriting);
        basketball = findViewById(R.id.basketball);
        football = findViewById(R.id.football);
        swimming = findViewById(R.id.swimming);
    }

    /**
     * 提交
     *
     * @param view
     */
    public void submit(View view) {
        studentInfo.setPhoneLabel(phoneLabel.getText().toString());
        studentInfo.setEmailLabel(emailLabel.getText().toString());
        studentInfo.setWeixinLabel(weixinLabel.getText().toString());
        StringBuilder specialSkillStr = getSpecialSkillStr();

        studentInfo.setSpecialSkillLabel(specialSkillStr.toString());
        sendBroadcast();
    }


    /**
     * 发送广播
     */
    private void sendBroadcast() {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction("showStudentInfo");
        intent.putExtra("studentInfo", studentInfo);
        sendBroadcast(intent);
    }

    /**
     * 根据选择的拼接特长
     *
     * @return
     */
    @NonNull
    private StringBuilder getSpecialSkillStr() {
        StringBuilder specialSkillStr = new StringBuilder();
        if (music.isChecked()) {
            specialSkillStr.append(music.getText().toString()).append("、");
        }
        if (arts.isChecked()) {
            specialSkillStr.append(arts.getText().toString()).append("、");
        }
        if (handwriting.isChecked()) {
            specialSkillStr.append(handwriting.getText().toString()).append("、");
        }
        if (basketball.isChecked()) {
            specialSkillStr.append(basketball.getText().toString()).append("、");
        }
        if (football.isChecked()) {
            specialSkillStr.append(football.getText().toString()).append("、");
        }
        if (swimming.isChecked()) {
            specialSkillStr.append(swimming.getText().toString()).append("、");
        }
        if (!"".equals(specialSkillStr.toString())) {
            specialSkillStr.delete(specialSkillStr.length() - 1, specialSkillStr.length());
        }
        return specialSkillStr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 取巧，实战不能这样，申请权限失败了暂时的解决方案
        activity = null;
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {
        private SQLiteDatabase database;
        private DBAdapter dbHelper;

        public MyBroadcastReceiver() {

        }


        @Override
        public void onReceive(final Context context, Intent intent) {
            dbHelper = DBAdapter.getInstance(context);
            database = dbHelper.getWritableDatabase();
            if ("showStudentInfo".equals(intent.getAction())) {
                Log.e(TAG, "onReceive: 接收到了广播");
                //接收到广播，取出里面携带的数据
                final StudentInfoEntities studentInfo = intent.getParcelableExtra("studentInfo");
                //TODO 取巧，实战不能这样，申请权限失败了暂时的解决方案
                android.app.AlertDialog show = new android.app.AlertDialog.Builder(activity)
                        .setTitle("个人信息确认")
                        .setMessage(studentInfo.toString())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DBAdapter.stuIdLabel, studentInfo.getStuIdLabel());
                                contentValues.put(DBAdapter.nameLabel, studentInfo.getNameLabel());
                                contentValues.put(DBAdapter.idNumberLabel, studentInfo.getIdNumberLabel());
                                contentValues.put(DBAdapter.sexLabel, studentInfo.getSexLabel());
                                contentValues.put(DBAdapter.nativePlaceLabel,studentInfo.getNativePlaceLabel());
                                contentValues.put(DBAdapter.birthdayLabel, studentInfo.getBirthdayLabel());
                                contentValues.put(DBAdapter.school, studentInfo.getSchool());
                                contentValues.put(DBAdapter.majorLabel, studentInfo.getMajorLabel());
                                contentValues.put(DBAdapter.stuClassLabel, studentInfo.getStuClassLabel());
                                contentValues.put(DBAdapter.phoneLabel, studentInfo.getPhoneLabel());
                                contentValues.put(DBAdapter.emailLabel, studentInfo.getEmailLabel());
                                contentValues.put(DBAdapter.weixinLabel, studentInfo.getWeixinLabel());
                                contentValues.put(DBAdapter.specialSkillLabel, studentInfo.getSpecialSkillLabel());
                                dbHelper.insertOrUpdata(S_INFO_TABLE_NAME, "id_number_label=?", studentInfo.getIdNumberLabel(), contentValues, database);
                                //将数据存入数据库
                                Toast.makeText(context, "成功添加学生信息", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取消
                            }
                        }).create();
                show.show();
            }
        }
    }

}
