package com.student.info.system;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.student.info.system.bean.StudentInfoEntities;

public class SchoolInfoActivity extends AppCompatActivity {

    private EditText majorLabel;
    private EditText stuClassLabel;
    private Spinner nameLabel;
    private StudentInfoEntities studentInfo;
    private String school=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        initView();
        getIntentData();
        initListener();
    }

    private void initListener() {
        //下拉选择器
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        Resources res = getResources();
        String[] schoolList = res.getStringArray(R.array.schoollist);
        adapter.addAll(schoolList);
        nameLabel.setAdapter(adapter);
        nameLabel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                school = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        //获取BasicInfoActivity中传递的StudentInfoEntities对象
        studentInfo = intent.getParcelableExtra("studentInfo");

    }

    /**
     * 初始化view
     */
    private void initView() {
        majorLabel = findViewById(R.id.major_label);
        stuClassLabel = findViewById(R.id.stu_class_label);
        nameLabel = findViewById(R.id.school_abel);
    }

    /**
     * 点击跳转下一步
     *
     * @param view
     */
    public void nextStep(View view) {
        Intent intent = new Intent(this, OtherInfoActivity.class);

        studentInfo.setSchool(school);
        studentInfo.setMajorLabel(majorLabel.getText().toString());
        studentInfo.setStuClassLabel(stuClassLabel.getText().toString());
        //将学生信息继续传递至 OtherInfoActivity
        intent.putExtra("studentInfo",studentInfo);
        startActivity(intent);
    }
}
