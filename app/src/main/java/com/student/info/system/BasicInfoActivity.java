package com.student.info.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.student.info.system.bean.StudentInfoEntities;
import com.student.info.system.db.DBAdapter;
import com.student.info.system.service.NativePlaceService;

public class BasicInfoActivity extends AppCompatActivity implements ServiceConnection {

    private EditText stuIdLabel;
    private EditText nameLabel;
    private EditText idNumberLabel;
    private RadioGroup sexSelect;
    private EditText nativePlaceLabel;
    private EditText birthdayLabelYear;
    private EditText birthdayLabelMouth;
    private EditText birthdayLabelDay;
    private NativePlaceService service1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        init();
        initView();
        initListener();
    }

    private void init() {
        //绑定服务
        Intent service = new Intent(this, NativePlaceService.class);
        bindService(service, this, Context.BIND_AUTO_CREATE);

    }

    /**
     * 监听
     */
    private void initListener() {
        //监听填入的身份证号，填入籍贯、生日信息
        idNumberLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //身份证
                if (s.length() == 18) {
                    String idCard = s.toString().substring(6, 14);
                    String year = idCard.substring(0, 4);
                    String month = idCard.substring(4, 6);
                    String day = idCard.substring(6, 8);
                    birthdayLabelYear.setText(year);
                    birthdayLabelMouth.setText(month);
                    birthdayLabelDay.setText(day);
                }
                //查询籍贯信息
                if (s.length() == 6) {
                    String nativePlaceInfo = service1.getnNativePlaceInfo(s.toString());
                    nativePlaceLabel.setText(nativePlaceInfo);
                }
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        stuIdLabel = findViewById(R.id.stu_id_label);
        nameLabel = findViewById(R.id.name_label);
        idNumberLabel = findViewById(R.id.id_number_label);
        sexSelect = findViewById(R.id.sex_select);
        nativePlaceLabel = findViewById(R.id.native_place_label);
        birthdayLabelYear = findViewById(R.id.birthday_label_year);
        birthdayLabelMouth = findViewById(R.id.birthday_label_mouth);
        birthdayLabelDay = findViewById(R.id.birthday_label_day);

    }

    /**
     * 下一步
     *
     * @param view
     */
    public void nextStep(View view) {
        //检查数据
        if (checkData()) {
            return;
        }

        StudentInfoEntities entities=new StudentInfoEntities();
        entities.setStuIdLabel(stuIdLabel.getText().toString());
        entities.setNameLabel(nameLabel.getText().toString());
        entities.setIdNumberLabel(idNumberLabel.getText().toString());
        entities.setSexLabel(sexSelect.getCheckedRadioButtonId() == R.id.man ? "男" : "女");
        entities.setNativePlaceLabel(nativePlaceLabel.getText().toString());
        entities.setBirthdayLabel(birthdayLabelYear.getText().toString() + birthdayLabelMouth.getText().toString() + birthdayLabelDay.getText().toString());

        Intent intent = new Intent(this, SchoolInfoActivity.class);
        //传递至SchoolInfoActivity中
        intent.putExtra("studentInfo",entities);
        startActivity(intent);
        finish();
    }

    /**
     * 检查数据
     * @return true、false
     */
    private boolean checkData() {
        String stuIdLabelStr = stuIdLabel.getText().toString();
        String nameLabelStr = nameLabel.getText().toString();
        String idNumberLabelStr = idNumberLabel.getText().toString();
        if ("".equals(stuIdLabelStr)) {
            Toast.makeText(this, "学号不能为空！", Toast.LENGTH_SHORT).show();
            return true;
        }
        if ("".equals(nameLabelStr)) {
            Toast.makeText(this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
            return true;
        }
        if ("".equals(idNumberLabelStr) || idNumberLabelStr.length() != 18) {
            Toast.makeText(this, "身份证号信息错误！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NativePlaceService.MyBinder binder = (NativePlaceService.MyBinder) service;
        service1 = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
        unbindService(this);
    }
}
