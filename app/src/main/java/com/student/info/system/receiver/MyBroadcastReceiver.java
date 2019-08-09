package com.student.info.system.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.student.info.system.R;
import com.student.info.system.bean.StudentInfoEntities;


public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = MyBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        if ("showStudentInfo".equals(intent.getAction())) {
            Log.e(TAG, "onReceive: 接收到了广播" );
            //接收到广播，取出里面携带的数据
            StudentInfoEntities studentInfo = intent.getParcelableExtra("studentInfo");

            AlertDialog show = new AlertDialog.Builder(context)
                    .setTitle("个人信息确认")
                    .setMessage(studentInfo.toString())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //将数据存入数据库
                            Toast.makeText(context, "成功添加学生信息", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消
                        }
                    }).create();
            show.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
            show.show();
        }
    }
}
